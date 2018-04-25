package com.kelvin.jacksgogo.Activities.Jobs;

import android.app.ProgressDialog;
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

import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.CustomView.Views.JGGAlertView;
import com.kelvin.jacksgogo.Fragments.Jobs.IncomingJobFragment;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.kelvin.jacksgogo.Utils.Global.JGGProposalStatus;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentActivityModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGContractModel;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGAppointmentActivityResponse;
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
import static com.kelvin.jacksgogo.Utils.Global.createProgressDialog;
import static com.kelvin.jacksgogo.Utils.JGGAppManager.currentUser;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getAppointmentTime;

public class IncomingJobActivity extends AppCompatActivity implements TextWatcher {

    @BindView(R.id.incoming_actionbar) Toolbar mToolbar;
    @BindView(R.id.img_detail) ImageView imgCategory;
    @BindView(R.id.lbl_title) TextView lblCategory;
    @BindView(R.id.lbl_date) TextView lblTime;
    @BindView(R.id.lblStatus) TextView lblCancel;

    public JGGActionbarView actionbarView;
    private ProgressDialog progressDialog;

    private JGGAppointmentModel mJob;
    private JGGContractModel mContract;
    private JGGProposalModel mProposal;
    private ArrayList<JGGProposalModel> mProposals = new ArrayList<>();
    private ArrayList<JGGAppointmentActivityModel> mActivities = new ArrayList<>();
    private boolean isRescheduleAllowed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_job);

        ButterKnife.bind(this);

        /* ---------    Custom view add to TopToolbar     --------- */
        actionbarView = new JGGActionbarView(this);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        mJob = JGGAppManager.getInstance().getSelectedAppointment();
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

        actionbarView.setDeleteJobStatus();
        getAppointmentActivities();
    }

    private void setCategory() {
        // Category
        Picasso.with(this)
                .load(mJob.getCategory().getImage())
                .placeholder(null)
                .into(imgCategory);
        lblCategory.setText(mJob.getCategory().getName());
        // Time
        lblTime.setText(getAppointmentTime(mJob));
    }

    private void actionbarViewItemClick(View view) {
        if (view.getId() == R.id.btn_more) {
            /* ---------    More button pressed     --------- */
            switch (actionbarView.getEditStatus()) {
                case NONE:
                    onWithdrawPopUpMenu(view);
                    break;
                case APPOINTMENT:
                    onWithdrawPopUpMenu(view);
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

    private void onIncomingJobFragment() {
        IncomingJobFragment providerFrag = new IncomingJobFragment();
        providerFrag.setAppointmentActivities(mActivities, mProposals, mContract);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.incoming_container, providerFrag)
                .commit();
    }

    public void withdrawFromJob(String reason) {

        String proposalID = mProposal.getID();

        progressDialog = createProgressDialog(this);

        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGProposalResponse> call = apiManager.withdrawProposal(proposalID);
        call.enqueue(new Callback<JGGProposalResponse>() {
            @Override
            public void onResponse(Call<JGGProposalResponse> call, Response<JGGProposalResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        getAppointmentActivities();
                    } else {
                        Toast.makeText(IncomingJobActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(IncomingJobActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGProposalResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(IncomingJobActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getAppointmentActivities() {
        progressDialog = createProgressDialog(this);
        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGAppointmentActivityResponse> call = apiManager.getAppointmentActivities(mJob.getID());
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
                        Toast.makeText(IncomingJobActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(IncomingJobActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGAppointmentActivityResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(IncomingJobActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getProposalsByJob() {
        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGProposalResponse> call = apiManager.getProposalsByJob(mJob.getID(), 0, 50);
        call.enqueue(new Callback<JGGProposalResponse>() {
            @Override
            public void onResponse(Call<JGGProposalResponse> call, Response<JGGProposalResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mProposals = response.body().getValue();

                        getContractByAppointment();
                    } else {
                        Toast.makeText(IncomingJobActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(IncomingJobActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGProposalResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(IncomingJobActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
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
                        onIncomingJobFragment();

                    } else {
                        Toast.makeText(IncomingJobActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(IncomingJobActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGGetContractResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(IncomingJobActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setStatus(JGGProposalModel proposal) {
        mProposal = proposal;
        isRescheduleAllowed = proposal.isRescheduleAllowed();
        if (proposal.getStatus() == JGGProposalStatus.withdrawn)
            actionbarView.setDeleteJobStatus();
        else
            actionbarView.setStatus(JGGActionbarView.EditStatus.APPOINTMENT, AppointmentType.UNKNOWN);
    }

    private void onReschedule() {

    }

    private void withDrawDialog() {
        String clientName = mJob.getUserProfile().getUser().getFullName();

        final JGGAlertView builder = new JGGAlertView(this,
                "Withdraw From Job?",
                "Let " + clientName + " know why you are withdrawing from the job.",
                true,
                getResources().getString(R.string.alert_cancel),
                R.color.JGGCyan,
                R.color.JGGCyan10Percent,
                getResources().getString(R.string.alert_withdraw),
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
                    withdrawFromJob(builder.txtReason.getText().toString());
                }
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    private void onWithdrawPopUpMenu(View view) {
        actionbarView.setEditMoreButtonClicked(true);

        PopupMenu popupMenu = new PopupMenu(this, view);
        if (isRescheduleAllowed)
            popupMenu.inflate(R.menu.reschedule_menu);
        else
            popupMenu.inflate(R.menu.withdraw_job_menu);
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

            if (menuItem.getItemId() == R.id.menu_option_reschedule) {  // Reschedule Job
                onReschedule();
            } else if (menuItem.getItemId() == R.id.menu_option_withdraw) {    // Withdraw From Job
                withDrawDialog();
            }
            return true;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
