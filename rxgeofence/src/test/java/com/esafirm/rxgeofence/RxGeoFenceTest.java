package com.esafirm.rxgeofence;

import com.esafirm.rxgeofence.model.GeoFenceEvent;
import org.junit.Test;
import rx.observers.TestSubscriber;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class RxGeoFenceTest {
  @Test public void testRaceCondition() throws Exception {
    TestSubscriber<GeoFenceEvent> testSubscriber = new TestSubscriber<>();
  }
}