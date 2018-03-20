package com.kelvin.jacksgogo.Activities.Search;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewBehavior;
import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewHelper;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.CustomView.Views.JGGShareIntentDialog;
import com.kelvin.jacksgogo.Fragments.Search.ServiceDetailFragment;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.selectedAppointment;

public class ServiceDetailActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.service_detail_actionbar) Toolbar mToolbar;
    @BindView(R.id.lbl_booked_count) TextView lblViewedCount;
    @BindView(R.id.lbl_viewing_count) TextView lblViewingCount;
    @BindView(R.id.service_detail_bottom_title) TextView bottomTitle;
    @BindView(R.id.lbl_booked_title) TextView lblViewedCountDesc;

    private JGGActionbarView actionbarView;
    private AlertDialog alertDialog;

    private boolean reportFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();

        // Hide Bottom NavigationView and ToolBar
        BottomNavigationView mbtmView = (BottomNavigationView) findViewById(R.id.service_detail_bottom);
        BottomNavigationViewHelper.disableShiftMode(mbtmView);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mbtmView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());

        /*
         *  Service BudgetType
         *  If Fixed Budget, Can buy Service
         *  If Package Budget, Can Request to Service
         */
        if (selectedAppointment.getBudgetFrom() == null)
            bottomTitle.setText("Buy Service");
        else
            bottomTitle.setText(R.string.title_request_quotation);
        bottomTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedAppointment.getBudget() == null) {
                    startActivity(new Intent(ServiceDetailActivity.this, PostQuotationActivity.class));
                } else {
                    startActivity(new Intent(ServiceDetailActivity.this, BuyServiceActivity.class));
                }
            }
        });

        // Top ActionbarView
        actionbarView = new JGGActionbarView(this);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);
        actionbarView.setStatus(JGGActionbarView.EditStatus.DETAILS, AppointmentType.SERVICES);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        // Main Fragment
        ServiceDetailFragment frag = new ServiceDetailFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.app_original_container, frag)
                .commit();
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
        popupMenu.inflate(R.menu.share_menu_green);

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
        reportButton.setBackgroundColor(getResources().getColor(R.color.JGGGreen));
        cancelButton.setBackgroundColor(getResources().getColor(R.color.JGGGreen10Percent));
        cancelButton.setTextColor(getResources().getColor(R.color.JGGGreen));
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
