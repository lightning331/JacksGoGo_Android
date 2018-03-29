package com.kelvin.jacksgogo.Fragments.Jobs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.Activities.Jobs.JobReportActivity;
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
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGBaseResponse;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.currentUser;
import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.selectedProposal;
import static com.kelvin.jacksgogo.Utils.Global.EDIT;
import static com.kelvin.jacksgogo.Utils.Global.EDIT_STATUS;
import static com.kelvin.jacksgogo.Utils.Global.INVITE_PROPOSAL;
import static com.kelvin.jacksgogo.Utils.Global.JGGJobStatus.confirmed;
import static com.kelvin.jacksgogo.Utils.Global.JGGJobStatus.deleted;
import static com.kelvin.jacksgogo.Utils.Global.JGGProposalStatus.open;
import static com.kelvin.jacksgogo.Utils.Global.JGGUserType;
import static com.kelvin.jacksgogo.Utils.Global.JGGUserType.PROVIDER;
import static com.kelvin.jacksgogo.Utils.Global.JGG_USERTYPE;
import static com.kelvin.jacksgogo.Utils.Global.MY_PROPOSAL;
import static com.kelvin.jacksgogo.Utils.Global.JGGProposalStatus;
import static com.kelvin.jacksgogo.Utils.Global.createProgressDialog;
import static com.kelvin.jacksgogo.Utils.Global.setBoldText;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getAppointmentTime;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getDayMonthYear;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getTimePeriodString;

public class ProgressProposalFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private Context mContext;
    private ProgressJobSummaryActivity mActivity;

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

    private JGGAppointmentModel mJob;
    private JGGProposalModel mProposal;
    private String clientName;

    public ProgressProposalFragment() {
        // Required empty public constructor
    }

    public static ProgressProposalFragment newInstance(String param1, String param2) {
        ProgressProposalFragment fragment = new ProgressProposalFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void setProposal(JGGProposalModel proposal) {
        mProposal = proposal;
        selectedProposal = mProposal;
        mJob = mProposal.getAppointment();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint()) {
            return;
        }
        onRefreshView();
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
        View view = inflater.inflate(R.layout.fragment_progress_proposal, container, false);
        initView(view);
        return view;
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

    private void resetViews() {
        cancelledLayout.removeAllViews();
        quotationLayout.removeAllViews();
        confirmedLayout.removeAllViews();
        progressLayout.removeAllViews();
        footerLayout.removeAllViews();
    }

    private void onRefreshView() {
        resetViews();
        Date submitOn = mProposal.getSubmitOn();
        String submitTime = getDayMonthYear(submitOn) + " " + getTimePeriodString(submitOn);
        lblPostedTime.setText(submitTime);
        lblPostedJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.actionbarView.setStatus(JGGActionbarView.EditStatus.JOB_DETAILS, Global.AppointmentType.UNKNOWN);
                mActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.app_detail_container, new NewJobDetailsFragment())
                        .addToBackStack("job_detail_fragment")
                        .commit();
            }
        });

        //Provider invited to Job
        if (mProposal.getStatus() == JGGProposalStatus.open) {
            if (mProposal.isInvited()) {
                imgProposal.setImageResource(R.mipmap.icon_posted_inactive);
                lblPostedJob.setText(R.string.invited_proposal_title);
                bottomLayout.setVisibility(View.VISIBLE);
                acceptLayout.setVisibility(View.VISIBLE);
            }
        } else if (mProposal.getStatus() == JGGProposalStatus.rejected) {
            // Client rejected provider's proposal
            setDeclineProposalStatus();
        } else if (mProposal.getStatus() == JGGProposalStatus.confirmed) {

            lblPostedJob.setText(R.string.sent_proposal_title);
            quotationView.btnViewQuotation.setVisibility(View.GONE);
            quotationView.lblTitle.setText(R.string.waiting_client_decision);
            quotationView.imgQuotation.setImageResource(R.mipmap.icon_provider_cyan);
            quotationView.quotationLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGCyan));

            // Client Info view
            onShowClientInfoView();

            // Job confirmed View
            if (mJob.getStatus() == confirmed) {
                setJobConfirmedStatus();

                // Client set Job time
                if (!getAppointmentTime(mJob).equals(""))
                    setJobStartedStatus();
            }
            if (mJob.getStatus() == deleted) {

                setDeletedJobStatus();
            }
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
            paymentView = new JobStatusSummaryPaymentView(mContext);
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
        Date submitOn = mProposal.getSubmitOn();
        String submitTime = getDayMonthYear(submitOn) + " " + getTimePeriodString(submitOn);

        // Confirmed View
        confirmedLayout.addView(confirmedView);
        confirmedView.lblConfirmedTime.setText(submitTime);
        confirmedView.confirmedLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGCyan));
        confirmedView.imgConfirmed.setImageResource(R.mipmap.icon_appointment);

        // Quotation View
        quotationView.lblTime.setText(submitTime);
        quotationView.viewQuotationLayout.setVisibility(View.GONE);
        quotationView.awardedLayout.setVisibility(View.VISIBLE);
        quotationView.quotationLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGGrey3));
        quotationView.imgQuotation.setImageResource(R.mipmap.icon_provider_inactive);
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

    private void setJobStartedStatus() {
        Date submitOn = mProposal.getSubmitOn();
        String submitTime = getDayMonthYear(submitOn) + " " + getTimePeriodString(submitOn);

        // Confirmed View
        confirmedView.lblConfirmedDesc.setVisibility(View.GONE);
        confirmedView.confirmedLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGGrey3));
        confirmedView.imgConfirmed.setImageResource(R.mipmap.icon_appointment_inactive);

        // Progress View
        progressLayout.addView(progressView);
        progressView.lblStartTime.setText(submitTime);
        progressView.imgStartWork.setImageResource(R.mipmap.icon_startwork);
        progressView.startWorkLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGCyan));
        progressView.lblStartedWork.setText("");
        progressView.lblStartedWork.append("Take note of ");
        progressView.lblStartedWork.append(setBoldText(clientName));
        progressView.lblStartedWork.append("'s requests (if any) and top on the button to start when you are ready.");
        progressView.btnStart.setVisibility(View.VISIBLE);
        progressView.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, JobReportActivity.class);
                intent.putExtra(JGG_USERTYPE, PROVIDER.toString());
                intent.putExtra("work_start", true);
                mActivity.startActivity(intent);
            }
        });

        // Footer Layout

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
        footerLayout.addView(footerView);*/
    }

    private void setDeclineProposalStatus() {
        lblPostedJob.setText(R.string.sent_proposal_title);
        quotationView.btnViewQuotation.setBackgroundResource(R.drawable.cyan_border_background);
        quotationView.btnViewQuotation.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGCyan));
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
    }

    private void setDeletedJobStatus() {

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
            onShowAlertDialog();
        } else if (view.getId() == R.id.btn_invite_accept) {
            onAcceptInvitation();
        } else if (view.getId() == R.id.btn_alert_cancel) {
            alertDialog.dismiss();
        } else if (view.getId() == R.id.btn_alert_ok) {
            alertDialog.dismiss();
            onRejectInvitation();
        } else if (view.getId() == R.id.btn_view_proposal) {
            Intent intent = new Intent(mContext, PostProposalActivity.class);
            intent.putExtra(EDIT_STATUS, MY_PROPOSAL);
            startActivity(intent);
        } else if (view.getId() == R.id.btn_chat) {

        }
    }

    private void onAcceptInvitation() {
        selectedProposal.setStatus(JGGProposalStatus.confirmed);
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

    private void onShowAlertDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = (this).getLayoutInflater();
        View alertView = inflater.inflate(R.layout.jgg_alert_view, null);
        builder.setView(alertView);
        alertDialog = builder.create();
        TextView cancelButton = alertView.findViewById(R.id.btn_alert_cancel);
        TextView okButton = alertView.findViewById(R.id.btn_alert_ok);
        TextView title = alertView.findViewById(R.id.lbl_alert_titile);
        TextView desc = alertView.findViewById(R.id.lbl_alert_description);

        title.setText(R.string.alert_reject_title);
        okButton.setText(R.string.alert_reject_ok);
        okButton.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGCyan));
        cancelButton.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGCyan10Percent));
        cancelButton.setTextColor(ContextCompat.getColor(mContext, R.color.JGGCyan));
        okButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        desc.setVisibility(View.GONE);
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
