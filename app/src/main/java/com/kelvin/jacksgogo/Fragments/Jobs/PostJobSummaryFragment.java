package com.kelvin.jacksgogo.Fragments.Jobs;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
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

import com.kelvin.jacksgogo.Activities.Search.PostServiceActivity;
import com.kelvin.jacksgogo.CustomView.Views.PostJobTabbarView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCreatingJobModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGPostJobResponse;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import co.lujun.androidtagview.TagContainerLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    private AlertDialog alertDialog;
    private PostJobStatus jobStatus;
    public JGGCategoryModel selectedCategory;
    private JGGCreatingJobModel creatingJob;
    private ProgressDialog progressDialog;

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

        selectedCategory = ((PostServiceActivity)mContext).selectedCategory;
        ((PostServiceActivity)mContext).creatingJob.setJobCategoryID(selectedCategory.getID());
        creatingJob = ((PostServiceActivity)mContext).creatingJob;

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

        btnDescribe.setOnClickListener(this);
        btnTime.setOnClickListener(this);
        btnAddress.setOnClickListener(this);
        btnBudget.setOnClickListener(this);
        btnReport.setOnClickListener(this);
        btnPostJob.setOnClickListener(this);
    }

    private void setDatas() {
        Picasso.with(mContext)
                .load(selectedCategory.getImage())
                .placeholder(null)
                .into(imgCategory);
        lblCategory.setText(selectedCategory.getName());

        if (creatingJob != null) {
            // Describe
            lblDescribeTitle.setText(creatingJob.getTitle());
            lblDescribeDesc.setText(creatingJob.getDescription());
            String [] strings = creatingJob.getTags().split(",");
            describeTagView.setTags(Arrays.asList(strings));
            // Address
            lblAddress.setText(creatingJob.getAddress().getFullAddress());
            // Budget
            if (creatingJob.getSelectedPriceType() == 1) lblBudget.setText("No limit");
            else if (creatingJob.getSelectedPriceType() == 2) lblBudget.setText("Fixed $ " + creatingJob.getBudget().toString());
            else if (creatingJob.getSelectedPriceType() == 3)
                lblBudget.setText("From $ " + creatingJob.getBudgetFrom().toString()
                        + " "
                        + "to $ " + creatingJob.getBudgetTo().toString());
            // Report
            lblReport.setText(creatingJob.reportTypeName());
            // Time
            if (creatingJob.getJobType() == Global.JGGJobType.oneTime) {
                String time = "";
                if (creatingJob.getJobTime().isSpecific()) {
                    if (creatingJob.getJobTime().getJobEndOn() != null)
                        time = "on "
                                + getDateString(creatingJob.getJobTime().getJobStartOn())
                                + " " + Global.getTimeString(creatingJob.getJobTime().getJobStartOn())
                                + " - "
                                + Global.getTimeString(creatingJob.getJobTime().getJobEndOn());
                    else
                        time = "on "
                                + getDateString(creatingJob.getJobTime().getJobStartOn())
                                + " " + Global.getTimeString(creatingJob.getJobTime().getJobStartOn());
                } else {
                    if (creatingJob.getJobTime().getJobEndOn() != null)
                        time = "any time until "
                                + getDateString(creatingJob.getJobTime().getJobStartOn())
                                + " " + Global.getTimeString(creatingJob.getJobTime().getJobStartOn())
                                + " - "
                                + Global.getTimeString(creatingJob.getJobTime().getJobEndOn());
                    else
                        time = "any time until "
                                + getDateString(creatingJob.getJobTime().getJobStartOn())
                                + " " + Global.getTimeString(creatingJob.getJobTime().getJobStartOn());
                }
                lblTime.setText(time);
            } else if (creatingJob.getJobType() == Global.JGGJobType.repeating) {
                String time = "";
                String dayString = creatingJob.getRepetition();
                String[] items = dayString.split(",");
                if (creatingJob.getRepetitionType() == Global.JGGRepetitionType.weekly) {
                    for (int i = 0; i < items.length; i ++) {
                        if (time.equals(""))
                            time = "Every " + Global.getWeekName(Integer.parseInt(items[i]) - 1);
                        else
                            time = time + ", " + "Every " + Global.getWeekName(Integer.parseInt(items[i]) - 1);
                    }
                } else if (creatingJob.getRepetitionType() == Global.JGGRepetitionType.monthly) {
                    for (int i = 0; i < items.length; i ++) {
                        if (time.equals(""))
                            time = "Every " + Global.getDayName(Integer.parseInt(items[i]) - 1) + " of the month";
                        else
                            time = time + ", " + "Every " + Global.getDayName(Integer.parseInt(items[i]) - 1) + " of the month";
                    }
                }
                lblTime.setText(time);
            }
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

    public String getDateString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");
        String dateString = dateFormat.format(date);
        return dateString;
    }

    private void onPostJob() {
        progressDialog = Global.createProgressDialog(mContext);
        JGGAPIManager manager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        Call<JGGPostJobResponse> call = manager.postJob(creatingJob);
        call.enqueue(new Callback<JGGPostJobResponse>() {
            @Override
            public void onResponse(Call<JGGPostJobResponse> call, Response<JGGPostJobResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    response.body().getValue();
                    showAlertDialog();
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

        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_post_job) {
            switch (jobStatus) {
                case NONE:
                    onPostJob();
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
