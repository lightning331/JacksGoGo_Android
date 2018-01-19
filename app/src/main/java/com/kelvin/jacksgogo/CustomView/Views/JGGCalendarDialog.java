package com.kelvin.jacksgogo.CustomView.Views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppBaseModel;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;

/**
 * Created by PUMA on 1/20/2018.
 */

public class JGGCalendarDialog extends android.app.AlertDialog.Builder implements View.OnClickListener, OnDateSelectedListener {

    private Context mContext;

    public MaterialCalendarView calendar;
    public TextView lblCalendarTitle;
    public TextView btnCalendarCancel;
    public TextView btnCalendarOk;

    private JGGAppBaseModel.AppointmentType mType;
    private int okButtonColor;
    private int cancelButtonColor;
    private Drawable leftArrow;
    private Drawable rightArrow;

    public JGGCalendarDialog(Context context, JGGAppBaseModel.AppointmentType type) {
        super(context);
        mContext = context;
        mType = type;
        if (mType == JGGAppBaseModel.AppointmentType.SERVICES) {
            okButtonColor = ContextCompat.getColor(mContext, R.color.JGGGreen);
            cancelButtonColor = ContextCompat.getColor(mContext, R.color.JGGGreen10Percent);
            leftArrow = mContext.getResources().getDrawable(R.mipmap.button_previous_green);
            rightArrow = mContext.getResources().getDrawable(R.mipmap.button_next_green);
        } else if (mType == JGGAppBaseModel.AppointmentType.JOBS) {
            okButtonColor = ContextCompat.getColor(mContext, R.color.JGGCyan);
            cancelButtonColor = ContextCompat.getColor(mContext, R.color.JGGCyan10Percent);
            leftArrow = mContext.getResources().getDrawable(R.mipmap.button_previous_cyan);
            rightArrow = mContext.getResources().getDrawable(R.mipmap.button_next_cyan);
        } else if (mType == JGGAppBaseModel.AppointmentType.GOCLUB) {
            okButtonColor = ContextCompat.getColor(mContext, R.color.JGGPurple);
            cancelButtonColor = ContextCompat.getColor(mContext, R.color.JGGPurple10Percent);
            leftArrow = mContext.getResources().getDrawable(R.mipmap.button_previous_purple);
            rightArrow = mContext.getResources().getDrawable(R.mipmap.button_next_purple);
        }
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.dialog_calendar, null);

        calendar = (MaterialCalendarView) view.findViewById(R.id.add_time_duplicate_calendar);
        calendar.setOnDateChangedListener(this);
        lblCalendarTitle = (TextView) view.findViewById(R.id.lbl_duplicate_time_title);
        btnCalendarCancel = (TextView) view.findViewById(R.id.btn_add_time_duplicate_cancel);
        btnCalendarCancel.setOnClickListener(this);
        btnCalendarOk = (TextView) view.findViewById(R.id.btn_add_time_duplicate_ok);
        btnCalendarCancel.setBackgroundColor(cancelButtonColor);
        btnCalendarCancel.setTextColor(okButtonColor);
        calendar.setArrowColor(okButtonColor);
        calendar.setSelectionColor(okButtonColor);
        calendar.setLeftArrowMask(leftArrow);
        calendar.setRightArrowMask(rightArrow);

        this.setView(view);
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        btnCalendarOk.setBackgroundColor(okButtonColor);
        btnCalendarOk.setTextColor(ContextCompat.getColor(mContext, R.color.JGGWhite));
        btnCalendarOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd");
        if (calendar.getSelectedDate() != null)
            listener.onDoneButtonClick(view, dateFormat.format(calendar.getSelectedDate().getDate()));
        else
            listener.onDoneButtonClick(view, null);
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onDoneButtonClick(View view, String date);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
