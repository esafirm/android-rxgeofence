package com.esafirm.rxgeofence.geofire;

import com.firebase.geofire.GeoQuery;

/**
 * Created by esafirm on 7/28/16.
 */
public class GeoQueryWrapper<T> {

  private final GeoQuery geoQuery;
  private final T data;

  public GeoQueryWrapper(GeoQuery geoQuery, T data) {
    this.geoQuery = geoQuery;
    this.data = data;
  }

  public GeoQuery getGeoQuery() {
    return geoQuery;
  }

  public T getData() {
    return data;
  }
}
