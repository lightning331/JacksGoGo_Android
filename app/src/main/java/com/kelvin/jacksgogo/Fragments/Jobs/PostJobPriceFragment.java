package com.kelvin.jacksgogo.Fragments.Jobs;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

public class PostJobPriceFragment extends Fragment implements View.OnClickListener, TextWatcher {

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    private TextView btnNoLimit;
    private TextView btnFixed;
    private TextView btnFrom;
    private TextView lblResponding;
    private EditText txtFixed;
    private EditText txtFromMin;
    private EditText txtFromMax;
    private RelativeLayout btnNext;
    private TextView lblNext;

    private boolean noLimit;
    private boolean fixed;
    private boolean from;

    public PostJobPriceFragment() {
        // Required empty public constructor
    }

    public static PostJobPriceFragment newInstance(String param1, String param2) {
        PostJobPriceFragment fragment = new PostJobPriceFragment();
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
        View view = inflater.inflate(R.layout.fragment_post_job_budget, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        btnNoLimit = view.findViewById(R.id.btn_post_job_no_limit);
        btnFixed = view.findViewById(R.id.btn_post_job_fixed_amount);
        btnFrom = view.findViewById(R.id.btn_post_job_From);
        lblResponding = view.findViewById(R.id.lbl_post_job_no_limit_desc);
        txtFixed = view.findViewById(R.id.txt_post_job_fixed_amount);
        txtFromMin = view.findViewById(R.id.txt_post_job_from_min);
        txtFromMax = view.findViewById(R.id.txt_post_job_from_max);
        btnNext = view.findViewById(R.id.btn_post_job_budget_next);
        lblNext = view.findViewById(R.id.lbl_post_job_budget_next);

        btnNoLimit.setOnClickListener(this);
        btnFixed.setOnClickListener(this);
        btnFrom.setOnClickListener(this);
    }

    private void onButtonClicked(TextView button) {
        button.setBackgroundResource(R.drawable.yellow_background);
        button.setTextColor(ContextCompat.getColor(mContext, R.color.JGGBlack));
        button.setClickable(false);
    }

    private void onNextButtonEnable() {
        lblNext.setTextColor(ContextCompat.getColor(mContext, R.color.JGGWhite));
        btnNext.setBackgroundResource(R.drawable.cyan_background);
        btnNext.setOnClickListener(this);
    }

    private void onNextButtonDissable() {
        lblNext.setTextColor(ContextCompat.getColor(mContext, R.color.JGGGrey2));
        btnNext.setBackgroundResource(R.drawable.grey_background);
        btnNext.setClickable(false);
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
        if (view.getId() == R.id.btn_post_job_no_limit) {
            onButtonClicked(btnNoLimit);
            noLimit = true;
            btnFixed.setVisibility(View.GONE);
            btnFrom.setVisibility(View.GONE);
            lblResponding.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
            onNextButtonEnable();
        } else if (view.getId() == R.id.btn_post_job_fixed_amount) {
            onButtonClicked(btnFixed);
            btnNoLimit.setVisibility(View.GONE);
            btnFrom.setVisibility(View.GONE);
            txtFixed.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
            txtFixed.addTextChangedListener(this);
        } else if (view.getId() == R.id.btn_post_job_From) {
            onButtonClicked(btnFrom );
            btnNoLimit.setVisibility(View.GONE);
            btnFixed.setVisibility(View.GONE);
            txtFromMin.setVisibility(View.VISIBLE);
            txtFromMax.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
            txtFromMin.addTextChangedListener(this);
            txtFromMax.addTextChangedListener(this);
        } else if (view.getId() == R.id.btn_post_job_budget_next) {
            if (noLimit) listener.onNextButtonClick("No Limit", null, null);
            else if (fixed) listener.onNextButtonClick("Fixed", txtFixed.getText().toString(), null);
            else if (from) listener.onNextButtonClick("From", txtFromMin.getText().toString(), txtFromMax.getText().toString());
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (txtFixed.length() > 0) {
            onNextButtonEnable();
            fixed = true;
        }
        else if (txtFromMin.length() > 0 && txtFromMax.length() > 0) {
            onNextButtonEnable();
            from = true;
        }
        else {
            onNextButtonDissable();
            noLimit = false; fixed = false;
            if (txtFromMin.length() > 0 && txtFromMax.length() > 0) from = false;
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onNextButtonClick(String type, String min, String max);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
