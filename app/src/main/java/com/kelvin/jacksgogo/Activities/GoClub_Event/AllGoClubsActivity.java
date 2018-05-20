package com.kelvin.jacksgogo.Activities.GoClub_Event;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewBehavior;
import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewHelper;
import com.kelvin.jacksgogo.Activities.Search.ServiceFilterActivity;
import com.kelvin.jacksgogo.Adapter.GoClub_Event.GoClubMainAdapter;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.MarginDecoration;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.FilterCategoryModel;
import com.kelvin.jacksgogo.Utils.Models.GoClub_Event.JGGGoClubModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGGetGoClubsResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.EDIT_STATUS;
import static com.kelvin.jacksgogo.Utils.Global.GOCLUB;
import static com.kelvin.jacksgogo.Utils.Global.POST;

public class AllGoClubsActivity extends AppCompatActivity {

    @BindView(R.id.all_go_club_actionbar) Toolbar mToolbar;
    @BindView(R.id.btn_go_club_filter) LinearLayout btnFilter;
    @BindView(R.id.swipeSearchContainer) SwipeRefreshLayout swipeContainer;
    @BindView(R.id.all_go_club_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.post_go_club_bottom) BottomNavigationView mbtmView;
    @BindView(R.id.btn_post) TextView btnPost;

    private JGGActionbarView actionbarView;
    private GoClubMainAdapter adapter;

    private JGGCategoryModel selectedCategory;
    private ArrayList<JGGGoClubModel> mClubs = new ArrayList<>();
    private ArrayList<JGGGoClubModel> mFiltedClubs = new ArrayList<>();
    private String categoryID;
    private boolean isCategory;

    private static final int REQUEST_CODE = 10051;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_go_clubs);
        ButterKnife.bind(this);

        selectedCategory = JGGAppManager.getInstance().getSelectedCategory();
        isCategory = getIntent().getBooleanExtra("is_category", false);

        // Hide Bottom NavigationView and ToolBar
        BottomNavigationViewHelper.disableShiftMode(mbtmView);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mbtmView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());

        // Top NavigationBar View
        actionbarView = new JGGActionbarView(this);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);
        if (isCategory) {
            actionbarView.setCategoryNameToActionBar(selectedCategory.getName(), Global.AppointmentType.GOCLUB);
            categoryID = selectedCategory.getID();
        } else {
            actionbarView.setPurpleBackButton(R.string.title_all_go_club, R.string.title_empty);
            categoryID = null;
        }
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
                                            onLoadGoClubs();
                                        }
                                    }
                );
            }
        });

        if (recyclerView != null) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
        recyclerView.addItemDecoration(new MarginDecoration(this));

        adapter = new GoClubMainAdapter(this, mClubs);
        adapter.setOnItemClickListener(new GoClubMainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                JGGGoClubModel club = mFiltedClubs.get(position);
                JGGAppManager.getInstance().setSelectedClub(club);
                startActivity(new Intent(AllGoClubsActivity.this, GoClubDetailActivity.class));
            }
        });
        recyclerView.setAdapter(adapter);

        onLoadGoClubs();
    }

    // TODO : Get Recommended GoClubs
    private void onLoadGoClubs() {
        swipeContainer.setRefreshing(true);
        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGGetGoClubsResponse> call = apiManager.searchGoClub(null, categoryID,
                null,  0, 10);
        call.enqueue(new Callback<JGGGetGoClubsResponse>() {
            @Override
            public void onResponse(Call<JGGGetGoClubsResponse> call, Response<JGGGetGoClubsResponse> response) {
                swipeContainer.setRefreshing(false);
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mClubs = response.body().getValue();
                        mFiltedClubs = mClubs;
                        adapter.refresh(mFiltedClubs);
                    } else {
                        Toast.makeText(AllGoClubsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AllGoClubsActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGGetGoClubsResponse> call, Throwable t) {
                swipeContainer.setRefreshing(false);
                Toast.makeText(AllGoClubsActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.post_go_club_bottom)
    public void onCreateGoClubClick() {
        Intent mIntent = new Intent(this, CreateGoClubActivity.class);
        mIntent.putExtra(EDIT_STATUS, POST);
        mIntent.putExtra(APPOINTMENT_TYPE, GOCLUB);
        startActivity(mIntent);
    }

    @OnClick(R.id.btn_go_club_filter)
    public void onFilterClick() {
        Intent intent = new Intent(AllGoClubsActivity.this, ServiceFilterActivity.class);
        intent.putExtra(APPOINTMENT_TYPE, GOCLUB);
        startActivityForResult(intent, REQUEST_CODE);
    }

    private ArrayList<JGGGoClubModel> filter(ArrayList<JGGGoClubModel> models, String query) {
        final String lowerCaseQuery = query.toLowerCase();

        final ArrayList<JGGGoClubModel> filteredModelList = new ArrayList<>();
        for (JGGGoClubModel model : models) {
            String name = "";
            if (model.getName() != null)
                name = model.getName().toLowerCase();
            String description = "";
            if (model.getDescription() != null)
                description = model.getDescription().toLowerCase();
            if (name.contains(lowerCaseQuery) || description.contains(lowerCaseQuery)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK){
                String categoriesStr = data.getStringExtra("categories");
                String keyword = data.getStringExtra("keyword");

                Type listType = new TypeToken<ArrayList<FilterCategoryModel>>() {}.getType();
                Gson gson = new Gson();
                ArrayList<FilterCategoryModel> filterCategoryModels = gson.fromJson(categoriesStr, listType);

                // get only selected models
                ArrayList<String> filtered = new ArrayList<>();
                for (FilterCategoryModel categoryModel : filterCategoryModels) {
                    if (categoryModel.isSelected()) {
                        filtered.add(categoryModel.getCategoryName());
                    }
                }

                // filter by category names
                ArrayList<JGGGoClubModel> filteredClubs = new ArrayList<>();
                if (filtered.size() > 0) {
                    for (JGGGoClubModel clubModel : mClubs) {
                        if (filtered.contains(clubModel.getCategory().getName())) {
                            filteredClubs.add(clubModel);
                        }
                    }
                    if (keyword.equals("")) {
                        mFiltedClubs = filteredClubs;
                        adapter.refresh(mFiltedClubs);
                    } else {
                        mFiltedClubs = filter(filteredClubs, keyword);
                        adapter.refresh(mFiltedClubs);
                    }
                } else {
                    if (keyword.equals("")) {
                        mFiltedClubs = mClubs;
                        adapter.refresh(mFiltedClubs);
                    } else {
                        mFiltedClubs = filter(mClubs, keyword);
                        adapter.refresh(mFiltedClubs);
                    }
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
