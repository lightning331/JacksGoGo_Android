package com.kelvin.jacksgogo.Fragments.Jobs;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail.JobMainConfirmedView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail.JobMainFooterView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail.JobMainReview;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail.JobMainPaymentView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail.JobMainQuotationView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail.JobMainTipView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail.JobMainWorkProgressView;
import com.kelvin.jacksgogo.R;

public class JobMainFragment extends Fragment implements View.OnClickListener {

    private Context mContext;
    private OnFragmentInteractionListener mListener;

    private LinearLayout btnPostedJob;

    public JobMainFragment() {
        // Required empty public constructor
    }

    public static JobMainFragment newInstance(String param1, String param2) {
        JobMainFragment fragment = new JobMainFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_job_main, container, false);

        initView(view);
        return view;
    }

    private void initView(final View view) {
        btnPostedJob = view.findViewById(R.id.btn_posted_job);
        btnPostedJob.setOnClickListener(this);

        LinearLayout footerLayout = (LinearLayout)view.findViewById(R.id.job_main_footer_layout);
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
        confirmedLayout.addView(confirmedView);

        LinearLayout quotationLayout = (LinearLayout)view.findViewById(R.id.job_main_quotation_layout);
        JobMainQuotationView quotationView = new JobMainQuotationView(mContext);
        quotationView.setOnItemClickListener(new JobMainQuotationView.OnItemClickListener() {
            @Override
            public void onItemClick(View item) {
                QuotationFragment frag = new QuotationFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.app_detail_container, frag, frag.getTag());
                ft.addToBackStack("view_quotation_fragment");
                ft.commit();
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
