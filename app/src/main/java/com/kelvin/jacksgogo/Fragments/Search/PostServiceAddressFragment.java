package com.kelvin.jacksgogo.Fragments.Search;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppBaseModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.System.JGGAddressModel;

import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.selectedAppointment;
import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.JOBS;
import static com.kelvin.jacksgogo.Utils.Global.SERVICES;

public class PostServiceAddressFragment extends Fragment implements View.OnClickListener, TextWatcher {

    private Context mContext;
    private OnFragmentInteractionListener mListener;

    private TextView lblTitle;
    private TextView lblDesc;
    private EditText txtPlaceName;
    private EditText txtUnit;
    private EditText txtStreet;
    private EditText txtPostCode;
    private LinearLayout checkboxLayout;
    private ImageView btnCheckBox;
    private RelativeLayout btnNext;
    private TextView lblNext;

    private boolean isChecked = false;
    private JGGAppBaseModel.AppointmentType mType;
    private JGGAppointmentModel creatingJob;

    public PostServiceAddressFragment() {
        // Required empty public constructor
    }

    public static PostServiceAddressFragment newInstance(String type) {
        PostServiceAddressFragment fragment = new PostServiceAddressFragment();
        Bundle args = new Bundle();
        args.putString(APPOINTMENT_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String type = getArguments().getString(APPOINTMENT_TYPE);
            if (type.equals(SERVICES))
                mType = JGGAppBaseModel.AppointmentType.SERVICES;
            else if (type.equals(JOBS))
                mType = JGGAppBaseModel.AppointmentType.JOBS;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_service_address, container, false);

        creatingJob = selectedAppointment;

        initView(view);

        return view;
    }

    private void initView(View view) {
        lblTitle = view.findViewById(R.id.lbl_post_address_title);
        lblDesc = view.findViewById(R.id.lbl_post_address_desc);
        txtPlaceName = view.findViewById(R.id.txt_post_address_place_name);
        txtUnit = view.findViewById(R.id.txt_post_address_unit);
        txtStreet = view.findViewById(R.id.txt_post_address_street);
        txtPostCode = view.findViewById(R.id.txt_post_address_postcode);
        checkboxLayout = view.findViewById(R.id.show_full_address_layout);
        btnCheckBox = view.findViewById(R.id.btn_post_address_checkbox);
        btnNext = view.findViewById(R.id.btn_post_address_next);
        lblNext = view.findViewById(R.id.lbl_post_address_next);

        btnCheckBox.setOnClickListener(this);
        txtUnit.addTextChangedListener(this);
        txtStreet.addTextChangedListener(this);
        txtPostCode.addTextChangedListener(this);

        txtPlaceName.setText("");
        txtUnit.setText("2");
        txtStreet.setText("Jurong West Avenue 5");
        txtPostCode.setText("638657");

        if (mType == JGGAppBaseModel.AppointmentType.JOBS) {

            lblTitle.setText(R.string.post_job_address_title);
            lblDesc.setVisibility(View.VISIBLE);
            checkboxLayout.setVisibility(View.GONE);
            lblNext.setText("Next");

        }
        txtPlaceName.setText(creatingJob.getAddress().getFloor());
        txtUnit.setText(creatingJob.getAddress().getUnit());
        String street;
        if (creatingJob.getAddress().getStreet() == null)
            street = creatingJob.getAddress().getAddress();
        else
            street = creatingJob.getAddress().getStreet();
        txtStreet.setText(street);
        txtPostCode.setText(creatingJob.getAddress().getPostalCode());
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_post_address_checkbox) {
            isChecked = !isChecked;
            if (isChecked) {
                btnCheckBox.setImageResource(R.mipmap.checkbox_on_green);
            } else
                btnCheckBox.setImageResource(R.mipmap.checkbox_off);
        } else if (view.getId() == R.id.btn_post_address_next) {
            JGGAddressModel address = new JGGAddressModel();
            address.setFloor(txtPlaceName.getText().toString());
            address.setUnit(txtUnit.getText().toString());
            address.setStreet(txtStreet.getText().toString());
            address.setPostalCode(txtPostCode.getText().toString());
            address.setShowFullAddress(isChecked);

            creatingJob.setAddress(address);
            selectedAppointment = creatingJob;

            listener.onNextButtonClick();
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
            btnNext.setOnClickListener(this);
            lblNext.setTextColor(ContextCompat.getColor(mContext, R.color.JGGWhite));
            btnNext.setBackgroundResource(R.drawable.green_background);
            if (mType == JGGAppBaseModel.AppointmentType.JOBS) {
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

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onNextButtonClick();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
