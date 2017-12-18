package com.kelvin.jacksgogo.Activities.Jobs;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.Fragments.Jobs.EditJobMainFragment;
import com.kelvin.jacksgogo.Fragments.Jobs.JobMainFragment;
import com.kelvin.jacksgogo.R;

import java.lang.reflect.Field;

public class JobDetailActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    JGGActionbarView actionbarView;
    JobMainFragment jobMainFragment;
    EditJobMainFragment editJobMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);

        actionbarView = new JGGActionbarView(this);
        /* ---------    Custom view add to TopToolbar     --------- */
        mToolbar = (Toolbar) findViewById(R.id.app_detail_actionbar);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        showJobMainFragment();

        actionbarView.setStatus(JGGActionbarView.EditStatus.APPOINTMENT);
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
                    onShowEditPopUpMenu(view);
                    break;
                case APPOINTMENT:
                    onShowEditPopUpMenu(view);
                    break;
                case EDIT_MAIN:
                    showJobMainFragment();
                    break;
                case EDIT_DETAIL:
                    backToEditJobMainFragment();
                    break;
                default:
                    break;
            }
        } else {
            /* ---------    Back button pressed     --------- */
            FragmentManager manager = getSupportFragmentManager();
            int backStackCount = manager.getBackStackEntryCount();
            switch (actionbarView.getEditStatus()) {
                case NONE:
                    if (backStackCount == 0) {
                        super.onBackPressed();
                    } else {
                        actionbarView.setStatus(JGGActionbarView.EditStatus.APPOINTMENT);
                        manager.popBackStack();
                    }
                    break;
                case JOB_REPORT:
                    actionbarView.setStatus(JGGActionbarView.EditStatus.APPOINTMENT);
                    manager.popBackStack();
                    break;
                case APPOINTMENT:
                    super.finish();
                    break;
                case EDIT_MAIN:
                    showJobMainFragment();
                    break;
                case EDIT_DETAIL:
                    backToEditJobMainFragment();
                    break;
                default:
                    break;
            }
        }
    }

    private void onShowEditPopUpMenu(View view) {
        actionbarView.setEditMoreButtonClicked(true);

        PopupMenu popupMenu = new PopupMenu(JobDetailActivity.this, view);
        popupMenu.inflate(R.menu.edit_menu);
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

    private void showJobMainFragment() {
        actionbarView.setStatus(JGGActionbarView.EditStatus.APPOINTMENT);

        jobMainFragment = new JobMainFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.app_detail_container, jobMainFragment, jobMainFragment.getTag());
        ft.commit();
    }

    private void backToEditJobMainFragment() {
        actionbarView.setStatus(JGGActionbarView.EditStatus.EDIT_MAIN);

        editJobMainFragment = EditJobMainFragment.newInstance(false);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.app_detail_container, editJobMainFragment, editJobMainFragment.getTag());
        ft.commit();
    }

    public void setStatus(JGGActionbarView.EditStatus status) {
        actionbarView.setStatus(status);
    }

    private void openEditJobMainFragment() {
        actionbarView.setStatus(JGGActionbarView.EditStatus.EDIT_MAIN);

        editJobMainFragment = EditJobMainFragment.newInstance(false);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.app_detail_container, editJobMainFragment, editJobMainFragment.getTag());
        ft.commit();
    }

    private void showDeleteJobDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(JobDetailActivity.this);
        LayoutInflater inflater = (JobDetailActivity.this).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.jgg_alert_view, null);
        builder.setView(dialogView);
        TextView cancelButton = (TextView) dialogView.findViewById(R.id.btn_alert_cancel);
        TextView deleteButton = (TextView) dialogView.findViewById(R.id.btn_alert_ok);
        final AlertDialog alertDialog = builder.create();
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionbarView.setStatus(JGGActionbarView.EditStatus.APPOINTMENT);
                alertDialog.dismiss();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        alertDialog.show();
    }

    private class OnDismissListener implements PopupMenu.OnDismissListener {
        @Override
        public void onDismiss(PopupMenu menu) {
            if (actionbarView.getEditStatus() == JGGActionbarView.EditStatus.APPOINTMENT
                    || actionbarView.getEditStatus() == JGGActionbarView.EditStatus.NONE)
                actionbarView.setEditMoreButtonClicked(false);
        }
    }

    private class OnMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            if (menuItem.getItemId() == R.id.menu_option_delete) {  // Delete Job
                showDeleteJobDialog();
            } else if (menuItem.getItemId() == R.id.menu_option_edit) {    // Edit Job
                openEditJobMainFragment();
            }
            return true;
        }
    }
}
