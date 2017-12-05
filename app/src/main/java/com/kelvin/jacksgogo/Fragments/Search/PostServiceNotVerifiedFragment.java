package com.kelvin.jacksgogo.Fragments.Search;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kelvin.jacksgogo.Activities.Profile.VerifyNewSkillsActivity;
import com.kelvin.jacksgogo.R;

public class PostServiceNotVerifiedFragment extends Fragment implements View.OnClickListener {

    private Context mContext;

    public static PostServiceNotVerifiedFragment newInstance(String param1, String param2) {
        PostServiceNotVerifiedFragment fragment = new PostServiceNotVerifiedFragment();
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
        View view = inflater.inflate(R.layout.fragment_post_service_not_verified, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {

        LinearLayout btnOther = view.findViewById(R.id.btn_post_other);
        btnOther.setOnClickListener(this);
        LinearLayout btnCooking = view.findViewById(R.id.btn_post_cooking);
        btnCooking.setOnClickListener(this);
        LinearLayout btnEducation = view.findViewById(R.id.btn_post_education);
        btnEducation.setOnClickListener(this);
        LinearLayout btnHand = view.findViewById(R.id.btn_post_handyman);
        btnHand.setOnClickListener(this);
        LinearLayout btnHouse = view.findViewById(R.id.btn_post_household);
        btnHouse.setOnClickListener(this);
        LinearLayout btnMessenger = view.findViewById(R.id.btn_post_messenger);
        btnMessenger.setOnClickListener(this);
        LinearLayout btnRun = view.findViewById(R.id.btn_post_run);
        btnRun.setOnClickListener(this);
        LinearLayout btnSport = view.findViewById(R.id.btn_post_sports);
        btnSport.setOnClickListener(this);

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

    private OnFragmentInteractionListener mListener;

    public PostServiceNotVerifiedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(mContext, VerifyNewSkillsActivity.class);
        intent.putExtra("already_verified_skills", false);
        mContext.startActivity(intent);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
