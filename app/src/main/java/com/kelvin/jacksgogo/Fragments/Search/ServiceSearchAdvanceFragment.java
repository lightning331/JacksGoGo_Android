package com.kelvin.jacksgogo.Fragments.Search;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.Adapter.CategoryCellAdapter;
import com.kelvin.jacksgogo.CustomView.Views.JGGAddTimeSlotDialog;
import com.kelvin.jacksgogo.CustomView.Views.JGGCalendarDialog;
import com.kelvin.jacksgogo.CustomView.Views.SelectAreaDialog;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;

import java.util.ArrayList;
import java.util.Date;

import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.GOCLUB;
import static com.kelvin.jacksgogo.Utils.Global.JOBS;
import static com.kelvin.jacksgogo.Utils.Global.SERVICES;

public class ServiceSearchAdvanceFragment extends Fragment implements View.OnClickListener, TextWatcher {

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
    private RecyclerView recyclerView;
    private CategoryCellAdapter adapter;
    private ArrayList<JGGCategoryModel> mCategories;

    private AppointmentType mType;
    private AlertDialog alertDialog;
    private int imgBorderBackground;
    private int imgBackground;
    private int imgArea;

    public ServiceSearchAdvanceFragment() {
        // Required empty public constructor
    }

    public static ServiceSearchAdvanceFragment newInstance(String type) {
        ServiceSearchAdvanceFragment fragment = new ServiceSearchAdvanceFragment();
        Bundle args = new Bundle();
        args.putString(APPOINTMENT_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String appType = getArguments().getString(APPOINTMENT_TYPE);
            switch (appType) {
                case SERVICES:
                    mType = AppointmentType.SERVICES;
                    imgBorderBackground = R.drawable.green_border_background;
                    imgBackground = R.drawable.green_background;
                    imgArea = R.mipmap.button_showless_green;
                    break;
                case JOBS:
                    mType = AppointmentType.JOBS;
                    imgBorderBackground = R.drawable.cyan_border_background;
                    imgBackground = R.drawable.cyan_background;
                    imgArea = R.mipmap.button_showless_cyan;
                    break;
                case GOCLUB:
                    mType = AppointmentType.GOCLUB;
                    imgBorderBackground = R.drawable.purple_border_background;
                    imgBackground = R.drawable.purple_background;
                    imgArea = R.mipmap.button_showless_purple;
                    break;
            }
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
        btnArea = view.findViewById(R.id.btn_service_search);
        lblArea = view.findViewById(R.id.lbl_advance_search_area);
        btnDate = view.findViewById(R.id.advance_search_date_layout);
        lblDate = view.findViewById(R.id.lbl_advance_search_date);
        btnTime = view.findViewById(R.id.advance_search_time_layout);
        lblTime = view.findViewById(R.id.lbl_advance_search_time);
        txtAdditionalTag = view.findViewById(R.id.txt_advance_additional_tag);
        btnSearch = view.findViewById(R.id.btn_advance_search);

        txtAdvanceSearch.setBackgroundResource(imgBorderBackground);
        btnSearch.setBackgroundResource(imgBackground);
        btnArea.setImageResource(imgArea);

        lblArea.setOnClickListener(this);
        btnDate.setOnClickListener(this);
        btnTime.setOnClickListener(this);
        btnSearch.setOnClickListener(this);

        mCategories = JGGAppManager.getInstance(mContext).categories;
        recyclerView = view.findViewById(R.id.category_recycler_view);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.VERTICAL, false));
        }
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        adapter = new CategoryCellAdapter(mContext, mCategories, mType);
        adapter.setOnItemClickListener(new CategoryCellAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (mType == AppointmentType.SERVICES) {
                    if (mCategories != null) {
                        String name = mCategories.get(position).getName();
                        Toast.makeText(mContext, name,
                                Toast.LENGTH_LONG).show();
                    }
                } else if (mType == AppointmentType.JOBS) {
                    if (mCategories != null) {
                        String name = "";
                        if (position == 0)
                            name = "Quick Jobs";
                        else
                            name = mCategories.get(position - 1).getName();

                        Toast.makeText(mContext, name,
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void onAddTimeClick() {
        JGGAddTimeSlotDialog builder = new JGGAddTimeSlotDialog(mContext, mType);
        builder.setOnItemClickListener(new JGGAddTimeSlotDialog.OnItemClickListener() {
            @Override
            public void onDoneButtonClick(View view, Date start, Date end, Integer number) {
                if (view.getId() == R.id.btn_add_time_cancel) {
                    alertDialog.dismiss();
                } else if (view.getId() == R.id.btn_add_time_ok) {
                    alertDialog.dismiss();
                }
            }
        });
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    private void onShowCalendarView() {
        JGGCalendarDialog builder = new JGGCalendarDialog(mContext, mType);
        builder.lblCalendarTitle.setText("Pick the Date:");
        builder.btnCalendarOk.setText("Done");
        builder.setOnItemClickListener(new JGGCalendarDialog.OnItemClickListener() {
            @Override
            public void onDoneButtonClick(View view, String month, String day, String year) {
                if (view.getId() == R.id.btn_add_time_duplicate_cancel) {
                    alertDialog.dismiss();
                } else if (view.getId() == R.id.btn_add_time_duplicate_ok) {
                    lblDate.setText(month + " " + day);
                    alertDialog.dismiss();
                }
            }
        });
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    private void onShowAreaView() {
        SelectAreaDialog builder = new SelectAreaDialog(mContext, mType);
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
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
}
