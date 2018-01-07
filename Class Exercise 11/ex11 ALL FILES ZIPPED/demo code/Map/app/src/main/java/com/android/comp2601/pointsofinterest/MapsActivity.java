package com.android.comp2601.pointsofinterest;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    private  final String TAG = this.getClass().getSimpleName();

    private GoogleMap mMap;
    private HashMap<String, LatLng> mCityCoordinates;
    private String mCity;
    private LatLng mCityLatLng;
    private final String mDefaultCity = "Ottawa";
    private Button mReturnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mReturnButton = (Button) findViewById(R.id.button_return_main);

        mCityCoordinates = Common.createMapCoordinates();

        Intent cityIntent = getIntent();
        if (cityIntent != null) {
            Bundle intentBundle = cityIntent.getBundleExtra(Common.LOCATION_BUNDLE);
            mCity = intentBundle.getString(Common.STATE_CITY_NAME);
        } else {
            //default city location
            mCity = mDefaultCity;
        }

        Log.i(TAG, "What city:" + mCity);
        mCityLatLng = mCityCoordinates.get(mCity);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //set listeners for Map click and Map Long click
        //mMap.setOnMapClickListener(...);

        /*TODO:
            1) Make the map hybrid type
            2) animateCamera to zoom into chosen city
        */
        CameraPosition cameraPosition = new CameraPosition(mCityLatLng, 15, 0, 0); //create appropriate camera position
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMapClickListener(this);

        mMap.setOnMapLongClickListener(this);

        HashMap<String, ArrayList<Marker>> mListCityMarkers;

        mListCityMarkers = MainActivity.getInstance().getMarkers();

        if(!mListCityMarkers.isEmpty() && mListCityMarkers.containsKey(mCity)){
            ArrayList<Marker> mCityMarkers = mListCityMarkers.get(mCity);
            for(Marker mPoint : mCityMarkers){
                mMap.addMarker(new MarkerOptions()
                .position(new LatLng(mPoint.getPosition().latitude, mPoint.getPosition().longitude)));
            }

        }

    }


    /*TODO:
        Add onMapClick code to move the map location towards the new location of a short tap or click
    */
    @Override
    public void onMapClick(LatLng latLng) {

    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

    }


    /*TODO:
        Add onLongMapClick() code to add a marker to the map and save the marker into
         mMarkers hashmap on a long tap or click
    */

    @Override
    public void onMapLongClick(LatLng point) {

        Marker marker = mMap.addMarker(new MarkerOptions().position(point));
        MainActivity.getInstance().getMarkers().get(mCity).add(marker);
       // MainActivity.mMarkers.put("city", tempMarker);
    }

}
