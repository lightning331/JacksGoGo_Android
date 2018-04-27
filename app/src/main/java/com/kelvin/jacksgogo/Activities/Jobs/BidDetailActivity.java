package com.kelvin.jacksgogo.Activities.Jobs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnDismissListener;
import com.bigkoo.alertview.OnItemClickListener;
import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewBehavior;
import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewHelper;
import com.kelvin.jacksgogo.Adapter.Jobs.BidDetailAdapter;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGBaseResponse;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.Global.JGGProposalStatus.confirmed;
import static com.kelvin.jacksgogo.Utils.Global.JGGProposalStatus.rejected;
import static com.kelvin.jacksgogo.Utils.Global.createProgressDialog;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getAppointmentTime;

public class BidDetailActivity extends AppCompatActivity implements View.OnClickListener, OnItemClickListener, OnDismissListener {

    @BindView(R.id.bid_detail_actionbar) Toolbar mToolbar;
    @BindView(R.id.bid_detail_navigation) BottomNavigationView mbtmView;;
    @BindView(R.id.bid_detail_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.btn_bid_detail_reject) TextView btnRejectBid;
    @BindView(R.id.btn_bid_detail_accept) TextView btnAcceptBid;
    @BindView(R.id.img_bid_detail_category) ImageView imgCategory;
    @BindView(R.id.lbl_bid_detail_category_title) TextView lblCategory;
    @BindView(R.id.lbl_bid_detail_category_date) TextView lblTime;

    private JGGActionbarView actionbarView;
    private AlertDialog alertDialog;
    private ProgressDialog progressDialog;

    private JGGAppointmentModel selectedAppointment;
    private JGGProposalModel mProposal;

    public static boolean getRandomBoolean() {
        return Math.random() < 0.5;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_detail);
        ButterKnife.bind(this);

        selectedAppointment = JGGAppManager.getInstance().getSelectedAppointment();
        mProposal = JGGAppManager.getInstance().getSelectedProposal();
        initView();
        setCategory();
    }

    private void initView() {

        // Hide Bottom NavigationView and ToolBar
        BottomNavigationViewHelper.disableShiftMode(mbtmView);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mbtmView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());
        mbtmView.setOnClickListener(this);
        btnRejectBid.setOnClickListener(this);
        btnAcceptBid.setOnClickListener(this);
        if (mProposal.getStatus() == rejected) {      // Rejected
            btnRejectBid.setVisibility(View.GONE);
        } else if (mProposal.getStatus() == confirmed)      // Accepted
            btnAcceptBid.setVisibility(View.GONE);

        // Top Navigationbar View
        actionbarView = new JGGActionbarView(this);
        mToolbar = (Toolbar) findViewById(R.id.bid_detail_actionbar);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        actionbarView.setStatus(JGGActionbarView.EditStatus.BID, AppointmentType.UNKNOWN);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        }

        BidDetailAdapter adapter = new BidDetailAdapter(this, mProposal);
        recyclerView.setAdapter(adapter);
    }

    private void setCategory() {
        // Category
        Picasso.with(this)
                .load(selectedAppointment.getCategory().getImage())
                .placeholder(null)
                .into(imgCategory);
        lblCategory.setText(selectedAppointment.getCategory().getName());
        // Time
        lblTime.setText(getAppointmentTime(mProposal.getAppointment()));
    }

    private void rejectProposal() {
        progressDialog = createProgressDialog(this);

        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, this);
        String proposalID = mProposal.getID();
        Call<JGGBaseResponse> call = apiManager.rejectProposal(proposalID);
        call.enqueue(new Callback<JGGBaseResponse>() {
            @Override
            public void onResponse(Call<JGGBaseResponse> call, Response<JGGBaseResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        BidDetailActivity.this.finish();
                    } else {
                        Toast.makeText(BidDetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode  = response.code();
                    Toast.makeText(BidDetailActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGBaseResponse> call, Throwable t) {
                Toast.makeText(BidDetailActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void actionbarViewItemClick(View view) {
        if (view.getId() == R.id.btn_back) {
            super.finish();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_bid_detail_reject) {
            onRejectProposalDialog();
        } else if (view.getId() == R.id.btn_bid_detail_accept) {
            boolean paymentVerified = getRandomBoolean();
            if (paymentVerified) {
                new AlertView("Choose a package:", null, "Cancel", null,
                        new String[]{"One-Time $ 100.00", "Package: $ 800.00 for 10"},
                        this, AlertView.Style.ActionSheet, this).show();
            } else {
                Intent intent = new Intent(this, AcceptBidActivity.class);
                startActivity(intent);
            }
        } else if (view.getId() == R.id.btn_alert_cancel) {
            alertDialog.dismiss();
        } else if (view.getId() == R.id.btn_alert_ok) {
            alertDialog.dismiss();
            rejectProposal();
        }
    }

    private void onRejectProposalDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (this).getLayoutInflater();
        View alertView = inflater.inflate(R.layout.jgg_alert_view, null);
        builder.setView(alertView);
        alertDialog = builder.create();
        TextView cancelButton = (TextView) alertView.findViewById(R.id.btn_alert_cancel);
        TextView okButton = (TextView) alertView.findViewById(R.id.btn_alert_ok);
        TextView title = (TextView) alertView.findViewById(R.id.lbl_alert_titile);
        TextView desc = (TextView) alertView.findViewById(R.id.lbl_alert_description);

        title.setText(R.string.alert_reject_title);
        okButton.setText(R.string.alert_reject_ok);
        okButton.setBackgroundColor(ContextCompat.getColor(this, R.color.JGGGreen));
        okButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        desc.setVisibility(View.GONE);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    @Override
    public void onDismiss(Object o) {

    }

    @Override
    public void onItemClick(Object o, int position) {
        if (position != AlertView.CANCELPOSITION) {
            Intent intent = new Intent(this, AcceptBidActivity.class);
            startActivity(intent);
        }
    }
}
