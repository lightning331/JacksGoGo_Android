package com.kelvin.jacksgogo.Adapter.Service;

import android.app.AlertDialog;
import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
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

import com.kelvin.jacksgogo.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;

import java.util.List;

import static android.view.accessibility.AccessibilityNodeInfo.CollectionInfo.SELECTION_MODE_MULTIPLE;


public class PostServiceTimeSlotAdapter extends Fragment implements View.OnClickListener, TextWatcher, OnDateSelectedListener {

    private Context mContext;
    private OnFragmentInteractionListener mListener;

    private TextView lblTimeAvailable;
    private TextView btnNoTimeSlots;
    private TextView lblCreateService;
    private TextView btnOnePerson;
    private TextView lblSetTime;
    private TextView btnOnePersonNow;
    private LinearLayout calendarBG;
    private MaterialCalendarView calendarView;
    private LinearLayout addTimeBG;
    private EditText lblTimeSlotsTitle;
    private ImageView btnTitleEdit;
    private LinearLayout timeBG;
    private TextView lblTime;
    private TextView lblPax;
    private ImageView btnTimeEdit;
    private ImageView btnTimeDelete;
    private LinearLayout btnAddTime;
    private TextView lblAddTimeButtonTitle;
    private RelativeLayout btnDuplicate;
    private RelativeLayout btnDone;
    private RelativeLayout btnViewTime;
    private TextView btnOnePersonLater;
    private TextView btnMultiPeople;
    private RelativeLayout btnNext;

    private ImageView btnStartTimeUp;
    private ImageView btnStartTimeDown;
    private ImageView btnStartMinuteUp;
    private ImageView btnStartMinuteDown;
    private ImageView btnStartTimeAM;
    private ImageView btnStartTimePM;
    private EditText txtStartTime;
    private EditText txtStartMinute;
    private ImageView btnEndTimeUp;
    private ImageView btnEndTimeDown;
    private ImageView btnEndMinuteUp;
    private ImageView btnEndMinuteDown;
    private ImageView btnEndTimeAM;
    private ImageView btnEndTimePM;
    private EditText txtEndTime;
    private EditText txtEndMinute;
    private ImageView btnPaxLeft;
    private ImageView btnPaxRight;
    private EditText txtPax;
    private LinearLayout paxBackground;
    private TextView btnCancel;
    private TextView btnOk;

    private MaterialCalendarView duplicateTimeCalendar;
    private TextView btnDuplicateCancel;
    private TextView btnDuplicateOk;

    private AlertDialog alertDialog;

    private String strTimeSlotTitle;
    private boolean isOnePerson = false;
    private boolean isTitleEdit = false;

    public PostServiceTimeSlotAdapter() {
        // Required empty public constructor
    }

    public static PostServiceTimeSlotAdapter newInstance(String param1, String param2) {
        PostServiceTimeSlotAdapter fragment = new PostServiceTimeSlotAdapter();
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
        View view = inflater.inflate(R.layout.post_service_time_slot_fragment, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        lblTimeAvailable = view.findViewById(R.id.lbl_time_slot_available);
        btnNoTimeSlots = view.findViewById(R.id.btn_post_service_no_time);
        lblCreateService = view.findViewById(R.id.lbl_post_timeslot_a_service);
        btnOnePerson =  view.findViewById(R.id.btn_post_service_one_person);
        lblSetTime =  view.findViewById(R.id.lbl_post_service_set_time);
        btnOnePersonNow =  view.findViewById(R.id.btn_post_one_person_now);
        calendarBG =  view.findViewById(R.id.post_calendar_bg);
        calendarView =  view.findViewById(R.id.post_service_calendar);
        addTimeBG =  view.findViewById(R.id.post_add_timeslot_bg);
        lblTimeSlotsTitle =  view.findViewById(R.id.txt_post_timeslot);
        btnTitleEdit =  view.findViewById(R.id.btn_post_timeslot_title);
        timeBG =  view.findViewById(R.id.post_timeslot_time_bg);
        lblTime =  view.findViewById(R.id.lbl_time_slots_time);
        lblPax =  view.findViewById(R.id.lbl_time_slots_pax);
        btnTimeEdit =  view.findViewById(R.id.btn_time_slots_edit);
        btnTimeDelete =  view.findViewById(R.id.btn_time_slots_delete);
        btnAddTime =  view.findViewById(R.id.btn_post_timeslot_add);
        lblAddTimeButtonTitle =  view.findViewById(R.id.lbl_post_timeslot_add);
        btnDuplicate =  view.findViewById(R.id.btn_post_timeslot_duplicate);
        btnDone =  view.findViewById(R.id.btn_post_timeslot_done);
        btnViewTime =  view.findViewById(R.id.btn_post_timeslot_view_time);
        btnOnePersonLater =  view.findViewById(R.id.btn_post_one_person_later);
        btnMultiPeople =  view.findViewById(R.id.btn_post_service_multi_people);
        btnNext =  view.findViewById(R.id.btn_time_slot_next);

        btnNoTimeSlots.setOnClickListener(this);
        btnOnePerson.setOnClickListener(this);
        btnMultiPeople.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_post_service_no_time) {
            listener.onNextButtonClick();
        } else if (view.getId() == R.id.btn_post_service_one_person) {
            isOnePerson = true;
            onOnePersonClick();
        } else if (view.getId() == R.id.btn_post_one_person_now) {
            onNowClick();
        } else if (view.getId() == R.id.btn_post_one_person_later) {
            onLaterClick();
        } else if (view.getId() == R.id.btn_post_timeslot_title) {
            onEditTitleClick();
        } else if (view.getId() == R.id.btn_post_timeslot_add) {
            onAddTimeClick();
        } else if (view.getId() == R.id.btn_post_timeslot_done) {
            onDoneClick();
        } else if (view.getId() == R.id.btn_post_timeslot_view_time) {
            onViewTimeSlotClick();
        } else if (view.getId() == R.id.btn_time_slot_next) {
            listener.onNextButtonClick();
        } else if (view.getId() == R.id.btn_post_service_multi_people) {
            isOnePerson = false;
            onOnePersonClick();
        } else if (view.getId() == R.id.btn_add_start_time_up) {

        } else if (view.getId() == R.id.btn_add_start_time_down) {

        } else if (view.getId() == R.id.btn_add_start_time_minute_up) {

        } else if (view.getId() == R.id.btn_add_start_time_minute_down) {

        } else if (view.getId() == R.id.btn_add_end_time_up) {

        } else if (view.getId() == R.id.btn_add_end_time_down) {

        } else if (view.getId() == R.id.btn_add_end_time_minute_up) {

        } else if (view.getId() == R.id.btn_add_end_time_minute_down) {

        } else if (view.getId() == R.id.btn_add_start_time_am) {
            onAmClick(view);
        } else if (view.getId() == R.id.btn_add_start_time_pm) {
            onPmClick(view);
        } else if (view.getId() == R.id.btn_add_end_time_am) {
            onAmClick(view);
        } else if (view.getId() == R.id.btn_add_end_time_pm) {
            onPmClick(view);
        } else if (view.getId() == R.id.btn_add_time_cancel) {
            alertDialog.dismiss();
        } else if (view.getId() == R.id.btn_add_time_ok) {
            alertDialog.dismiss();
            timeBG.setVisibility(View.VISIBLE);
            btnDuplicate.setVisibility(View.VISIBLE);
            btnDuplicate.setOnClickListener(this);
            btnTimeEdit.setOnClickListener(this);
            btnTimeDelete.setOnClickListener(this);
            if (!isOnePerson) lblPax.setVisibility(View.VISIBLE);
        } else if (view.getId() == R.id.btn_time_slots_edit) {
            onAddTimeClick();
        } else if (view.getId() == R.id.btn_time_slots_delete) {
            timeBG.setVisibility(View.GONE);
            btnDuplicate.setVisibility(View.GONE);
        } else if (view.getId() == R.id.btn_post_timeslot_duplicate) {
            onShowDuplicateTimeCalendarView();
        } else if (view.getId() == R.id.btn_add_time_duplicate_cancel) {
            alertDialog.dismiss();
        } else if (view.getId() == R.id.btn_add_time_duplicate_ok) {
            alertDialog.dismiss();
        }
    }

    private void onAmClick(View view) {
        if (view.getId() == R.id.btn_add_start_time_am) {
            btnStartTimeAM.setBackgroundResource(R.mipmap.button_am_active_green);
            btnStartTimePM.setBackgroundResource(R.mipmap.button_pm_green);
        } else {
            btnEndTimeAM.setBackgroundResource(R.mipmap.button_am_active_green);
            btnEndTimePM.setBackgroundResource(R.mipmap.button_pm_green);
        }
    }

    private void onPmClick(View view) {
        if (view.getId() == R.id.btn_add_start_time_pm) {
            btnStartTimeAM.setBackgroundResource(R.mipmap.button_am_green);
            btnStartTimePM.setBackgroundResource(R.mipmap.button_pm_active_green);
        } else {
            btnEndTimeAM.setBackgroundResource(R.mipmap.button_am_green);
            btnEndTimePM.setBackgroundResource(R.mipmap.button_pm_active_green);
        }
    }

    private void onAddTimeClick() {

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
        LayoutInflater inflater = this.getLayoutInflater();

        View alertView = inflater.inflate(R.layout.post_service_add_time_slot_view, null);
        builder.setView(alertView);
        alertDialog = builder.create();

        onShowAddTimeView(alertView);

        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();

    }

    private void onShowAddTimeView(View view) {
        btnStartTimeUp = (ImageView) view.findViewById(R.id.btn_add_start_time_up);
        btnStartTimeUp.setOnClickListener(this);
        txtStartTime = (EditText) view.findViewById(R.id.txt_add_start_time);
        txtStartTime.addTextChangedListener(this);
        btnStartTimeDown = (ImageView) view.findViewById(R.id.btn_add_start_time_down);
        btnStartTimeDown.setOnClickListener(this);
        btnStartMinuteUp = (ImageView) view.findViewById(R.id.btn_add_start_time_minute_up);
        btnStartMinuteUp.setOnClickListener(this);
        txtStartMinute = (EditText) view.findViewById(R.id.txt_add_start_minute_time);
        txtStartMinute.addTextChangedListener(this);
        btnStartMinuteDown = (ImageView) view.findViewById(R.id.btn_add_start_time_minute_down);
        btnStartMinuteDown.setOnClickListener(this);
        btnStartTimeAM = (ImageView) view.findViewById(R.id.btn_add_start_time_am);
        btnStartTimeAM.setOnClickListener(this);
        btnStartTimePM = (ImageView) view.findViewById(R.id.btn_add_start_time_pm);
        btnStartTimePM.setOnClickListener(this);
        btnEndTimeUp = (ImageView) view.findViewById(R.id.btn_add_end_time_up);
        btnEndTimeUp.setOnClickListener(this);
        txtEndTime = (EditText) view.findViewById(R.id.txt_add_end_time);
        txtEndTime.addTextChangedListener(this);
        btnEndTimeDown = (ImageView) view.findViewById(R.id.btn_add_end_time_down);
        btnEndTimeDown.setOnClickListener(this);
        btnEndMinuteUp = (ImageView) view.findViewById(R.id.btn_add_end_time_minute_up);
        btnEndMinuteUp.setOnClickListener(this);
        txtEndMinute = (EditText) view.findViewById(R.id.txt_add_end_minute_time);
        txtEndMinute.addTextChangedListener(this);
        btnEndMinuteDown = (ImageView) view.findViewById(R.id.btn_add_end_time_minute_down);
        btnEndMinuteDown.setOnClickListener(this);
        btnEndTimeAM = (ImageView) view.findViewById(R.id.btn_add_end_time_am);
        btnEndTimeAM.setOnClickListener(this);
        btnEndTimePM = (ImageView) view.findViewById(R.id.btn_add_end_time_pm);
        btnEndTimePM.setOnClickListener(this);
        btnPaxLeft = (ImageView) view.findViewById(R.id.btn_add_time_pax_left);
        btnPaxLeft.setOnClickListener(this);
        txtPax = (EditText) view.findViewById(R.id.txt_add_time_pax);
        txtPax.addTextChangedListener(this);
        btnPaxRight = (ImageView) view.findViewById(R.id.btn_add_time_pax_right);
        btnPaxRight.setOnClickListener(this);
        btnCancel = (TextView) view.findViewById(R.id.btn_add_time_cancel);
        btnCancel.setOnClickListener(this);
        btnOk = (TextView) view.findViewById(R.id.btn_add_time_ok);
        btnOk.setOnClickListener(this);
        paxBackground = (LinearLayout) view.findViewById(R.id.add_time_pax_background);
        if (!isOnePerson) paxBackground.setVisibility(View.VISIBLE);
    }

    private void onShowDuplicateTimeCalendarView() {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
        LayoutInflater inflater = this.getLayoutInflater();

        View calendarView = inflater.inflate(R.layout.post_service_duplicate_time_view, null);
        builder.setView(calendarView);
        alertDialog = builder.create();

        duplicateTimeCalendar = calendarView.findViewById(R.id.add_time_duplicate_calendar);
        duplicateTimeCalendar.setSelectionMode(SELECTION_MODE_MULTIPLE);
        duplicateTimeCalendar.setOnDateChangedListener(this);
        btnDuplicateCancel = calendarView.findViewById(R.id.btn_add_time_duplicate_cancel);
        btnDuplicateCancel.setOnClickListener(this);
        btnDuplicateOk = calendarView.findViewById(R.id.btn_add_time_duplicate_ok);

        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();

    }

    private void onViewTimeSlotClick() {
        onNowClick();
        btnViewTime.setVisibility(View.GONE);
        btnNext.setVisibility(View.GONE);
    }

    private void onOnePersonClick() {
        if (isOnePerson) {
            btnOnePerson.setClickable(false);
            btnOnePerson.setBackgroundResource(R.drawable.yellow_background);
            btnOnePerson.setTextColor(ContextCompat.getColor(mContext, R.color.JGGBlack));
            btnMultiPeople.setVisibility(View.GONE);
        } else {
            btnMultiPeople.setClickable(false);
            btnMultiPeople.setBackgroundResource(R.drawable.yellow_background);
            btnMultiPeople.setTextColor(ContextCompat.getColor(mContext, R.color.JGGBlack));
            btnOnePerson.setVisibility(View.GONE);
        }
        lblCreateService.setVisibility(View.VISIBLE);
        btnNoTimeSlots.setVisibility(View.GONE);
        lblTimeAvailable.setVisibility(View.GONE);
        lblSetTime.setVisibility(View.VISIBLE);
        btnOnePersonNow.setVisibility(View.VISIBLE);
        btnOnePersonNow.setOnClickListener(this);
        btnOnePersonLater.setVisibility(View.VISIBLE);
        btnOnePersonLater.setOnClickListener(this);
    }

    private void onNowClick() {
        btnOnePerson.setVisibility(View.GONE);
        btnOnePersonNow.setVisibility(View.GONE);
        lblCreateService.setVisibility(View.GONE);
        lblSetTime.setVisibility(View.GONE);
        btnOnePersonLater.setVisibility(View.GONE);
        calendarBG.setVisibility(View.VISIBLE);
        addTimeBG.setVisibility(View.VISIBLE);
        btnDone.setVisibility(View.VISIBLE);

        if (!isOnePerson) {
            lblAddTimeButtonTitle.setText("Add Time Slots & No. Of Pax");
            btnMultiPeople.setVisibility(View.GONE);
        }
        btnTitleEdit.setOnClickListener(this);
        btnAddTime.setOnClickListener(this);
        btnDone.setOnClickListener(this);
    }

    private void onLaterClick() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        if (manager.getBackStackEntryCount() == 0) {
            getActivity().onBackPressed();
        } else {
            manager.popBackStack();
        }
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

    private void onDoneClick() {
        lblCreateService.setVisibility(View.VISIBLE);
        calendarBG.setVisibility(View.GONE);
        addTimeBG.setVisibility(View.GONE);
        btnDone.setVisibility(View.GONE);
        btnViewTime.setVisibility(View.VISIBLE);
        btnNext.setVisibility(View.VISIBLE);
        if (!isOnePerson) {
            btnMultiPeople.setVisibility(View.VISIBLE);
        } else {
            btnOnePerson.setVisibility(View.VISIBLE);
        }
        btnViewTime.setOnClickListener(this);
        btnNext.setOnClickListener(this);
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

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        btnDuplicateOk.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGGreen));
        btnDuplicateOk.setTextColor(ContextCompat.getColor(mContext, R.color.JGGWhite));
        btnDuplicateOk.setOnClickListener(this);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
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
