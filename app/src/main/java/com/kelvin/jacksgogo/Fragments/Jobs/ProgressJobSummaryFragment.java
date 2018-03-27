package com.kelvin.jacksgogo.Fragments.Jobs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.Activities.Jobs.InviteProviderActivity;
import com.kelvin.jacksgogo.Activities.Jobs.PostProposalActivity;
import com.kelvin.jacksgogo.Activities.Jobs.ProgressJobSummaryActivity;
import com.kelvin.jacksgogo.Activities.Jobs.ServiceProviderActivity;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobStatusSummaryCancelled;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobStatusSummaryConfirmedView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobStatusSummaryFooterView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobStatusSummaryPaymentView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobStatusSummaryQuotationView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobStatusSummaryTipView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobStatusSummaryWorkProgressView;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGBaseResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGProposalResponse;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.currentUser;
import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.selectedAppointment;
import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.selectedProposal;
import static com.kelvin.jacksgogo.Utils.Global.EDIT_STATUS;
import static com.kelvin.jacksgogo.Utils.Global.JGGProposalStatus.confirmed;
import static com.kelvin.jacksgogo.Utils.Global.createProgressDialog;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.appointmentMonthDate;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getDayMonthYear;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getTimePeriodString;

public class ProgressJobSummaryFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private Context mContext;
    private ProgressJobSummaryActivity mActivity;

    private TextView lblPostedTime;
    private TextView lblPostedJob;
    private LinearLayout btnPostedJob;
    private LinearLayout mBottomLayout;
    private RoundedImageView imgProvider;
    private TextView lblProviderName;
    private LinearLayout btnViewProposal;
    private RelativeLayout btnChat;

    private LinearLayout quotationLayout;
    private JobStatusSummaryQuotationView quotationView;
    private LinearLayout cancelledLayout;
    private JobStatusSummaryCancelled cancelledView;
    private LinearLayout confirmedLayout;
    private JobStatusSummaryConfirmedView confirmedView;
    private LinearLayout progressLayout;
    private JobStatusSummaryWorkProgressView progressView;
    private LinearLayout footerLayout;
    private JobStatusSummaryFooterView footerView;
    private LinearLayout tipLayout;
    private JobStatusSummaryTipView tipView;
    private LinearLayout paymentLayout;
    private JobStatusSummaryPaymentView paymentView;

    private ProgressDialog progressDialog;
    private View view;

    private JGGAppointmentModel mJob;
    private ArrayList<JGGProposalModel> mProposals = new ArrayList<>();
    private String mReason;
    private boolean isDeleted;

    public ProgressJobSummaryFragment() {
        // Required empty public constructor
    }

    public static ProgressJobSummaryFragment newInstance(String param1, String param2) {
        ProgressJobSummaryFragment fragment = new ProgressJobSummaryFragment();
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
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint()) {
            return;
        }
        mJob = selectedAppointment;
        if (mJob.getStatus() == confirmed)
            getProposalsByJob();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_job_progress_summary, container, false);

        initView();
        return view;
    }

    private void initView() {
        lblPostedTime = view.findViewById(R.id.lbl_posted_time);
        lblPostedJob = view.findViewById(R.id.lbl_next_step_title);
        btnPostedJob = view.findViewById(R.id.btn_posted_job);
        mBottomLayout = view.findViewById(R.id.posted_service_chat_layout);
        imgProvider = view.findViewById(R.id.img_avatar);
        lblProviderName = view.findViewById(R.id.lbl_provider_name);
        btnViewProposal = view.findViewById(R.id.btn_view_proposal);
        btnChat = view.findViewById(R.id.btn_chat);
        btnPostedJob.setOnClickListener(this);
        btnViewProposal.setOnClickListener(this);
        btnChat.setOnClickListener(this);

        cancelledLayout = view.findViewById(R.id.job_main_cancelled_layout);
        cancelledView = new JobStatusSummaryCancelled(mContext);

        quotationLayout = view.findViewById(R.id.job_main_quotation_layout);
        quotationView = new JobStatusSummaryQuotationView(mContext);

        confirmedLayout = view.findViewById(R.id.job_main_confirmed_layout);
        confirmedView = new JobStatusSummaryConfirmedView(mContext);

        progressLayout = view.findViewById(R.id.job_main_work_progress_layout);
        progressView = new JobStatusSummaryWorkProgressView(mContext);

        footerLayout = (LinearLayout)view.findViewById(R.id.job_main_footer_layout);
        footerView = new JobStatusSummaryFooterView(mContext);

        tipLayout = view.findViewById(R.id.job_main_tip_layout);
        tipView = new JobStatusSummaryTipView(mContext);

        paymentLayout = view.findViewById(R.id.job_main_payment_layout);
        paymentView = new JobStatusSummaryPaymentView(mContext);
    }

    private void resetViews() {
        cancelledLayout.removeAllViews();
        quotationLayout.removeAllViews();
        confirmedLayout.removeAllViews();
        progressLayout.removeAllViews();
        footerLayout.removeAllViews();
    }

    private void onRefreshView() {
        resetViews();
        Date postOn = appointmentMonthDate(mJob.getPostOn());
        String postedTime = getDayMonthYear(postOn) + " " + getTimePeriodString(postOn);
        lblPostedTime.setText(postedTime);

        /*LinearLayout givenReviewLayout = (LinearLayout)view.findViewById(R.id.job_main_given_review_layout);
        JobStatusSummaryReview givenReviewView = new JobStatusSummaryReview(mContext);
        givenReviewLayout.addView(givenReviewView);
        givenReviewView.setOnItemClickListener(new JobStatusSummaryReview.OnItemClickListener() {
            @Override
            public void onItemClick(View item) {
                onShowReviewFragment();
            }
        });

        LinearLayout getReviewLayout = (LinearLayout)view.findViewById(R.id.job_main_get_review_layout);
        JobStatusSummaryReview getReviewView = new JobStatusSummaryReview(mContext);
        getReviewLayout.addView(getReviewView);
        getReviewView.setOnItemClickListener(new JobStatusSummaryReview.OnItemClickListener() {
            @Override
            public void onItemClick(View item) {
                onShowReviewFragment();
            }
        });

        tipLayout = view.findViewById(R.id.job_main_tip_layout);
        tipView = new JobStatusSummaryTipView(mContext);
        tipLayout.addView(tipView);

        paymentLayout = view.findViewById(R.id.job_main_payment_layout);
        paymentView = new JobStatusSummaryPaymentView(mContext);
        paymentView.setOnItemClickListener(new JobStatusSummaryPaymentView.OnItemClickListener() {
            @Override
            public void onItemClick(View item) {
                JobReportFragment frag = JobReportFragment.newInstance(false);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.app_detail_container, frag, frag.getTag());
                ft.addToBackStack("report_fragment");
                ft.commit();
            }
        });
        paymentLayout.addView(paymentView);*/


        /*
         *  Confirmed View
         */
        if (mJob.getStatus() == confirmed) {
            // Footer Layout
            footerView.reviewLayout.setVisibility(View.GONE);
            footerView.tipLayout.setVisibility(View.GONE);
            footerView.rehireLayout.setVisibility(View.GONE);
            footerView.setOnItemClickListener(new JobStatusSummaryFooterView.OnItemClickListener() {
                @Override
                public void onItemClick(View item) {
                    if (item.getId() == R.id.job_report_layout) {
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.app_detail_container, JobReportFragment.newInstance(true))
                                .addToBackStack("report_fragment")
                                .commit();
                    } else if (item.getId() == R.id.job_invoice_layout) {

                    }
                }
            });
            footerLayout.addView(footerView);

            // Start Work Layout
            progressLayout.addView(progressView);
            progressView.lblStartTime.setText(postedTime);
            progressView.imgStartWork.setImageResource(R.mipmap.icon_startwork);
            progressView.startWorkLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGGreen));
            progressView.lblUserName.setText(mJob.getUserProfile().getUser().getFullName());

            // Confirmed Layout
            confirmedLayout.addView(confirmedView);
            confirmedView.lblConfirmedTime.setText(postedTime);
            confirmedView.lblConfirmedDesc.setVisibility(View.GONE);
            //confirmedView.confirmedLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGGreen));
            //confirmedView.imgConfirmed.setImageResource(R.mipmap.icon_appointment);
            Picasso.with(mContext)
                    .load(mJob.getUserProfile().getUser().getPhotoURL())
                    .placeholder(R.mipmap.icon_profile)
                    .into(imgProvider);
            lblProviderName.setText(mJob.getUserProfile().getUser().getFullName());
            mBottomLayout.setVisibility(View.VISIBLE);
        }
        if (isDeleted) {
            /*
             *  Cancelled View
             */
            Picasso.with(mContext).load(currentUser.getUser().getPhotoURL())
                    .placeholder(R.mipmap.icon_profile)
                    .into(cancelledView.imgAvatar);
            cancelledView.lblComment.setText(mReason);
            cancelledLayout.addView(cancelledView);
        } else {
            // Invite Service Provider
            if (mProposals.size() == 0 || mProposals == null || mJob.getStatus() == confirmed)
                lblPostedJob.setText(R.string.job_request_posted);
            // View Quotation
            else if (mProposals.size() > 0)
                lblPostedJob.setText(R.string.outgoing_job);
        }

        /*
         *  Quotation View
         */
        if (mJob.getStatus() == confirmed) {
            quotationView.lblTime.setText(postedTime);
            quotationView.viewQuotationLayout.setVisibility(View.GONE);
            quotationView.awardedLayout.setVisibility(View.VISIBLE);
            quotationView.quotationLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGGrey3));
            quotationView.imgQuotation.setImageResource(R.mipmap.icon_provider_inactive);
            String award = "You have awarded " + mJob.getUserProfile().getUser().getFullName() + " to the job.";
            quotationView.lblQuotationCount.setText(award);
            quotationView.lblQuotationCount.setOnClickListener(this);
        } else
            quotationView.notifyDataChanged(isDeleted, mProposals);
        quotationView.setOnItemClickListener(new JobStatusSummaryQuotationView.OnItemClickListener() {
            @Override
            public void onItemClick(View item) {
                if (item.getId() == R.id.btn_view_quotation) {
                    if (mProposals.size() == 0 || mProposals == null) {
                        Intent intent = new Intent(mContext, InviteProviderActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(mContext, ServiceProviderActivity.class);
                        startActivity(intent);
                    }
                } else if (item.getId() == R.id.lbl_quotation_count) {
                    Intent intent = new Intent(mContext, ServiceProviderActivity.class);
                    startActivity(intent);
                }
            }
        });
        quotationLayout.addView(quotationView);
    }

    private void getProposalsByJob() {
        progressDialog = createProgressDialog(mContext);
        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        Call<JGGProposalResponse> call = apiManager.getProposalsByJob(mJob.getID(), 0, 40);
        call.enqueue(new Callback<JGGProposalResponse>() {
            @Override
            public void onResponse(Call<JGGProposalResponse> call, Response<JGGProposalResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mProposals = response.body().getValue();
                        for (JGGProposalModel p : mProposals) {
                            if (p.getStatus() == confirmed)
                                selectedProposal = p;
                        }
                        onRefreshView();
                    } else {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode  = response.code();
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGProposalResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteJob(String reason) {
        mReason = reason;
        progressDialog = createProgressDialog(mContext);

        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        String jobID = mJob.getID();
        Call<JGGBaseResponse> call = apiManager.deleteJob(jobID, reason);
        call.enqueue(new Callback<JGGBaseResponse>() {
            @Override
            public void onResponse(Call<JGGBaseResponse> call, Response<JGGBaseResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        ((ProgressJobSummaryActivity)mContext).deleteJobFinished();
                        isDeleted = true;
                        onRefreshView();
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
                Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void onShowReviewFragment() {
        mActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.app_detail_container, new JobReviewFragment())
                .addToBackStack("review_fragment")
                .commit();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_posted_job) {
            mActivity.actionbarView.setStatus(JGGActionbarView.EditStatus.JOB_DETAILS, AppointmentType.UNKNOWN);
            mActivity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.app_detail_container, new NewJobDetailsFragment())
                    .addToBackStack("job_detail_fragment")
                    .commit();
        } else if (view.getId() == R.id.btn_view_proposal) {
            Intent intent = new Intent(mContext, PostProposalActivity.class);
            intent.putExtra(EDIT_STATUS, "ACCEPTED");
            startActivity(intent);
        } else if (view.getId() == R.id.btn_chat) {

        } else if (view.getId() == R.id.lbl_quotation_count) {
            Intent intent = new Intent(mContext, ServiceProviderActivity.class);
            startActivity(intent);
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
        mActivity = ((ProgressJobSummaryActivity) mContext);
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
