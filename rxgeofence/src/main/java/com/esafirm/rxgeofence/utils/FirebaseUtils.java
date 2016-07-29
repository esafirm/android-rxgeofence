package com.esafirm.rxgeofence.utils;

import android.content.Context;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

/**
 * Created by esafirm on 7/29/16.
 */
public class FirebaseUtils {

  public static FirebaseApp getApp(Context context, FirebaseOptions options, String appName) {
    FirebaseApp app = getApp(appName);
    if (app == null) {
      app = FirebaseApp.initializeApp(context, options, appName);
    }
    return app;
  }

  private static FirebaseApp getApp(String appName) {
    FirebaseApp app;
    try {
      app = FirebaseApp.getInstance(appName);
      return app;
    } catch (Exception e) {
      return null;
    }
  }
}
