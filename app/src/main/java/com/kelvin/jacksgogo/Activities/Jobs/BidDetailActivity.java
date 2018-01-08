package com.kelvin.jacksgogo.Activities.Jobs;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnDismissListener;
import com.bigkoo.alertview.OnItemClickListener;
import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewBehavior;
import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewHelper;
import com.kelvin.jacksgogo.Adapter.Jobs.BidDetailAdapter;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.Utils.Models.JGGBiddingProviderModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppBaseModel;
import com.kelvin.jacksgogo.R;

public class BidDetailActivity extends AppCompatActivity implements View.OnClickListener, OnItemClickListener, OnDismissListener {

    private Toolbar mToolbar;
    private JGGActionbarView actionbarView;
    private BottomNavigationView mbtmView;;
    private RecyclerView recyclerView;
    private TextView btnRejectBid;
    private TextView btnAcceptBid;

    private AlertDialog alertDialog;
    private JGGBiddingProviderModel provider;

    public static boolean getRandomBoolean() {
        return Math.random() < 0.5;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_detail);

        initView();
    }

    private void initView() {

        // Hide Bottom NavigationView and ToolBar
        mbtmView = (BottomNavigationView) findViewById(R.id.bid_detail_navigation);
        BottomNavigationViewHelper.disableShiftMode(mbtmView);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mbtmView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());
        mbtmView.setOnClickListener(this);

        btnRejectBid = (TextView) findViewById(R.id.btn_bid_detail_reject);
        btnRejectBid.setOnClickListener(this);
        btnAcceptBid = (TextView) findViewById(R.id.btn_bid_detail_accept);
        btnAcceptBid.setOnClickListener(this);
        String status = getIntent().getStringExtra("bid_status");
        if (status.equals("Rejected")) {
        btnAcceptBid.setOnClickListener(this);
            btnRejectBid.setVisibility(View.GONE);
        }

        // Top Navigationbar View
        actionbarView = new JGGActionbarView(this);
        mToolbar = (Toolbar) findViewById(R.id.bid_detail_actionbar);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        actionbarView.setStatus(JGGActionbarView.EditStatus.BID, JGGAppBaseModel.AppointmentType.UNKNOWN);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.bid_detail_recycler_view);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        }

        BidDetailAdapter adapter = new BidDetailAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    private void actionbarViewItemClick(View view) {
        if (view.getId() == R.id.btn_back) {
            super.finish();
        }
    }

    private void onShowAlertDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (this).getLayoutInflater();
        //JGGAlertView alertView = new JGGAlertView(this);
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
    public void onClick(View view) {
        if (view.getId() == R.id.btn_bid_detail_reject) {
            onShowAlertDialog();
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
            super.finish();
        }
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
