package com.kelvin.jacksgogo.Activities.Search;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.Fragments.Jobs.PostJobCategoryFragment;
import com.kelvin.jacksgogo.Fragments.Search.PostServiceSkillNotVerifiedFragment;
import com.kelvin.jacksgogo.Fragments.Search.PostServiceSkillVerifiedFragment;
import com.kelvin.jacksgogo.Fragments.Search.PostServiceSummaryFragment;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppBaseModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGJobModel;
import com.kelvin.jacksgogo.Utils.Models.System.JGGAddressModel;
import com.kelvin.jacksgogo.Utils.Models.System.JGGJobTimeModel;
import com.kelvin.jacksgogo.Utils.Models.System.JGGRegionModel;
import com.kelvin.jacksgogo.Utils.Models.System.JGGTimeSlotModel;

import java.util.ArrayList;

import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.GOCLUB;
import static com.kelvin.jacksgogo.Utils.Global.JOBS;
import static com.kelvin.jacksgogo.Utils.Global.SERVICES;

public class PostServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private JGGActionbarView actionbarView;
    private android.app.AlertDialog alertDialog;

    private boolean alreadyVerifiedSkills = true;//getRandomBoolean();
    private String status;
    private JGGAppBaseModel.AppointmentType appType;

    public JGGCategoryModel selectedCategory;
    public JGGJobModel creatingAppointment;
    public int selectedPeopleType = 0;
    public ArrayList<JGGTimeSlotModel> arrayOnePersonTimeSlots;
    public ArrayList<JGGTimeSlotModel> arrayMultiplePeopleTimeSlots;

    public static boolean getRandomBoolean() {
        return Math.random() < 0.5;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_service);

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            status = extra.getString("EDIT_STATUS");
            String type = extra.getString(APPOINTMENT_TYPE);
            if (type.equals(SERVICES))
                appType = JGGAppBaseModel.AppointmentType.SERVICES;
            else if (type.equals(JOBS))
                appType = JGGAppBaseModel.AppointmentType.JOBS;
            else if (type.equals(GOCLUB))
                appType = JGGAppBaseModel.AppointmentType.GOCLUB;
        } else {
            status = "None";
        }

        actionbarView = new JGGActionbarView(this);
        mToolbar = (Toolbar) findViewById(R.id.post_service_actionbar);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        actionbarView.setStatus(JGGActionbarView.EditStatus.POST, JGGAppBaseModel.AppointmentType.SERVICES);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        initFragment();
    }

    private void initFragment() {

        // Create New Appointment Model
        creatingAppointment = new JGGJobModel();
        creatingAppointment.setUserProfileID(JGGAppManager.getInstance(this).currentUser.getID());
        JGGRegionModel currentRegion = JGGAppManager.getInstance(this).getCurrentRegion();
        creatingAppointment.setRegion(currentRegion);
        creatingAppointment.setRegionID(currentRegion.getID());
        creatingAppointment.setCurrencyCode(currentRegion.getCurrencyCode());

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        PostServiceSummaryFragment frag;
        switch (status) {
            case "Edit":
                frag = new PostServiceSummaryFragment();
                ft.replace(R.id.post_service_container, frag, frag.getTag());
                frag.setEditStatus(PostServiceSummaryFragment.PostEditStatus.EDIT);
                ft.commit();
                break;
            case "Duplicate":
                frag = new PostServiceSummaryFragment();
                ft.replace(R.id.post_service_container, frag, frag.getTag());
                frag.setEditStatus(PostServiceSummaryFragment.PostEditStatus.DUPLICATE);
                ft.commit();
                break;
            // Appointment Post
            case "None":
                switch (appType) {
                    case SERVICES:
                        actionbarView.setStatus(JGGActionbarView.EditStatus.POST, JGGAppBaseModel.AppointmentType.SERVICES);
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
                        actionbarView.setStatus(JGGActionbarView.EditStatus.POST, JGGAppBaseModel.AppointmentType.JOBS);
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.post_service_container, new PostJobCategoryFragment())
                                .commit();
                        break;
                    case GOCLUB:
                        actionbarView.setStatus(JGGActionbarView.EditStatus.POST, JGGAppBaseModel.AppointmentType.GOCLUB);
                        break;
                    default:
                        break;
                }
                ft.commit();
                break;
            default:
                break;
        }
    }

    private void actionbarViewItemClick(View view) {
        if (view.getId() == R.id.btn_back) {
            FragmentManager manager = getSupportFragmentManager();
            if (manager.getBackStackEntryCount() == 0) {
                if (appType == JGGAppBaseModel.AppointmentType.JOBS) showAlertDialog();
                else super.onBackPressed();
            } else {
                manager.popBackStack();
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

        title.setText(R.string.alert_quit_post_job_title);
        desc.setText(R.string.alert_quit_quotation_desc);
        okButton.setText(R.string.alert_quit_button);
        okButton.setBackgroundColor(ContextCompat.getColor(this, R.color.JGGRed));
        cancelButton.setBackgroundColor(ContextCompat.getColor(this, R.color.JGGCyan10Percent));
        cancelButton.setTextColor(ContextCompat.getColor(this, R.color.JGGCyan));
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
            super.onBackPressed();
        }
    }
}
