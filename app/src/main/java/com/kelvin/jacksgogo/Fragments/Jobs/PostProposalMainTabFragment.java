package com.kelvin.jacksgogo.Fragments.Jobs;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.Jobs.PostProposalActivity;
import com.kelvin.jacksgogo.CustomView.Views.PostProposalTabbarView;
import com.kelvin.jacksgogo.R;
import com.squareup.picasso.Picasso;

import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.selectedAppointment;
import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.selectedCategory;
import static com.kelvin.jacksgogo.Utils.Global.EDIT;
import static com.kelvin.jacksgogo.Utils.Global.POST;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.convertJobTimeString;

public class PostProposalMainTabFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    private PostProposalTabbarView tabbarView;
    private LinearLayout tabbarLayout;
    private ImageView imgCategory;
    private TextView lblCategory;
    private TextView lblTime;

    private String tabName;
    private String postStatus;

    private PostProposalActivity mActivity;

    public PostProposalMainTabFragment() {
        // Required empty public constructor
    }

    public static PostProposalMainTabFragment newInstance(PostProposalTabbarView.TabName name, PostProposalSummaryFragment.ProposalStatus status) {
        PostProposalMainTabFragment fragment = new PostProposalMainTabFragment();
        Bundle args = new Bundle();
        args.putString("tabName", name.toString());
        args.putString("postStatus", status.toString());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tabName = getArguments().getString("tabName");
            postStatus = getArguments().getString("postStatus");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_proposal_main_tab, container, false);

        imgCategory = (ImageView) view.findViewById(R.id.img_category);
        lblCategory = (TextView) view.findViewById(R.id.lbl_category_name);
        lblTime = (TextView) view.findViewById(R.id.lbl_date);

        Picasso.with(mContext)
                .load(selectedAppointment.getCategory().getImage())
                .placeholder(null)
                .into(imgCategory);
        lblCategory.setText(selectedAppointment.getCategory().getName());
        lblTime.setText(convertJobTimeString(selectedAppointment));

        initTabbarView(view);

        return view;
    }

    private void initTabbarView(View view) {

        tabbarLayout = (LinearLayout)view.findViewById(R.id.post_proposal_tabbar_layout);
        tabbarView = new PostProposalTabbarView(mContext);
        tabbarLayout.addView(tabbarView);
        if (tabName == "DESCRIBE") {
            tabbarView.setPostJobTabName(PostProposalTabbarView.TabName.DESCRIBE);
        } else if (tabName == "BID") {
            tabbarView.setPostJobTabName(PostProposalTabbarView.TabName.BID);
        } else if (tabName == "RESCHEDULING") {
            tabbarView.setPostJobTabName(PostProposalTabbarView.TabName.RESCHEDULING);
        } else if (tabName == "CANCELLATION") {
            tabbarView.setPostJobTabName(PostProposalTabbarView.TabName.CANCELLATION);
        }
        tabbarView.setTabItemClickLietener(new PostProposalTabbarView.OnTabItemClickListener() {
            @Override
            public void onTabItemClick(View view) {
                onTabbarViewClick(view);
            }
        });
        refreshFragment();
    }

    private void onTabbarViewClick(View view) {

        if (view.getId() == R.id.btn_describe) {
            tabbarView.setPostJobTabName(PostProposalTabbarView.TabName.DESCRIBE);
        } else if (view.getId() == R.id.btn_bid) {
            tabbarView.setPostJobTabName(PostProposalTabbarView.TabName.BID);
        } else if (view.getId() == R.id.btn_rescheduling) {
            tabbarView.setPostJobTabName(PostProposalTabbarView.TabName.RESCHEDULING);
        } else if (view.getId() == R.id.btn_cancellation) {
            tabbarView.setPostJobTabName(PostProposalTabbarView.TabName.CANCELLATION);
        }
        refreshFragment();
    }

    private void refreshFragment() {
        FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
        if (tabbarView.getPostJobTabName() == PostProposalTabbarView.TabName.DESCRIBE) {
            PostProposalDescribeFragment fragment = new PostProposalDescribeFragment();
            fragment.setOnItemClickListener(new PostProposalDescribeFragment.OnItemClickListener() {
                @Override
                public void onNextButtonClick() {
                    tabbarView.setPostJobTabName(PostProposalTabbarView.TabName.BID);
                    refreshFragment();
                }
            });
            ft.replace(R.id.post_proposal_main_container, fragment);
        } else if (tabbarView.getPostJobTabName() == PostProposalTabbarView.TabName.BID) {
            PostProposalBidFragment fragment = new PostProposalBidFragment();
            fragment.setOnItemClickListener(new PostProposalBidFragment.OnItemClickListener() {
                @Override
                public void onNextButtonClick() {
                    tabbarView.setPostJobTabName(PostProposalTabbarView.TabName.RESCHEDULING);
                    refreshFragment();
                }
            });
            ft.replace(R.id.post_proposal_main_container, fragment);
        } else if (tabbarView.getPostJobTabName() == PostProposalTabbarView.TabName.RESCHEDULING) {
            PostProposalRescheduleFragment fragment = new PostProposalRescheduleFragment();
            fragment.setRescheduling(true);
            fragment.setOnItemClickListener(new PostProposalRescheduleFragment.OnItemClickListener() {
                @Override
                public void onNextButtonClick() {
                    tabbarView.setPostJobTabName(PostProposalTabbarView.TabName.CANCELLATION);
                    refreshFragment();
                }
            });
            ft.replace(R.id.post_proposal_main_container, fragment);
        } else if (tabbarView.getPostJobTabName() == PostProposalTabbarView.TabName.CANCELLATION) {
            PostProposalRescheduleFragment fragment = new PostProposalRescheduleFragment();
            fragment.setRescheduling(false);
            fragment.setOnItemClickListener(new PostProposalRescheduleFragment.OnItemClickListener() {
                @Override
                public void onNextButtonClick() {
                    PostProposalSummaryFragment fragment = new PostProposalSummaryFragment();
                    if (postStatus.equals(POST))
                        fragment.setEditStatus(PostProposalSummaryFragment.ProposalStatus.POST);
                    else if (postStatus.equals(EDIT))
                        fragment.setEditStatus(PostProposalSummaryFragment.ProposalStatus.EDIT);

                    mActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.post_proposal_container, fragment)
                            .addToBackStack("post_proposal")
                            .commit();
                }
            });
            ft.replace(R.id.post_proposal_main_container, fragment);
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
        mActivity = ((PostProposalActivity) mContext);
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
