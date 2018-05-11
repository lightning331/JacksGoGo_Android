package com.kelvin.jacksgogo.Fragments.GoClub_Event;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.Adapter.GoClub_Event.GcTimeSlotAdapter;
import com.kelvin.jacksgogo.Adapter.Services.PostServiceTimeSlotAdapter;
import com.kelvin.jacksgogo.CustomView.JGGCalendarDecorator;
import com.kelvin.jacksgogo.CustomView.JGGCalendarDotDecorator;
import com.kelvin.jacksgogo.CustomView.Views.JGGAddTimeSlotDialog;
import com.kelvin.jacksgogo.CustomView.Views.JGGCalendarDialog;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.GoClub_Event.JGGEventModel;
import com.kelvin.jacksgogo.Utils.Models.System.JGGTimeSlotModel;
import com.prolificinteractive.jggcalendarview.CalendarDay;
import com.prolificinteractive.jggcalendarview.MaterialCalendarView;
import com.prolificinteractive.jggcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.kelvin.jacksgogo.Utils.JGGTimeManager.appointmentMonthDate;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.convertCalendarDate;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getAppointmentDay;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getAppointmentMonth;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getAppointmentYear;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getTimePeriodString;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getTimeString;
import static com.prolificinteractive.jggcalendarview.MaterialCalendarView.SELECTION_MODE_EDIT;

/**
 * A simple {@link Fragment} subclass.
 */
public class GcTimeFragment extends Fragment implements
        OnDateSelectedListener {

    @BindView(R.id.txt_kind_event)              TextView txtKindEvent;
    @BindView(R.id.btn_one_time_event)          Button btnOneTimeEvent;
    @BindView(R.id.txt_when)                    TextView txtWhen;
    @BindView(R.id.ll_when)                     LinearLayout ll_when;
    @BindView(R.id.txt_date)                    TextView txtDate;
    @BindView(R.id.txt_time)                    TextView txtTime;
    @BindView(R.id.btn_repeating_event)         Button btnRepeatingEvent;
    @BindView(R.id.txt_post_service_set_time)   TextView txtTimeLater;
    @BindView(R.id.btn_now)                     Button btnNow;
    @BindView(R.id.btn_later)                   Button btnLater;
    @BindView(R.id.btn_next)                    Button btnNext;
    @BindView(R.id.ll_calendar_bg)              LinearLayout ll_calendar_bg;
    @BindView(R.id.gc_time_calendar)            MaterialCalendarView calendarView;
    @BindView(R.id.ll_schedule_bg)              LinearLayout ll_schedule_bg;
    @BindView(R.id.schedule_recycler_view)      RecyclerView recyclerView;
    @BindView(R.id.ll_set_time)                 LinearLayout ll_set_time;
    @BindView(R.id.btn_duplicate)               Button btnDuplicate;
    @BindView(R.id.btn_done)                    Button btnDone;
    @BindView(R.id.btn_view_timeslot)           Button btnViewTimeSlot;

    private Context mContext;
    private AlertDialog alertDialog;
    private boolean bOneTime = false;
    private boolean bRepeatingTime = false;
    private boolean bNow = false;
    private boolean bLater = false;

    private String startTime;
    private String endTime;
    private Date startOn;
    private Date endOn;

    private String selectedDay;

    private JGGEventModel mEvent;
    private ArrayList<JGGTimeSlotModel> mTimeSlots = new ArrayList<JGGTimeSlotModel>();

    private JGGTimeSlotModel mSelectedTimeSlot = new JGGTimeSlotModel(); // use this variable when duplicate
    private ArrayList<CalendarDay> calendarDays = new ArrayList<>();
    private CalendarDay mSelectedCaledarDay;
    private boolean isEditTimeSlot;
    private JGGTimeSlotModel mEditingTimeSlot;

    public GcTimeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gc_time, container, false);
        ButterKnife.bind(this, view);

        initView();

        return view;
    }

    private void initView() {
        txtWhen.setVisibility(View.GONE);
        ll_when.setVisibility(View.GONE);
        txtTimeLater.setVisibility(View.GONE);
        btnNow.setVisibility(View.GONE);
        btnLater.setVisibility(View.GONE);
        btnNext.setVisibility(View.GONE);
        ll_calendar_bg.setVisibility(View.GONE);
        ll_schedule_bg.setVisibility(View.GONE);
        btnDone.setVisibility(View.GONE);
        btnViewTimeSlot.setVisibility(View.GONE);

        btnNext.setClickable(false);

        mEvent = JGGAppManager.getInstance().getSelectedEvent();
        if (mEvent != null
                && mEvent.getSessions() != null
                && mEvent.getSessions().size() > 0) {
            mTimeSlots = mEvent.getSessions();
            mSelectedTimeSlot = mEvent.getSessions().get(0);
            bOneTime = mEvent.getOnetime();
            Date date = appointmentMonthDate(mSelectedTimeSlot.getStartOn());
            Date endDate = appointmentMonthDate(mSelectedTimeSlot.getEndOn());

            if (bOneTime) {
                bOneTime = false;
                onClickOneTimeEvent();
                setOneTimeDate(date);
                setOneTimePeriod(date, endDate);
            } else {
                onClickDone();
                calendarView.setSelectedDate(date);
                updateRecyclerView(mTimeSlots);
            }
        } else {
            calendarView.setSelectedDate(new Date());
        }

        calendarView.setOnDateChangedListener(this);

        // set calendar mode as edit-mode
        calendarView.setSelectionMode(SELECTION_MODE_EDIT);

        calendarView.setSelectedDate(new Date());
        this.onDateSelected(calendarView, calendarView.getSelectedDate(), true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    private void onYellowButtonColor(Button button) {
        button.setBackgroundResource(R.drawable.yellow_background);
        button.setTextColor(ContextCompat.getColor(mContext, R.color.JGGBlack));
    }

    private void onPurpleButtonColor(Button button) {
        button.setBackgroundResource(R.drawable.purple_border_background);
        button.setTextColor(ContextCompat.getColor(mContext, R.color.JGGPurple));
    }

    private void onNextButtonEnable() {
        btnNext.setClickable(true);
        btnNext.setTextColor(ContextCompat.getColor(mContext, R.color.JGGWhite));
        btnNext.setBackgroundResource(R.drawable.purple_background);
    }

    private void onShowCalendarDialog() {
        JGGCalendarDialog builder = new JGGCalendarDialog(mContext, Global.AppointmentType.GOCLUB);
        builder.lblCalendarTitle.setText("Pick the date:");
        builder.btnCalendarOk.setText("Done");
        builder.setOnItemClickListener(new JGGCalendarDialog.OnItemClickListener() {
            @Override
            public void onDoneButtonClick(View view, List<CalendarDay> dates) {
                if (view.getId() == R.id.btn_add_time_duplicate_cancel) {
                    alertDialog.dismiss();
                } else if (view.getId() == R.id.btn_add_time_duplicate_ok) {
                    alertDialog.dismiss();
                    if (dates.size() > 0) {
                        Date date = dates.get(0).getDate();
                        setOneTimeDate(date);
                        if (txtTime.getText().length() > 0 && txtDate.getText().length() > 0)
                            onNextButtonEnable();
                    }
                }
            }
        });
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    private void setOneTimeDate(Date date) {
        String year = getAppointmentYear(date);
        String month = getAppointmentMonth(date);
        String day = getAppointmentDay(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd");
        try {
            Date varDate = dateFormat.parse(year + "-" + month + "-" + day);
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            selectedDay = dateFormat.format(varDate);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        txtDate.setText(day + " " + month);
    }

    private void setOneTimePeriod(Date date, Date endDate) {
        startTime = getTimePeriodString(date);
        startOn = date;
        if (endDate != null) {
            endTime = getTimePeriodString(endDate);
            endOn = endDate;
            txtTime.setText(startTime + " - " + endTime);
        } else {
            txtTime.setText(startTime);
        }
    }

    private void onShowAddTimeClickDialog() {
        JGGAddTimeSlotDialog builder = new JGGAddTimeSlotDialog(mContext, Global.AppointmentType.GOCLUB, null, null);
        builder.setOnItemClickListener(new JGGAddTimeSlotDialog.OnItemClickListener() {
            @Override
            public void onDoneButtonClick(View view, Date start, Date end, Integer number) {
                if (view.getId() == R.id.btn_add_time_cancel) {
                    alertDialog.dismiss();
                } else if (view.getId() == R.id.btn_add_time_ok) {
                    alertDialog.dismiss();
                    // Todo - set Period time
                    setOneTimePeriod(start, end);
                    if (txtTime.getText().length() > 0 && txtDate.getText().length() > 0)
                        onNextButtonEnable();
                }
            }
        });
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    private boolean isValidEndTime(Date selectedDate, Date endDate) {
        ArrayList<JGGTimeSlotModel> slotModels = getSelectedDateTimeSlot(selectedDate);
        String yearMonthDay = convertCalendarDate(selectedDate);
        String dateStr = yearMonthDay + "T" + getTimeString(endDate);
        Date newDate = appointmentMonthDate(dateStr);
        if (slotModels.size() > 0) {
            for (int i=0;i<slotModels.size(); i++) {
                JGGTimeSlotModel slotModel = slotModels.get(i);
                Date endSlotDate = appointmentMonthDate(slotModel.getEndOn());
                if (endSlotDate == null) {}
                else
                    if (endSlotDate.after(newDate) || endSlotDate.equals(newDate)) {
                        return false;
                    }
            }

        }
        return true;
    }

    private boolean isValidEndTimeForEditing(Date seletedDate, Date endDate) {
        ArrayList<JGGTimeSlotModel> slotModels = getSelectedDateTimeSlot(seletedDate);
        // remove mEditingTimeSlot object from slotModel - to avoid time overlap of mEditingTimeSlot
        slotModels.remove(mEditingTimeSlot);
        String yearMonthDay = convertCalendarDate(seletedDate);
        String dateStr = yearMonthDay + "T" + getTimeString(endDate);
        Date newDate = appointmentMonthDate(dateStr);
        if (slotModels.size() > 0) {
            for (int i=0;i<slotModels.size(); i++) {
                JGGTimeSlotModel slotModel = slotModels.get(i);
                Date endSlotDate = appointmentMonthDate(slotModel.getEndOn());
                if (endSlotDate == null) {}
                else
                    if (endSlotDate.before(newDate) || endSlotDate.equals(newDate)) {
                        return false;
                    }
            }

        }
        return true;
    }

    private void onAddTimeClick(String startDateStr, String endDateStr) {
        JGGAddTimeSlotDialog builder = new JGGAddTimeSlotDialog(mContext, Global.AppointmentType.GOCLUB, startDateStr, endDateStr);
        builder.onShowPaxLayout(false);
        builder.setOnItemClickListener(new JGGAddTimeSlotDialog.OnItemClickListener() {
            @Override
            public void onDoneButtonClick(View view, Date start, Date end, Integer number) {
                if (view.getId() == R.id.btn_add_time_cancel) {
                    alertDialog.dismiss();
                } else if (view.getId() == R.id.btn_add_time_ok) {

                    Date selectedDate = mSelectedCaledarDay.getDate();
                    String yearMonthDay = convertCalendarDate(selectedDate);
                    // TODO - decide if exist selected time period in mTimeSlots array
                    if (isEditTimeSlot) {
                        // TODO - update corresponding item from mTimeSlots array
                        // get index of mEditingTimeSlot index from mTimeSlots
                        int index = mTimeSlots.indexOf(mEditingTimeSlot);
                        if (isValidEndTimeForEditing(selectedDate, end)) {
                            alertDialog.dismiss();

                            JGGTimeSlotModel timeSlotModel = new JGGTimeSlotModel();
                            timeSlotModel.setStartOn(yearMonthDay + "T" + getTimeString(start));
                            if (end == null) {}
                            else
                                timeSlotModel.setEndOn(yearMonthDay + "T" + getTimeString(end));

                            timeSlotModel.setPeoples(number);   // Multi person


                            mSelectedTimeSlot = timeSlotModel;
                            mTimeSlots.set(index, timeSlotModel);

                            ArrayList<JGGTimeSlotModel> selectedSlotModels = getSelectedDateTimeSlot(selectedDate);
                            updateRecyclerView(selectedSlotModels);
                        } else {
                            Toast.makeText(mContext, "Time could not overlap", Toast.LENGTH_SHORT).show();
                        }
                        isEditTimeSlot = false;
                    } else {
                        if (isValidEndTime(selectedDate, end)) {
                            alertDialog.dismiss();

                            mTimeSlots.clear();

                            JGGTimeSlotModel timeSlotModel = new JGGTimeSlotModel();
                            timeSlotModel.setStartOn(yearMonthDay + "T" + getTimeString(start));
                            if (end == null) {}
                            else
                                timeSlotModel.setEndOn(yearMonthDay + "T" + getTimeString(end));

                            timeSlotModel.setPeoples(number); // Multi person

                            mSelectedTimeSlot = timeSlotModel;

                            mTimeSlots.add(timeSlotModel);

                            ArrayList<JGGTimeSlotModel> selectedSlotModels = getSelectedDateTimeSlot(selectedDate);
                            updateRecyclerView(selectedSlotModels);

                            // To display days containing any time slots
                            if (!calendarDays.contains(mSelectedCaledarDay)) {
                                calendarDays.add(mSelectedCaledarDay);
                            }

                            updateSelectedDaysDecorator();
                        } else {
                            Toast.makeText(mContext, "Time could not overlap", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    private void showDateSelected(CalendarDay calendarDay) {
        Date date = calendarDay.getDate();

        Calendar calendar = Calendar.getInstance();
        int year = Integer.valueOf(getAppointmentYear(date));
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        int month = Integer.valueOf(monthFormat.format(date)) - 1;
        int day = Integer.valueOf(getAppointmentDay(date));
        calendar.set(year, month, day);
        calendarView.setDateSelected(calendar, true);
    }

    private void onShowDuplicateTimeCalendarView() {
        JGGCalendarDialog builder = new JGGCalendarDialog(mContext, Global.AppointmentType.GOCLUB);
        builder.calendar.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);
        builder.setSelectedDate(mSelectedCaledarDay.getDate());
        builder.setOnItemClickListener(new JGGCalendarDialog.OnItemClickListener() {
            @Override
            public void onDoneButtonClick(View view, List<CalendarDay> dates) {
                if (view.getId() == R.id.btn_add_time_duplicate_cancel) {
                    alertDialog.dismiss();
                } else if (view.getId() == R.id.btn_add_time_duplicate_ok) {
                    alertDialog.dismiss();

                    btnDuplicate.setVisibility(View.GONE);

                    calendarView.setSelectionMode(SELECTION_MODE_EDIT);
                    mTimeSlots.clear();
                    if (dates.size() > 0) {
                        for (int i = 0; i < dates.size(); i ++) {
                            CalendarDay calendarDay = dates.get(i);
                            Date duplicateDate = calendarDay.getDate();
                            showDateSelected(calendarDay);

                            // add selected timeSlot for n times
                            JGGTimeSlotModel tmpTimeSlot = new JGGTimeSlotModel();
                            String ymdStart = convertCalendarDate(duplicateDate) + "T" + getTimeString(appointmentMonthDate(mSelectedTimeSlot.getStartOn()));
                            String ymdEnd = null;
                            if (mSelectedTimeSlot.getEndOn() == null) {}
                            else
                                ymdEnd = convertCalendarDate(duplicateDate) + "T" + getTimeString(appointmentMonthDate(mSelectedTimeSlot.getEndOn()));
                            tmpTimeSlot.setStartOn(ymdStart);
                            tmpTimeSlot.setEndOn(ymdEnd);
                            tmpTimeSlot.setPeoples(mSelectedTimeSlot.getPeoples());
                            tmpTimeSlot.setSpecific(mSelectedTimeSlot.getSpecific());

                            mTimeSlots.add(tmpTimeSlot);

                            // To display days containing any time slots
                            if (!calendarDays.contains(calendarDay)) {
                                calendarDays.add(calendarDay);
                            }

                        }
                    }
                }
            }
        });
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    private void setCurrentDateDot() {
        List<CalendarDay> currentDateList = new ArrayList<>();
        currentDateList.add(new CalendarDay(new Date()));
        calendarView.addDecorator(new JGGCalendarDotDecorator(mContext, currentDateList, Global.AppointmentType.GOCLUB));
    }

    private void setSelectedDateCircle() {
        List<CalendarDay> selectedDateList = new ArrayList<>();
        selectedDateList.add(calendarView.getSelectedDate());
        calendarView.addDecorator(new JGGCalendarDecorator(mContext, selectedDateList, Global.AppointmentType.GOCLUB));
    }

    private ArrayList<JGGTimeSlotModel> getSelectedDateTimeSlot(Date selectedDate) {
        ArrayList<JGGTimeSlotModel> slotModels = new ArrayList<>();
        for (int i=0;i<mTimeSlots.size(); i++) {
            JGGTimeSlotModel slotModel = mTimeSlots.get(i);
            if (slotModel.isEqualSlotDate(selectedDate)) {
                slotModels.add(slotModel);
            }
        }
        return slotModels;
    }

    private void onSaveCreatingService() {
        if (bOneTime) {     // One-time Event
            mTimeSlots.clear();
            if (startOn != null) {
                String startTime = selectedDay + "T" + getTimeString(startOn);
                mSelectedTimeSlot.setStartOn(startTime);
            }
            if (endOn != null) {
                String endTime = selectedDay + "T" + getTimeString(endOn);
                mSelectedTimeSlot.setEndOn(endTime);
            }
            mTimeSlots.add(mSelectedTimeSlot);
        }
        mEvent.setOnetime(bOneTime);
        mEvent.setSessions(mTimeSlots);
        JGGAppManager.getInstance().setSelectedEvent(mEvent);
    }

    private void updateRecyclerView(final ArrayList<JGGTimeSlotModel> selectedSlotModels) {
        recyclerView.setVisibility(View.VISIBLE);
        if (selectedSlotModels.size() > 0) {
            btnDuplicate.setVisibility(View.VISIBLE);
        } else {
            btnDuplicate.setVisibility(View.GONE);
        }
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.VERTICAL, false));
        }
        final GcTimeSlotAdapter adapter = new GcTimeSlotAdapter(mContext, selectedSlotModels);
        // TODO - OnPostServiceTimeSlotItemClickListener
        adapter.setOnItemClickListener(new PostServiceTimeSlotAdapter.OnPostServiceTimeSlotItemClickListener() {
            @Override
            public void onPostServiceTimeSlotItemClick(boolean isDelete, int position) {
                JGGTimeSlotModel selectedTimeSlot = selectedSlotModels.get(position);
                if (isDelete) {
                    isEditTimeSlot = false;
                    mTimeSlots.remove(selectedTimeSlot);

                    if (mTimeSlots.size() == 0)
                        btnDuplicate.setVisibility(View.GONE);
                    adapter.notifyDataChanged(mTimeSlots);
                    recyclerView.setAdapter(adapter);
                } else {
                    // TODO - show dialog to confirm delete
                    isEditTimeSlot = true;
                    mEditingTimeSlot = selectedTimeSlot;
                    String startStr = selectedTimeSlot.getStartOn();
                    String endStr = selectedTimeSlot.getEndOn();
                    onAddTimeClick(startStr, endStr);
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void updateSelectedDaysDecorator() {
        for (int i = 0; i < calendarDays.size(); i ++) {
            CalendarDay calendarDay = calendarDays.get(i);
            showDateSelected(calendarDay);
        }

        if (calendarDays.size() > 1) {
            btnDuplicate.setVisibility(View.GONE);
        }
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
            ArrayList<JGGTimeSlotModel> seletedSlotModels = getSelectedDateTimeSlot(date.getDate());
            if (seletedSlotModels.size() > 0) {
                List<CalendarDay> selectedDateList = new ArrayList<>();
                selectedDateList.add(date);
                calendarView.addDecorator(new JGGCalendarDecorator(mContext, selectedDateList, Global.AppointmentType.GOCLUB));
            }

            updateRecyclerView(seletedSlotModels);

        }

        updateSelectedDaysDecorator();
    }

    @OnClick(R.id.btn_one_time_event)
    public void onClickOneTimeEvent() {
        bOneTime = !bOneTime;
        if (bOneTime) {
            this.onYellowButtonColor(btnOneTimeEvent);
            btnRepeatingEvent.setVisibility(View.GONE);
            txtWhen.setVisibility(View.VISIBLE);
            ll_when.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
        } else {
            this.onPurpleButtonColor(btnOneTimeEvent);
            btnRepeatingEvent.setVisibility(View.VISIBLE);
            txtWhen.setVisibility(View.GONE);
            ll_when.setVisibility(View.GONE);
            btnNext.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.btn_repeating_event)
    public void onClickRepeatingEvent() {
        bRepeatingTime = !bRepeatingTime;
        if (bRepeatingTime) {
            btnOneTimeEvent.setVisibility(View.GONE);
            this.onYellowButtonColor(btnRepeatingEvent);
            txtWhen.setVisibility(View.GONE);
            txtTimeLater.setVisibility(View.VISIBLE);
            btnNow.setVisibility(View.VISIBLE);
            btnLater.setVisibility(View.VISIBLE);
        } else {
            btnOneTimeEvent.setVisibility(View.VISIBLE);
            this.onPurpleButtonColor(btnRepeatingEvent);
            btnViewTimeSlot.setVisibility(View.GONE);
            btnNext.setVisibility(View.GONE);
            txtWhen.setVisibility(View.GONE);
            btnNow.setVisibility(View.GONE);
            btnLater.setVisibility(View.GONE);
            txtTimeLater.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.btn_now)
    public void onClickNow() {
        txtKindEvent.setVisibility(View.GONE);
        btnRepeatingEvent.setVisibility(View.GONE);
        txtTimeLater.setVisibility(View.GONE);
        btnNow.setVisibility(View.GONE);
        btnLater.setVisibility(View.GONE);

        ll_calendar_bg.setVisibility(View.VISIBLE);
        ll_schedule_bg.setVisibility(View.VISIBLE);
        ll_set_time.setVisibility(View.VISIBLE);
        if (mTimeSlots.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            btnDuplicate.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.GONE);
            btnDuplicate.setVisibility(View.GONE);
        }

        btnDone.setVisibility(View.VISIBLE);
        btnNext.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_later)
    public void onClickLater() {
        mEvent.setTimeSlotType(Global.TimeSlotSelectionStatus.none);
        mTimeSlots.clear();

        listener.onNextButtonClick();
    }

    @OnClick(R.id.btn_duplicate)
    public void onClickDuplicate() {
        this.onShowDuplicateTimeCalendarView();
    }

    @OnClick(R.id.btn_done)
    public void onClickDone() {
        bNow = true;
        txtKindEvent.setVisibility(View.VISIBLE);
        this.onYellowButtonColor(btnRepeatingEvent);
        btnRepeatingEvent.setVisibility(View.VISIBLE);
        btnDone.setVisibility(View.GONE);
        btnViewTimeSlot.setVisibility(View.VISIBLE);

        btnOneTimeEvent.setVisibility(View.GONE);
        ll_calendar_bg.setVisibility(View.GONE);
        ll_schedule_bg.setVisibility(View.GONE);
        ll_set_time.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        btnDuplicate.setVisibility(View.GONE);

        this.onNextButtonEnable();
        btnNext.setVisibility(View.VISIBLE);

        //mEvent.setTimeSlotType(Global.TimeSlotSelectionStatus.done);
        //onSaveCreatingService();
    }

    @OnClick(R.id.txt_date)
    public void onClickDate() {
        this.onShowCalendarDialog();
    }

    @OnClick(R.id.txt_time)
    public void onClickTime() {
        this.onShowAddTimeClickDialog();
    }

    @OnClick(R.id.btn_next)
    public void onClickNext() {
        onSaveCreatingService();
        listener.onNextButtonClick();
    }

    @OnClick(R.id.btn_view_timeslot)
    public void onClickViewTimeSlot() {
        mEvent.setTimeSlotType(Global.TimeSlotSelectionStatus.now);
        updateRecyclerView(mTimeSlots);
        txtKindEvent.setVisibility(View.GONE);
        btnRepeatingEvent.setVisibility(View.GONE);
        txtTimeLater.setVisibility(View.GONE);
        btnNow.setVisibility(View.GONE);
        btnLater.setVisibility(View.GONE);

        ll_calendar_bg.setVisibility(View.VISIBLE);
        ll_schedule_bg.setVisibility(View.VISIBLE);
        ll_set_time.setVisibility(View.VISIBLE);
        if (mTimeSlots.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            btnDuplicate.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.GONE);
            btnDuplicate.setVisibility(View.GONE);
        }

        btnViewTimeSlot.setVisibility(View.GONE);
        btnDone.setVisibility(View.VISIBLE);
        btnNext.setVisibility(View.GONE);
    }

    @OnClick(R.id.ll_set_time)
    public void onClickAddTimeSlot() {
        Date selectedDate = calendarView.getSelectedDate().getDate();
        if (selectedDate.before(new Date())) {
            Toast.makeText(mContext, getString(R.string.date_alert), Toast.LENGTH_SHORT).show();
            return;
        }
        onAddTimeClick(null, null);
    }

    // TODO : Next Click Listener
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onNextButtonClick();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
