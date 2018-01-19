package com.kelvin.jacksgogo.Fragments.Jobs;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.CustomView.Views.JGGAddTimeSlotDialog;
import com.kelvin.jacksgogo.CustomView.Views.JGGCalendarDialog;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppBaseModel;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

public class PostJobTimeFragment extends Fragment implements View.OnClickListener {

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

        btnOneTime.setOnClickListener(this);
        btnRepeating.setOnClickListener(this);
    }

    private void onShowCalendarView() {
        JGGCalendarDialog builder = new JGGCalendarDialog(mContext, JGGAppBaseModel.AppointmentType.JOBS);
        builder.lblCalendarTitle.setText("Pick the date:");
        builder.btnCalendarOk.setText("Done");
        builder.setOnItemClickListener(new JGGCalendarDialog.OnItemClickListener() {
            @Override
            public void onDoneButtonClick(View view, String date) {
                if (view.getId() == R.id.btn_add_time_duplicate_cancel) {
                    alertDialog.dismiss();
                } else if (view.getId() == R.id.btn_add_time_duplicate_ok) {
                    lblDate.setText(date);
                    alertDialog.dismiss();
                    onNextButtonEnable();
                }
            }
        });
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    private void onAddTimeClick() {

        JGGAddTimeSlotDialog builder = new JGGAddTimeSlotDialog(mContext, JGGAppBaseModel.AppointmentType.JOBS);
        alertDialog = builder.create();

        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
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

        else if (view.getId() == R.id.btn_add_time_cancel) {
            alertDialog.dismiss();
        } else if (view.getId() == R.id.btn_add_time_ok) {
            onNextButtonEnable();
            alertDialog.dismiss();
        }

        if (view.getId() == R.id.btn_post_job_date) {
            onShowCalendarView();
        }
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

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onNextButtonClick();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
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
        void onFragmentInteraction(Uri uri);
    }
}
