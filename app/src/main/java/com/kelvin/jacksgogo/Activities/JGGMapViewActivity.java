package com.kelvin.jacksgogo.Activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.Fragments.Appointments.AppMapViewFragment;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;

public class JGGMapViewActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private JGGActionbarView actionbarView;
    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_map_view);

        // Top Toolbar Initialize
        actionbarView = new JGGActionbarView(this);
        mToolbar = (Toolbar) findViewById(R.id.map_view_actionbar);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        actionbarView.setStatus(JGGActionbarView.EditStatus.MAP, AppointmentType.UNKNOWN);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                if (view.getId() == R.id.btn_back) {
                    JGGMapViewActivity.this.finish();
                }
            }
        });

        // Google MapView Initialize
        getLocationPermission();
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        // Construct a GeoDataClient.
        mGeoDataClient = Places.getGeoDataClient(this, null);
        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);
        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }
}
