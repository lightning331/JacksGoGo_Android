package com.kelvin.jacksgogo.Activities.Jobs;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.CustomView.Views.JGGAlertView;
import com.kelvin.jacksgogo.CustomView.Views.PostProposalTabView;
import com.kelvin.jacksgogo.Fragments.Jobs.PostProposalMainTabFragment;
import com.kelvin.jacksgogo.Fragments.Jobs.PostProposalSummaryFragment;
import com.kelvin.jacksgogo.Fragments.Jobs.PostedProposalFragment;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGPostAppResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.JGGAppManager.currentUser;
import static com.kelvin.jacksgogo.Utils.JGGAppManager.selectedAppointment;
import static com.kelvin.jacksgogo.Utils.JGGAppManager.selectedProposal;
import static com.kelvin.jacksgogo.Utils.Global.ACCEPTED;
import static com.kelvin.jacksgogo.Utils.Global.EDIT;
import static com.kelvin.jacksgogo.Utils.Global.EDIT_STATUS;
import static com.kelvin.jacksgogo.Utils.Global.INVITE_PROPOSAL;
import static com.kelvin.jacksgogo.Utils.Global.MY_PROPOSAL;
import static com.kelvin.jacksgogo.Utils.Global.NONE;
import static com.kelvin.jacksgogo.Utils.Global.POST;
import static com.kelvin.jacksgogo.Utils.Global.VIEW;
import static com.kelvin.jacksgogo.Utils.Global.createProgressDialog;

public class PostProposalActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private JGGActionbarView actionbarView;
    private android.app.AlertDialog alertDialog;
    private ProgressDialog progressDialog;
    private String postedProposalID;

    private PostProposalSummaryFragment proposalSummaryFragment;
    private String status;
    public boolean isEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_proposal);

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            status = extra.getString(EDIT_STATUS);
        } else {
            status = NONE;
        }

        actionbarView = new JGGActionbarView(this);
        mToolbar = (Toolbar) findViewById(R.id.post_proposal_actionbar);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        actionbarView.setStatus(JGGActionbarView.EditStatus.POST_PROPOSAL, AppointmentType.UNKNOWN);
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
            // Create New Proposal Model
            selectedProposal = new JGGProposalModel();
            selectedProposal.setAppointmentID(selectedAppointment.getID());
            selectedProposal.setUserProfileID(currentUser.getID());
            selectedProposal.setCurrencyCode(selectedAppointment.getCurrencyCode());
            selectedProposal.setUserProfile(currentUser);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.post_proposal_container, PostProposalMainTabFragment.newInstance(PostProposalTabView.TabName.DESCRIBE,
                            PostProposalSummaryFragment.ProposalStatus.POST))
                    .commit();
        } else if (status.equals(INVITE_PROPOSAL)) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.post_proposal_container, PostProposalMainTabFragment.newInstance(PostProposalTabView.TabName.DESCRIBE,
                            PostProposalSummaryFragment.ProposalStatus.INVITE))
                    .commit();
        } else if (status.equals(EDIT)) {
            proposalSummaryFragment = new PostProposalSummaryFragment();
            proposalSummaryFragment.setEditStatus(PostProposalSummaryFragment.ProposalStatus.EDIT);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.post_proposal_container, proposalSummaryFragment)
                    .commit();
        } else if (status.equals(VIEW)){
            isEdit = true;
            actionbarView.setEditProposalMenu(true);
            onShowPostedProposalFragment();
        } else if (status.equals(ACCEPTED)) {
            actionbarView.setAcceptedBid(R.string.accepted_bid_title);
            onShowPostedProposalFragment();
        } else if (status.equals(MY_PROPOSAL)) {
            actionbarView.setAcceptedBid(R.string.my_proposal_title);
            onShowPostedProposalFragment();
        }
    }

    private void onShowPostedProposalFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.post_proposal_container, PostedProposalFragment.newInstance(status))
                .commit();
    }

    public void onEditProposal() {
        //showPostProposalAlertDialog();
        progressDialog = createProgressDialog(this);
        JGGAPIManager manager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGPostAppResponse> call = manager.editProposal(selectedProposal);
        call.enqueue(new Callback<JGGPostAppResponse>() {
            @Override
            public void onResponse(Call<JGGPostAppResponse> call, Response<JGGPostAppResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        postedProposalID = response.body().getValue();
                        selectedProposal.setID(postedProposalID);
                        showPostProposalAlertDialog();
                    } else {
                        Toast.makeText(PostProposalActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode  = response.code();
                    Toast.makeText(PostProposalActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGPostAppResponse> call, Throwable t) {
                Toast.makeText(PostProposalActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void actionbarViewItemClick(View view) {
        if (view.getId() == R.id.btn_back) {
            FragmentManager manager = getSupportFragmentManager();
            if (manager.getBackStackEntryCount() == 0) {
                if (status.equals(POST)
                        || status.equals(EDIT)
                        || status.equals(INVITE_PROPOSAL)) {
                    showAlertDialog();
                } else {
                    onBackPressed();
                }
            } else {
                actionbarView.setStatus(JGGActionbarView.EditStatus.POST_PROPOSAL, AppointmentType.UNKNOWN);
                actionbarView.mMoreButton.setVisibility(View.GONE);
                manager.popBackStack();
            }
        } else if (view.getId() == R.id.btn_more) {
            if (isEdit) {
                proposalSummaryFragment = new PostProposalSummaryFragment();
                proposalSummaryFragment.setEditStatus(PostProposalSummaryFragment.ProposalStatus.EDIT);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.post_proposal_container, proposalSummaryFragment)
                        .commit();
            } else {
                onEditProposal();
                //proposalSummaryFragment.showPostProposalAlertDialog();
            }
        }
    }

    public void showPostProposalAlertDialog() {
        JGGAlertView builder = new JGGAlertView(this,
                getResources().getString(R.string.alert_proposal_posted_title),
                "Proposal reference no: " + postedProposalID + '\n' +  '\n' + "Good luck!",
                false,
                "",
                R.color.JGGCyan,
                R.color.JGGCyan10Percent,
                getResources().getString(R.string.alert_view_proposal),
                R.color.JGGCyan);
        alertDialog = builder.create();
        builder.setOnItemClickListener(new JGGAlertView.OnItemClickListener() {
            @Override
            public void onDoneButtonClick(View view) {
                if (view.getId() == R.id.btn_alert_cancel)
                    alertDialog.dismiss();
                else {
                    alertDialog.dismiss();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.post_proposal_container, PostedProposalFragment.newInstance(EDIT))
                            .commit();
                }
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
        actionbarView.setEditProposalMenu(edit);
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

        title.setText(R.string.alert_quit_post_proposal_title);
        desc.setText(R.string.alert_quit_quotation_desc);
        okButton.setText(R.string.alert_quit_button);
        okButton.setBackgroundColor(ContextCompat.getColor(this, R.color.JGGRed));
        cancelButton.setBackgroundColor(ContextCompat.getColor(this, R.color.JGGCyan10Percent));
        cancelButton.setTextColor(ContextCompat.getColor(this, R.color.JGGCyan));
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                onBackPressed();
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }
}
