package com.kelvin.jacksgogo.Fragments.Search;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.Activities.Search.PostServiceActivity;
import com.kelvin.jacksgogo.Adapter.Services.PostServiceTimeSlotAdapter;
import com.kelvin.jacksgogo.CustomView.JGGCalendarDecorator;
import com.kelvin.jacksgogo.CustomView.JGGCalendarDotDecorator;
import com.kelvin.jacksgogo.CustomView.Views.JGGAddTimeSlotDialog;
import com.kelvin.jacksgogo.CustomView.Views.JGGCalendarDialog;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.kelvin.jacksgogo.Utils.Global.TimeSlotSelectionStatus;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.System.JGGTimeSlotModel;
import com.prolificinteractive.jggcalendarview.CalendarDay;
import com.prolificinteractive.jggcalendarview.MaterialCalendarView;
import com.prolificinteractive.jggcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.kelvin.jacksgogo.Utils.JGGTimeManager.appointmentMonthDate;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.convertCalendarDate;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getAppointmentDay;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getAppointmentYear;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getTimeString;
import static com.prolificinteractive.jggcalendarview.MaterialCalendarView.SELECTION_MODE_EDIT;


public class PostServiceTimeSlotFragment extends Fragment implements
        View.OnClickListener,
        TextWatcher, OnDateSelectedListener {

    private OnFragmentInteractionListener mListener;
    private Context mContext;
    private PostServiceActivity mActivity;

    private TextView lblTimeAvailable;
    private TextView btnNoTimeSlots;
    private TextView lblCreateService;
    private TextView btnOnePerson;
    private TextView lblSetTimeNow;
    private TextView btnNow;
    private TextView btnLater;
    private LinearLayout calendarViewLayout;
    private MaterialCalendarView calendarView;
    private LinearLayout addTimeLayout;
    private LinearLayout editTimeLayout;
    private EditText lblTimeSlotsTitle;
    private ImageView btnTitleEdit;
    private LinearLayout btnAddTime;
    private TextView lblAddTimeButtonTitle;
    private RelativeLayout btnDuplicate;
    private RelativeLayout btnDone;
    private RelativeLayout btnViewTime;
    private TextView btnMultiPeople;
    private RelativeLayout btnNext;
    private RecyclerView recyclerView;
    private AlertDialog alertDialog;

    private JGGAppointmentModel mService;
    private JGGTimeSlotModel mSelectedTimeSlot; // use this variable when duplicate
    private Integer peopleType;
    private ArrayList<JGGTimeSlotModel> mTimeSlots = new ArrayList<>();
    private ArrayList<CalendarDay> calendarDays = new ArrayList<>();
    private LinearLayout.LayoutParams params;

    private boolean isTitleEdit;
    private boolean isEditTimeSlot;
    private JGGTimeSlotModel mEditingTimeSlot;
    private CalendarDay mSelectedCaledarDay;

    public PostServiceTimeSlotFragment() {
        // Required empty public constructor
    }

    public static PostServiceTimeSlotFragment newInstance(String param1, String param2) {
        PostServiceTimeSlotFragment fragment = new PostServiceTimeSlotFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_service_time_slot, container, false);

        initView(view);
        updateView();

        return view;
    }

    private void initView(View view) {
        lblTimeAvailable = view.findViewById(R.id.lbl_time_slot_available);
        btnNoTimeSlots = view.findViewById(R.id.btn_post_service_no_time);
        lblCreateService = view.findViewById(R.id.lbl_post_timeslot_a_service);
        btnOnePerson =  view.findViewById(R.id.btn_post_service_one_person);
        lblSetTimeNow =  view.findViewById(R.id.lbl_post_service_set_time);
        btnNow =  view.findViewById(R.id.btn_post_one_person_now);
        btnLater =  view.findViewById(R.id.btn_post_one_person_later);
        calendarViewLayout =  view.findViewById(R.id.post_calendar_bg);
        calendarView =  view.findViewById(R.id.post_service_calendar);
        addTimeLayout =  view.findViewById(R.id.post_add_time_slot_bg);
        editTimeLayout =  view.findViewById(R.id.edit_time_slots);
        lblTimeSlotsTitle =  view.findViewById(R.id.txt_post_time_slot);
        btnTitleEdit =  view.findViewById(R.id.btn_post_time_slot_title);
        btnAddTime =  view.findViewById(R.id.btn_post_timeslot_add);
        lblAddTimeButtonTitle =  view.findViewById(R.id.lbl_post_timeslot_add);
        btnDuplicate =  view.findViewById(R.id.btn_post_timeslot_duplicate);
        btnDone =  view.findViewById(R.id.btn_post_timeslot_done);
        btnViewTime =  view.findViewById(R.id.btn_post_timeslot_view_time);
        btnMultiPeople =  view.findViewById(R.id.btn_post_service_multi_people);
        btnNext =  view.findViewById(R.id.btn_time_slot_next);
        recyclerView = view.findViewById(R.id.post_service_time_slot_recycler_view);

        btnNoTimeSlots.setOnClickListener(this);
        btnOnePerson.setOnClickListener(this);
        btnMultiPeople.setOnClickListener(this);
        btnNow.setOnClickListener(this);
        btnLater.setOnClickListener(this);
        btnTitleEdit.setOnClickListener(this);
        btnAddTime.setOnClickListener(this);
        btnDuplicate.setOnClickListener(this);
        btnDone.setOnClickListener(this);
        btnViewTime.setOnClickListener(this);
        btnNext.setOnClickListener(this);

        mService = JGGAppManager.getSelectedAppointment();
        if (mService != null
                && mService.getSessions() != null
                && mService.getSessions().size() > 0) {
            mTimeSlots = mService.getSessions();
            mSelectedTimeSlot = mService.getSessions().get(0);
            peopleType = mSelectedTimeSlot.getPeoples();

            Date date = appointmentMonthDate(mSelectedTimeSlot.getStartOn());
            calendarView.setSelectedDate(date);
            updateRecyclerView(mTimeSlots);
        } else {
            calendarView.setSelectedDate(new Date());
        }

        calendarView.setOnDateChangedListener(this);

        // set calendar mode as edit-mode
        calendarView.setSelectionMode(SELECTION_MODE_EDIT);

        calendarView.setSelectedDate(new Date());
        this.onDateSelected(calendarView, calendarView.getSelectedDate(), true);

    }

    private void updateView() {

        btnNoTimeSlots.setVisibility(View.VISIBLE);
        btnOnePerson.setVisibility(View.VISIBLE);
        btnMultiPeople.setVisibility(View.VISIBLE);
        lblTimeAvailable.setVisibility(View.VISIBLE);
        onGreenButtonColor(btnNoTimeSlots);
        onGreenButtonColor(btnOnePerson);
        onGreenButtonColor(btnMultiPeople);
        lblSetTimeNow.setVisibility(View.GONE);
        lblCreateService.setVisibility(View.GONE);
        btnNow.setVisibility(View.GONE);
        btnLater.setVisibility(View.GONE);
        calendarViewLayout.setVisibility(View.GONE);
        addTimeLayout.setVisibility(View.GONE);
        btnDuplicate.setVisibility(View.GONE);
        btnDone.setVisibility(View.GONE);
        btnViewTime.setVisibility(View.GONE);
        btnNext.setVisibility(View.GONE);

        if (peopleType == null)
            return;
        else if (peopleType == 1){                       // TODO - One person at a time
            onYellowButtonColor(btnOnePerson);
            btnNoTimeSlots.setVisibility(View.GONE);
            btnMultiPeople.setVisibility(View.GONE);
            lblTimeAvailable.setVisibility(View.GONE);
            if (mService.getTimeSlotType() == TimeSlotSelectionStatus.none) {
                btnNoTimeSlots.setVisibility(View.VISIBLE);
                btnOnePerson.setVisibility(View.VISIBLE);
                btnMultiPeople.setVisibility(View.VISIBLE);
                onGreenButtonColor(btnOnePerson);
            } else if (mService.getTimeSlotType() == TimeSlotSelectionStatus.progress) {
                lblCreateService.setVisibility(View.VISIBLE);
                lblTimeAvailable.setVisibility(View.GONE);
                lblSetTimeNow.setVisibility(View.VISIBLE);
                btnNow.setVisibility(View.VISIBLE);
                btnLater.setVisibility(View.VISIBLE);
            } else if (mService.getTimeSlotType() == TimeSlotSelectionStatus.now) {
                btnOnePerson.setVisibility(View.GONE);
                btnNow.setVisibility(View.GONE);
                btnLater.setVisibility(View.GONE);
                lblSetTimeNow.setVisibility(View.GONE);
                calendarViewLayout.setVisibility(View.VISIBLE);
                addTimeLayout.setVisibility(View.VISIBLE);
                btnDone.setVisibility(View.VISIBLE);
//                if (mTimeSlots.size() > 0)
//                    btnDuplicate.setVisibility(View.VISIBLE);
            } else if (mService.getTimeSlotType() == TimeSlotSelectionStatus.done) {
                btnViewTime.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.VISIBLE);
            }
        } else if (peopleType > 1) {               // TODO - Multi people at a time
            onYellowButtonColor(btnMultiPeople);
            btnNoTimeSlots.setVisibility(View.GONE);
            btnOnePerson.setVisibility(View.GONE);
            lblTimeAvailable.setVisibility(View.GONE);
            if (mService.getTimeSlotType() == TimeSlotSelectionStatus.none) {
                btnNoTimeSlots.setVisibility(View.VISIBLE);
                btnOnePerson.setVisibility(View.VISIBLE);
                btnMultiPeople.setVisibility(View.VISIBLE);
                onGreenButtonColor(btnMultiPeople);
            } else if (mService.getTimeSlotType() == TimeSlotSelectionStatus.progress) {
                lblCreateService.setVisibility(View.VISIBLE);
                lblTimeAvailable.setVisibility(View.GONE);
                lblSetTimeNow.setVisibility(View.VISIBLE);
                btnNow.setVisibility(View.VISIBLE);
                btnLater.setVisibility(View.VISIBLE);
            } else if (mService.getTimeSlotType() == TimeSlotSelectionStatus.now) {
                btnMultiPeople.setVisibility(View.GONE);
                btnNow.setVisibility(View.GONE);
                btnLater.setVisibility(View.GONE);
                lblSetTimeNow.setVisibility(View.GONE);
                calendarViewLayout.setVisibility(View.VISIBLE);
                lblAddTimeButtonTitle.setText("Add Time Slots & No. Of Pax");
                addTimeLayout.setVisibility(View.VISIBLE);
                btnDone.setVisibility(View.VISIBLE);
            } else if (mService.getTimeSlotType() == TimeSlotSelectionStatus.done) {
                btnViewTime.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.VISIBLE);
            }
        }
    }

    private void updateRecyclerView(final ArrayList<JGGTimeSlotModel> seletedSlotModels) {
        recyclerView.setVisibility(View.VISIBLE);
        if (seletedSlotModels.size() > 0) {
            btnDuplicate.setVisibility(View.VISIBLE);
        } else {
            btnDuplicate.setVisibility(View.GONE);
        }
        btnDuplicate.setOnClickListener(PostServiceTimeSlotFragment.this);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.VERTICAL, false));
        }
        final PostServiceTimeSlotAdapter adapter = new PostServiceTimeSlotAdapter(mContext, seletedSlotModels);
        // TODO - OnPostServiceTimeSlotItemClickListener
        adapter.setOnItemClickListener(new PostServiceTimeSlotAdapter.OnPostServiceTimeSlotItemClickListener() {
            @Override
            public void onPostServiceTimeSlotItemClick(boolean isDelete, int position) {
                JGGTimeSlotModel selectedTimeSlot = seletedSlotModels.get(position);
                if (isDelete) {
                    isEditTimeSlot = false;
                    mTimeSlots.remove(selectedTimeSlot);

                    if (mTimeSlots.size() == 0)
                        btnDuplicate.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
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

    private boolean isValidEndTime(Date selectedDate, Date endDate) {
        ArrayList<JGGTimeSlotModel> slotModels = getSeletedDateTimeSlot(selectedDate);
        String yearMonthDay = convertCalendarDate(selectedDate);
        String dateStr = yearMonthDay + "T" + getTimeString(endDate);
        Date newDate = appointmentMonthDate(dateStr);
        if (slotModels.size() > 0) {
            for (int i=0;i<slotModels.size(); i++) {
                JGGTimeSlotModel slotModel = slotModels.get(i);
                Date endSlotDate = appointmentMonthDate(slotModel.getEndOn());
                if (endSlotDate.after(newDate) || endSlotDate.equals(newDate)) {
                    return false;
                }
            }

        }
        return true;
    }

    private boolean isVaildEndTimeForEditing(Date seletedDate, Date endDate) {
        ArrayList<JGGTimeSlotModel> slotModels = getSeletedDateTimeSlot(seletedDate);
        // remove mEditingTimeSlot object from slotModel - to avoid time overlap of mEditingTimeSlot
        slotModels.remove(mEditingTimeSlot);
        String yearMonthDay = convertCalendarDate(seletedDate);
        String dateStr = yearMonthDay + "T" + getTimeString(endDate);
        Date newDate = appointmentMonthDate(dateStr);
        if (slotModels.size() > 0) {
            for (int i=0;i<slotModels.size(); i++) {
                JGGTimeSlotModel slotModel = slotModels.get(i);
                Date endSlotDate = appointmentMonthDate(slotModel.getEndOn());
                if (endSlotDate.before(newDate) || endSlotDate.equals(newDate)) {
                    return false;
                }
            }

        }
        return true;
    }

    private void onAddTimeClick(String startDateStr, String endDateStr) {
        JGGAddTimeSlotDialog builder = new JGGAddTimeSlotDialog(mContext, AppointmentType.SERVICES, startDateStr, endDateStr);
        if (peopleType == 1) // One person
            builder.onShowPaxLayout(false);
        else if (peopleType > 1) // Multi person
            builder.onShowPaxLayout(true);
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
                        if (isVaildEndTimeForEditing(selectedDate, end)) {
                            alertDialog.dismiss();

                            JGGTimeSlotModel timeSlotModel = new JGGTimeSlotModel();
                            timeSlotModel.setStartOn(yearMonthDay + "T" + getTimeString(start));
                            timeSlotModel.setEndOn(yearMonthDay + "T" + getTimeString(end));
                            if (peopleType == 1) {       // One person
                                timeSlotModel.setPeoples(1);
                            } else if (peopleType > 1) {   // Multi person
                                timeSlotModel.setPeoples(number);
                            }

                            mSelectedTimeSlot = timeSlotModel;
                            mTimeSlots.set(index, timeSlotModel);

                            ArrayList<JGGTimeSlotModel> seletedSlotModels = getSeletedDateTimeSlot(selectedDate);
                            updateRecyclerView(seletedSlotModels);
                        } else {
                            Toast.makeText(mContext, "Time could not overlap", Toast.LENGTH_SHORT).show();
                        }
                        isEditTimeSlot = false;
                    } else {
                        if (isValidEndTime(selectedDate, end)) {
                            alertDialog.dismiss();
                            JGGTimeSlotModel timeSlotModel = new JGGTimeSlotModel();
                            timeSlotModel.setStartOn(yearMonthDay + "T" + getTimeString(start));
                            timeSlotModel.setEndOn(yearMonthDay + "T" + getTimeString(end));
                            if (peopleType == 1) {       // One person
                                timeSlotModel.setPeoples(1);
                            } else if (peopleType > 1) {   // Multi person
                                timeSlotModel.setPeoples(number);
                            }

                            mSelectedTimeSlot = timeSlotModel;

                            mTimeSlots.add(timeSlotModel);

                            ArrayList<JGGTimeSlotModel> seletedSlotModels = getSeletedDateTimeSlot(selectedDate);
                            updateRecyclerView(seletedSlotModels);

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

    private void onShowDuplicateTimeCalendarView() {
        JGGCalendarDialog builder = new JGGCalendarDialog(mContext, AppointmentType.SERVICES);
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
                            String ymdEnd = convertCalendarDate(duplicateDate) + "T" + getTimeString(appointmentMonthDate(mSelectedTimeSlot.getEndOn()));
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
            ArrayList<JGGTimeSlotModel> seletedSlotModels = getSeletedDateTimeSlot(date.getDate());
            if (seletedSlotModels.size() > 0) {
                List<CalendarDay> selectedDateList = new ArrayList<>();
                selectedDateList.add(date);
//                calendarView.removeDecorators();
                calendarView.addDecorator(new JGGCalendarDecorator(mContext, selectedDateList, AppointmentType.SERVICES));
            }

            updateRecyclerView(seletedSlotModels);

        }

        updateSelectedDaysDecorator();
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_post_service_no_time) {
            peopleType = 0;
            onSaveCreatingService();
        }
        else if (view.getId() == R.id.btn_post_service_one_person) {
            peopleType = 1;
            mTimeSlots.clear();
            if (mService.getTimeSlotType() == TimeSlotSelectionStatus.none)
                mService.setTimeSlotType(TimeSlotSelectionStatus.progress);
            else if (mService.getTimeSlotType() == TimeSlotSelectionStatus.progress) {
                peopleType = 0;
                mService.setTimeSlotType(TimeSlotSelectionStatus.none);
            }
            else if (mService.getTimeSlotType() == TimeSlotSelectionStatus.done)
                mService.setTimeSlotType(TimeSlotSelectionStatus.progress);
        }
        else if (view.getId() == R.id.btn_post_service_multi_people) {
            peopleType = 2;
            mTimeSlots.clear();
            recyclerView.setVisibility(View.GONE);
            if (mService.getTimeSlotType() == TimeSlotSelectionStatus.none)
                mService.setTimeSlotType(TimeSlotSelectionStatus.progress);
            else if (mService.getTimeSlotType() == TimeSlotSelectionStatus.progress) {
                peopleType = 0;
                mService.setTimeSlotType(TimeSlotSelectionStatus.none);
            }
            else if (mService.getTimeSlotType() == TimeSlotSelectionStatus.done)
                mService.setTimeSlotType(TimeSlotSelectionStatus.progress);
        }
        else if (view.getId() == R.id.btn_post_one_person_now) {
            mService.setTimeSlotType(TimeSlotSelectionStatus.now);
        } else if (view.getId() == R.id.btn_post_one_person_later) {
            peopleType = 0;
            onSaveCreatingService();
        }
        else if (view.getId() == R.id.btn_post_time_slot_title) {
            onEditTitleClick();
        } else if (view.getId() == R.id.btn_post_timeslot_add) {
            Date selectedDate = calendarView.getSelectedDate().getDate();
            if (selectedDate.before(new Date())) {
                Toast.makeText(mContext, getString(R.string.date_alert), Toast.LENGTH_SHORT).show();
                return;
            }
            onAddTimeClick(null, null);
        } else if (view.getId() == R.id.btn_post_timeslot_done) {
            mService.setTimeSlotType(TimeSlotSelectionStatus.done);
            onSaveCreatingService();
        } else if (view.getId() == R.id.btn_post_timeslot_view_time) {
            mService.setTimeSlotType(TimeSlotSelectionStatus.now);
            updateRecyclerView(mTimeSlots);
        } else if (view.getId() == R.id.btn_time_slots_delete) {
            recyclerView.setVisibility(View.GONE);
            btnDuplicate.setVisibility(View.GONE);
        } else if (view.getId() == R.id.btn_post_timeslot_duplicate) {
            onShowDuplicateTimeCalendarView();
        } else if (view.getId() == R.id.btn_add_time_duplicate_cancel) {
            alertDialog.dismiss();
        } else if (view.getId() == R.id.btn_add_time_duplicate_ok) {
            alertDialog.dismiss();
        } else if (view.getId() == R.id.btn_time_slot_next) {
            listener.onNextButtonClick();
        }
        updateView();
    }

    private void onSaveCreatingService() {
        if (peopleType == 0) {  // No time slots
            mService.setTimeSlotType(TimeSlotSelectionStatus.none);
            mTimeSlots.clear();
            listener.onNextButtonClick();
        }
        mService.setSessions(mTimeSlots);
        JGGAppManager.setSelectedAppointment(mService);
    }

    private void onEditTitleClick() {
        isTitleEdit = !isTitleEdit;

        if (isTitleEdit) {
            editTimeLayout.setBackgroundResource(R.drawable.green_border_background);
            lblTimeSlotsTitle.setGravity(Gravity.LEFT);
            btnTitleEdit.setImageResource(R.mipmap.button_tick_green);
            params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            params.setMargins(32, 0, 0, 0);
        } else {
            editTimeLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGWhite));
            lblTimeSlotsTitle.setGravity(Gravity.CENTER);
            btnTitleEdit.setImageResource(R.mipmap.button_edit_green);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0);
            //params.setMargins(0, 0, 8, 0);
        }
        lblTimeSlotsTitle.setLayoutParams(params);
    }

    private void setCurrentDateDot() {
        List<CalendarDay> currentDateList = new ArrayList<>();
        currentDateList.add(new CalendarDay(new Date()));
        calendarView.addDecorator(new JGGCalendarDotDecorator(mContext, currentDateList, AppointmentType.SERVICES));
    }

    private void setSelectedDateCircle() {
        List<CalendarDay> selectedDateList = new ArrayList<>();
        selectedDateList.add(calendarView.getSelectedDate());
        calendarView.addDecorator(new JGGCalendarDecorator(mContext, selectedDateList, AppointmentType.SERVICES));
    }

    private void onYellowButtonColor(TextView button) {
        button.setBackgroundResource(R.drawable.yellow_background);
        button.setTextColor(ContextCompat.getColor(mContext, R.color.JGGBlack));
    }

    private void onGreenButtonColor(TextView button) {
        button.setBackgroundResource(R.drawable.green_border_background);
        button.setTextColor(ContextCompat.getColor(mContext, R.color.JGGGreen));
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mActivity = ((PostServiceActivity) mContext);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onNextButtonClick();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
