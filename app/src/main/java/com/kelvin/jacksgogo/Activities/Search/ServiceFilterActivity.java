package com.kelvin.jacksgogo.Activities.Search;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kelvin.jacksgogo.Adapter.Services.CategoryGridAdapter;
import com.kelvin.jacksgogo.Models.Jobs_Services_Events.JGGAppBaseModel;
import com.kelvin.jacksgogo.R;

public class ServiceFilterActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton closeButton;
    private EditText txtKeyword;
    private EditText txtLocation;
    private TextView btnCurrentLocation;
    private GridView gridView;

    private CategoryGridAdapter adapter;
    private String appType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_filter);

        appType = getIntent().getStringExtra("APPOINTMENT_TYPE"); // Dennis

        initView();
    }

    private void initView() {

        closeButton = (ImageButton) findViewById(R.id.btn_filter_close);
        txtKeyword = (EditText) findViewById(R.id.txt_filter_keyword);
        txtLocation = (EditText) findViewById(R.id.txt_filter_location);
        btnCurrentLocation = (TextView) findViewById(R.id.btn_current_location);
        gridView = (GridView) findViewById(R.id.category_grid_view);

        closeButton.setOnClickListener(this);

        if (appType.equals("SERVICES")) {
            closeButton.setImageResource(R.mipmap.button_tick_area_round_green);
            btnCurrentLocation.setBackgroundResource(R.drawable.green_background);

            adapter = new CategoryGridAdapter(this, JGGAppBaseModel.AppointmentType.SERVICES);
        } else if (appType.equals("JOBS")) {
            closeButton.setImageResource(R.mipmap.button_tick_area_round_cyan);
            btnCurrentLocation.setBackgroundResource(R.drawable.cyan_background);

            adapter = new CategoryGridAdapter(this, JGGAppBaseModel.AppointmentType.JOBS);
        } else if (appType.equals("GOCLUB")) {
            closeButton.setImageResource(R.mipmap.button_tick_area_round_purple);
            btnCurrentLocation.setBackgroundResource(R.drawable.purple_background);

            adapter = new CategoryGridAdapter(this, JGGAppBaseModel.AppointmentType.GOCLUB);
        }

        gridView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_filter_close) {
            onBackPressed();
        }
    }
}
