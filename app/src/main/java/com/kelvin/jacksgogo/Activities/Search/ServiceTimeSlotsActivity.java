package com.kelvin.jacksgogo.Activities.Search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.Adapter.Services.ServiceDetailTimeSlotsAdapter;
import com.kelvin.jacksgogo.CustomView.JGGCalendarDecorator;
import com.kelvin.jacksgogo.CustomView.JGGCalendarDotDecorator;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.System.JGGTimeSlotModel;
import com.prolificinteractive.jggcalendarview.CalendarDay;
import com.prolificinteractive.jggcalendarview.MaterialCalendarView;
import com.prolificinteractive.jggcalendarview.OnDateSelectedListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.kelvin.jacksgogo.Utils.JGGTimeManager.appointmentMonthDateString;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getAppointmentDay;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getAppointmentYear;
import static com.prolificinteractive.jggcalendarview.MaterialCalendarView.SELECTION_MODE_EDIT;

public class ServiceTimeSlotsActivity extends AppCompatActivity implements OnDateSelectedListener {

    @BindView(R.id.service_time_slots_actionbar) Toolbar mToolbar;
    @BindView(R.id.service_time_slots_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.img_category) ImageView img_category;
    @BindView(R.id.lbl_category) TextView lbl_category;
    @BindView(R.id.calendarView) MaterialCalendarView calendarView;
    @BindView(R.id.lbl_title) TextView lbl_title;
    @BindView(R.id.txt_date_time) TextView txt_date_time;
    @BindView(R.id.rl_time_recyclerview) RelativeLayout rl_time_recyclerview;
    @BindView(R.id.ll_calendar) LinearLayout ll_calendar;

    private JGGActionbarView actionbarView;
    private ServiceDetailTimeSlotsAdapter adapter;
    private ArrayList<JGGTimeSlotModel> mTimeSlots = new ArrayList<>();
    private CalendarDay mSelectedCaledarDay;
    private boolean isBoughtService = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_time_slots);
        ButterKnife.bind(this);

        mTimeSlots = JGGAppManager.getInstance().getSelectedAppointment().getSessions();
        isBoughtService = getIntent().getBooleanExtra("is_bought_service", false);

        actionbarView = new JGGActionbarView(this);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        actionbarView.setStatus(JGGActionbarView.EditStatus.SERVICE_TIME_SLOTS, AppointmentType.UNKNOWN);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        if (isBoughtService) {
            if (mTimeSlots.size() == 0) {
                setNoTimeSlots();
            } else {
                setTimeSlots();
            }
        } else {
            lbl_title.setVisibility(View.GONE);
            setTimeSlots();
        }

        setData();
    }

    private void setData() {
        // Category
        Picasso.with(this)
                .load(JGGAppManager.getInstance().getSelectedAppointment().getCategory().getImage())
                .placeholder(null)
                .into(img_category);
        lbl_category.setText(JGGAppManager.getInstance().getSelectedAppointment().getTitle());
    }

    private void setNoTimeSlots() {
        rl_time_recyclerview.setVisibility(View.GONE);
        ll_calendar.setVisibility(View.GONE);
    }

    private void setTimeSlots() {

        txt_date_time.setVisibility(View.GONE);

        // TODO - initialize RecyclerView
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        }

        Date now = new Date();
        adapter = new ServiceDetailTimeSlotsAdapter(this, getSelectedDateTimeSlot(now));
        adapter.setOnItemClickListener(new ServiceDetailTimeSlotsAdapter.OnServiceDetailTimeSlotItemClickListener() {
            @Override
            public void onServiceDetailTimeSlotClick(int position) {

            }
        });
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

    private ArrayList<JGGTimeSlotModel> getSelectedDateTimeSlot(Date selectedDate) {
        ArrayList<JGGTimeSlotModel> slotModels = new ArrayList<JGGTimeSlotModel>();
        for (int i=0;i<mTimeSlots.size(); i++) {
            JGGTimeSlotModel slotModel = mTimeSlots.get(i);
            if (slotModel.isEqualSlotDate(selectedDate)) {
                slotModels.add(slotModel);
            }
        }
        return slotModels;
    }

    private void updateRecyclerView(ArrayList<JGGTimeSlotModel> timeSlotModels) {
        adapter.refreshData(timeSlotModels);
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
            String startOn = appointmentMonthDateString(timeSlotModel.getStartOn());
            Date startDate = this.getSlotDate(startOn);
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
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
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

            ArrayList<JGGTimeSlotModel> selectedSlotModels = getSelectedDateTimeSlot(date.getDate());
            if (selectedSlotModels.size() > 0) {
                List<CalendarDay> selectedDateList = new ArrayList<>();
                selectedDateList.add(date);
                calendarView.addDecorator(new JGGCalendarDecorator(this, selectedDateList, AppointmentType.SERVICES));
            }

            updateRecyclerView(selectedSlotModels);

        }

        updateSelectedDaysDecorator();
    }
}
