package com.kelvin.jacksgogo.Activities.Service;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kelvin.jacksgogo.Adapter.Services.BookedServiceAdapter;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.MarginDecoration;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookedServiceActivity extends AppCompatActivity {

    @BindView(R.id.book_actionbar)              Toolbar mToolbar;
    @BindView(R.id.booking_recycler_view)       RecyclerView bookingRecyclerView;

    private BookedServiceAdapter adapter;
    public JGGActionbarView actionbarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_service);

        ButterKnife.bind(this);

        actionbarView = new JGGActionbarView(this);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        actionbarView.setDeleteJobStatus();
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        bookingRecyclerView.setLayoutManager(layoutManager);
        bookingRecyclerView.addItemDecoration(new MarginDecoration(this));

        adapter = new BookedServiceAdapter(this, new ArrayList<Date>());
        bookingRecyclerView.setAdapter(adapter);
    }

    private void actionbarViewItemClick(View view) {
        if (view.getId() == R.id.btn_back) {
            finish();
        }
    }

}
