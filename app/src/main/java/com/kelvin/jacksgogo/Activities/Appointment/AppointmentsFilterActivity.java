package com.kelvin.jacksgogo.Activities.Appointment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.Adapters.AppointmentsFilterRecyclerViewAdapter;
import com.kelvin.jacksgogo.R;
import com.makeramen.roundedimageview.RoundedImageView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AppointmentsFilterActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageButton closeButton;
    ArrayList arrayFilterTypes;

    private static AppointmentsFilterRecyclerViewAdapter filterRecyclerViewAdapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointments_filter_fragment);

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
        filterRecyclerViewAdapter = new AppointmentsFilterRecyclerViewAdapter(this);
        filterRecyclerViewAdapter.addSection("Show Only", arrayFilterTypes);

        recyclerView.setAdapter(filterRecyclerViewAdapter);
        // RecyclerView Item select
        filterRecyclerViewAdapter.setOnItemClickListener(new AppointmentsFilterRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("Item Selected", "==========" + position + "============");
            }
        });

        closeButton = (ImageButton) findViewById(R.id.btn_close);

        // Close button clicked
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
