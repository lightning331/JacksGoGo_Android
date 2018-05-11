package com.kelvin.jacksgogo.Activities.Search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.Fragments.Jobs.PostJobCategoryFragment;
import com.kelvin.jacksgogo.Fragments.Jobs.PostJobMainTabFragment;
import com.kelvin.jacksgogo.Fragments.Jobs.PostJobSummaryFragment;
import com.kelvin.jacksgogo.Fragments.Search.PostServiceMainTabFragment;
import com.kelvin.jacksgogo.Fragments.Search.PostServiceSkillNotVerifiedFragment;
import com.kelvin.jacksgogo.Fragments.Search.PostServiceSkillVerifiedFragment;
import com.kelvin.jacksgogo.Fragments.Search.PostServiceSummaryFragment;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global.PostStatus;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.System.JGGRegionModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserProfileModel;

import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.DUPLICATE;
import static com.kelvin.jacksgogo.Utils.Global.EDIT;
import static com.kelvin.jacksgogo.Utils.Global.EDIT_STATUS;
import static com.kelvin.jacksgogo.Utils.Global.GOCLUB;
import static com.kelvin.jacksgogo.Utils.Global.JOBS;
import static com.kelvin.jacksgogo.Utils.Global.POST;
import static com.kelvin.jacksgogo.Utils.Global.SERVICES;

public class PostServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private JGGActionbarView actionbarView;
    private android.app.AlertDialog alertDialog;

    private boolean alreadyVerifiedSkills = true;
    private String status;
    private AppointmentType appType;
    private JGGAppointmentModel selectedAppointment;
    private JGGUserProfileModel currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_service);

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            status = extra.getString(EDIT_STATUS);
            String type = extra.getString(APPOINTMENT_TYPE);
            if (type.equals(SERVICES))
                appType = AppointmentType.SERVICES;
            else if (type.equals(JOBS))
                appType = AppointmentType.JOBS;
        }

        currentUser = JGGAppManager.getInstance().getCurrentUser();

        actionbarView = new JGGActionbarView(this);
        mToolbar = (Toolbar) findViewById(R.id.post_service_actionbar);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        actionbarView.setStatus(JGGActionbarView.EditStatus.POST, AppointmentType.SERVICES);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        initFragment();
    }

    private void initFragment() {

        if (status.equals(POST)) {
            // Create New Appointment Model
            selectedAppointment = new JGGAppointmentModel();
            selectedAppointment.setUserProfile(currentUser);
            selectedAppointment.setUserProfileID(currentUser.getID());
            JGGRegionModel currentRegion = JGGAppManager.getInstance().getCurrentRegion();
            selectedAppointment.setRegion(currentRegion);
            selectedAppointment.setRegionID(currentRegion.getID());
            selectedAppointment.setCurrencyCode(currentRegion.getCurrencyCode());

            switch (appType) {
                case SERVICES:
                    selectedAppointment.setRequest(false);
                    actionbarView.setStatus(JGGActionbarView.EditStatus.POST, AppointmentType.SERVICES);
                    if (alreadyVerifiedSkills) {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.post_service_container, new PostServiceSkillVerifiedFragment())
                                .commit();
                    } else {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.post_service_container, new PostServiceSkillNotVerifiedFragment())
                                .commit();
                    }
                    break;
                case JOBS:
                    selectedAppointment.setRequest(true);
                    actionbarView.setStatus(JGGActionbarView.EditStatus.POST, AppointmentType.JOBS);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.post_service_container, PostJobCategoryFragment.newInstance(JOBS))
                            .commit();
                    break;
                default:
                    break;
            }

            JGGAppManager.getInstance().setSelectedAppointment(selectedAppointment);

        } else if (status.equals(EDIT) || status.equals(DUPLICATE)) {
            switch (appType) {
                case SERVICES:
                    actionbarView.setStatus(JGGActionbarView.EditStatus.POST, AppointmentType.SERVICES);
                    PostServiceSummaryFragment serviceFag = new PostServiceSummaryFragment();
                    if (status.equals(EDIT))
                        serviceFag.setEditStatus(PostServiceSummaryFragment.PostEditStatus.EDIT);
                    else if (status.equals(DUPLICATE))
                        serviceFag.setEditStatus(PostServiceSummaryFragment.PostEditStatus.DUPLICATE);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.post_service_container, serviceFag)
                            .commit();
                    break;
                case JOBS:
                    actionbarView.setStatus(JGGActionbarView.EditStatus.EDIT_JOB, AppointmentType.UNKNOWN);
                    PostJobSummaryFragment jobFrag = new PostJobSummaryFragment();
                    if (status.equals(EDIT))
                        jobFrag.setEditStatus(PostStatus.EDIT);
                    else if (status.equals(DUPLICATE))
                        jobFrag.setEditStatus(PostStatus.DUPLICATE);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.post_service_container, jobFrag)
                            .commit();
                    break;
                default:
                    break;
            }
        }
    }

    private void actionbarViewItemClick(View view) {
        if (view.getId() == R.id.btn_back) {
            FragmentManager manager = getSupportFragmentManager();
            if (manager.getBackStackEntryCount() == 0) {
                showAlertDialog();
            } else {
                Fragment fragment = manager.findFragmentById(R.id.post_service_container);
                if (fragment instanceof PostJobMainTabFragment) {
                    PostJobMainTabFragment frag = (PostJobMainTabFragment) fragment;
                    if (frag.getTabIndex() > 0) {
                        frag.setTabIndex(frag.getTabIndex() - 1);
                    } else {
                        manager.popBackStack();
                    }
                }
                else if (fragment instanceof PostServiceMainTabFragment) {
                    PostServiceMainTabFragment frag = (PostServiceMainTabFragment) fragment;
                    if (frag.getTabIndex() > 0) {
                        frag.setTabIndex(frag.getTabIndex() - 1);
                    } else {
                        manager.popBackStack();
                    }
                }
                else {
                    manager.popBackStack();
                }

            }
        }
    }

    private void showAlertDialog() {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        View alertView = inflater.inflate(R.layout.jgg_alert_view, null);
        builder.setView(alertView);
        alertDialog = builder.create();
        TextView cancelButton = (TextView) alertView.findViewById(R.id.btn_alert_cancel);
        TextView okButton = (TextView) alertView.findViewById(R.id.btn_alert_ok);
        TextView title = (TextView) alertView.findViewById(R.id.lbl_alert_titile);
        TextView desc = (TextView) alertView.findViewById(R.id.lbl_alert_description);

        if (appType == AppointmentType.JOBS) {
            title.setText(R.string.alert_quit_post_job_title);
            cancelButton.setBackgroundColor(ContextCompat.getColor(this, R.color.JGGCyan10Percent));
            cancelButton.setTextColor(ContextCompat.getColor(this, R.color.JGGCyan));
        } else if (appType == AppointmentType.SERVICES) {
            title.setText(R.string.alert_quit_post_service_title);
            cancelButton.setBackgroundColor(ContextCompat.getColor(this, R.color.JGGGreen10Percent));
            cancelButton.setTextColor(ContextCompat.getColor(this, R.color.JGGGreen));
        }
        desc.setText(R.string.alert_quit_quotation_desc);
        okButton.setText(R.string.alert_quit_button);
        okButton.setBackgroundColor(ContextCompat.getColor(this, R.color.JGGRed));
        cancelButton.setOnClickListener(this);
        okButton.setOnClickListener(this);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_alert_cancel) {
            alertDialog.dismiss();
        } else if (view.getId() == R.id.btn_alert_ok) {
            alertDialog.dismiss();
            onBackPressed();
        }
    }
}
