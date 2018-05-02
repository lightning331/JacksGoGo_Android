package com.kelvin.jacksgogo.Fragments.Jobs;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.Activities.Jobs.PostProposalActivity;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserBaseModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGBaseResponse;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.Global.EDIT_STATUS;
import static com.kelvin.jacksgogo.Utils.Global.MY_PROPOSAL;
import static com.kelvin.jacksgogo.Utils.Global.createProgressDialog;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.appointmentMonthDate;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getAppointmentTime;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getDayMonthYear;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getProposalBudget;
import static com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel.getDaysString;

public class PostedProposalFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    @BindView(R.id.img_category) ImageView imgCategory;
    @BindView(R.id.lbl_category_name)  TextView lblCategory;
    @BindView(R.id.lbl_date)  TextView lblTime;
    @BindView(R.id.img_user_avatar) RoundedImageView imgAvatar;
    @BindView(R.id.lbl_username) TextView lblUserName;
    @BindView(R.id.user_rating) MaterialRatingBar ratingBar;
    @BindView(R.id.lbl_description) TextView lblDesc;
    @BindView(R.id.lbl_budget_type) TextView lblBudgetType;
    @BindView(R.id.lbl_bid_budget) TextView lblBudget;
    @BindView(R.id.lbl_breakdown) TextView lblBreakDown;
    @BindView(R.id.lbl_rescheduling) TextView lblRescheduling;
    @BindView(R.id.lbl_cancellation) TextView lblCancellation;
    @BindView(R.id.lbl_reference_no) TextView lblReferenceNo;
    @BindView(R.id.lbl_reference_posted_date) TextView lblPostedTime;
    @BindView(R.id.btn_delete_proposal) TextView btnDeleteProposal;
    @BindView(R.id.ll_breakdown) LinearLayout ll_breakdown;

    private PostProposalActivity mActivity;
    private ProgressDialog progressDialog;

    private JGGAppointmentModel selectedAppointment;
    private JGGProposalModel mProposal;
    private String editStatus;

    public PostedProposalFragment() {
        // Required empty public constructor
    }

    public static PostedProposalFragment newInstance(String status) {
        PostedProposalFragment fragment = new PostedProposalFragment();
        Bundle args = new Bundle();
        args.putString(EDIT_STATUS, status);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            editStatus = getArguments().getString(EDIT_STATUS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_posted_proposal, container, false);
        ButterKnife.bind(this, view);

        selectedAppointment = JGGAppManager.getInstance().getSelectedAppointment();
        mProposal = JGGAppManager.getInstance().getSelectedProposal();

        initView(view);
        setData();

        return view;
    }

    private void initView(View view) {

        if (editStatus.equals(MY_PROPOSAL))
            btnDeleteProposal.setVisibility(View.VISIBLE);
        else
            btnDeleteProposal.setVisibility(View.GONE);

        btnDeleteProposal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDeleteProposal();
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
            // Budget Type
            //lblBudgetType.setText();
            // Budget
            String budget = getProposalBudget(mProposal) + "/month";
            lblBudget.setText(budget);
            // Supplies
            String breakdown = mProposal.getBreakdown();
            if (breakdown == null)
                ll_breakdown.setVisibility(View.GONE);
            else
                lblBreakDown.setText(breakdown);
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
            String postedTime = "Posted on ";
            if (mProposal.isInvited() == null) {
                postedTime = postedTime + getDayMonthYear(appointmentMonthDate(mProposal.getPostOn()));
                // Description
                lblDesc.setText(mProposal.getDescription());
            } else {
                postedTime = postedTime + getDayMonthYear(mProposal.getSubmitOn());
                // Description
                lblDesc.setText(mProposal.getNote());
            }
            lblPostedTime.setText(postedTime);
        } else {
            lblDesc.setText("");
            lblBudgetType.setText("No set");
            lblBudget.setText("No set");
            lblBreakDown.setText("No set");
            lblRescheduling.setText("No set");
            lblCancellation.setText("No set");
        }
    }

    private void onDeleteProposal() {
        progressDialog = createProgressDialog(mContext);

        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        String proposalID = mProposal.getID();
        Call<JGGBaseResponse> call = apiManager.deleteProposal(proposalID);
        call.enqueue(new Callback<JGGBaseResponse>() {
            @Override
            public void onResponse(Call<JGGBaseResponse> call, Response<JGGBaseResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mActivity.finish();
                    } else {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode  = response.code();
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGBaseResponse> call, Throwable t) {
                Log.d("SignUpPhoneActivity", t.getMessage());
                Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
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
