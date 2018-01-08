package com.kelvin.jacksgogo.Activities.Search;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewBehavior;
import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewHelper;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.Fragments.Search.ActiveServiceMainFragment;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppBaseModel;
import com.kelvin.jacksgogo.R;

public class ActiveServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private JGGActionbarView actionbarView;
    private ActiveServiceMainFragment activeFrag;
    private BottomNavigationView mbtmView;;
    private TextView btnPost;

    private String appType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_active_service);

        initializeView();
    }

    private void initializeView() {

        appType = getIntent().getStringExtra("APPOINTMENT_TYPE");

        // Hide Bottom NavigationView and ToolBar
        mbtmView = (BottomNavigationView) findViewById(R.id.active_service_navigation);
        btnPost = (TextView) findViewById(R.id.btn_post);
        BottomNavigationViewHelper.disableShiftMode(mbtmView);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mbtmView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());
        mbtmView.setOnClickListener(this);

        // Top Navigationbar View
        actionbarView = new JGGActionbarView(this);
        mToolbar = (Toolbar) findViewById(R.id.active_service_actionbar);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        if (appType.equals("SERVICES")) {
            actionbarView.setStatus(JGGActionbarView.EditStatus.ACTIVE_AROUND, JGGAppBaseModel.AppointmentType.SERVICES);
            btnPost.setText(R.string.title_post_service);
            btnPost.setBackgroundColor(ContextCompat.getColor(this, R.color.JGGGreen));
        } else if (appType.equals("JOBS")) {
            actionbarView.setStatus(JGGActionbarView.EditStatus.ACTIVE_AROUND, JGGAppBaseModel.AppointmentType.JOBS);
            btnPost.setText(R.string.title_post_job);
            btnPost.setBackgroundColor(ContextCompat.getColor(this, R.color.JGGCyan));
        } else if (appType.equals("GOCLUB")) {
            actionbarView.setStatus(JGGActionbarView.EditStatus.ACTIVE_AROUND, JGGAppBaseModel.AppointmentType.GOCLUB);
            btnPost.setText(R.string.title_post_goclub);
            btnPost.setBackgroundColor(ContextCompat.getColor(this, R.color.JGGPurple));
        }
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        // Main Fragment
        activeFrag = ActiveServiceMainFragment.newInstance(appType);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.active_service_container, activeFrag, activeFrag.getTag());
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
            Intent intent = new Intent(this, PostServiceActivity.class);
            intent.putExtra("EDIT_STATUS", "None");
            intent.putExtra("APPOINTMENT_TYPE", appType);
            startActivity(intent);
        }
    }
}
