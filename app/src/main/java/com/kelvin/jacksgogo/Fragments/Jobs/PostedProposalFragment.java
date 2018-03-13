package com.kelvin.jacksgogo.Fragments.Jobs;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.Jobs.PostProposalActivity;
import com.kelvin.jacksgogo.R;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class PostedProposalFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    @BindView(R.id.img_category) ImageView imgCategory;
    @BindView(R.id.lbl_category_name) TextView lblCategory;
    @BindView(R.id.lbl_date) TextView lblTime;
    @BindView(R.id.img_user_avatar) RoundedImageView imgAvatar;
    @BindView(R.id.lbl_username) TextView lblUserName;
    @BindView(R.id.user_rating) MaterialRatingBar ratingBar;
    @BindView(R.id.lbl_description) TextView lblDesc;
    @BindView(R.id.lbl_budget_type) TextView lblBudgetType;
    @BindView(R.id.lbl_bid_budget) TextView lblBudget;
    @BindView(R.id.lbl_supplies) TextView lblSupplies;
    @BindView(R.id.lbl_rescheduling) TextView lblRescheduling;
    @BindView(R.id.lbl_cancellation) TextView lblCancellation;
    @BindView(R.id.lbl_reference_no) TextView lblReferenceNo;
    @BindView(R.id.lbl_reference_posted_date) TextView lblPostedTime;

    private PostProposalActivity mActivity;

    public PostedProposalFragment() {
        // Required empty public constructor
    }

    public static PostedProposalFragment newInstance(String param1, String param2) {
        PostedProposalFragment fragment = new PostedProposalFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        ButterKnife.bind(mActivity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_posted_proposal, container, false);
        return view;
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
        void onFragmentInteraction(Uri uri);
    }
}
