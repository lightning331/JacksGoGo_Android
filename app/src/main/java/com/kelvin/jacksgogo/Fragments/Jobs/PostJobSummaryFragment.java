package com.kelvin.jacksgogo.Fragments.Jobs;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.CustomView.Views.PostJobTabbarView;
import com.kelvin.jacksgogo.R;

import java.util.ArrayList;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;

public class PostJobSummaryFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    private LinearLayout btnDescribe;
    private LinearLayout btnTime;
    private LinearLayout btnAddress;
    private LinearLayout btnBudget;
    private LinearLayout btnReport;
    private LinearLayout btnPostJob;
    private TextView lblDescribeTitle;
    private TextView lblDescribeDesc;
    private TextView lblTime;
    private TextView lblAddress;
    private TextView lblBudget;
    private TextView lblReport;
    private TagContainerLayout describeTagView;

    private AlertDialog alertDialog;

    private PostJobStatus jobStatus;

    public enum PostJobStatus {
        NONE,
        EDIT,
        DUPLICATE
    }

    public void setEditStatus(PostJobStatus editStatus) {
        this.jobStatus = editStatus;
    }

    public PostJobSummaryFragment() {
        // Required empty public constructor
    }

    public static PostJobSummaryFragment newInstance(String param1, String param2) {
        PostJobSummaryFragment fragment = new PostJobSummaryFragment();
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
        View view = inflater.inflate(R.layout.fragment_post_job_summary, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        btnDescribe = view.findViewById(R.id.btn_post_job_summary_describe);
        btnTime = view.findViewById(R.id.btn_post_job_summary_time);
        btnAddress = view.findViewById(R.id.btn_post_job_summary_address);
        btnBudget = view.findViewById(R.id.btn_post_job_summary_budget);
        btnReport = view.findViewById(R.id.btn_post_job_summary_report);
        describeTagView = view.findViewById(R.id.post_job_summary_tag_view);
        lblDescribeTitle = view.findViewById(R.id.lbl_post_job_describe_title);
        lblDescribeDesc = view.findViewById(R.id.lbl_post_job_describe_desc);
        lblTime = view.findViewById(R.id.lbl_post_job_time);
        lblAddress = view.findViewById(R.id.lbl_post_job_address);
        lblBudget = view.findViewById(R.id.lbl_post_job_budget);
        lblReport = view.findViewById(R.id.lbl_post_job_report);
        btnPostJob = view.findViewById(R.id.btn_post_job);
        setTagList();

        btnDescribe.setOnClickListener(this);
        btnTime.setOnClickListener(this);
        btnAddress.setOnClickListener(this);
        btnBudget.setOnClickListener(this);
        btnReport.setOnClickListener(this);
        btnPostJob.setOnClickListener(this);
    }

    private void setTagList() {
        Typeface typeface = Typeface.create("muliregular", Typeface.NORMAL);
        describeTagView.setTagTypeface(typeface);
        List<String> tags = new ArrayList<String>();
        tags.add("faucet");
        tags.add("plumbing");
        describeTagView.setTags(tags);
    }

    private void showAlertDialog() {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
        LayoutInflater inflater = this.getLayoutInflater();

        View alertView = inflater.inflate(R.layout.jgg_alert_view, null);
        builder.setView(alertView);
        alertDialog = builder.create();

        TextView cancelButton = (TextView) alertView.findViewById(R.id.btn_alert_cancel);
        TextView okButton = (TextView) alertView.findViewById(R.id.btn_alert_ok);
        TextView title = (TextView) alertView.findViewById(R.id.lbl_alert_titile);
        TextView desc = (TextView) alertView.findViewById(R.id.lbl_alert_description);

        title.setText(R.string.alert_job_posted_title);
        desc.setText(R.string.alert_job_posted_desc);
        cancelButton.setVisibility(View.GONE);
        okButton.setText(R.string.alert_view_job_button);
        okButton.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGCyan));
        okButton.setOnClickListener(this);

        alertDialog.setCanceledOnTouchOutside(false);
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
        if (view.getId() == R.id.btn_post_job) {
            switch (jobStatus) {
                case NONE:
                    showAlertDialog();
                    break;
                case EDIT:
                    showAlertDialog();
                    break;
                case DUPLICATE:
//                    Intent intent = new Intent(mContext, PostServiceActivity.class);
//                    intent.putExtra("EDIT_STATUS", "None");
//                    intent.putExtra("APPOINTMENT_TYPE", "SERVICES");
//                    startActivity(intent);
                    break;
                default:
                    break;
            }
        } else if (view.getId() == R.id.btn_alert_ok) {
            alertDialog.dismiss();
//            Intent intent = new Intent(mContext, PostedServiceActivity.class);
//            intent.putExtra("is_post", true);
//            mContext.startActivity(intent);
        } else if (view.getId() == R.id.btn_post_job_summary_describe) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.post_service_container, PostJobMainTabFragment.newInstance(PostJobTabbarView.TabName.DESCRIBE))
                    .addToBackStack("post_job")
                    .commit();
        } else if (view.getId() == R.id.btn_post_job_summary_time) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.post_service_container, PostJobMainTabFragment.newInstance(PostJobTabbarView.TabName.TIME))
                    .addToBackStack("post_job")
                    .commit();
        } else if (view.getId() == R.id.btn_post_job_summary_address) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.post_service_container, PostJobMainTabFragment.newInstance(PostJobTabbarView.TabName.ADDRESS))
                    .addToBackStack("post_job")
                    .commit();
        }  else if (view.getId() == R.id.btn_post_job_summary_budget) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.post_service_container, PostJobMainTabFragment.newInstance(PostJobTabbarView.TabName.BUDGET))
                    .addToBackStack("post_job")
                    .commit();
        } else if (view.getId() == R.id.btn_post_job_summary_report) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.post_service_container, PostJobMainTabFragment.newInstance(PostJobTabbarView.TabName.REPORT))
                    .addToBackStack("post_job")
                    .commit();
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
