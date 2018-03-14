package com.kelvin.jacksgogo.Fragments.Search;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.Jobs.JobStatusSummaryActivity;
import com.kelvin.jacksgogo.Activities.Search.PostQuotationActivity;
import com.kelvin.jacksgogo.Adapter.Jobs.AppointmentReportAdapter;
import com.kelvin.jacksgogo.Adapter.Jobs.PostQuotationAddressAdapter;
import com.kelvin.jacksgogo.Adapter.Jobs.PostQuotationTimeAdapter;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.CustomView.Views.PostServiceTabbarView;
import com.kelvin.jacksgogo.CustomView.Views.RecyclerItemClickListener;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGReportModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGServiceModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostQuotationMainTabFragment extends Fragment implements View.OnClickListener {

    private Context mContext;
    private PostQuotationActivity mActivity;

    private FrameLayout describeContainer;
    private RecyclerView recyclerView;
    private PostServiceTabbarView tabbarView;
    private JGGServiceModel serviceModel = new JGGServiceModel();
    private AlertDialog alertDialog;
    private AppointmentReportAdapter reportAdapter;

    private List<Integer> selectedIds = new ArrayList<>();
    private boolean isMultiSelect = false;
    private JGGReportModel data;
    private int reportType = 0;
    private String status;
    private boolean isRequest; // Request Or Edit

    public PostQuotationMainTabFragment() {
        // Required empty public constructor
    }

    public static PostQuotationMainTabFragment newInstance(PostServiceTabbarView.PostServiceTabName status, boolean b) {
        PostQuotationMainTabFragment fragment = new PostQuotationMainTabFragment();
        Bundle args = new Bundle();
        args.putString("tabName", status.toString());
        args.putBoolean("isRequest", b);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_quotation_main_tab, container, false);

        LinearLayout tabbarLayout = (LinearLayout)view.findViewById(R.id.edit_job_tabbar_view);
        tabbarView = new PostServiceTabbarView(getContext());
        tabbarLayout.addView(tabbarView);

        describeContainer = (FrameLayout) view.findViewById(R.id.edit_job_describe_container);
        recyclerView = (RecyclerView) view.findViewById(R.id.descibe_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        initTabbarView();

        tabbarView.setTabItemClickLietener(new PostServiceTabbarView.OnTabItemClickListener() {
            @Override
            public void onTabItemClick(View view) {
                onTabbarViewClick(view);
            }
        });

        return view;
    }

    private void initTabbarView() {

        if (status == "DESCRIBE") {
            tabbarView.setTabName(PostServiceTabbarView.PostServiceTabName.DESCRIBE, isRequest);
        } else if (status == "TIME") {
            tabbarView.setTabName(PostServiceTabbarView.PostServiceTabName.TIME, isRequest);
        } else if (status == "ADDRESS") {
            tabbarView.setTabName(PostServiceTabbarView.PostServiceTabName.ADDRESS, isRequest);
        } else if (status == "REPORT") {
            tabbarView.setTabName(PostServiceTabbarView.PostServiceTabName.REPORT, isRequest);
        }
        refreshRecyclerView();
    }

    private void onTabbarViewClick(View view) {

        if (view.getId() == R.id.btn_describe) {
            tabbarView.setTabName(PostServiceTabbarView.PostServiceTabName.DESCRIBE, isRequest);
        } else if (view.getId() == R.id.btn_time) {
            tabbarView.setTabName(PostServiceTabbarView.PostServiceTabName.TIME, isRequest);
        } else if (view.getId() == R.id.btn_address) {
            tabbarView.setTabName(PostServiceTabbarView.PostServiceTabName.ADDRESS, isRequest);
        } else if (view.getId() == R.id.btn_report) {
            tabbarView.setTabName(PostServiceTabbarView.PostServiceTabName.REPORT, isRequest);
        }
        refreshRecyclerView();
    }

    private void refreshRecyclerView() {

        describeContainer.setVisibility(View.GONE);

        if (tabbarView.getPostServiceTabName() == PostServiceTabbarView.PostServiceTabName.DESCRIBE) {
            describeContainer.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            PostQuotationDescribeFragment frag = PostQuotationDescribeFragment.newInstance(isRequest);
            frag.setOnItemClickListener(new PostQuotationDescribeFragment.OnItemClickListener() {
                @Override
                public void onNextButtonClick(String jobTitle, String jobDesc) {
                    tabbarView.setTabName(PostServiceTabbarView.PostServiceTabName.TIME, isRequest);
                    serviceModel.setTitle(jobTitle);
                    serviceModel.setComment(jobDesc);
                    refreshRecyclerView();
                }
            });
            ft.replace(R.id.edit_job_describe_container, frag, frag.getTag());
            ft.commit();
        } else if (tabbarView.getPostServiceTabName() == PostServiceTabbarView.PostServiceTabName.TIME) {
            recyclerView.setVisibility(View.VISIBLE);
            PostQuotationTimeAdapter mTimeAdapter = new PostQuotationTimeAdapter(mContext, isRequest, serviceModel);
            mTimeAdapter.setOnItemClickListener(new PostQuotationTimeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Date date) {
                    tabbarView.setTabName(PostServiceTabbarView.PostServiceTabName.ADDRESS, isRequest);
                    serviceModel.setDate(date);
                    refreshRecyclerView();
                }
            });
            recyclerView.setAdapter(mTimeAdapter);
        } else if (tabbarView.getPostServiceTabName() == PostServiceTabbarView.PostServiceTabName.ADDRESS) {
            recyclerView.setVisibility(View.VISIBLE);
            PostQuotationAddressAdapter addressAdapter = new PostQuotationAddressAdapter(mContext, isRequest, serviceModel);
            addressAdapter.setOnItemClickListener(new PostQuotationAddressAdapter.OnItemClickListener() {
                @Override
                public void onNextButtonClick(String unit, String street, String postcode) {
                    tabbarView.setTabName(PostServiceTabbarView.PostServiceTabName.REPORT, isRequest);
                    serviceModel.setUnit(unit);
                    serviceModel.setStreet(street);
                    serviceModel.setPostcode(postcode);
                    refreshRecyclerView();
                }
            });
            recyclerView.setAdapter(addressAdapter);
        } else if (tabbarView.getPostServiceTabName() == PostServiceTabbarView.PostServiceTabName.REPORT) {
            recyclerView.setVisibility(View.VISIBLE);
            reportAdapter = new AppointmentReportAdapter(mContext, isRequest, "SERVICE");
            recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(mContext, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                    int itemCount = reportAdapter.getItemCount();
                    if (position == itemCount - 1) {
                        onSaveCreatingJob();
                        mActivity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.request_quotation_container, PostQuotationSummaryFragment.newInstance(true))
                                .commit();
                    } else {
                        if (!isMultiSelect) {
                            selectedIds = new ArrayList<>();
                            isMultiSelect = true;
                        }
                        multiSelect(position);
                    }
                }
                @Override
                public void onItemLongClick(View view, int position) {

                }
            }));
            recyclerView.setAdapter(reportAdapter);
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.refreshDrawableState();
    }

    private void onSaveCreatingJob() {
        reportType = 0;
        for (Integer index : selectedIds) {
            if (index == 1) {
                reportType += 1;
            } else if (index == 2) {
                reportType += 2;
            } else if (index == 3) {
                reportType += 4;
            }
        }
        //creatingJob.setReportType(reportType);
        //selectedAppointment = creatingJob;
    }

    private void multiSelect(int position) {

        data = reportAdapter.getItem(position - 1);

        if (data != null) {
            Integer id = data.getId();
            if (selectedIds.contains(data.getId()))
                selectedIds.remove(Integer.valueOf(data.getId()));
            else
                selectedIds.add(data.getId());
            reportAdapter.setSelectedIds(selectedIds);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mActivity = ((PostQuotationActivity) mContext);
        if (getArguments() != null) {
            status = getArguments().getString("tabName");
            isRequest = getArguments().getBoolean("isRequest");
            if (isRequest)((PostQuotationActivity)context).setBottomViewHidden(true);
            else ((JobStatusSummaryActivity) context).setStatus(JGGActionbarView.EditStatus.EDIT_DETAIL);
        }
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
        if (view.getId() == R.id.btn_alert_ok) {
            alertDialog.dismiss();
        }
    }

    private OnFragmentInteractionListener mListener;

    public void setmListener(OnFragmentInteractionListener mListener) {
        this.mListener = mListener;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
