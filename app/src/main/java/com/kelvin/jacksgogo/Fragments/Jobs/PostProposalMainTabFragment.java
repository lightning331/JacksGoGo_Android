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
import com.kelvin.jacksgogo.CustomView.Views.PostProposalTabView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.squareup.picasso.Picasso;

import static com.kelvin.jacksgogo.Utils.Global.EDIT;
import static com.kelvin.jacksgogo.Utils.Global.INVITE_PROPOSAL;
import static com.kelvin.jacksgogo.Utils.Global.POST;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getAppointmentTime;

public class PostProposalMainTabFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    private PostProposalTabView tabbarView;
    private LinearLayout tabbarLayout;
    private ImageView imgCategory;
    private TextView lblCategory;
    private TextView lblTime;

    private String tabName;
    private String postStatus;
    private int tabIndex = 0;

    private JGGAppointmentModel selectedAppointment;
    private PostProposalActivity mActivity;

    public PostProposalMainTabFragment() {
        // Required empty public constructor
    }

    public static PostProposalMainTabFragment newInstance(PostProposalTabView.TabName name, PostProposalSummaryFragment.ProposalStatus status) {
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

        selectedAppointment = JGGAppManager.getInstance().getSelectedAppointment();

        Picasso.with(mContext)
                .load(selectedAppointment.getCategory().getImage())
                .placeholder(null)
                .into(imgCategory);
        lblCategory.setText(selectedAppointment.getCategory().getName());
        lblTime.setText(getAppointmentTime(selectedAppointment));

        tabbarLayout = (LinearLayout)view.findViewById(R.id.post_proposal_tabbar_layout);
        tabbarView = new PostProposalTabView(mContext);
        tabbarLayout.addView(tabbarView);
        tabbarView.setTabItemClickListener(new PostProposalTabView.OnTabItemClickListener() {
            @Override
            public void onTabItemClick(View view) {
                onTabbarViewClick(view);
            }
        });

        initTabbarView(view);

        return view;
    }

    public int getTabIndex() {
        return this.tabIndex;
    }

    public void setTabIndex(int index) {
        this.tabIndex = index;
        switch (index) {
            case 0:
                tabbarView.setPostJobTabName(PostProposalTabView.TabName.DESCRIBE);
                break;
            case 1:
                tabbarView.setPostJobTabName(PostProposalTabView.TabName.BID);
                break;
            case 2:
                tabbarView.setPostJobTabName(PostProposalTabView.TabName.RESCHEDULING);
                break;
            case 3:
                tabbarView.setPostJobTabName(PostProposalTabView.TabName.CANCELLATION);
                break;
        }

        refreshFragment();
    }

    private void initTabbarView(View view) {
        if (tabName == "DESCRIBE") {
            setTabIndex(0);
        } else if (tabName == "BID") {
            setTabIndex(1);
        } else if (tabName == "RESCHEDULING") {
            setTabIndex(2);
        } else if (tabName == "CANCELLATION") {
            setTabIndex(3);
        }
    }

    private void onTabbarViewClick(View view) {
        if (view.getId() == R.id.btn_describe) {
            setTabIndex(0);
        } else if (view.getId() == R.id.btn_bid) {
            setTabIndex(1);
        } else if (view.getId() == R.id.btn_rescheduling) {
            setTabIndex(2);
        } else if (view.getId() == R.id.btn_cancellation) {
            setTabIndex(3);
        }
    }

    private void refreshFragment() {
        FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
        if (tabbarView.getPostJobTabName() == PostProposalTabView.TabName.DESCRIBE) {
            PostProposalDescribeFragment fragment = new PostProposalDescribeFragment();
            fragment.setOnItemClickListener(new PostProposalDescribeFragment.OnItemClickListener() {
                @Override
                public void onNextButtonClick() {
                    setTabIndex(1);
                }
            });
            ft.replace(R.id.post_proposal_main_container, fragment);
        } else if (tabbarView.getPostJobTabName() == PostProposalTabView.TabName.BID) {
            PostProposalBidFragment fragment = new PostProposalBidFragment();
            fragment.setOnItemClickListener(new PostProposalBidFragment.OnItemClickListener() {
                @Override
                public void onNextButtonClick() {
                    setTabIndex(2);
                }
            });
            ft.replace(R.id.post_proposal_main_container, fragment);
        } else if (tabbarView.getPostJobTabName() == PostProposalTabView.TabName.RESCHEDULING) {
            PostProposalRescheduleFragment fragment = new PostProposalRescheduleFragment();
            fragment.setRescheduling(true);
            fragment.setOnItemClickListener(new PostProposalRescheduleFragment.OnItemClickListener() {
                @Override
                public void onNextButtonClick() {
                    setTabIndex(3);
                }
            });
            ft.replace(R.id.post_proposal_main_container, fragment);
        } else if (tabbarView.getPostJobTabName() == PostProposalTabView.TabName.CANCELLATION) {
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
                    else if (postStatus.equals(INVITE_PROPOSAL))
                        fragment.setEditStatus(PostProposalSummaryFragment.ProposalStatus.INVITE);

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
