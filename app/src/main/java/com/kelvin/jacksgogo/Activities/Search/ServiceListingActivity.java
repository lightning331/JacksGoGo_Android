package com.kelvin.jacksgogo.Activities.Search;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewBehavior;
import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewHelper;
import com.kelvin.jacksgogo.Adapter.Service.ServiceListingAdapter;
import com.kelvin.jacksgogo.CustomView.JGGActionbarView;
import com.kelvin.jacksgogo.R;

public class ServiceListingActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    JGGActionbarView actionbarView;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_service_listing_activity);

        // Hide Bottom NavigationView and ToolBar
        BottomNavigationView mbtmView = (BottomNavigationView) findViewById(R.id.service_listing_navigation);
        BottomNavigationViewHelper.disableShiftMode(mbtmView);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mbtmView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());
        mbtmView.setOnClickListener(this);

        actionbarView = new JGGActionbarView(this);
        mToolbar = (Toolbar) findViewById(R.id.service_listing_actionbar);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        actionbarView.setStatus(JGGActionbarView.EditStatus.SERVICE_LISTING);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.service_listing_recycler_view);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        }

        ServiceListingAdapter adapter = new ServiceListingAdapter(this);
        adapter.setOnItemClickListener(new ServiceListingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick() {
                onCellClick();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void onCellClick() {
        Intent intent = new Intent(this, PostedServiceActivity.class);
        startActivity(intent);
    }

    private void actionbarViewItemClick(View view) {
        if (view.getId() == R.id.btn_back) {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.service_listing_navigation) {
            startActivity(new Intent(this, PostServiceActivity.class));
        }
    }
}
