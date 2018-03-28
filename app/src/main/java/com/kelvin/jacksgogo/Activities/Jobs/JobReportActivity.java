package com.kelvin.jacksgogo.Activities.Jobs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.Fragments.Jobs.JobReportSummaryFragment;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.kelvin.jacksgogo.Utils.Global.JGG_USERTYPE;

public class JobReportActivity extends AppCompatActivity {

    @BindView(R.id.job_report_actionbar) Toolbar mToolbar;

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

        actionbarView.setStatus(JGGActionbarView.EditStatus.JOB_REPORT, Global.AppointmentType.UNKNOWN);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                onBackPressed();
            }
        });

        if (isStartWork)
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.job_report_container, JobReportSummaryFragment.newInstance(mUserType))
                    .commit();
        else
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.job_report_container, JobReportSummaryFragment.newInstance(mUserType))
                    .commit();
    }
}
