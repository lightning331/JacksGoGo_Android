package com.kelvin.jacksgogo.Activities.Jobs;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.Fragments.Jobs.JobReportMainFragment;
import com.kelvin.jacksgogo.Fragments.Jobs.JobReportSummaryFragment;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGReportResultModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGGetReportResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGGetReportsResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView.EditStatus.ADD_BILLABLE_ITEM;
import static com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView.EditStatus.ADD_TOOLS;
import static com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView.EditStatus.JOB_REPORT;
import static com.kelvin.jacksgogo.Utils.Global.AppointmentType.UNKNOWN;
import static com.kelvin.jacksgogo.Utils.Global.JGG_USERTYPE;
import static com.kelvin.jacksgogo.Utils.Global.REPORTID;
import static com.kelvin.jacksgogo.Utils.Global.createProgressDialog;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getAppointmentTime;

public class JobReportActivity extends AppCompatActivity {

    @BindView(R.id.job_report_actionbar) Toolbar mToolbar;
    @BindView(R.id.img_detail) ImageView imgCategory;
    @BindView(R.id.lbl_title) TextView lblCategory;
    @BindView(R.id.lbl_date) TextView lblJobTime;

    private JGGActionbarView actionbarView;
    private ProgressDialog progressDialog;

    private JGGAppointmentModel selectedAppointment;
    private String mUserType;
    private String reportID;
    private boolean isStartWork;
    private JGGReportResultModel mReportResult;
    private ArrayList<JGGReportResultModel> mReportResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_report);
        ButterKnife.bind(this);

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            mUserType = extra.getString(JGG_USERTYPE);
            reportID = extra.getString(REPORTID);
            isStartWork = extra.getBoolean("work_start");
        }

        selectedAppointment = JGGAppManager.getInstance().getSelectedAppointment();
        mReportResult = JGGAppManager.getInstance().getReportResultModel();

        // Top Navigationbar View
        actionbarView = new JGGActionbarView(this);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        actionbarView.setStatus(JOB_REPORT, UNKNOWN);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        setCategory();
        getReportByID();
    }

    private void setCategory() {
        // Category
        Picasso.with(this)
                .load(selectedAppointment.getCategory().getImage())
                .placeholder(null)
                .into(imgCategory);
        lblCategory.setText(selectedAppointment.getCategory().getName());
        // Time
        lblJobTime.setText(getAppointmentTime(selectedAppointment));
    }

    private void getReportByID() {
        progressDialog = createProgressDialog(this);
        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGGetReportResponse> call = apiManager.getReportByID(reportID);
        call.enqueue(new Callback<JGGGetReportResponse>() {
            @Override
            public void onResponse(Call<JGGGetReportResponse> call, Response<JGGGetReportResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mReportResult = response.body().getValue();
                        getReportByContract();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(JobReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(JobReportActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGGetReportResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(JobReportActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getReportByContract() {
        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGGetReportsResponse> call = apiManager.getReportsByContract(mReportResult.getContractID());
        call.enqueue(new Callback<JGGGetReportsResponse>() {
            @Override
            public void onResponse(Call<JGGGetReportsResponse> call, Response<JGGGetReportsResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mReportResults = response.body().getValue();

                        initFragment();
                    } else {
                        Toast.makeText(JobReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(JobReportActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGGetReportsResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(JobReportActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initFragment() {

        if (isStartWork) {
            JobReportMainFragment frag = JobReportMainFragment.newInstance(mUserType);
            frag.setReportResult(mReportResults);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.job_report_container, frag)
                    .addToBackStack("report_main")
                    .commit();
        } else {
            JobReportSummaryFragment sumFrag = JobReportSummaryFragment.newInstance(mUserType);
            sumFrag.setReportResult(mReportResults);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.job_report_container, sumFrag)
                    .addToBackStack("report_summary")
                    .commit();
        }
    }

    public void setActionbarView(JGGActionbarView.EditStatus status) {
        if (status == ADD_TOOLS)
            actionbarView.setStatus(ADD_TOOLS, UNKNOWN);
        else if (status == ADD_BILLABLE_ITEM)
            actionbarView.setStatus(ADD_BILLABLE_ITEM, UNKNOWN);
        else if (status == JOB_REPORT)
            actionbarView.setStatus(JOB_REPORT, UNKNOWN);
    }

    private void actionbarViewItemClick(View view) {
        FragmentManager manager = getSupportFragmentManager();
        if (view.getId() == R.id.btn_back) {
            if (manager.getBackStackEntryCount() == 0)
                onBackPressed();
            else {
                if (actionbarView.getEditStatus() == ADD_TOOLS
                        || actionbarView.getEditStatus() == ADD_BILLABLE_ITEM) {
                    actionbarView.setStatus(JOB_REPORT, Global.AppointmentType.UNKNOWN);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.job_report_container, JobReportMainFragment.newInstance(mUserType))
                            .commit();
                } else
                    finish();
            }
        }
    }
}
