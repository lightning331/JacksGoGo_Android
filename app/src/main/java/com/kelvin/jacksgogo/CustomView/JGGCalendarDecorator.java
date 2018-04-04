package com.kelvin.jacksgogo.CustomView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Collection;
import java.util.HashSet;

public class JGGCalendarDecorator implements DayViewDecorator {
    private Context mContext;
    private final HashSet<CalendarDay> dates;
    private AppointmentType mType;
    private Drawable drawable;

    public JGGCalendarDecorator(Context context, Collection<CalendarDay> dates, AppointmentType type) {
        this.mContext = context;
        this.dates = new HashSet<>(dates);
        this.mType = type;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        if (mType == AppointmentType.SERVICES) {
            drawable = ContextCompat.getDrawable(mContext, R.drawable.calendar_selector_green);
        } else if (mType == AppointmentType.JOBS) {
            drawable = ContextCompat.getDrawable(mContext, R.drawable.calendar_selector_cyan);
        } else if (mType == AppointmentType.GOCLUB || mType == AppointmentType.EVENT) {
            drawable = ContextCompat.getDrawable(mContext, R.drawable.calendar_selector_purple);
        } else if (mType == AppointmentType.USERS) {
            drawable = ContextCompat.getDrawable(mContext, R.drawable.calendar_selector_orange);
        }
        view.setBackgroundDrawable(drawable);
    }
}
