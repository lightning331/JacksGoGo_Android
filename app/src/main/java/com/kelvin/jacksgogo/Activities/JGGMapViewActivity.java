package com.kelvin.jacksgogo.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.kelvin.jacksgogo.Utils.LocationManager.JGGMapPermissionUtils;
import com.kelvin.jacksgogo.Utils.Models.System.JGGAddressModel;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.EVENTS;
import static com.kelvin.jacksgogo.Utils.Global.GOCLUB;
import static com.kelvin.jacksgogo.Utils.Global.JOBS;
import static com.kelvin.jacksgogo.Utils.Global.SERVICES;
import static com.kelvin.jacksgogo.Utils.Global.USERS;

public class JGGMapViewActivity extends AppCompatActivity implements View.OnClickListener,
        OnMapReadyCallback,
        GoogleMap.OnMapClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        ActivityCompat.OnRequestPermissionsResultCallback,
        JGGMapPermissionUtils.PermissionResultCallback {

    @BindView(R.id.lbl_lat) TextView lblLat;
    @BindView(R.id.lbl_lng) TextView lblLng;
    @BindView(R.id.lbl_address) TextView lblAddress;
    @BindView(R.id.btn_location) LinearLayout btnLocation;
    @BindView(R.id.img_location) ImageView imgLocation;
    private Toolbar mToolbar;
    private JGGActionbarView actionbarView;

    private final static int PLAY_SERVICES_REQUEST = 1000;
    private final static int REQUEST_CHECK_SETTINGS = 2000;
    private ArrayList<String> permissions = new ArrayList<>();
    private JGGMapPermissionUtils permissionUtils;
    boolean isPermissionGranted;

    private GoogleMap mGoogleMap;
    private MarkerOptions markerOpt;
    private SupportMapFragment mapFrag;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private int markerResource;

    private AppointmentType mType;

    public JGGAddressModel mAddress = new JGGAddressModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jgg_map_view);
        ButterKnife.bind(this);

        String type = getIntent().getExtras().getString(APPOINTMENT_TYPE);
        if (type.equals(SERVICES)) {
            mType = AppointmentType.SERVICES;
            imgLocation.setImageResource(R.mipmap.button_location_green);
            markerResource = R.mipmap.icon_pin;
        } else if (type.equals(JOBS)) {
            mType = AppointmentType.JOBS;
            imgLocation.setImageResource(R.mipmap.button_location_cyan);
            markerResource = R.mipmap.icon_pin;
        } else if (type.equals(GOCLUB) || type.equals(EVENTS)) {
            mType = AppointmentType.GOCLUB;
            imgLocation.setImageResource(R.mipmap.button_location_purple);
            markerResource = R.mipmap.icon_pin;
        } else if (type.equals(USERS)) {
            mType = AppointmentType.USERS;
            imgLocation.setImageResource(R.mipmap.button_location_orange);
            markerResource = R.mipmap.icon_pin;
        }

        // Top Toolbar Initialize
        actionbarView = new JGGActionbarView(this);
        mToolbar = (Toolbar) findViewById(R.id.map_view_actionbar);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        actionbarView.setStatus(JGGActionbarView.EditStatus.LOCATION, mType);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                if (view.getId() == R.id.btn_more) {
                    Gson gson = new Gson();
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("result", gson.toJson(mAddress));
                    setResult(Activity.RESULT_OK, returnIntent);
                }
                JGGMapViewActivity.this.finish();
            }
        });

        //To get MapFragment reference from xml layout
        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.active_service_map_view);
        mapFrag.getMapAsync(this);
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
            }
        });

        permissionUtils=new JGGMapPermissionUtils(this);
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionUtils.check_permission(permissions,"Need GPS permission for getting your location",1);

        // check availability of play services
        if (checkPlayServices()) {

            // Building the GoogleApi client
            buildGoogleApiClient();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPlayServices();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    /**
     * Method to display the location on UI
     * */
    private void getLocation() {

        if (isPermissionGranted) {
            try {
                mLastLocation = LocationServices.FusedLocationApi
                        .getLastLocation(mGoogleApiClient);
                if (mLastLocation != null) {
                    LatLng latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());

                    getAddress(JGGMapViewActivity.this, mLastLocation.getLatitude(), mLastLocation.getLongitude());

                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    private void getAddress(Context context, double LATITUDE, double LONGITUDE) {

        //Set Address
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null && addresses.size() > 0) {

                // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String countryCode = addresses.get(0).getCountryCode();
                String postalCode = addresses.get(0).getPostalCode();
                String featureName = addresses.get(0).getFeatureName();
                String subLocality = addresses.get(0).getSubLocality();
                String thoroughfare = addresses.get(0).getThoroughfare();// Only if available else return NULL

                lblAddress.setText(address);
                DecimalFormat df2 = new DecimalFormat(".#####");
                lblLat.setText(String.valueOf(df2.format(LATITUDE)) + "° N");
                lblLng.setText(String.valueOf(df2.format(LONGITUDE)) + "° E");

                String street;
                if (thoroughfare == null) {
                    if (featureName == null)
                        street = subLocality;
                    else
                        street = featureName + ", " + subLocality + ", " + city;
                } else {
                    if (featureName.equals(thoroughfare))
                        street = thoroughfare + ", " + subLocality + ", " + city;
                    else
                        street = featureName + ", " + thoroughfare + ", " + subLocality + ", " + city;
                }

                mAddress.setAddress(address);
                mAddress.setCity(city);
                mAddress.setUnit(state);
                mAddress.setState(state);
                mAddress.setStreet(street);
                mAddress.setPostalCode(postalCode);
                mAddress.setCountryCode(countryCode);
                mAddress.setLat(Double.valueOf(df2.format(LATITUDE)));
                mAddress.setLon(Double.valueOf(df2.format(LONGITUDE)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    /**
     * Creating google api client object
     **/
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

        mGoogleApiClient.connect();

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult locationSettingsResult) {

                final Status status = locationSettingsResult.getStatus();

                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location requests here
                        //getLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(JGGMapViewActivity.this, REQUEST_CHECK_SETTINGS);

                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });
    }

    /**
     * Method to verify google play services on the device
     * */
    private boolean checkPlayServices() {

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();

        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(this,resultCode,
                        PLAY_SERVICES_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    /**
     * Google api callback methods
     */
    @Override
    public void onConnected(Bundle arg0) {
        // Once connected with google api, get the location
        getLocation();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Toast.makeText(this, "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        mGoogleMap = googleMap;

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);
                mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng position) {
                        mGoogleMap.clear();
                        markerOpt = new MarkerOptions();
                        markerOpt.position(position)
                                .icon(BitmapDescriptorFactory.fromResource(markerResource));
                        mGoogleMap.addMarker(markerOpt);
                        JGGMapViewActivity.this.getAddress(JGGMapViewActivity.this, position.latitude, position.longitude);
                    }
                });
            } else {
                //Request Location Permission
                permissionUtils.check_permission(permissions,"Need GPS permission for getting your location",1);
            }
        }
        else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng position) {
                    mGoogleMap.clear();
                    markerOpt = new MarkerOptions();
                    markerOpt.position(position)
                            .icon(BitmapDescriptorFactory.fromResource(markerResource));
                    mGoogleMap.addMarker(markerOpt);
                    JGGMapViewActivity.this.getAddress(JGGMapViewActivity.this, position.latitude, position.longitude);
                }
            });
        }
    }

    /**
     * Permission check functions
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // redirects to utils
        permissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //getLocation();
    }

    @Override
    public void PermissionGranted(int request_code) {
        Log.i("PERMISSION","GRANTED");
        isPermissionGranted = true;
    }

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {
        Log.i("PERMISSION PARTIALLY","GRANTED");
    }

    @Override
    public void PermissionDenied(int request_code) {
        Log.i("PERMISSION","DENIED");
    }

    @Override
    public void NeverAskAgain(int request_code) {
        Log.i("PERMISSION","NEVER ASK AGAIN");
    }
}
