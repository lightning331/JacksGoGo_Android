package com.kelvin.jacksgogo.Adapter.Services;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Edit.EditJobTimeSlotsCell;
import com.kelvin.jacksgogo.CustomView.Views.SectionTitleView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.System.JGGTimeSlotModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by storm2 on 4/10/2018.
 */

public class ServiceDetailTimeSlotsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<JGGTimeSlotModel> mTimeSlots = new ArrayList<>();

    private OnServiceDetailTimeSlotItemClickListener listener;

    public interface OnServiceDetailTimeSlotItemClickListener {
        void onServiceDetailTimeSlotClick(boolean isDelete, int position);
    }

    public void setOnItemClickListener(OnServiceDetailTimeSlotItemClickListener listener) {
        this.listener = listener;
    }

    public ServiceDetailTimeSlotsAdapter(Context context, ArrayList<JGGTimeSlotModel> list) {
        mContext = context;
        mTimeSlots = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View timeSlotsTitle = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_section_title, parent, false);
            SectionTitleView timeViewHolder = new SectionTitleView(timeSlotsTitle);
            timeViewHolder.txtTitle.setText("Time Slots");
            timeViewHolder.txtTitle.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
            timeViewHolder.background.setBackgroundResource(R.color.JGGWhite);
            timeViewHolder.txtTitle.setGravity(Gravity.CENTER_HORIZONTAL);
            return timeViewHolder;
        } else {
            View timesloatView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_edit_job_time_slots, parent, false);
            EditJobTimeSlotsCell viewHolder = new EditJobTimeSlotsCell(timesloatView);
            return viewHolder;
        }
    }

    public void refreshData(ArrayList<JGGTimeSlotModel> timeSlotModels) {
        this.mTimeSlots = timeSlotModels;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof EditJobTimeSlotsCell) {
            EditJobTimeSlotsCell cell = (EditJobTimeSlotsCell) holder;
            cell.setData(mTimeSlots.get(position-1));
            cell.btnSlots.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onServiceDetailTimeSlotClick(false, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mTimeSlots.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
