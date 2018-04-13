package com.kelvin.jacksgogo.Activities.GoClub_Event;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewBehavior;
import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewHelper;
import com.kelvin.jacksgogo.Adapter.GoClub_Event.EventDetailAdapter;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.CustomView.Views.JGGAlertView;
import com.kelvin.jacksgogo.CustomView.Views.JGGShareIntentDialog;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.kelvin.jacksgogo.Utils.Global.JoinGoClubStatus;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventDetailActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    @BindView(R.id.event_detail_actionbar) Toolbar mToolbar;
    @BindView(R.id.event_detail_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.btn_join_event) TextView btnJoinGoClub;
    @BindView(R.id.lbl_joined_count) TextView lblJoinedCount;
    @BindView(R.id.lbl_viewing_count) TextView lblViewingCount;
    @BindView(R.id.owner_layout) LinearLayout ownerLayout;

    private JGGActionbarView actionbarView;
    private BottomNavigationView mbtmView;
    private AlertDialog alertDialog;
    private ProgressDialog progressDialog;

    private JoinGoClubStatus joinGoClubStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        ButterKnife.bind(this);

        // Todo - Dummy Data
        setJoinToGoClubStatus(JoinGoClubStatus.none);

        // Hide Bottom NavigationView and ToolBar
        mbtmView = findViewById(R.id.event_detail_bottom);
        BottomNavigationViewHelper.disableShiftMode(mbtmView);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mbtmView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());
        btnJoinGoClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onShowJoinDialog();
            }
        });

        // Top ActionbarView
        actionbarView = new JGGActionbarView(this);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);
        actionbarView.setStatus(JGGActionbarView.EditStatus.DETAILS, AppointmentType.EVENTS);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarItemClick(view);
            }
        });

        if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        }
        EventDetailAdapter adapter = new EventDetailAdapter(this);
        mRecyclerView.setAdapter(adapter);
    }

    public void setJoinToGoClubStatus(JoinGoClubStatus status) {
        joinGoClubStatus = status;
        btnJoinGoClub.setVisibility(View.GONE);
        ownerLayout.setVisibility(View.GONE);

        if (status == JoinGoClubStatus.none || status == JoinGoClubStatus.rejected) {
            btnJoinGoClub.setVisibility(View.VISIBLE);
        } else if (status == JoinGoClubStatus.approved) {
            ownerLayout.setVisibility(View.VISIBLE);
        }
    }

    // Todo - Send Report request
    private void onSendReport() {
        alertDialog.dismiss();
        onShowReportDialog(true);
    }

    // Todo - Send join request to the Event
    private void onSendJoinRequest() {
        alertDialog.dismiss();
        setJoinToGoClubStatus(JoinGoClubStatus.approved);
    }

    // Todo - Withdraw from Event
    private void onWithdrawFromEvent() {
        alertDialog.dismiss();
        setJoinToGoClubStatus(JoinGoClubStatus.none);
    }

    private void actionbarItemClick(View view) {
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
        if (joinGoClubStatus == JoinGoClubStatus.none)
            popupMenu.inflate(R.menu.event_share_menu);
        else if (joinGoClubStatus == JoinGoClubStatus.approved)
            popupMenu.inflate(R.menu.event_joined_menu);

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

            if (menuItem.getItemId() == R.id.menu_option_share) {  // Share Event
                openShareDialog();
            } else if (menuItem.getItemId() == R.id.menu_option_leave_event) {    // Withdraw Event
                onShowWithdrawEventDialog();
            } else if (menuItem.getItemId() == R.id.menu_option_report_event) {    // Report Event
                onShowReportDialog(false);
            }
            return true;
        }
    }

    private void onShowReportDialog(final boolean reportFlag) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (this).getLayoutInflater();

        final View alertView = inflater.inflate(R.layout.jgg_alert_view, null);
        builder.setView(alertView);
        alertDialog = builder.create();
        TextView cancelButton = alertView.findViewById(R.id.btn_alert_cancel);
        TextView reportButton = alertView.findViewById(R.id.btn_alert_ok);
        TextView title = alertView.findViewById(R.id.lbl_alert_titile);
        TextView description = alertView.findViewById(R.id.lbl_alert_description);

        title.setText(R.string.alert_report_event_title);
        description.setText(R.string.alert_report_event_desc);
        reportButton.setText(R.string.alert_report_service_ok);
        reportButton.setBackgroundColor(getResources().getColor(R.color.JGGPurple));
        cancelButton.setBackgroundColor(getResources().getColor(R.color.JGGPurple10Percent));
        cancelButton.setTextColor(getResources().getColor(R.color.JGGPurple));
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reportFlag)
                    alertDialog.dismiss();
                else
                    onSendReport();
            }
        });
        if (reportFlag) {
            alertDialog.setCanceledOnTouchOutside(false);
            cancelButton.setVisibility(View.GONE);
            title.setText(R.string.alert_report_service_thanks_title);
            description.setText(R.string.alert_report_service_thanks_desc);
            reportButton.setText(R.string.alert_done);
        }
        cancelButton.setOnClickListener(this);
        alertDialog.show();
    }

    private void onShowJoinDialog() {
        final JGGAlertView builder = new JGGAlertView(this,
                "Join This Events?",
                "",
                false,
                getResources().getString(R.string.alert_cancel),
                R.color.JGGPurple,
                R.color.JGGPurple10Percent,
                getResources().getString(R.string.alert_join),
                R.color.JGGPurple);
        alertDialog = builder.create();
        builder.setOnItemClickListener(new JGGAlertView.OnItemClickListener() {
            @Override
            public void onDoneButtonClick(View view) {
                if (view.getId() == R.id.btn_alert_cancel)
                    alertDialog.dismiss();
                else {
                    onSendJoinRequest();
                }
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    // Todo - Leave GoClub
    private void onShowWithdrawEventDialog() {
        final JGGAlertView builder = new JGGAlertView(this,
                "Withdraw From Event?",
                "Let Clarence.Tan know why you are withdrawing from the event.",
                true,
                getResources().getString(R.string.alert_cancel),
                R.color.JGGPurple,
                R.color.JGGPurple10Percent,
                getResources().getString(R.string.alert_withdraw),
                R.color.JGGRed);
        alertDialog = builder.create();
        builder.txtReason.addTextChangedListener(this);
        builder.setOnItemClickListener(new JGGAlertView.OnItemClickListener() {
            @Override
            public void onDoneButtonClick(View view) {
                if (view.getId() == R.id.btn_alert_cancel)
                    alertDialog.dismiss();
                else
                    onWithdrawFromEvent();
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
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
