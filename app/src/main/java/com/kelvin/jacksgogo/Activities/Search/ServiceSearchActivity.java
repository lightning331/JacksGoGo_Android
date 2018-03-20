package com.kelvin.jacksgogo.Activities.Search;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.Fragments.Search.ServiceSearchMainFragment;
import com.kelvin.jacksgogo.R;

import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.GOCLUB;
import static com.kelvin.jacksgogo.Utils.Global.JOBS;
import static com.kelvin.jacksgogo.Utils.Global.SERVICES;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;

public class ServiceSearchActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private JGGActionbarView actionbarView;

    private String appType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_search);

        appType = getIntent().getStringExtra(APPOINTMENT_TYPE);

        actionbarView = new JGGActionbarView(this);
        mToolbar = (Toolbar) findViewById(R.id.service_search_actionbar);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        if (appType.equals(SERVICES)) {
            actionbarView.setStatus(JGGActionbarView.EditStatus.SEARCH, AppointmentType.SERVICES);
        } else if (appType.equals(JOBS)) {
            actionbarView.setStatus(JGGActionbarView.EditStatus.SEARCH, AppointmentType.JOBS);
        } else if (appType.equals(GOCLUB)) {
            actionbarView.setStatus(JGGActionbarView.EditStatus.SEARCH, AppointmentType.GOCLUB);
        }
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        ServiceSearchMainFragment frag = ServiceSearchMainFragment.newInstance(appType);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.service_search_container, frag, frag.getTag());
        ft.commit();
    }

    private void actionbarViewItemClick(View view) {
        if (view.getId() == R.id.btn_back) {
            FragmentManager manager = getSupportFragmentManager();
            if (manager.getBackStackEntryCount() == 0) {
                super.onBackPressed();
            } else {
                manager.popBackStack();
            }
        }
    }
}
