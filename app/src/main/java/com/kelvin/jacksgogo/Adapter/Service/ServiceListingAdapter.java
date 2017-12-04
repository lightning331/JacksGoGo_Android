package com.kelvin.jacksgogo.Adapter.Service;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_list_cell, parent, false);
        ServiceListCell categoryListView = new ServiceListCell(view);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return categoryListView;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ServiceListCell cell = (ServiceListCell)holder;
        cell.itemView.setOnClickListener(this);
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
        listener.onItemClick();
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
