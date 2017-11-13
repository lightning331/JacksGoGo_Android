package com.kelvin.jacksgogo.Activities.Appointment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.kelvin.jacksgogo.CustomView.JGGActionbarView;
import com.kelvin.jacksgogo.CustomView.JGGAlertView;
import com.kelvin.jacksgogo.Fragments.Appointments.EditJobMainFragment;
import com.kelvin.jacksgogo.Fragments.Appointments.JobDetailFragment;
import com.kelvin.jacksgogo.R;

import java.lang.reflect.Field;

public class JobDetailActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    JGGActionbarView actionbarView;
    JobDetailFragment jobDetailFragment;
    EditJobMainFragment editJobMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_detail_activity);

        actionbarView = new JGGActionbarView(this);
        /* ---------    Custom view add to TopToolbar     --------- */
        mToolbar = (Toolbar) findViewById(R.id.app_detail_actionbar);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        showJobDetailFragment();

        actionbarView.setStatus(JGGActionbarView.EditStatus.NONE);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });
    }

    private void actionbarViewItemClick(View view) {
        if (view.getId() == R.id.btn_more) {
            /* ---------    More button pressed     --------- */
            switch (actionbarView.getEditStatus()) {
                case NONE:
                    actionbarView.setDetailMoreButtonClicked(true);

                    PopupWindow mPopupWindow = new PopupWindow();
                    PopupMenu popupMenu = new PopupMenu(JobDetailActivity.this, view);
                    popupMenu.inflate(R.menu.menu_option);
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
                    break;
                case EDIT:
                    showJobDetailFragment();
                    break;
                default:
                    break;
            }
        } else {
            /* ---------    Back button pressed     --------- */
            switch (actionbarView.getEditStatus()) {
                case NONE:
                    JobDetailActivity.this.finish();
                    break;
                case EDIT:
                    showJobDetailFragment();
                    break;
                default:
                    break;
            }
        }
    }

    private void showJobDetailFragment() {
        actionbarView.setStatus(JGGActionbarView.EditStatus.NONE);

        jobDetailFragment = new JobDetailFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.app_detail_container, jobDetailFragment, jobDetailFragment.getTag());
        ft.commit();
    }

    private void openEditJobFragment() {
        actionbarView.setStatus(JGGActionbarView.EditStatus.EDIT);

        editJobMainFragment = new EditJobMainFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.app_detail_container, editJobMainFragment, editJobMainFragment.getTag());
        ft.commit();
    }

    private void showDeleteJobDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(JobDetailActivity.this);
        LayoutInflater inflater = (JobDetailActivity.this).getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the
        // dialog layout
        View dialogView = inflater.inflate(R.layout.jgg_alert_view, null);
        builder.setView(dialogView);
        TextView cancelButton = (TextView) dialogView.findViewById(R.id.btn_alert_cancel);
        TextView deleteButton = (TextView) dialogView.findViewById(R.id.btn_alert_ok);
        final AlertDialog alertDialog = builder.create();
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionbarView.setStatus(JGGActionbarView.EditStatus.NONE);
                alertDialog.dismiss();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JobDetailActivity.this.finish();
            }
        });
        alertDialog.show();
    }

    private class OnDismissListener implements PopupMenu.OnDismissListener {
        @Override
        public void onDismiss(PopupMenu menu) {
            if (actionbarView.getEditStatus() == JGGActionbarView.EditStatus.NONE)
                actionbarView.setDetailMoreButtonClicked(false);
        }
    }

    private class OnMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            if (menuItem.getItemId() == R.id.menu_option_delete) {  // Delete Job
                showDeleteJobDialog();
            } else if (menuItem.getItemId() == R.id.menu_option_edit) {    // Edit Job
                openEditJobFragment();
            }
            return true;
        }
    }
}
