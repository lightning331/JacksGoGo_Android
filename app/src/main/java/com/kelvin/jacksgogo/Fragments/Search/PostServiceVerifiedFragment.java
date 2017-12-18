package com.kelvin.jacksgogo.Fragments.Search;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.Profile.VerifyNewSkillsActivity;
import com.kelvin.jacksgogo.CustomView.Views.EditJobTabbarView;
import com.kelvin.jacksgogo.R;

public class PostServiceVerifiedFragment extends Fragment implements View.OnClickListener {

    private Context mContext;

    public static PostServiceVerifiedFragment newInstance(String param1, String param2) {
        PostServiceVerifiedFragment fragment = new PostServiceVerifiedFragment();

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
        View view = inflater.inflate(R.layout.fragment_post_service_verified, container, false);

        TextView btnVerify = view.findViewById(R.id.btn_verify_new_skills);
        btnVerify.setOnClickListener(this);
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

        return view;
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_verify_new_skills) {
            Intent intent = new Intent(mContext, VerifyNewSkillsActivity.class);
            intent.putExtra("already_verified_skills", true);
            mContext.startActivity(intent);
        } else {
            PostServiceDetailFragment editJobMainFragment = PostServiceDetailFragment.newInstance(EditJobTabbarView.EditTabStatus.DESCRIBE);
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.post_service_container, editJobMainFragment, editJobMainFragment.getTag());
            ft.addToBackStack("post_service");
            ft.commit();
        }
    }

    private OnFragmentInteractionListener mListener;

    public PostServiceVerifiedFragment() {
        // Required empty public constructor
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
