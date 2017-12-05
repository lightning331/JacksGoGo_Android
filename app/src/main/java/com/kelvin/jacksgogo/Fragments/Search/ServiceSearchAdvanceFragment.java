package com.kelvin.jacksgogo.Fragments.Search;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

public class ServiceSearchAdvanceFragment extends Fragment implements View.OnClickListener, TextWatcher, OnDateSelectedListener {

    private Context mContext;
    private OnFragmentInteractionListener mListener;

    private EditText txtAdvanceSearch;
    private TextView lblArea;
    private LinearLayout btnDate;
    private TextView lblDate;
    private LinearLayout btnTime;
    private TextView lblTime;
    private EditText txtAdditionalTag;
    private TextView btnSearch;

    // TimeView
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

    // CalendarView
    private MaterialCalendarView duplicateTimeCalendar;
    private TextView lblCalendarTitle;
    private TextView btnCalendarCancel;
    private TextView btnCalendarOk;

    // AreaView
    private ImageButton btnAreaClose;
    private ImageView btnCBD;
    private ImageView btnCentral;
    private ImageView btnEunos;
    private ImageView btnOrchard;
    private ImageView btnNewton;
    private ImageView btnToa;

    private AlertDialog alertDialog;

    private boolean cbdSelected = true;
    private boolean centralSelected = true;
    private boolean eunosSelected = true;
    private boolean orchardSelected = true;
    private boolean newtonSelected = true;
    private boolean toaSelected = true;

    public ServiceSearchAdvanceFragment() {
        // Required empty public constructor
    }

    public static ServiceSearchAdvanceFragment newInstance(String param1, String param2) {
        ServiceSearchAdvanceFragment fragment = new ServiceSearchAdvanceFragment();
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
        View view = inflater.inflate(R.layout.fragment_service_search_advance, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        txtAdvanceSearch = view.findViewById(R.id.txt_advance_search);
        lblArea = view.findViewById(R.id.lbl_advance_search_area);
        btnDate = view.findViewById(R.id.advance_search_date_layout);
        lblDate = view.findViewById(R.id.lbl_advance_search_date);
        btnTime = view.findViewById(R.id.advance_search_time_layout);
        lblTime = view.findViewById(R.id.lbl_advance_search_time);
        txtAdditionalTag = view.findViewById(R.id.txt_advance_additional_tag);
        btnSearch = view.findViewById(R.id.btn_advance_search);

        lblArea.setOnClickListener(this);
        btnDate.setOnClickListener(this);
        btnTime.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
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
        btnCancel = (TextView) view.findViewById(R.id.btn_add_time_cancel);
        btnCancel.setOnClickListener(this);
        btnOk = (TextView) view.findViewById(R.id.btn_add_time_ok);
        btnOk.setOnClickListener(this);
        btnClose = (ImageView) view.findViewById(R.id.btn_advance_search_close);
        btnSetEndTime = (TextView) view.findViewById(R.id.btn_advance_search_end_time);
        btnSetEndTime.setOnClickListener(this);
        endTimeOptionalLayout = (LinearLayout) view.findViewById(R.id.set_end_time_layout);
        endTimeLayout = (LinearLayout) view.findViewById(R.id.advance_search_end_time_layout);
    }

    private void onShowCalendarView() {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
        LayoutInflater inflater = this.getLayoutInflater();

        View calendarView = inflater.inflate(R.layout.view_post_service_duplicate_time, null);
        builder.setView(calendarView);
        alertDialog = builder.create();

        duplicateTimeCalendar = calendarView.findViewById(R.id.add_time_duplicate_calendar);
        duplicateTimeCalendar.setOnDateChangedListener(this);
        lblCalendarTitle = calendarView.findViewById(R.id.lbl_duplicate_time_title);
        lblCalendarTitle.setText("Pick the Date:");
        btnCalendarCancel = calendarView.findViewById(R.id.btn_add_time_duplicate_cancel);
        btnCalendarCancel.setOnClickListener(this);
        btnCalendarOk = calendarView.findViewById(R.id.btn_add_time_duplicate_ok);
        btnCalendarOk.setText("Done");

        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();

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

    private void onShowAreaView() {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
        LayoutInflater inflater = this.getLayoutInflater();

        View areaView = inflater.inflate(R.layout.view_advance_search_area, null);
        builder.setView(areaView);
        alertDialog = builder.create();

        btnAreaClose = areaView.findViewById(R.id.btn_area_close);
        btnCBD = areaView.findViewById(R.id.btn_area_cbd);
        btnCentral = areaView.findViewById(R.id.btn_area_central);
        btnEunos = areaView.findViewById(R.id.btn_area_eunos);
        btnOrchard = areaView.findViewById(R.id.btn_area_orchard);
        btnNewton = areaView.findViewById(R.id.btn_area_newton);
        btnToa = areaView.findViewById(R.id.btn_area_toa);

        btnAreaClose.setOnClickListener(this);
        btnCBD.setOnClickListener(this);
        btnCentral.setOnClickListener(this);
        btnEunos.setOnClickListener(this);
        btnOrchard.setOnClickListener(this);
        btnNewton.setOnClickListener(this);
        btnToa.setOnClickListener(this);

        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
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
        if (view.getId() == R.id.lbl_advance_search_area) {
            onShowAreaView();
        } else if (view.getId() == R.id.advance_search_date_layout) {
            onShowCalendarView();
        } else if (view.getId() == R.id.advance_search_time_layout) {
            onAddTimeClick();
        } else if (view.getId() == R.id.btn_advance_search) {

        } else if (view.getId() == R.id.btn_advance_search_end_time) {
            endTimeOptionalLayout.setVisibility(View.GONE);
            endTimeLayout.setVisibility(View.VISIBLE);
            btnClose.setOnClickListener(this);
        } else if (view.getId() == R.id.btn_advance_search_close) {
            endTimeOptionalLayout.setVisibility(View.VISIBLE);
            endTimeLayout.setVisibility(View.GONE);
        } else if (view.getId() == R.id.btn_add_start_time_am) {
            onAmClick(view);
        } else if (view.getId() == R.id.btn_add_start_time_pm) {
            onPmClick(view);
        } else if (view.getId() == R.id.btn_add_end_time_am) {
            onAmClick(view);
        } else if (view.getId() == R.id.btn_add_end_time_pm) {
            onPmClick(view);
        } else if (view.getId() == R.id.btn_area_cbd) {
            cbdSelected = !cbdSelected;
            setOnCheck(btnCBD, cbdSelected);
        } else if (view.getId() == R.id.btn_area_central) {
            centralSelected = !centralSelected;
            setOnCheck(btnCentral, centralSelected);
        } else if (view.getId() == R.id.btn_area_eunos) {
            eunosSelected = !eunosSelected;
            setOnCheck(btnEunos, eunosSelected);
        } else if (view.getId() == R.id.btn_area_orchard) {
            orchardSelected = !orchardSelected;
            setOnCheck(btnOrchard, orchardSelected);
        } else if (view.getId() == R.id.btn_area_newton) {
            newtonSelected = !newtonSelected;
            setOnCheck(btnNewton, newtonSelected);
        } else if (view.getId() == R.id.btn_area_toa) {
            toaSelected = !toaSelected;
            setOnCheck(btnToa, toaSelected);
        } else if (view.getId() == R.id.btn_area_close) {
            alertDialog.dismiss();
        } else if (view.getId() == R.id.btn_add_time_cancel) {
            alertDialog.dismiss();
        } else if (view.getId() == R.id.btn_add_time_ok) {
            alertDialog.dismiss();
        } else if (view.getId() == R.id.btn_add_time_duplicate_cancel) {
            alertDialog.dismiss();
        } else if (view.getId() == R.id.btn_add_time_duplicate_ok) {
            alertDialog.dismiss();
        }
    }

    private void setOnCheck(ImageView button, boolean selected) {
        if (selected) button.setImageResource(R.mipmap.checkbox_on_green);
        else button.setImageResource(R.mipmap.checkbox_off);
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
        btnCalendarOk.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGGreen));
        btnCalendarOk.setTextColor(ContextCompat.getColor(mContext, R.color.JGGWhite));
        btnCalendarOk.setOnClickListener(this);
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
}
