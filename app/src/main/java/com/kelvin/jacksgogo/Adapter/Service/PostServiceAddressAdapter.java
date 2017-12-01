package com.kelvin.jacksgogo.Adapter.Service;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

public class PostServiceAddressAdapter extends Fragment implements View.OnClickListener, TextWatcher {

    private Context mContext;
    private OnFragmentInteractionListener mListener;

    private EditText txtUnit;
    private EditText txtStreet;
    private EditText txtPostCode;
    private ImageView btnCheckBox;
    private RelativeLayout btnNext;
    private TextView lblNext;

    private boolean isChecked = false;

    public PostServiceAddressAdapter() {
        // Required empty public constructor
    }

    public static PostServiceAddressAdapter newInstance(String param1, String param2) {
        PostServiceAddressAdapter fragment = new PostServiceAddressAdapter();
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
        View view = inflater.inflate(R.layout.post_service_address_fragment, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        txtUnit = view.findViewById(R.id.txt_post_address_unit);
        txtStreet = view.findViewById(R.id.txt_post_address_street);
        txtPostCode = view.findViewById(R.id.txt_post_address_postcode);
        btnCheckBox = view.findViewById(R.id.btn_post_address_checkbox);
        btnNext = view.findViewById(R.id.btn_post_address_next);
        lblNext = view.findViewById(R.id.lbl_post_address_next);

        btnCheckBox.setOnClickListener(this);
        txtUnit.addTextChangedListener(this);
        txtStreet.addTextChangedListener(this);
        txtPostCode.addTextChangedListener(this);
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
        if (view.getId() == R.id.btn_post_address_checkbox) {
            isChecked = !isChecked;
            if (isChecked) btnCheckBox.setImageResource(R.mipmap.checkbox_on_green);
            else btnCheckBox.setImageResource(R.mipmap.checkbox_off);
        } else if (view.getId() == R.id.btn_post_address_next) {
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
        } else {
            lblNext.setTextColor(ContextCompat.getColor(mContext, R.color.JGGGrey2));
            btnNext.setBackgroundResource(R.drawable.grey_background);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

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

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onNextButtonClick();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
