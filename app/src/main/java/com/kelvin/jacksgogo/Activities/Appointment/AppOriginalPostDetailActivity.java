package com.kelvin.jacksgogo.Activities.Appointment;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.kelvin.jacksgogo.CustomView.AppDetailActionbarView;
import com.kelvin.jacksgogo.CustomView.AppOriginalPostActionbarView;
import com.kelvin.jacksgogo.Fragments.Appointments.AppClientServiceDetailFragment;
import com.kelvin.jacksgogo.Fragments.Appointments.AppOriginalPostFragment;
import com.kelvin.jacksgogo.R;

public class AppOriginalPostDetailActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.original_post_detail_activity);

        AppOriginalPostActionbarView actionbarView = new AppOriginalPostActionbarView(this);
        mToolbar = (Toolbar) findViewById(R.id.app_original_actionbar);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        AppOriginalPostFragment frag = new AppOriginalPostFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.app_original_container, frag, frag.getTag());
        ft.commit();
    }
}
