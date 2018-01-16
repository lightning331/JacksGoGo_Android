package com.kelvin.jacksgogo.Fragments.Jobs;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.text.SimpleDateFormat;

public class PostJobTimeFragment extends Fragment implements View.OnClickListener, TextWatcher, OnDateSelectedListener {

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    private TextView btnOneTime;
    private TextView btnRepeating;
    private TextView lblDone;
    private TextView btnSpecific;
    private TextView btnAnyDay;
    private TextView lblCertainDateTime;
    private LinearLayout dateTimeLayout;
    private LinearLayout btnDate;
    private TextView lblDate;
    private LinearLayout btnTime;
    private TextView lblTime;
    private TextView btnWeekly;
    private TextView btnMonthly;
    private RecyclerView recyclerView;
    private TextView btnAnotherDay;
    private RelativeLayout btnNext;
    private TextView lblNext;

    // Date View
    private MaterialCalendarView calendar;
    private TextView lblCalendarTitle;
    private TextView btnCalendarCancel;
    private TextView btnCalendarOk;

    // Time View
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
    private ImageView btnClose;
    private TextView btnSetEndTime;
    private LinearLayout endTimeOptionalLayout;
    private LinearLayout endTimeLayout;
    private EditText txtEndTime;
    private EditText txtEndMinute;
    private TextView btnCancel;
    private TextView btnOk;

    private int okButtonColor;
    private int cancelButtonColor;
    private int checkImage;
    private int borderBackground;
    private int imgActiveAM;
    private int imgActivePM;
    private int imgInActiveAM;
    private int imgInActivePM;
    private int imgUp;
    private int imgDown;

    private int startHour = 10;
    private int startMinute = 0;
    private boolean startAM = true;
    private int endHour = 11;
    private int endMinute = 0;
    private boolean endAM = true;


    private AlertDialog alertDialog;

    public PostJobTimeFragment() {
        // Required empty public constructor
    }

    public static PostJobTimeFragment newInstance(String param1, String param2) {
        PostJobTimeFragment fragment = new PostJobTimeFragment();
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
        View view = inflater.inflate(R.layout.fragment_post_job_time, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        btnOneTime = view.findViewById(R.id.btn_post_job_one_time);
        btnRepeating = view.findViewById(R.id.btn_post_job_repeating);
        lblDone = view.findViewById(R.id.lbl_post_job_done);
        btnSpecific = view.findViewById(R.id.btn_post_job_specific);
        btnAnyDay = view.findViewById(R.id.btn_post_job_any_day);
        dateTimeLayout = view.findViewById(R.id.post_job_date_layout);
        btnDate = view.findViewById(R.id.btn_post_job_date);
        lblDate = view.findViewById(R.id.lbl_post_job_date);
        btnTime = view.findViewById(R.id.btn_post_job_time);
        lblTime = view.findViewById(R.id.lbl_post_job_time);
        lblCertainDateTime = view.findViewById(R.id.lbl_post_job_certain_date);
        btnWeekly = view.findViewById(R.id.btn_post_job_weekly);
        btnMonthly = view.findViewById(R.id.btn_post_job_monthly);
        btnAnotherDay = view.findViewById(R.id.btn_post_job_add_another_day);
        btnNext = view.findViewById(R.id.btn_post_job_time_next);
        lblNext = view.findViewById(R.id.lbl_post_job_time_next);
        recyclerView = view.findViewById(R.id.post_job_time_day_recycler_view);

        borderBackground = R.drawable.cyan_border_background;
        okButtonColor = ContextCompat.getColor(mContext, R.color.JGGCyan);
        cancelButtonColor = ContextCompat.getColor(mContext, R.color.JGGCyan10Percent);
        imgActiveAM = R.mipmap.button_am_active_cyan;
        imgInActiveAM = R.mipmap.button_am_cyan;
        imgActivePM = R.mipmap.button_pm_active_cyan;
        imgInActivePM = R.mipmap.button_pm_cyan;
        imgUp = R.mipmap.button_showmore_cyan;
        imgDown  = R.mipmap.button_showless_cyan;

        btnOneTime.setOnClickListener(this);
        btnRepeating.setOnClickListener(this);
    }

    private void onNextButtonEnable() {
        if (lblDate.length() > 0 && lblTime.length() > 0) {
            lblNext.setTextColor(ContextCompat.getColor(mContext, R.color.JGGWhite));
            btnNext.setBackgroundResource(R.drawable.cyan_background);
            btnNext.setOnClickListener(this);
        } else {
            onNextButtonDissable();
        }
    }

    private void onNextButtonDissable() {
        lblNext.setTextColor(ContextCompat.getColor(mContext, R.color.JGGGrey2));
        btnNext.setBackgroundResource(R.drawable.grey_background);
        btnNext.setClickable(false);
    }

    private void onButtonClicked(TextView button) {
        button.setBackgroundResource(R.drawable.yellow_background);
        button.setTextColor(ContextCompat.getColor(mContext, R.color.JGGBlack));
        button.setClickable(false);
    }

    private void onShowCalendarView() {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
        LayoutInflater inflater = this.getLayoutInflater();

        View calendarView = inflater.inflate(R.layout.view_post_service_duplicate_time, null);
        builder.setView(calendarView);
        alertDialog = builder.create();

        calendar = calendarView.findViewById(R.id.add_time_duplicate_calendar);
        calendar.setOnDateChangedListener(this);
        lblCalendarTitle = calendarView.findViewById(R.id.lbl_duplicate_time_title);
        lblCalendarTitle.setText("Pick the Date:");
        btnCalendarCancel = calendarView.findViewById(R.id.btn_add_time_duplicate_cancel);
        btnCalendarCancel.setOnClickListener(this);
        btnCalendarOk = calendarView.findViewById(R.id.btn_add_time_duplicate_ok);
        btnCalendarOk.setText("Done");
        btnCalendarCancel.setBackgroundColor(cancelButtonColor);
        btnCalendarCancel.setTextColor(okButtonColor);
        calendar.setArrowColor(okButtonColor);
        calendar.setSelectionColor(okButtonColor);
        calendar.setLeftArrowMask(getResources().getDrawable(R.mipmap.button_previous_cyan));
        calendar.setRightArrowMask(getResources().getDrawable(R.mipmap.button_next_cyan));

        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();

    }

    private void onAddTimeClick() {

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
        LayoutInflater inflater = this.getLayoutInflater();

        View alertView = inflater.inflate(R.layout.view_advance_search_time, null);
        builder.setView(alertView);
        alertDialog = builder.create();

        onShowAddTimeView(alertView);

        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    private void onShowAddTimeView(View view) {
        btnStartTimeUp = (ImageView) view.findViewById(R.id.btn_add_start_time_up);
        txtStartTime = (EditText) view.findViewById(R.id.txt_add_start_time);
        btnStartTimeDown = (ImageView) view.findViewById(R.id.btn_add_start_time_down);
        btnStartMinuteUp = (ImageView) view.findViewById(R.id.btn_add_start_time_minute_up);
        txtStartMinute = (EditText) view.findViewById(R.id.txt_add_start_minute_time);
        btnStartMinuteDown = (ImageView) view.findViewById(R.id.btn_add_start_time_minute_down);
        btnStartTimeAM = (ImageView) view.findViewById(R.id.btn_add_start_time_am);
        btnStartTimePM = (ImageView) view.findViewById(R.id.btn_add_start_time_pm);
        btnEndTimeUp = (ImageView) view.findViewById(R.id.btn_add_end_time_up);
        txtEndTime = (EditText) view.findViewById(R.id.txt_add_end_time);
        btnEndTimeDown = (ImageView) view.findViewById(R.id.btn_add_end_time_down);
        btnEndMinuteUp = (ImageView) view.findViewById(R.id.btn_add_end_time_minute_up);
        txtEndMinute = (EditText) view.findViewById(R.id.txt_add_end_minute_time);
        btnEndMinuteDown = (ImageView) view.findViewById(R.id.btn_add_end_time_minute_down);
        btnEndTimeAM = (ImageView) view.findViewById(R.id.btn_add_end_time_am);
        btnEndTimePM = (ImageView) view.findViewById(R.id.btn_add_end_time_pm);
        btnCancel = (TextView) view.findViewById(R.id.btn_add_time_cancel);
        btnOk = (TextView) view.findViewById(R.id.btn_add_time_ok);
        btnClose = (ImageView) view.findViewById(R.id.btn_advance_search_close);
        btnSetEndTime = (TextView) view.findViewById(R.id.btn_advance_search_end_time);
        endTimeOptionalLayout = (LinearLayout) view.findViewById(R.id.set_end_time_layout);
        endTimeLayout = (LinearLayout) view.findViewById(R.id.advance_search_end_time_layout);

        btnCancel.setBackgroundColor(cancelButtonColor);
        btnCancel.setTextColor(okButtonColor);
        btnOk.setBackgroundColor(okButtonColor);
        txtStartTime.setBackgroundResource(borderBackground);
        txtStartMinute.setBackgroundResource(borderBackground);
        txtEndTime.setBackgroundResource(borderBackground);
        txtEndMinute.setBackgroundResource(borderBackground);
        btnSetEndTime.setBackgroundResource(borderBackground);
        btnSetEndTime.setTextColor(okButtonColor);
        btnStartTimeUp.setImageResource(imgUp);
        btnStartTimeDown.setImageResource(imgDown);
        btnStartMinuteUp.setImageResource(imgUp);
        btnStartMinuteDown.setImageResource(imgDown);
        btnStartTimeAM.setImageResource(imgActiveAM);
        btnStartTimePM.setImageResource(imgInActivePM);
        btnEndTimeUp.setImageResource(imgUp);
        btnEndTimeDown.setImageResource(imgDown);
        btnEndMinuteUp.setImageResource(imgUp);
        btnEndMinuteDown.setImageResource(imgDown);
        btnEndTimeAM.setImageResource(imgActiveAM);
        btnEndTimePM.setImageResource(imgInActivePM);
        btnClose.setImageResource(R.mipmap.button_close_round_cyan);

        btnStartTimeUp.setOnClickListener(this);
        txtStartTime.addTextChangedListener(this);
        btnStartTimeDown.setOnClickListener(this);
        btnStartMinuteUp.setOnClickListener(this);
        btnStartMinuteDown.setOnClickListener(this);
        txtStartMinute.addTextChangedListener(this);
        btnStartTimeAM.setOnClickListener(this);
        btnStartTimePM.setOnClickListener(this);
        btnEndTimeUp.setOnClickListener(this);
        txtEndTime.addTextChangedListener(this);
        btnEndTimeDown.setOnClickListener(this);
        btnEndMinuteUp.setOnClickListener(this);
        txtEndMinute.addTextChangedListener(this);
        btnEndMinuteDown.setOnClickListener(this);
        btnEndTimeAM.setOnClickListener(this);
        btnEndTimePM.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnOk.setOnClickListener(this);
        btnSetEndTime.setOnClickListener(this);
    }

    private void onAmClick(ImageView view) {
        if (view == btnStartTimeAM) {
            btnStartTimeAM.setImageResource(imgActiveAM);
            btnStartTimePM.setImageResource(imgInActivePM);
        } else if (view == btnEndTimeAM) {
            btnEndTimeAM.setImageResource(imgActiveAM);
            btnEndTimePM.setImageResource(imgInActivePM);
        } else if (view == btnStartTimePM) {
            btnStartTimeAM.setImageResource(imgInActiveAM);
            btnStartTimePM.setImageResource(imgActivePM);
        } else if (view == btnEndTimePM) {
            btnEndTimeAM.setImageResource(imgInActiveAM);
            btnEndTimePM.setImageResource(imgActivePM);
        }
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
        if (view.getId() == R.id.btn_post_job_one_time) {
            onButtonClicked(btnOneTime);
            btnRepeating.setVisibility(View.GONE);
            lblDone.setVisibility(View.VISIBLE);
            btnSpecific.setVisibility(View.VISIBLE);
            btnAnyDay.setVisibility(View.VISIBLE);
            btnSpecific.setOnClickListener(this);
            btnAnyDay.setOnClickListener(this);
        } else if (view.getId() == R.id.btn_post_job_repeating) {
            onButtonClicked(btnRepeating);
            btnOneTime.setVisibility(View.GONE);
            lblDone.setVisibility(View.VISIBLE);
            btnWeekly.setVisibility(View.VISIBLE);
            btnMonthly.setVisibility(View.VISIBLE);
            btnWeekly.setOnClickListener(this);
            btnMonthly.setOnClickListener(this);
        } else if (view.getId() == R.id.btn_post_job_specific) {
            onButtonClicked(btnSpecific);
            btnAnyDay.setVisibility(View.GONE);
            dateTimeLayout.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
            btnDate.setOnClickListener(this);
            btnTime.setOnClickListener(this);
        } else if (view.getId() == R.id.btn_post_job_any_day) {
            onButtonClicked(btnAnyDay);
            btnSpecific.setVisibility(View.GONE);
            lblCertainDateTime.setVisibility(View.VISIBLE);
            dateTimeLayout.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
            btnDate.setOnClickListener(this);
            btnTime.setOnClickListener(this);
        } else if (view.getId() == R.id.btn_post_job_time) {
            onAddTimeClick();
        } else if (view.getId() == R.id.btn_post_job_weekly) {
            onButtonClicked(btnWeekly);
            recyclerView.setVisibility(View.VISIBLE);
            btnMonthly.setVisibility(View.GONE);
            btnNext.setVisibility(View.VISIBLE);
        } else if (view.getId() == R.id.btn_post_job_monthly) {
            onButtonClicked(btnMonthly);
            recyclerView.setVisibility(View.VISIBLE);
            btnWeekly.setVisibility(View.GONE);
            btnNext.setVisibility(View.VISIBLE);
        } else if (view.getId() == R.id.btn_post_job_add_another_day) {

        } else if (view.getId() == R.id.btn_post_job_time_next) {

        }

        // Time Alert
        else if (view.getId() == R.id.btn_advance_search_end_time) {
            endTimeOptionalLayout.setVisibility(View.GONE);
            endTimeLayout.setVisibility(View.VISIBLE);
            btnClose.setOnClickListener(this);
        } else if (view.getId() == R.id.btn_advance_search_close) {
            endTimeOptionalLayout.setVisibility(View.VISIBLE);
            endTimeLayout.setVisibility(View.GONE);
        }

        else if (view.getId() == R.id.btn_add_start_time_up) {
            onPressedChangeTime(view);
        } else if (view.getId() == R.id.btn_add_start_time_down) {
            onPressedChangeTime(view);
        } else if (view.getId() == R.id.btn_add_start_time_minute_up) {
            onPressedChangeTime(view);
        } else if (view.getId() == R.id.btn_add_start_time_minute_down) {
            onPressedChangeTime(view);
        } else if (view.getId() == R.id.btn_add_start_time_am) {
            onPressedChangeTime(view);
        } else if (view.getId() == R.id.btn_add_start_time_pm) {
            onPressedChangeTime(view);
        }

        else if (view.getId() == R.id.btn_add_end_time_up) {
            onPressedChangeTime(view);
        } else if (view.getId() == R.id.btn_add_end_time_down) {
            onPressedChangeTime(view);
        } else if (view.getId() == R.id.btn_add_end_time_minute_up) {
            onPressedChangeTime(view);
        } else if (view.getId() == R.id.btn_add_end_time_minute_down) {
            onPressedChangeTime(view);
        } else if (view.getId() == R.id.btn_add_end_time_am) {
            onPressedChangeTime(view);
        } else if (view.getId() == R.id.btn_add_end_time_pm) {
            onPressedChangeTime(view);
        }

        else if (view.getId() == R.id.btn_add_time_cancel) {
            alertDialog.dismiss();
        } else if (view.getId() == R.id.btn_add_time_ok) {
            onNextButtonEnable();
            alertDialog.dismiss();
        }

        // Calendar Alert
        else if (view.getId() == R.id.btn_post_job_date) {
            onShowCalendarView();
        } else if (view.getId() == R.id.btn_add_time_duplicate_cancel) {
            alertDialog.dismiss();
        } else if (view.getId() == R.id.btn_add_time_duplicate_ok) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd");
            lblDate.setText(dateFormat.format(calendar.getSelectedDate().getDate()));
            onNextButtonEnable();
            alertDialog.dismiss();
        }
    }

    private void onPressedChangeTime(View view) {
        if (view.getId() == R.id.btn_add_start_time_up) {
            startHour = startHour + 1;
        } else if (view.getId() == R.id.btn_add_start_time_down) {
            startHour = startHour - 1;
        } else if (view.getId() == R.id.btn_add_start_time_minute_up) {
            startMinute = startMinute + 15;
        } else if (view.getId() == R.id.btn_add_start_time_minute_down) {
            startMinute = startMinute - 15;
        } else if (view.getId() == R.id.btn_add_start_time_am) {
            onAmClick(btnStartTimeAM);
        } else if (view.getId() == R.id.btn_add_start_time_pm) {
            onAmClick(btnStartTimePM);
        }

        else if (view.getId() == R.id.btn_add_end_time_up) {
            endHour = endHour + 1;
        } else if (view.getId() == R.id.btn_add_end_time_down) {
            endHour = endHour - 1;
        } else if (view.getId() == R.id.btn_add_end_time_minute_up) {
            endMinute = endMinute + 15;
        } else if (view.getId() == R.id.btn_add_end_time_minute_down) {
            endMinute = endMinute - 15;
        } else if (view.getId() == R.id.btn_add_end_time_am) {
            onAmClick(btnEndTimeAM);
        } else if (view.getId() == R.id.btn_add_end_time_pm) {
            onAmClick(btnEndTimePM);
        }

        if (startHour < 1) startHour = 12;
        else if (startHour > 12) startHour = 1;
        if (startMinute < 0) startMinute = 45;
        else if (startMinute > 45) startMinute = 0;
        txtStartTime.setText("" + startHour);
        txtStartMinute.setText("" + startMinute);

        if (endHour < 1) endHour = 12;
        else if (endHour > 12) endHour = 1;
        if (endMinute < 0) endMinute = 45;
        else if (endMinute > 45) endMinute = 0;
        txtEndTime.setText("" + endHour);
        txtEndMinute.setText("" + endMinute);
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

        btnCalendarOk.setBackgroundColor(okButtonColor);
        btnCalendarOk.setTextColor(ContextCompat.getColor(mContext, R.color.JGGWhite));
        btnCalendarOk.setOnClickListener(this);
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

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onNextButtonClick();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
