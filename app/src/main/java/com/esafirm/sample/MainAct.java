package com.esafirm.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.esafirm.rxgeofence.RxGeoFence;
import com.esafirm.rxgeofence.model.GeoFenceEvent;
import com.esafirm.rxgeofence.model.GeoFenceType;
import com.esafirm.rxgeofence.model.LatLng;
import com.esafirm.rxgeofence.model.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;
import java.util.Arrays;
import java.util.List;
import rx.AsyncEmitter;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by esafirm on 7/29/16.
 */
public class MainAct extends AppCompatActivity {

  private static final LatLng INITIAL_LOCATION = new LatLng(-6.884170, 107.553127);
  private static final String GEO_FIRE_DB = "https://fir-codelab-b635b.firebaseio.com/";
  private static final int INITIAL_ZOOM_LEVEL = 14;

  private Marker userMarker;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    SupportMapFragment mapFragment =
        (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
    mapFragment.getMapAsync(new OnMapReadyCallback() {
      @Override public void onMapReady(GoogleMap googleMap) {
        setup(googleMap);
      }
    });
  }

  private void setup(final GoogleMap googleMap) {
    com.google.android.gms.maps.model.LatLng latLng =
        new com.google.android.gms.maps.model.LatLng(INITIAL_LOCATION.getLatitude(),
            INITIAL_LOCATION.getLongitude());

    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, INITIAL_ZOOM_LEVEL));
    googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
      @Override public void onMapClick(com.google.android.gms.maps.model.LatLng latLng) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, INITIAL_ZOOM_LEVEL));
      }
    });

    for (Place place : getPlaces()) {
      MapUtils.addCircleFromPlace(googleMap, place);
    }

    final TextView txtInfo = (TextView) findViewById(R.id.txtInfo);

    RxGeoFence.with(this)
        .dbUrl(GEO_FIRE_DB)
        .geoFenceSource(getGeoFenceSource())
        .locationSource(getLocationSource(googleMap))
        .build()
        .filter(new Func1<GeoFenceEvent, Boolean>() {
          @Override public Boolean call(GeoFenceEvent geoFenceEvent) {
            return geoFenceEvent.getType() == GeoFenceType.ENTER;
          }
        })
        .subscribe(new Action1<GeoFenceEvent>() {
          @Override public void call(GeoFenceEvent geoFenceEvent) {
            txtInfo.setText(String.format("Welcome to %s", geoFenceEvent.getPlace()
                .getName()));
          }
        });
  }

  private Observable<LatLng> getLocationSource(final GoogleMap googleMap) {
    return Observable.fromAsync(new Action1<AsyncEmitter<LatLng>>() {
      @Override public void call(final AsyncEmitter<LatLng> emitter) {
        emitter.onNext(INITIAL_LOCATION);

        googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
          @Override public void onCameraChange(CameraPosition cameraPosition) {
            emitter.onNext(
                new LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude));
          }
        });
      }
    }, AsyncEmitter.BackpressureMode.BUFFER)
        .doOnNext(new Action1<LatLng>() {
          @Override public void call(LatLng latLng) {

            if (userMarker == null) {
              userMarker = MapUtils.addMarker(googleMap, "My Location", latLng.getLatitude(),
                  latLng.getLongitude());
              return;
            }
            userMarker.setPosition(
                new com.google.android.gms.maps.model.LatLng(latLng.getLatitude(),
                    latLng.getLongitude()));
          }
        });
  }

  private Observable<List<Place>> getGeoFenceSource() {
    return Observable.just(getPlaces());
  }

  private List<Place> getPlaces() {
    String data = "\n"
        + "[{\"name\":\"RS Cibabat\",\"lat\":-6.879632,\"lng\":107.550895,\"rad\":0.5},{\"name\":\"Rabbani\",\"lat\":-6.875925,\"lng\":107.547162,\"rad\":0.2},{\"name\":\"Pizza Hut\",\"lat\":-6.881635,\"lng\":107.551904,\"rad\":0.2},{\"name\":\"Masjid Agung Cimahi\",\"lat\":-6.87322,\"lng\":107.542162,\"rad\":0.5},{\"name\":\"Cisangkan Motor\",\"lat\":-6.870812,\"lng\":107.537355,\"rad\":0.3},{\"name\":\"Transmart\",\"lat\":6.869726,\"lng\":107.533836,\"rad\":0.4}]";
    return Arrays.asList(new Gson().fromJson(data, Place[].class));
  }
}
