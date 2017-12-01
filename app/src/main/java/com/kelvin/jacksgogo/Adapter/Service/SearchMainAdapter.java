package com.kelvin.jacksgogo.Adapter.Service;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.jacksgogo.Activities.Search.ActiveServiceActivity;
import com.kelvin.jacksgogo.Activities.Search.PostServiceActivity;
import com.kelvin.jacksgogo.Activities.Search.ServiceDetailActivity;
import com.kelvin.jacksgogo.Activities.Search.ServiceListingActivity;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Search.SearchCategoryListView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Search.SearchHomeHeaderView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Search.ServiceDetailListCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.SectionTitleView;
import com.kelvin.jacksgogo.R;

import static android.content.ContentValues.TAG;

/**
 * Created by PUMA on 11/14/2017.
 */

public class SearchMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private int HEADER_TYPE = 0;
    private int CATEGORY_SECTION_TITLE_TYPE = 1;
    private int CATEGORY_SECTION_TYPE = 2;
    private int RECOMMEND_SECTION_TITLE_TYPE = 3;

    public SearchMainAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == HEADER_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_home_headerview, parent, false);
            SearchHomeHeaderView categoryListView = new SearchHomeHeaderView(view);
            categoryListView.setOnClickListener(this);
            return categoryListView;
        } else if (viewType == CATEGORY_SECTION_TITLE_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_title_view, parent, false);
            SectionTitleView sectionView = new SectionTitleView(view);
            sectionView.txtTitle.setText("All Categories");
            sectionView.txtTitle.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
            return sectionView;
        } else if (viewType == CATEGORY_SECTION_TYPE) {
            View listView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_category_list_view, parent, false);
            SearchCategoryListView categoryListView = new SearchCategoryListView(listView);
            categoryListView.setOnClickListener(this);
            return categoryListView;
        } else if (viewType == RECOMMEND_SECTION_TITLE_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_title_view, parent, false);
            SectionTitleView sectionView = new SectionTitleView(view);
            sectionView.txtTitle.setText("Recommended For You");
            sectionView.txtTitle.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
            return sectionView;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_service_detail_list_cell, parent, false);
            ServiceDetailListCell cell = new ServiceDetailListCell(view);
            cell.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ServiceDetailActivity.class);
                    intent.putExtra("is_service", true);
                    mContext.startActivity(intent);
                }
            });
            return cell;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 7;
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_view_my_service) {
            Log.d(TAG, "view my services ========= : ");
            Intent intent = new Intent(mContext.getApplicationContext(), ServiceListingActivity.class);
            view.getContext().startActivity(intent);
        } else if (view.getId() == R.id.btn_view_all || view.getId() == R.id.btn_other_professions) {
            Log.d(TAG, "view all services ========= : ");
            Intent intent = new Intent(mContext.getApplicationContext(), ActiveServiceActivity.class);
            view.getContext().startActivity(intent);
        } else if (view.getId() == R.id.btn_post_new) {
            Log.d(TAG, "post new ========= : ");
            Intent intent = new Intent(mContext.getApplicationContext(), PostServiceActivity.class);
            view.getContext().startActivity(intent);
        }
    }
}
