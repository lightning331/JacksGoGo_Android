package com.kelvin.jacksgogo.Activities.GoClub_Event;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.Adapter.GoClub_Event.EventsListingAdapter;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.GoClub_Event.JGGEventModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PastEventsActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.past_event_actionbar) Toolbar mToolbar;
    @BindView(R.id.past_event_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.btn_by_latest) TextView btnLatest;
    @BindView(R.id.btn_by_earliest) TextView btnEarliest;

    private JGGActionbarView actionbarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_events);
        ButterKnife.bind(this);
        btnLatest.setOnClickListener(this);
        btnEarliest.setOnClickListener(this);

        // Top NavigationBar View
        actionbarView = new JGGActionbarView(this);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);
        actionbarView.setPurpleBackButton(R.string.title_past_event, R.string.title_empty);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View item) {
                onBackPressed();
            }
        });

        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        }
        EventsListingAdapter adapter = new EventsListingAdapter(this, new ArrayList<JGGEventModel>());
        adapter.setOnItemClickListener(new EventsListingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick() {
                startActivity(new Intent(PastEventsActivity.this, EventDetailActivity.class));
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_by_latest) {
            btnLatest.setTextColor(ContextCompat.getColor(this, R.color.JGGPurple));
            btnEarliest.setTextColor(ContextCompat.getColor(this, R.color.JGGGrey2));
        } else if (view.getId() == R.id.btn_by_earliest) {
            btnEarliest.setTextColor(ContextCompat.getColor(this, R.color.JGGPurple));
            btnLatest.setTextColor(ContextCompat.getColor(this, R.color.JGGGrey2));
        }
    }
}
