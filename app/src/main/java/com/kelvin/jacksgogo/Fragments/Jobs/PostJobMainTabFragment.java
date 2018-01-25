package com.kelvin.jacksgogo.Fragments.Jobs;

import android.app.AlertDialog;
import android.content.Context;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.Search.PostServiceActivity;
import com.kelvin.jacksgogo.Adapter.Jobs.EditJobReportAdapter;
import com.kelvin.jacksgogo.CustomView.Views.PostJobTabbarView;
import com.kelvin.jacksgogo.Fragments.Search.PostServiceAddressFragment;
import com.kelvin.jacksgogo.Fragments.Search.PostServiceDescribeFragment;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCreatingJobModel;
import com.kelvin.jacksgogo.Utils.Models.System.JGGAddressModel;
import com.kelvin.jacksgogo.Utils.Models.System.JGGJobTimeModel;
import com.squareup.picasso.Picasso;

public class PostJobMainTabFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    private PostJobTabbarView tabbarView;
    private LinearLayout tabbarLayout;
    private ImageView imgCategory;
    private TextView lblCategory;
    private RecyclerView recyclerView;

    private FrameLayout describeContainer;
    private AlertDialog alertDialog;
    public JGGCreatingJobModel creatingJob;
    private String tabName;

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
            EditJobReportAdapter reportAdapter = new EditJobReportAdapter(mContext, true, "JOB");
            reportAdapter.setOnItemClickListener(new EditJobReportAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int[] position) {
                    PostJobSummaryFragment fragment = new PostJobSummaryFragment();
                    fragment.setEditStatus(PostJobSummaryFragment.PostJobStatus.NONE);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.post_service_container, fragment, fragment.getTag())
                            .addToBackStack("post_job_summary")
                            .commit();
                }
            });
            recyclerView.setAdapter(reportAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.refreshDrawableState();
        }
        ft.commit();
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
