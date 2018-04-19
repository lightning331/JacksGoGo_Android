package com.kelvin.jacksgogo.Fragments.Home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.MainActivity;
import com.kelvin.jacksgogo.Activities.Search.PostServiceActivity;
import com.kelvin.jacksgogo.Activities.Search.ServiceListingActivity;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Home.HomeMainDetails;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Home.HomeMainExplore;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Home.HomeMainUserInfo;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Home.HomePostAppointment;
import com.kelvin.jacksgogo.R;
import com.squareup.picasso.Picasso;

import static com.kelvin.jacksgogo.Utils.JGGAppManager.currentUser;
import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.EDIT_STATUS;
import static com.kelvin.jacksgogo.Utils.Global.JOBS;
import static com.kelvin.jacksgogo.Utils.Global.POST;
import static com.kelvin.jacksgogo.Utils.Global.SERVICES;

public class HomeFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Context mContext;
    private MainActivity mActivity;

    private TextView lblTitle;
    private LinearLayout lblQuickJobTitle;
    private LinearLayout signUpTitle;
    private LinearLayout btnQuickJob;
    private TextView btnSignUp;

    private LinearLayout userInfoLayout;
    private HomeMainUserInfo userInfoView;
    private LinearLayout detailsLayout;
    private HomeMainDetails detailsView;
    private LinearLayout exploreLayout;
    private HomeMainExplore exploreView;
    private LinearLayout postAppLayout;
    private HomePostAppointment postView;

    private Intent intent;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint()) {
            return;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        updateView();
        return view;
    }

    private void initView(View view) {
        lblTitle = view.findViewById(R.id.lbl_title);
        lblQuickJobTitle = view.findViewById(R.id.quick_job_title);
        signUpTitle = view.findViewById(R.id.sign_up_title);
        btnQuickJob = view.findViewById(R.id.btn_quick_job);
        btnSignUp = view.findViewById(R.id.btn_sign_up);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)mContext).setProfilePage();
            }
        });

        userInfoLayout = view.findViewById(R.id.user_info_layout);
        detailsLayout = view.findViewById(R.id.details_layout);
        exploreLayout = view.findViewById(R.id.explore_layout);
        postAppLayout = view.findViewById(R.id.post_layout);

        userInfoView = new HomeMainUserInfo(mContext);
        detailsView = new HomeMainDetails(mContext);
        exploreView = new HomeMainExplore(mContext);
        postView = new HomePostAppointment(mContext);
    }

    private void updateView() {
        resetView();

        if (currentUser == null) {
            userInfoLayout.setVisibility(View.GONE);
            detailsLayout.setVisibility(View.GONE);
            exploreLayout.addView(exploreView);
            postAppLayout.addView(postView);
        } else {
            lblTitle.setVisibility(View.GONE);
            // User Info
            Picasso.with(mContext)
                    .load(currentUser.getUser().getPhotoURL())
                    .placeholder(R.mipmap.icon_profile)
                    .into(userInfoView.imgAvatar);
            userInfoView.lblUserName.setText(currentUser.getUser().getFullName() + "!");
            userInfoLayout.addView(userInfoView);

            // Details view
            detailsView.btnVerified.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(mContext, ServiceListingActivity.class));
                }
            });
            detailsLayout.addView(detailsView);

            // Explore view
            exploreView.titleLayout.setVisibility(View.GONE);
            exploreLayout.addView(exploreView);

            // Post view
            postView.titleLayout.setVisibility(View.GONE);
            postView.setOnItemClickListener(new HomePostAppointment.OnItemClickListener() {
                @Override
                public void onItemClick(View item) {
                    onPostAppointment(item);
                }
            });
            postAppLayout.addView(postView);

            // Quick Job view
            lblQuickJobTitle.setVisibility(View.GONE);
            signUpTitle.setVisibility(View.GONE);
            btnSignUp.setVisibility(View.GONE);
        }
    }

    private void onPostAppointment(View view) {
        intent = new Intent(mContext.getApplicationContext(), PostServiceActivity.class);
        if (view.getId() == R.id.btn_post_service) {
            intent.putExtra(APPOINTMENT_TYPE, SERVICES);
        } else if (view.getId() == R.id.btn_post_job) {
            intent.putExtra(APPOINTMENT_TYPE, JOBS);
        } else if (view.getId() == R.id.btn_post_event) {

        } else if (view.getId() == R.id.btn_show_service) {

        } else if (view.getId() == R.id.btn_show_jobs) {

        } else if (view.getId() == R.id.btn_show_events) {

        } else
            return;
        intent.putExtra(EDIT_STATUS, POST);
        mContext.startActivity(intent);
    }

    private void resetView() {
        userInfoLayout.removeAllViews();
        detailsLayout.removeAllViews();
        exploreLayout.removeAllViews();
        postAppLayout.removeAllViews();
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
        mActivity = ((MainActivity) context);
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
