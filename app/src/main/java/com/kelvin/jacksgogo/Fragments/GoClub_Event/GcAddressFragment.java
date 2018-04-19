package com.kelvin.jacksgogo.Fragments.GoClub_Event;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kelvin.jacksgogo.Activities.JGGMapViewActivity;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.System.JGGAddressModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.GOCLUB;
import static com.kelvin.jacksgogo.Utils.Global.JOBS;
import static com.kelvin.jacksgogo.Utils.Global.REQUEST_CODE;
import static com.kelvin.jacksgogo.Utils.Global.SERVICES;
import static com.kelvin.jacksgogo.Utils.JGGAppManager.selectedAppointment;

/**
 * A simple {@link Fragment} subclass.
 */
public class GcAddressFragment extends Fragment implements TextWatcher {

    private Context mContext;

    @BindView(R.id.btn_share_location)              Button btnShareLocation;
    @BindView(R.id.btn_type_address)                Button btnTypeAddress;
    @BindView(R.id.lbl_post_address_title)          TextView lblTitle;
    @BindView(R.id.txt_post_address_place_name)     EditText txtPlaceName;
    @BindView(R.id.txt_post_address_unit)           EditText txtUnit;
    @BindView(R.id.txt_post_address_street)         EditText txtStreet;
    @BindView(R.id.txt_post_address_postcode)       EditText txtPostCode;
    @BindView(R.id.show_full_address_layout)        LinearLayout checkboxLayout;
    @BindView(R.id.btn_post_address_checkbox)       ImageView btnCheckBox;
    @BindView(R.id.btn_post_address_next)           RelativeLayout btnNext;
    @BindView(R.id.lbl_post_address_next)           TextView lblNext;
    @BindView(R.id.btn_location)                    TextView btnLocation;
    @BindView(R.id.lbl_coordinate)                  TextView lblCoordinate;

    private boolean isShowFullAddress = false;
    private Global.AppointmentType mType;
    private String type;
    private JGGAppointmentModel creatingJob;
    private JGGAddressModel mAddress;

    private static final String TAG = GcAddressFragment.class.getSimpleName();
    public GcAddressFragment() {
        // Required empty public constructor
    }

    public static GcAddressFragment newInstance(String type) {
        GcAddressFragment fragment = new GcAddressFragment();
        Bundle args = new Bundle();
        args.putString(APPOINTMENT_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(APPOINTMENT_TYPE);
            if (type.equals(SERVICES))
                mType = Global.AppointmentType.SERVICES;
            else if (type.equals(JOBS))
                mType = Global.AppointmentType.JOBS;
            else if (type.equals(GOCLUB))
                mType = Global.AppointmentType.GOCLUB;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gc_address, container, false);
        ButterKnife.bind(this, view);

        creatingJob = JGGAppManager.getSelectedAppointment();
        mAddress = creatingJob.getAddress();

        setData();

        return view;
    }

    public void setData() {
        txtPlaceName.setText("");
        txtUnit.setText("2");
        txtStreet.setText("Jurong West Avenue 5");
        txtPostCode.setText("638657");

        if (mType == Global.AppointmentType.JOBS) {

            lblTitle.setText(R.string.post_job_address_title);
            checkboxLayout.setVisibility(View.GONE);
            lblNext.setText("Next");

            btnLocation.setBackgroundResource(R.drawable.purple_border_background);
            btnLocation.setTextColor(ContextCompat.getColor(mContext, R.color.JGGPurple));
        }
        txtPlaceName.setText(mAddress.getFloor());
        txtUnit.setText(mAddress.getUnit());
        txtStreet.setText(mAddress.getStreet());
        txtPostCode.setText(mAddress.getPostalCode());
        lblCoordinate.setText(String.valueOf(mAddress.getLat()) + "° N, " + String.valueOf(mAddress.getLon()) + "° E");
    }

    @OnClick(R.id.btn_post_address_checkbox)
    public void onCickAddressCheckbox() {
        isShowFullAddress = !isShowFullAddress;
        if (isShowFullAddress) {
            btnCheckBox.setImageResource(R.mipmap.checkbox_on_purple);
        } else
            btnCheckBox.setImageResource(R.mipmap.checkbox_off);
    }
    @OnClick(R.id.btn_location)
    public void onClickLocation() {
        Intent intent = new Intent(mContext, JGGMapViewActivity.class);
        intent.putExtra(APPOINTMENT_TYPE, type);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @OnClick(R.id.btn_post_address_next)
    public void onClickNext() {
        mAddress.setFloor(txtPlaceName.getText().toString());
        mAddress.setUnit(txtUnit.getText().toString());
        mAddress.setStreet(txtStreet.getText().toString());
        mAddress.setPostalCode(txtPostCode.getText().toString());
        mAddress.setShowFullAddress(!isShowFullAddress);

        creatingJob.setAddress(mAddress);
        selectedAppointment = creatingJob;

        listener.onNextButtonClick();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK){
                Gson gson = new Gson();
                String result=data.getStringExtra("result");
                Log.d(TAG, result);
                mAddress = gson.fromJson(result, JGGAddressModel.class);
                setData();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (txtUnit.length() > 0
                && txtStreet.length() > 0
                && txtPostCode.length() > 0) {
            lblNext.setTextColor(ContextCompat.getColor(mContext, R.color.JGGWhite));
            btnNext.setBackgroundResource(R.drawable.green_background);
            if (mType == Global.AppointmentType.JOBS) {
                btnNext.setBackgroundResource(R.drawable.cyan_background);
            }
        } else {
            lblNext.setTextColor(ContextCompat.getColor(mContext, R.color.JGGGrey2));
            btnNext.setBackgroundResource(R.drawable.grey_background);
            btnNext.setClickable(false);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    // TODO : Next Click Listener
    private GcTimeFragment.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onNextButtonClick();
    }

    public void setOnItemClickListener(GcTimeFragment.OnItemClickListener listener) {
        this.listener = listener;
    }

}
