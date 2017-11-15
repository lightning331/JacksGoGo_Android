package com.kelvin.jacksgogo.Fragments.Appointments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kelvin.jacksgogo.Activities.Appointment.JobDetailActivity;
import com.kelvin.jacksgogo.Adapter.EditJobAddressAdapter;
import com.kelvin.jacksgogo.Adapter.EditJobDescribeAdapter;
import com.kelvin.jacksgogo.Adapter.EditJobReportAdapter;
import com.kelvin.jacksgogo.Adapter.EditJobTimeAdapter;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail.EditJobTabbarView;
import com.kelvin.jacksgogo.R;

public class EditJobFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public void setmListener(OnFragmentInteractionListener mListener) {
        this.mListener = mListener;
    }

    Context mContext;

    private String status;
    RecyclerView recyclerView;
    EditJobTabbarView tabbarView;


    public EditJobFragment() {
        // Required empty public constructor
    }

    public static EditJobFragment newInstance(EditJobTabbarView.EditTabStatus status) {
        EditJobFragment fragment = new EditJobFragment();
        Bundle args = new Bundle();
        args.putString("status", status.toString());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            status = getArguments().getString("status");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.edit_job_fragment, container, false);

        LinearLayout tabbarLayout = (LinearLayout)view.findViewById(R.id.edit_job_tabbar_view);
        tabbarView = new EditJobTabbarView(getContext());
        tabbarLayout.addView(tabbarView);

        recyclerView = (RecyclerView) view.findViewById(R.id.descibe_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        initTabbarView();

        tabbarView.setTabItemClickLietener(new EditJobTabbarView.OnTabItemClickListener() {
            @Override
            public void onTabItemClick(View view) {
                if (view.getId() == R.id.btn_describe) {
                    tabbarView.setEditTabStatus(EditJobTabbarView.EditTabStatus.DESCRIBE);
                } else if (view.getId() == R.id.btn_time) {
                    tabbarView.setEditTabStatus(EditJobTabbarView.EditTabStatus.TIME);
                } else if (view.getId() == R.id.btn_address) {
                    tabbarView.setEditTabStatus(EditJobTabbarView.EditTabStatus.ADDRESS);
                } else if (view.getId() == R.id.btn_report) {
                    tabbarView.setEditTabStatus(EditJobTabbarView.EditTabStatus.REPORT);
                }
                refreshRecyclerView();
            }
        });

        return view;
    }

    public void initTabbarView() {

        if (status == "DESCRIBE") {
            tabbarView.setEditTabStatus(EditJobTabbarView.EditTabStatus.DESCRIBE);
        } else if (status == "TIME") {
            tabbarView.setEditTabStatus(EditJobTabbarView.EditTabStatus.TIME);
        } else if (status == "ADDRESS") {
            tabbarView.setEditTabStatus(EditJobTabbarView.EditTabStatus.ADDRESS);
        } else if (status == "REPORT") {
            tabbarView.setEditTabStatus(EditJobTabbarView.EditTabStatus.REPORT);
        }
        refreshRecyclerView();
    }

    public void refreshRecyclerView() {
        if (tabbarView.getEditTabStatus() == EditJobTabbarView.EditTabStatus.DESCRIBE) {
            EditJobDescribeAdapter mAdapter = new EditJobDescribeAdapter(this);
            recyclerView.setAdapter(mAdapter);
        } else if (tabbarView.getEditTabStatus() == EditJobTabbarView.EditTabStatus.TIME) {
            EditJobTimeAdapter mTimeAdapter = new EditJobTimeAdapter(this);
            recyclerView.setAdapter(mTimeAdapter);
        } else if (tabbarView.getEditTabStatus() == EditJobTabbarView.EditTabStatus.ADDRESS) {
            EditJobAddressAdapter addressAdapter = new EditJobAddressAdapter(this);
            recyclerView.setAdapter(addressAdapter);
        } else if (tabbarView.getEditTabStatus() == EditJobTabbarView.EditTabStatus.REPORT) {
            EditJobReportAdapter reportAdapter = new EditJobReportAdapter(this);
            recyclerView.setAdapter(reportAdapter);
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.refreshDrawableState();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;

        ((JobDetailActivity)context).setStatus();

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
