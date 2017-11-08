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
import com.kelvin.jacksgogo.Fragments.Appointments.JobDetailFragment;
import com.kelvin.jacksgogo.R;

import java.lang.reflect.Field;

public class JobDetailActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    JobDetailActionbarView jobDetailActionbarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_detail_activity);

        jobDetailActionbarView = new JobDetailActionbarView(this);
        mToolbar = (Toolbar) findViewById(R.id.app_detail_actionbar);
        mToolbar.addView(jobDetailActionbarView);
        setSupportActionBar(mToolbar);

        jobDetailActionbarView.setTabbarItemClickListener(new JobDetailActionbarView.OnTabbarItemClickListener() {
            @Override
            public void onTabbarItemClick(View item) {

                if (item.getId() == R.id.btn_more) {

                    PopupWindow mPopupWindow = new PopupWindow();
                    PopupMenu popupMenu = new PopupMenu(JobDetailActivity.this, item);
                    popupMenu.inflate(R.menu.menu_option);

                    jobDetailActionbarView.setSelected(!jobDetailActionbarView.isMenuOpenStatus());
                    optionMenuIconChange();

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {

                            jobDetailActionbarView.setSelected(!jobDetailActionbarView.isMenuOpenStatus());
                            optionMenuIconChange();

                            if (menuItem.getItemId() == R.id.menu_option_delete) {

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

                            } else {

                            }
                            return true;
                        }
                    });

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
                } else {
                    // back to previous view
                    JobDetailActivity.this.finish();
                }
            }
        });

        JobDetailFragment frag = new JobDetailFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.app_detail_container, frag, frag.getTag());
        ft.commit();
    }

    public void optionMenuIconChange() {
        if (!jobDetailActionbarView.isMenuOpenStatus()) jobDetailActionbarView.moreMenuImage.setImageResource(R.mipmap.button_more_orange);
        else jobDetailActionbarView.moreMenuImage.setImageResource(R.mipmap.button_more_orange_active);

    }
}
