package com.kelvin.jacksgogo.Fragments.Jobs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.Jobs.InviteProviderActivity;
import com.kelvin.jacksgogo.Activities.Jobs.JobReportActivity;
import com.kelvin.jacksgogo.Activities.Jobs.OutgoingJobActivity;
import com.kelvin.jacksgogo.Activities.Jobs.PostProposalActivity;
import com.kelvin.jacksgogo.Activities.Jobs.ServiceProviderActivity;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobStatusSummaryCancelled;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobStatusSummaryConfirmedView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobStatusSummaryHeaderView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobStatusSummaryPaymentView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobStatusSummaryQuotationView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobStatusSummaryReview;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobStatusSummaryTipView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobStatusSummaryWorkProgressView;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentActivityModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGContractModel;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserProfileModel;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.kelvin.jacksgogo.Utils.Global.EDIT_STATUS;
import static com.kelvin.jacksgogo.Utils.Global.JGGUserType.CLIENT;
import static com.kelvin.jacksgogo.Utils.Global.JGG_USERTYPE;
import static com.kelvin.jacksgogo.Utils.Global.JobReportStatus.pending;
import static com.kelvin.jacksgogo.Utils.Global.setBoldText;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getDayMonthYear;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getTimePeriodString;

public class OutgoingJobFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Context mContext;
    private OutgoingJobActivity mActivity;

    @BindView(R.id.lbl_posted_time)                 TextView lblPostedTime;
    @BindView(R.id.lbl_next_step_title)             TextView lblPostedJob;
    @BindView(R.id.btn_posted_job)                  LinearLayout btnPostedJob;

    @BindView(R.id.posted_service_chat_layout)      LinearLayout providerDetailLayout;
    @BindView(R.id.img_avatar)                      RoundedImageView imgProvider;
    @BindView(R.id.lbl_provider_name)               TextView lblProviderName;
    @BindView(R.id.btn_view_proposal)               LinearLayout btnViewProposal;
    @BindView(R.id.btn_chat)                        RelativeLayout btnChat;

    @BindView(R.id.job_main_quotation_layout)       LinearLayout quotationLayout;
    @BindView(R.id.job_main_cancelled_layout)       LinearLayout cancelledLayout;
    @BindView(R.id.job_main_confirmed_layout)       LinearLayout confirmedLayout;
    @BindView(R.id.job_main_work_progress_layout)   LinearLayout progressLayout;
    @BindView(R.id.job_main_header_layout)          LinearLayout headerLayout;
    @BindView(R.id.job_main_tip_layout)             LinearLayout tipLayout;
    @BindView(R.id.job_main_payment_layout)         LinearLayout paymentLayout;
    @BindView(R.id.job_main_given_review_layout) LinearLayout givenReviewLayout;
    @BindView(R.id.job_main_get_review_layout) LinearLayout getReviewLayout;

    private JobStatusSummaryQuotationView quotationView;
    private JobStatusSummaryConfirmedView confirmedView;
    private JobStatusSummaryCancelled cancelledView;
    private JobStatusSummaryWorkProgressView progressView;
    private JobStatusSummaryHeaderView headerView;
    private JobStatusSummaryTipView tipView;
    private JobStatusSummaryPaymentView paymentView;
    private JobStatusSummaryReview givenReviewView;
    private JobStatusSummaryReview getReviewView;

    private ProgressDialog progressDialog;
    private View view;

    private JGGAppointmentModel mJob;
    private JGGUserProfileModel currentUser;
    private JGGProposalModel mProposal;
    private JGGContractModel mContract;
    private ArrayList<JGGProposalModel> mProposals = new ArrayList<>();
    private ArrayList<JGGAppointmentActivityModel> mActivities = new ArrayList<>();
    private String mReason;
    private boolean isDeleted;

    public OutgoingJobFragment() {
        // Required empty public constructor
    }

    public static OutgoingJobFragment newInstance(String param1, String param2) {
        OutgoingJobFragment fragment = new OutgoingJobFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_outgoing_job, container, false);
        ButterKnife.bind(this, view);

        mJob = JGGAppManager.getInstance().getSelectedAppointment();
        currentUser = JGGAppManager.getInstance().getCurrentUser();

        initView();
        onRefreshView();
        return view;
    }

    public void setAppointmentActivities(ArrayList<JGGAppointmentActivityModel> activities,
                                         ArrayList<JGGProposalModel> proposals,
                                         JGGContractModel contract) {
        mActivities = activities;
        mProposals = proposals;
        mContract = contract;
    }

    private void initView() {
        providerDetailLayout.setVisibility(View.GONE);
        quotationView = new JobStatusSummaryQuotationView(mContext, CLIENT);
        confirmedView = new JobStatusSummaryConfirmedView(mContext, CLIENT);
        progressView = new JobStatusSummaryWorkProgressView(mContext, CLIENT);
        cancelledView = new JobStatusSummaryCancelled(mContext, CLIENT);
        headerView = new JobStatusSummaryHeaderView(mContext, CLIENT);
        tipView = new JobStatusSummaryTipView(mContext, CLIENT);
        paymentView = new JobStatusSummaryPaymentView(mContext, CLIENT);

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

    public void onRefreshView() {
        resetViews();

        if (mActivities.size() > 0) {
            for (int i = mActivities.size() - 1; i >= 0; i --) {
                JGGAppointmentActivityModel activity = mActivities.get(i);
                // TODO - update required
                    /*if (activity.getReferenceID().equals(mProposal.getID()))
                    if (activity.getReferenceID().equals(currentUser.getID()))*/
                switch (activity.getStatus()) {
                    case none:
                        break;
                    case unknown:
                        break;
                    case job_created:
                        showNewJobRequest(activity);
                        break;
                    case job_edited:
                        break;
                    case job_closed:
                        break;
                    case job_confirmed:
                        showAppointmentConfirmed(activity);
                        break;
                    case job_flagged:
                        break;
                    case job_deleted:
                        break;
                    case job_reported:
                        break;
                    case job_awarded: // 107
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
                        break;
                    case proposal_edited:  //
                        break;
                    case proposal_rejected: // 402
                        break;
                    case proposal_withdraw:
                        break;
                    case proposal_approved: // 404
                        showAwardQuotation(activity);
                        break;
                    case proposal_flagged:
                    case proposal_deleted:
                        break;

                    case invite_sent: // 407
                        break;
                    case invite_accepted:
                        break;
                    case invite_rejected:
                        break;

                    case contract_created: // 500
                        break;
                    case contract_started: // 501
                        showStartedWork(activity);
                        showReportInvoice();
                        break;
                    case contract_paused:
                    case contract_held:
                    case contract_end:
                        showOfficialComplete(activity);
                        break;
                    case contract_flagged:
                        break;

                    case result_reported:  // 600
                        if (mContract.getReportStatus() != null) {
                            if (mContract.getReportStatus() == pending) {
                                showComplete(activity);
                            } else {
                                showCompleteAndVerify(activity);
                            }
                        }
                        break;
                    case result_accepted:
                        showVerifiedWork(activity);
                        break;
                    case result_rejected:
                        break;

                    case invoice_sent:
                    case invoice_approved:
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

        if (mJob.getStatus() == Global.JGGJobStatus.open) {
            if (mProposals.size() == 0) {
                showWaitingProvidersResponse();
            } else {
                showNewQuoations();
            }
        }
    }


    // TODO - 1.1
    private void showNewJobRequest(JGGAppointmentActivityModel activity) {
        Date postOn = activity.getActiveOn();
        String postedTime = getDayMonthYear(postOn) + " " + getTimePeriodString(postOn);

        lblPostedJob.setText(R.string.job_request_posted);
        lblPostedTime.setText(postedTime);
    }
    // TODO - 1.2
    // TODO - 2.1 - You have awarded catherin the job
    private void showAwardQuotation(JGGAppointmentActivityModel activity) {
        // Quotation View
        Date postOn = activity.getActiveOn();
        String postedTime = getDayMonthYear(postOn) + " " + getTimePeriodString(postOn);
        String providerName = mContract.getProposal().getUserProfile().getUser().getEmail();

        quotationLayout.removeAllViews();

        quotationView.imgQuotation.setImageResource(R.mipmap.icon_provider_inactive);
        quotationView.quotationLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGGrey3));

        quotationView.lblTime.setVisibility(View.GONE);
        quotationView.lblTitle.setVisibility(View.GONE);

        quotationView.lblAwardTime.setVisibility(View.VISIBLE);
        quotationView.llAwardQuote.setVisibility(View.VISIBLE);

        quotationView.lblAwardTime.setText(postedTime);
        quotationView.lblQuotationCount.setText("");
        quotationView.lblQuotationCount.append("You have awarded ");
        quotationView.lblQuotationCount.append(setBoldText(providerName));
        quotationView.lblQuotationCount.append(" to the job.");
        quotationView.lblQuotationCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ServiceProviderActivity.class);
                startActivity(intent);
            }
        });

        quotationView.imgRightButton.setVisibility(View.VISIBLE);

        quotationView.btnViewQuotation.setVisibility(View.GONE);
        quotationLayout.addView(quotationView);
    }
    // TODO - 2.2 - Waiting for service provider's to respond...
    private void showWaitingProvidersResponse() {
        quotationLayout.removeAllViews();

        quotationView.imgQuotation.setImageResource(R.mipmap.icon_provider_green);
        quotationView.quotationLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGGreen));

        quotationView.lblTime.setVisibility(View.GONE);
        quotationView.lblTitle.setText(R.string.waiting_service_provider);

        quotationView.lblAwardTime.setVisibility(View.GONE);
        quotationView.llAwardQuote.setVisibility(View.GONE);

        quotationView.btnViewQuotation.setVisibility(View.VISIBLE);
        quotationView.btnViewQuotation.setText(R.string.invite_service_provider);
        quotationView.btnViewQuotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, InviteProviderActivity.class);
                startActivity(intent);
            }
        });

        quotationLayout.addView(quotationView);
    }
    // TODO - 2.3 - You have received 1 new quotation
    private void showNewQuoations() {
        quotationLayout.removeAllViews();

        quotationView.imgQuotation.setImageResource(R.mipmap.icon_provider_green);
        quotationView.quotationLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGGreen));

        quotationView.lblTime.setVisibility(View.GONE);

        quotationView.lblTitle.setText(setBoldText("You have received " + mProposals.size() + " new quotation!"));

        quotationView.lblAwardTime.setVisibility(View.GONE);
        quotationView.llAwardQuote.setVisibility(View.GONE);

        quotationView.btnViewQuotation.setVisibility(View.VISIBLE);
        quotationView.btnViewQuotation.setText(R.string.view_quotation);
        quotationView.btnViewQuotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ServiceProviderActivity.class);
                startActivity(intent);
            }
        });

        quotationLayout.addView(quotationView);
    }
    // TODO - 3.1 - Appointment confirmed
    private void showAppointmentConfirmed(JGGAppointmentActivityModel activity) {
        Date postOn = activity.getActiveOn();
        String postedTime = getDayMonthYear(postOn) + " " + getTimePeriodString(postOn);

        confirmedLayout.removeAllViews();

        confirmedView.confirmedLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGGreen));
        confirmedView.imgConfirmed.setImageResource(R.mipmap.icon_appointment_orange);

        confirmedView.lblConfirmedTime.setText(postedTime);
        confirmedView.lblConfirmedTitle.setText(R.string.appointment_confirmed);
        confirmedView.lblConfirmedDesc.setVisibility(View.VISIBLE);
        confirmedView.lblConfirmedDesc.setText(R.string.appointment_description);
        confirmedView.btnSetAppDate.setVisibility(View.GONE);

        confirmedLayout.addView(confirmedView);
    }
    // TODO - 3.2 - Appointment to be set
    private void showUpdateAppointment(JGGAppointmentActivityModel activity) {
        Date postOn = activity.getActiveOn();
        String postedTime = getDayMonthYear(postOn) + " " + getTimePeriodString(postOn);

        confirmedLayout.removeAllViews();

        confirmedView.confirmedLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGGreen));
        confirmedView.imgConfirmed.setImageResource(R.mipmap.icon_appointment_orange);

        confirmedView.lblConfirmedTime.setText(postedTime);
        confirmedView.lblConfirmedTitle.setText(R.string.set_app_date_title);
        confirmedView.lblConfirmedDesc.setVisibility(View.VISIBLE);
        confirmedView.lblConfirmedDesc.setText(R.string.set_app_date_desc);

        confirmedView.btnSetAppDate.setVisibility(View.VISIBLE);
        confirmedView.btnSetAppDate.setText(R.string.set_appointment_date);
        confirmedView.btnSetAppDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        confirmedLayout.addView(confirmedView);
    }
    // TODO - 3.3 - Pending Setting Appointment...
    private void showPendingAppointment(JGGAppointmentActivityModel activity) {
        Date postOn = activity.getActiveOn();
        String postedTime = getDayMonthYear(postOn) + " " + getTimePeriodString(postOn);

        confirmedLayout.removeAllViews();

        confirmedView.confirmedLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGGreen));
        confirmedView.imgConfirmed.setImageResource(R.mipmap.icon_appointment_orange);

        confirmedView.lblConfirmedTime.setText(postedTime);
        confirmedView.lblConfirmedTitle.setText(R.string.pending_appointment);

        String description = "Pending setting appointment to ";
        String time = "18 Jul, 2017 10:00 AM - 12:30 PM";
        String end = ".\nWaiting for ";
        String providerName = mContract.getProposal().getUserProfile().getUser().getEmail();
        String respone = "'s response";
        confirmedView.lblConfirmedDesc.setVisibility(View.VISIBLE);
        confirmedView.lblConfirmedDesc.setText("");
        confirmedView.lblConfirmedDesc.append(description);
        confirmedView.lblConfirmedDesc.append(time);
        confirmedView.lblConfirmedDesc.append(end);
        confirmedView.lblConfirmedDesc.append(setBoldText(providerName));
        confirmedView.lblConfirmedDesc.append(respone);

        confirmedView.btnSetAppDate.setVisibility(View.VISIBLE);
        confirmedView.btnSetAppDate.setText(R.string.set_appointment_date);
        confirmedView.btnSetAppDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        confirmedLayout.addView(confirmedView);
    }

    // TODO - 3.4 - Having problems
    private void showHavingProblems(JGGAppointmentActivityModel activity) {
        Date postOn = activity.getActiveOn();
        String postedTime = getDayMonthYear(postOn) + " " + getTimePeriodString(postOn);

        confirmedLayout.removeAllViews();

        confirmedView.confirmedLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGGreen));
        confirmedView.imgConfirmed.setImageResource(R.mipmap.icon_appointment_orange);

        confirmedView.lblConfirmedTime.setText(postedTime);
        confirmedView.lblConfirmedTitle.setText(R.string.having_problem);

        String description = "We notice that ";
        String end = " did not complete her job. If you need help, we are a button tap away.";
        String providerName = mContract.getProposal().getUserProfile().getUser().getEmail();
        confirmedView.lblConfirmedDesc.setVisibility(View.VISIBLE);
        confirmedView.lblConfirmedDesc.setText("");
        confirmedView.lblConfirmedDesc.append(description);
        confirmedView.lblConfirmedDesc.append(setBoldText(providerName));
        confirmedView.lblConfirmedDesc.append(end);

        confirmedView.btnSetAppDate.setVisibility(View.VISIBLE);
        confirmedView.btnSetAppDate.setText(R.string.report_to_us);
        confirmedView.btnSetAppDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // show chat view
            }
        });

        confirmedLayout.addView(confirmedView);
    }
    // TODO - 4.1 - Job Cancelled
    private void showCancelled() {
        String providerName = mContract.getProposal().getUserProfile().getUser().getEmail();
        cancelledLayout.removeAllViews();

        // Cancelled View
        Picasso.with(mContext).load(mContract.getProposal().getUserProfile().getUser().getPhotoURL())
                .placeholder(R.mipmap.icon_profile)
                .into(cancelledView.imgAvatar);
        cancelledView.lblCancelTitle.setText("You have cancelled the job.");
        cancelledView.lblComment.setText(mJob.getReason());

        cancelledLayout.addView(cancelledView);
    }
    // TODO - 4.2
    // TODO - 5.1 - catherin has started the work.(progress-view)
    private void showStartedWork(JGGAppointmentActivityModel activity) {
        Date postOn = activity.getActiveOn();
        String postedTime = getDayMonthYear(postOn) + " " + getTimePeriodString(postOn);
        String providerName = mContract.getProposal().getUserProfile().getUser().getEmail();

        progressLayout.removeAllViews();

        progressView.imgStartWork.setImageResource(R.mipmap.icon_startwork_orange);
        progressView.startWorkLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGGreen));

        progressView.lblStartTime.setText(postedTime);

        progressView.lblStartedWork.setText("");
        progressView.lblStartedWork.append(setBoldText(providerName));
        progressView.lblStartedWork.append(" has started the work.");

        progressView.lblReportDesc.setVisibility(View.GONE);
        progressView.btnStart.setVisibility(View.GONE);

        progressView.billableLayout.setVisibility(View.GONE);

        progressLayout.addView(progressView);
    }
    // TODO - 5.2 - show billable layout
    private void showStartedWorkBillable(JGGAppointmentActivityModel activity) {
        Date postOn = activity.getActiveOn();
        String postedTime = getDayMonthYear(postOn) + " " + getTimePeriodString(postOn);
        String providerName = mContract.getProposal().getUserProfile().getUser().getEmail();

        progressLayout.removeAllViews();

        progressView.imgStartWork.setImageResource(R.mipmap.icon_startwork_orange);
        progressView.startWorkLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGGreen));

        progressView.lblStartTime.setText(postedTime);

        progressView.lblStartedWork.setText("");
        progressView.lblStartedWork.append(setBoldText(providerName));
        progressView.lblStartedWork.append(" has started the work.");

        progressView.lblReportDesc.setVisibility(View.GONE);
        progressView.btnStart.setVisibility(View.GONE);

        progressView.billableLayout.setVisibility(View.VISIBLE);

        progressLayout.addView(progressView);
    }
    // TODO - 5.3 - review header
    private void showReportInvoice() {
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
                intent.putExtra(JGG_USERTYPE, CLIENT.toString());
                intent.putExtra("work_start_status", false);
                mActivity.startActivity(intent);
            }
        });

        headerLayout.addView(headerView);
    }

    // TODO - 6.1
    private void showComplete(JGGAppointmentActivityModel activity) {

        Date submitOn = activity.getActiveOn();
        String submitTime = getDayMonthYear(submitOn) + " " + getTimePeriodString(submitOn);
        String providerName = mContract.getProposal().getUserProfile().getUser().getEmail();

        paymentLayout.removeAllViews();

        paymentView.imgDone.setImageResource(R.mipmap.icon_verified_orange);
        paymentView.dotLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGGreen));

        paymentView.firstLayout.setVisibility(View.GONE);
        paymentView.secondLayout.setVisibility(View.GONE);

        paymentView.thirdLayout.setVisibility(View.VISIBLE);
        paymentView.txtThirdTime.setText(submitTime);
        paymentView.txtThirdDescription.setText("");

        paymentView.txtThirdDescription.append(setBoldText(providerName));
        paymentView.txtThirdDescription.append(" has completed the work. You have 3 days to verify the work.");

        paymentView.btnReport.setVisibility(View.VISIBLE);
        paymentView.btnReport.setText(R.string.view_job_report);
        paymentView.btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JobReportSummaryFragment frag = JobReportSummaryFragment.newInstance(Global.JGGUserType.PROVIDER.toString());
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.incoming_container, frag, frag.getTag());
                ft.addToBackStack("report_fragment");
                ft.commit();
            }
        });
    }

    // TODO - 6.2.1. catherin has completed the work.
    private void showCompleteAndVerify(JGGAppointmentActivityModel activity) {
        Date submitOn = activity.getActiveOn();
        String submitTime = getDayMonthYear(submitOn) + " " + getTimePeriodString(submitOn);
        String providerName = mContract.getProposal().getUserProfile().getUser().getEmail();

        paymentLayout.removeAllViews();

        paymentView.imgDone.setImageResource(R.mipmap.icon_verified);
        paymentView.dotLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGGreen));

        paymentView.firstLayout.setVisibility(View.GONE);
        paymentView.secondLayout.setVisibility(View.GONE);

        paymentView.thirdLayout.setVisibility(View.VISIBLE);
        paymentView.txtThirdTime.setText(submitTime);
        paymentView.txtThirdDescription.setText("");

        paymentView.txtThirdDescription.append(setBoldText(providerName));
        paymentView.txtThirdDescription.append(" has completed the work");
        paymentView.btnReport.setVisibility(View.GONE);
    }

    // TODO - 6.2.2. You have verified the work
    private void showVerifiedWork(JGGAppointmentActivityModel activity) {
        Date submitOn = activity.getActiveOn();
        String submitTime = getDayMonthYear(submitOn) + " " + getTimePeriodString(submitOn);

        paymentView.secondLayout.setVisibility(View.VISIBLE);
        paymentView.txtSecondTime.setText(submitTime);
        paymentView.txtSecondDescription.setText(R.string.verified_work);
    }

    // TODO - 6.2.3. You officially completed.
    private void showOfficialComplete(JGGAppointmentActivityModel activity) {
        paymentView.firstLayout.setVisibility(View.VISIBLE);

        Date submitOn = activity.getActiveOn();
        String submitTime = getDayMonthYear(submitOn) + " " + getTimePeriodString(submitOn);

        paymentView.txtFirstTime.setText(submitTime);
        paymentView.txtFirstDescription.setText(R.string.official_complete);
    }

    private void onShowReviewFragment() {
        mActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.app_detail_container, new JobReviewFragment())
                .addToBackStack("review_fragment")
                .commit();
    }

    // TODO - show get review
    private void showGetReview(JGGAppointmentActivityModel activity) {
        getReviewLayout.removeAllViews();

        Date submitOn = activity.getActiveOn();
        String submitTime = getDayMonthYear(submitOn) + " " + getTimePeriodString(submitOn);
        String providerName = mContract.getProposal().getUserProfile().getUser().getEmail();

        getReviewView.lblReviewDate.setText(submitTime);
        getReviewView.lblReviewTitle.setText("");
        getReviewView.lblReviewTitle.append(setBoldText(providerName));
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
        String providerName = mContract.getProposal().getUserProfile().getUser().getEmail();

        givenReviewView.lblReviewDate.setText(submitTime);
        givenReviewView.lblReviewTitle.setText("");
        givenReviewView.lblReviewTitle.append("You have given ");
        givenReviewView.lblReviewTitle.append(setBoldText(providerName));
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

    @OnClick(R.id.btn_posted_job)
    public void onClickPostedJob() {
        mActivity.actionbarView.setStatus(JGGActionbarView.EditStatus.JOB_DETAILS, AppointmentType.UNKNOWN);
        mActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.app_detail_container, new NewJobDetailsFragment())
                .addToBackStack("job_detail_fragment")
                .commit();
    }

    @OnClick(R.id.btn_view_proposal)
    public void onClickViewProposal() {
        Intent intent = new Intent(mContext, PostProposalActivity.class);
        intent.putExtra(EDIT_STATUS, "ACCEPTED");
        startActivity(intent);
    }

    @OnClick(R.id.btn_chat)
    public void onClickChat() {

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
        mActivity = ((OutgoingJobActivity) mContext);
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
