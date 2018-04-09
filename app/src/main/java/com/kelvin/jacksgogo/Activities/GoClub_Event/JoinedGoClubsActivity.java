package com.kelvin.jacksgogo.Activities.GoClub_Event;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.kelvin.jacksgogo.Adapter.GoClub_Event.JoinedGoClubAdapter;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JoinedGoClubsActivity extends AppCompatActivity {

    @BindView(R.id.joined_go_club_actionbar) Toolbar mToolbar;
    @BindView(R.id.joined_go_club_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.search_bar) SearchView searchView;

    private JGGActionbarView actionbarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joined_go_clubs);
        ButterKnife.bind(this);

        // Top Navigationbar View
        actionbarView = new JGGActionbarView(this);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        actionbarView.setPurpleBackButton(R.string.title_joined_go_club, R.string.title_empty);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                onBackPressed();
            }
        });

        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        }
        JoinedGoClubAdapter adapter = new JoinedGoClubAdapter(this);
        recyclerView.setAdapter(adapter);
    }
}
