package com.kelvin.jacksgogo.Activities.Search;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kelvin.jacksgogo.Adapter.Services.ServiceDetailTimeSlotsAdapter;
import com.kelvin.jacksgogo.CustomView.JGGCalendarDecorator;
import com.kelvin.jacksgogo.CustomView.JGGCalendarDotDecorator;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Edit.EditJobTimeSlotsCell;
import com.kelvin.jacksgogo.CustomView.Views.SectionTitleView;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.System.JGGTimeSlotModel;
import com.prolificinteractive.jggcalendarview.CalendarDay;
import com.prolificinteractive.jggcalendarview.MaterialCalendarView;
import com.prolificinteractive.jggcalendarview.OnDateSelectedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.kelvin.jacksgogo.Utils.JGGTimeManager.convertCalendarDate;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getAppointmentDay;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getAppointmentYear;
import static com.prolificinteractive.jggcalendarview.MaterialCalendarView.SELECTION_MODE_EDIT;

public class ServiceTimeSlotsActivity extends AppCompatActivity implements OnDateSelectedListener {

    private Toolbar mToolbar;
    JGGActionbarView actionbarView;
    RecyclerView recyclerView;
    ServiceDetailTimeSlotsAdapter adapter;
    private MaterialCalendarView calendarView;
    ArrayList<JGGTimeSlotModel> mTimeSlots = new ArrayList<>();
    CalendarDay mSelectedCaledarDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_time_slots);

        mTimeSlots = JGGAppManager.getInstance().getSelectedAppointment().getSessions();

        actionbarView = new JGGActionbarView(this);
        mToolbar = (Toolbar) findViewById(R.id.service_time_slots_actionbar);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        actionbarView.setStatus(JGGActionbarView.EditStatus.SERVICE_TIME_SLOTS, AppointmentType.UNKNOWN);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        // TODO - initialize calendarView
        calendarView =  (MaterialCalendarView) findViewById(R.id.calendarView);

        // TODO - initialize RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.service_time_slots_recycler_view);

        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        }

        Date now = new Date();
        adapter = new ServiceDetailTimeSlotsAdapter(this, getSeletedDateTimeSlot(now));
        recyclerView.setAdapter(adapter);

        calendarView.setOnDateChangedListener(this);

        // set calendar mode as edit-mode
        calendarView.setSelectionMode(SELECTION_MODE_EDIT);

        calendarView.setSelectedDate(now);
        this.onDateSelected(calendarView, calendarView.getSelectedDate(), true);

    }

    private void setCurrentDateDot() {
        List<CalendarDay> currentDateList = new ArrayList<>();
        currentDateList.add(new CalendarDay(new Date()));
        calendarView.addDecorator(new JGGCalendarDotDecorator(this, currentDateList, AppointmentType.SERVICES));
    }

    private void setSelectedDateCircle() {
        List<CalendarDay> selectedDateList = new ArrayList<>();
        selectedDateList.add(calendarView.getSelectedDate());
        calendarView.addDecorator(new JGGCalendarDecorator(this, selectedDateList, AppointmentType.SERVICES));
    }

    private void actionbarViewItemClick(View view) {
        if (view.getId() == R.id.btn_back) {
            onBackPressed();
        }
    }

    private ArrayList<JGGTimeSlotModel> getSeletedDateTimeSlot(Date seletedDate) {
        ArrayList<JGGTimeSlotModel> slotModels = new ArrayList<JGGTimeSlotModel>();
        for (int i=0;i<mTimeSlots.size(); i++) {
            JGGTimeSlotModel slotModel = mTimeSlots.get(i);
            if (slotModel.isEqualSlotDate(seletedDate)) {
                slotModels.add(slotModel);
            }
        }
        return slotModels;
    }

    private void updateRecyclerView(ArrayList<JGGTimeSlotModel> timeslotModels) {
        adapter.refreshData(timeslotModels);
    }

    private void showDateSelected(Date date) {

        Calendar calendar = Calendar.getInstance();
        int year = Integer.valueOf(getAppointmentYear(date));
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        int month = Integer.valueOf(monthFormat.format(date)) - 1;
        int day = Integer.valueOf(getAppointmentDay(date));
        calendar.set(year, month, day);
        calendarView.setDateSelected(calendar, true);
    }

    private void updateSelectedDaysDecorator() {
        ArrayList<Date> slotDays = new ArrayList<Date>();
        for (int i=0; i<mTimeSlots.size(); i++) {
            JGGTimeSlotModel timeSlotModel = mTimeSlots.get(i);
            String startOn = timeSlotModel.getStartOn();
            String startDateStr = startOn.substring(0, 10);
            Date startDate = this.getSlotDate(startDateStr);
            if (!slotDays.contains(startDate)) {
                slotDays.add(startDate);
            }
        }

        for (int i=0; i<slotDays.size(); i++) {
            Date slotDay = slotDays.get(i);
            showDateSelected(slotDay);
        }
    }

    public Date getSlotDate(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {

            if (dateString != null) {
                Date date = formatter.parse(dateString);
                return date;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        calendarView.setSelectedDate(date);

        mSelectedCaledarDay = date;

        if (calendarView.getSelectionMode() == SELECTION_MODE_EDIT) {
            calendarView.removeDecorators();

            setCurrentDateDot();
            setSelectedDateCircle();

            Log.d("onDateSelected", convertCalendarDate(date.getDate()));
            ArrayList<JGGTimeSlotModel> seletedSlotModels = getSeletedDateTimeSlot(date.getDate());
            if (seletedSlotModels.size() > 0) {
                List<CalendarDay> selectedDateList = new ArrayList<>();
                selectedDateList.add(date);
//                calendarView.removeDecorators();
                calendarView.addDecorator(new JGGCalendarDecorator(this, selectedDateList, AppointmentType.SERVICES));
            }

            updateRecyclerView(seletedSlotModels);

        }

        updateSelectedDaysDecorator();
    }
}
