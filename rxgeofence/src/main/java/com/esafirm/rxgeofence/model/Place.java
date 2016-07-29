package com.esafirm.rxgeofence.model;

import com.firebase.geofire.GeoLocation;

/**
 * Created by esafirm on 7/28/16.
 */
public class Place {

  private String name;
  private double lat;
  private double lng;
  private double rad;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getLat() {
    return lat;
  }

  public void setLat(double lat) {
    this.lat = lat;
  }

  public double getLng() {
    return lng;
  }

  public void setLng(double lng) {
    this.lng = lng;
  }

  public double getRad() {
    return rad;
  }

  public void setRad(double rad) {
    this.rad = rad;
  }

  public GeoLocation toGeoLocation() {
    return new GeoLocation(lat, lng);
  }
}
