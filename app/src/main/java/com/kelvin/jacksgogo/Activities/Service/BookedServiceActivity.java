package com.kelvin.jacksgogo.Activities.Service;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.Adapter.Services.BookedServiceAdapter;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.MarginDecoration;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookedServiceActivity extends AppCompatActivity {

    @BindView(R.id.book_actionbar)              Toolbar mToolbar;
    @BindView(R.id.booking_recycler_view)       RecyclerView bookingRecyclerView;
    @BindView(R.id.img_category)                ImageView img_category;
    @BindView(R.id.lbl_title)                   TextView lbl_title;
    @BindView(R.id.lbl_posted_time)             TextView lbl_posted_time;
    @BindView(R.id.btn_bought_service)          LinearLayout btn_bought_service;

    private BookedServiceAdapter adapter;
    public JGGActionbarView actionbarView;

    private JGGAppointmentModel mJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_service);

        ButterKnife.bind(this);

        mJob = JGGAppManager.getInstance().getSelectedAppointment();

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

        setData();
        initRecyclerView();
    }

    private void initRecyclerView() {
        if (bookingRecyclerView != null) {
            bookingRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        }
        bookingRecyclerView.addItemDecoration(new MarginDecoration(this));

        adapter = new BookedServiceAdapter(this, mJob.getSessions());
        bookingRecyclerView.setAdapter(adapter);
    }

    private void setData() {
        // Category
        Picasso.with(this)
                .load(mJob.getCategory().getImage())
                .placeholder(null)
                .into(img_category);
        lbl_title.setText(mJob.getTitle());
    }

    private void actionbarViewItemClick(View view) {
        if (view.getId() == R.id.btn_back) {
            finish();
        }
    }

}
