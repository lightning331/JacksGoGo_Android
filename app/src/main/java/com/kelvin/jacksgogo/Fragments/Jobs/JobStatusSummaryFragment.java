package com.kelvin.jacksgogo.Fragments.Jobs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.Activities.Jobs.InviteProviderActivity;
import com.kelvin.jacksgogo.Adapter.Jobs.InviteProviderAdapter;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobMainQuotationView;
import com.kelvin.jacksgogo.CustomView.Views.PostServiceTabbarView;
import com.kelvin.jacksgogo.Fragments.Search.PostServiceMainTabFragment;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGInviteUsersResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGProposalResponse;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.creatingAppointment;
import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.currentUser;
import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.selectedCategory;
import static com.kelvin.jacksgogo.Utils.Global.getDayMonthYear;
import static com.kelvin.jacksgogo.Utils.Global.getTimePeriodString;
import static com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentBaseModel.appointmentMonthDate;

public class JobStatusSummaryFragment extends Fragment implements View.OnClickListener {

    private Context mContext;
    private OnFragmentInteractionListener mListener;

    private TextView lblPostedTime;
    private TextView lblPostedJob;
    private LinearLayout btnPostedJob;

    private ProgressDialog progressDialog;
    private View view;

    private ArrayList<JGGProposalModel> proposals;

    public JobStatusSummaryFragment() {
        // Required empty public constructor
    }

    public static JobStatusSummaryFragment newInstance(String param1, String param2) {
        JobStatusSummaryFragment fragment = new JobStatusSummaryFragment();
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
        getProposalsByJob();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_job_main, container, false);

        initView();
        return view;
    }

    private void getProposalsByJob() {
        progressDialog = Global.createProgressDialog(mContext);
        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        Call<JGGProposalResponse> call = apiManager.getProposalsByJob(creatingAppointment.getID(), 0, 0);
        call.enqueue(new Callback<JGGProposalResponse>() {
            @Override
            public void onResponse(Call<JGGProposalResponse> call, Response<JGGProposalResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    proposals = response.body().getValue();
                    setData();
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

    private void initView() {
        lblPostedTime = view.findViewById(R.id.lbl_posted_time);
        lblPostedJob = view.findViewById(R.id.lbl_next_step_title);
        btnPostedJob = view.findViewById(R.id.btn_posted_job);
        btnPostedJob.setOnClickListener(this);

        Date postOn = appointmentMonthDate(creatingAppointment.getPostOn());
        lblPostedTime.setText(getDayMonthYear(postOn) + " " + getTimePeriodString(postOn));
    }

    private void setData() {
        /*LinearLayout footerLayout = (LinearLayout)view.findViewById(R.id.job_main_footer_layout);
        JobMainFooterView footerView = new JobMainFooterView(mContext);
        footerView.setOnItemClickListener(new JobMainFooterView.OnItemClickListener() {
            @Override
            public void onItemClick(View item) {
                if (item.getId() == R.id.job_report_layout) {
                    JobReportFragment frag = JobReportFragment.newInstance(true);
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.app_detail_container, frag, frag.getTag());
                    ft.addToBackStack("report_fragment");
                    ft.commit();
                }
            }
        });
        footerLayout.addView(footerView);

        LinearLayout givenReviewLayout = (LinearLayout)view.findViewById(R.id.job_main_given_review_layout);
        JobMainReview givenReviewView = new JobMainReview(mContext);
        givenReviewLayout.addView(givenReviewView);
        givenReviewView.setOnItemClickListener(new JobMainReview.OnItemClickListener() {
            @Override
            public void onItemClick(View item) {
                onShowReviewFragment();
            }
        });

        LinearLayout getReviewLayout = (LinearLayout)view.findViewById(R.id.job_main_get_review_layout);
        JobMainReview getReviewView = new JobMainReview(mContext);
        getReviewLayout.addView(getReviewView);
        getReviewView.setOnItemClickListener(new JobMainReview.OnItemClickListener() {
            @Override
            public void onItemClick(View item) {
                onShowReviewFragment();
            }
        });

        LinearLayout tipLayout = (LinearLayout)view.findViewById(R.id.job_main_tip_layout);
        JobMainTipView tipView = new JobMainTipView(mContext);
        tipLayout.addView(tipView);

        LinearLayout paymentLayout = (LinearLayout)view.findViewById(R.id.job_main_payment_layout);
        JobMainPaymentView paymentView = new JobMainPaymentView(mContext);
        paymentView.setOnItemClickListener(new JobMainPaymentView.OnItemClickListener() {
            @Override
            public void onItemClick(View item) {
                JobReportFragment frag = JobReportFragment.newInstance(false);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.app_detail_container, frag, frag.getTag());
                ft.addToBackStack("report_fragment");
                ft.commit();
            }
        });
        paymentLayout.addView(paymentView);

        LinearLayout progressLayout = (LinearLayout)view.findViewById(R.id.job_main_work_progress_layout);
        JobMainWorkProgressView progressView = new JobMainWorkProgressView(mContext);
        progressLayout.addView(progressView);

        LinearLayout confirmedLayout = (LinearLayout)view.findViewById(R.id.job_main_confirmed_layout);
        JobMainConfirmedView confirmedView = new JobMainConfirmedView(mContext);
        confirmedLayout.addView(confirmedView);*/

        LinearLayout quotationLayout = (LinearLayout)view.findViewById(R.id.job_main_quotation_layout);
        JobMainQuotationView quotationView = new JobMainQuotationView(mContext);
        quotationView.quotationLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGGreen));
        quotationView.imgQuotation.setImageResource(R.mipmap.icon_provider_green);
        if (proposals.size() == 0 || proposals == null) {
            quotationView.lblTitle.setText(R.string.waiting_service_provider);
            quotationView.btnViewQuotation.setText(R.string.invite_service_provider);
            lblPostedJob.setText(R.string.job_request_posted);
        } else if (proposals.size() > 0) {
            quotationView.lblTitle.setText("You have received 1 new quotation!");
            quotationView.btnViewQuotation.setText(R.string.view_quotation);
            lblPostedJob.setText(R.string.outgoing_job);
        }
        quotationView.setOnItemClickListener(new JobMainQuotationView.OnItemClickListener() {
            @Override
            public void onItemClick(View item) {

                if (proposals.size() == 0 || proposals == null) {
                    Intent intent = new Intent(mContext, InviteProviderActivity.class);
                    startActivity(intent);
                } else {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.app_detail_container, new QuotationFragment())
                            .addToBackStack("view_quotation_fragment")
                            .commit();
                }
            }
        });
        quotationLayout.addView(quotationView);
    }

    private void onShowReviewFragment() {
        JobReviewFragment frag = new JobReviewFragment();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.app_detail_container, frag, frag.getTag());
        ft.addToBackStack("review_fragment");
        ft.commit();
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_posted_job) {
            JobDetailFragment frag = new JobDetailFragment();
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.app_detail_container, frag, frag.getTag());
            ft.addToBackStack("job_detail_fragment");
            ft.commit();
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
