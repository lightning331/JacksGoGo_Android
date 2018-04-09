package com.kelvin.jacksgogo.CustomView.Views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.kelvin.jacksgogo.CustomView.JGGCalendarDecorator;
import com.kelvin.jacksgogo.CustomView.JGGCalendarDotDecorator;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.prolificinteractive.jggcalendarview.CalendarDay;
import com.prolificinteractive.jggcalendarview.MaterialCalendarView;
import com.prolificinteractive.jggcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.prolificinteractive.jggcalendarview.MaterialCalendarView.SELECTION_MODE_MULTIPLE;

/**
 * Created by PUMA on 1/20/2018.
 */

public class JGGCalendarDialog extends android.app.AlertDialog.Builder implements View.OnClickListener, OnDateSelectedListener {

    private Context mContext;

    public MaterialCalendarView calendar;
    public TextView lblCalendarTitle;
    public TextView btnCalendarCancel;
    public TextView btnCalendarOk;

    private AppointmentType mType;
    private int okButtonColor;
    private int cancelButtonColor;
    private int textColor;
    private Drawable leftArrow;
    private Drawable rightArrow;

    public JGGCalendarDialog(Context context, AppointmentType type) {
        super(context);
        mContext = context;
        mType = type;
        if (mType == AppointmentType.SERVICES) {
            okButtonColor = ContextCompat.getColor(mContext, R.color.JGGGreen);
            cancelButtonColor = ContextCompat.getColor(mContext, R.color.JGGGreen10Percent);
            leftArrow = mContext.getResources().getDrawable(R.mipmap.button_previous_green);
            rightArrow = mContext.getResources().getDrawable(R.mipmap.button_next_green);
            textColor = R.style.GreenTextAppearance;
        } else if (mType == AppointmentType.JOBS) {
            okButtonColor = ContextCompat.getColor(mContext, R.color.JGGCyan);
            cancelButtonColor = ContextCompat.getColor(mContext, R.color.JGGCyan10Percent);
            leftArrow = mContext.getResources().getDrawable(R.mipmap.button_previous_cyan);
            rightArrow = mContext.getResources().getDrawable(R.mipmap.button_next_cyan);
            textColor = R.style.CyanTextAppearance;
        } else if (mType == AppointmentType.GOCLUB) {
            okButtonColor = ContextCompat.getColor(mContext, R.color.JGGPurple);
            cancelButtonColor = ContextCompat.getColor(mContext, R.color.JGGPurple10Percent);
            leftArrow = mContext.getResources().getDrawable(R.mipmap.button_previous_purple);
            rightArrow = mContext.getResources().getDrawable(R.mipmap.button_next_purple);
            textColor = R.style.PurpleTextAppearance;
        } else if (mType == AppointmentType.USERS) {
            okButtonColor = ContextCompat.getColor(mContext, R.color.JGGOrange);
            cancelButtonColor = ContextCompat.getColor(mContext, R.color.JGGOrange10Percent);
            leftArrow = mContext.getResources().getDrawable(R.mipmap.button_previous_orange);
            rightArrow = mContext.getResources().getDrawable(R.mipmap.button_next_orange);
            textColor = R.style.OrangeTextAppearance;
        }
        initView();
    }

    private void initView() {
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
        calendar.setLeftArrowMask(leftArrow);
        calendar.setRightArrowMask(rightArrow);
        calendar.setDateTextAppearance(textColor);
        setCurrentDateDot();
        setSelectedDateCircle();

        this.setView(view);
    }

    public void setSelectedDate(Date date) {
        calendar.setSelectedDate(date);
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        setCurrentDateDot();
        setSelectedDateCircle();
        btnCalendarOk.setBackgroundColor(okButtonColor);
        btnCalendarOk.setTextColor(ContextCompat.getColor(mContext, R.color.JGGWhite));
        btnCalendarOk.setOnClickListener(this);
    }

    private void setCurrentDateDot() {
        List<CalendarDay> currentDateList = new ArrayList<>();
        currentDateList.add(new CalendarDay(new Date()));
        calendar.addDecorator(new JGGCalendarDotDecorator(mContext, currentDateList, mType));
    }

    private void setSelectedDateCircle() {
        List<CalendarDay> selectedDateList = new ArrayList<>();
        selectedDateList.add(calendar.getSelectedDate());
        calendar.addDecorator(new JGGCalendarDecorator(mContext, selectedDateList, mType));
    }

    @Override
    public void onClick(View view) {
        if (calendar.getSelectedDate() != null) {
            listener.onDoneButtonClick(view, calendar.getSelectedDates());
        } else
            listener.onDoneButtonClick(view, null);
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onDoneButtonClick(View view, List<CalendarDay> dates);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
