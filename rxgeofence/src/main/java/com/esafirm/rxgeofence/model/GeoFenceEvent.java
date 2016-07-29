package com.esafirm.rxgeofence.model;

/**
 * Created by esafirm on 7/29/16.
 */
public class GeoFenceEvent {
  private String key;
  private Place place;
  private GeoFenceType type;

  public GeoFenceEvent(String key, Place place, GeoFenceType type) {
    this.key = key;
    this.place = place;
    this.type = type;
  }

  public String getKey() {
    return key;
  }

  public Place getPlace() {
    return place;
  }

  public GeoFenceType getType() {
    return type;
  }
}
