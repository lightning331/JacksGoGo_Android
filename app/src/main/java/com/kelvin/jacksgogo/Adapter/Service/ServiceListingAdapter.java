package com.kelvin.jacksgogo.Adapter.Service;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Search.CategoryDetailHeaderView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Search.SearchHomeHeaderView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Search.ServiceListCell;
import com.kelvin.jacksgogo.R;

import java.util.List;

/**
 * Created by PUMA on 11/14/2017.
 */

public class ServiceListingAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private List itemModels;
    private Context mContext;

    public ServiceListingAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_category_detail_header_view, parent, false);
            CategoryDetailHeaderView categoryListView = new CategoryDetailHeaderView(view);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return categoryListView;
        } else if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_service_list_cell, parent, false);
            ServiceListCell cell = new ServiceListCell(view);
            return cell;
        } else if (viewType == 2) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_service_list_cell, parent, false);
            ServiceListCell cell = new ServiceListCell(view);
            return cell;
        } else if (viewType == 3) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_category_detail_header_view, parent, false);
            CategoryDetailHeaderView categoryListView = new CategoryDetailHeaderView(view);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return categoryListView;
        } else if (viewType == 4) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_service_list_cell, parent, false);
            ServiceListCell cell = new ServiceListCell(view);
            return cell;
        } else if (viewType == 5) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_service_list_cell, parent, false);
            ServiceListCell cell = new ServiceListCell(view);
            return cell;
        } else if (viewType == 6) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_category_detail_header_view, parent, false);
            CategoryDetailHeaderView categoryListView = new CategoryDetailHeaderView(view);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return categoryListView;
        } else if (viewType == 7) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_service_list_cell, parent, false);
            ServiceListCell cell = new ServiceListCell(view);
            return cell;
        } else if (viewType == 8) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_service_list_cell, parent, false);
            ServiceListCell cell = new ServiceListCell(view);
            return cell;
        } else if (viewType == 9) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_service_list_cell, parent, false);
            ServiceListCell cell = new ServiceListCell(view);
            return cell;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 1 || position == 2 || position == 4 || position == 5 || position == 7 || position == 8 || position == 9) {
            ServiceListCell cell = (ServiceListCell)holder;
            cell.itemView.setOnClickListener(this);
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private OnItemClickListener listener;

    @Override
    public void onClick(View view) {
        listener.onItemClick();
    }

    public interface OnItemClickListener {
        void onItemClick();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
