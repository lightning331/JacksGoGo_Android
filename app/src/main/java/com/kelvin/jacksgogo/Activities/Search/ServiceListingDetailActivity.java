package com.kelvin.jacksgogo.Activities.Search;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewBehavior;
import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewHelper;
import com.kelvin.jacksgogo.Adapter.Services.ActiveServiceAdapter;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.R;

public class ServiceListingDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private JGGActionbarView actionbarView;
    private RecyclerView recyclerView;

    private boolean isPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_listing_detail);

        Bundle bundle = getIntent().getExtras();
        isPost = bundle.getBoolean("is_post");

        // Hide Bottom NavigationView and ToolBar
        BottomNavigationView mbtmView = (BottomNavigationView) findViewById(R.id.service_listing_detail_navigation);
        BottomNavigationViewHelper.disableShiftMode(mbtmView);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mbtmView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());
        mbtmView.setOnClickListener(this);

        actionbarView = new JGGActionbarView(this);
        mToolbar = (Toolbar) findViewById(R.id.service_listing_detail_actionbar);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        actionbarView.setStatus(JGGActionbarView.EditStatus.SERVICE_LISTING_DETAIL);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.service_listing_detail_recycler_view);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        }
        ActiveServiceAdapter adapter = new ActiveServiceAdapter();
        adapter.setOnItemClickListener(new ActiveServiceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick() {
                onPostedServiceClick();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void actionbarViewItemClick(View view) {
        if (view.getId() == R.id.btn_back) {
            if (isPost) {
                Intent intent = new Intent(this, ServiceListingActivity.class);
                startActivity(intent);
            } else {
                onBackPressed();
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.service_listing_detail_navigation) {
            startActivity(new Intent(this, PostServiceActivity.class));
        }
    }

    private void onPostedServiceClick() {
        Intent intent = new Intent(this, PostedServiceActivity.class);
        intent.putExtra("is_post", false);
        startActivity(intent);
    }
}
