package com.kelvin.jacksgogo.Activities.Search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.Adapter.CategoryAdapter;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.EVENTS;
import static com.kelvin.jacksgogo.Utils.Global.GOCLUB;
import static com.kelvin.jacksgogo.Utils.Global.JOBS;
import static com.kelvin.jacksgogo.Utils.Global.SERVICES;
import static com.kelvin.jacksgogo.Utils.JGGAppManager.categories;

public class ServiceFilterActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.btn_filter_close) ImageButton closeButton;
    @BindView(R.id.txt_filter_keyword) EditText txtKeyword;
    @BindView(R.id.txt_filter_location) EditText txtLocation;
    @BindView(R.id.btn_current_location) TextView btnCurrentLocation;
    @BindView(R.id.lbl_title) TextView lblTitle;
    @BindView(R.id.location_layout) LinearLayout locationLayout;
    @BindView(R.id.category_recycler_view) RecyclerView recyclerView;

    private CategoryAdapter adapter;
    private ArrayList<JGGCategoryModel> mCategories;
    private AppointmentType mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_filter);
        ButterKnife.bind(this);

        String appType = getIntent().getStringExtra(APPOINTMENT_TYPE);
        if (appType.equals(SERVICES)) {
            mType = AppointmentType.SERVICES;
        } else if (appType.equals(JOBS)) {
            mType = AppointmentType.JOBS;
        } else if (appType.equals(GOCLUB)) {
            mType = AppointmentType.GOCLUB;
        } else if (appType.equals(EVENTS)) {
            mType = AppointmentType.EVENT;
        }

        initView();
    }

    private void initView() {

        closeButton.setOnClickListener(this);

        if (mType == AppointmentType.SERVICES) {
            closeButton.setImageResource(R.mipmap.button_tick_area_round_green);
            btnCurrentLocation.setBackgroundResource(R.drawable.green_background);
            lblTitle.setText(R.string.title_show_services);

        } else if (mType == AppointmentType.JOBS) {
            closeButton.setImageResource(R.mipmap.button_tick_area_round_cyan);
            btnCurrentLocation.setBackgroundResource(R.drawable.cyan_background);
            lblTitle.setText(R.string.title_show_jobs);

        } else if (mType == AppointmentType.GOCLUB) {
            closeButton.setImageResource(R.mipmap.button_tick_area_round_purple);
            btnCurrentLocation.setBackgroundResource(R.drawable.purple_background);
            lblTitle.setText(R.string.title_show_go_club);
            locationLayout.setVisibility(View.GONE);

        } else if (mType == AppointmentType.EVENT) {
            closeButton.setImageResource(R.mipmap.button_tick_area_round_purple);
            btnCurrentLocation.setBackgroundResource(R.drawable.purple_background);
            lblTitle.setText(R.string.title_show_event);
        }

        mCategories = categories;
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        }
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        adapter = new CategoryAdapter(this, mCategories, mType);
        adapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (mType == AppointmentType.SERVICES) {
                    if (mCategories != null) {
                        String name = mCategories.get(position).getName();
                        Toast.makeText(ServiceFilterActivity.this, name,
                                Toast.LENGTH_LONG).show();
                    }
                } else if (mType == AppointmentType.JOBS) {
                    if (mCategories != null) {
                        String name = "";
                        if (position == 0)
                            name = "Quick Jobs";
                        else
                            name = mCategories.get(position - 1).getName();

                        Toast.makeText(ServiceFilterActivity.this, name,
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_filter_close) {
            onBackPressed();
        }
    }
}
