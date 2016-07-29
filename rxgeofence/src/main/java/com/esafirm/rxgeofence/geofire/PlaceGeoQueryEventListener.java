package com.esafirm.rxgeofence.geofire;

import com.esafirm.rxgeofence.model.GeoFenceType;
import com.esafirm.rxgeofence.model.Place;
import com.firebase.geofire.GeoLocation;
import com.google.firebase.database.DatabaseError;

/**
 * Created by esafirm on 7/29/16.
 */
public abstract class PlaceGeoQueryEventListener extends AbsGeoQueryEventListener {

  private final Place place;

  public PlaceGeoQueryEventListener(Place place) {
    this.place = place;
  }

  public abstract void onChange(GeoFenceType geoFenceType, String key, Place place);

  public abstract void onGeoQueryError(DatabaseError error);

  @Override public void onKeyEntered(String key, GeoLocation location) {
    onChange(GeoFenceType.ENTER, key, place);
  }

  @Override public void onKeyExited(String key) {
    onChange(GeoFenceType.EXIT, key, place);
  }

  @Override public void onKeyMoved(String key, GeoLocation location) {
    onChange(GeoFenceType.MOVE, key, place);
  }
}
