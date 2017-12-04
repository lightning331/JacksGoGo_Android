package com.kelvin.jacksgogo.Activities.Search;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewBehavior;
import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewHelper;
import com.kelvin.jacksgogo.CustomView.JGGActionbarView;
import com.kelvin.jacksgogo.CustomView.JGGShareIntentDialog;
import com.kelvin.jacksgogo.Fragments.Search.ServiceDetailFragment;
import com.kelvin.jacksgogo.R;

import java.lang.reflect.Field;

public class ServiceDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    JGGActionbarView actionbarView;
    AlertDialog alertDialog;

    boolean isService;
    boolean reportFlag = false;

    public void setReportFlag(boolean reportFlag) {
        this.reportFlag = reportFlag;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_detail_activity);

        Bundle bundle = getIntent().getExtras();
        isService = bundle.getBoolean("is_service");

        // Hide Bottom NavigationView and ToolBar
        BottomNavigationView mbtmView = (BottomNavigationView) findViewById(R.id.service_detail_bottom);
        TextView title = mbtmView.findViewById(R.id.service_detail_bottom_title);
        if (!isService) title.setText(R.string.request_quotation);
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

        actionbarView.setStatus(JGGActionbarView.EditStatus.SERVICE);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });
    }

    private void actionbarViewItemClick(View view) {
        if (view.getId() == R.id.btn_like_original) {
            actionbarView.setLikeButtonClicked(actionbarView.mLikeButtonSelected);
        } else if (view.getId() == R.id.btn_more) {
            actionbarView.setShareMoreButtonClicked(true);
            // Show Edit PopUp Menu
            showEditPopUpMenu(view);
        } else if (view.getId() == R.id.btn_back) {
            ServiceDetailActivity.this.finish();
        }
    }

    private void showEditPopUpMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.share_menu);
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
                startActivity(new Intent(this, ServiceBuyActivity.class));
            } else {
                startActivity(new Intent(this, RequestQuotationActivity.class));
            }
        }
    }

    private class OnDismissListener implements PopupMenu.OnDismissListener {
        @Override
        public void onDismiss(PopupMenu menu) {
            actionbarView.setShareMoreButtonClicked(false);
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
        reportButton.setBackgroundColor(ContextCompat.getColor(this, R.color.JGGGreen));
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
        JGGShareIntentDialog dialogShare = new JGGShareIntentDialog.Builder(this)
                .setDialogTitle("Share with your friends")
                .setShareLink(null)
                .build();
        dialogShare.show();
    }
}
