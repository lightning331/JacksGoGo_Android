package com.kelvin.jacksgogo.Activities.Search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.kelvin.jacksgogo.Activities.Profile.VerifyNewSkillsActivity;
import com.kelvin.jacksgogo.CustomView.JGGActionbarView;
import com.kelvin.jacksgogo.R;

public class PostServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    JGGActionbarView actionbarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_post_service_activity);

        actionbarView = new JGGActionbarView(this);
        mToolbar = (Toolbar) findViewById(R.id.post_service_actionbar);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        actionbarView.setStatus(JGGActionbarView.EditStatus.POST_SERVICE);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        initView();
    }

    private void initView() {
        LinearLayout btnOther = findViewById(R.id.btn_post_other);
        btnOther.setOnClickListener(this);
        LinearLayout btnCooking = findViewById(R.id.btn_post_cooking);
        btnCooking.setOnClickListener(this);
        LinearLayout btnEducation = findViewById(R.id.btn_post_education);
        btnEducation.setOnClickListener(this);
        LinearLayout btnHand = findViewById(R.id.btn_post_handyman);
        btnHand.setOnClickListener(this);
        LinearLayout btnHouse = findViewById(R.id.btn_post_household);
        btnHouse.setOnClickListener(this);
        LinearLayout btnMessenger = findViewById(R.id.btn_post_messenger);
        btnMessenger.setOnClickListener(this);
        LinearLayout btnRun = findViewById(R.id.btn_post_run);
        btnRun.setOnClickListener(this);
        LinearLayout btnSport = findViewById(R.id.btn_post_sports);
        btnSport.setOnClickListener(this);
    }

    private void actionbarViewItemClick(View view) {
        if (view.getId() == R.id.btn_back) {
            onBackPressed();
        }
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, VerifyNewSkillsActivity.class));
    }
}
