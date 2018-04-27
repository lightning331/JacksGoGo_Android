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
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobStatusSummaryFooterView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobStatusSummaryPaymentView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobStatusSummaryQuotationView;
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

import static com.kelvin.jacksgogo.Utils.Global.EDIT_STATUS;
import static com.kelvin.jacksgogo.Utils.Global.JGGJobStatus.confirmed;
import static com.kelvin.jacksgogo.Utils.Global.JGGJobStatus.deleted;
import static com.kelvin.jacksgogo.Utils.Global.JGGJobStatus.open;
import static com.kelvin.jacksgogo.Utils.Global.JGGJobStatus.started;
import static com.kelvin.jacksgogo.Utils.Global.JGGUserType.CLIENT;
import static com.kelvin.jacksgogo.Utils.Global.JGG_USERTYPE;
import static com.kelvin.jacksgogo.Utils.Global.setBoldText;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.appointmentMonthDate;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getAppointmentTime;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getDayMonthYear;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getTimePeriodString;

public class OutgoingJobFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private Context mContext;
    private OutgoingJobActivity mActivity;

    private TextView lblPostedTime;
    private TextView lblPostedJob;
    private LinearLayout btnPostedJob;
    private LinearLayout providerDetailLayout;
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
        lblPostedTime = view.findViewById(R.id.lbl_posted_time);
        lblPostedJob = view.findViewById(R.id.lbl_next_step_title);
        btnPostedJob = view.findViewById(R.id.btn_posted_job);
        providerDetailLayout = view.findViewById(R.id.posted_service_chat_layout);
        imgProvider = view.findViewById(R.id.img_avatar);
        lblProviderName = view.findViewById(R.id.lbl_provider_name);
        btnViewProposal = view.findViewById(R.id.btn_view_proposal);
        btnChat = view.findViewById(R.id.btn_chat);
        btnPostedJob.setOnClickListener(this);
        btnViewProposal.setOnClickListener(this);
        btnChat.setOnClickListener(this);

        quotationLayout = view.findViewById(R.id.job_main_quotation_layout);
        quotationView = new JobStatusSummaryQuotationView(mContext, CLIENT);

        confirmedLayout = view.findViewById(R.id.job_main_confirmed_layout);
        confirmedView = new JobStatusSummaryConfirmedView(mContext, CLIENT);

        progressLayout = view.findViewById(R.id.job_main_work_progress_layout);
        progressView = new JobStatusSummaryWorkProgressView(mContext, CLIENT);

        footerLayout = view.findViewById(R.id.job_main_header_layout);
        footerView = new JobStatusSummaryFooterView(mContext, CLIENT);

        cancelledLayout = view.findViewById(R.id.job_main_cancelled_layout);
        cancelledView = new JobStatusSummaryCancelled(mContext, CLIENT);

        tipLayout = view.findViewById(R.id.job_main_tip_layout);
        tipView = new JobStatusSummaryTipView(mContext, CLIENT);

        paymentLayout = view.findViewById(R.id.job_main_payment_layout);
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
                quotationView.setOnItemClickListener(new JobStatusSummaryQuotationView.OnItemClickListener() {
                    @Override
                    public void onItemClick(View item) {
                        quotationViewItemClick(item);
                    }
                });
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

        // Quotation View

        quotationView.lblTime.setText(postedTime);
        quotationView.viewQuotationLayout.setVisibility(View.GONE);
        quotationView.awardedLayout.setVisibility(View.VISIBLE);
        quotationView.quotationLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGGrey3));
        quotationView.imgQuotation.setImageResource(R.mipmap.icon_provider_inactive);
        quotationView.lblQuotationCount.setText("");
        quotationView.lblQuotationCount.append("You have awarded ");

        String providerName = mContract.getProposal().getUserProfile().getUser().getEmail();
        quotationView.lblQuotationCount.append(setBoldText(providerName));
        quotationView.lblQuotationCount.append(" to the job.");
        quotationView.lblQuotationCount.setOnClickListener(this);
        quotationView.setOnItemClickListener(new JobStatusSummaryQuotationView.OnItemClickListener() {
            @Override
            public void onItemClick(View item) {
                quotationViewItemClick(item);
            }
        });
        quotationLayout.addView(quotationView);

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
        footerView.setOnItemClickListener(new JobStatusSummaryFooterView.OnItemClickListener() {
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

    private void quotationViewItemClick(View item) {
        if (item.getId() == R.id.btn_view_quotation) {
            if (mProposals.size() == 0) {
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

    private void onShowReviewFragment() {
        mActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.app_detail_container, new JobReviewFragment())
                .addToBackStack("review_fragment")
                .commit();
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
