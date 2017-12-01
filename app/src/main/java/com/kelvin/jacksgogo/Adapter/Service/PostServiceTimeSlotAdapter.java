package com.kelvin.jacksgogo.Adapter.Service;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebHistoryItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;


public class PostServiceTimeSlotAdapter extends Fragment implements View.OnClickListener, TextWatcher {

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

        } else if (view.getId() == R.id.btn_post_timeslot_done) {
            onDoneClick();
        } else if (view.getId() == R.id.btn_post_timeslot_view_time) {
            onViewTimeSlot();
        } else if (view.getId() == R.id.btn_time_slot_next) {
            listener.onNextButtonClick();
        } else if (view.getId() == R.id.btn_post_service_multi_people) {
            isOnePerson = false;
            onOnePersonClick();
        }
    }

    private void onViewTimeSlot() {
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
