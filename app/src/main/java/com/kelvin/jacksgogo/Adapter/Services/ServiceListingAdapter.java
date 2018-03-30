package com.kelvin.jacksgogo.Adapter.Services;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services.ServiceListCell;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PUMA on 11/14/2017.
 */

public class ServiceListingAdapter extends RecyclerView.Adapter {

    private Context mContext;

    private ArrayList<JGGCategoryModel> mCategories = new ArrayList<>();

    public ServiceListingAdapter(Context context) {
        this.mContext = context;
    }

    public void notifyDataChanged(ArrayList<JGGCategoryModel> categories) {
        mCategories = categories;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_service_list, parent, false);
        ServiceListCell categoryListView = new ServiceListCell(view, mContext);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return categoryListView;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ServiceListCell cell = (ServiceListCell)holder;
        JGGCategoryModel category = mCategories.get(position);
        cell.setData(category);
        cell.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
