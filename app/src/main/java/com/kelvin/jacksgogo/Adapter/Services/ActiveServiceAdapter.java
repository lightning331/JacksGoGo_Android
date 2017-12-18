package com.kelvin.jacksgogo.Adapter.Services;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services.ServiceListDetailCell;
import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/14/2017.
 */

public class ActiveServiceAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.cell_service_list_detail, null, false);

        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        return new ServiceListDetailCell(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ServiceListDetailCell cell = (ServiceListDetailCell)holder;
        cell.itemView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return 5;
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
