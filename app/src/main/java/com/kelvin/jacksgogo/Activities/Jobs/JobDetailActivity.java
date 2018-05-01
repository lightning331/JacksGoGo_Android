package com.kelvin.jacksgogo.Activities.Jobs;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewBehavior;
import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewHelper;
import com.kelvin.jacksgogo.Adapter.Jobs.JobDetailsAdapter;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.CustomView.Views.JGGShareIntentDialog;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGJobInfoModel;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserProfileModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGGetJobInfoResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGProposalResponse;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.Global.EDIT_STATUS;
import static com.kelvin.jacksgogo.Utils.Global.POST;
import static com.kelvin.jacksgogo.Utils.Global.VIEW;
import static com.kelvin.jacksgogo.Utils.Global.createProgressDialog;

public class JobDetailActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.job_detail_actionbar) Toolbar mToolbar;
    @BindView(R.id.job_detail_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.btn_job_detail_make_proposal) TextView btnMakeProposal;
    @BindView(R.id.lbl_booked_count) TextView lblViewedCount;
    @BindView(R.id.lbl_viewing_count) TextView lblViewingCount;
    @BindView(R.id.lbl_booked_title) TextView lblViewedCountDesc;

    private JGGActionbarView actionbarView;
    private BottomNavigationView mbtmView;
    private JobDetailsAdapter adapter;
    private AlertDialog alertDialog;
    private ProgressDialog progressDialog;

    private JGGAppointmentModel selectedAppointment;
    private JGGUserProfileModel currentUser;
    private JGGProposalModel mProposal;
    private JGGJobInfoModel mJobInfo;
    private boolean reportFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        ButterKnife.bind(this);

        selectedAppointment = JGGAppManager.getInstance().getSelectedAppointment();
        currentUser = JGGAppManager.getInstance().getCurrentUser();
        // Hide Bottom NavigationView and ToolBar
        mbtmView = findViewById(R.id.job_detail_bottom);
        btnMakeProposal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JobDetailActivity.this, PostProposalActivity.class);
                if (mProposal == null) {
                    // TODO : New Proposal
                    intent.putExtra(EDIT_STATUS, POST);
                } else {
                    // TODO : Already proposed
                    JGGAppManager.getInstance().setSelectedProposal(mProposal);
                    intent.putExtra(EDIT_STATUS, VIEW);
                }
                startActivity(intent);
            }
        });
        mbtmView.setVisibility(View.GONE);

        // Top ActionbarView
        actionbarView = new JGGActionbarView(this);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);
        actionbarView.setStatus(JGGActionbarView.EditStatus.DETAILS, AppointmentType.JOBS);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        }
        adapter = new JobDetailsAdapter(this);
        mRecyclerView.setAdapter(adapter);

        // Get Proposed status
        getProposedStatus();

        // Get Appointment Information - AveragePrice, ProposalCount, LastRespondOn
        getInformationOfAppointment();
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

                        ArrayList<JGGProposalModel> proposals = response.body().getValue();
                        // TODO : Already proposed
                        if (proposals.size() > 0) {
                            mProposal = proposals.get(0);
                            btnMakeProposal.setText("View Proposal");
                            onShowBottomView();
                        }
                        // TODO : New Proposal
                        if (!selectedAppointment.getUserProfileID().equals(currentUser.getID())) {
                            onShowBottomView();
                        } else {
                            // TODO : It is my Job
                        }

                    } else {
                        Toast.makeText(JobDetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode  = response.code();
                    Toast.makeText(JobDetailActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGProposalResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(JobDetailActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getInformationOfAppointment() {
        JGGAPIManager apiManager = JGGURLManager.getClient().create(JGGAPIManager.class);
        Call<JGGGetJobInfoResponse> call = apiManager.getInformationOfAppointment(selectedAppointment.getID());
        call.enqueue(new Callback<JGGGetJobInfoResponse>() {
            @Override
            public void onResponse(Call<JGGGetJobInfoResponse> call, Response<JGGGetJobInfoResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {

                        mJobInfo = response.body().getValue();

                        adapter.notifyDataChanged(mJobInfo);
                        adapter.notifyDataSetChanged();
                        //mRecyclerView.setAdapter(adapter);

                    } else {
                        Toast.makeText(JobDetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(JobDetailActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGGetJobInfoResponse> call, Throwable t) {
                //Toast.makeText(JobDetailActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onShowBottomView() {
        mbtmView.setVisibility(View.VISIBLE);
        BottomNavigationViewHelper.disableShiftMode(mbtmView);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mbtmView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());
    }

    private void actionbarViewItemClick(View view) {
        if (view.getId() == R.id.btn_like_original) {
            actionbarView.setLikeButtonClicked(actionbarView.mLikeButtonSelected);
        } else if (view.getId() == R.id.btn_more) {
            actionbarView.setMoreButtonClicked(true);
            // Show Edit PopUp Menu
            showEditPopUpMenu(view);
        } else if (view.getId() == R.id.btn_back) {
            this.finish();
        }
    }

    private void showEditPopUpMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.job_share_menu);

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
            actionbarView.setMoreButtonClicked(false);
        }
    }

    private class OnMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            if (menuItem.getItemId() == R.id.menu_option_share) {  // Share the Job
                openShareDialog();
            } else if (menuItem.getItemId() == R.id.menu_option_report_job) {    // Report the Job
                showReportDialog(false);
            }
            return true;
        }
    }

    private void showReportDialog(final boolean reportFlag) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (this).getLayoutInflater();

        View alertView = inflater.inflate(R.layout.jgg_alert_view, null);
        builder.setView(alertView);
        alertDialog = builder.create();
        TextView cancelButton = (TextView) alertView.findViewById(R.id.btn_alert_cancel);
        TextView reportButton = (TextView) alertView.findViewById(R.id.btn_alert_ok);
        TextView title = (TextView) alertView.findViewById(R.id.lbl_alert_titile);
        TextView description = (TextView) alertView.findViewById(R.id.lbl_alert_description);

        title.setText(R.string.alert_report_job_title);
        description.setText(R.string.alert_report_service_desc);
        reportButton.setText(R.string.alert_report_service_ok);
        reportButton.setBackgroundColor(getResources().getColor(R.color.JGGCyan));
        cancelButton.setBackgroundColor(getResources().getColor(R.color.JGGCyan10Percent));
        cancelButton.setTextColor(getResources().getColor(R.color.JGGCyan));
        if (reportFlag) {
            alertDialog.setCanceledOnTouchOutside(false);
            cancelButton.setVisibility(View.GONE);
            title.setText(R.string.alert_report_service_thanks_title);
            description.setText(R.string.alert_report_service_thanks_desc);
            reportButton.setText(R.string.alert_done);
        }
        cancelButton.setOnClickListener(this);
        reportButton.setOnClickListener(this);
        alertDialog.show();
    }

    private void openShareDialog() {
        JGGShareIntentDialog shareDialog = new JGGShareIntentDialog.Builder(this)
                .setDialogTitle("Share with your friends")
                .setShareLink(null)
                .build();
        shareDialog.show();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_alert_cancel) {
            alertDialog.dismiss();
        } else if (view.getId() == R.id.btn_alert_ok) {
            if (!reportFlag) {
                alertDialog.dismiss();
                showReportDialog(true);
            } else {
                alertDialog.dismiss();
            }
            reportFlag = !reportFlag;
        }
    }
}
