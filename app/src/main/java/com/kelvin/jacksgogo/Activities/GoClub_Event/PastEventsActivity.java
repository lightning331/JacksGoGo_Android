package com.kelvin.jacksgogo.Activities.GoClub_Event;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kelvin.jacksgogo.Adapter.GoClub_Event.EventsListingAdapter;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.GoClub_Event.JGGEventModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGGetEventsResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PastEventsActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.past_event_actionbar)        Toolbar mToolbar;
    @BindView(R.id.swipeSearchContainer)        SwipeRefreshLayout swipeContainer;
    @BindView(R.id.past_event_recycler_view)    RecyclerView recyclerView;
    @BindView(R.id.btn_by_latest)               TextView btnLatest;
    @BindView(R.id.btn_by_earliest)             TextView btnEarliest;

    private JGGActionbarView actionbarView;
    private String clubID;
    private ArrayList<JGGEventModel> clubEvents = new ArrayList<>();
    private EventsListingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_events);
        ButterKnife.bind(this);

        clubID = getIntent().getStringExtra("clubID");

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

        // Setup refresh listener which triggers new data loading
        swipeContainer.setColorSchemeResources(R.color.JGGPurple,
                R.color.JGGPurple,
                R.color.JGGPurple,
                R.color.JGGPurple);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            getEventsByClub();
                                        }
                                    }
                );
            }
        });

        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        }
        adapter = new EventsListingAdapter(this, clubEvents);
        adapter.setOnItemClickListener(new EventsListingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Gson gson = new Gson();
                JGGEventModel eventModel = clubEvents.get(position);
                String jsonEvent = gson.toJson(eventModel);

                Intent intent = new Intent(PastEventsActivity.this, EventDetailActivity.class);
                intent.putExtra("clubEventModel", jsonEvent);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        getEventsByClub();
    }

    /**
     * Get GoClub events by ClubID
     */
    private void getEventsByClub() {
        JGGAPIManager manager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGGetEventsResponse> call = manager.getEventsByClub(clubID, 0, 30);
        call.enqueue(new Callback<JGGGetEventsResponse>() {
            @Override
            public void onResponse(Call<JGGGetEventsResponse> call, Response<JGGGetEventsResponse> response) {
                swipeContainer.setRefreshing(false);
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        // Todo - Set Events Data
                        clubEvents = response.body().getValue();

                        // Todo - Update Detail RecyclerView
                        adapter.refresh(clubEvents);

                    } else {
                        Toast.makeText(PastEventsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PastEventsActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGGetEventsResponse> call, Throwable t) {
                swipeContainer.setRefreshing(false);
                Toast.makeText(PastEventsActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
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
