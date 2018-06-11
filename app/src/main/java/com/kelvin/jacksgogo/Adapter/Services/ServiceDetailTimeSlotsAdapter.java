package com.kelvin.jacksgogo.Adapter.Services;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Edit.EditJobTimeSlotsCell;
import com.kelvin.jacksgogo.CustomView.Views.HeaderTitleView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.System.JGGTimeSlotModel;

import java.util.ArrayList;

import static com.kelvin.jacksgogo.Utils.Global.setBoldText;

/**
 * Created by storm2 on 4/10/2018.
 */

public class ServiceDetailTimeSlotsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<JGGTimeSlotModel> mTimeSlots = new ArrayList<>();

    private OnServiceDetailTimeSlotItemClickListener listener;

    public interface OnServiceDetailTimeSlotItemClickListener {
        void onServiceDetailTimeSlotClick(int position);
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
            HeaderTitleView timeViewHolder = new HeaderTitleView(timeSlotsTitle);
            timeViewHolder.txtTitle.setText(setBoldText("Time Slots"));
            timeViewHolder.background.setBackgroundResource(R.color.JGGWhite);
            timeViewHolder.txtTitle.setGravity(Gravity.CENTER_HORIZONTAL);
            return timeViewHolder;
        } else {
            View timeSlotView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_edit_job_time_slots, parent, false);
            EditJobTimeSlotsCell viewHolder = new EditJobTimeSlotsCell(timeSlotView);
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
            cell.setData(mTimeSlots.get(position - 1));
            cell.btnSlots.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onServiceDetailTimeSlotClick(position);
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
