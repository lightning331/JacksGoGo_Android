package com.kelvin.jacksgogo.Fragments.Search;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.Search.ActiveServiceActivity;
import com.kelvin.jacksgogo.R;

public class ActiveServiceMapFragment extends Fragment implements View.OnClickListener {


    private OnAcitiveServiceFragmentInteractionListener mListener;

    TextView lblServiceCount;
    LinearLayout btnMapFilter;
    LinearLayout btnUserLocation;
    LinearLayout btnListView;
    Context mContext;

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

        initTabView(view);
        initMapView();

        return view;
    }

    private void initTabView(View view) {

        lblServiceCount = view.findViewById(R.id.lbl_service_count);
        btnMapFilter = view.findViewById(R.id.btn_map_filter);
        btnUserLocation = view.findViewById(R.id.btn_user_location);
        btnListView = view.findViewById(R.id.btn_list_view);

        btnMapFilter.setOnClickListener(this);
        btnUserLocation.setOnClickListener(this);
        btnListView.setOnClickListener(this);
    }

    private void initMapView() {

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

    public interface OnAcitiveServiceFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
