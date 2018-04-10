package com.kelvin.jacksgogo.Adapter.Events;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.GoClub_Events.EventListDetailCell;
import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 12/18/2017.
 */

public class EventsListingAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private Context mContext;

    public EventsListingAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.cell_event_list_detail, null, false);

        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        return new EventListDetailCell(view, mContext);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        EventListDetailCell cell = (EventListDetailCell) holder;
        cell.itemView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return 3;
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
