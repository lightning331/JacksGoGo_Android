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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.Adapter.Services.CategoryGridAdapter;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServiceSearchAdvanceFragment extends Fragment implements View.OnClickListener, TextWatcher, OnDateSelectedListener {

    private Context mContext;
    private OnFragmentInteractionListener mListener;

    private EditText txtAdvanceSearch;
    private TextView lblArea;
    private ImageView btnArea;
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

    private CategoryGridAdapter adapter;
    private GridView gridView;
    private ArrayList<Map<String, Object>> datas = new ArrayList<>();

    private String appType;
    private boolean cbdSelected = true;
    private boolean centralSelected = true;
    private boolean eunosSelected = true;
    private boolean orchardSelected = true;
    private boolean newtonSelected = true;
    private boolean toaSelected = true;
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

    public ServiceSearchAdvanceFragment() {
        // Required empty public constructor
    }

    public static ServiceSearchAdvanceFragment newInstance(String type) {
        ServiceSearchAdvanceFragment fragment = new ServiceSearchAdvanceFragment();
        Bundle args = new Bundle();
        args.putString("APPOINTMENT_TYPE", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            appType = getArguments().getString("APPOINTMENT_TYPE");
        }
        //createCategory();
    }

    private void createCategory() {
        datas.add(createMap("Cooking & Baking", R.mipmap.icon_cat_cooking_baking));
        datas.add(createMap("Education", R.mipmap.icon_cat_education));
        datas.add(createMap("Handyman", R.mipmap.icon_cat_handyman));
        datas.add(createMap("Household Chores", R.mipmap.icon_cat_householdchores));
        datas.add(createMap("Messenger", R.mipmap.icon_cat_messenger));
        datas.add(createMap("Running Man", R.mipmap.icon_cat_runningman));
        datas.add(createMap("Sports", R.mipmap.icon_cat_sports));
        datas.add(createMap("Other Professions", R.mipmap.icon_cat_other));
    }

    private Map<String, Object> createMap(String name, int iconId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("icon", iconId);
        String nn = map.get("name").toString();
        int id = (int)map.get("icon");
        return map;
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
        btnArea = view.findViewById(R.id.btn_service_search);
        lblArea = view.findViewById(R.id.lbl_advance_search_area);
        btnDate = view.findViewById(R.id.advance_search_date_layout);
        lblDate = view.findViewById(R.id.lbl_advance_search_date);
        btnTime = view.findViewById(R.id.advance_search_time_layout);
        lblTime = view.findViewById(R.id.lbl_advance_search_time);
        txtAdditionalTag = view.findViewById(R.id.txt_advance_additional_tag);
        btnSearch = view.findViewById(R.id.btn_advance_search);
        gridView = (GridView) view.findViewById(R.id.category_grid_view);

        lblArea.setOnClickListener(this);
        btnDate.setOnClickListener(this);
        btnTime.setOnClickListener(this);
        btnSearch.setOnClickListener(this);

        switch (appType) {
            case "SERVICES":
                cancelButtonColor = getResources().getColor(R.color.JGGGreen10Percent);
                okButtonColor = getResources().getColor(R.color.JGGGreen);
                borderBackground = R.drawable.green_border_background;
                txtAdvanceSearch.setBackgroundResource(R.drawable.green_border_background);
                btnSearch.setBackgroundResource(R.drawable.green_background);
                btnArea.setImageResource(R.mipmap.button_showless_green);
                break;
            case "JOBS":
                cancelButtonColor = getResources().getColor(R.color.JGGCyan10Percent);
                okButtonColor = getResources().getColor(R.color.JGGCyan);
                borderBackground = R.drawable.cyan_border_background;
                txtAdvanceSearch.setBackgroundResource(borderBackground);
                btnSearch.setBackgroundResource(R.drawable.cyan_background);
                btnArea.setImageResource(R.mipmap.button_showless_cyan);
                break;
            case "GOCLUB":
                cancelButtonColor = getResources().getColor(R.color.JGGPurple10Percent);
                okButtonColor = getResources().getColor(R.color.JGGPurple);
                borderBackground = R.drawable.purple_border_background;
                txtAdvanceSearch.setBackgroundResource(R.drawable.purple_border_background);
                btnSearch.setBackgroundResource(R.drawable.purple_background);
                btnArea.setImageResource(R.mipmap.button_showless_purple);
                break;
        }
        ArrayList<JGGCategoryModel> categories = JGGAppManager.getInstance(mContext).categories;
        adapter = new CategoryGridAdapter(mContext, categories, appType);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the GridView selected/clicked item text
                String name = datas.get(position).get("name").toString();
                Toast.makeText(getActivity(), name,
                        Toast.LENGTH_LONG).show();
            }
        });
        gridView.setAdapter(adapter);
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

        switch (appType) {
            case "SERVICES":
                imgActiveAM = R.mipmap.button_am_active_green;
                imgInActiveAM = R.mipmap.button_am_green;
                imgActivePM = R.mipmap.button_pm_active_green;
                imgInActivePM = R.mipmap.button_pm_green;
                imgUp = R.mipmap.button_showmore_green;
                imgDown  = R.mipmap.button_showless_green;
                btnClose.setImageResource(R.mipmap.button_close_round_green);
                break;
            case "JOBS":
                imgActiveAM = R.mipmap.button_am_active_cyan;
                imgInActiveAM = R.mipmap.button_am_cyan;
                imgActivePM = R.mipmap.button_pm_active_cyan;
                imgInActivePM = R.mipmap.button_pm_cyan;
                imgUp = R.mipmap.button_showmore_cyan;
                imgDown  = R.mipmap.button_showless_cyan;
                btnClose.setImageResource(R.mipmap.button_close_round_cyan);
                break;
            case "GOCLUB":
                imgActiveAM = R.mipmap.button_am_active_purple;
                imgInActiveAM = R.mipmap.button_am_purple;
                imgActivePM = R.mipmap.button_pm_active_purple;
                imgInActivePM = R.mipmap.button_pm_purple;
                imgUp = R.mipmap.button_showmore_purple;
                imgDown  = R.mipmap.button_showless_purple;
                btnClose.setImageResource(R.mipmap.button_close_round_purple);
                break;
        }

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

        switch (appType) {
            case "SERVICES":
                setCalendarViewImage(R.mipmap.button_previous_green, R.mipmap.button_next_green);
                break;
            case "JOBS":
                setCalendarViewImage(R.mipmap.button_previous_cyan, R.mipmap.button_next_cyan);
                break;
            case "GOCLUB":
                setCalendarViewImage(R.mipmap.button_previous_purple, R.mipmap.button_next_purple);
                break;
        }

        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();

    }

    private void setCalendarViewImage(int imgName1, int imgName2) {
        btnCalendarCancel.setBackgroundColor(cancelButtonColor);
        btnCalendarCancel.setTextColor(okButtonColor);
        duplicateTimeCalendar.setArrowColor(okButtonColor);
        duplicateTimeCalendar.setSelectionColor(okButtonColor);
        duplicateTimeCalendar.setLeftArrowMask(getResources().getDrawable(imgName1));
        duplicateTimeCalendar.setRightArrowMask(getResources().getDrawable(imgName2));
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

        switch (appType) {
            case "SERVICES":
                checkImage = R.mipmap.checkbox_on_green;
                setAreaViewImage(R.mipmap.button_tick_area_round_green);
                break;
            case "JOBS":
                checkImage = R.mipmap.checkbox_on_blue;
                setAreaViewImage(R.mipmap.button_tick_area_round_cyan);
                break;
            case "GOCLUB":
                checkImage = R.mipmap.checkbox_on_purple;
                setAreaViewImage(R.mipmap.button_tick_area_round_purple);
                break;
        }

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

    private void setAreaViewImage(int closeImage) {
        btnAreaClose.setImageResource(closeImage);
        btnCBD.setImageResource(checkImage);
        btnCentral.setImageResource(checkImage);
        btnEunos.setImageResource(checkImage);
        btnOrchard.setImageResource(checkImage);
        btnNewton.setImageResource(checkImage);
        btnToa.setImageResource(checkImage);
    }

    private void setOnCheck(ImageView button, boolean selected) {
        if (selected) button.setImageResource(checkImage);
        else button.setImageResource(R.mipmap.checkbox_off);
    }

    private void onAmClick(View view) {
        if (view.getId() == R.id.btn_add_start_time_am) {
            btnStartTimeAM.setImageResource(imgActiveAM);
            btnStartTimePM.setImageResource(imgInActivePM);
        } else {
            btnEndTimeAM.setImageResource(imgActiveAM);
            btnEndTimePM.setImageResource(imgInActivePM);
        }
    }

    private void onPmClick(View view) {
        if (view.getId() == R.id.btn_add_start_time_pm) {
            btnStartTimeAM.setImageResource(imgInActiveAM);
            btnStartTimePM.setImageResource(imgActivePM);
        } else {
            btnEndTimeAM.setImageResource(imgInActiveAM);
            btnEndTimePM.setImageResource(imgActivePM);
        }
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
        btnCalendarOk.setBackgroundColor(okButtonColor);
        btnCalendarOk.setTextColor(ContextCompat.getColor(mContext, R.color.JGGWhite));
        btnCalendarOk.setOnClickListener(this);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
