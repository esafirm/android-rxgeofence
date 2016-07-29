package com.esafirm.rxgeofence;

import android.content.Context;
import com.esafirm.rxgeofence.geofire.GeoQueryWrapper;
import com.esafirm.rxgeofence.geofire.PlaceGeoQueryEventListener;
import com.esafirm.rxgeofence.model.GeoFenceEvent;
import com.esafirm.rxgeofence.model.GeoFenceType;
import com.esafirm.rxgeofence.model.LatLng;
import com.esafirm.rxgeofence.model.Place;
import com.esafirm.rxgeofence.model.exception.GeoFenceError;
import com.esafirm.rxgeofence.utils.FirebaseUtils;
import com.esafirm.rxgeofence.utils.Preconditions;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoQuery;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by esafirm on 7/29/16.
 */
public class RxGeoFenceOnSubscribe implements Observable.OnSubscribe<GeoFenceEvent> {

  private static final String GEO_FIRE_REF = "/_geofire";
  private static final String APP_ID = "RxGeoFence";
  private static final String USER_KEY = "UserRxGeoFence";

  private GeoFire geoFire;
  private Observable<List<Place>> geoFenceSource;
  private Observable<LatLng> locationSource;

  private List<GeoQueryWrapper<Place>> queries = new ArrayList<>();

  public RxGeoFenceOnSubscribe(Context context, String dbUrl) {

    FirebaseOptions options = new FirebaseOptions.Builder().setApplicationId(APP_ID)
        .setDatabaseUrl(dbUrl)
        .build();
    FirebaseApp app = FirebaseUtils.getApp(context, options, APP_ID);

    geoFire = new GeoFire(FirebaseDatabase.getInstance(app)
        .getReferenceFromUrl(dbUrl + GEO_FIRE_REF));
  }

  public void setLocationSource(Observable<LatLng> locationSource) {
    this.locationSource = locationSource;
  }

  public void setGeoFenceSource(Observable<List<Place>> geoFenceSource) {
    this.geoFenceSource = geoFenceSource;
  }

  @Override public void call(final Subscriber<? super GeoFenceEvent> subscriber) {
    Preconditions.checkNotNull(locationSource, "Location source must not be null");
    Preconditions.checkNotNull(geoFenceSource, "Geo fence source must not be null");

    locationSource.forEach(new Action1<LatLng>() {
      @Override public void call(LatLng latLng) {
        geoFire.setLocation(USER_KEY, latLng.toGeoLocation());
      }
    });

    geoFenceSource.forEach(new Action1<List<Place>>() {
      @Override public void call(List<Place> places) {
        stopListen();
        for (Place place : places) {
          listen(
              new GeoQueryWrapper<>(geoFire.queryAtLocation(place.toGeoLocation(), place.getRad()),
                  place), subscriber);
        }
      }
    });
  }

  private void listen(GeoQueryWrapper<Place> wrapper,
      final Subscriber<? super GeoFenceEvent> subscriber) {

    GeoQuery geoQuery = wrapper.getGeoQuery();
    geoQuery.addGeoQueryEventListener(new PlaceGeoQueryEventListener(wrapper.getData()) {
      @Override public void onChange(GeoFenceType type, String key, Place place) {
        if (key.equalsIgnoreCase(USER_KEY)) {
          subscriber.onNext(new GeoFenceEvent(key, place, type));
        }
      }

      @Override public void onGeoQueryError(DatabaseError error) {
        subscriber.onError(new GeoFenceError(error));
      }
    });
    queries.add(wrapper);
  }

  private void stopListen() {
    for (GeoQueryWrapper<Place> wrapper : queries) {
      GeoQuery geoQuery = wrapper.getGeoQuery();
      geoQuery.removeAllListeners();
    }
    queries.clear();
  }
}
