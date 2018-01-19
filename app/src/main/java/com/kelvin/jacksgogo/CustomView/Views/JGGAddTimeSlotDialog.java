package com.kelvin.jacksgogo.CustomView.Views;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppBaseModel;

/**
 * Created by PUMA on 1/20/2018.
 */

public class JGGAddTimeSlotDialog extends android.app.AlertDialog.Builder implements View.OnClickListener {

    private Context mContext;
    private JGGAppBaseModel.AppointmentType mType;

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
    private int borderBackground;
    private int imgActiveAM;
    private int imgActivePM;
    private int imgInActiveAM;
    private int imgInActivePM;
    private int imgUp;
    private int imgDown;
    private int imgClose;

    private int startHour = 10;
    private int startMinute = 0;
    private boolean startAM = true;
    private int endHour = 11;
    private int endMinute = 0;
    private boolean endAM = true;

    public JGGAddTimeSlotDialog(Context context, JGGAppBaseModel.AppointmentType type) {
        super(context);
        mContext = context;
        mType = type;
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.dialog_add_time_slot, null);

        switch (mType) {
            case SERVICES:
                okButtonColor = ContextCompat.getColor(mContext, R.color.JGGGreen);
                cancelButtonColor = ContextCompat.getColor(mContext, R.color.JGGGreen10Percent);
                imgActiveAM = R.mipmap.button_am_active_green;
                imgInActiveAM = R.mipmap.button_am_green;
                imgActivePM = R.mipmap.button_pm_active_green;
                imgInActivePM = R.mipmap.button_pm_green;
                imgUp = R.mipmap.button_showmore_green;
                imgDown  = R.mipmap.button_showless_green;
                imgClose = R.mipmap.button_close_round_green;
                borderBackground = R.drawable.green_border_background;
                break;
            case JOBS:
                okButtonColor = ContextCompat.getColor(mContext, R.color.JGGCyan);
                cancelButtonColor = ContextCompat.getColor(mContext, R.color.JGGCyan10Percent);
                imgActiveAM = R.mipmap.button_am_active_cyan;
                imgInActiveAM = R.mipmap.button_am_cyan;
                imgActivePM = R.mipmap.button_pm_active_cyan;
                imgInActivePM = R.mipmap.button_pm_cyan;
                imgUp = R.mipmap.button_showmore_cyan;
                imgDown  = R.mipmap.button_showless_cyan;
                imgClose = R.mipmap.button_close_round_cyan;
                borderBackground = R.drawable.cyan_border_background;
                break;
            case GOCLUB:
                okButtonColor = ContextCompat.getColor(mContext, R.color.JGGPurple);
                cancelButtonColor = ContextCompat.getColor(mContext, R.color.JGGPurple10Percent);
                imgActiveAM = R.mipmap.button_am_active_purple;
                imgInActiveAM = R.mipmap.button_am_purple;
                imgActivePM = R.mipmap.button_pm_active_purple;
                imgInActivePM = R.mipmap.button_pm_purple;
                imgUp = R.mipmap.button_showmore_purple;
                imgDown  = R.mipmap.button_showless_purple;
                imgClose = R.mipmap.button_close_round_purple;
                borderBackground = R.drawable.purple_border_background;
                break;
            default:
                break;
        }

        onShowAddTimeView(view);
        this.setView(view);
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
        btnClose.setImageResource(imgClose);

        btnStartTimeUp.setOnClickListener(this);
        btnStartTimeDown.setOnClickListener(this);
        btnStartMinuteUp.setOnClickListener(this);
        btnStartMinuteDown.setOnClickListener(this);
        btnStartTimeAM.setOnClickListener(this);
        btnStartTimePM.setOnClickListener(this);
        btnEndTimeUp.setOnClickListener(this);
        btnEndTimeDown.setOnClickListener(this);
        btnEndMinuteUp.setOnClickListener(this);
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
    public void onClick(View view) {
        if (view.getId() == R.id.btn_advance_search_end_time) {
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
    }
}
