package com.kelvin.jacksgogo.Activities.GoClub_Event;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kelvin.jacksgogo.Adapter.GoClub_Event.GcAddedAdminAdapter;
import com.kelvin.jacksgogo.Adapter.GoClub_Event.GcAdminAdapter;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GcAddAdminActivity extends AppCompatActivity {

    @BindView(R.id.admin_actionbar)
    Toolbar mToolbar;
    @BindView(R.id.added_recycler_view)
    RecyclerView addedRecyclerView;
    @BindView(R.id.user_recycler_view)
    RecyclerView userRecyclerView;

    private JGGActionbarView actionbarView;

    private GcAddedAdminAdapter adapter;
    private GcAdminAdapter adminAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gc_add_admin);
        ButterKnife.bind(this);

        actionbarView = new JGGActionbarView(this);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        actionbarView.setPurpleBackButton(R.string.title_create_go_club, R.string.title_empty);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        if (addedRecyclerView != null) {
            addedRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
            userRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        }

        // Added Recycler View
        adapter = new GcAddedAdminAdapter(this);
        adapter.setOnItemClickListener(new GcAddedAdminAdapter.OnItemClickListener() {
            @Override
            public void onDeleteItemClick(int position) {
                Toast.makeText(GcAddAdminActivity.this, "Delete button clicked "+position, Toast.LENGTH_SHORT).show();
            }
        });
        addedRecyclerView.setAdapter(adapter);


        // User Recycler View
        adminAdapter = new GcAdminAdapter(this);
        adminAdapter.setOnItemClickListener(new GcAdminAdapter.OnItemClickListener() {
            @Override
            public void onPlusItemClick(int position) {
                Toast.makeText(GcAddAdminActivity.this, "Plus button clicked "+position, Toast.LENGTH_SHORT).show();
            }
        });
        userRecyclerView.setAdapter(adminAdapter);
    }

    @OnClick(R.id.btn_add_as_admin)
    public void onClickAddAsAdmin() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result","result");
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private void actionbarViewItemClick(View view) {
        if (view.getId() == R.id.btn_back) {
            finish();
        }
    }
}
