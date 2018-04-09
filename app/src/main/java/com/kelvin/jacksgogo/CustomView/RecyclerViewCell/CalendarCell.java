package com.kelvin.jacksgogo.CustomView.RecyclerViewCell;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kelvin.jacksgogo.R;
import com.prolificinteractive.jggcalendarview.MaterialCalendarView;

/**
 * Created by PUMA on 11/13/2017.
 */

public class CalendarCell extends RecyclerView.ViewHolder {

    public MaterialCalendarView calendarView;

    public CalendarCell(View itemView) {
        super(itemView);

        calendarView = itemView.findViewById(R.id.cell_calendar);
        //calendarView.setSelectionMode(SELECTION_MODE_MULTIPLE);
    }
}
