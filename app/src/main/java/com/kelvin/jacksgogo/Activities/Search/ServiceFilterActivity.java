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

import com.kelvin.jacksgogo.Adapter.CategoryCellAdapter;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;

import java.util.ArrayList;

public class ServiceFilterActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton closeButton;
    private EditText txtKeyword;
    private EditText txtLocation;
    private TextView btnCurrentLocation;
    private RecyclerView recyclerView;

    private CategoryCellAdapter adapter;
    private ArrayList<JGGCategoryModel> mCategories;
    private String appType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_filter);

        appType = getIntent().getStringExtra("APPOINTMENT_TYPE");

        initView();
    }

    private void initView() {

        closeButton = (ImageButton) findViewById(R.id.btn_filter_close);
        txtKeyword = (EditText) findViewById(R.id.txt_filter_keyword);
        txtLocation = (EditText) findViewById(R.id.txt_filter_location);
        btnCurrentLocation = (TextView) findViewById(R.id.btn_current_location);

        closeButton.setOnClickListener(this);

        if (appType.equals("SERVICES")) {
            closeButton.setImageResource(R.mipmap.button_tick_area_round_green);
            btnCurrentLocation.setBackgroundResource(R.drawable.green_background);

        } else if (appType.equals("JOBS")) {
            closeButton.setImageResource(R.mipmap.button_tick_area_round_cyan);
            btnCurrentLocation.setBackgroundResource(R.drawable.cyan_background);

        } else if (appType.equals("GOCLUB")) {
            closeButton.setImageResource(R.mipmap.button_tick_area_round_purple);
            btnCurrentLocation.setBackgroundResource(R.drawable.purple_background);

        }

        mCategories = JGGAppManager.getInstance(this).categories;
        recyclerView = findViewById(R.id.category_recycler_view);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        }
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        adapter = new CategoryCellAdapter(this, mCategories);
        adapter.setOnItemClickListener(new CategoryCellAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String name = mCategories.get(position).getName();
                Toast.makeText(ServiceFilterActivity.this, name,
                        Toast.LENGTH_LONG).show();

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
