package com.kelvin.jacksgogo.Activities.Search;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewBehavior;
import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewHelper;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.CustomView.Views.JGGShareIntentDialog;
import com.kelvin.jacksgogo.Fragments.Search.ServiceDetailFragment;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppBaseModel;

import java.lang.reflect.Field;

import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.GOCLUB;
import static com.kelvin.jacksgogo.Utils.Global.JOBS;
import static com.kelvin.jacksgogo.Utils.Global.SERVICES;

public class ServiceDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private JGGActionbarView actionbarView;
    private LinearLayout viewedLayout;
    private TextView lblViewedCount;
    private TextView lblViewedCountDesc;
    private TextView lblViewingCount;
    private AlertDialog alertDialog;

    private boolean isService = false;
    private boolean reportFlag = false;
    private String appType;
    private TextView bottomTitle;
    private int mColor;
    private int mColorPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);

        Bundle bundle = getIntent().getExtras();
        isService = bundle.getBoolean("is_service");
        appType = bundle.getString(APPOINTMENT_TYPE);

        lblViewedCount = (TextView) findViewById(R.id.lbl_booked_count);
        lblViewedCountDesc = (TextView) findViewById(R.id.lbl_booked_title);
        lblViewingCount = (TextView) findViewById(R.id.lbl_viewing_count);
        viewedLayout = (LinearLayout) findViewById(R.id.bottom_top_view_layout);

        // Hide Bottom NavigationView and ToolBar
        BottomNavigationView mbtmView = (BottomNavigationView) findViewById(R.id.service_detail_bottom);
        bottomTitle = mbtmView.findViewById(R.id.service_detail_bottom_title);
        BottomNavigationViewHelper.disableShiftMode(mbtmView);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mbtmView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());
        mbtmView.setOnClickListener(this);

        ServiceDetailFragment frag = new ServiceDetailFragment();
        frag.setFlagForServiceStatus(isService);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.app_original_container, frag, frag.getTag());
        ft.commit();

        actionbarView = new JGGActionbarView(this);
        mToolbar = (Toolbar) findViewById(R.id.app_original_actionbar);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        initViewColor();
    }

    private void initViewColor() {
        if (appType.equals(SERVICES)) {
            actionbarView.setStatus(JGGActionbarView.EditStatus.DETAILS, JGGAppBaseModel.AppointmentType.SERVICES);
            mColor = getResources().getColor(R.color.JGGGreen);
            mColorPercent = getResources().getColor(R.color.JGGGreen10Percent);
            bottomTitle.setText("Buy Service");
            if (!isService) bottomTitle.setText(R.string.request_quotation);
            lblViewedCountDesc.setText("people have booked this service!");
            bottomTitle.setBackgroundColor(mColor);
            viewedLayout.setBackgroundColor(mColorPercent);
        } else if (appType.equals(JOBS)) {
            actionbarView.setStatus(JGGActionbarView.EditStatus.DETAILS, JGGAppBaseModel.AppointmentType.JOBS);
            mColor = getResources().getColor(R.color.JGGCyan);
            mColorPercent = getResources().getColor(R.color.JGGCyan10Percent);
            bottomTitle.setText("Make A Proposal");
            lblViewedCountDesc.setText("people have viewed this job recently!");
            bottomTitle.setBackgroundColor(mColor);
            viewedLayout.setBackgroundColor(mColorPercent);
        } else if (appType.equals(GOCLUB)) {
            actionbarView.setStatus(JGGActionbarView.EditStatus.DETAILS, JGGAppBaseModel.AppointmentType.GOCLUB);
            mColor = getResources().getColor(R.color.JGGPurple);
            mColorPercent = getResources().getColor(R.color.JGGPurple10Percent);
            bottomTitle.setText("Make A Event");
            bottomTitle.setBackgroundColor(mColor);
            viewedLayout.setBackgroundColor(mColorPercent);
        }
    }

    private void actionbarViewItemClick(View view) {
        if (view.getId() == R.id.btn_like_original) {
            actionbarView.setLikeButtonClicked(actionbarView.mLikeButtonSelected);
        } else if (view.getId() == R.id.btn_more) {
            actionbarView.setMoreButtonClicked(true);
            // Show Edit PopUp Menu
            showEditPopUpMenu(view);
        } else if (view.getId() == R.id.btn_back) {
            ServiceDetailActivity.this.finish();
        }
    }

    private void showEditPopUpMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        if (appType.equals(SERVICES)) {
            popupMenu.inflate(R.menu.share_menu_green);
        } else if (appType.equals(JOBS)) {
            popupMenu.inflate(R.menu.share_menu_cyan);
        } else if (appType.equals(GOCLUB)) {
            popupMenu.inflate(R.menu.share_menu_purple);
        }

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
        } else if (view.getId() == R.id.service_detail_bottom) {
            if (isService) {
                startActivity(new Intent(this, BuyServiceActivity.class));
            } else {
                startActivity(new Intent(this, RequestQuotationActivity.class));
            }
        }
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

            if (menuItem.getItemId() == R.id.menu_option_share) {  // Share the Service
                openShareDialog();
            } else if (menuItem.getItemId() == R.id.menu_option_report_service) {    // Report the Service
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

        title.setText(R.string.alert_report_service_title);
        description.setText(R.string.alert_report_service_desc);
        reportButton.setText(R.string.alert_report_service_ok);
        reportButton.setBackgroundColor(mColor);
        cancelButton.setBackgroundColor(mColorPercent);
        cancelButton.setTextColor(mColor);
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
}
