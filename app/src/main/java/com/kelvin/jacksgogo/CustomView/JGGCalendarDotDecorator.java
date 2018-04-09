package com.kelvin.jacksgogo.CustomView;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.prolificinteractive.jggcalendarview.CalendarDay;
import com.prolificinteractive.jggcalendarview.DayViewDecorator;
import com.prolificinteractive.jggcalendarview.DayViewFacade;
import com.prolificinteractive.jggcalendarview.spans.DotSpan;

import java.util.Collection;
import java.util.HashSet;

public class JGGCalendarDotDecorator implements DayViewDecorator {

    private Context mContext;
    private final HashSet<CalendarDay> mDates;
    private AppointmentType mType;
    private int mColor;

    public JGGCalendarDotDecorator(Context context, Collection<CalendarDay> dates, AppointmentType type) {
        mContext = context;
        mDates =  new HashSet<>(dates);
        mType = type;

        if (mType == AppointmentType.SERVICES) {
            mColor = ContextCompat.getColor(mContext, R.color.JGGGreen);
        } else if (mType == AppointmentType.JOBS) {
            mColor = ContextCompat.getColor(mContext, R.color.JGGCyan);
        } else if (mType == AppointmentType.GOCLUB || mType == AppointmentType.EVENT) {
            mColor = ContextCompat.getColor(mContext, R.color.JGGPurple);
        }
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return mDates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(5, mColor));
    }
}
