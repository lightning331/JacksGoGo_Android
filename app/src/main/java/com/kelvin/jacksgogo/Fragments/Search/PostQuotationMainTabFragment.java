package com.kelvin.jacksgogo.Fragments.Search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.kelvin.jacksgogo.Activities.JGGMapViewActivity;
import com.kelvin.jacksgogo.Activities.Search.PostQuotationActivity;
import com.kelvin.jacksgogo.Adapter.Jobs.AppointmentReportAdapter;
import com.kelvin.jacksgogo.Adapter.Jobs.PostQuotationAddressAdapter;
import com.kelvin.jacksgogo.Adapter.Jobs.PostQuotationTimeAdapter;
import com.kelvin.jacksgogo.CustomView.Views.PostServiceTabView;
import com.kelvin.jacksgogo.CustomView.Views.RecyclerItemClickListener;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGReportModel;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGQuotationModel;
import com.kelvin.jacksgogo.Utils.Models.System.JGGAddressModel;
import com.kelvin.jacksgogo.Utils.Models.System.JGGTimeSlotModel;

import java.util.ArrayList;
import java.util.List;

import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.REQUEST_CODE;
import static com.kelvin.jacksgogo.Utils.Global.SERVICES;
import static com.kelvin.jacksgogo.Utils.Global.getReportTypeID;

public class PostQuotationMainTabFragment extends Fragment implements View.OnClickListener {

    private Context mContext;
    private PostQuotationActivity mActivity;

    private FrameLayout describeContainer;
    private RecyclerView recyclerView;
    private PostServiceTabView tabbarView;

    private AppointmentReportAdapter reportAdapter;
    private PostQuotationAddressAdapter addressAdapter;

    private JGGQuotationModel mQuotation;
    private JGGAddressModel mAddress;
    private List<Integer> selectedIds = new ArrayList<>();
    private boolean isRequest;      // true: Request, false: Edit
    private boolean isMultiSelect;
    private String status;
    private int reportType = 0;

    private int tabIndex = 0;

    public PostQuotationMainTabFragment() {
        // Required empty public constructor
    }

    public static PostQuotationMainTabFragment newInstance(PostServiceTabView.PostServiceTabName status, boolean b) {
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
        mQuotation = JGGAppManager.getInstance().getSelectedQuotation();
        mAddress = mQuotation.getAddress();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_quotation_main_tab, container, false);

        LinearLayout tabbarLayout = (LinearLayout)view.findViewById(R.id.edit_job_tabbar_view);
        tabbarView = new PostServiceTabView(getContext());
        tabbarLayout.addView(tabbarView);

        describeContainer = (FrameLayout) view.findViewById(R.id.edit_job_describe_container);
        recyclerView = (RecyclerView) view.findViewById(R.id.descibe_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(lp);

        initTabbarView();

        tabbarView.setTabItemClickListener(new PostServiceTabView.OnTabItemClickListener() {
            @Override
            public void onTabItemClick(View view) {
                onTabbarViewClick(view);
            }
        });

        return view;
    }

    public int getTabIndex() {
        return this.tabIndex;
    }

    public void setTabIndex(int index) {
        this.tabIndex = index;
        switch (index) {
            case 0:
                tabbarView.setTabName(PostServiceTabView.PostServiceTabName.DESCRIBE, isRequest);
                break;
            case 1:
                tabbarView.setTabName(PostServiceTabView.PostServiceTabName.TIME, isRequest);
                break;
            case 2:
                tabbarView.setTabName(PostServiceTabView.PostServiceTabName.ADDRESS, isRequest);
                break;
            case 3:
                tabbarView.setTabName(PostServiceTabView.PostServiceTabName.REPORT, isRequest);
                break;
        }

        refreshRecyclerView();
    }


    private void initTabbarView() {

        if (status.equals("DESCRIBE")) {
            setTabIndex(0);
        } else if (status.equals("TIME")) {
            setTabIndex(1);
        } else if (status.equals("ADDRESS")) {
            setTabIndex(2);
        } else if (status.equals("REPORT")) {
            setTabIndex(3);
        }
    }

    private void onTabbarViewClick(View view) {

        if (view.getId() == R.id.btn_describe) {
            setTabIndex(0);
        } else if (view.getId() == R.id.btn_time) {
            setTabIndex(1);
        } else if (view.getId() == R.id.btn_address) {
            setTabIndex(2);
        } else if (view.getId() == R.id.btn_report) {
            setTabIndex(3);
        }
    }

    private void refreshRecyclerView() {

        describeContainer.setVisibility(View.GONE);

        if (tabbarView.getPostServiceTabName() == PostServiceTabView.PostServiceTabName.DESCRIBE) {
            describeContainer.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            PostQuotationDescribeFragment frag = PostQuotationDescribeFragment.newInstance();
            frag.setOnItemClickListener(new PostQuotationDescribeFragment.OnItemClickListener() {
                @Override
                public void onNextButtonClick(String jobTitle, String jobDesc) {
                    // Save Quotation Describe Data
                    mQuotation.setTitle(jobTitle);
                    mQuotation.setDescription(jobDesc);
                    setTabIndex(1);
                }
            });
            ft.replace(R.id.edit_job_describe_container, frag, frag.getTag());
            ft.commit();
        } else if (tabbarView.getPostServiceTabName() == PostServiceTabView.PostServiceTabName.TIME) {
            recyclerView.setVisibility(View.VISIBLE);
            PostQuotationTimeAdapter mTimeAdapter = new PostQuotationTimeAdapter(mContext, mQuotation);
            mTimeAdapter.setOnItemClickListener(new PostQuotationTimeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(ArrayList<JGGTimeSlotModel> sessions) {
                    // Save Quotation TimeSlot Data
                    mQuotation.setSessions(sessions);
                    mQuotation.setAppointmentType(1);
                    setTabIndex(2);
                }
            });
            recyclerView.setAdapter(mTimeAdapter);
        } else if (tabbarView.getPostServiceTabName() == PostServiceTabView.PostServiceTabName.ADDRESS) {
            recyclerView.setVisibility(View.VISIBLE);
            addressAdapter = new PostQuotationAddressAdapter(mContext, mAddress);
            addressAdapter.setOnItemClickListener(new PostQuotationAddressAdapter.OnItemClickListener() {
                @Override
                public void onNextButtonClick(View view, JGGAddressModel address) {
                    if (view.getId() == R.id.btn_invite) {
                        Intent intent = new Intent(mContext, JGGMapViewActivity.class);
                        intent.putExtra(APPOINTMENT_TYPE, SERVICES);
                        startActivityForResult(intent, REQUEST_CODE);
                    } else if (view.getId() == R.id.view_filter_bg) {
                        // Save Quotation Address Data
                        mQuotation.setAddress(address);
                        setTabIndex(3);
                    }
                }
            });
            recyclerView.setAdapter(addressAdapter);
        } else if (tabbarView.getPostServiceTabName() == PostServiceTabView.PostServiceTabName.REPORT) {

            selectedIds = getReportTypeID(mQuotation.getReportType());

            recyclerView.setVisibility(View.VISIBLE);
            reportAdapter = new AppointmentReportAdapter(mContext, isRequest, SERVICES);
            reportAdapter.setSelectedIds(selectedIds);
            recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(mContext, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                    int itemCount = reportAdapter.getItemCount();
                    if (position == itemCount - 1) {
                        onSaveCreatingQuotation();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK){
                Gson gson = new Gson();
                String result=data.getStringExtra("result");
                mAddress = gson.fromJson(result, JGGAddressModel.class);
                addressAdapter.notifyDataChanged(mAddress);
                recyclerView.setAdapter(addressAdapter);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    private void onSaveCreatingQuotation() {
        for (Integer index : selectedIds) {
            if (index == 1) {
                reportType += 1;
            } else if (index == 2) {
                reportType += 2;
            } else if (index == 3) {
                reportType += 4;
            }
        }
        // Save Quotation Report Data
        mQuotation.setReportType(reportType);

        JGGAppManager.getInstance().setSelectedQuotation(mQuotation);

        mActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.request_quotation_container, PostQuotationSummaryFragment.newInstance(true))
                .addToBackStack("post_quotation")
                .commit();
    }

    private void multiSelect(int position) {

        JGGReportModel report = reportAdapter.getItem(position - 1);

        if (report != null) {
            Integer id = report.getId();
            if (selectedIds.contains(id))
                selectedIds.remove(Integer.valueOf(id));
            else
                selectedIds.add(id);
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
