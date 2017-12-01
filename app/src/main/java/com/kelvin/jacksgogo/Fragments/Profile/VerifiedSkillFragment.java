package com.kelvin.jacksgogo.Fragments.Profile;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kelvin.jacksgogo.Fragments.Search.ActiveServiceMapFragment;
import com.kelvin.jacksgogo.R;

public class VerifiedSkillFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;

    public VerifiedSkillFragment() {
        // Required empty public constructor
    }

    public static VerifiedSkillFragment newInstance(String param1, String param2) {
        VerifiedSkillFragment fragment = new VerifiedSkillFragment();

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
        View view = inflater.inflate(R.layout.profile_verified_skill_fragment, container, false);
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

        NotVerifiedSkillFragment frag = new NotVerifiedSkillFragment();

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.verify_skill_container, frag, frag.getTag());
        ft.addToBackStack("verify_skill");
        ft.commit();

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
