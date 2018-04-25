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
import com.kelvin.jacksgogo.Utils.Global.JGGBudgetType;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;

import static com.kelvin.jacksgogo.Utils.Global.JGGBudgetType.fixed;
import static com.kelvin.jacksgogo.Utils.Global.JGGBudgetType.from;
import static com.kelvin.jacksgogo.Utils.Global.JGGBudgetType.no_limit;
import static com.kelvin.jacksgogo.Utils.Global.JGGBudgetType.none;

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

    private JGGAppointmentModel mJob;
    private JGGBudgetType budgetType;
    private boolean isNolimit;
    private boolean isFixed;
    private boolean isFrom;

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

        mJob = JGGAppManager.getInstance().getSelectedAppointment();
        budgetType = mJob.getBudgetType();
        if (budgetType == no_limit) isNolimit = true;
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
        switch (budgetType) {
            case none:
                btnNext.setVisibility(View.GONE);
                break;
            case no_limit:
                onYellowButtonColor(btnNoLimit);
                btnFixed.setVisibility(View.GONE);
                btnFrom.setVisibility(View.GONE);
                lblResponding.setVisibility(View.VISIBLE);
                onNextButtonEnable();
                break;
            case fixed:
                onYellowButtonColor(btnFixed);
                btnNoLimit.setVisibility(View.GONE);
                btnFrom.setVisibility(View.GONE);
                fixedLayout.setVisibility(View.VISIBLE);
                if (mJob.getBudget() != null) {
                    txtFixed.setText(mJob.getBudget().toString());
                    isFixed = true;
                    onNextButtonEnable();
                } else
                    onNextButtonDisable();
                txtFixed.addTextChangedListener(this);
                break;
            case from:
                onYellowButtonColor(btnFrom);
                btnNoLimit.setVisibility(View.GONE);
                btnFixed.setVisibility(View.GONE);
                minLayout.setVisibility(View.VISIBLE);
                maxLayout.setVisibility(View.VISIBLE);
                if (mJob.getBudgetFrom() != null
                        && mJob.getBudgetTo() != null) {
                    txtFromMin.setText(mJob.getBudgetFrom().toString());
                    txtFromMax.setText(mJob.getBudgetTo().toString());
                    isFrom = true;
                    onNextButtonEnable();
                } else
                    onNextButtonDisable();
                txtFromMin.addTextChangedListener(this);
                txtFromMax.addTextChangedListener(this);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_post_job_no_limit) {
            if (isNolimit)
                budgetType = none;
            else
                budgetType = no_limit;
            isNolimit = !isNolimit;
        } else if (view.getId() == R.id.btn_post_job_fixed_amount) {
            if (isFixed)
                budgetType = none;
            else
                budgetType = fixed;
            isFixed = !isFixed;
        } else if (view.getId() == R.id.btn_post_job_From) {
            if (isFrom)
                budgetType = none;
            else
                budgetType = from;
            isFrom = !isFrom;
        } else if (view.getId() == R.id.btn_post_job_budget_next) {
            if (budgetType == fixed)
                mJob.setBudget(Double.parseDouble(txtFixed.getText().toString()));
            else if (budgetType == from) {
                mJob.setBudgetFrom(Double.parseDouble(txtFromMin.getText().toString()));
                mJob.setBudgetTo(Double.parseDouble(txtFromMax.getText().toString()));
            } else if (budgetType == no_limit) {
                mJob.setBudget(null);   mJob.setBudgetFrom(null);   mJob.setBudgetTo(null);
            }
            mJob.setBudgetType(budgetType);

            JGGAppManager.getInstance().setSelectedAppointment(mJob);
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
        if (budgetType == fixed) {
            if (txtFixed.length() > 0) onNextButtonEnable();
            else onNextButtonDisable();
        } else if (budgetType == from) {
            if (txtFromMin.length() > 0 && txtFromMax.length() > 0) onNextButtonEnable();
            else onNextButtonDisable();
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
