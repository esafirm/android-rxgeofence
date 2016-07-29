package com.esafirm.rxgeofence;

import android.content.Context;
import com.esafirm.rxgeofence.model.GeoFenceEvent;
import com.esafirm.rxgeofence.model.LatLng;
import com.esafirm.rxgeofence.model.Place;
import java.util.List;
import rx.Observable;

/**
 * Created by esafirm on 7/29/16.
 */
public class RxGeoFence {

  public static RxGeoFenceBuilder with(Context context) {
    return new RxGeoFenceBuilder(context);
  }

  public static class RxGeoFenceBuilder {

    private final Context context;
    private Observable<LatLng> locationSource;
    private Observable<List<Place>> geoFenceSource;
    private String dbUrl;

    private RxGeoFenceBuilder(Context context) {
      this.context = context;
    }

    public RxGeoFenceBuilder dbUrl(String url) {
      this.dbUrl = url;
      return this;
    }

    public RxGeoFenceBuilder locationSource(Observable<LatLng> locationSource) {
      this.locationSource = locationSource;
      return this;
    }

    public RxGeoFenceBuilder geoFenceSource(Observable<List<Place>> geoFenceSource) {
      this.geoFenceSource = geoFenceSource;
      return this;
    }

    public Observable<GeoFenceEvent> build() {
      RxGeoFenceOnSubscribe rxGeoFence = new RxGeoFenceOnSubscribe(context, dbUrl);
      rxGeoFence.setLocationSource(locationSource);
      rxGeoFence.setGeoFenceSource(geoFenceSource);
      return Observable.create(rxGeoFence);
    }
  }
}

