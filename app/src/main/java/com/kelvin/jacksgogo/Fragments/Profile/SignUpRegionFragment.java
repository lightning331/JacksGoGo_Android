package com.kelvin.jacksgogo.Fragments.Profile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kelvin.jacksgogo.Activities.Profile.SignUpEmailActivity;
import com.kelvin.jacksgogo.Activities.SplashActivity;
import com.kelvin.jacksgogo.Adapter.Profile.RegionAdapter;
import com.kelvin.jacksgogo.R;

public class SignUpRegionFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;

    private Context mContext;
    private RecyclerView recyclerView;
    private LinearLayout btnSignIn;

    public SignUpRegionFragment() {
        // Required empty public constructor
    }

    public static SignUpRegionFragment newInstance() {
        SignUpRegionFragment fragment = new SignUpRegionFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up_region, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.region_recycler_view);
        btnSignIn = (LinearLayout) view.findViewById(R.id.btn_sign_in);
        btnSignIn.setOnClickListener(this);

        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        }
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        RegionAdapter mAdapter = new RegionAdapter(mContext);
        mAdapter.setOnItemClickListener(new RegionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick() {
                Intent intent = new Intent(mContext, SignUpEmailActivity.class);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mAdapter);

        return view;
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
        FragmentManager manager = getActivity().getSupportFragmentManager();
        if (view.getId() == (R.id.btn_sign_in)) {
            manager.popBackStack();
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
