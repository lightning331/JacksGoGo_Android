package com.kelvin.jacksgogo.Activities.Search;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.Fragments.Search.ActiveServiceMainFragment;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppBaseModel;
import com.kelvin.jacksgogo.R;

public class SearchResultActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private JGGActionbarView actionbarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        String appType = getIntent().getStringExtra("APPOINTMENT_TYPE");

        // Top Navigationbar View
        actionbarView = new JGGActionbarView(this);
        mToolbar = (Toolbar) findViewById(R.id.search_result_actionbar);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        if (appType.equals("SERVICES")) {
            actionbarView.setStatus(JGGActionbarView.EditStatus.SEARCH_RESULT, JGGAppBaseModel.AppointmentType.SERVICES);
        } else if (appType.equals("JOBS")) {
            actionbarView.setStatus(JGGActionbarView.EditStatus.SEARCH_RESULT, JGGAppBaseModel.AppointmentType.JOBS);
        } else if (appType.equals("GOCLUB")) {
            actionbarView.setStatus(JGGActionbarView.EditStatus.SEARCH_RESULT, JGGAppBaseModel.AppointmentType.GOCLUB);
        }
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                onBackPressed();
            }
        });

        // Main Fragment
        ActiveServiceMainFragment frag = ActiveServiceMainFragment.newInstance(appType);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.active_service_container, frag, frag.getTag());
        ft.commit();
    }
}
