package com.kelvin.jacksgogo.Fragments.Jobs;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kelvin.jacksgogo.Activities.Jobs.JobStatusSummaryActivity;
import com.kelvin.jacksgogo.Adapter.Jobs.NewJobDetailsAdapter;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.R;


public class NewJobDetailsFragment extends Fragment {

    private Context mContext;
    private RecyclerView recyclerView;

    public NewJobDetailsFragment() {
        // Required empty public constructor
    }

    public static NewJobDetailsFragment newInstance(String param1, String param2) {
        NewJobDetailsFragment fragment = new NewJobDetailsFragment();
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
        View view = inflater.inflate(R.layout.fragment_new_job_details, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.detail_recycler_view);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        }
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        NewJobDetailsAdapter mAdapter = new NewJobDetailsAdapter(mContext);

        recyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        mContext = context;
        super.onAttach(context);
        //((JobStatusSummaryActivity) context).setStatus(JGGActionbarView.EditStatus.NONE);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
