package com.kelvin.jacksgogo.Adapter.Events;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.GoClub_Events.EventListDetailCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.GoClub_Events.GoClubRecyclerView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services.CategoryRecyclerView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services.SearchHomeHeaderView;
import com.kelvin.jacksgogo.CustomView.Views.SectionTitleView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;

import java.util.ArrayList;

public class SearchGoClubAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private ArrayList<JGGCategoryModel> mCategories;

    public SearchGoClubAdapter(Context context, ArrayList<JGGCategoryModel> data) {
        this.mContext = context;
        mCategories = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // GoClub
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_search_home_header, parent, false);
            SearchHomeHeaderView headerView = new SearchHomeHeaderView(view, AppointmentType.GOCLUB, mContext);
            headerView.setOnClickListener(this);
            return headerView;
        } else if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_section_title, parent, false);
            SectionTitleView sectionView = new SectionTitleView(view);
            sectionView.txtTitle.setText("All Categories");
            sectionView.txtTitle.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
            return sectionView;
        } else if (viewType == 2) {
            View listView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_category_list, parent, false);
            CategoryRecyclerView categoryListView = new CategoryRecyclerView(listView, mContext, AppointmentType.GOCLUB, mCategories);
            return categoryListView;
        } else if (viewType == 3) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_section_title, parent, false);
            SectionTitleView sectionView = new SectionTitleView(view);
            sectionView.txtTitle.setText("Recommended For You");
            sectionView.txtTitle.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
            return sectionView;
        } else if (viewType == 4) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_goclub_recyclerview, parent, false);
            GoClubRecyclerView goClubCell = new GoClubRecyclerView(view, mContext);
            return goClubCell;
        }

        // Event
        else if (viewType == 5) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_search_home_header, parent, false);
            SearchHomeHeaderView headerView = new SearchHomeHeaderView(view, AppointmentType.EVENT, mContext);
            headerView.setOnClickListener(this);
            return headerView;
        } else if (viewType == 6) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_section_title, parent, false);
            SectionTitleView sectionView = new SectionTitleView(view);
            sectionView.txtTitle.setText("All Categories");
            sectionView.txtTitle.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
            return sectionView;
        } else if (viewType == 7) {
            View listView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_category_list, parent, false);
            CategoryRecyclerView categoryListView = new CategoryRecyclerView(listView, mContext, AppointmentType.EVENT, mCategories);
            return categoryListView;
        } else if (viewType == 8) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_section_title, parent, false);
            SectionTitleView sectionView = new SectionTitleView(view);
            sectionView.txtTitle.setText("Recommended For You");
            sectionView.txtTitle.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
            return sectionView;
        } else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_event_list_detail, parent, false);
            EventListDetailCell eventCell = new EventListDetailCell(view, mContext);
            return eventCell;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return 15;
    }

    @Override
    public void onClick(View view) {

    }
}
