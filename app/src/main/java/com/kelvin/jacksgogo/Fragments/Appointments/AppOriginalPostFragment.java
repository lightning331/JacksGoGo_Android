package com.kelvin.jacksgogo.Fragments.Appointments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kelvin.jacksgogo.Adapter.AppClientServiceDetailAdapter;
import com.kelvin.jacksgogo.Adapter.AppOriginalPostDetailAdapter;
import com.kelvin.jacksgogo.R;


public class AppOriginalPostFragment extends Fragment {

    RecyclerView recyclerView;

    private OnFragmentInteractionListener mListener;

    public AppOriginalPostFragment() {
        // Required empty public constructor
    }

    public static AppOriginalPostFragment newInstance(String param1, String param2) {
        AppOriginalPostFragment fragment = new AppOriginalPostFragment();
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
        View view = inflater.inflate(R.layout.original_post_detail_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.original_post_recycler_view);

        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        }
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        AppOriginalPostDetailAdapter mAdapter = new AppOriginalPostDetailAdapter(this);

        recyclerView.setAdapter(mAdapter);

        return view;
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
