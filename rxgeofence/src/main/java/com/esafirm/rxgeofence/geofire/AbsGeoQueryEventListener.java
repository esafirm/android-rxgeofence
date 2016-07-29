package com.esafirm.rxgeofence.geofire;

import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.firebase.database.DatabaseError;

/**
 * Created by esafirm on 7/28/16.
 */
public abstract class AbsGeoQueryEventListener implements GeoQueryEventListener {

  @Override public void onGeoQueryError(DatabaseError error) {
  }

  @Override public void onGeoQueryReady() {
  }

  @Override public void onKeyMoved(String key, GeoLocation location) {
  }

  @Override public void onKeyExited(String key) {
  }

  @Override public void onKeyEntered(String key, GeoLocation location) {
  }
}
