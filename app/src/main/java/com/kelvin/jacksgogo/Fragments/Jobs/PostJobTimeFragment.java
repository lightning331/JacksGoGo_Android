package com.kelvin.jacksgogo.Fragments.Jobs;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.Adapter.Jobs.PostJobRepeatingDayAdapter;
import com.kelvin.jacksgogo.CustomView.Views.JGGAddTimeSlotDialog;
import com.kelvin.jacksgogo.CustomView.Views.JGGCalendarDialog;
import com.kelvin.jacksgogo.CustomView.Views.RepeatingDayDialog;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global.JGGRepetitionType;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.System.JGGTimeSlotModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.selectedAppointment;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.appointmentMonthDate;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getDayMonthString;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getTimePeriodString;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getTimeString;

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
    private JGGAppointmentModel mJob;
    private Integer jobType;
    private JGGRepetitionType repetitionType;
    private Boolean isSpecific;
    private String repetition = "";
    private List<Integer> repetitions = new ArrayList<Integer>();
    private String selectedDay;
    private String startTime;
    private String endTime;
    private Date startOn;
    private Date endOn;

    private boolean oneTimeJob;
    private boolean repeatingJob;
    private boolean specific;
    private boolean anyTime;
    private boolean weekly;
    private boolean monthly;

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
        recyclerView = view.findViewById(R.id.post_job_selected_day_recycler_view);

        btnOneTime.setOnClickListener(this);
        btnRepeating.setOnClickListener(this);

        mJob = selectedAppointment;
        jobType = mJob.getAppointmentType();
        repetitionType = mJob.getRepetitionType();
        repetition = mJob.getRepetition();
        if (repetition != null && repetition.length() > 0) {
            String [] strings = repetition.split(",");
            for (String rep: strings) {
                repetitions.add(Integer.valueOf(rep));
            }
        }
        if (mJob.getSessions() != null
                && mJob.getSessions().size() > 0) {
            isSpecific = mJob.getSessions().get(0).isSpecific();
            if (mJob.getSessions().get(0).getStartOn() != null) {
                String strDate = mJob.getSessions().get(0).getStartOn();
                Date date = appointmentMonthDate(strDate);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                selectedDay = dateFormat.format(date);
                lblDate.setText(getDayMonthString(date));
                startOn = appointmentMonthDate(mJob.getSessions().get(0).getStartOn());
                startTime = getTimePeriodString(startOn);
                lblTime.setText(startTime);
                if (mJob.getSessions().get(0).getEndOn() != null) {
                    endOn = appointmentMonthDate(mJob.getSessions().get(0).getEndOn());
                    endTime = getTimePeriodString(endOn);
                    lblTime.setText(startTime + " - " + endTime);
                }
            }
        }
        updateData();
    }

    private void updateData() {
        btnOneTime.setVisibility(View.VISIBLE);
        btnRepeating.setVisibility(View.VISIBLE);
        btnNext.setVisibility(View.VISIBLE);
        btnSpecific.setVisibility(View.GONE);
        btnAnyDay.setVisibility(View.GONE);
        btnWeekly.setVisibility(View.GONE);
        btnMonthly.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        dateTimeLayout.setVisibility(View.GONE);
        lblCertainDateTime.setVisibility(View.GONE);
        lblDone.setVisibility(View.VISIBLE);
        btnAnotherDay.setVisibility(View.GONE);
        onNextButtonDisable();
        onCyanButtonColor(btnOneTime);
        onCyanButtonColor(btnRepeating);
        if (jobType == null) {      // None selected
            lblDone.setVisibility(View.GONE);
            btnNext.setVisibility(View.GONE);
        } else if (jobType == 1) {      // One-time Job TimeSlot
            onYellowButtonColor(btnOneTime);
            onCyanButtonColor(btnSpecific);
            onCyanButtonColor(btnAnyDay);
            btnSpecific.setVisibility(View.VISIBLE);
            btnAnyDay.setVisibility(View.VISIBLE);
            dateTimeLayout.setVisibility(View.VISIBLE);
            btnRepeating.setVisibility(View.GONE);
            btnSpecific.setOnClickListener(this);
            btnAnyDay.setOnClickListener(this);
            if (lblTime.getText().toString().length() > 0 && lblDate.getText().toString().length() > 0 )
                onNextButtonEnable();
            else
                onNextButtonDisable();
            if (isSpecific == null) {
                dateTimeLayout.setVisibility(View.GONE);
                btnNext.setVisibility(View.GONE);
            } else if (isSpecific) {
                specific = true;
                repeatingJob = false;
                onYellowButtonColor(btnSpecific);
                btnAnyDay.setVisibility(View.GONE);
                btnDate.setOnClickListener(this);
                btnTime.setOnClickListener(this);
            } else {
                anyTime = true;
                repeatingJob = false;
                onYellowButtonColor(btnAnyDay);
                btnSpecific.setVisibility(View.GONE);
                lblCertainDateTime.setVisibility(View.VISIBLE);
                btnDate.setOnClickListener(this);
                btnTime.setOnClickListener(this);
            }
        } else if (jobType == 0) {      // Repeating Job TimeSlot
            onYellowButtonColor(btnRepeating);
            onCyanButtonColor(btnWeekly);
            onCyanButtonColor(btnMonthly);
            btnOneTime.setVisibility(View.GONE);
            btnWeekly.setVisibility(View.VISIBLE);
            btnMonthly.setVisibility(View.VISIBLE);
            lblDone.setVisibility(View.VISIBLE);
            btnWeekly.setOnClickListener(this);
            btnMonthly.setOnClickListener(this);
            if (repetitions.size() > 0) {
                btnAnotherDay.setVisibility(View.VISIBLE);
                btnAnotherDay.setOnClickListener(this);
                onNextButtonEnable();
            }
            switch (repetitionType) {
                case none:
                    btnNext.setVisibility(View.GONE);
                    break;
                case weekly:
                    weekly = true;
                    onYellowButtonColor(btnWeekly);
                    initRecyclerView(repetitionType);
                    btnWeekly.setVisibility(View.VISIBLE);
                    break;
                case monthly:
                    monthly = true;
                    onYellowButtonColor(btnMonthly);
                    initRecyclerView(repetitionType);
                    btnMonthly.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    private void onShowCalendarDialog() {
        JGGCalendarDialog builder = new JGGCalendarDialog(mContext, AppointmentType.JOBS);
        builder.lblCalendarTitle.setText("Pick the date:");
        builder.btnCalendarOk.setText("Done");
        builder.setOnItemClickListener(new JGGCalendarDialog.OnItemClickListener() {
            @Override
            public void onDoneButtonClick(View view, String month, String day, String year) {
                if (view.getId() == R.id.btn_add_time_duplicate_cancel) {
                    alertDialog.dismiss();
                } else if (view.getId() == R.id.btn_add_time_duplicate_ok) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd");
                    try {
                        Date varDate = dateFormat.parse(year + "-" + month + "-" + day);
                        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        selectedDay = dateFormat.format(varDate);
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                    lblDate.setText(day + " " + month);
                    alertDialog.dismiss();
                    if (lblTime.getText().length() > 0 && lblDate.getText().length() > 0)
                        onNextButtonEnable();
                }
            }
        });
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    private void onShowAddTimeClickDialog() {
        JGGAddTimeSlotDialog builder = new JGGAddTimeSlotDialog(mContext, AppointmentType.JOBS);
        builder.setOnItemClickListener(new JGGAddTimeSlotDialog.OnItemClickListener() {
            @Override
            public void onDoneButtonClick(View view, Date start, Date end, Integer number) {
                if (view.getId() == R.id.btn_add_time_cancel) {
                    alertDialog.dismiss();
                } else if (view.getId() == R.id.btn_add_time_ok) {
                    alertDialog.dismiss();
                    startTime = getTimePeriodString(start);
                    startOn = start;
                    if (end != null) {
                        endTime = getTimePeriodString(end);
                        endOn = end;
                        lblTime.setText(startTime + " - " + endTime);
                    } else {
                        lblTime.setText(startTime);
                    }
                    if (lblTime.getText().length() > 0 && lblDate.getText().length() > 0)
                        onNextButtonEnable();
                }
            }
        });
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    private void onShowRepeatingDayDialog(JGGRepetitionType type) {
        repetitions.clear();
        RepeatingDayDialog builder = new RepeatingDayDialog(mContext, type);
        builder.setOnItemClickListener(new RepeatingDayDialog.OnItemClickListener() {
            @Override
            public void onDoneButtonClick(View view, List<Integer> days) {
                if (view.getId() == R.id.btn_alert_cancel) {
                    alertDialog.dismiss();
                } else if (view.getId() == R.id.btn_alert_ok) {
                    for (int i = 0; i < days.size(); i ++) {
                        Integer day = days.get(i) - 1;
                        if (repetition == null || repetition.equals("")) repetition = day.toString();
                        else repetition = repetition + "," + day.toString();
                        repetitions.add(day);
                    }
                    initRecyclerView(repetitionType);
                    onNextButtonEnable();
                    btnAnotherDay.setVisibility(View.VISIBLE);
                    btnAnotherDay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onShowRepeatingDayDialog(repetitionType);
                        }
                    });
                    alertDialog.dismiss();
                }
            }
        });
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    private void initRecyclerView(final JGGRepetitionType type) {
        if (type == JGGRepetitionType.weekly) {
            onYellowButtonColor(btnWeekly);
            btnMonthly.setVisibility(View.GONE);
            btnNext.setVisibility(View.VISIBLE);
        } else if (type == JGGRepetitionType.monthly) {
            onYellowButtonColor(btnMonthly);
            btnWeekly.setVisibility(View.GONE);
            btnNext.setVisibility(View.VISIBLE);
        }

        recyclerView.setVisibility(View.VISIBLE);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.VERTICAL, false));
        }
        final PostJobRepeatingDayAdapter adapter = new PostJobRepeatingDayAdapter(mContext, repetitions, repetitionType);
        adapter.setOnItemClickListener(new PostJobRepeatingDayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (view.getId() == R.id.btn_post_job_repeating_day) onShowRepeatingDayDialog(type);
                else if (view.getId() == R.id.btn_post_job_repeating_day_close) {
                    if (adapter.getItemCount() == 1) {
                        btnAnotherDay.setVisibility(View.GONE);
                        onNextButtonDisable();
                    }
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_post_job_one_time) {
            if (oneTimeJob)
                jobType = null;
            else {
                jobType = 1;
                isSpecific = null;
                specific = false;
            }
            oneTimeJob = !oneTimeJob;
        } else if (view.getId() == R.id.btn_post_job_repeating) {
            if (repeatingJob)
                jobType = null;
            else {
                jobType = 0;
                repetitionType = JGGRepetitionType.none;
                weekly = false;
                monthly = false;
            }
            repeatingJob = !repeatingJob;
        } else if (view.getId() == R.id.btn_post_job_specific) {
            if (specific)
                isSpecific = null;
            else
                isSpecific = true;
            specific = !specific;
        } else if (view.getId() == R.id.btn_post_job_any_day) {
            if (anyTime)
                isSpecific = null;
            else
                isSpecific = false;
            anyTime = !anyTime;
        } else if (view.getId() == R.id.btn_post_job_date) {
            onShowCalendarDialog();
        } else if (view.getId() == R.id.btn_post_job_time) {
            onShowAddTimeClickDialog();
        } else if (view.getId() == R.id.btn_post_job_weekly) {
            if (weekly)
                repetitionType = JGGRepetitionType.none;
            else
                repetitionType = JGGRepetitionType.weekly;
            repetitions.clear();
            repetition = "";
            weekly = !weekly;
        } else if (view.getId() == R.id.btn_post_job_monthly) {
            if (monthly)
                repetitionType = JGGRepetitionType.none;
            else
                repetitionType = JGGRepetitionType.monthly;
            repetitions.clear();
            repetition = "";
            monthly = !monthly;
        } else if (view.getId() == R.id.btn_post_job_add_another_day) {
            onShowRepeatingDayDialog(repetitionType);
        } else if (view.getId() == R.id.btn_post_job_time_next) {
            onSaveCreatingJob();
            listener.onNextButtonClick();

            return;
        }
        updateData();
    }

    private void onSaveCreatingJob() {
        ArrayList<JGGTimeSlotModel> selectedTimeSlots = new ArrayList<>();
        JGGTimeSlotModel timeModel = new JGGTimeSlotModel();
        if (jobType == 1) {     // One-time
            timeModel.setSpecific(isSpecific);
            String startTime = selectedDay + "T" + getTimeString(startOn);
            timeModel.setStartOn(startTime);
            if (endOn != null) {
                String endTime = selectedDay + "T" + getTimeString(endOn);
                timeModel.setEndOn(endTime);
            }
            selectedTimeSlots.add(timeModel);
            mJob.setRepetition(null);
        } else if (jobType == 0) {      // Repeating
            mJob.setRepetition(repetition);
        }
        mJob.setSessions(selectedTimeSlots);
        mJob.setRepetitionType(repetitionType);
        mJob.setAppointmentType(jobType);
        selectedAppointment = mJob;
    }

    private void onYellowButtonColor(TextView button) {
        button.setBackgroundResource(R.drawable.yellow_background);
        button.setTextColor(ContextCompat.getColor(mContext, R.color.JGGBlack));
    }

    private void onCyanButtonColor(TextView button) {
        button.setBackgroundResource(R.drawable.cyan_border_background);
        button.setTextColor(ContextCompat.getColor(mContext, R.color.JGGCyan));
    }

    private void onNextButtonEnable() {
        lblNext.setTextColor(ContextCompat.getColor(mContext, R.color.JGGWhite));
        btnNext.setBackgroundResource(R.drawable.cyan_background);
        btnNext.setOnClickListener(this);
    }

    private void onNextButtonDisable() {
        lblNext.setTextColor(ContextCompat.getColor(mContext, R.color.JGGGrey2));
        btnNext.setBackgroundResource(R.drawable.grey_background);
        btnNext.setClickable(false);
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