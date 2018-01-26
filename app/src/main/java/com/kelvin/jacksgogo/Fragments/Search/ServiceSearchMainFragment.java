package com.kelvin.jacksgogo.Fragments.Search;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.Search.SearchResultActivity;
import com.kelvin.jacksgogo.R;

import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.GOCLUB;
import static com.kelvin.jacksgogo.Utils.Global.JOBS;
import static com.kelvin.jacksgogo.Utils.Global.SERVICES;

public class ServiceSearchMainFragment extends Fragment implements View.OnClickListener {

    private Context mContext;
    private OnFragmentInteractionListener mListener;

    private LinearLayout searchLayout;
    private EditText txtSearch;
    private ImageView btnSearch;
    private TextView btnAdvanced;
    private LinearLayout recyclerViewLayout;
    private RecyclerView recyclerView;
    private TextView btnJobs;
    private TextView btnGoClub;
    private TextView btnEvent;
    private TextView btnUser;

    private String appType;

    public ServiceSearchMainFragment() {
        // Required empty public constructor
    }

    public static ServiceSearchMainFragment newInstance(String type) {
        ServiceSearchMainFragment fragment = new ServiceSearchMainFragment();
        Bundle args = new Bundle();
        args.putString(APPOINTMENT_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            appType = getArguments().getString(APPOINTMENT_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_service_search_main, container, false);

        initView(view);

        if (appType.equals(SERVICES)) {
            initViewColor(getResources().getColor(R.color.JGGGreen));
        } else if (appType.equals(JOBS)) {
            initViewColor(getResources().getColor(R.color.JGGCyan));
        } else if (appType.equals(GOCLUB)) {
            initViewColor(getResources().getColor(R.color.JGGPurple));
        }

        return view;
    }

    private void initView(View view) {
        searchLayout = view.findViewById(R.id.search_layout);
        txtSearch = view.findViewById(R.id.txt_search);
        btnSearch = view.findViewById(R.id.btn_search);
        btnAdvanced = view.findViewById(R.id.btn_search_advanced);
        recyclerViewLayout = view.findViewById(R.id.search_recycler_view_layout);
        recyclerView = view.findViewById(R.id.search_recycler_view);
        btnJobs = view.findViewById(R.id.btn_search_jobs);
        btnGoClub = view.findViewById(R.id.btn_search_go_club);
        btnEvent = view.findViewById(R.id.btn_search_event);
        btnUser = view.findViewById(R.id.btn_search_user);

        btnSearch.setOnClickListener(this);
        btnAdvanced.setOnClickListener(this);
        btnJobs.setOnClickListener(this);
        btnGoClub.setOnClickListener(this);
        btnEvent.setOnClickListener(this);
        btnUser.setOnClickListener(this);
    }

    private void initViewColor(int color) {

        if (appType.equals(SERVICES)) {
            searchLayout.setBackgroundResource(R.drawable.green_border_background);
            btnAdvanced.setBackgroundResource(R.drawable.green_border_background);
            txtSearch.setHint("Search Services");
            btnJobs.setText("Jobs");
            btnJobs.setBackgroundResource(R.drawable.green_border_background);
            btnGoClub.setBackgroundResource(R.drawable.green_border_background);
            btnEvent.setBackgroundResource(R.drawable.green_border_background);
            btnUser.setBackgroundResource(R.drawable.green_border_background);
            btnSearch.setImageResource(R.mipmap.button_search_round_green);
        } else if (appType.equals(JOBS)) {
            searchLayout.setBackgroundResource(R.drawable.cyan_border_background);
            btnAdvanced.setBackgroundResource(R.drawable.cyan_border_background);
            txtSearch.setHint("Search Jobs");
            btnJobs.setText("Services");
            btnJobs.setBackgroundResource(R.drawable.cyan_border_background);
            btnGoClub.setBackgroundResource(R.drawable.cyan_border_background);
            btnEvent.setBackgroundResource(R.drawable.cyan_border_background);
            btnUser.setBackgroundResource(R.drawable.cyan_border_background);
            btnSearch.setImageResource(R.mipmap.button_search_round_cyan);
        } else if (appType.equals(GOCLUB)) {
            searchLayout.setBackgroundResource(R.drawable.purple_border_background);
            btnAdvanced.setBackgroundResource(R.drawable.purple_border_background);
            txtSearch.setHint("Search GoClub");
            btnGoClub.setText("Services");
            btnJobs.setBackgroundResource(R.drawable.purple_border_background);
            btnGoClub.setBackgroundResource(R.drawable.purple_border_background);
            btnEvent.setBackgroundResource(R.drawable.purple_border_background);
            btnUser.setBackgroundResource(R.drawable.purple_border_background);
            btnSearch.setImageResource(R.mipmap.button_search_round_purple);
        }
        btnAdvanced.setTextColor(color);
        btnJobs.setTextColor(color);
        btnGoClub.setTextColor(color);
        btnEvent.setTextColor(color);
        btnUser.setTextColor(color);
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_search_advanced) {
            ServiceSearchAdvanceFragment frag = ServiceSearchAdvanceFragment.newInstance(appType);
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.service_search_container, frag, frag.getTag());
            ft.addToBackStack("advance_search");
            ft.commit();
        } else if (view.getId() == R.id.btn_search) {
            Intent intent = new Intent(mContext, SearchResultActivity.class);
            intent.putExtra(APPOINTMENT_TYPE, appType);
            startActivity(intent);
        } else if (view.getId() == R.id.btn_search_jobs) {

        } else if (view.getId() == R.id.btn_search_go_club) {

        } else if (view.getId() == R.id.btn_search_event) {

        } else if (view.getId() == R.id.btn_search_user) {

        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
