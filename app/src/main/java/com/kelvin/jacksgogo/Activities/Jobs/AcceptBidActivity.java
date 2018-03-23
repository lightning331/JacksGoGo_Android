package com.kelvin.jacksgogo.Activities.Jobs;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGBaseResponse;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.selectedProposal;
import static com.kelvin.jacksgogo.Utils.Global.createProgressDialog;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.convertJobTimeString;

public class AcceptBidActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.accept_bid_actionbar) Toolbar mToolbar;
    @BindView(R.id.img_category) ImageView imgCategory;
    @BindView(R.id.lbl_category_name) TextView lblCategory;
    @BindView(R.id.lbl_time) TextView lblTime;
    @BindView(R.id.img_avatar) RoundedImageView userAvatar;
    @BindView(R.id.lbl_username) TextView lblUserName;
    @BindView(R.id.user_ratingbar) MaterialRatingBar ratingBar;
    @BindView(R.id.lbl_accept_bid_price) TextView lblBudget;
    @BindView(R.id.btn_setup) TextView btnSetUp;
    @BindView(R.id.btn_credit_card) TextView btnCredit;

    private JGGActionbarView actionbarView;
    private ProgressDialog progressDialog;

    private JGGProposalModel mProposal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_bid);
        ButterKnife.bind(this);

        // Top Navigationbar View
        actionbarView = new JGGActionbarView(this);
        mToolbar = (Toolbar) findViewById(R.id.accept_bid_actionbar);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        actionbarView.setStatus(JGGActionbarView.EditStatus.ACCEPT_BIDE, AppointmentType.UNKNOWN);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        initView();
    }

    private void initView() {
        btnSetUp.setOnClickListener(this);
        btnCredit.setOnClickListener(this);

        mProposal = selectedProposal;

        // Category
        Picasso.with(this)
                .load(mProposal.getAppointment().getCategory().getImage())
                .placeholder(null)
                .into(imgCategory);
        lblCategory.setText(mProposal.getAppointment().getCategory().getName());
        // Time
        lblTime.setText(convertJobTimeString(mProposal.getAppointment()));
    }

    private void approveProposal() {
        progressDialog = createProgressDialog(this);

        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, this);
        String proposalID = mProposal.getID();
        String appointmentID = mProposal.getAppointmentID();
        String currencyCode = mProposal.getCurrencyCode();
        Double grossAmt = mProposal.getBudget();
        Call<JGGBaseResponse> call = apiManager.approveProposal(appointmentID, proposalID, grossAmt, currencyCode);
        call.enqueue(new Callback<JGGBaseResponse>() {
            @Override
            public void onResponse(Call<JGGBaseResponse> call, Response<JGGBaseResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {

                    } else {
                        Toast.makeText(AcceptBidActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode  = response.code();
                    Toast.makeText(AcceptBidActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGBaseResponse> call, Throwable t) {
                Toast.makeText(AcceptBidActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View view) {
        approveProposal();
        if (view.getId() == R.id.btn_setup) {

        } else if (view.getId() == R.id.btn_credit_card) {

        }
    }

    private void actionbarViewItemClick(View view) {
        if (view.getId() == R.id.btn_back) {
            super.finish();
        }
    }
}
