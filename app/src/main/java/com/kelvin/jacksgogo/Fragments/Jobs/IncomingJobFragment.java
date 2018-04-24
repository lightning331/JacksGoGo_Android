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
import com.kelvin.jacksgogo.Activities.Jobs.ServiceProviderActivity;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobStatusSummaryCancelled;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobStatusSummaryConfirmedView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobStatusSummaryFooterView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobStatusSummaryPaymentView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobStatusSummaryQuotationView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobStatusSummaryTipView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobStatusSummaryWorkProgressView;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.CustomView.Views.JGGAlertView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentActivityModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGContractModel;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGBaseResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGPostAppResponse;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.Global.EDIT;
import static com.kelvin.jacksgogo.Utils.Global.EDIT_STATUS;
import static com.kelvin.jacksgogo.Utils.Global.INVITE_PROPOSAL;
import static com.kelvin.jacksgogo.Utils.Global.JGGProposalStatus;
import static com.kelvin.jacksgogo.Utils.Global.JGGUserType;
import static com.kelvin.jacksgogo.Utils.Global.JGGUserType.PROVIDER;
import static com.kelvin.jacksgogo.Utils.Global.JGG_USERTYPE;
import static com.kelvin.jacksgogo.Utils.Global.MY_PROPOSAL;
import static com.kelvin.jacksgogo.Utils.Global.createProgressDialog;
import static com.kelvin.jacksgogo.Utils.Global.setBoldText;
import static com.kelvin.jacksgogo.Utils.JGGAppManager.currentUser;
import static com.kelvin.jacksgogo.Utils.JGGAppManager.selectedProposal;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getDayMonthYear;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getTimePeriodString;

public class IncomingJobFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private Context mContext;
    private IncomingJobActivity mActivity;

    private TextView lblPostedTime;
    private TextView lblPostedJob;
    private LinearLayout btnPostedJob;
    private LinearLayout bottomLayout;
    private LinearLayout clientDetailLayout;
    private LinearLayout acceptLayout;
    private TextView btnReject;
    private TextView btnAccept;
    private ImageView imgProposal;
    private RoundedImageView imgClient;
    private TextView lblClientName;
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

    private AlertDialog alertDialog;
    private ProgressDialog progressDialog;

    private ArrayList<JGGAppointmentActivityModel> mActivities = new ArrayList<>();
    private JGGAppointmentModel mJob;
    private JGGProposalModel mProposal;
    private JGGContractModel mContract;
    private String clientName;

    public IncomingJobFragment() {
        // Required empty public constructor
    }

    public static IncomingJobFragment newInstance(String param1, String param2) {
        IncomingJobFragment fragment = new IncomingJobFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint()) {
            return;
        }
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
        View view = inflater.inflate(R.layout.fragment_incoming_job, container, false);
        initView(view);
        onRefreshView();
        return view;
    }

    public void setAppointmentActivities(ArrayList<JGGAppointmentActivityModel> activities,
                                         ArrayList<JGGProposalModel> proposals,
                                         JGGContractModel contract) {
        mActivities = activities;
        mContract = contract;
        for (JGGProposalModel p : proposals) {
            if (p.getUserProfileID().equals(currentUser.getID())) {
                mProposal = p;
                selectedProposal = mProposal;
                mJob = mProposal.getAppointment();
            }
        }
    }

    private void initView(View view) {
        lblPostedTime = view.findViewById(R.id.lbl_posted_time);
        lblPostedJob = view.findViewById(R.id.lbl_next_step_title);
        btnPostedJob = view.findViewById(R.id.btn_posted_job);
        bottomLayout = view.findViewById(R.id.bottom_layout);
        clientDetailLayout = view.findViewById(R.id.chat_layout);
        acceptLayout = view.findViewById(R.id.accept_layout);
        btnReject = view.findViewById(R.id.btn_invite_reject);
        btnAccept = view.findViewById(R.id.btn_invite_accept);
        imgProposal = view.findViewById(R.id.img_proposal);
        imgClient = view.findViewById(R.id.img_avatar);
        lblClientName = view.findViewById(R.id.lbl_provider_name);
        btnViewProposal = view.findViewById(R.id.btn_view_proposal);
        btnChat = view.findViewById(R.id.btn_chat);
        btnPostedJob.setOnClickListener(this);
        btnViewProposal.setOnClickListener(this);
        btnChat.setOnClickListener(this);
        btnReject.setOnClickListener(this);
        btnAccept.setOnClickListener(this);

        cancelledLayout = view.findViewById(R.id.job_main_cancelled_layout);
        cancelledView = new JobStatusSummaryCancelled(mContext, JGGUserType.PROVIDER);

        quotationLayout = view.findViewById(R.id.job_main_quotation_layout);
        quotationView = new JobStatusSummaryQuotationView(mContext, JGGUserType.PROVIDER);

        confirmedLayout = view.findViewById(R.id.job_main_confirmed_layout);
        confirmedView = new JobStatusSummaryConfirmedView(mContext, JGGUserType.PROVIDER);

        progressLayout = view.findViewById(R.id.job_main_work_progress_layout);
        progressView = new JobStatusSummaryWorkProgressView(mContext, JGGUserType.PROVIDER);

        footerLayout = view.findViewById(R.id.job_main_footer_layout);
        footerView = new JobStatusSummaryFooterView(mContext, JGGUserType.PROVIDER);

        tipLayout = view.findViewById(R.id.job_main_tip_layout);
        tipView = new JobStatusSummaryTipView(mContext, JGGUserType.PROVIDER);

        paymentLayout = view.findViewById(R.id.job_main_payment_layout);
        paymentView = new JobStatusSummaryPaymentView(mContext, JGGUserType.PROVIDER);
    }

    private void onRefreshView() {

        //Provider invited to Job
        if (mProposal == null) {}
        else {
            if (mActivities.size() > 0) {
                for (int i = mActivities.size() - 1; i >= 0; i --) {
                    JGGAppointmentActivityModel activity = mActivities.get(i);
                    switch (activity.getStatus()) {
                        case job_deleted:
                            setDeletedJobStatus();
                            break;
                        case invite_sent:
                            setInvitedStatus(activity);
                            break;
                        case proposal_sent:
                            if (activity.getReferenceID().equals(mProposal.getID())) {
                                setProposedStatus(activity);
                                // Waiting for Client's decision
                                setWaitingClientDecision(activity);
                                // Client Info view
                                onShowClientInfoView();
                            }
                            break;
                        case proposal_edited:
                            if (activity.getReferenceID().equals(mProposal.getID())) {
                                setProposedStatus(activity);
                                // Waiting for Client's decision
                                setWaitingClientDecision(activity);
                                // Client Info view
                                onShowClientInfoView();
                            }
                            break;
                        case proposal_rejected:
                            // Client rejected provider's proposal
                            setDeclineProposalStatus(activity);
                            break;
                        case proposal_deleted:
                            break;
                        case proposal_withdraw:
                            setDeletedJobStatus();
                            mActivity.setStatus(mProposal);
                            break;
                        case proposal_approved:
                            // Set More button
                            mActivity.setStatus(mProposal);
                            // Job confirmed View
                            setReadyToStartStatus(activity);
                            setJobConfirmedStatus(activity);
                            break;
                        case contract_started:
                            setJobReportStatus(activity);
                            break;
                    }
                }
            }
        }



        /*footerView.reportLayout.setVisibility(View.VISIBLE);
        footerView.invoiceLayout.setVisibility(View.VISIBLE);
        footerView.setOnItemClickListener(new JobStatusSummaryFooterView.OnItemClickListener() {
            @Override
            public void onItemClick(View item) {
                if (item.getId() == R.id.job_report_layout) {
                    Intent intent = new Intent(mContext, JobReportActivity.class);
                    intent.putExtra(JGG_USERTYPE, PROVIDER.toString());
                    intent.putExtra("work_start", false);
                    mActivity.startActivity(intent);
                } else if (item.getId() == R.id.job_invoice_layout) {

                }
            }
        });
        footerLayout.addView(footerView);
        LinearLayout givenReviewLayout = (LinearLayout)view.findViewById(R.id.job_main_given_review_layout);
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
                JobReportSummaryFragment frag = JobReportSummaryFragment.newInstance(false);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.incoming_container, frag, frag.getTag());
                ft.addToBackStack("report_fragment");
                ft.commit();
            }
        });
        paymentLayout.addView(paymentView);*/
    }

    private void onPostedJob() {
        mActivity.actionbarView.setStatus(JGGActionbarView.EditStatus.JOB_DETAILS, Global.AppointmentType.UNKNOWN);
        mActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.incoming_container, new NewJobDetailsFragment())
                .addToBackStack("job_detail_fragment")
                .commit();
    }

    private void setInvitedStatus(JGGAppointmentActivityModel activity) {
        Date submitOn = activity.getActiveOn();
        String submitTime = getDayMonthYear(submitOn) + " " + getTimePeriodString(submitOn);
        lblPostedTime.setText(submitTime);
        imgProposal.setImageResource(R.mipmap.icon_posted_orange);
        lblPostedJob.setText(R.string.invited_proposal_title);
        bottomLayout.setVisibility(View.VISIBLE);
        acceptLayout.setVisibility(View.VISIBLE);
        lblPostedJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPostedJob();
            }
        });
    }

    private void setProposedStatus(JGGAppointmentActivityModel activity) {
        Date submitOn = activity.getActiveOn();
        String submitTime = getDayMonthYear(submitOn) + " " + getTimePeriodString(submitOn);
        lblPostedTime.setText(submitTime);
        lblPostedJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPostedJob();
            }
        });
    }

    private void setWaitingClientDecision(JGGAppointmentActivityModel activity) {
        bottomLayout.setVisibility(View.GONE);
        lblPostedJob.setText(R.string.sent_proposal_title);

        quotationLayout.removeAllViews();
        Date submitOn = activity.getActiveOn();
        String submitTime = getDayMonthYear(submitOn) + " " + getTimePeriodString(submitOn);
        quotationView.lblTime.setText(submitTime);
        quotationView.btnViewQuotation.setVisibility(View.GONE);
        quotationView.lblTitle.setText(R.string.waiting_client_decision);
        quotationView.imgQuotation.setImageResource(R.mipmap.icon_provider_cyan);
        quotationView.quotationLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGCyan));
        quotationLayout.addView(quotationView);
    }

    private void setJobConfirmedStatus(JGGAppointmentActivityModel activity) {

        // Confirmed View
        confirmedLayout.removeAllViews();
        Date submitOn = activity.getActiveOn();
        String submitTime = getDayMonthYear(submitOn) + " " + getTimePeriodString(submitOn);
        confirmedView.lblConfirmedTime.setText(submitTime);
        confirmedView.confirmedLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGCyan));
        confirmedView.imgConfirmed.setImageResource(R.mipmap.icon_appointment_orange);
        confirmedLayout.addView(confirmedView);

        // Quotation View
        quotationLayout.removeAllViews();
        quotationView.viewQuotationLayout.setVisibility(View.GONE);
        quotationView.awardedLayout.setVisibility(View.VISIBLE);
        quotationView.quotationLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGGrey3));
        quotationView.imgQuotation.setImageResource(R.mipmap.icon_provider_inactive);
        quotationView.imgRightButton.setVisibility(View.GONE);
        quotationView.lblQuotationCount.setText(R.string.proposal_accepted_title);
        quotationView.lblQuotationCount.setOnClickListener(this);
        quotationView.setOnItemClickListener(new JobStatusSummaryQuotationView.OnItemClickListener() {
            @Override
            public void onItemClick(View item) {
                if (item.getId() == R.id.btn_view_quotation) {

                } else if (item.getId() == R.id.lbl_quotation_count) {
                    Intent intent = new Intent(mContext, ServiceProviderActivity.class);
                    startActivity(intent);
                }
            }
        });
        quotationLayout.addView(quotationView);
    }

    private void setReadyToStartStatus(JGGAppointmentActivityModel activity) {
        Date submitOn = activity.getActiveOn();
        String submitTime = getDayMonthYear(submitOn) + " " + getTimePeriodString(submitOn);

        // Progress View
        progressLayout.removeAllViews();
        clientName = mJob.getUserProfile().getUser().getFullName();
        progressView.lblStartTime.setText(submitTime);
        progressView.imgStartWork.setImageResource(R.mipmap.icon_startwork_orange);
        progressView.startWorkLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGCyan));
        progressView.lblStartedWork.setText("");
        progressView.lblStartedWork.append("Take note of ");
        progressView.lblStartedWork.append(setBoldText(clientName));
        progressView.lblStartedWork.append("'s requests (if any) and top on the button to start when you are ready.");
        progressView.btnStart.setVisibility(View.VISIBLE);
        progressView.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStartContract();
            }
        });
        progressLayout.addView(progressView);

        // Confirmed View
        confirmedLayout.removeAllViews();
        confirmedView.lblConfirmedDesc.setVisibility(View.GONE);
        confirmedView.confirmedLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGGrey3));
        confirmedView.imgConfirmed.setImageResource(R.mipmap.icon_appointment_inactive);
        confirmedLayout.addView(confirmedView);
    }

    private void setJobReportStatus(JGGAppointmentActivityModel activity) {
        Date submitOn = activity.getActiveOn();
        String submitTime = getDayMonthYear(submitOn) + " " + getTimePeriodString(submitOn);

        // Progress View
        progressLayout.removeAllViews();
        progressView.lblStartTime.setText(submitTime);
        progressView.imgStartWork.setImageResource(R.mipmap.icon_startwork_orange);
        progressView.startWorkLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGCyan));
        progressView.lblStartedWork.setVisibility(View.GONE);
        progressView.lblReportDesc.setVisibility(View.VISIBLE);
        progressView.lblUserName.setText("Work is in progress...");
        progressView.btnStart.setText("Job Report...");
        progressView.btnStart.setVisibility(View.VISIBLE);
        progressView.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onShowReportActivity();
            }
        });
        progressLayout.addView(progressView);

        // Confirmed View
        confirmedLayout.removeAllViews();
        confirmedView.lblConfirmedDesc.setVisibility(View.GONE);
        confirmedView.confirmedLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGGrey3));
        confirmedView.imgConfirmed.setImageResource(R.mipmap.icon_appointment_inactive);
        confirmedLayout.addView(confirmedView);
    }

    private void onShowReportActivity() {
        Intent intent = new Intent(mContext, JobReportActivity.class);
        intent.putExtra(JGG_USERTYPE, PROVIDER.toString());
        intent.putExtra("work_start", true);
        mActivity.startActivity(intent);
    }

    private void setDeclineProposalStatus(JGGAppointmentActivityModel activity) {
        quotationLayout.removeAllViews();

        Date submitOn = activity.getActiveOn();
        String submitTime = getDayMonthYear(submitOn) + " " + getTimePeriodString(submitOn);
        quotationView.lblTime.setText(submitTime);
        quotationView.btnViewQuotation.setBackgroundResource(R.drawable.cyan_border_background);
        quotationView.btnViewQuotation.setTextColor(ContextCompat.getColor(getContext(), R.color.JGGCyan));
        quotationView.btnViewQuotation.setText(R.string.edit_your_title);
        quotationView.imgQuotation.setImageResource(R.mipmap.icon_provider_cyan);
        quotationView.lblTitle.setText(R.string.proposal_declined);
        quotationView.quotationLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGCyan));
        quotationView.btnViewQuotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PostProposalActivity.class);
                intent.putExtra(EDIT_STATUS, EDIT);
                startActivity(intent);
            }
        });
        quotationLayout.addView(quotationView);
    }

    private void setDeletedJobStatus() {

        cancelledLayout.removeAllViews();

        // Cancelled View
        Picasso.with(mContext).load(mJob.getUserProfile().getUser().getPhotoURL())
                .placeholder(R.mipmap.icon_profile)
                .into(cancelledView.imgAvatar);
        cancelledView.lblComment.setText(mJob.getReason());
        cancelledView.lblCancelTitle.setText("");
        cancelledView.lblCancelTitle.append(setBoldText(mJob.getUserProfile().getUser().getFullName()));
        cancelledView.lblCancelTitle.append(" has cancelled the job.");
        cancelledLayout.addView(cancelledView);
    }

    private void onShowClientInfoView() {
        bottomLayout.setVisibility(View.VISIBLE);
        clientName = mJob.getUserProfile().getUser().getFullName();
        Picasso.with(mContext)
                .load(mJob.getUserProfile().getUser().getPhotoURL())
                .placeholder(R.mipmap.icon_profile)
                .into(imgClient);
        lblClientName.setText(clientName);
        clientDetailLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_invite_reject) {
            onShowRejectAlertDialog();
        } else if (view.getId() == R.id.btn_invite_accept) {
            onAcceptInvitation();
        } else if (view.getId() == R.id.btn_view_proposal) {
            Intent intent = new Intent(mContext, PostProposalActivity.class);
            intent.putExtra(EDIT_STATUS, MY_PROPOSAL);
            startActivity(intent);
        } else if (view.getId() == R.id.btn_chat) {

        }
    }

    private void onAcceptInvitation() {
        mProposal.setStatus(JGGProposalStatus.confirmed);
        Intent intent = new Intent(mContext, PostProposalActivity.class);
        intent.putExtra(EDIT_STATUS, INVITE_PROPOSAL);
        startActivity(intent);
    }

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
                    int statusCode  = response.code();
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
