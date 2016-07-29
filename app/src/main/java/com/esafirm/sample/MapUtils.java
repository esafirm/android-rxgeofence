package com.esafirm.sample;

import android.graphics.Color;
import com.esafirm.rxgeofence.model.Place;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by esafirm on 7/28/16.
 */
public class MapUtils {

  public static Marker addMarker(GoogleMap googleMap, String name, double latitude,
      double longitude) {
    return googleMap.addMarker(new MarkerOptions().position(
        new LatLng(latitude, longitude)
    ).title(name));
  }

  public static Circle addCircleFromPlace(GoogleMap googleMap, Place place) {
    return googleMap.addCircle(new CircleOptions().center(getLatLngFromPlace(place))
        .radius(place.getRad() * 1000)
        .fillColor(Color.argb(66, 255, 0, 255))
        .strokeColor(Color.argb(66, 0, 0, 0)));
  }

  private static LatLng getLatLngFromPlace(Place place) {
    return new LatLng(place.getLat(), place.getLng());
  }
}
