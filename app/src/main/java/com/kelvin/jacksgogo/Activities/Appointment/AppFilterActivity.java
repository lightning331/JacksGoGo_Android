package com.kelvin.jacksgogo.Activities.Appointment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.kelvin.jacksgogo.Adapter.Appointment.AppointmentFilterAdapter;
import com.kelvin.jacksgogo.R;

import java.util.ArrayList;

public class AppFilterActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageButton closeButton;
    ArrayList arrayFilterTypes;

    private static AppointmentFilterAdapter filterRecyclerViewAdapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_filter);

        recyclerView = (RecyclerView) findViewById(R.id.appointment_filter_recycler_view);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        }
        arrayFilterTypes = new ArrayList<>();

        arrayFilterTypes.add("Jobs posted.");
        arrayFilterTypes.add("Jobs accepted.");
        arrayFilterTypes.add("Invited jobs.");
        arrayFilterTypes.add("Quick jobs.");
        arrayFilterTypes.add("Events joined.");
        arrayFilterTypes.add("Events posted.");

        // create our list and custom adapter
        filterRecyclerViewAdapter = new AppointmentFilterAdapter(this);
        filterRecyclerViewAdapter.addSection("Show Only", arrayFilterTypes);

        recyclerView.setAdapter(filterRecyclerViewAdapter);
        // RecyclerView Item select
        filterRecyclerViewAdapter.setOnItemClickListener(new AppointmentFilterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("Item Selected", "==========" + position + "============");
            }
        });

        // Close button clicked
        closeButton = (ImageButton) findViewById(R.id.btn_close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                onBackPressed();
            }
        });
    }

    public Context getContext() {
        return context;
    }
}
