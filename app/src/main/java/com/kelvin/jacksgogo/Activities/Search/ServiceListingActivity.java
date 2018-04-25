package com.kelvin.jacksgogo.Activities.Search;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewBehavior;
import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewHelper;
import com.kelvin.jacksgogo.Adapter.Services.ServiceListingAdapter;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGCategoryResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.EDIT_STATUS;
import static com.kelvin.jacksgogo.Utils.Global.POST;
import static com.kelvin.jacksgogo.Utils.Global.SERVICES;

public class ServiceListingActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private JGGActionbarView actionbarView;
    private RecyclerView recyclerView;

    private ServiceListingAdapter adapter;
    private ProgressDialog progressDialog;

    private ArrayList<JGGCategoryModel> categories;
    private ArrayList<JGGCategoryModel> mCategories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_listing);

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

        actionbarView.setStatus(JGGActionbarView.EditStatus.SERVICE_LISTING, AppointmentType.UNKNOWN);
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

        adapter = new ServiceListingAdapter(this);

        categories = JGGAppManager.getInstance().getCategories();
        if (categories == null)
            loadCategories();
        else {
            if (categories.size() == 0)
                loadCategories();
            else
                mCategories = categories;
        }
        adapter.notifyDataChanged(mCategories);
        adapter.setOnItemClickListener(new ServiceListingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                JGGAppManager.getInstance().setSelectedCategory(mCategories.get(position));

                Intent intent = new Intent(ServiceListingActivity.this, ServiceListingDetailActivity.class);
                intent.putExtra("is_post", false);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void loadCategories() {
        progressDialog = Global.createProgressDialog(this);
        final JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGCategoryResponse> call = apiManager.getCategory();
        call.enqueue(new Callback<JGGCategoryResponse>() {
            @Override
            public void onResponse(Call<JGGCategoryResponse> call, Response<JGGCategoryResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mCategories = response.body().getValue();
                        categories = mCategories;
                        adapter.notifyDataChanged(mCategories);
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(ServiceListingActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode  = response.code();
                    Toast.makeText(ServiceListingActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGCategoryResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ServiceListingActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void actionbarViewItemClick(View view) {
        if (view.getId() == R.id.btn_back) {
            onBackPressed();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.service_listing_navigation) {
            Intent intent = new Intent(this, PostServiceActivity.class);
            intent.putExtra(EDIT_STATUS, POST);
            intent.putExtra(APPOINTMENT_TYPE, SERVICES);
            startActivity(intent);
        }
    }
}
