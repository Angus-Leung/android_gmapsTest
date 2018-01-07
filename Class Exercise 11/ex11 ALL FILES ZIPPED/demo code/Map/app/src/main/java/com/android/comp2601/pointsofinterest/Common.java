package com.android.comp2601.pointsofinterest;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by seansteelebenjamin on 16-02-07.
 */
public class Common {

    static final String STATE_CITY_NAME = "city_name";
    static final String LOCATION_BUNDLE = "location";

    //TODO: Add two city name(String) and coordinates (LatLng) to createMapCoordinates hashmap
    public static HashMap<String, LatLng> createMapCoordinates(){

        HashMap<String, LatLng> mCityCoord = new HashMap<String, LatLng>();

        LatLng toronto = new LatLng(43.7000, -79.4000);
        LatLng ottawa = new LatLng(45.4214, -75.6919);
        mCityCoord.put("Ottawa", ottawa);
        mCityCoord.put("Toronto", toronto);

        return mCityCoord;
    }
}
