package com.kelvin.jacksgogo.Activities.Search;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewBehavior;
import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewHelper;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.Fragments.Search.ActiveServiceMainFragment;
import com.kelvin.jacksgogo.R;

public class ActiveServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private JGGActionbarView actionbarView;
    private ActiveServiceMainFragment activeServiceMainFragment;
    private BottomNavigationView mbtmView;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_active_service);

        initializeView();
    }

    private void initializeView() {

        // Hide Bottom NavigationView and ToolBar
        mbtmView = (BottomNavigationView) findViewById(R.id.active_service_navigation);
        BottomNavigationViewHelper.disableShiftMode(mbtmView);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mbtmView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());
        mbtmView.setOnClickListener(this);

        // Top Navigationbar View
        actionbarView = new JGGActionbarView(this);
        mToolbar = (Toolbar) findViewById(R.id.active_service_actionbar);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        actionbarView.setStatus(JGGActionbarView.EditStatus.ACTIVE_SERVICE);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        // Main Fragment
        activeServiceMainFragment = new ActiveServiceMainFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.active_service_container, activeServiceMainFragment, activeServiceMainFragment.getTag());
        ft.commit();
    }

    public void setBottomViewHidden(boolean isHidden) {
        if (isHidden) {
            this.mbtmView.setVisibility(View.GONE);
        } else {
            this.mbtmView.setVisibility(View.VISIBLE);
        }
    }

    private void actionbarViewItemClick(View view) {
        if (view.getId() == R.id.btn_back) {
            FragmentManager manager = getSupportFragmentManager();
            if (manager.getBackStackEntryCount() == 0) {
                super.onBackPressed();
            } else {
                manager.popBackStack();
                setBottomViewHidden(false);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.active_service_navigation) {
            startActivity(new Intent(this, PostServiceActivity.class));
        }
    }
}
