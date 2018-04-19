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
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.Activities.Jobs.PostProposalActivity;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserBaseModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGBaseResponse;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.Global.EDIT_STATUS;
import static com.kelvin.jacksgogo.Utils.Global.MY_PROPOSAL;
import static com.kelvin.jacksgogo.Utils.Global.createProgressDialog;
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
    private ProgressDialog progressDialog;

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

    private void onDeleteProposal() {
        progressDialog = createProgressDialog(mContext);

        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        String proposalID = selectedProposal.getID();
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
