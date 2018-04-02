package com.kelvin.jacksgogo.Activities.Jobs;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.Fragments.Jobs.JobReportMainFragment;
import com.kelvin.jacksgogo.Fragments.Jobs.JobReportSummaryFragment;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView.EditStatus.ADD_BILLABLE_ITEM;
import static com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView.EditStatus.ADD_TOOLS;
import static com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView.EditStatus.JOB_REPORT;
import static com.kelvin.jacksgogo.Utils.JGGAppManager.selectedAppointment;
import static com.kelvin.jacksgogo.Utils.Global.AppointmentType.UNKNOWN;
import static com.kelvin.jacksgogo.Utils.Global.JGG_USERTYPE;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getAppointmentTime;

public class JobReportActivity extends AppCompatActivity {

    @BindView(R.id.job_report_actionbar) Toolbar mToolbar;
    @BindView(R.id.img_detail) ImageView imgCategory;
    @BindView(R.id.lbl_title) TextView lblCategory;
    @BindView(R.id.lbl_date) TextView lblJobTime;

    private JGGActionbarView actionbarView;

    private String mUserType;
    private boolean isStartWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_report);
        ButterKnife.bind(this);

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            mUserType = extra.getString(JGG_USERTYPE);
            isStartWork = extra.getBoolean("work_start");
        }

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

        if (isStartWork)
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.job_report_container, JobReportMainFragment.newInstance(mUserType))
                    .addToBackStack("report_main")
                    .commit();
        else
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.job_report_container, JobReportSummaryFragment.newInstance(mUserType))
                    .addToBackStack("report_summary")
                    .commit();

        setCategory();
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
