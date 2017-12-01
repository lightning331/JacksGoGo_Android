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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

public class PostServicePriceAdapter extends Fragment implements View.OnClickListener, TextWatcher {

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    private TextView btnOneTime;
    private TextView lblPriceRange;
    private TextView btnFixedAmount;
    private EditText txtFixedAmount;
    private TextView btnFrom;
    private EditText txtFromMin;
    private EditText txtFromMax;
    private TextView btnPackage;
    private TextView lblPackageNum;
    private EditText txtPackageNum;
    private TextView lblPackageAmount;
    private EditText txtPackageAmount;
    private RelativeLayout btnNext;
    private TextView lblNext;

    private boolean isOneTimeService = false;
    private boolean isFixedAmount = false;
    private boolean isFromAmount = false;

    public PostServicePriceAdapter() {
        // Required empty public constructor
    }

    public static PostServicePriceAdapter newInstance(String param1, String param2) {
        PostServicePriceAdapter fragment = new PostServicePriceAdapter();
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
        View view = inflater.inflate(R.layout.post_service_price_fragment, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {

        btnOneTime = view.findViewById(R.id.btn_post_service_one_time);
        lblPriceRange = view.findViewById(R.id.lbl_post_service_price_range);
        btnFixedAmount = view.findViewById(R.id.btn_post_service_fixed_amount);
        txtFixedAmount = view.findViewById(R.id.txt_post_service_fixed_amount);
        txtFixedAmount.addTextChangedListener(this);
        btnFrom = view.findViewById(R.id.btn_post_service_From);
        txtFromMin = view.findViewById(R.id.txt_post_service_from_min);
        txtFromMin.addTextChangedListener(this);
        txtFromMax = view.findViewById(R.id.txt_post_service_from_max);
        txtFromMax.addTextChangedListener(this);
        btnPackage = view.findViewById(R.id.btn_post_service_package);
        lblPackageNum = view.findViewById(R.id.lbl_post_service_package_num);
        txtPackageNum = view.findViewById(R.id.txt_post_service_package_num);
        txtPackageNum.addTextChangedListener(this);
        lblPackageAmount = view.findViewById(R.id.lbl_post_service_package_amount);
        txtPackageAmount = view.findViewById(R.id.txt_post_service_package_amount);
        txtPackageAmount.addTextChangedListener(this);
        btnNext = view.findViewById(R.id.btn_post_service_price_next);
        lblNext = view.findViewById(R.id.lbl_post_service_price_next);
        btnOneTime.setOnClickListener(this);
        btnFixedAmount.setOnClickListener(this);
        btnFrom.setOnClickListener(this);
        btnPackage.setOnClickListener(this);
    }

    private void onNextButtonEnable() {
        lblNext.setTextColor(ContextCompat.getColor(mContext, R.color.JGGWhite));
        btnNext.setBackgroundResource(R.drawable.green_background);
        btnNext.setOnClickListener(this);
    }

    private void onNextButtonDissable() {
        lblNext.setTextColor(ContextCompat.getColor(mContext, R.color.JGGGrey2));
        btnNext.setBackgroundResource(R.drawable.grey_background);
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
        if (view.getId() == R.id.btn_post_service_one_time) {
            isOneTimeService = true;
            btnOneTime.setClickable(false);
            btnOneTime.setBackgroundResource(R.drawable.yellow_background);
            btnOneTime.setTextColor(ContextCompat.getColor(mContext, R.color.JGGBlack));
            btnPackage.setVisibility(View.GONE);
            lblPriceRange.setVisibility(View.VISIBLE);
            btnFixedAmount.setVisibility(View.VISIBLE);
            btnFrom.setVisibility(View.VISIBLE);
        } else if (view.getId() == R.id.btn_post_service_fixed_amount) {
            isFixedAmount = true;
            btnFixedAmount.setClickable(false);
            btnFixedAmount.setBackgroundResource(R.drawable.yellow_background);
            btnFixedAmount.setTextColor(ContextCompat.getColor(mContext, R.color.JGGBlack));
            btnFrom.setVisibility(View.GONE);
            txtFromMin.setVisibility(View.GONE);
            txtFromMax.setVisibility(View.GONE);
            txtFixedAmount.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
        } else if (view.getId() == R.id.btn_post_service_From) {
            isFromAmount = true;
            btnFrom.setClickable(false);
            btnFrom.setBackgroundResource(R.drawable.yellow_background);
            btnFrom.setTextColor(ContextCompat.getColor(mContext, R.color.JGGBlack));
            btnFixedAmount.setVisibility(View.GONE);
            txtFixedAmount.setVisibility(View.GONE);
            txtFromMin.setVisibility(View.VISIBLE);
            txtFromMax.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
        } else if (view.getId() == R.id.btn_post_service_package) {
            isOneTimeService = false;
            btnPackage.setClickable(false);
            btnOneTime.setVisibility(View.GONE);
            btnPackage.setBackgroundResource(R.drawable.yellow_background);
            btnPackage.setTextColor(ContextCompat.getColor(mContext, R.color.JGGBlack));
            lblPackageNum.setVisibility(View.VISIBLE);
            txtPackageNum.setVisibility(View.VISIBLE);
            lblPackageAmount.setVisibility(View.VISIBLE);
            txtPackageAmount.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
        } else if (view.getId() == R.id.btn_post_service_price_next) {
            if (isOneTimeService) {
                if (isFixedAmount) {
                    listener.onNextButtonClick();
                } else if (isFromAmount) {
                    listener.onNextButtonClick();
                }
            } else {
                listener.onNextButtonClick();
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (isOneTimeService) {
            if (isFixedAmount) {
                if (txtFixedAmount.length() > 0) onNextButtonEnable();
                else onNextButtonDissable();
            } else if (isFromAmount) {
                if (txtFromMin.length() > 0 && txtFromMax.length() > 0) onNextButtonEnable();
                else onNextButtonDissable();
            }
        } else {
            if (txtPackageNum.length() > 0 && txtPackageAmount.length() > 0) onNextButtonEnable();
            else onNextButtonDissable();
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
