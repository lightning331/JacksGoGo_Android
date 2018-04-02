package com.kelvin.jacksgogo.Fragments.Jobs;

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

import com.kelvin.jacksgogo.Activities.Jobs.JobReportActivity;
import com.kelvin.jacksgogo.Adapter.Jobs.AppointmentReportAdapter;
import com.kelvin.jacksgogo.CustomView.Views.RecyclerItemClickListener;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;

import java.util.ArrayList;
import java.util.List;

import static com.kelvin.jacksgogo.Utils.JGGAppManager.selectedAppointment;
import static com.kelvin.jacksgogo.Utils.Global.JOBS;
import static com.kelvin.jacksgogo.Utils.Global.getReportTypeID;

public class JobReportAddToolsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Context mContext;
    private JobReportActivity mActivity;

    private RecyclerView recyclerView;

    private AppointmentReportAdapter reportAdapter;
    private JGGAppointmentModel mAppointment = new JGGAppointmentModel();
    private List<Integer> selectedIds = new ArrayList<>();
    private boolean isMultiSelect = false;

    public JobReportAddToolsFragment() {
        // Required empty public constructor
    }

    public static JobReportAddToolsFragment newInstance(String param1, String param2) {
        JobReportAddToolsFragment fragment = new JobReportAddToolsFragment();
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
        View view = inflater.inflate(R.layout.fragment_job_report_add_tools, container, false);

        recyclerView = view.findViewById(R.id.job_report_tools_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        initRecyclerView();

        return view;
    }

    private void initRecyclerView() {
        mAppointment = selectedAppointment;
        selectedIds = getReportTypeID(mAppointment.getReportType());

        reportAdapter = new AppointmentReportAdapter(mContext, true, JOBS);
        reportAdapter.setSelectedIds(selectedIds);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(mContext, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                if (position == 1) {

                } else {

                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
        recyclerView.setAdapter(reportAdapter);
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
        mActivity = ((JobReportActivity) context);
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
