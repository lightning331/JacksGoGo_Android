package com.kelvin.jacksgogo.Activities.Appointment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.kelvin.jacksgogo.CustomView.AppDetailActionbarView;
import com.kelvin.jacksgogo.Fragments.Appointments.AppDetailRecyclerViewFragment;
import com.kelvin.jacksgogo.R;

public class AppDetailActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_detail_activity);

        AppDetailActionbarView appDetailActionbarView = new AppDetailActionbarView(this);
        mToolbar = (Toolbar) findViewById(R.id.app_detail_actionbar);
        mToolbar.addView(appDetailActionbarView);
        setSupportActionBar(mToolbar);

        AppDetailRecyclerViewFragment frag = new AppDetailRecyclerViewFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.app_detail_container, frag, frag.getTag());
        ft.commit();
    }
}
