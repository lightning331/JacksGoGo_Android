package com.kelvin.jacksgogo.Activities.GoClub_Event;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewBehavior;
import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewHelper;
import com.kelvin.jacksgogo.Activities.Search.ServiceFilterActivity;
import com.kelvin.jacksgogo.Adapter.GoClub_Event.GoClubMainAdapter;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.GOCLUB;
import static com.kelvin.jacksgogo.Utils.JGGAppManager.selectedCategory;

public class AllGoClubsActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.all_go_club_actionbar) Toolbar mToolbar;
    @BindView(R.id.btn_go_club_filter) LinearLayout btnFilter;
    @BindView(R.id.all_go_club_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.post_go_club_bottom) BottomNavigationView mbtmView;
    @BindView(R.id.btn_post) TextView btnPost;

    private JGGActionbarView actionbarView;

    private boolean isCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_go_clubs);
        ButterKnife.bind(this);

        isCategory = getIntent().getBooleanExtra("is_category", false);

        // Hide Bottom NavigationView and ToolBar
        BottomNavigationViewHelper.disableShiftMode(mbtmView);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mbtmView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());

        // Top NavigationBar View
        actionbarView = new JGGActionbarView(this);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);
        if (isCategory)
            actionbarView.setCategoryNameToActionBar(selectedCategory.getName(), Global.AppointmentType.GOCLUB);
        else
            actionbarView.setPurpleBackButton(R.string.title_all_go_club, R.string.title_empty);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View item) {
                onBackPressed();
            }
        });

        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        }
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        GoClubMainAdapter adapter = new GoClubMainAdapter(this);
        recyclerView.setAdapter(adapter);

        mbtmView.setOnClickListener(this);
        btnFilter.setOnClickListener(this);
        btnPost.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_go_club_filter) {
            Intent intent = new Intent(AllGoClubsActivity.this, ServiceFilterActivity.class);
            intent.putExtra(APPOINTMENT_TYPE, GOCLUB);
            startActivity(intent);
        }
    }
}
