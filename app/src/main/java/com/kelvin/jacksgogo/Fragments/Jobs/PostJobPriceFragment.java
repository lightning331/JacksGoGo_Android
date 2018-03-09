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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGJobModel;

import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.creatingAppointment;

public class PostJobPriceFragment extends Fragment implements View.OnClickListener, TextWatcher {

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    private TextView btnNoLimit;
    private TextView btnFixed;
    private TextView btnFrom;
    private TextView lblResponding;
    private LinearLayout fixedLayout;
    private EditText txtFixed;
    private LinearLayout minLayout;
    private EditText txtFromMin;
    private LinearLayout maxLayout;
    private EditText txtFromMax;
    private RelativeLayout btnNext;
    private TextView lblNext;

    private JGGJobModel creatingJob;
    private int selectedPriceType = 0; // 0: None select, 1: No limit, 2: Fixed amount, 3: From amount
    private boolean nolimit;
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
        fixedLayout = view.findViewById(R.id.post_job_fixed_layout);
        txtFixed = view.findViewById(R.id.txt_post_job_fixed_amount);
        minLayout = view.findViewById(R.id.post_job_min_layout);
        txtFromMin = view.findViewById(R.id.txt_post_job_from_min);
        maxLayout = view.findViewById(R.id.post_job_max_layout);
        txtFromMax = view.findViewById(R.id.txt_post_job_from_max);
        btnNext = view.findViewById(R.id.btn_post_job_budget_next);
        lblNext = view.findViewById(R.id.lbl_post_job_budget_next);

        btnNoLimit.setOnClickListener(this);
        btnFixed.setOnClickListener(this);
        btnFrom.setOnClickListener(this);

        creatingJob = creatingAppointment;
        selectedPriceType = creatingJob.getSelectedServiceType();
        updateData();
    }

    private void updateData() {
        btnNoLimit.setVisibility(View.VISIBLE);
        btnFixed.setVisibility(View.VISIBLE);
        btnFrom.setVisibility(View.VISIBLE);
        btnNext.setVisibility(View.VISIBLE);
        onCyanButtonColor(btnNoLimit);
        onCyanButtonColor(btnFixed);
        onCyanButtonColor(btnFrom);
        fixedLayout.setVisibility(View.GONE);
        minLayout.setVisibility(View.GONE);
        maxLayout.setVisibility(View.GONE);
        lblResponding.setVisibility(View.GONE);
        switch (selectedPriceType) {
            case 0:
                btnNext.setVisibility(View.GONE);
                break;
            case 1:
                onYellowButtonColor(btnNoLimit);
                btnFixed.setVisibility(View.GONE);
                btnFrom.setVisibility(View.GONE);
                lblResponding.setVisibility(View.VISIBLE);
                onNextButtonEnable();
                break;
            case 2:
                onYellowButtonColor(btnFixed);
                btnNoLimit.setVisibility(View.GONE);
                btnFrom.setVisibility(View.GONE);
                fixedLayout.setVisibility(View.VISIBLE);
                if (creatingJob.getBudget() != null) {
                    txtFixed.setText(creatingJob.getBudget().toString());
                    fixed = true;
                    onNextButtonEnable();
                }
                txtFixed.addTextChangedListener(this);
                break;
            case 3:
                onYellowButtonColor(btnFrom);
                btnNoLimit.setVisibility(View.GONE);
                btnFixed.setVisibility(View.GONE);
                minLayout.setVisibility(View.VISIBLE);
                maxLayout.setVisibility(View.VISIBLE);
                if (creatingJob.getBudgetFrom() != null
                        && creatingJob.getBudgetTo() != null) {
                    txtFromMin.setText(creatingJob.getBudgetFrom().toString());
                    txtFromMax.setText(creatingJob.getBudgetTo().toString());
                    from = true;
                    onNextButtonEnable();
                }
                txtFromMin.addTextChangedListener(this);
                txtFromMax.addTextChangedListener(this);
                break;
            default:
                break;
        }
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_post_job_no_limit) {
            if (nolimit)
                selectedPriceType = 0;
            else
                selectedPriceType = 1;
            nolimit = !nolimit;
        } else if (view.getId() == R.id.btn_post_job_fixed_amount) {
            if (fixed)
                selectedPriceType = 0;
            else
                selectedPriceType = 2;
            fixed = !fixed;
        } else if (view.getId() == R.id.btn_post_job_From) {
            if (from)
                selectedPriceType = 0;
            else
                selectedPriceType = 3;
            from = !from;
        } else if (view.getId() == R.id.btn_post_job_budget_next) {
            if (selectedPriceType == 2)
                creatingJob.setBudget(Double.parseDouble(txtFixed.getText().toString()));
            else if (selectedPriceType == 3) {
                creatingJob.setBudgetFrom(Double.parseDouble(txtFromMin.getText().toString()));
                creatingJob.setBudgetTo(Double.parseDouble(txtFromMax.getText().toString()));
            }
            creatingJob.setSelectedServiceType(selectedPriceType);
            creatingAppointment = creatingJob;
            listener.onNextButtonClick();

            return;
        }
        updateData();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (selectedPriceType == 2) {
            if (txtFixed.length() > 0) onNextButtonEnable();
            else onNextButtonDisable();
        } else if (selectedPriceType == 3) {
            if (txtFromMin.length() > 0 && txtFromMax.length() > 0) onNextButtonEnable();
            else onNextButtonDisable();
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

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
