package com.kelvin.jacksgogo.Adapter;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.jacksgogo.Fragments.Appointments.EditJobFragment;
import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/10/2017.
 */

public class EditJobTimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    EditJobFragment mContext;

    int ITEM_COUNT = 7;
    private Context context;

    public EditJobTimeAdapter(EditJobFragment context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == 0) {
            View jobTitleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_detail_header_view, parent, false);
            SectionTitleViewHolder jobTitleViewHolder = new SectionTitleViewHolder(jobTitleView);
            jobTitleViewHolder.title.setText("This will create a one-time job.");
            jobTitleViewHolder.title.setTypeface(Typeface.create("muliregular", Typeface.NORMAL));
            return jobTitleViewHolder;
        } else if (viewType == 1) {
            View dateTitleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_detail_header_view, parent, false);
            SectionTitleViewHolder dateTitleViewHolder = new SectionTitleViewHolder(dateTitleView);
            dateTitleViewHolder.title.setText("When do you need the services?");
            return dateTitleViewHolder;
        } else if (viewType == 2) {
            View dateTitleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.jgg_calendar_view, parent, false);
            return new CalendarViewHolder(dateTitleView);
        } else if (viewType == 3) {
            View timeSlotsTitle = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_detail_header_view, parent, false);
            SectionTitleViewHolder timeViewHolder = new SectionTitleViewHolder(timeSlotsTitle);
            timeViewHolder.title.setText("Time Slots");
            timeViewHolder.background.setBackgroundResource(R.color.JGGWhite);
            timeViewHolder.title.setGravity(Gravity.CENTER_HORIZONTAL);
            return timeViewHolder;
        } else if (viewType == 4) {
            View timesloatView = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_job_time_slots_cell, parent, false);
            return new TimeSlotsViewHolder(timesloatView);
        } else if (viewType == 5) {
            View timesloatView = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_job_time_slots_cell, parent, false);
            return new TimeSlotsViewHolder(timesloatView);
        } else if (viewType == 6) {
            View timesloatView = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_job_time_slots_cell, parent, false);
            return new TimeSlotsViewHolder(timesloatView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return ITEM_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}

class CalendarViewHolder extends RecyclerView.ViewHolder {

    public CalendarViewHolder(View itemView) {
        super(itemView);
    }
}

class TimeSlotsViewHolder extends RecyclerView.ViewHolder {

    public TimeSlotsViewHolder(View itemView) {
        super(itemView);
    }
}
