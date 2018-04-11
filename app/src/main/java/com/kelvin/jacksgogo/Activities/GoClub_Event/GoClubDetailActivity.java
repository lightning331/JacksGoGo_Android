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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewBehavior;
import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewHelper;
import com.kelvin.jacksgogo.Adapter.GoClub_Event.GoClubDetailAdapter;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.CustomView.Views.JGGShareIntentDialog;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoClubDetailActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.go_club_detail_actionbar) Toolbar mToolbar;
    @BindView(R.id.go_club_detail_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.btn_join_go_club) TextView btnJoinGoClub;
    @BindView(R.id.lbl_joined_count) TextView lblJoinedCount;
    @BindView(R.id.lbl_viewing_count) TextView lblViewingCount;

    private JGGActionbarView actionbarView;
    private BottomNavigationView mbtmView;
    private GoClubDetailAdapter adapter;
    private AlertDialog alertDialog;
    private ProgressDialog progressDialog;

    private boolean reportFlag = false;
    private boolean joinFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_club_detail);
        ButterKnife.bind(this);

        // Hide Bottom NavigationView and ToolBar
        mbtmView = findViewById(R.id.go_club_detail_bottom);
        BottomNavigationViewHelper.disableShiftMode(mbtmView);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mbtmView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());
        btnJoinGoClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRequestJoinDialog(false);
            }
        });

        // Top ActionbarView
        actionbarView = new JGGActionbarView(this);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);
        actionbarView.setStatus(JGGActionbarView.EditStatus.DETAILS, Global.AppointmentType.GOCLUB);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarItemClick(view);
            }
        });

        if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        }
        adapter = new GoClubDetailAdapter(this);
        mRecyclerView.setAdapter(adapter);
    }

    private void onSendReport() {
        alertDialog.dismiss();
        showReportDialog(true);
    }

    private void onSendJoinRequest() {
        alertDialog.dismiss();
        onRequestJoinDialog(true);
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
        popupMenu.inflate(R.menu.go_club_share_menu);

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
        TextView cancelButton = alertView.findViewById(R.id.btn_alert_cancel);
        TextView reportButton = alertView.findViewById(R.id.btn_alert_ok);
        TextView title = alertView.findViewById(R.id.lbl_alert_titile);
        TextView description = alertView.findViewById(R.id.lbl_alert_description);

        title.setText(R.string.alert_report_go_club_title);
        description.setText(R.string.alert_report_go_club_desc);
        reportButton.setText(R.string.alert_report_service_ok);
        reportButton.setBackgroundColor(getResources().getColor(R.color.JGGPurple));
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSendReport();
            }
        });
        cancelButton.setBackgroundColor(getResources().getColor(R.color.JGGPurple10Percent));
        cancelButton.setTextColor(getResources().getColor(R.color.JGGPurple));
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        if (reportFlag) {
            alertDialog.setCanceledOnTouchOutside(false);
            cancelButton.setVisibility(View.GONE);
            title.setText(R.string.alert_report_service_thanks_title);
            description.setText(R.string.alert_report_service_thanks_desc);
            reportButton.setText(R.string.alert_done);
            reportButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
        }
        alertDialog.show();
    }

    private void onRequestJoinDialog(final boolean reportFlag) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (this).getLayoutInflater();

        View alertView = inflater.inflate(R.layout.jgg_alert_view, null);
        builder.setView(alertView);
        alertDialog = builder.create();
        TextView cancelButton = alertView.findViewById(R.id.btn_alert_cancel);
        TextView joinButton = alertView.findViewById(R.id.btn_alert_ok);
        TextView title = alertView.findViewById(R.id.lbl_alert_titile);
        TextView description = alertView.findViewById(R.id.lbl_alert_description);

        title.setText(R.string.alert_join_go_club_title);
        description.setVisibility(View.GONE);
        joinButton.setText(R.string.alert_join);
        joinButton.setBackgroundColor(getResources().getColor(R.color.JGGPurple));
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSendJoinRequest();
            }
        });
        cancelButton.setBackgroundColor(getResources().getColor(R.color.JGGPurple10Percent));
        cancelButton.setTextColor(getResources().getColor(R.color.JGGPurple));
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        if (reportFlag) {
            alertDialog.setCanceledOnTouchOutside(false);
            description.setVisibility(View.VISIBLE);
            title.setText(R.string.alert_join_request_title);
            description.setText(R.string.alert_join_request_desc);
            cancelButton.setVisibility(View.GONE);
            joinButton.setText(R.string.alert_done);
            joinButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
        }
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
    public void onClick(View v) {

    }
}
