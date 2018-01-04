package com.kelvin.jacksgogo.Activities.Search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.Adapter.Services.CategoryGridAdapter;
import com.kelvin.jacksgogo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServiceFilterActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton closeButton;
    private EditText txtKeyword;
    private EditText txtLocation;
    private TextView btnCurrentLocation;
    private CategoryGridAdapter adapter;
    private String appType;
    private GridView gridView;
    private ArrayList<Map<String, Object>> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_filter);

        appType = getIntent().getStringExtra("APPOINTMENT_TYPE");

        addCategoryData();
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

        } else if (appType.equals("JOBS")) {
            closeButton.setImageResource(R.mipmap.button_tick_area_round_cyan);
            btnCurrentLocation.setBackgroundResource(R.drawable.cyan_background);

        } else if (appType.equals("GOCLUB")) {
            closeButton.setImageResource(R.mipmap.button_tick_area_round_purple);
            btnCurrentLocation.setBackgroundResource(R.drawable.purple_background);

        }

        adapter = new CategoryGridAdapter(this, datas, appType);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the GridView selected/clicked item text
                String name = datas.get(position).get("name").toString();
                Toast.makeText(ServiceFilterActivity.this, name,
                        Toast.LENGTH_LONG).show();
            }
        });
        gridView.setAdapter(adapter);
    }

    private void addCategoryData() {
        datas.add(createMap("Cooking & Baking", R.mipmap.icon_cat_cooking_baking));
        datas.add(createMap("Education", R.mipmap.icon_cat_education));
        datas.add(createMap("Handyman", R.mipmap.icon_cat_handyman));
        datas.add(createMap("Household Chores", R.mipmap.icon_cat_householdchores));
        datas.add(createMap("Messenger", R.mipmap.icon_cat_messenger));
        datas.add(createMap("Running Man", R.mipmap.icon_cat_runningman));
        datas.add(createMap("Sports", R.mipmap.icon_cat_sports));
        datas.add(createMap("Other Professions", R.mipmap.icon_cat_other));
    }

    private Map<String, Object> createMap(String name, int iconId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("icon", iconId);
        return map;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_filter_close) {
            onBackPressed();
        }
    }
}
