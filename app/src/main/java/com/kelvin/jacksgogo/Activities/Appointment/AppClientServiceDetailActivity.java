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

import com.kelvin.jacksgogo.CustomView.AppDetailActionbarView;
import com.kelvin.jacksgogo.Fragments.Appointments.AppClientServiceDetailFragment;
import com.kelvin.jacksgogo.R;

import java.lang.reflect.Field;

public class AppClientServiceDetailActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    AppDetailActionbarView appDetailActionbarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_info_activity);

        appDetailActionbarView = new AppDetailActionbarView(this);
        mToolbar = (Toolbar) findViewById(R.id.app_detail_actionbar);
        mToolbar.addView(appDetailActionbarView);
        setSupportActionBar(mToolbar);

        appDetailActionbarView.setTabbarItemClickListener(new AppDetailActionbarView.OnTabbarItemClickListener() {
            @Override
            public void onTabbarItemClick(View item) {

                if (item.getId() == R.id.btn_more) {

                    PopupWindow mPopupWindow = new PopupWindow();
                    PopupMenu popupMenu = new PopupMenu(AppClientServiceDetailActivity.this, item);
                    popupMenu.inflate(R.menu.menu_option);

                    appDetailActionbarView.setSelected(!appDetailActionbarView.isMenuOpenStatus());
                    optionMenuIconChange();

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {

                            appDetailActionbarView.setSelected(!appDetailActionbarView.isMenuOpenStatus());
                            optionMenuIconChange();

                            if (menuItem.getItemId() == R.id.menu_option_delete) {

                                final AlertDialog.Builder builder = new AlertDialog.Builder(AppClientServiceDetailActivity.this);
                                LayoutInflater inflater = (AppClientServiceDetailActivity.this).getLayoutInflater();
                                // Inflate and set the layout for the dialog
                                // Pass null as the parent view because its going in the
                                // dialog layout
                                View dialogView = inflater.inflate(R.layout.jgg_custom_alert_view, null);
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
                                        AppClientServiceDetailActivity.this.finish();
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
                    AppClientServiceDetailActivity.this.finish();
                }
            }
        });

        AppClientServiceDetailFragment frag = new AppClientServiceDetailFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.app_detail_container, frag, frag.getTag());
        ft.commit();
    }

    public void optionMenuIconChange() {
        if (!appDetailActionbarView.isMenuOpenStatus()) appDetailActionbarView.moreMenuImage.setImageResource(R.mipmap.button_more_orange);
        else appDetailActionbarView.moreMenuImage.setImageResource(R.mipmap.button_more_orange_active);

    }
}
