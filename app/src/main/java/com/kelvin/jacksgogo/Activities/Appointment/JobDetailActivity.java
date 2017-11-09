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

import com.kelvin.jacksgogo.CustomView.JobDetailActionbarView;
import com.kelvin.jacksgogo.Fragments.Appointments.EditJobFragment;
import com.kelvin.jacksgogo.Fragments.Appointments.JobDetailFragment;
import com.kelvin.jacksgogo.R;

import java.lang.reflect.Field;

public class JobDetailActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    JobDetailActionbarView jobDetailActionbarView;
    JobDetailFragment jobDetailFragment;
    EditJobFragment editJobFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_detail_activity);

        jobDetailActionbarView = new JobDetailActionbarView(this);
        mToolbar = (Toolbar) findViewById(R.id.app_detail_actionbar);
        mToolbar.addView(jobDetailActionbarView);
        setSupportActionBar(mToolbar);

        jobDetailActionbarView.setStatus(JobDetailActionbarView.EditStatus.NONE);

        jobDetailFragment = new JobDetailFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.app_detail_container, jobDetailFragment, jobDetailFragment.getTag());
        ft.commit();

        jobDetailActionbarView.setJobDetailActionbarItemClickListener(new JobDetailActionbarView.OnJobDetailActionbarItemClickListener() {
            @Override
            public void onDetailActionbarItemClick(View item) {

                if (item.getId() == R.id.btn_more) {
                    switch (jobDetailActionbarView.getEditStatus()) {
                        case NONE:
                            setMoreButtonClicked(true);

                            PopupWindow mPopupWindow = new PopupWindow();
                            PopupMenu popupMenu = new PopupMenu(JobDetailActivity.this, item);
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
                        case EDIT: // when click check button
                            showJobDetailFragment();
                            break;
                        case DELETE:
                            break;
                        default:
                            break;
                    }
                } else {
                    // back to previous view
                    switch (jobDetailActionbarView.getEditStatus()) {
                        case NONE:
                            JobDetailActivity.this.finish();
                            break;
                        case EDIT:
                            showJobDetailFragment();
                            break;
                        case DELETE:
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }

    private void showJobDetailFragment() {
        jobDetailActionbarView.setStatus(JobDetailActionbarView.EditStatus.NONE);

        jobDetailFragment = new JobDetailFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.app_detail_container, jobDetailFragment, jobDetailFragment.getTag());
        ft.commit();
    }

    private void openEditJobFragment() {
        jobDetailActionbarView.setStatus(JobDetailActionbarView.EditStatus.EDIT);

        editJobFragment = new EditJobFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.app_detail_container, editJobFragment, editJobFragment.getTag());
        ft.commit();
    }

    private void showDeleteJobDialog() {
        jobDetailActionbarView.setStatus(JobDetailActionbarView.EditStatus.DELETE);

        final AlertDialog.Builder builder = new AlertDialog.Builder(JobDetailActivity.this);
        LayoutInflater inflater = (JobDetailActivity.this).getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the
        // dialog layout
        View dialogView = inflater.inflate(R.layout.custom_alert_view, null);
        builder.setView(dialogView);
        TextView cancelButton = (TextView) dialogView.findViewById(R.id.dialog_cancel);
        TextView deleteButton = (TextView) dialogView.findViewById(R.id.dialog_ok);
        final AlertDialog alertDialog = builder.create();
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jobDetailActionbarView.setStatus(JobDetailActionbarView.EditStatus.NONE);
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

    private void setMoreButtonClicked(boolean isSelected) {
        if (isSelected) {
            jobDetailActionbarView.moreMenuImage.setImageResource(R.mipmap.button_more_orange_active);
        } else {
            jobDetailActionbarView.moreMenuImage.setImageResource(R.mipmap.button_more_orange);
        }
    }

    private class OnDismissListener implements PopupMenu.OnDismissListener {
        @Override
        public void onDismiss(PopupMenu menu) {
            if (jobDetailActionbarView.getEditStatus() == JobDetailActionbarView.EditStatus.NONE)
                setMoreButtonClicked(false);
        }

    }

    private class OnMenuItemClickListener implements  PopupMenu.OnMenuItemClickListener {
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
