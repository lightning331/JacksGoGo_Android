package com.kelvin.jacksgogo.Activities.Search;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.kelvin.jacksgogo.R;

public class ServiceFilterActivity extends AppCompatActivity {

    ImageButton closeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_filter_activity);

        // Close button clicked
        closeButton = (ImageButton) findViewById(R.id.btn_filter_close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
