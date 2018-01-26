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

import com.kelvin.jacksgogo.Activities.Search.PostServiceActivity;
import com.kelvin.jacksgogo.Adapter.Jobs.PostJobRepeatingDayAdapter;
import com.kelvin.jacksgogo.CustomView.Views.JGGAddTimeSlotDialog;
import com.kelvin.jacksgogo.CustomView.Views.JGGCalendarDialog;
import com.kelvin.jacksgogo.CustomView.Views.RepeatingDayDialog;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppBaseModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCreatingJobModel;
import com.kelvin.jacksgogo.Utils.Models.System.JGGJobTimeModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private JGGCreatingJobModel creatingJob;
    private Global.JGGJobType selectedJobType;
    private Boolean isSpecific;
    private Global.JGGRepetitionType selectedRepeatingType;
    private List<Integer> selectedRepeatingDays = new ArrayList<Integer>();
    private String repetition = "";
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

        creatingJob = ((PostServiceActivity)mContext).creatingAppointment;
        selectedJobType = creatingJob.getJobType();
        isSpecific = creatingJob.getJobTime().isSpecific();
        selectedRepeatingType = creatingJob.getRepetitionType();
        repetition = creatingJob.getRepetition();
        selectedRepeatingDays = creatingJob.getSelectedRepeatingDays();
        if (creatingJob.getJobTime().getStartOn() != null) {
            lblDate.setText(getDateString(creatingJob.getJobTime().getStartOn()));
            startTime = Global.getTimeString(creatingJob.getJobTime().getStartOn());
            lblTime.setText(startTime);
            if (creatingJob.getJobTime().getEndOn() != null) {
                endTime = Global.getTimeString(creatingJob.getJobTime().getEndOn());
                lblTime.setText(startTime + " - " + endTime);
            }
            onNextButtonEnable();
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
        switch (selectedJobType) {
            case none:
                lblDone.setVisibility(View.GONE);
                btnNext.setVisibility(View.GONE);
                break;
            case oneTime:
                onYellowButtonColor(btnOneTime);
                onCyanButtonColor(btnSpecific);
                onCyanButtonColor(btnAnyDay);
                btnSpecific.setVisibility(View.VISIBLE);
                btnAnyDay.setVisibility(View.VISIBLE);
                dateTimeLayout.setVisibility(View.VISIBLE);
                btnRepeating.setVisibility(View.GONE);
                btnSpecific.setOnClickListener(this);
                btnAnyDay.setOnClickListener(this);
                if (isSpecific == null) {
                    dateTimeLayout.setVisibility(View.GONE);
                    btnNext.setVisibility(View.GONE);
                } else if (isSpecific) {
                    specific = true;
                    repeatingJob = true;
                    onYellowButtonColor(btnSpecific);
                    btnAnyDay.setVisibility(View.GONE);
                    btnDate.setOnClickListener(this);
                    btnTime.setOnClickListener(this);
                } else if (!isSpecific) {
                    anyTime = true;
                    repeatingJob = true;
                    onYellowButtonColor(btnAnyDay);
                    btnSpecific.setVisibility(View.GONE);
                    lblCertainDateTime.setVisibility(View.VISIBLE);
                    btnDate.setOnClickListener(this);
                    btnTime.setOnClickListener(this);
                }
                break;
            case repeating:
                onYellowButtonColor(btnRepeating);
                onCyanButtonColor(btnWeekly);
                onCyanButtonColor(btnMonthly);
                btnOneTime.setVisibility(View.GONE);
                btnWeekly.setVisibility(View.VISIBLE);
                btnMonthly.setVisibility(View.VISIBLE);
                lblDone.setVisibility(View.VISIBLE);
                btnWeekly.setOnClickListener(this);
                btnMonthly.setOnClickListener(this);
                if (selectedRepeatingDays.size() > 0) {
                    btnAnotherDay.setVisibility(View.VISIBLE);
                    btnAnotherDay.setOnClickListener(this);
                    onNextButtonEnable();
                }
                switch (selectedRepeatingType) {
                    case none:
                        btnNext.setVisibility(View.GONE);
                        break;
                    case weekly:
                        weekly = true;
                        onYellowButtonColor(btnWeekly);
                        initRecyclerView(selectedRepeatingType);
                        btnWeekly.setVisibility(View.VISIBLE);
                        break;
                    case monthly:
                        monthly = true;
                        onYellowButtonColor(btnMonthly);
                        initRecyclerView(selectedRepeatingType);
                        btnMonthly.setVisibility(View.VISIBLE);
                        break;
                }
                break;
        }
    }

    private void onShowCalendarDialog() {
        JGGCalendarDialog builder = new JGGCalendarDialog(mContext, JGGAppBaseModel.AppointmentType.JOBS);
        builder.lblCalendarTitle.setText("Pick the date:");
        builder.btnCalendarOk.setText("Done");
        builder.setOnItemClickListener(new JGGCalendarDialog.OnItemClickListener() {
            @Override
            public void onDoneButtonClick(View view, String month, String day, String year) {
                if (view.getId() == R.id.btn_add_time_duplicate_cancel) {
                    alertDialog.dismiss();
                } else if (view.getId() == R.id.btn_add_time_duplicate_ok) {
                    selectedDay = day + "/" + month + "/" + year;
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
        JGGAddTimeSlotDialog builder = new JGGAddTimeSlotDialog(mContext, JGGAppBaseModel.AppointmentType.JOBS);
        builder.setOnItemClickListener(new JGGAddTimeSlotDialog.OnItemClickListener() {
            @Override
            public void onDoneButtonClick(View view, String start, String end, Boolean startAM, Boolean endAM) {
                if (view.getId() == R.id.btn_add_time_cancel) {
                    alertDialog.dismiss();
                } else if (view.getId() == R.id.btn_add_time_ok) {
                    alertDialog.dismiss();
                    startTime = start; endTime = end;
                    if (endTime.equals(""))
                        lblTime.setText(start + " " + Global.getPeriod(startAM));
                    else
                        lblTime.setText(start + " " + Global.getPeriod(startAM) + " - " + end + " " + Global.getPeriod(endAM));
                    if (lblTime.getText().length() > 0 && lblDate.getText().length() > 0)
                        onNextButtonEnable();
                }
            }
        });
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    private void onShowRepeatingDayDialog(Global.JGGRepetitionType type) {
        selectedRepeatingDays.clear();
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
                        selectedRepeatingDays.add(day);
                    }
                    initRecyclerView(selectedRepeatingType);
                    onNextButtonEnable();
                    btnAnotherDay.setVisibility(View.VISIBLE);
                    btnAnotherDay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onShowRepeatingDayDialog(selectedRepeatingType);
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

    private void initRecyclerView(final Global.JGGRepetitionType type) {
        if (type == Global.JGGRepetitionType.weekly) {
            onYellowButtonColor(btnWeekly);
            btnMonthly.setVisibility(View.GONE);
            btnNext.setVisibility(View.VISIBLE);
        } else if (type == Global.JGGRepetitionType.monthly) {
            onYellowButtonColor(btnMonthly);
            btnWeekly.setVisibility(View.GONE);
            btnNext.setVisibility(View.VISIBLE);
        }

        recyclerView.setVisibility(View.VISIBLE);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.VERTICAL, false));
        }
        final PostJobRepeatingDayAdapter adapter = new PostJobRepeatingDayAdapter(mContext, selectedRepeatingDays, selectedRepeatingType);
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
            if (oneTimeJob) selectedJobType = Global.JGGJobType.none;
            else selectedJobType = Global.JGGJobType.oneTime; isSpecific = null; specific = false;
            lblDate.setText(null);
            lblTime.setText(null);
            oneTimeJob = !oneTimeJob;
        } else if (view.getId() == R.id.btn_post_job_repeating) {
            if (repeatingJob) selectedJobType = Global.JGGJobType.none;
            else selectedJobType = Global.JGGJobType.repeating; selectedRepeatingType = Global.JGGRepetitionType.none; weekly = false; monthly = false;
            repeatingJob = !repeatingJob;
        } else if (view.getId() == R.id.btn_post_job_specific) {
            if (specific) isSpecific = null;
            else isSpecific = true;
            lblDate.setText(null);
            lblTime.setText(null);
            specific = !specific;
        } else if (view.getId() == R.id.btn_post_job_any_day) {
            if (anyTime) isSpecific = null;
            else isSpecific = false;
            lblDate.setText(null);
            lblTime.setText(null);
            anyTime = !anyTime;
        } else if (view.getId() == R.id.btn_post_job_date) {
            onShowCalendarDialog();
        } else if (view.getId() == R.id.btn_post_job_time) {
            onShowAddTimeClickDialog();
        } else if (view.getId() == R.id.btn_post_job_weekly) {
            if (weekly) selectedRepeatingType = Global.JGGRepetitionType.none;
            else selectedRepeatingType = Global.JGGRepetitionType.weekly;
            selectedRepeatingDays.clear();
            repetition = "";
            weekly = !weekly;
        } else if (view.getId() == R.id.btn_post_job_monthly) {
            if (monthly) selectedRepeatingType = Global.JGGRepetitionType.none;
            else selectedRepeatingType = Global.JGGRepetitionType.monthly;
            selectedRepeatingDays.clear();
            repetition = "";
            monthly = !monthly;
        } else if (view.getId() == R.id.btn_post_job_add_another_day) {
            onShowRepeatingDayDialog(selectedRepeatingType);
        } else if (view.getId() == R.id.btn_post_job_time_next) {
            onSaveCreatingJob();
            listener.onNextButtonClick();

            return;
        }
        updateData();
    }

    private void onSaveCreatingJob() {
        JGGJobTimeModel timeModel = new JGGJobTimeModel();
        if (selectedJobType == Global.JGGJobType.oneTime) {
            timeModel.setSpecific(isSpecific);
            startOn = Global.getDate(selectedDay + " " + startTime + ":" + "00");
            endOn = Global.getDate(selectedDay + " " + endTime + ":" + "00");
            timeModel.setStartOn(startOn);
            timeModel.setEndOn(endOn);
            creatingJob.setJobTime(timeModel);
        } else if (selectedJobType == Global.JGGJobType.repeating) {
            creatingJob.setRepetition(repetition);
        }
        creatingJob.setRepetitionType(selectedRepeatingType);
        creatingJob.setJobType(selectedJobType);
        ((PostServiceActivity)mContext).creatingAppointment = creatingJob;
    }

    public String getDateString(Date date) {
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMM");
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        String month = monthFormat.format(date);
        String day = dayFormat.format(date);
        String year = yearFormat.format(date);
        selectedDay = day + "/" + month + "/" + year;
        return day + " " + month;
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