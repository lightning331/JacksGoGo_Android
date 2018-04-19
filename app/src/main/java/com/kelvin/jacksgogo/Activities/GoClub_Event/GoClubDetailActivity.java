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
import com.kelvin.jacksgogo.Adapter.GoClub_Event.GoClubDetailAdapter;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.CustomView.Views.JGGAlertView;
import com.kelvin.jacksgogo.CustomView.Views.JGGShareIntentDialog;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.kelvin.jacksgogo.Utils.Global.JoinGoClubStatus;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoClubDetailActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    @BindView(R.id.go_club_detail_actionbar) Toolbar mToolbar;
    @BindView(R.id.go_club_detail_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.btn_join_go_club) TextView btnJoinGoClub;
    @BindView(R.id.lbl_joined_count) TextView lblJoinedCount;
    @BindView(R.id.lbl_viewing_count) TextView lblViewingCount;
    @BindView(R.id.pending_layout) LinearLayout pendingLayout;
    @BindView(R.id.owner_layout) LinearLayout ownerLayout;

    private JGGActionbarView actionbarView;
    private BottomNavigationView mbtmView;
    private GoClubDetailAdapter adapter;
    private AlertDialog alertDialog;
    private ProgressDialog progressDialog;

    private JoinGoClubStatus joinGoClubStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_club_detail);
        ButterKnife.bind(this);

        // Todo - Dummy Data
        setJoinToGoClubStatus(JoinGoClubStatus.none);

        // Todo - Hide Bottom NavigationView and ToolBar
        mbtmView = findViewById(R.id.go_club_detail_bottom);
        BottomNavigationViewHelper.disableShiftMode(mbtmView);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mbtmView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());
        btnJoinGoClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onJoinToGoClubDialog(false);
            }
        });

        // Todo - Top ActionbarView
        actionbarView = new JGGActionbarView(this);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);
        actionbarView.setStatus(JGGActionbarView.EditStatus.DETAILS, AppointmentType.GOCLUB);
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

    public void setJoinToGoClubStatus(JoinGoClubStatus status) {
        joinGoClubStatus = status;
        btnJoinGoClub.setVisibility(View.GONE);
        pendingLayout.setVisibility(View.GONE);
        ownerLayout.setVisibility(View.GONE);

        if (status == JoinGoClubStatus.none || status == JoinGoClubStatus.rejected) {
            btnJoinGoClub.setVisibility(View.VISIBLE);
        } else if (status == JoinGoClubStatus.pending) {
            pendingLayout.setVisibility(View.VISIBLE);
        } else if (status == JoinGoClubStatus.approved) {
            ownerLayout.setVisibility(View.VISIBLE);
        }
    }

    // Todo - Send Report request
    private void onSendReport() {
        alertDialog.dismiss();
        onReportDialog(true);
    }

    // Todo - Send GoClub join request
    private void onSendJoinRequest() {
        alertDialog.dismiss();
        onJoinToGoClubDialog(true);
    }

    // Todo - Withdraw GoClub
    private void onWithdrawGoClub() {
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

    // Todo - Edit Popup Menu
    private void showEditPopUpMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        //if (selectedAppointment.getUserProfileID().equals(currentUser.getID()))     // Owner popup menu
        //    popupMenu.inflate(R.menu.go_club_owner_share_menu);
        //else{                                                                           // personal user popup menu
            if (joinGoClubStatus == JoinGoClubStatus.none)
                popupMenu.inflate(R.menu.go_club_share_menu);
            else if (joinGoClubStatus == JoinGoClubStatus.approved || joinGoClubStatus == JoinGoClubStatus.pending)
                popupMenu.inflate(R.menu.go_club_owner_share_menu);//go_club_joined_menu);
        //}

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

    // Todo - Menu item selection
    private class OnMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            if (menuItem.getItemId() == R.id.menu_option_share) {
                openShareDialog();
            } else if (menuItem.getItemId() == R.id.menu_option_report_go_club) {
                onReportDialog(false);
            } else if (menuItem.getItemId() == R.id.menu_option_leave_go_club) {
                onLeaveGoClubDialog();
            } else if (menuItem.getItemId() == R.id.menu_option_edit_go_club) {

            }
            return true;
        }
    }

    // Todo - Report GoClub
    private void onReportDialog(final boolean reportFlag) {
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

    // Todo - Request to join GoClub
    private void onJoinToGoClubDialog(final boolean reportFlag) {
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
                    setJoinToGoClubStatus(JoinGoClubStatus.approved);
                }
            });
        }
        alertDialog.show();
    }

    // Todo - Leave GoClub
    private void onLeaveGoClubDialog() {
        final JGGAlertView builder = new JGGAlertView(this,
                "Withdraw From GoClub?",
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
                    onWithdrawGoClub();
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    // Todo - Can not Leave GoClub
    private void onNnotLeaveGoClubDialog() {
        final JGGAlertView builder = new JGGAlertView(this,
                "Promote Another As Admin",
                "You can't leave this GoClub if you are the sole admin.",
                false,
                "",
                R.color.JGGPurple,
                R.color.JGGPurple10Percent,
                getResources().getString(R.string.alert_withdraw),
                R.color.JGGPurple);
        alertDialog = builder.create();
        builder.setOnItemClickListener(new JGGAlertView.OnItemClickListener() {
            @Override
            public void onDoneButtonClick(View view) {
                if (view.getId() == R.id.btn_alert_cancel)
                    alertDialog.dismiss();
                else
                    alertDialog.dismiss();
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    // Todo - Share GoClub
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
