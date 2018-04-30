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
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobStatusSummaryTipView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobStatusSummaryWorkProgressView;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.R;
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
import static com.kelvin.jacksgogo.Utils.Global.setBoldText;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.appointmentMonthDate;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getAppointmentTime;
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
    @BindView(R.id.job_main_header_layout)          LinearLayout footerLayout;
    @BindView(R.id.job_main_tip_layout)             LinearLayout tipLayout;
    @BindView(R.id.job_main_payment_layout)         LinearLayout paymentLayout;

    private JobStatusSummaryQuotationView quotationView;
    private JobStatusSummaryConfirmedView confirmedView;
    private JobStatusSummaryCancelled cancelledView;
    private JobStatusSummaryWorkProgressView progressView;
    private JobStatusSummaryHeaderView footerView;
    private JobStatusSummaryTipView tipView;
    private JobStatusSummaryPaymentView paymentView;

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
        quotationView = new JobStatusSummaryQuotationView(mContext, CLIENT);
        confirmedView = new JobStatusSummaryConfirmedView(mContext, CLIENT);
        progressView = new JobStatusSummaryWorkProgressView(mContext, CLIENT);
        footerView = new JobStatusSummaryHeaderView(mContext, CLIENT);
        cancelledView = new JobStatusSummaryCancelled(mContext, CLIENT);
        tipView = new JobStatusSummaryTipView(mContext, CLIENT);
        paymentView = new JobStatusSummaryPaymentView(mContext, CLIENT);
    }

    private void resetViews() {
        cancelledLayout.removeAllViews();
        quotationLayout.removeAllViews();
        confirmedLayout.removeAllViews();
        progressLayout.removeAllViews();
        footerLayout.removeAllViews();
    }

    public void onRefreshView() {
        resetViews();

        Date postOn = appointmentMonthDate(mJob.getPostOn());
        String postedTime = getDayMonthYear(postOn) + " " + getTimePeriodString(postOn);

        lblPostedTime.setText(postedTime);

        // Job Open View
        switch (mJob.getStatus()) {
            case open:
                quotationView.notifyDataChanged(isDeleted, mProposals.size());
                lblPostedJob.setText(R.string.outgoing_job);
                quotationLayout.addView(quotationView);
                break;
            case closed:
                break;
            case confirmed:
                // TODO - contract exist
                if (mContract != null)
                    setJobConfirmedStatus();
                break;
            case finished:
                break;
            case flagged:
                break;
            case deleted:
                // Cancelled View
                Picasso.with(mContext).load(currentUser.getUser().getPhotoURL())
                        .placeholder(R.mipmap.icon_profile)
                        .into(cancelledView.imgAvatar);
                cancelledView.lblComment.setText(mReason);
                cancelledLayout.addView(cancelledView);
                break;
            case started:
                // TODO - contract exist
                if (mContract != null) {
                    setJobConfirmedStatus();
                    setJobStartedStatus();
                }
                break;
        }
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
        paymentView = new JobStatusSummaryPaymentView(mContext, JGGUserType.CLIENT);
        paymentView.setOnItemClickListener(new JobStatusSummaryPaymentView.OnItemClickListener() {
            @Override
            public void onItemClick(View item) {
                JobReportSummaryFragment frag = JobReportSummaryFragment.newInstance(false);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.app_detail_container, frag, frag.getTag());
                ft.addToBackStack("report_fragment");
                ft.commit();
            }
        });
        paymentLayout.addView(paymentView);*/
    }

    private void setJobConfirmedStatus() {
        Date postOn = appointmentMonthDate(mJob.getPostOn());
        String postedTime = getDayMonthYear(postOn) + " " + getTimePeriodString(postOn);

        lblPostedJob.setText(R.string.job_request_posted);

        String providerName = mContract.getProposal().getUserProfile().getUser().getEmail();

        // Quotation View

        showAwardQuotation();

        // Confirmed Layout
        confirmedLayout.addView(confirmedView);
        confirmedView.lblConfirmedTime.setText(postedTime);
        confirmedView.confirmedLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGGreen));
        confirmedView.imgConfirmed.setImageResource(R.mipmap.icon_appointment);
        // Make Appointment
        if (getAppointmentTime(mJob).equals(""))
            setAppointmentDate();

        // Provider Info view
        String imgURL = null;
        imgURL = mContract.getProposal().getUserProfile().getUser().getPhotoURL();
        Picasso.with(mContext)
                .load(imgURL)
                .placeholder(R.mipmap.icon_profile)
                .into(imgProvider);
        lblProviderName.setText(providerName);

        providerDetailLayout.setVisibility(View.VISIBLE);
    }

    private void setJobStartedStatus() {
        Date postOn = appointmentMonthDate(mJob.getPostOn());
        String postedTime = getDayMonthYear(postOn) + " " + getTimePeriodString(postOn);

        confirmedView.lblConfirmedDesc.setVisibility(View.GONE);
        confirmedView.confirmedLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGGrey3));
        confirmedView.imgConfirmed.setImageResource(R.mipmap.icon_appointment_inactive);

        progressView.lblStartTime.setText(postedTime);
        progressView.imgStartWork.setImageResource(R.mipmap.icon_startwork);
        progressView.startWorkLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGGreen));

        String providerName = mContract.getProposal().getUserProfile().getUser().getEmail();
        progressView.lblUserName.setText(providerName);
        progressLayout.addView(progressView);

        // Footer Layout
        footerView.reportLayout.setVisibility(View.VISIBLE);
        footerView.invoiceLayout.setVisibility(View.VISIBLE);
        footerView.setOnItemClickListener(new JobStatusSummaryHeaderView.OnItemClickListener() {
            @Override
            public void onItemClick(View item) {
                if (item.getId() == R.id.job_report_layout) {
                    Intent intent = new Intent(mContext, JobReportActivity.class);
                    intent.putExtra(JGG_USERTYPE, CLIENT.toString());
                    intent.putExtra("work_start_status", false);
                    mActivity.startActivity(intent);
                } else if (item.getId() == R.id.job_invoice_layout) {

                }
            }
        });
        footerLayout.addView(footerView);
    }

    private void setAppointmentDate() {
        confirmedView.lblConfirmedTitle.setText(R.string.set_app_date_title);
        confirmedView.lblConfirmedDesc.setText(R.string.set_app_date_desc);
        confirmedView.btnSetAppDate.setVisibility(View.VISIBLE);
        confirmedView.btnSetAppDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void onShowReviewFragment() {
        mActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.app_detail_container, new JobReviewFragment())
                .addToBackStack("review_fragment")
                .commit();
    }

    // TODO - 1.1
    private void showNewJobRequest() {


        lblPostedJob.setText(R.string.job_request_posted);


    }
    // TODO - 1.2
    // TODO - 2.1 - You have awarded catherin the job
    private void showAwardQuotation() {
        // Quotation View
        Date postOn = appointmentMonthDate(mJob.getPostOn());
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
    private void showAppointmentConfirmed() {
        Date postOn = appointmentMonthDate(mJob.getPostOn());
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
    private void showUpdateAppointment() {
        Date postOn = appointmentMonthDate(mJob.getPostOn());
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
    private void showPendingAppointment() {
        Date postOn = appointmentMonthDate(mJob.getPostOn());
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
    private void showHavingProblems() {
        Date postOn = appointmentMonthDate(mJob.getPostOn());
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
    private void showStartedWork() {
        Date postOn = appointmentMonthDate(mJob.getPostOn());
        String postedTime = getDayMonthYear(postOn) + " " + getTimePeriodString(postOn);
        String providerName = mContract.getProposal().getUserProfile().getUser().getEmail();

        progressLayout.removeAllViews();

        progressView.imgStartWork.setImageResource(R.mipmap.icon_startwork_orange);
        progressView.startWorkLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGCyan));

        progressView.lblStartTime.setText(postedTime);

        progressView.lblStartedWork.setText("");
        progressView.lblStartedWork.append(setBoldText(providerName));
        progressView.lblStartedWork.append(" has started the work.");

        progressView.lblReportDesc.setVisibility(View.GONE);
        progressView.btnStart.setVisibility(View.GONE);

        progressLayout.addView(progressView);
    }
    // TODO - 5.2

    @OnClick(R.id.btn_posted_job)
    public void onClickPostedJob() {
        mActivity.actionbarView.setStatus(JGGActionbarView.EditStatus.JOB_DETAILS, AppointmentType.UNKNOWN);
        mActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.app_detail_container, new NewJobDetailsFragment())
                .addToBackStack("job_detail_fragment")
                .commit();
    }

    @OnClick(R.id.btn_chat)
    public void onClickChat() {
        Intent intent = new Intent(mContext, PostProposalActivity.class);
        intent.putExtra(EDIT_STATUS, "ACCEPTED");
        startActivity(intent);
    }

    @OnClick(R.id.lbl_quotation_count)
    public void onClickQuotationCount() {
        Intent intent = new Intent(mContext, ServiceProviderActivity.class);
        startActivity(intent);
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
