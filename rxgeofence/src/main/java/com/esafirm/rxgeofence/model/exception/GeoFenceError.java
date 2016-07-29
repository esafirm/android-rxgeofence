package com.esafirm.rxgeofence.model.exception;

import com.google.firebase.database.DatabaseError;

/**
 * Created by esafirm on 7/29/16.
 */
public class GeoFenceError extends Exception {

  private final DatabaseError databaseError;

  public GeoFenceError(DatabaseError databaseError) {
    this.databaseError = databaseError;
  }

  public DatabaseError getDatabaseError() {
    return databaseError;
  }
}
