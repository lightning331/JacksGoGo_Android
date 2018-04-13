package com.kelvin.jacksgogo.Adapter.GoClub_Event;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.jacksgogo.Activities.GoClub_Event.EventDetailActivity;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.GoClub_Events.EventListDetailCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.GoClub_Events.GoClubRecyclerView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services.CategoryRecyclerView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services.SearchHomeHeaderView;
import com.kelvin.jacksgogo.CustomView.Views.SectionTitleView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;

import java.util.ArrayList;

public class SearchGoClubAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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
            SearchHomeHeaderView headerView = new SearchHomeHeaderView(view, AppointmentType.GOCLUB, mContext, mCategories);
            headerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goClubListener.onItemClick(view);
                }
            });
            return headerView;
        } else if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_category_list, parent, false);
            CategoryRecyclerView categoryCell = new CategoryRecyclerView(view, mContext, AppointmentType.JOBS, mCategories);
            return categoryCell;
        } else if (viewType == 2) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_goclub_recyclerview, parent, false);
            GoClubRecyclerView goClubCell = new GoClubRecyclerView(view, mContext);
            return goClubCell;
        }

        // Event
        else if (viewType == 3) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_search_home_header, parent, false);
            SearchHomeHeaderView headerView = new SearchHomeHeaderView(view, AppointmentType.EVENT, mContext, mCategories);
            headerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    eventListener.onItemClick(view);
                }
            });
            return headerView;
        } else if (viewType == 4) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_category_list, parent, false);
            CategoryRecyclerView categoryCell = new CategoryRecyclerView(view, mContext, AppointmentType.JOBS, mCategories);
            return categoryCell;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_event_list_detail, parent, false);
            EventListDetailCell eventCell = new EventListDetailCell(view, mContext);
            eventCell.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContext.startActivity(new Intent(mContext, EventDetailActivity.class));
                }
            });
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
        return 9;
    }

    // GoClub Header View item click listener
    private OnGoClubHeaderViewClickListener goClubListener;

    public interface OnGoClubHeaderViewClickListener {
        void onItemClick(View view);
    }

    public void setOnGoClubClickListener(OnGoClubHeaderViewClickListener listener) {
        this.goClubListener = listener;
    }

    // Event Header View item click listener
    private OnEventHeaderViewClickListener eventListener;

    public interface OnEventHeaderViewClickListener {
        void onItemClick(View view);
    }

    public void setOnEventClickListener(OnEventHeaderViewClickListener listener) {
        this.eventListener = listener;
    }
}
