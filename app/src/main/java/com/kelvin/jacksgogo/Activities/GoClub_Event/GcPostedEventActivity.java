package com.kelvin.jacksgogo.Activities.GoClub_Event;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewBehavior;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.CustomView.Views.JGGAlertView;
import com.kelvin.jacksgogo.CustomView.Views.JGGShareIntentDialog;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.DUPLICATE;
import static com.kelvin.jacksgogo.Utils.Global.EDIT;
import static com.kelvin.jacksgogo.Utils.Global.EDIT_STATUS;
import static com.kelvin.jacksgogo.Utils.Global.GOCLUB;

public class GcPostedEventActivity extends AppCompatActivity {

    @BindView(R.id.posted_gc_actionbar)                         Toolbar mToolbar;
    @BindView(R.id.btn_view_attendees)                          Button btnViewAttendees;
    @BindView(R.id.txt_schedule)                            TextView txtSchedule;

    BottomNavigationView mBottomNavigationView;
    private JGGActionbarView actionbarView;
    private AlertDialog alertDialog;
    private boolean isPost = false;
    private boolean isVerified;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gc_posted_event);

        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        isPost = bundle.getBoolean("is_post");

        // Top Navigationbar View
        actionbarView = new JGGActionbarView(this);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        actionbarView.setStatus(JGGActionbarView.EditStatus.POSTED, Global.AppointmentType.GOCLUB);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        // Hide bottom navigation bar
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mBottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());
    }

    private void onBackClick() {
//        if (isPost) {
//            Intent intent = new Intent(this, ServiceListingDetailActivity.class);
//            intent.putExtra("is_post", true);
//            startActivity(intent);
//        } else {
            onBackPressed();
//        }
    }

    private void onWithdrawEvent() {

    }

    private void onShowWithdrawOneEventDialog() {
        final JGGAlertView builder = new JGGAlertView(this,
                "Withdraw From GoClub?"
                        + '\n'
                        + "30 Dec, 2017",
                "Let Clarence.Tan know why you are withdrawing from the event.",
                true,
                getResources().getString(R.string.alert_cancel),
                R.color.JGGPurple,
                R.color.JGGPurple10Percent,
                getResources().getString(R.string.alert_withdraw),
                R.color.JGGRed);
        alertDialog = builder.create();
        builder.setOnItemClickListener(new JGGAlertView.OnItemClickListener() {
            @Override
            public void onDoneButtonClick(View view) {
                if (view.getId() == R.id.btn_alert_cancel)
                    alertDialog.dismiss();
                else {
                    onWithdrawEvent();
                }
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    private void onMorePopUpMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.posted_goclubs_menu);
        popupMenu.setOnDismissListener(new GcPostedEventActivity.OnDismissListener());
        popupMenu.setOnMenuItemClickListener(new GcPostedEventActivity.OnMenuItemClickListener());
        if (!isVerified) {
            Menu m = popupMenu.getMenu();
            m.removeItem(R.id.posted_goclub_menu_share);
        }

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

    private void openShareDialog() {
        JGGShareIntentDialog dialogShare = new JGGShareIntentDialog.Builder(this)
                .setDialogTitle("Share with your friends")
                .setShareLink(null)
                .build();
        dialogShare.show();
    }

    private void onDeleteButtonClick() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (this).getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.jgg_alert_view, null);
        builder.setView(dialogView);
        TextView cancelButton = (TextView) dialogView.findViewById(R.id.btn_alert_cancel);
        TextView deleteButton = (TextView) dialogView.findViewById(R.id.btn_alert_ok);
        TextView title = (TextView) dialogView.findViewById(R.id.lbl_alert_titile);
        TextView desc = (TextView) dialogView.findViewById(R.id.lbl_alert_description);

        title.setText(R.string.alert_posted_service_delete_title);
        desc.setText(R.string.alert_posted_service_delete_desc);

        final AlertDialog alertDialog = builder.create();
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                onDeleteService();
            }
        });
        alertDialog.show();
    }

    private void onDeleteService() {}

    private class OnDismissListener implements PopupMenu.OnDismissListener {
        @Override
        public void onDismiss(PopupMenu menu) {
            actionbarView.setMoreButtonClicked(false);
        }
    }

    private class OnMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            if (menuItem.getItemId() == R.id.posted_goclub_menu_share) {  // Share the Goclub Event
                openShareDialog();
            } else if (menuItem.getItemId() == R.id.posted_goclub_menu_edit) {    // Edit Goclub Event
                Intent intent = new Intent(GcPostedEventActivity.this, CreateGoClubActivity.class);
                intent.putExtra(EDIT_STATUS, EDIT);
                intent.putExtra(APPOINTMENT_TYPE, GOCLUB);
                startActivity(intent);
            } else if (menuItem.getItemId() == R.id.posted_goclub_menu_withdraw) { // Withdraw the Goclub Event
                onShowWithdrawOneEventDialog();
            }
            else if (menuItem.getItemId() == R.id.posted_goclub_menu_duplicate) {    // Duplicate the Goclub Event
                Intent intent = new Intent(GcPostedEventActivity.this, CreateGoClubActivity.class);
                intent.putExtra(EDIT_STATUS, DUPLICATE);
                intent.putExtra(APPOINTMENT_TYPE, GOCLUB);
                startActivity(intent);
            } else if (menuItem.getItemId() == R.id.posted_goclub_menu_delete) {    // Delete the Goclub Event
                onDeleteButtonClick();
            }
            return true;
        }
    }

    private void actionbarViewItemClick(View view) {
        if (view.getId() == R.id.btn_back) {
            onBackClick();
        } else if (view.getId() == R.id.btn_more) {
            actionbarView.setMoreButtonClicked(true);
            onMorePopUpMenu(view);
        }
    }

    @OnClick(R.id.btn_view_attendees)
    public void onClickViewAttendees() {
        Intent intent = new Intent(GcPostedEventActivity.this, GcAttendeesActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.txt_schedule)
    public void onClickSchedule() {
        Intent intent = new Intent(GcPostedEventActivity.this, GcScheduleActivity.class);
        startActivity(intent);
    }
}
