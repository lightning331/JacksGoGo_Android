package com.kelvin.jacksgogo.Activities.Search;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.CustomView.JGGActionbarView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Search.ServiceReviewCell;
import com.kelvin.jacksgogo.R;

import java.util.ArrayList;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class ServiceReviewsActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    JGGActionbarView actionbarView;
    RecyclerView recyclerView;
    MaterialRatingBar totalRatingBar;
    TextView lblTotalRatingCount;

    ArrayList<Float> totalRatingCount = new ArrayList<>();
    ArrayList<String> desc = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_reviews_activity);

        actionbarView = new JGGActionbarView(this);
        mToolbar = (Toolbar) findViewById(R.id.service_reviews_actionbar);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        actionbarView.setStatus(JGGActionbarView.EditStatus.SERVICE_REVIEWS);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        initViews();

        recyclerView = (RecyclerView) findViewById(R.id.service_reviews_recycler_view);

        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        }

        ServiceTotalReviewsAdapter adapter = new ServiceTotalReviewsAdapter();
        adapter.setData(desc, totalRatingCount);
        recyclerView.setAdapter(adapter);
        recyclerView.refreshDrawableState();
    }

    private void initViews() {
        desc.add("Very patient instructor.");
        desc.add("10 people in a group, but Ester can handle all of us very well. Love her easy-to-learn tactics.");
        desc.add("I enjoy the classes.");
        desc.add("I like the swim club she teaches in. Her classes are easy to follow.");
        desc.add("I enjoy the classes.");
        desc.add("10 people in a group, but Ester can handle all of us very well. Love her easy-to-learn tactics.");
        desc.add("Very patient instructor.");
        totalRatingCount.add(4.7f);
        totalRatingCount.add(5.0f);
        totalRatingCount.add(4.2f);
        totalRatingCount.add(4.0f);
        totalRatingCount.add(5.0f);
        totalRatingCount.add(3.7f);
        totalRatingCount.add(4.5f);

        totalRatingBar = findViewById(R.id.service_total_reviews_ratingbar);
        lblTotalRatingCount = findViewById(R.id.lbl_service_total_reviews_count);
        totalRatingBar.setRating(4.6f);
        lblTotalRatingCount.setText("(" + totalRatingCount.size() + ")");
    }

    private void actionbarViewItemClick(View view) {
        if (view.getId() == R.id.btn_back) {
            onBackPressed();
        }
    }
}

class ServiceTotalReviewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Float> totalRatingCount = new ArrayList<>();
    ArrayList<String> desc = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_total_review_cell, parent, false);

        return new ServiceReviewCell(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ServiceReviewCell cell = (ServiceReviewCell)holder;
        cell.lblReviewDesc.setText(desc.get(position));
        cell.ratingBar.setRating(totalRatingCount.get(position));
    }

    @Override
    public int getItemCount() {
        return totalRatingCount.size();
    }

    public void setData(ArrayList<String> desc, ArrayList<Float> rating) {
        this.totalRatingCount = rating;
        this.desc = desc;
    }
}
