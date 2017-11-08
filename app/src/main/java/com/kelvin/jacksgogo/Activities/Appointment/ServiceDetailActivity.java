package com.kelvin.jacksgogo.Activities.Appointment;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.kelvin.jacksgogo.CustomView.ServiceActionbarView;
import com.kelvin.jacksgogo.Fragments.Appointments.ServiceDetailFragment;
import com.kelvin.jacksgogo.R;

public class ServiceDetailActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_detail_activity);

        ServiceActionbarView actionbarView = new ServiceActionbarView(this);
        mToolbar = (Toolbar) findViewById(R.id.app_original_actionbar);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        ServiceDetailFragment frag = new ServiceDetailFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.app_original_container, frag, frag.getTag());
        ft.commit();
    }
}
