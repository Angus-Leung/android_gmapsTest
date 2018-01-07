package com.android.comp2601.pointsofinterest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;



public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private  final String TAG = this.getClass().getSimpleName();

    //TODO: Create a spinner and button
    Spinner mCitySpinner;
    Button mCityButton;


    private String mPickedCity = null;
    private static MainActivity instance; //globally accessible instance

    public static HashMap<String, ArrayList<Marker>> mMarkers = new HashMap<String, ArrayList<Marker>>();

    public static MainActivity getInstance(){return instance;}

    public static HashMap<String, ArrayList<Marker>> getMarkers(){
        return mMarkers;
    }

    private void initMarkerHashTable(){
        HashMap<String, LatLng> initCoor = Common.createMapCoordinates();
        Set<String> mCityNames = initCoor.keySet();
        for(String mName : mCityNames){
            mMarkers.put(mName, new ArrayList<Marker>());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCitySpinner = (Spinner) findViewById(R.id.CitySpinner);
        mCityButton = (Button) findViewById(R.id.CityButton);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
            R.array.city_names, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mCitySpinner.setAdapter(adapter);

        mCitySpinner.setOnItemSelectedListener(this);

        //Creates the hashmap to save points of interest Makers for each city
        initMarkerHashTable();


        //TODO: Add button and a spinner listeners and create an ArrayAdapter to be used by the spinner
        mCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mapIntent = new Intent(MainActivity.this, MapsActivity.class);
                //or you can use intent.putExtra or parcelable
                if(mPickedCity != null) {
                    Bundle mapsInfoBundle = new Bundle();
                    mapsInfoBundle.putString(Common.STATE_CITY_NAME, mPickedCity);
                    mapIntent.putExtra(Common.LOCATION_BUNDLE, mapsInfoBundle);
                }
                startActivity(mapIntent);


            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        mPickedCity = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
