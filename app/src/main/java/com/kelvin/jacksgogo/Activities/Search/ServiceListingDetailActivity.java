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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewBehavior;
import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewHelper;
import com.kelvin.jacksgogo.Adapter.Services.ActiveServiceAdapter;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGGetAppsResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.JGGAppManager.selectedAppointment;
import static com.kelvin.jacksgogo.Utils.JGGAppManager.selectedCategory;
import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.EDIT_STATUS;
import static com.kelvin.jacksgogo.Utils.Global.POST;
import static com.kelvin.jacksgogo.Utils.Global.SERVICES;

public class ServiceListingDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private JGGActionbarView actionbarView;
    @BindView(R.id.service_listing_detail_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.img_category_icon) ImageView imgCategory;
    @BindView(R.id.lbl_category_name) TextView lblCategory;

    private ActiveServiceAdapter adapter;
    private ProgressDialog progressDialog;
    private ArrayList<JGGAppointmentModel> mServices = new ArrayList<>();
    private boolean isPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_listing_detail);
        ButterKnife.bind(this);

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

        actionbarView.setStatus(JGGActionbarView.EditStatus.SERVICE_LISTING_DETAIL, AppointmentType.UNKNOWN);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        }
        adapter = new ActiveServiceAdapter(this);
        adapter.setOnItemClickListener(new ActiveServiceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                selectedAppointment = mServices.get(position);
                onPostedServiceClick();
            }
        });
        recyclerView.setAdapter(adapter);

        setCategory();
        getServicesByCategory();
    }

    private void setCategory() {
        // Category
        Picasso.with(this)
                .load(selectedCategory.getImage())
                .placeholder(null)
                .into(imgCategory);
        lblCategory.setText(selectedCategory.getName());
    }

    private void getServicesByCategory() {
        progressDialog = Global.createProgressDialog(this);
        final JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGGetAppsResponse> call = apiManager.getServicesByCategory(selectedCategory.getID(), 0, 50);
        call.enqueue(new Callback<JGGGetAppsResponse>() {
            @Override
            public void onResponse(Call<JGGGetAppsResponse> call, Response<JGGGetAppsResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mServices = response.body().getValue();
                        adapter.notifyDataChanged(mServices);
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(ServiceListingDetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode  = response.code();
                    Toast.makeText(ServiceListingDetailActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGGetAppsResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ServiceListingDetailActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
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
            Intent intent = new Intent(this, PostServiceActivity.class);
            intent.putExtra(EDIT_STATUS, POST);
            intent.putExtra(APPOINTMENT_TYPE, SERVICES);
            startActivity(intent);
        }
    }

    private void onPostedServiceClick() {
        Intent intent = new Intent(this, PostedServiceActivity.class);
        intent.putExtra("is_post", false);
        startActivity(intent);
    }
}
