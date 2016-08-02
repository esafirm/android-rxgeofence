##RxGeoFence

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-RxGeoFence-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/4004)


<img src="https://github.com/esafirm/android-rxgeofence/blob/master/art/ss1.png" width="200px">

Rx wrapped GeoFence library, built on top of `GeoFire` well, maybe in the future we can choose the engine ( such as Awarness API) or maybe or own implementation :punch:

##Download
```groovy
allprojects {
		repositories {
			maven { url "https://jitpack.io" }
		}
	}
```	
```
dependencies {
	        compile 'com.github.esafirm:android-rxgeofence:latest-version'
	}
```
	
##Setup

```java
 RxGeoFence.with(this) //context
        .dbUrl(GEO_FIRE_DB) //String
        .geoFenceSource(...) // rx.Observable<List<Place>>
        .locationSource(...) // rx.Observable<LatLng>
        .build()
        .subscribe(geoFenceEvent -> {
         // do what you wanna do with the event
     });
```

You can see the implementation on the example. 

##Running Sample

 1. Create `gradle.properties` in the root of the project
 2. Add `Firebase` database URL and `GoogleMap` API key to your `gradle.properties`

```
#gradle.properties

GEO_FIRE_DB="ENTER_YOUR_FIREBASE_DB_URL_HERE"
MAP_KEY=ENTER_YOUT_MAP_API_KEY_HERE
```

~ *You're good to go*




---
---
```
The MIT License (MIT)

Copyright (c) 2016 Esa Firman

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
