package com.kelvin.jacksgogo.Activities.Appointment;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.kelvin.jacksgogo.CustomView.JGGMapViewActionbarView;
import com.kelvin.jacksgogo.Fragments.Appointments.JGGMapViewFragment;
import com.kelvin.jacksgogo.R;

public class JGGMapViewActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    GeoDataClient mGeoDataClient;
    PlaceDetectionClient mPlaceDetectionClient;
    FusedLocationProviderClient mFusedLocationProviderClient;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jgg_map_view_activity);

        // Construct a GeoDataClient.
        mGeoDataClient = Places.getGeoDataClient(this, null);
        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);
        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        JGGMapViewActionbarView actionbarView = new JGGMapViewActionbarView(this);
        mToolbar = (Toolbar) findViewById(R.id.map_view_actionbar);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        JGGMapViewFragment frag = new JGGMapViewFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.map_container, frag, frag.getTag());
        ft.commit();

    }

    private void getLocationPermission() {
    /*
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */
//        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
//                android.Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            mLocationPermissionGranted = true;
//        } else {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
//                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
//        }
    }
}
