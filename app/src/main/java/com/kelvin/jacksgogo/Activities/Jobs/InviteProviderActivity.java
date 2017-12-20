package com.kelvin.jacksgogo.Activities.Jobs;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.kelvin.jacksgogo.Adapter.Jobs.InviteProviderAdapter;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.Models.Jobs_Services_Events.JGGAppBaseModel;
import com.kelvin.jacksgogo.R;

public class InviteProviderActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private JGGActionbarView actionbarView;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_provider);

        initView();
    }

    private void initView() {

        // Top Navigationbar View
        actionbarView = new JGGActionbarView(this);
        mToolbar = (Toolbar) findViewById(R.id.app_invite_actionbar);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        actionbarView.setStatus(JGGActionbarView.EditStatus.INVITE, JGGAppBaseModel.AppointmentType.UNKNOWN);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.invite_provider_recycler_view);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        }

        InviteProviderAdapter adapter = new InviteProviderAdapter(this);
        recyclerView.setAdapter(adapter);
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
