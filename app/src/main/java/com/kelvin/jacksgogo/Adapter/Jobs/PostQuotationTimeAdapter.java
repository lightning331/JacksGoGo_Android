package com.kelvin.jacksgogo.Adapter.Jobs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.CalendarCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Edit.EditJobTimeSlotsCell;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGQuotationModel;
import com.kelvin.jacksgogo.Utils.Models.System.JGGTimeSlotModel;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Date;

import static com.kelvin.jacksgogo.Utils.JGGTimeManager.appointmentMonthDate;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.convertCalendarDate;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getTimeSlot;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getTimeSlotPosition;

/**
 * Created by PUMA on 11/10/2017.
 */

public class PostQuotationTimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    private CalendarCell calendarViewCell;
    private EditJobTimeSlotsCell timeSlotsCell;

    private ArrayList<JGGTimeSlotModel> mSessions = new ArrayList<>();
    private JGGQuotationModel mQuotation;
    private ArrayList<String> mTimeSlots = new ArrayList<>();

    public PostQuotationTimeAdapter(Context context, JGGQuotationModel data) {
        this.mContext = context;
        this.mQuotation = data;
        addTimeSlot();
    }

    private void addTimeSlot() {
        mTimeSlots.add("10:00AM - 12:00PM");
        mTimeSlots.add("01:00PM - 03:00PM");
        mTimeSlots.add("04:00PM - 06:00PM");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == 0) {
            View dateTitleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.jgg_calendar_view, parent, false);
            return new CalendarCell(dateTitleView);
        } else {
            View timeSlotView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_edit_job_time_slots, parent, false);
            return new EditJobTimeSlotsCell(timeSlotView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (position == 0) {
            calendarViewCell = (CalendarCell)holder;
            if (mQuotation.getSessions() != null
                    && mQuotation.getSessions().size() > 0) {
                String startOn = mQuotation.getSessions().get(position).getStartOn();
                Date date = appointmentMonthDate(startOn);
                calendarViewCell.calendarView.setSelectedDate(date);
            } else
                calendarViewCell.calendarView.setSelectedDate(new Date());
        } else {
            timeSlotsCell = (EditJobTimeSlotsCell)holder;
            timeSlotsCell.lblSlots.setText(mTimeSlots.get(position - 1));
            if (mQuotation.getSessions() != null
                    && mQuotation.getSessions().size() > 0) {

                int bookedTime = getTimeSlotPosition(mQuotation.getSessions().get(0));
                if (position == bookedTime) {
                    timeSlotsCell.btnSlots.setText("Booked");
                    timeSlotsCell.btnSlots.setTextColor(ContextCompat.getColor(mContext, R.color.JGGGrey2));
                    timeSlotsCell.btnSlots.setBackgroundResource(R.drawable.grey_background);
                }
            }

            timeSlotsCell.btnSlots.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (calendarViewCell.calendarView.getSelectedDate().getDate().before(new Date())) {
                        Toast.makeText(mContext, "Please set Date later than current time.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    JGGTimeSlotModel timeSlot = getTimeSlot(position, calendarViewCell.calendarView.getSelectedDate().getDate());
                    timeSlot.setSpecific(true);
                    mSessions.add(timeSlot);
                    listener.onItemClick(mSessions);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mTimeSlots.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ArrayList<JGGTimeSlotModel> sessions);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}