package com.kelvin.jacksgogo.Fragments.Search;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kelvin.jacksgogo.Activities.Search.ActiveServiceActivity;
import com.kelvin.jacksgogo.Activities.Search.PostServiceActivity;
import com.kelvin.jacksgogo.Activities.Search.ServiceListingActivity;
import com.kelvin.jacksgogo.Adapter.Services.SearchJobsAdapter;
import com.kelvin.jacksgogo.Adapter.Services.SearchServicesAdapter;
import com.kelvin.jacksgogo.R;

public class SearchFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    private RecyclerView recyclerView;
    private String appType = "SERVICES";
    private Intent mIntent;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = view.findViewById(R.id.search_main_recycler_view);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        }
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        refreshFragment("SERVICES");

        return view;
    }

    public void refreshFragment(String textView) {

        appType = textView;

        if (textView == "SERVICES") {
            SearchServicesAdapter adapter = new SearchServicesAdapter(mContext);
            adapter.setOnItemClickLietener(new SearchServicesAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view) {
                    onHeaderViewItemClick(view);
                }
            });
            recyclerView.setAdapter(adapter);
        } else if (textView == "JOBS") {
            SearchJobsAdapter adapter = new SearchJobsAdapter(mContext);
            adapter.setOnItemClickLietener(new SearchJobsAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view) {
                    onHeaderViewItemClick(view);
                }
            });
            recyclerView.setAdapter(adapter);
        } else if (textView == "GOCLUB") {

        }
    }

    private void onHeaderViewItemClick(View view) {

        if (view.getId() == R.id.btn_view_my_service) {
            mIntent = new Intent(mContext.getApplicationContext(), ServiceListingActivity.class);
        } else if (view.getId() == R.id.btn_view_all) {
            mIntent = new Intent(mContext.getApplicationContext(), ActiveServiceActivity.class);
        } else if (view.getId() == R.id.btn_post_new) {
            mIntent = new Intent(mContext.getApplicationContext(), PostServiceActivity.class);
        }
        mIntent.putExtra("APPOINTMENT_TYPE", appType);
        mIntent.putExtra("EDIT_STATUS", "None");
        view.getContext().startActivity(mIntent);
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
