package com.esafirm.rxgeofence.model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import com.esafirm.rxgeofence.utils.Preconditions;
import com.firebase.geofire.GeoLocation;

/**
 * Created by esafirm on 7/29/16.
 */
public class LatLng implements Parcelable {

  private final double latitude;
  private final double longitude;

  public LatLng(double latitude, double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public LatLng(Location location) {
    Preconditions.checkNotNull(location, "Location must not be null");
    latitude = location.getLatitude();
    longitude = location.getLongitude();
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeDouble(this.latitude);
    dest.writeDouble(this.longitude);
  }

  protected LatLng(Parcel in) {
    this.latitude = in.readDouble();
    this.longitude = in.readDouble();
  }

  public static final Parcelable.Creator<LatLng> CREATOR = new Parcelable.Creator<LatLng>() {
    @Override public LatLng createFromParcel(Parcel source) {
      return new LatLng(source);
    }

    @Override public LatLng[] newArray(int size) {
      return new LatLng[size];
    }
  };

  public GeoLocation toGeoLocation() {
    return new GeoLocation(latitude, longitude);
  }
}
