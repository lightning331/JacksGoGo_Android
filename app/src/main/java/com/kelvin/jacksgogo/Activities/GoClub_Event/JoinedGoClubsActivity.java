package com.kelvin.jacksgogo.Activities.GoClub_Event;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kelvin.jacksgogo.Adapter.GoClub_Event.JoinedGoClubAdapter;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.GoClub_Event.JGGGoClubModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGGetGoClubsResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinedGoClubsActivity extends AppCompatActivity {

    @BindView(R.id.joined_go_club_actionbar) Toolbar mToolbar;
    @BindView(R.id.swipeSearchContainer) SwipeRefreshLayout swipeContainer;
    @BindView(R.id.joined_go_club_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.search_bar) SearchView searchView;

    private JGGActionbarView actionbarView;

    private ArrayList<JGGGoClubModel> mGoClubs = new ArrayList<>();

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
                                            getJoinedGoClub();
                                        }
                                    }
                );
            }
        });

        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        }
        getJoinedGoClub();
    }

    private void getJoinedGoClub() {
        swipeContainer.setRefreshing(true);
        String userID = JGGAppManager.getInstance().getCurrentUser().getID();
        JGGAPIManager manager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGGetGoClubsResponse> call = manager.getClubsByUser(userID, 0, 20);
        call.enqueue(new Callback<JGGGetGoClubsResponse>() {
            @Override
            public void onResponse(Call<JGGGetGoClubsResponse> call, Response<JGGGetGoClubsResponse> response) {
                swipeContainer.setRefreshing(false);
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {

                        mGoClubs = response.body().getValue();

                        updateClubRecyclerView();

                    } else {
                        Toast.makeText(JoinedGoClubsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(JoinedGoClubsActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGGetGoClubsResponse> call, Throwable t) {
                swipeContainer.setRefreshing(false);
                Toast.makeText(JoinedGoClubsActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateClubRecyclerView() {
        JoinedGoClubAdapter adapter = new JoinedGoClubAdapter(this, mGoClubs);
        recyclerView.setAdapter(adapter);
    }
}
