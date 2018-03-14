package com.kelvin.jacksgogo.Adapter.Jobs;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.CalendarCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Edit.EditJobTimeSlotsCell;
import com.kelvin.jacksgogo.CustomView.Views.SectionTitleView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGServiceModel;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by PUMA on 11/10/2017.
 */

public class PostQuotationTimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    Context mContext;

    CalendarCell calendarViewCell;
    EditJobTimeSlotsCell timeSlotsCell;

    ArrayList<Date> selectedDates = new ArrayList<>();
    Date selectedDate;

    JGGServiceModel serviceObject;
    boolean isRequest;

    int ITEM_COUNT = 7;

    public PostQuotationTimeAdapter(Context context, boolean isRequest, JGGServiceModel data) {
        this.mContext = context;
        this.isRequest = isRequest;
        this.serviceObject = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == 0) {
            View jobTitleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_section_title, parent, false);
            SectionTitleView jobTitleViewHolder = new SectionTitleView(jobTitleView);
            return jobTitleViewHolder;
        } else if (viewType == 1) {
            View dateTitleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_section_title, parent, false);
            SectionTitleView dateTitleViewHolder = new SectionTitleView(dateTitleView);
            return dateTitleViewHolder;
        } else if (viewType == 2) {
            View dateTitleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.jgg_calendar_view, parent, false);
            return new CalendarCell(dateTitleView);
        } else if (viewType == 3) {
            View timeSlotsTitle = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_section_title, parent, false);
            SectionTitleView timeViewHolder = new SectionTitleView(timeSlotsTitle);
            return timeViewHolder;
        } else {
            View timeSlotView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_edit_job_time_slots, parent, false);
            return new EditJobTimeSlotsCell(timeSlotView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            SectionTitleView jobTitleViewHolder = (SectionTitleView)holder;
            jobTitleViewHolder.txtTitle.setText("This will create a one-time job.");
        } else if (position == 1) {
            SectionTitleView dateTitleViewHolder = (SectionTitleView)holder;
            dateTitleViewHolder.txtTitle.setText("When do you need the services?");
            dateTitleViewHolder.txtTitle.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
        } else if (position == 2) {
            calendarViewCell = (CalendarCell)holder;
            if (!isRequest) {

            } else {
                if (serviceObject.getDate() != null) {
                    calendarViewCell.calendarView.setDateSelected(serviceObject.getDate(), true);
                }
                calendarViewCell.calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
                    @Override
                    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                        selectedDate = date.getDate();
//                    selectedDates.add(date.getDate());
//                    Log.d(TAG, "selected dates =========== " + selectedDates);
                    }
                });
            }
        } else if (position == 3) {
            SectionTitleView timeViewHolder = (SectionTitleView)holder;
            timeViewHolder.txtTitle.setText("Time Slots");
            timeViewHolder.txtTitle.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
            timeViewHolder.background.setBackgroundResource(R.color.JGGWhite);
            timeViewHolder.txtTitle.setGravity(Gravity.CENTER_HORIZONTAL);
        } else {
            timeSlotsCell = (EditJobTimeSlotsCell)holder;
            timeSlotsCell.btnSlots.setOnClickListener(this);
        }
    }

    @Override
    public int getItemCount() {
        return ITEM_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_time_slots_status) {
            listener.onItemClick(selectedDate);
        }
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Date date);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}