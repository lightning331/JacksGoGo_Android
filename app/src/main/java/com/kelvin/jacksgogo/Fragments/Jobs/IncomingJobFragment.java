package com.kelvin.jacksgogo.Fragments.Jobs;

import android.app.AlertDialog;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.Activities.Jobs.IncomingJobActivity;
import com.kelvin.jacksgogo.Activities.Jobs.JobReportActivity;
import com.kelvin.jacksgogo.Activities.Jobs.PostProposalActivity;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobStatusSummaryCancelled;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobStatusSummaryConfirmedView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobStatusSummaryHeaderView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobStatusSummaryPaymentView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobStatusSummaryQuotationView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobStatusSummaryReview;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobStatusSummaryTipView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobStatusSummaryWorkProgressView;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.CustomView.Views.JGGAlertView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentActivityModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGContractModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGReportResultModel;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserProfileModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGBaseResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGPostAppResponse;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.Global.ContractStatus.started;
import static com.kelvin.jacksgogo.Utils.Global.EDIT;
import static com.kelvin.jacksgogo.Utils.Global.EDIT_STATUS;
import static com.kelvin.jacksgogo.Utils.Global.INVITE_PROPOSAL;
import static com.kelvin.jacksgogo.Utils.Global.JGGProposalStatus;
import static com.kelvin.jacksgogo.Utils.Global.JGGUserType;
import static com.kelvin.jacksgogo.Utils.Global.JGGUserType.PROVIDER;
import static com.kelvin.jacksgogo.Utils.Global.JGG_USERTYPE;
import static com.kelvin.jacksgogo.Utils.Global.JobReportStatus.pending;
import static com.kelvin.jacksgogo.Utils.Global.MY_PROPOSAL;
import static com.kelvin.jacksgogo.Utils.Global.createProgressDialog;
import static com.kelvin.jacksgogo.Utils.Global.setBoldText;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getDayMonthYear;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getTimePeriodString;

public class IncomingJobFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Context mContext;
    private IncomingJobActivity mActivity;

    @BindView(R.id.lbl_posted_time) TextView lblPostedTime;
    @BindView(R.id.lbl_next_step_title) TextView lblPostedJob;
    @BindView(R.id.btn_posted_job) LinearLayout btnPostedJob;

    @BindView(R.id.bottom_layout) LinearLayout bottomLayout;
    @BindView(R.id.chat_layout) LinearLayout chatLayout;
    @BindView(R.id.accept_layout) LinearLayout acceptLayout;
    @BindView(R.id.btn_invite_reject) TextView btnReject;
    @BindView(R.id.btn_invite_accept) TextView btnAccept;

    @BindView(R.id.img_proposal) ImageView imgProposal;
    @BindView(R.id.img_avatar) RoundedImageView imgClient;
    @BindView(R.id.lbl_provider_name) TextView lblClientName;
    @BindView(R.id.btn_view_proposal) LinearLayout btnViewProposal;
    @BindView(R.id.btn_chat) RelativeLayout btnChat;

    @BindView(R.id.job_main_quotation_layout) LinearLayout quotationLayout;
    @BindView(R.id.job_main_cancelled_layout) LinearLayout cancelledLayout;
    @BindView(R.id.job_main_confirmed_layout) LinearLayout confirmedLayout;
    @BindView(R.id.job_main_work_progress_layout) LinearLayout progressLayout;
    @BindView(R.id.job_main_header_layout) LinearLayout headerLayout;
    @BindView(R.id.job_main_tip_layout) LinearLayout tipLayout;
    @BindView(R.id.job_main_payment_layout) LinearLayout paymentLayout;
    @BindView(R.id.job_main_given_review_layout) LinearLayout givenReviewLayout;
    @BindView(R.id.job_main_get_review_layout) LinearLayout getReviewLayout;

    private JobStatusSummaryQuotationView quotationView;
    private JobStatusSummaryCancelled cancelledView;
    private JobStatusSummaryConfirmedView confirmedView;
    private JobStatusSummaryWorkProgressView progressView;
    private JobStatusSummaryHeaderView headerView;
    private JobStatusSummaryTipView tipView;
    private JobStatusSummaryPaymentView paymentView;
    private JobStatusSummaryReview givenReviewView;
    private JobStatusSummaryReview getReviewView;

    private AlertDialog alertDialog;
    private ProgressDialog progressDialog;

    private JGGUserProfileModel currentUser;
    private ArrayList<JGGAppointmentActivityModel> mActivities = new ArrayList<>();
    private JGGAppointmentModel mJob;
    private JGGProposalModel mProposal;
    private JGGContractModel mContract;
    private String clientName;

    public IncomingJobFragment() {
    }

    public static IncomingJobFragment newInstance(String param1, String param2) {
        IncomingJobFragment fragment = new IncomingJobFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_incoming_job, container, false);
        ButterKnife.bind(this, view);

        initView(view);
        onRefreshView();
        return view;
    }

    public void setAppointmentActivities(ArrayList<JGGAppointmentActivityModel> activities,
                                         ArrayList<JGGProposalModel> proposals,
                                         JGGContractModel contract) {
        currentUser = JGGAppManager.getInstance().getCurrentUser();
        mJob = JGGAppManager.getInstance().getSelectedAppointment();
        mActivities = activities;
        mContract = contract;
        for (JGGProposalModel p : proposals) {
            if (p.getUserProfileID().equals(currentUser.getID())) {
                mProposal = p;
                JGGAppManager.getInstance().setSelectedProposal(mProposal);
            }
        }
    }

    private void onShowReviewFragment() {
        mActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.app_detail_container, new JobReviewFragment())
                .addToBackStack("review_fragment")
                .commit();
    }

    private void onPostedJob() {
        mActivity.actionbarView.setStatus(JGGActionbarView.EditStatus.JOB_DETAILS, Global.AppointmentType.UNKNOWN);
        mActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.incoming_container, new NewJobDetailsFragment())
                .addToBackStack("job_detail_fragment")
                .commit();
    }

    private void initView(View view) {
        cancelledView = new JobStatusSummaryCancelled(mContext, JGGUserType.PROVIDER);
        quotationView = new JobStatusSummaryQuotationView(mContext, JGGUserType.PROVIDER);
        confirmedView = new JobStatusSummaryConfirmedView(mContext, JGGUserType.PROVIDER);
        progressView = new JobStatusSummaryWorkProgressView(mContext, JGGUserType.PROVIDER);
        headerView = new JobStatusSummaryHeaderView(mContext, JGGUserType.PROVIDER);
        tipView = new JobStatusSummaryTipView(mContext, JGGUserType.PROVIDER);
        paymentView = new JobStatusSummaryPaymentView(mContext, JGGUserType.PROVIDER);

        getReviewView = new JobStatusSummaryReview(mContext);
        givenReviewView = new JobStatusSummaryReview(mContext);
    }

    private void resetViews() {
        quotationLayout.removeAllViews();
        confirmedLayout.removeAllViews();
        progressLayout.removeAllViews();
        cancelledLayout.removeAllViews();
        headerLayout.removeAllViews();
        tipLayout.removeAllViews();
        paymentLayout.removeAllViews();
        getReviewLayout.removeAllViews();
        givenReviewLayout.removeAllViews();
    }

    private void onRefreshView() {
        resetViews();

        if (mProposal == null) {
            bottomLayout.setVisibility(View.GONE);
        } else {

        }

        if (mActivities.size() > 0) {
            for (int i = mActivities.size() - 1; i >= 0; i --) {
                JGGAppointmentActivityModel activity = mActivities.get(i);
                // TODO - update required
                //if (activity.getReferenceID().equals(mProposal.getID()))
                //if (activity.getReferenceID().equals(currentUser.getID()))
                    switch (activity.getStatus()) {
                        case none:
                            break;
                        case unknown:
                            break;
                        case job_created:
                            break;
                        case job_edited:    // TODO - Need to fix
                            showJobChanged(activity);
                            break;
                        case job_closed:
                            showJobClosed(activity);
                            break;
                        case job_confirmed:
                            // TODO - need to check the activity status
                            showConfirmed(activity);
                            break;
                        case job_flagged:
                            break;
                        case job_deleted:
                            showDeletedJob();
                            break;
                        case job_reported:
                            break;
                        case job_awarded: // 107
                            // Set More button
                            //mActivity.setStatus(mProposal);s
                            showProposalAccepted(activity);
                            break;
                        case job_rejected:
                            break;

                        case service_created:
                        case service_edited:
                        case service_closed:
                        case service_confirmed:
                        case service_flagged:
                        case service_deleted:
                        case service_reported:
                        case service_reschedule_requested:
                        case service_reschedule_declined:
                        case service_reschedule_agreed:
                        case service_rescheduled:
                            break;

                        case quotation_created:
                        case quotation_edited:
                        case quotation_closed:
                        case quotation_confirmed:
                        case quotation_flagged:
                        case quotation_deleted:
                        case quotation_reported:
                            break;

                        case proposal_sent: // 400
                            showSentProposal(activity);
                            setWaitingClientDecision();
                            break;
                        case proposal_edited:  // 401
                            break;
                        case proposal_rejected: // 402
                            showDeclineProposal(activity);
                            break;
                        case proposal_withdraw:
                            showDeletedJob();
                            mActivity.setStatus(mProposal);
                            break;
                        case proposal_approved: // 404
                            break;
                        case proposal_flagged:
                        case proposal_deleted:
                            break;

                        case invite_sent: // 407
                            showInvited(activity);
                            break;
                        case invite_accepted: // 408
                            setWaitingClientDecision();
                            break;
                        case invite_rejected:
                            showDeclineInvite(activity);
                            break;

                        case contract_created: // 500
                            showProposalAccepted(activity);
                            break;
                        case contract_started: // 501
                            if (mContract.getStatus() == started) {
                                showStartedWork(activity);
                            } else {
                                showJobReport(activity);
                            }
                            break;
                        case contract_paused:
                        case contract_held:
                            break;
                        case contract_end:
                            showOfficialComplete(activity);
                            break;
                        case contract_flagged:
                            break;
                        case contract_award:

                            break;
                        case contract_confirmed:
                            showConfirmed(activity);
                            setReadyToStartStatus(activity);
                            break;

                        case result_reported:  // 600
                            if (mContract.getReportStatus() != null) {
                                if (mContract.getReportStatus() == pending) {
                                    showWaitingComplete(activity);
                                } else {
                                    showComplete(activity);
                                    showHeaderView();
                                }
                            }
                            break;
                        case result_accepted:
                            showVerifiedWork(activity);
                            break;
                        case result_rejected:
                            break;

                        case invoice_sent:
                            break;
                        case invoice_approved:
                            break;
                        case give_tip:
                            showTipView();
                            break;

                        case client_feedback:
                            showGivenReview(activity);
                            break;
                        case provider_feedback:
                            showGetReview(activity);
                            break;
                    }
            }
        }

    }

    // TODO - 1-1. You sent in a proposal to this job
    private void showSentProposal(JGGAppointmentActivityModel activity) {
        Date submitOn = activity.getActiveOn();
        String submitTime = getDayMonthYear(submitOn) + " " + getTimePeriodString(submitOn);
        imgProposal.setImageResource(R.mipmap.icon_posted_inactive);
        lblPostedTime.setText(submitTime);
        lblPostedJob.setText(R.string.sent_proposal_title);
        lblPostedJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPostedJob();
            }
        });
    }

    // TODO - 1-2. Awaiting client acceptance
    private void setWaitingClientDecision() {
        chatLayout.setVisibility(View.VISIBLE);
        acceptLayout.setVisibility(View.GONE);

        // update post view
        imgProposal.setImageResource(R.mipmap.icon_posted_inactive);
        lblPostedJob.setText(R.string.sent_proposal_title);

        quotationLayout.removeAllViews();
        quotationView.imgQuotation.setImageResource(R.mipmap.icon_provider_cyan);
        quotationView.quotationLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGCyan));

        quotationView.lblTime.setVisibility(View.GONE);
        quotationView.lblTitle.setVisibility(View.VISIBLE);
        quotationView.lblTitle.setText(R.string.waiting_client_decision);

        quotationView.lblAwardTime.setVisibility(View.GONE);
        quotationView.llAwardQuote.setVisibility(View.GONE);
        quotationView.btnViewQuotation.setVisibility(View.GONE);
        quotationView.ll_award.setVisibility(View.GONE);

        quotationLayout.addView(quotationView);
    }

    // TODO - 1-3. You were invited
    private void showInvited(JGGAppointmentActivityModel activity) {
        bottomLayout.setVisibility(View.VISIBLE);
        chatLayout.setVisibility(View.GONE);
        acceptLayout.setVisibility(View.VISIBLE);

        Date submitOn = activity.getActiveOn();
        String submitTime = getDayMonthYear(submitOn) + " " + getTimePeriodString(submitOn);
        lblPostedTime.setText(submitTime);
        imgProposal.setImageResource(R.mipmap.icon_posted_orange);
        lblPostedJob.setText(R.string.invited_proposal_title);
        lblPostedJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPostedJob();
            }
        });
    }

    // TODO - 2. Congratulations! Your proposal was accepted. (quotation view)
    private void showProposalAccepted(JGGAppointmentActivityModel activity) {
        // Quotation View
        quotationLayout.removeAllViews();

        quotationView.quotationLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGCyan));
        quotationView.imgQuotation.setImageResource(R.mipmap.icon_provider_cyan);

        Date submitOn = activity.getActiveOn();
        String submitTime = getDayMonthYear(submitOn) + " " + getTimePeriodString(submitOn);

        quotationView.lblTime.setVisibility(View.GONE);
        quotationView.lblTitle.setVisibility(View.GONE);

        quotationView.lblAwardTime.setVisibility(View.VISIBLE);
        quotationView.llAwardQuote.setVisibility(View.VISIBLE);
        quotationView.lblQuotationCount.setVisibility(View.VISIBLE);

        quotationView.lblAwardTime.setText(submitTime);
        quotationView.lblQuotationCount.setText(R.string.proposal_accepted_title);
        quotationView.imgRightButton.setVisibility(View.GONE);

        quotationView.btnViewQuotation.setVisibility(View.GONE);
        quotationView.ll_award.setVisibility(View.VISIBLE);

        quotationView.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAcceptAward();
            }
        });
        quotationView.btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRejectAward();
            }
        });

        quotationLayout.addView(quotationView);
    }

    // TODO - 2-2. Your propoasl has been declined. (quotation view)
    // TODO - Edit Your Proposal
    private void showDeclineProposal(JGGAppointmentActivityModel activity) {
        quotationLayout.removeAllViews();

        Date submitOn = activity.getActiveOn();
        String submitTime = getDayMonthYear(submitOn) + " " + getTimePeriodString(submitOn);

        quotationView.imgQuotation.setImageResource(R.mipmap.icon_provider_cyan);
        quotationView.quotationLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGCyan));

        quotationView.lblTime.setVisibility(View.GONE);
        quotationView.lblTitle.setVisibility(View.GONE);

        quotationView.lblAwardTime.setVisibility(View.VISIBLE);
        quotationView.llAwardQuote.setVisibility(View.VISIBLE);
        quotationView.lblAwardTime.setText(submitTime);
        quotationView.lblQuotationCount.setText(R.string.proposal_declined);
        quotationView.imgRightButton.setVisibility(View.GONE);

        quotationView.btnViewQuotation.setVisibility(View.VISIBLE);
        quotationView.btnViewQuotation.setBackgroundResource(R.drawable.cyan_border_background);
        quotationView.btnViewQuotation.setTextColor(ContextCompat.getColor(getContext(), R.color.JGGCyan));
        quotationView.btnViewQuotation.setText(R.string.edit_your_title);
        quotationView.btnViewQuotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PostProposalActivity.class);
                intent.putExtra(EDIT_STATUS, EDIT);
                startActivity(intent);
            }
        });

        quotationView.ll_award.setVisibility(View.GONE);

        quotationLayout.addView(quotationView);
    }

    // TODO - 2.3. Alicia has made some changes.
    private void showJobChanged(JGGAppointmentActivityModel activity) {
        quotationLayout.removeAllViews();

        Date submitOn = activity.getActiveOn();
        String submitTime = getDayMonthYear(submitOn) + " " + getTimePeriodString(submitOn);
        clientName = mJob.getUserProfile().getUser().getFullName();

        quotationView.imgQuotation.setImageResource(R.mipmap.icon_provider_cyan);
        quotationView.quotationLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGCyan));

        quotationView.lblTime.setVisibility(View.GONE);
        quotationView.lblTitle.setVisibility(View.GONE);


        quotationView.lblAwardTime.setVisibility(View.VISIBLE);
        quotationView.llAwardQuote.setVisibility(View.VISIBLE);
        quotationView.lblAwardTime.setText(submitTime);
        String description = clientName + " has made some changes.";
        quotationView.lblQuotationCount.setText("");
        quotationView.lblQuotationCount.append(setBoldText(description));
        quotationView.lblQuotationCount.append("\nYou may want to update your proposal if necessary.");

        quotationView.imgRightButton.setVisibility(View.GONE);

        quotationView.btnViewQuotation.setVisibility(View.VISIBLE);
        quotationView.btnViewQuotation.setBackgroundResource(R.drawable.cyan_border_background);
        quotationView.btnViewQuotation.setTextColor(ContextCompat.getColor(getContext(), R.color.JGGCyan));
        quotationView.btnViewQuotation.setText(R.string.edit_your_title);
        quotationView.btnViewQuotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PostProposalActivity.class);
                intent.putExtra(EDIT_STATUS, EDIT);
                startActivity(intent);
            }
        });
        quotationView.ll_award.setVisibility(View.GONE);

        quotationLayout.addView(quotationView);
    }

    // TODO - 2-4. decline invitation
    private void showDeclineInvite(JGGAppointmentActivityModel activity) {
        chatLayout.setVisibility(View.VISIBLE);
        acceptLayout.setVisibility(View.GONE);

        // update post view
        imgProposal.setImageResource(R.mipmap.icon_posted_inactive);
        lblPostedJob.setText(R.string.sent_proposal_title);

        quotationLayout.removeAllViews();

        quotationView.imgQuotation.setImageResource(R.mipmap.icon_provider_cyan);
        quotationView.quotationLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGCyan));

        Date submitOn = activity.getActiveOn();
        String submitTime = getDayMonthYear(submitOn) + " " + getTimePeriodString(submitOn);

        quotationView.lblTime.setVisibility(View.GONE);
        quotationView.lblTitle.setVisibility(View.GONE);

        quotationView.lblAwardTime.setVisibility(View.VISIBLE);
        quotationView.llAwardQuote.setVisibility(View.VISIBLE);
        quotationView.lblAwardTime.setText(submitTime);
        quotationView.lblQuotationCount.setText(R.string.declined_invite);

        quotationView.imgRightButton.setVisibility(View.GONE);

        quotationView.btnViewQuotation.setVisibility(View.GONE);
        quotationView.ll_award.setVisibility(View.GONE);

        quotationLayout.addView(quotationView);
    }

    // TODO - 2-5. Job Closed (quotation view)
    // TODO - Your proposal has been declined.
    private void showJobClosed(JGGAppointmentActivityModel activity) {

        clientName = mJob.getUserProfile().getUser().getFullName();

        quotationView.imgQuotation.setImageResource(R.mipmap.icon_provider_cyan);
        quotationView.quotationLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGCyan));

        Date submitOn = activity.getActiveOn();
        String submitTime = getDayMonthYear(submitOn) + " " + getTimePeriodString(submitOn);

        quotationView.lblTime.setVisibility(View.VISIBLE);
        quotationView.lblTitle.setVisibility(View.VISIBLE);
        quotationView.lblTime.setText(submitTime);
        quotationView.lblTitle.setText("");
        String description = "Job Closed. \n" + clientName;
        quotationView.lblTitle.append(description);
        quotationView.lblTitle.append(" has awarded the job to someone else.");
    }


    // TODO - 3-1. Appointment confirmed (confirm)
    private void showConfirmed(JGGAppointmentActivityModel activity) {
        quotationView.ll_award.setVisibility(View.GONE);
        quotationView.imgQuotation.setImageResource(R.mipmap.icon_provider_inactive);
        quotationView.quotationLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGGrey3));

        // Confirmed View
        confirmedLayout.removeAllViews();

        confirmedView.confirmedLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGCyan));
        confirmedView.imgConfirmed.setImageResource(R.mipmap.icon_appointment_orange);

        Date submitOn = activity.getActiveOn();
        String submitTime = getDayMonthYear(submitOn) + " " + getTimePeriodString(submitOn);
        confirmedView.lblConfirmedTime.setText(submitTime);
        confirmedView.lblConfirmedTitle.setText(R.string.appointment_confirmed);
        confirmedView.lblConfirmedDesc.setVisibility(View.GONE);
        confirmedView.btnSetAppDate.setVisibility(View.GONE);

        confirmedLayout.addView(confirmedView);
    }


    // TODO - 3-2. Job cancelled (cancel)
    private void showDeletedJob() {

        cancelledLayout.removeAllViews();

        // Cancelled View
        Picasso.with(mContext).load(mJob.getUserProfile().getUser().getPhotoURL())
                .placeholder(R.mipmap.icon_profile)
                .into(cancelledView.imgAvatar);

        cancelledView.lblCancelTitle.setText("");
        cancelledView.lblCancelTitle.append(setBoldText(mJob.getUserProfile().getUser().getFullName()));
        cancelledView.lblCancelTitle.append(" has cancelled the job.");

        cancelledView.lblComment.setText(mJob.getReason());

        cancelledLayout.addView(cancelledView);
    }
    // TODO - 3-3. Pending Scheduling... (work-progress)


    // TODO - 4-1. I am ready to start (work-progress)
    private void setReadyToStartStatus(JGGAppointmentActivityModel activity) {
        chatLayout.setVisibility(View.VISIBLE);
        acceptLayout.setVisibility(View.GONE);

        Date submitOn = activity.getActiveOn();
        String submitTime = getDayMonthYear(submitOn) + " " + getTimePeriodString(submitOn);
        clientName = mJob.getUserProfile().getUser().getFullName();

        confirmedView.confirmedLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGGrey3));
        confirmedView.imgConfirmed.setImageResource(R.mipmap.icon_appointment_inactive);

        // Progress View
        progressLayout.removeAllViews();

        progressView.imgStartWork.setImageResource(R.mipmap.icon_startwork_orange);
        progressView.startWorkLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGCyan));

        progressView.lblStartTime.setText(submitTime);

        progressView.lblStartedWork.setText("");
        progressView.lblStartedWork.append("Take note of ");
        progressView.lblStartedWork.append(setBoldText(clientName));
        progressView.lblStartedWork.append("'s requests (if any) and top on the button to start when you are ready.");
        progressView.btnStart.setVisibility(View.VISIBLE);
        progressView.btnStart.setText(R.string.ready_start);
        progressView.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStartContract();
            }
        });

        progressView.billableLayout.setVisibility(View.GONE);

        progressLayout.addView(progressView);
    }

    // TODO - 4-2. Work in progress... (work-progress)
    // TODO - Job Report ...
    private void showJobReport(JGGAppointmentActivityModel activity) {
        chatLayout.setVisibility(View.VISIBLE);
        acceptLayout.setVisibility(View.GONE);

        Date submitOn = activity.getActiveOn();
        String submitTime = getDayMonthYear(submitOn) + " " + getTimePeriodString(submitOn);

        confirmedView.confirmedLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGGrey3));
        confirmedView.imgConfirmed.setImageResource(R.mipmap.icon_appointment_inactive);

        // Progress View
        progressLayout.removeAllViews();
        progressView.imgStartWork.setImageResource(R.mipmap.icon_startwork_orange);
        progressView.startWorkLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGCyan));

        progressView.lblStartTime.setText(submitTime);
        progressView.lblStartedWork.setText(R.string.work_in_progress);
        progressView.lblReportDesc.setText(R.string.report_description);

        progressView.btnStart.setVisibility(View.VISIBLE);
        progressView.btnStart.setText(R.string.job_report);
        progressView.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onShowReportActivity();
            }
        });

        progressView.billableLayout.setVisibility(View.GONE);

        progressLayout.addView(progressView);
    }

    // TODO - 4-3 You started the work.
    private void showStartedWork(JGGAppointmentActivityModel activity) {
        chatLayout.setVisibility(View.VISIBLE);
        acceptLayout.setVisibility(View.GONE);

        Date submitOn = activity.getActiveOn();
        String submitTime = getDayMonthYear(submitOn) + " " + getTimePeriodString(submitOn);

        confirmedView.confirmedLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGGrey3));
        confirmedView.imgConfirmed.setImageResource(R.mipmap.icon_appointment_inactive);

        // Progress View
        progressLayout.removeAllViews();
        progressView.imgStartWork.setImageResource(R.mipmap.icon_startwork_inactive);
        progressView.startWorkLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGGrey3));

        progressView.lblStartTime.setText(submitTime);
        progressView.lblStartedWork.setText(R.string.started_work);
        progressView.lblReportDesc.setVisibility(View.GONE);

        progressView.btnStart.setVisibility(View.GONE);
        progressView.billableLayout.setVisibility(View.GONE);

        progressLayout.addView(progressView);
    }

    // TODO - 4-4. Appointment to be set. (work-progress)
    // TODO - Set appointment Date


    // TODO - 4-5. You started work. (work-progress)


    // TODO - 5-1. You completed the work. (payment-view)
    private void showComplete(JGGAppointmentActivityModel activity) {
        chatLayout.setVisibility(View.VISIBLE);
        acceptLayout.setVisibility(View.GONE);

        progressView.btnStart.setVisibility(View.GONE);
        progressView.imgStartWork.setImageResource(R.mipmap.icon_startwork_inactive);
        progressView.startWorkLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGGrey3));

        Date submitOn = activity.getActiveOn();
        String submitTime = getDayMonthYear(submitOn) + " " + getTimePeriodString(submitOn);

        paymentLayout.removeAllViews();

        paymentView.imgDone.setImageResource(R.mipmap.icon_verified_orange);
        paymentView.dotLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGCyan));

        paymentView.firstLayout.setVisibility(View.GONE);
        paymentView.secondLayout.setVisibility(View.GONE);

        paymentView.txtThirdTime.setText(submitTime);
        paymentView.txtThirdDescription.setText(R.string.completed_work);

        paymentView.btnReport.setVisibility(View.GONE);
        paymentLayout.addView(paymentView);
    }

    // TODO - 5.1-1. You completed the work and waiting verify of
    private void showWaitingComplete(JGGAppointmentActivityModel activity) {
        chatLayout.setVisibility(View.VISIBLE);
        acceptLayout.setVisibility(View.GONE);

        Date submitOn = activity.getActiveOn();
        String submitTime = getDayMonthYear(submitOn) + " " + getTimePeriodString(submitOn);

        clientName = mJob.getUserProfile().getUser().getFullName();

        paymentLayout.removeAllViews();

        paymentView.imgDone.setImageResource(R.mipmap.icon_verified_orange);
        paymentView.dotLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGCyan));

        paymentView.firstLayout.setVisibility(View.GONE);
        paymentView.secondLayout.setVisibility(View.GONE);

        paymentView.txtThirdTime.setText(submitTime);
        paymentView.txtThirdDescription.setText("");
        paymentView.txtThirdDescription.append("You completed the work and waiting verify of ");
        paymentView.txtThirdDescription.append(setBoldText(clientName));

        paymentView.btnReport.setVisibility(View.GONE);
        paymentLayout.addView(paymentView);
    }

    // TODO - 5-2. Client has verified work
    private void showVerifiedWork(JGGAppointmentActivityModel activity) {
        paymentView.secondLayout.setVisibility(View.VISIBLE);

        Date submitOn = activity.getActiveOn();
        String submitTime = getDayMonthYear(submitOn) + " " + getTimePeriodString(submitOn);
        clientName = mJob.getUserProfile().getUser().getFullName();

        paymentView.txtSecondTime.setText(submitTime);

        paymentView.txtSecondDescription.setText("");
        paymentView.txtSecondDescription.append(setBoldText(clientName));
        paymentView.txtSecondDescription.append(" verified the job done.");
    }

    // TODO - 5-3 Payment is released. Job officially completed.
    private void showOfficialComplete(JGGAppointmentActivityModel activity) {
        paymentView.firstLayout.setVisibility(View.VISIBLE);

        Date submitOn = activity.getActiveOn();
        String submitTime = getDayMonthYear(submitOn) + " " + getTimePeriodString(submitOn);

        paymentView.txtFirstTime.setText(submitTime);
        paymentView.txtFirstDescription.setText(R.string.official_complete);
    }

    // TODO - show header view (job report, invoice)
    private void showHeaderView() {
        clientName = mJob.getUserProfile().getUser().getFullName();

        headerLayout.removeAllViews();

        headerView.reportLayout.setVisibility(View.VISIBLE);
        headerView.invoiceLayout.setVisibility(View.VISIBLE);
        headerView.reviewLayout.setVisibility(View.GONE);
        headerView.tipLayout.setVisibility(View.GONE);
        headerView.rehireLayout.setVisibility(View.GONE);

        headerView.reportLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, JobReportActivity.class);
                intent.putExtra(JGG_USERTYPE, PROVIDER.toString());
                intent.putExtra("work_start", false);
                mActivity.startActivity(intent);
            }
        });

        String verifyDay = "3";
        String description = clientName + " will have "
                + verifyDay + " days to verify the work done.";
        headerView.txtHeader.setText(setBoldText(description));

        headerLayout.addView(headerView);
    }

    // TODO - show get review
    private void showGetReview(JGGAppointmentActivityModel activity) {
        getReviewLayout.removeAllViews();

        Date submitOn = activity.getActiveOn();
        String submitTime = getDayMonthYear(submitOn) + " " + getTimePeriodString(submitOn);
        clientName = mJob.getUserProfile().getUser().getFullName();

        getReviewView.lblReviewDate.setText(submitTime);
        getReviewView.lblReviewTitle.setText("");
        getReviewView.lblReviewTitle.append(setBoldText(clientName));
        getReviewView.lblReviewTitle.append(" has given you a review.");

        getReviewView.btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onShowReviewFragment();
            }
        });

        getReviewLayout.addView(getReviewView);
    }

    private void showGivenReview(JGGAppointmentActivityModel activity) {
        givenReviewLayout.removeAllViews();

        Date submitOn = activity.getActiveOn();
        String submitTime = getDayMonthYear(submitOn) + " " + getTimePeriodString(submitOn);
        clientName = mJob.getUserProfile().getUser().getFullName();

        givenReviewView.lblReviewDate.setText(submitTime);
        givenReviewView.lblReviewTitle.setText("");
        givenReviewView.lblReviewTitle.append("You have given ");
        givenReviewView.lblReviewTitle.append(setBoldText(clientName));
        givenReviewView.lblReviewTitle.append(" a review.");

        givenReviewView.btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onShowReviewFragment();
            }
        });

        givenReviewLayout.addView(givenReviewView);
    }
    // TODO - show tip view
    private void showTipView() {
        tipLayout.removeAllViews();
        tipLayout.addView(tipView);
    }


    private void onShowReportActivity() {
        // TODO - create global ReportResultModel
        JGGReportResultModel resultModel = new JGGReportResultModel();
        resultModel.setContractID(mContract.getID());
        resultModel.setContractModel(mContract);
        JGGAppManager.getInstance().setReportResultModel(resultModel);

        Intent intent = new Intent(mContext, JobReportActivity.class);
        intent.putExtra(JGG_USERTYPE, PROVIDER.toString());
        intent.putExtra("work_start", true);
        mActivity.startActivity(intent);
    }

    // TODO - Accept Invitation
    private void onAcceptInvitation() {
        mProposal.setStatus(JGGProposalStatus.open);
        Intent intent = new Intent(mContext, PostProposalActivity.class);
        intent.putExtra(EDIT_STATUS, INVITE_PROPOSAL);
        startActivity(intent);
    }

    // TODO - Reject Invitation
    private void onRejectInvitation() {
        progressDialog = createProgressDialog(mContext);
        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        Call<JGGBaseResponse> call = apiManager.rejectInvite(mProposal.getID());
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
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGBaseResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // TODO - Accept Offer
    private void onAcceptAward() {
        progressDialog = createProgressDialog(mContext);
        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        Call<JGGPostAppResponse> call = apiManager.acceptAward(mJob.getID());
        call.enqueue(new Callback<JGGPostAppResponse>() {
            @Override
            public void onResponse(Call<JGGPostAppResponse> call, Response<JGGPostAppResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {

                    } else {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGPostAppResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // TODO - Reject Offer
    private void onRejectAward() {
        progressDialog = createProgressDialog(mContext);
        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        Call<JGGPostAppResponse> call = apiManager.rejectAward(mJob.getID());
        call.enqueue(new Callback<JGGPostAppResponse>() {
            @Override
            public void onResponse(Call<JGGPostAppResponse> call, Response<JGGPostAppResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {

                    } else {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGPostAppResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // TODO - Ready to start work
    private void onStartContract() {
        progressDialog = createProgressDialog(mContext);

        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        String contractID = mContract.getID();
        Call<JGGPostAppResponse> call = apiManager.startContract(contractID);
        call.enqueue(new Callback<JGGPostAppResponse>() {
            @Override
            public void onResponse(Call<JGGPostAppResponse> call, Response<JGGPostAppResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        onShowReportActivity();
                    } else {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGPostAppResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onShowRejectAlertDialog() {
        JGGAlertView builder = new JGGAlertView(mContext,
                mContext.getResources().getString(R.string.alert_reject_title),
                "",
                false,
                mContext.getResources().getString(R.string.alert_cancel),
                R.color.JGGCyan,
                R.color.JGGCyan10Percent,
                mContext.getResources().getString(R.string.alert_reject_ok),
                R.color.JGGCyan);
        alertDialog = builder.create();
        builder.setOnItemClickListener(new JGGAlertView.OnItemClickListener() {
            @Override
            public void onDoneButtonClick(View view) {
                if (view.getId() == R.id.btn_alert_cancel)
                    alertDialog.dismiss();
                else {
                    alertDialog.dismiss();
                    onRejectInvitation();
                }
            }
        });
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    @OnClick(R.id.btn_invite_reject)
    public void onClickInviteReject() {
        onShowRejectAlertDialog();
    }

    @OnClick(R.id.btn_invite_accept)
    public void onClickInviteAccept() {
        onAcceptInvitation();
    }

    @OnClick(R.id.btn_view_proposal)
    public void onClickViewProposal() {
        Intent intent = new Intent(mContext, PostProposalActivity.class);
        intent.putExtra(EDIT_STATUS, MY_PROPOSAL);
        startActivity(intent);
    }

    @OnClick(R.id.btn_chat)
    public void onClickChat() {}

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mActivity = ((IncomingJobActivity) mContext);
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
