package com.kelvin.jacksgogo.Fragments.Search;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.gcm.Task;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.kelvin.jacksgogo.Activities.Search.ActiveServiceActivity;
import com.kelvin.jacksgogo.R;

import static android.content.ContentValues.TAG;

public class ActiveServiceMapFragment extends Fragment
        implements View.OnClickListener,
        OnMapReadyCallback {

    Context mContext;
    private OnAcitiveServiceFragmentInteractionListener mListener;

    GoogleMap mMap;
    boolean mLocationPermissionGranted = false;
    FusedLocationProviderClient mFusedLocationProviderClient;
    Location mLastKnownLocation;

    SeekBar seekBar;
    TextView lblServiceCount;
    TextView lblSearchRadius;
    LinearLayout btnMapFilter;
    LinearLayout btnUserLocation;
    LinearLayout btnListView;

    public void setOnAcitiveServiceFragmentInteractionListener(
            OnAcitiveServiceFragmentInteractionListener listener) {
        mListener = listener;
    }

    public ActiveServiceMapFragment() {
        // Required empty public constructor
    }

    public static ActiveServiceMapFragment newInstance(String param1, String param2) {
        ActiveServiceMapFragment fragment = new ActiveServiceMapFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.search_active_service_map_fragment, container, false);

        initView(view);
        initMapView();

        return view;
    }

    private void initView(View view) {

        seekBar = view.findViewById(R.id.search_radius_seekbar);
        lblSearchRadius = view.findViewById(R.id.lbl_kilometer);
        lblServiceCount = view.findViewById(R.id.lbl_service_count);
        btnMapFilter = view.findViewById(R.id.btn_map_filter);
        btnUserLocation = view.findViewById(R.id.btn_user_location);
        btnListView = view.findViewById(R.id.btn_list_view);

        btnMapFilter.setOnClickListener(this);
        btnUserLocation.setOnClickListener(this);
        btnListView.setOnClickListener(this);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        ((ActiveServiceActivity)context).setBottomViewHidden(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_map_filter) {

        } else if (view.getId() == R.id.btn_user_location) {

        } else if (view.getId() == R.id.btn_list_view) {
            backToMainFragment();
        }
    }

    private void backToMainFragment() {
        ActiveServiceMainFragment frag = new ActiveServiceMainFragment();

        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.active_service_container, frag, frag.getTag());
        ft.remove(this);
        ft.commit();
        ((ActiveServiceActivity)mContext).setBottomViewHidden(false);

        manager.popBackStack();
    }

    private void initMapView() {

        //To get MapFragment reference from xml layout
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.active_service_map_view);
        mapFragment.getMapAsync(this);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int radiusChangeValue = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                radiusChangeValue = i;
                if (radiusChangeValue < 1000) {
                    lblSearchRadius.setText(radiusChangeValue + " m");
                } else {
                    float distance = radiusChangeValue / 1000;
                    lblSearchRadius.setText(String.format("%.1f", distance) + " km");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        mMap = googleMap;

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getDeviceLocation() {
    /*
     * Get the best and most recent location of the device, which may be null in rare
     * cases when a location is not available.
     */
        try {
            if (mLocationPermissionGranted) {
                com.google.android.gms.tasks.Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = (Location) task.getResult();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                        } else {
                            Log.d(TAG,"Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            LatLng mDefaultLocation = new LatLng(1.294595, 103.852089);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch(SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getLocationPermission() {
    /*
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    public interface OnAcitiveServiceFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
