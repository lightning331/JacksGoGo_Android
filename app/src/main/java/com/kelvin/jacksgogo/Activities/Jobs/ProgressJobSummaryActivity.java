package com.kelvin.jacksgogo.Activities.Jobs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.Activities.Search.PostServiceActivity;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.Fragments.Jobs.ProgressJobFragment;
import com.kelvin.jacksgogo.Fragments.Jobs.ProgressProposalFragment;
import com.kelvin.jacksgogo.Fragments.Search.PostQuotationSummaryFragment;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel;
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
import static com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView.EditStatus.JOB_REPORT;
import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.currentUser;
import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.selectedAppointment;
import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.selectedProposal;
import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.EDIT;
import static com.kelvin.jacksgogo.Utils.Global.EDIT_STATUS;
import static com.kelvin.jacksgogo.Utils.Global.JGGProposalStatus.confirmed;
import static com.kelvin.jacksgogo.Utils.Global.JOBS;
import static com.kelvin.jacksgogo.Utils.Global.createProgressDialog;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getAppointmentTime;

public class ProgressJobSummaryActivity extends AppCompatActivity implements TextWatcher {

    @BindView(R.id.app_detail_actionbar) Toolbar mToolbar;
    @BindView(R.id.img_detail) ImageView imgCategory;
    @BindView(R.id.lbl_title) TextView lblCategory;
    @BindView(R.id.lbl_date) TextView lblTime;
    @BindView(R.id.lblStatus) TextView lblCancel;
    private EditText reason;

    public JGGActionbarView actionbarView;
    private ProgressJobFragment frag;
    private ProgressDialog progressDialog;

    private JGGAppointmentModel mJob;
    private JGGProposalModel mProposal = new JGGProposalModel();
    private ArrayList<JGGProposalModel> mProposals = new ArrayList<>();

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
        if (mJob.getUserProfileID().equals(currentUser.getID())) {
            actionbarView.setStatus(JGGActionbarView.EditStatus.APPOINTMENT, AppointmentType.UNKNOWN);
            getProposalsByJob();
        } else {
            actionbarView.setDeleteJobStatus();
            if (mJob.getProposal() == null)
                getProposedStatus();
            else {
                mProposal = mJob.getProposal();
                onProgressProposalFragment();
            }
        }

        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });
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
                onBackPressed();
            } else {
                if (actionbarView.getEditStatus() == JOB_DETAILS
                        || actionbarView.getEditStatus() == JOB_REPORT) {
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

    private void getProposalsByJob() {
        progressDialog = createProgressDialog(this);
        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGProposalResponse> call = apiManager.getProposalsByJob(mJob.getID(), 0, 40);
        call.enqueue(new Callback<JGGProposalResponse>() {
            @Override
            public void onResponse(Call<JGGProposalResponse> call, Response<JGGProposalResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mProposals = response.body().getValue();
                        for (JGGProposalModel p : mProposals) {
                            if (p.getStatus() == confirmed)
                                selectedProposal = p;
                        }
                        onProgressJobFragment();
                    } else {
                        Toast.makeText(ProgressJobSummaryActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode  = response.code();
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

    private void getProposedStatus() {
        progressDialog = createProgressDialog(this);
        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGProposalResponse> call = apiManager.getProposedStatus(selectedAppointment.getID(), currentUser.getID());
        call.enqueue(new Callback<JGGProposalResponse>() {
            @Override
            public void onResponse(Call<JGGProposalResponse> call, Response<JGGProposalResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mProposals = response.body().getValue();
                        mProposal = mProposals.get(0);
                        onProgressProposalFragment();
                    } else {
                        Toast.makeText(ProgressJobSummaryActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode  = response.code();
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

    private void onProgressJobFragment() {
        frag = new ProgressJobFragment();
        frag.setProposals(mProposals);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.app_detail_container, frag)
                .commit();
    }

    private void onProgressProposalFragment() {
        ProgressProposalFragment frag = new ProgressProposalFragment();
        frag.setProposal(mProposal);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.app_detail_container, frag)
                .commit();
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

    private void backToEditJobMainFragment() {
        actionbarView.setStatus(JGGActionbarView.EditStatus.EDIT_MAIN, AppointmentType.UNKNOWN);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.app_detail_container, PostQuotationSummaryFragment.newInstance(false))
                .commit();
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

    private void showDeleteJobDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (this).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.jgg_alert_view, null);
        builder.setView(dialogView);
        TextView desc = (TextView) dialogView.findViewById(R.id.lbl_alert_description);
        desc.setText(R.string.alert_edit_job_delete_desc);
        TextView cancelButton = (TextView) dialogView.findViewById(R.id.btn_alert_cancel);
        TextView deleteButton = (TextView) dialogView.findViewById(R.id.btn_alert_ok);
        reason = (EditText) dialogView.findViewById(R.id.txt_alert_reason);
        reason.addTextChangedListener(this);
        reason.setVisibility(View.VISIBLE);
        final AlertDialog alertDialog = builder.create();
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionbarView.setStatus(JGGActionbarView.EditStatus.APPOINTMENT, AppointmentType.UNKNOWN);
                alertDialog.dismiss();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                frag.deleteJob(reason.getText().toString());
            }
        });
        alertDialog.show();
    }

    public void deleteJobFinished() {
        lblCancel.setVisibility(View.VISIBLE);
        actionbarView.setDeleteJobStatus();
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

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (reason.getText().length() > 0) {

        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
