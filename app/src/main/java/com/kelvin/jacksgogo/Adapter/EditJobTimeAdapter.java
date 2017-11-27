package com.kelvin.jacksgogo.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.jacksgogo.CustomView.JGGCalendarView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.SectionTitleView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail.EditJobTimeSlotsCell;
import com.kelvin.jacksgogo.Fragments.Appointments.EditJobFragment;
import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/10/2017.
 */

public class EditJobTimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;

    int ITEM_COUNT = 7;

    public EditJobTimeAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == 0) {
            View jobTitleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_title_view, parent, false);
            SectionTitleView jobTitleViewHolder = new SectionTitleView(jobTitleView);
            jobTitleViewHolder.txtTitle.setText("This will create a one-time job.");
            return jobTitleViewHolder;
        } else if (viewType == 1) {
            View dateTitleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_title_view, parent, false);
            SectionTitleView dateTitleViewHolder = new SectionTitleView(dateTitleView);
            dateTitleViewHolder.txtTitle.setText("When do you need the services?");
            dateTitleViewHolder.txtTitle.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
            return dateTitleViewHolder;
        } else if (viewType == 2) {
            View dateTitleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.jgg_calendar_view, parent, false);
            return new JGGCalendarView(dateTitleView);
        } else if (viewType == 3) {
            View timeSlotsTitle = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_title_view, parent, false);
            SectionTitleView timeViewHolder = new SectionTitleView(timeSlotsTitle);
            timeViewHolder.txtTitle.setText("Time Slots");
            timeViewHolder.txtTitle.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
            timeViewHolder.background.setBackgroundResource(R.color.JGGWhite);
            timeViewHolder.txtTitle.setGravity(Gravity.CENTER_HORIZONTAL);
            return timeViewHolder;
        } else {
            View timesloatView = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_job_time_slots_cell, parent, false);
            return new EditJobTimeSlotsCell(timesloatView);
        }
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