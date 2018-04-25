package com.kelvin.jacksgogo.Fragments.Search;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kelvin.jacksgogo.Activities.Jobs.JobDetailActivity;
import com.kelvin.jacksgogo.Activities.Search.ActiveServiceActivity;
import com.kelvin.jacksgogo.Activities.Search.ServiceDetailActivity;
import com.kelvin.jacksgogo.Activities.Search.ServiceFilterActivity;
import com.kelvin.jacksgogo.CustomView.JGGJobInfoWindow;
import com.kelvin.jacksgogo.CustomView.JGGServiceInfoWindow;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;

import java.util.ArrayList;

import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.EVENTS;
import static com.kelvin.jacksgogo.Utils.Global.JOBS;
import static com.kelvin.jacksgogo.Utils.Global.SERVICES;

public class ActiveServiceMapFragment extends Fragment implements
        View.OnClickListener,
        OnMapReadyCallback,
        GoogleMap.OnInfoWindowClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private Context mContext;
    private OnFragmentInteractionListener mListener;

    private GoogleMap mGoogleMap;
    private SupportMapFragment mapFrag;
    private GoogleApiClient mGoogleApiClient;
    private JGGServiceInfoWindow serviceinfoWindow;
    private JGGJobInfoWindow jobInfoWindow;

    private SeekBar seekBar;
    private TextView lblServiceCount;
    private TextView lblSearchRadius;
    private LinearLayout btnMapFilter;
    private LinearLayout btnUserLocation;
    private LinearLayout btnListView;
    private ImageView imgFilter;
    private ImageView imgUserLocation;
    private ImageView imgListView;

    private ArrayList<JGGAppointmentModel> mAppointments = new ArrayList<>();
    private String appType;
    private int mColor;

    public void setOnFragmentInteractionListener(OnFragmentInteractionListener listener) {
        mListener = listener;
    }

    public ActiveServiceMapFragment() {
        // Required empty public constructor
    }

    public static ActiveServiceMapFragment newInstance(String type) {
        ActiveServiceMapFragment fragment = new ActiveServiceMapFragment();
        Bundle args = new Bundle();
        args.putString(APPOINTMENT_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    public void setAppointment(ArrayList<JGGAppointmentModel> appointments) {
        mAppointments = appointments;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            appType = getArguments().getString(APPOINTMENT_TYPE);
        } else {
            appType = SERVICES;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_active_service_map, container, false);

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
        imgFilter = view.findViewById(R.id.img_map_filter);
        imgUserLocation = view.findViewById(R.id.img_user_location);
        imgListView = view.findViewById(R.id.img_list_view);

        switch (appType) {
            case SERVICES:
                mColor = ContextCompat.getColor(mContext, R.color.JGGGreen);
                imgListView.setImageResource(R.mipmap.button_listview_green);
                imgFilter.setImageResource(R.mipmap.button_filter_green);
                imgUserLocation.setImageResource(R.mipmap.button_location_green);
                lblServiceCount.setText(mAppointments.size() + " Services");
                break;
            case JOBS:
                mColor = ContextCompat.getColor(mContext, R.color.JGGCyan);
                imgListView.setImageResource(R.mipmap.button_listview_cyan);
                imgFilter.setImageResource(R.mipmap.button_filter_cyan);
                imgUserLocation.setImageResource(R.mipmap.button_location_cyan);
                lblServiceCount.setText(mAppointments.size() + " Jobs");
                break;
            case EVENTS:
                mColor = ContextCompat.getColor(mContext, R.color.JGGPurple);
                imgListView.setImageResource(R.mipmap.button_listview_purple);
                imgFilter.setImageResource(R.mipmap.button_filter_purple);
                imgUserLocation.setImageResource(R.mipmap.button_location_purple);
                lblServiceCount.setText(mAppointments.size() + " Events");
                break;
        }
        seekBar.getProgressDrawable().setColorFilter(mColor, PorterDuff.Mode.SRC_ATOP);

        btnMapFilter.setOnClickListener(this);
        btnUserLocation.setOnClickListener(this);
        btnListView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_map_filter) {
            Intent intent = new Intent(getActivity(), ServiceFilterActivity.class);
            intent.putExtra(APPOINTMENT_TYPE, appType);
            startActivity(intent);
        } else if (view.getId() == R.id.btn_user_location) {

        } else if (view.getId() == R.id.btn_list_view) {
            backToMainFragment();
        }
    }

    private void backToMainFragment() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.active_service_container, new ActiveServiceMainFragment())
                .remove(this)
                .commit();
        if (mContext instanceof ActiveServiceActivity) {
            ((ActiveServiceActivity) mContext).setBottomViewHidden(false);
        }

        manager.popBackStack();
    }

    private void initMapView() {

        //To get MapFragment reference from xml layout
        mapFrag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.active_service_map_view);
        mapFrag.getMapAsync(this);

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

    private void addAppointmentMarker() {
        if (appType.equals(SERVICES)) {
            serviceinfoWindow = new JGGServiceInfoWindow(mContext, mAppointments);
            mGoogleMap.setInfoWindowAdapter(serviceinfoWindow);
        } else if (appType.equals(JOBS)) {
            jobInfoWindow = new JGGJobInfoWindow(mContext, mAppointments);
            mGoogleMap.setInfoWindowAdapter(jobInfoWindow);
        }

        MarkerOptions markerOpt = new MarkerOptions();
        for (int i = 0 ; i < mAppointments.size() ; i++ ) {
            JGGAppointmentModel app = mAppointments.get(i);
            LatLng marker = new LatLng(app.getAddress().getLat(), app.getAddress().getLon());
            String pinSnippet = String.valueOf(i);
            markerOpt.position(marker)
                    .snippet(pinSnippet)
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_pin));
            mGoogleMap.addMarker(markerOpt);
        }

        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if (marker.getSnippet() != null) {
                    int index = Integer.parseInt(marker.getSnippet());

                    JGGAppManager.getInstance().setSelectedAppointment(mAppointments.get(index));

                    if (appType.equals(SERVICES)) {
                        Intent intent = new Intent(mContext, ServiceDetailActivity.class);
                        mContext.startActivity(intent);
                    } else if (appType.equals(JOBS)) {
                        mContext.startActivity(new Intent(getContext(), JobDetailActivity.class));
                    }
                }
            }
        });}

    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        mGoogleMap = googleMap;

        addAppointmentMarker();

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this.mContext,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        }
        else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this.mContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        try {
            Location mLastLocation = LocationServices.FusedLocationApi
                    .getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                LatLng latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.mContext, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this.mContext)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this.getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this.mContext,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this.mContext, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        mContext.startActivity(new Intent(mContext, ServiceDetailActivity.class));
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof ActiveServiceActivity) {
            ((ActiveServiceActivity) context).setBottomViewHidden(true);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
