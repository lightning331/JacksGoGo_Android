package com.kelvin.jacksgogo.Fragments.Search;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.kelvin.jacksgogo.CustomView.Views.JGGAddTimeSlotDialog;
import com.kelvin.jacksgogo.CustomView.Views.JGGCalendarDialog;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppBaseModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGJobModel;
import com.kelvin.jacksgogo.Utils.Models.System.JGGTimeSlotModel;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.view.accessibility.AccessibilityNodeInfo.CollectionInfo.SELECTION_MODE_MULTIPLE;
import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.selectedAppointment;
import static com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentBaseModel.appointmentDateString;
import static com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentBaseModel.appointmentMonthDate;


public class PostServiceTimeSlotFragment extends Fragment implements View.OnClickListener, TextWatcher {

    private Context mContext;
    private OnFragmentInteractionListener mListener;

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
    private PostServiceActivity postServiceActivity;
    private JGGJobModel creatingService;
    private ArrayList<JGGTimeSlotModel> selectedTimeSlots = new ArrayList<>();
    private Integer editingTimeSlot;

    private boolean isTitleEdit;
    private boolean isEditTimeSlot;

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

        postServiceActivity = ((PostServiceActivity)mContext);
        creatingService = selectedAppointment;
        if (postServiceActivity.selectedPeopleType == 2) {
            selectedTimeSlots = postServiceActivity.arrayOnePersonTimeSlots;
        } else if (postServiceActivity.selectedPeopleType == 3) {
            selectedTimeSlots = postServiceActivity.arrayMultiplePeopleTimeSlots;
        }

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
        addTimeLayout =  view.findViewById(R.id.post_add_timeslot_bg);
        lblTimeSlotsTitle =  view.findViewById(R.id.txt_post_timeslot);
        btnTitleEdit =  view.findViewById(R.id.btn_post_timeslot_title);
        btnAddTime =  view.findViewById(R.id.btn_post_timeslot_add);
        lblAddTimeButtonTitle =  view.findViewById(R.id.lbl_post_timeslot_add);
        btnDuplicate =  view.findViewById(R.id.btn_post_timeslot_duplicate);
        btnDone =  view.findViewById(R.id.btn_post_timeslot_done);
        btnViewTime =  view.findViewById(R.id.btn_post_timeslot_view_time);
        btnMultiPeople =  view.findViewById(R.id.btn_post_service_multi_people);
        btnNext =  view.findViewById(R.id.btn_time_slot_next);
        recyclerView = view.findViewById(R.id.post_service_time_slot_recycler_view);

        if (creatingService.getSessions() != null
                && creatingService.getSessions().size() > 0){
            Date date = appointmentMonthDate(creatingService.getSessions().get(0).getStartOn());
            calendarView.setSelectedDate(date);
        } else
            calendarView.setSelectedDate(new Date());
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
        if (postServiceActivity.selectedPeopleType == 2){                       // One Person
            onYellowButtonColor(btnOnePerson);
            btnNoTimeSlots.setVisibility(View.GONE);
            btnMultiPeople.setVisibility(View.GONE);
            lblTimeAvailable.setVisibility(View.GONE);
            if (creatingService.getTimeSlotType() == Global.TimeSlotSelectionStatus.none) {
                btnNoTimeSlots.setVisibility(View.VISIBLE);
                btnOnePerson.setVisibility(View.VISIBLE);
                btnMultiPeople.setVisibility(View.VISIBLE);
                onGreenButtonColor(btnOnePerson);
            } else if (creatingService.getTimeSlotType() == Global.TimeSlotSelectionStatus.progress) {
                lblCreateService.setVisibility(View.VISIBLE);
                lblTimeAvailable.setVisibility(View.GONE);
                lblSetTimeNow.setVisibility(View.VISIBLE);
                btnNow.setVisibility(View.VISIBLE);
                btnLater.setVisibility(View.VISIBLE);
            } else if (creatingService.getTimeSlotType() == Global.TimeSlotSelectionStatus.now) {
                btnOnePerson.setVisibility(View.GONE);
                btnNow.setVisibility(View.GONE);
                btnLater.setVisibility(View.GONE);
                lblSetTimeNow.setVisibility(View.GONE);
                calendarViewLayout.setVisibility(View.VISIBLE);
                addTimeLayout.setVisibility(View.VISIBLE);
                btnDone.setVisibility(View.VISIBLE);
            } else if (creatingService.getTimeSlotType() == Global.TimeSlotSelectionStatus.done) {
                btnViewTime.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.VISIBLE);
            }
        } else if (postServiceActivity.selectedPeopleType == 3) {               // Multi Person
            onYellowButtonColor(btnMultiPeople);
            btnNoTimeSlots.setVisibility(View.GONE);
            btnOnePerson.setVisibility(View.GONE);
            lblTimeAvailable.setVisibility(View.GONE);
            if (creatingService.getTimeSlotType() == Global.TimeSlotSelectionStatus.none) {
                btnNoTimeSlots.setVisibility(View.VISIBLE);
                btnOnePerson.setVisibility(View.VISIBLE);
                btnMultiPeople.setVisibility(View.VISIBLE);
                onGreenButtonColor(btnMultiPeople);
            } else if (creatingService.getTimeSlotType() == Global.TimeSlotSelectionStatus.progress) {
                lblCreateService.setVisibility(View.VISIBLE);
                lblTimeAvailable.setVisibility(View.GONE);
                lblSetTimeNow.setVisibility(View.VISIBLE);
                btnNow.setVisibility(View.VISIBLE);
                btnLater.setVisibility(View.VISIBLE);
            } else if (creatingService.getTimeSlotType() == Global.TimeSlotSelectionStatus.now) {
                btnMultiPeople.setVisibility(View.GONE);
                btnNow.setVisibility(View.GONE);
                btnLater.setVisibility(View.GONE);
                lblSetTimeNow.setVisibility(View.GONE);
                calendarViewLayout.setVisibility(View.VISIBLE);
                lblAddTimeButtonTitle.setText("Add Time Slots & No. Of Pax");
                addTimeLayout.setVisibility(View.VISIBLE);
                btnDone.setVisibility(View.VISIBLE);
            } else if (creatingService.getTimeSlotType() == Global.TimeSlotSelectionStatus.done) {
                btnViewTime.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.VISIBLE);
            }
        }
    }

    private void onAddTimeClick() {
        JGGAddTimeSlotDialog builder = new JGGAddTimeSlotDialog(mContext, JGGAppBaseModel.AppointmentType.SERVICES);
        if (postServiceActivity.selectedPeopleType == 2) // One person
            builder.onShowPaxLayout(false);
        else if (postServiceActivity.selectedPeopleType == 3) // Multi person
            builder.onShowPaxLayout(true);
        builder.setOnItemClickListener(new JGGAddTimeSlotDialog.OnItemClickListener() {
            @Override
            public void onDoneButtonClick(View view, Date start, Date end, Integer number) {
                if (view.getId() == R.id.btn_add_time_cancel) {
                    alertDialog.dismiss();
                } else if (view.getId() == R.id.btn_add_time_ok) {
                    alertDialog.dismiss();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd");
                    String month = "";
                    try {
                        Date varDate = dateFormat.parse(appointmentDateString(calendarView.getSelectedDate().getDate()));
                        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        month = dateFormat.format(varDate);
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                    if (calendarView.getSelectedDate().getDate().before(new Date())) {
                        Toast.makeText(mContext, "Please set Date later than current time.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    JGGTimeSlotModel timeSlotModel = new JGGTimeSlotModel();
                    timeSlotModel.setStartOn(month + "T" + Global.getTimeString(start));
                    timeSlotModel.setEndOn(month + "T" + Global.getTimeString(end));
                    timeSlotModel.setPeoples(number);
                    if (isEditTimeSlot) {
                        selectedTimeSlots.set(editingTimeSlot, timeSlotModel);
                    } else {
                        if (selectedTimeSlots == null)
                            selectedTimeSlots = new ArrayList<>();
                        selectedTimeSlots.add(timeSlotModel);
                    }
                    updateRecyclerView();
                }
            }
        });
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    private void updateRecyclerView() {
        recyclerView.setVisibility(View.VISIBLE);
        btnDuplicate.setVisibility(View.VISIBLE);
        btnDuplicate.setOnClickListener(PostServiceTimeSlotFragment.this);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.VERTICAL, false));
        }
        final PostServiceTimeSlotAdapter adapter = new PostServiceTimeSlotAdapter(mContext, selectedTimeSlots);
        adapter.setOnItemClickListener(new PostServiceTimeSlotAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(boolean isDelete, int position) {
                editingTimeSlot = position;
                if (isDelete) {
                    isEditTimeSlot = false;
                    selectedTimeSlots.remove(position);
                    if (selectedTimeSlots.size() == 0)
                        btnDuplicate.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                } else {
                    isEditTimeSlot = true;
                    onAddTimeClick();
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void onShowDuplicateTimeCalendarView() {
        JGGCalendarDialog builder = new JGGCalendarDialog(mContext, JGGAppBaseModel.AppointmentType.SERVICES);
        builder.calendar.setSelectionMode(SELECTION_MODE_MULTIPLE);
        builder.setOnItemClickListener(new JGGCalendarDialog.OnItemClickListener() {
            @Override
            public void onDoneButtonClick(View view, String month, String day, String year) {
                if (view.getId() == R.id.btn_add_time_duplicate_cancel) {
                    alertDialog.dismiss();
                } else if (view.getId() == R.id.btn_add_time_duplicate_ok) {
                    alertDialog.dismiss();
                }
            }
        });
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    private void onEditTitleClick() {
        isTitleEdit = !isTitleEdit;

        if (isTitleEdit) {
            lblTimeSlotsTitle.setFocusableInTouchMode(true);
            lblTimeSlotsTitle.setBackgroundResource(R.drawable.green_border_background);
            lblTimeSlotsTitle.setGravity(Gravity.LEFT);
            btnTitleEdit.setImageResource(R.mipmap.button_tick_green);
        } else {
            lblTimeSlotsTitle.setFocusable(false);
            lblTimeSlotsTitle.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGWhite));
            lblTimeSlotsTitle.setGravity(Gravity.CENTER);
            btnTitleEdit.setImageResource(R.mipmap.button_edit_green);
        }
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
    public void onClick(View view) {
        if (view.getId() == R.id.btn_post_service_no_time) {
            postServiceActivity.selectedPeopleType = 1;
            onSaveCreatingService();
        }
        else if (view.getId() == R.id.btn_post_service_one_person) {
            postServiceActivity.selectedPeopleType = 2;
            if (creatingService.getTimeSlotType() == Global.TimeSlotSelectionStatus.none)
                creatingService.setTimeSlotType(Global.TimeSlotSelectionStatus.progress);
            else if (creatingService.getTimeSlotType() == Global.TimeSlotSelectionStatus.progress) {
                postServiceActivity.selectedPeopleType = 0;
                creatingService.setTimeSlotType(Global.TimeSlotSelectionStatus.none);
            }
            else if (creatingService.getTimeSlotType() == Global.TimeSlotSelectionStatus.done)
                creatingService.setTimeSlotType(Global.TimeSlotSelectionStatus.progress);
        }
        else if (view.getId() == R.id.btn_post_service_multi_people) {
            postServiceActivity.selectedPeopleType = 3;
            if (creatingService.getTimeSlotType() == Global.TimeSlotSelectionStatus.none)
                creatingService.setTimeSlotType(Global.TimeSlotSelectionStatus.progress);
            else if (creatingService.getTimeSlotType() == Global.TimeSlotSelectionStatus.progress) {
                postServiceActivity.selectedPeopleType = 0;
                creatingService.setTimeSlotType(Global.TimeSlotSelectionStatus.none);
            }
            else if (creatingService.getTimeSlotType() == Global.TimeSlotSelectionStatus.done)
                creatingService.setTimeSlotType(Global.TimeSlotSelectionStatus.progress);
        }
        else if (view.getId() == R.id.btn_post_one_person_now) {
            creatingService.setTimeSlotType(Global.TimeSlotSelectionStatus.now);
        } else if (view.getId() == R.id.btn_post_one_person_later) {
            postServiceActivity.selectedPeopleType = 1;
            onSaveCreatingService();
        }
        else if (view.getId() == R.id.btn_post_timeslot_title) {
            onEditTitleClick();
        } else if (view.getId() == R.id.btn_post_timeslot_add) {
            onAddTimeClick();
        } else if (view.getId() == R.id.btn_post_timeslot_done) {
            creatingService.setTimeSlotType(Global.TimeSlotSelectionStatus.done);
            onSaveCreatingService();
        } else if (view.getId() == R.id.btn_post_timeslot_view_time) {
            creatingService.setTimeSlotType(Global.TimeSlotSelectionStatus.now);
            updateRecyclerView();
        } else if (view.getId() == R.id.btn_time_slots_edit) {
            onAddTimeClick();
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
        if (postServiceActivity.selectedPeopleType == 1) {  // No time slots
            creatingService.setTimeSlotType(Global.TimeSlotSelectionStatus.none);
            selectedTimeSlots.clear();
            listener.onNextButtonClick();
        }
        if (postServiceActivity.selectedPeopleType == 2) {  // One person
            postServiceActivity.selectedPeopleType = 2;
            postServiceActivity.arrayOnePersonTimeSlots = selectedTimeSlots;
        }
        if (postServiceActivity.selectedPeopleType == 3) {  // Multiple person
            postServiceActivity.selectedPeopleType = 3;
            postServiceActivity.arrayMultiplePeopleTimeSlots = selectedTimeSlots;
        }
        creatingService.setSessions(selectedTimeSlots);
        selectedAppointment = creatingService;
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
