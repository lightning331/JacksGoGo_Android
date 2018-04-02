package com.kelvin.jacksgogo.Fragments.Jobs;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.Jobs.PostProposalActivity;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserBaseModel;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

import static com.kelvin.jacksgogo.Utils.JGGAppManager.selectedAppointment;
import static com.kelvin.jacksgogo.Utils.JGGAppManager.selectedProposal;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.appointmentMonthDate;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getAppointmentTime;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getDayMonthYear;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getProposalBudget;
import static com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel.getDaysString;

public class PostedProposalFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    private ImageView imgCategory;
    private TextView lblCategory;
    private TextView lblTime;
    private RoundedImageView imgAvatar;
    private TextView lblUserName;
    private MaterialRatingBar ratingBar;
    private TextView lblDesc;
    private TextView lblBudgetType;
    private TextView lblBudget;
    private TextView lblSupplies;
    private TextView lblRescheduling;
    private TextView lblCancellation;
    private TextView lblReferenceNo;
    private TextView lblPostedTime;
    private TextView btnDeleteProposal;

    private PostProposalActivity mActivity;

    private JGGProposalModel mProposal;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_posted_proposal, container, false);
        mProposal = selectedProposal;

        initView(view);
        setData();

        return view;
    }

    private void initView(View view) {
        imgCategory = view.findViewById(R.id.img_category);
        lblCategory = view.findViewById(R.id.lbl_category_name);
        lblTime = view.findViewById(R.id.lbl_date);
        imgAvatar = view.findViewById(R.id.img_user_avatar);
        lblUserName = view.findViewById(R.id.lbl_username);
        ratingBar = view.findViewById(R.id.user_rating);
        lblDesc = view.findViewById(R.id.lbl_description);
        lblBudgetType = view.findViewById(R.id.lbl_budget_type);
        lblBudget = view.findViewById(R.id.lbl_bid_budget);
        lblSupplies = view.findViewById(R.id.lbl_supplies);
        lblRescheduling = view.findViewById(R.id.lbl_rescheduling);
        lblCancellation = view.findViewById(R.id.lbl_cancellation);
        lblReferenceNo = view.findViewById(R.id.lbl_reference_no);
        lblPostedTime = view.findViewById(R.id.lbl_reference_posted_date);
        btnDeleteProposal = view.findViewById(R.id.btn_delete_proposal);

        btnDeleteProposal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void setData() {

        Picasso.with(mContext)
                .load(selectedAppointment.getCategory().getImage())
                .placeholder(null)
                .into(imgCategory);
        lblCategory.setText(selectedAppointment.getCategory().getName());
        lblTime.setText(getAppointmentTime(selectedAppointment));

        if (mProposal != null) {
            // Proposed User
            JGGUserBaseModel user = mProposal.getUserProfile().getUser();
            Picasso.with(mContext)
                    .load(user.getPhotoURL())
                    .placeholder(R.mipmap.icon_profile)
                    .into(imgAvatar);
            lblUserName.setText(user.getUserName());
            if (user.getGivenName() == null)
                lblUserName.setText(user.getUserName());
            else
                lblUserName.setText(user.getFullName());
            Double rating = user.getRate();
            if (rating == null)
                ratingBar.setRating(0);
            else
                ratingBar.setRating(rating.floatValue());
            // Description
            lblDesc.setText(mProposal.getDescription());
            // Budget Type
            //lblBudgetType.setText();
            // Budget
            String budget = getProposalBudget(mProposal) + "/month";
            lblBudget.setText(budget);
            // Supplies
            String supplies = "Our own supplies - $ ";
            lblSupplies.setText(supplies);
            // Rescheduling
            String noReschedule = "No rescheduling allowed.";
            if (mProposal.isRescheduleAllowed() == null)
                lblRescheduling.setText(noReschedule);
            else
                if (mProposal.isRescheduleAllowed())
                    lblRescheduling.setText(getDaysString(Long.valueOf(mProposal.getRescheduleTime())));
                else
                    lblRescheduling.setText(noReschedule);
            // Cancellation
            String noCancellation = "No cancellation allowed.";
            if (mProposal.isCancellationAllowed() == null)
                lblCancellation.setText(noCancellation);
            else
                if (mProposal.isCancellationAllowed())
                    lblCancellation.setText(getDaysString(Long.valueOf(mProposal.getCancellationTime())));
                else
                    lblCancellation.setText(noCancellation);
            String proposalNo = "Proposal reference no: " + mProposal.getID();
            lblReferenceNo.setText(proposalNo);
            String postedTime = "Posted on " + getDayMonthYear(appointmentMonthDate(mProposal.getPostOn()));
            lblPostedTime.setText(postedTime);
        } else {
            lblDesc.setText("");
            lblBudgetType.setText("No set");
            lblBudget.setText("No set");
            lblSupplies.setText("No set");
            lblRescheduling.setText("No set");
            lblCancellation.setText("No set");
        }
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
