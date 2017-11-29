package com.kelvin.jacksgogo.CustomView;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kelvin.jacksgogo.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import static android.view.accessibility.AccessibilityNodeInfo.CollectionInfo.SELECTION_MODE_MULTIPLE;

/**
 * Created by PUMA on 11/13/2017.
 */

public class JGGCalendarView extends RecyclerView.ViewHolder {

    public MaterialCalendarView calendarView;

    public JGGCalendarView(View itemView) {
        super(itemView);

        calendarView = itemView.findViewById(R.id.jgg_calendarView);
        //calendarView.setSelectionMode(SELECTION_MODE_MULTIPLE);
    }
}
