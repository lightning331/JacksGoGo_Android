package com.kelvin.jacksgogo.Fragments.Jobs;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.Search.PostServiceActivity;
import com.kelvin.jacksgogo.Adapter.Jobs.EditJobReportAdapter;
import com.kelvin.jacksgogo.CustomView.Views.PostJobTabbarView;
import com.kelvin.jacksgogo.CustomView.Views.RecyclerItemClickListener;
import com.kelvin.jacksgogo.Fragments.Search.PostServiceAddressFragment;
import com.kelvin.jacksgogo.Fragments.Search.PostServiceDescribeFragment;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCreatingJobModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGReportModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PostJobMainTabFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    private PostJobTabbarView tabbarView;
    private LinearLayout tabbarLayout;
    private ImageView imgCategory;
    private TextView lblCategory;
    private RecyclerView recyclerView;

    private FrameLayout describeContainer;
    private String tabName;

    private JGGCreatingJobModel creatingJob;
    private EditJobReportAdapter reportAdapter;
    private List<Integer> selectedIds = new ArrayList<>();
    private boolean isMultiSelect = false;
    private JGGReportModel data;
    private int reportType = 0;

    public PostJobMainTabFragment() {
        // Required empty public constructor
    }

    public static PostJobMainTabFragment newInstance(PostJobTabbarView.TabName name) {
        PostJobMainTabFragment fragment = new PostJobMainTabFragment();
        Bundle args = new Bundle();
        args.putString("tabName", name.toString());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tabName = getArguments().getString("tabName");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_job_main_tab, container, false);

        describeContainer = (FrameLayout) view.findViewById(R.id.post_job_detail_container);
        imgCategory = (ImageView) view.findViewById(R.id.img_post_job_tab_category);
        lblCategory = (TextView) view.findViewById(R.id.lbl_post_job_tab_category_name);
        Picasso.with(mContext)
                .load(((PostServiceActivity)mContext).selectedCategory.getImage())
                .placeholder(null)
                .into(imgCategory);
        lblCategory.setText(((PostServiceActivity)mContext).selectedCategory.getName());
        creatingJob = ((PostServiceActivity)mContext).creatingJob;

        recyclerView = (RecyclerView)view.findViewById(R.id.post_job_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        initTabbarView(view);

        return view;
    }

    private void initTabbarView(View view) {

        tabbarLayout = (LinearLayout)view.findViewById(R.id.post_job_tabbar_view);
        tabbarView = new PostJobTabbarView(mContext);
        tabbarLayout.addView(tabbarView);
        if (tabName == "DESCRIBE") {
            tabbarView.setTabName(PostJobTabbarView.TabName.DESCRIBE, true);
        } else if (tabName == "TIME") {
            tabbarView.setTabName(PostJobTabbarView.TabName.TIME, true);
        } else if (tabName == "ADDRESS") {
            tabbarView.setTabName(PostJobTabbarView.TabName.ADDRESS, true);
        } else if (tabName == "BUDGET") {
            tabbarView.setTabName(PostJobTabbarView.TabName.BUDGET, true);
        } else if (tabName == "REPORT") {
            tabbarView.setTabName(PostJobTabbarView.TabName.REPORT, true);
        }
        tabbarView.setTabItemClickLietener(new PostJobTabbarView.OnTabItemClickListener() {
            @Override
            public void onTabItemClick(View view) {
                onTabbarViewClick(view);
            }
        });
        refreshFragment();
    }

    private void onTabbarViewClick(View view) {

        if (view.getId() == R.id.btn_describe) {
            tabbarView.setTabName(PostJobTabbarView.TabName.DESCRIBE, true);
        } else if (view.getId() == R.id.btn_time) {
            tabbarView.setTabName(PostJobTabbarView.TabName.TIME, true);
        } else if (view.getId() == R.id.btn_address) {
            tabbarView.setTabName(PostJobTabbarView.TabName.ADDRESS, true);
        } else if (view.getId() == R.id.btn_budget) {
            tabbarView.setTabName(PostJobTabbarView.TabName.BUDGET, true);
        } else if (view.getId() == R.id.btn_report) {
            tabbarView.setTabName(PostJobTabbarView.TabName.REPORT, true);
        }
        refreshFragment();
    }

    private void refreshFragment() {

        describeContainer.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        if (tabbarView.getTabName() == PostJobTabbarView.TabName.DESCRIBE) {
            PostServiceDescribeFragment frag = PostServiceDescribeFragment.newInstance("JOB");
            frag.setOnItemClickListener(new PostServiceDescribeFragment.OnItemClickListener() {
                @Override
                public void onNextButtonClick() {
                    tabbarView.setTabName(PostJobTabbarView.TabName.TIME, true);
                    refreshFragment();
                }
            });
            ft.replace(R.id.post_job_detail_container, frag, frag.getTag());
        } else if (tabbarView.getTabName() == PostJobTabbarView.TabName.TIME) {
            PostJobTimeFragment frag = new PostJobTimeFragment();
            frag.setOnItemClickListener(new PostJobTimeFragment.OnItemClickListener() {
                @Override
                public void onNextButtonClick() {
                    tabbarView.setTabName(PostJobTabbarView.TabName.ADDRESS, true);
                    refreshFragment();
                }
            });
            ft.replace(R.id.post_job_detail_container, frag, frag.getTag());
        } else if (tabbarView.getTabName() == PostJobTabbarView.TabName.ADDRESS) {
            PostServiceAddressFragment frag = PostServiceAddressFragment.newInstance("JOB");
            frag.setOnItemClickListener(new PostServiceAddressFragment.OnItemClickListener() {
                @Override
                public void onNextButtonClick() {
                    tabbarView.setTabName(PostJobTabbarView.TabName.BUDGET, true);
                    refreshFragment();
                }
            });
            ft.replace(R.id.post_job_detail_container, frag, frag.getTag());
        } else if (tabbarView.getTabName() == PostJobTabbarView.TabName.BUDGET) {
            PostJobPriceFragment frag = new PostJobPriceFragment();
            frag.setOnItemClickListener(new PostJobPriceFragment.OnItemClickListener() {
                @Override
                public void onNextButtonClick() {
                    tabbarView.setTabName(PostJobTabbarView.TabName.REPORT, true);
                    refreshFragment();
                }
            });
            ft.replace(R.id.post_job_detail_container, frag, frag.getTag());
        } else if (tabbarView.getTabName() == PostJobTabbarView.TabName.REPORT) {
            describeContainer.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            creatingJob = ((PostServiceActivity)mContext).creatingJob;
            selectedIds = creatingJob.selectedID();

            reportAdapter = new EditJobReportAdapter(mContext, true, "JOB");
            reportAdapter.setSelectedIds(selectedIds);
            recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(mContext, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    int itemCount = reportAdapter.getItemCount();
                    if (position == itemCount - 1) {
                        onSaveCreatingJob();
                        PostJobSummaryFragment fragment = new PostJobSummaryFragment();
                        fragment.setEditStatus(PostJobSummaryFragment.PostJobStatus.NONE);

                        getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.post_service_container, fragment, fragment.getTag())
                        .addToBackStack("post_job_summary")
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
        ft.commit();
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
        creatingJob.setReportType(reportType);
        ((PostServiceActivity)mContext).creatingJob = creatingJob;
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}