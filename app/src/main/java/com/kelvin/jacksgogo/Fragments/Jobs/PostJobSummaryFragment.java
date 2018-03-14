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
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.Activities.Jobs.PostedJobActivity;
import com.kelvin.jacksgogo.CustomView.Views.PostJobTabbarView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGJobModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGPostJobResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import co.lujun.androidtagview.TagContainerLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.selectedAppointment;
import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.selectedCategory;
import static com.kelvin.jacksgogo.Utils.Global.convertJobBudgetString;
import static com.kelvin.jacksgogo.Utils.Global.convertJobTimeString;

public class PostJobSummaryFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    private ImageView imgCategory;
    private TextView lblCategory;
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
    private TextView lblPostJob;

    private AlertDialog alertDialog;
    private PostJobStatus jobStatus;
    private JGGCategoryModel category;
    private JGGJobModel creatingJob;
    private ProgressDialog progressDialog;
    private ArrayList<String> attachmentURLs;
    private String postedJobID;

    private PostJobMainTabFragment fragment;

    public enum PostJobStatus {
        POST,
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
        attachmentURLs = new ArrayList<>();
        category = selectedCategory;
        selectedAppointment.setCategoryID(category.getID());
        creatingJob = selectedAppointment;
        creatingJob.setAttachmentURLs(attachmentURLs);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_job_summary, container, false);

        initView(view);
        setDatas();

        return view;
    }

    private void initView(View view) {
        imgCategory = view.findViewById(R.id.img_category);
        lblCategory = view.findViewById(R.id.lbl_category_name);
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
        lblPostJob = view.findViewById(R.id.lbl_post_job);

        if (jobStatus == PostJobStatus.EDIT) lblPostJob.setText("Save Changes");
        btnDescribe.setOnClickListener(this);
        btnTime.setOnClickListener(this);
        btnAddress.setOnClickListener(this);
        btnBudget.setOnClickListener(this);
        btnReport.setOnClickListener(this);
        btnPostJob.setOnClickListener(this);
    }

    private void setDatas() {

        if (creatingJob != null) {
            // Category
            Picasso.with(mContext)
                    .load(category.getImage())
                    .placeholder(null)
                    .into(imgCategory);
            lblCategory.setText(category.getName());
            // Describe
            lblDescribeTitle.setText(creatingJob.getTitle());
            lblDescribeDesc.setText(creatingJob.getDescription());
            String tags = creatingJob.getTags();
            if (tags != null && tags.length() > 0) {
                String [] strings = tags.split(",");
                describeTagView.setTags(Arrays.asList(strings));
            }
            // Address
            lblAddress.setText(creatingJob.getAddress().getFullAddress());
            // Budget
            lblBudget.setText(convertJobBudgetString(creatingJob));
            // Report
            lblReport.setText(Global.reportTypeName(creatingJob.getReportType()));
            // Time
            lblTime.setText(convertJobTimeString(creatingJob));
        } else {
            lblDescribeTitle.setText("No title");
            lblDescribeDesc.setText("");
            describeTagView.removeAllTags();
            lblTime.setText("No set");
            lblAddress.setText("No set");
            lblBudget.setText("No set");
            lblReport.setText("No set");
        }
    }

    private void onPostJob() {
        progressDialog = Global.createProgressDialog(mContext);
        JGGAPIManager manager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        Call<JGGPostJobResponse> call = manager.postNewJob(creatingJob);
        call.enqueue(new Callback<JGGPostJobResponse>() {
            @Override
            public void onResponse(Call<JGGPostJobResponse> call, Response<JGGPostJobResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        postedJobID = response.body().getValue();
                        showPostJobAlertDialog(false);
                    } else {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode  = response.code();
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGPostJobResponse> call, Throwable t) {
                Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void onEditJob() {
        progressDialog = Global.createProgressDialog(mContext);
        JGGAPIManager manager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        Call<JGGPostJobResponse> call = manager.editJob(creatingJob);
        call.enqueue(new Callback<JGGPostJobResponse>() {
            @Override
            public void onResponse(Call<JGGPostJobResponse> call, Response<JGGPostJobResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        postedJobID = response.body().getValue();
                        showPostJobAlertDialog(true);
                    } else {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode  = response.code();
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGPostJobResponse> call, Throwable t) {
                Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void showPostJobAlertDialog(final boolean isEdit) {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
        LayoutInflater inflater = this.getLayoutInflater();

        View alertView = inflater.inflate(R.layout.jgg_alert_view, null);
        builder.setView(alertView);
        alertDialog = builder.create();

        TextView cancelButton = (TextView) alertView.findViewById(R.id.btn_alert_cancel);
        TextView okButton = (TextView) alertView.findViewById(R.id.btn_alert_ok);
        TextView title = (TextView) alertView.findViewById(R.id.lbl_alert_titile);
        TextView desc = (TextView) alertView.findViewById(R.id.lbl_alert_description);

        if (isEdit) {
            title.setText(R.string.alert_job_edit_title);
            desc.setText(R.string.alert_job_edit_desc);
            okButton.setText(R.string.alert_ok);
            okButton.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGGreen));
        } else {
            title.setText(R.string.alert_job_posted_title);
            desc.setText("Job reference no: " + postedJobID + '\n' +  '\n' + "Good luck!");
            okButton.setText(R.string.alert_view_job_button);
            okButton.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGCyan));
        }
        cancelButton.setVisibility(View.GONE);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                if (isEdit) {
                    getActivity().onBackPressed();
                } else {
                    //getActivity().finish();
                    Intent intent = new Intent(mContext, PostedJobActivity.class);
                    mContext.startActivity(intent);
                }
            }
        });

        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_post_job) {
            switch (jobStatus) {
                case POST:
                    //showPostJobAlertDialog(false);
                    onPostJob();
                    break;
                case EDIT:
                    onEditJob();
                    break;
                case DUPLICATE:
//                    Intent intent = new Intent(mContext, PostServiceActivity.class);
//                    intent.putExtra(EDIT_STATUS, POST);
//                    intent.putExtra(APPOINTMENT_TYPE, SERVICES);
//                    startActivity(intent);
                    break;
                default:
                    break;
            }
            return;
        } else if (view.getId() == R.id.btn_post_job_summary_describe) {
            fragment = PostJobMainTabFragment.newInstance(PostJobTabbarView.PostJobTabName.DESCRIBE, PostJobStatus.POST);
            if (jobStatus == PostJobStatus.EDIT) {
                fragment = PostJobMainTabFragment.newInstance(PostJobTabbarView.PostJobTabName.DESCRIBE, PostJobStatus.EDIT);
            }
        } else if (view.getId() == R.id.btn_post_job_summary_time) {
            fragment = PostJobMainTabFragment.newInstance(PostJobTabbarView.PostJobTabName.TIME, PostJobStatus.POST);
            if (jobStatus == PostJobStatus.EDIT) {
                fragment = PostJobMainTabFragment.newInstance(PostJobTabbarView.PostJobTabName.TIME, PostJobStatus.EDIT);
            }
        } else if (view.getId() == R.id.btn_post_job_summary_address) {
            fragment = PostJobMainTabFragment.newInstance(PostJobTabbarView.PostJobTabName.ADDRESS, PostJobStatus.POST);
            if (jobStatus == PostJobStatus.EDIT) {
                fragment = PostJobMainTabFragment.newInstance(PostJobTabbarView.PostJobTabName.ADDRESS, PostJobStatus.EDIT);
            }
        }  else if (view.getId() == R.id.btn_post_job_summary_budget) {
            fragment = PostJobMainTabFragment.newInstance(PostJobTabbarView.PostJobTabName.BUDGET, PostJobStatus.POST);
            if (jobStatus == PostJobStatus.EDIT) {
                fragment = PostJobMainTabFragment.newInstance(PostJobTabbarView.PostJobTabName.BUDGET, PostJobStatus.EDIT);
            }
        } else if (view.getId() == R.id.btn_post_job_summary_report) {
            fragment = PostJobMainTabFragment.newInstance(PostJobTabbarView.PostJobTabName.REPORT, PostJobStatus.POST);
            if (jobStatus == PostJobStatus.EDIT) {
                fragment = PostJobMainTabFragment.newInstance(PostJobTabbarView.PostJobTabName.REPORT, PostJobStatus.EDIT);
            }
        }
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.post_service_container, fragment)
                .addToBackStack("post_job")
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
        void onFragmentInteraction(Uri uri);
    }
}
