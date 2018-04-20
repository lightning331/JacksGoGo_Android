package com.kelvin.jacksgogo.Activities.Jobs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.Activities.Search.PostServiceActivity;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.CustomView.Views.JGGAlertView;
import com.kelvin.jacksgogo.Fragments.Jobs.ProgressJobClientFragment;
import com.kelvin.jacksgogo.Fragments.Jobs.ProgressJobProviderFragment;
import com.kelvin.jacksgogo.Fragments.Search.PostQuotationSummaryFragment;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentActivityModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGContractModel;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGAppointmentActivityResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGBaseResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGGetContractResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGProposalResponse;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView.EditStatus.APPOINTMENT;
import static com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView.EditStatus.JOB_DETAILS;
import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.EDIT;
import static com.kelvin.jacksgogo.Utils.Global.EDIT_STATUS;
import static com.kelvin.jacksgogo.Utils.Global.JGGJobStatus.deleted;
import static com.kelvin.jacksgogo.Utils.Global.JOBS;
import static com.kelvin.jacksgogo.Utils.Global.createProgressDialog;
import static com.kelvin.jacksgogo.Utils.JGGAppManager.currentUser;
import static com.kelvin.jacksgogo.Utils.JGGAppManager.selectedAppointment;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getAppointmentTime;

public class ProgressJobSummaryActivity extends AppCompatActivity implements TextWatcher {

    @BindView(R.id.app_detail_actionbar) Toolbar mToolbar;
    @BindView(R.id.img_detail) ImageView imgCategory;
    @BindView(R.id.lbl_title) TextView lblCategory;
    @BindView(R.id.lbl_date) TextView lblTime;
    @BindView(R.id.lblStatus) TextView lblCancel;

    public JGGActionbarView actionbarView;
    private ProgressDialog progressDialog;

    private JGGAppointmentModel mJob;
    private JGGContractModel mContract;
    private ArrayList<JGGProposalModel> mProposals = new ArrayList<>();
    private ArrayList<JGGAppointmentActivityModel> mActivities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_job_summary);

        ButterKnife.bind(this);

        /* ---------    Custom view add to TopToolbar     --------- */
        actionbarView = new JGGActionbarView(this);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        mJob = selectedAppointment;
        setCategory();

        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mJob.getUserProfileID().equals(currentUser.getID())) {
            actionbarView.setStatus(JGGActionbarView.EditStatus.APPOINTMENT, AppointmentType.UNKNOWN);
        } else {
            actionbarView.setDeleteJobStatus();
        }
        getAppointmentActivities();
    }

    private void setCategory() {
        // Category
        Picasso.with(this)
                .load(selectedAppointment.getCategory().getImage())
                .placeholder(null)
                .into(imgCategory);
        lblCategory.setText(selectedAppointment.getCategory().getName());
        // Time
        lblTime.setText(getAppointmentTime(mJob));
    }

    private void onProgressJobFragment() {
        ProgressJobClientFragment clientFrag = new ProgressJobClientFragment();
        clientFrag.setAppointmentActivities(mActivities, mProposals, mContract);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.app_detail_container, clientFrag)
                .commit();
    }

    private void onProgressProposalFragment() {
        ProgressJobProviderFragment providerFrag = new ProgressJobProviderFragment();
        providerFrag.setAppointmentActivities(mActivities, mProposals, mContract);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.app_detail_container, providerFrag)
                .commit();
    }

    public void deleteJob(String reason) {
        selectedAppointment.setStatus(deleted);
        selectedAppointment.setReason(reason);
        mJob = selectedAppointment;
        String jobID = mJob.getID();

        progressDialog = createProgressDialog(this);

        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGBaseResponse> call = apiManager.deleteJob(jobID, reason);
        call.enqueue(new Callback<JGGBaseResponse>() {
            @Override
            public void onResponse(Call<JGGBaseResponse> call, Response<JGGBaseResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        deleteJobFinished();
                        getAppointmentActivities();
                    } else {
                        Toast.makeText(ProgressJobSummaryActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode  = response.code();
                    Toast.makeText(ProgressJobSummaryActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGBaseResponse> call, Throwable t) {
                Toast.makeText(ProgressJobSummaryActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    public void getAppointmentActivities() {
        progressDialog = createProgressDialog(this);
        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGAppointmentActivityResponse> call = apiManager.getAppointmentActivities(selectedAppointment.getID());
        call.enqueue(new Callback<JGGAppointmentActivityResponse>() {
            @Override
            public void onResponse(Call<JGGAppointmentActivityResponse> call, Response<JGGAppointmentActivityResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mActivities = response.body().getValue();
                        String userID = currentUser.getID();
                        getProposalsByJob();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(ProgressJobSummaryActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(ProgressJobSummaryActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGAppointmentActivityResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ProgressJobSummaryActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getProposalsByJob() {
        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGProposalResponse> call = apiManager.getProposalsByJob(mJob.getID(), 0, 50);
        call.enqueue(new Callback<JGGProposalResponse>() {
            @Override
            public void onResponse(Call<JGGProposalResponse> call, Response<JGGProposalResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mProposals = response.body().getValue();

                        getContractByAppointment();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(ProgressJobSummaryActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(ProgressJobSummaryActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGProposalResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ProgressJobSummaryActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Dump API
    private void getContractByAppointment() {
        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGGetContractResponse> call = apiManager.getContractByAppointment(mJob.getID());
        call.enqueue(new Callback<JGGGetContractResponse>() {
            @Override
            public void onResponse(Call<JGGGetContractResponse> call, Response<JGGGetContractResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mContract = response.body().getValue();
                        // Todo: send Appointment Status
                        if (mJob.getUserProfileID().equals(currentUser.getID())) {
                            onProgressJobFragment();
                        } else {
                            onProgressProposalFragment();
                        }
                    } else {
                        Toast.makeText(ProgressJobSummaryActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ProgressJobSummaryActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGGetContractResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ProgressJobSummaryActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setStatus(JGGActionbarView.EditStatus status) {
        actionbarView.setStatus(status, AppointmentType.UNKNOWN);
    }

    private void onEditJob() {
        Intent intent = new Intent(this, PostServiceActivity.class);
        intent.putExtra(EDIT_STATUS, EDIT);
        intent.putExtra(APPOINTMENT_TYPE, JOBS);
        startActivity(intent);
    }

    private void actionbarViewItemClick(View view) {
        if (view.getId() == R.id.btn_more) {
            /* ---------    More button pressed     --------- */
            switch (actionbarView.getEditStatus()) {
                case NONE:
                    onShowEditPopUpMenu(view);
                    break;
                case APPOINTMENT:
                    onShowEditPopUpMenu(view);
                    break;
                case EDIT_MAIN:
                    //showJobStatusSummaryFragment();
                    break;
                case EDIT_DETAIL:
                    //backToEditJobMainFragment();
                    break;
                default:
                    break;
            }
        } else if (view.getId() == R.id.btn_back) {
            if (actionbarView.getEditStatus() == null) {
                FragmentManager manager = getSupportFragmentManager();
                if (manager.getBackStackEntryCount() == 0)
                    onBackPressed();
                else
                    manager.popBackStack();
            } else {
                if (actionbarView.getEditStatus() == JOB_DETAILS) {
                    if (mJob.getUserProfileID().equals(currentUser.getID()))
                        actionbarView.setStatus(JGGActionbarView.EditStatus.APPOINTMENT, AppointmentType.UNKNOWN);
                    else
                        actionbarView.setDeleteJobStatus();
                    onBackPressed();
                } else if (actionbarView.getEditStatus() == APPOINTMENT)
                    finish();
            }
        }
    }

    private void showDeleteJobDialog() {
        final JGGAlertView builder = new JGGAlertView(this,
                "Delete Job?",
                getResources().getString(R.string.alert_edit_job_delete_desc),
                true,
                getResources().getString(R.string.alert_cancel),
                R.color.JGGGreen,
                R.color.JGGGreen10Percent,
                getResources().getString(R.string.alert_ok),
                R.color.JGGRed);
        final android.app.AlertDialog alertDialog = builder.create();
        builder.txtReason.addTextChangedListener(this);
        builder.setOnItemClickListener(new JGGAlertView.OnItemClickListener() {
            @Override
            public void onDoneButtonClick(View view) {
                if (view.getId() == R.id.btn_alert_cancel)
                    alertDialog.dismiss();
                else {
                    alertDialog.dismiss();
                    deleteJob(builder.txtReason.getText().toString());
                }
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    public void deleteJobFinished() {
        lblCancel.setVisibility(View.VISIBLE);
        actionbarView.setDeleteJobStatus();
    }

    private void onShowEditPopUpMenu(View view) {
        actionbarView.setEditMoreButtonClicked(true);

        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.edit_menu);
        popupMenu.setOnDismissListener(new OnDismissListener());
        popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener());

        // Force icons to show in Custom Overflow Menu
        Object menuHelper;
        Class[] argTypes;
        try {
            Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
            fMenuHelper.setAccessible(true);
            menuHelper = fMenuHelper.get(popupMenu);
            argTypes = new Class[] { boolean.class };
            menuHelper.getClass().getDeclaredMethod("setForceShowIcon", argTypes).invoke(menuHelper, true);
        } catch (Exception e) {
            popupMenu.show();
            return;
        }
        popupMenu.show();
    }

    private class OnDismissListener implements PopupMenu.OnDismissListener {
        @Override
        public void onDismiss(PopupMenu menu) {
            if (actionbarView.getEditStatus() == JGGActionbarView.EditStatus.APPOINTMENT
                    || actionbarView.getEditStatus() == JGGActionbarView.EditStatus.NONE)
                actionbarView.setEditMoreButtonClicked(false);
        }
    }

    private class OnMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            if (menuItem.getItemId() == R.id.menu_option_delete) {  // Delete Job
                showDeleteJobDialog();
            } else if (menuItem.getItemId() == R.id.menu_option_edit) {    // Edit Job
                onEditJob();
            }
            return true;
        }
    }

    private void backToEditJobMainFragment() {
        actionbarView.setStatus(JGGActionbarView.EditStatus.EDIT_MAIN, AppointmentType.UNKNOWN);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.app_detail_container, PostQuotationSummaryFragment.newInstance(false))
                .commit();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
