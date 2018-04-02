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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global.JGGBudgetType;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;

import static com.kelvin.jacksgogo.Utils.JGGAppManager.selectedAppointment;
import static com.kelvin.jacksgogo.Utils.Global.JGGBudgetType.fixed;
import static com.kelvin.jacksgogo.Utils.Global.JGGBudgetType.from;
import static com.kelvin.jacksgogo.Utils.Global.JGGBudgetType.none;

public class PostServicePriceFragment extends Fragment implements View.OnClickListener, TextWatcher {

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    private TextView btnOneTime;
    private TextView lblPriceRange;
    private TextView btnFixedAmount;
    private LinearLayout fixedAmountLayout;
    private EditText txtFixedAmount;
    private TextView btnFrom;
    private LinearLayout fromMinLayout;
    private EditText txtFromMin;
    private LinearLayout fromMaxLayout;
    private EditText txtFromMax;
    private TextView btnPackage;
    private TextView lblPackageNum;
    private EditText txtPackageNum;
    private TextView lblPackageAmount;
    private LinearLayout packageAmountLayout;
    private EditText txtPackageAmount;
    private RelativeLayout btnNext;
    private TextView lblNext;

    private JGGAppointmentModel mService;
    private Integer serviceType;
    private JGGBudgetType budgetType;
    private boolean isOneTimeService;
    private boolean isPackageService;
    private boolean isFixedAmount;
    private boolean isFromAmount;

    public PostServicePriceFragment() {
        // Required empty public constructor
    }

    public static PostServicePriceFragment newInstance(String param1, String param2) {
        PostServicePriceFragment fragment = new PostServicePriceFragment();
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
        View view = inflater.inflate(R.layout.fragment_post_service_price, container, false);

        initView(view);
        updateData();

        return view;
    }

    private void initView(View view) {
        btnOneTime = view.findViewById(R.id.btn_post_service_one_time);
        lblPriceRange = view.findViewById(R.id.lbl_post_service_price_range);
        btnFixedAmount = view.findViewById(R.id.btn_post_service_fixed_amount);
        fixedAmountLayout = view.findViewById(R.id.post_service_fixed_layout);
        txtFixedAmount = view.findViewById(R.id.txt_post_service_fixed_amount);
        txtFixedAmount.addTextChangedListener(this);
        btnFrom = view.findViewById(R.id.btn_post_service_From);
        fromMinLayout = view.findViewById(R.id.post_service_min_layout);
        txtFromMin = view.findViewById(R.id.txt_post_service_from_min);
        txtFromMin.addTextChangedListener(this);
        fromMaxLayout = view.findViewById(R.id.post_service_max_layout);
        txtFromMax = view.findViewById(R.id.txt_post_service_from_max);
        txtFromMax.addTextChangedListener(this);
        btnPackage = view.findViewById(R.id.btn_post_service_package);
        lblPackageNum = view.findViewById(R.id.lbl_post_service_package_num);
        txtPackageNum = view.findViewById(R.id.txt_post_service_package_num);
        txtPackageNum.addTextChangedListener(this);
        packageAmountLayout = view.findViewById(R.id.post_service_package_amount_layout);
        lblPackageAmount = view.findViewById(R.id.lbl_post_service_package_amount);
        txtPackageAmount = view.findViewById(R.id.txt_post_service_package_amount);
        txtPackageAmount.addTextChangedListener(this);
        btnNext = view.findViewById(R.id.btn_post_service_price_next);
        lblNext = view.findViewById(R.id.lbl_post_service_price_next);
        btnOneTime.setOnClickListener(this);
        btnFixedAmount.setOnClickListener(this);
        btnFrom.setOnClickListener(this);
        btnPackage.setOnClickListener(this);

        mService = selectedAppointment;
        serviceType = mService.getAppointmentType();
        budgetType = mService.getBudgetType();
    }

    private void updateData() {
        btnOneTime.setVisibility(View.VISIBLE);
        btnPackage.setVisibility(View.VISIBLE);
        btnNext.setVisibility(View.GONE);
        onGreenButtonColor(btnOneTime);
        onGreenButtonColor(btnPackage);
        lblPriceRange.setVisibility(View.GONE);
        btnFixedAmount.setVisibility(View.GONE);
        fixedAmountLayout.setVisibility(View.GONE);
        btnFrom.setVisibility(View.GONE);
        fromMinLayout.setVisibility(View.GONE);
        fromMaxLayout.setVisibility(View.GONE);
        lblPackageNum.setVisibility(View.GONE);
        txtPackageNum.setVisibility(View.GONE);
        lblPackageAmount.setVisibility(View.GONE);
        packageAmountLayout.setVisibility(View.GONE);
        if (serviceType == null) {
            return;
        } else if (serviceType == 1) {     // One-time Service Budget
            onYellowButtonColor(btnOneTime);
            onGreenButtonColor(btnFixedAmount);
            onGreenButtonColor(btnFrom);
            btnPackage.setVisibility(View.GONE);
            btnFixedAmount.setVisibility(View.VISIBLE);
            btnFrom.setVisibility(View.VISIBLE);
            lblPriceRange.setVisibility(View.VISIBLE);
            switch (budgetType) {
                case fixed:     // Fixed Amount
                    onNextButtonDisable();
                    onYellowButtonColor(btnFixedAmount);
                    btnFrom.setVisibility(View.GONE);
                    fixedAmountLayout.setVisibility(View.VISIBLE);
                    if (mService.getBudget() != null) {
                        isOneTimeService = true;
                        isFixedAmount = true;
                        txtFixedAmount.setText(String.valueOf(mService.getBudget()));
                        onNextButtonEnable();
                    }
                    break;
                case from:     // From To Amount
                    onNextButtonDisable();
                    onYellowButtonColor(btnFrom);
                    btnFixedAmount.setVisibility(View.GONE);
                    fromMinLayout.setVisibility(View.VISIBLE);
                    fromMaxLayout.setVisibility(View.VISIBLE);
                    if (mService.getBudgetFrom() != null
                            && mService.getBudgetTo() != null) {
                        isOneTimeService = true;
                        isFromAmount = true;
                        txtFromMin.setText(String.valueOf(mService.getBudgetFrom()));
                        txtFromMax.setText(String.valueOf(mService.getBudgetTo()));
                        onNextButtonEnable();
                    }
                    break;
                default:
                    break;
            }
        } else if (serviceType >= 2) {       // Package Service Budget
            onNextButtonDisable();
            onYellowButtonColor(btnPackage);
            btnOneTime.setVisibility(View.GONE);
            lblPackageNum.setVisibility(View.VISIBLE);
            txtPackageNum.setVisibility(View.VISIBLE);
            lblPackageAmount.setVisibility(View.VISIBLE);
            packageAmountLayout.setVisibility(View.VISIBLE);
            if (mService.getBudget() != null) {
                isPackageService = true;
                txtPackageNum.setText(String.valueOf(mService.getAppointmentType()));
                txtPackageAmount.setText(String.valueOf(mService.getBudget()));
                onNextButtonEnable();
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_post_service_one_time) {
            if (isOneTimeService)
                serviceType = null;
            else
                serviceType = 1;
            isOneTimeService = !isOneTimeService;
        } else if (view.getId() == R.id.btn_post_service_package) {
            if (isPackageService)
                serviceType = null;
            else {
                serviceType = 2;
            }
            isPackageService = !isPackageService;
        }

        else if (view.getId() == R.id.btn_post_service_fixed_amount) {
            if (isFixedAmount)
                budgetType = none;
            else {
                budgetType = fixed;
            }
            isFixedAmount = !isFixedAmount;
        } else if (view.getId() == R.id.btn_post_service_From) {
            if (isFromAmount)
                budgetType = none;
            else {
                budgetType = from;
            }
            isFromAmount = !isFromAmount;
        } else if (view.getId() == R.id.btn_post_service_price_next) {
            onSaveCreatingService();
            return;
        }
        updateData();
    }

    private void onSaveCreatingService() {
        selectedAppointment.setBudgetFrom(null);
        selectedAppointment.setBudgetTo(null);
        selectedAppointment.setBudget(null);
        if (serviceType == 1) {     // One-time Service Budget
            if (budgetType == fixed) {
                mService.setBudget(Double.parseDouble(txtFixedAmount.getText().toString()));
            } else if (budgetType == from) {
                mService.setBudgetFrom(Double.parseDouble(txtFromMin.getText().toString()));
                mService.setBudgetTo(Double.parseDouble(txtFromMax.getText().toString()));
            }
        } else if (serviceType >= 2){       // Package Service Budget
            mService.setAppointmentType(Integer.parseInt(txtPackageNum.getText().toString()));
            mService.setBudget(Double.parseDouble(txtPackageAmount.getText().toString()));
        }
        mService.setBudgetType(budgetType);
        mService.setAppointmentType(serviceType);
        selectedAppointment = mService;
        listener.onNextButtonClick();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (serviceType == 1) {
            if (budgetType == fixed) {
                if (txtFixedAmount.length() > 0) onNextButtonEnable();
                else onNextButtonDisable();
            } else if (budgetType == from){
                if (txtFromMax.length() > 0 && txtFromMin.length() > 0) onNextButtonEnable();
                else onNextButtonDisable();
            }
        } else if (serviceType >= 2) {
            String packageNum = txtPackageNum.getText().toString();
            if (packageNum.length() > 0
                    && Integer.valueOf(packageNum) >= 2
                    && txtPackageAmount.length() > 0) {
                onNextButtonEnable();
            } else {
                if (packageNum.equals("")) {
                    txtPackageNum.setBackgroundResource(R.drawable.grey_border_background);
                } else if (Integer.valueOf(packageNum) < 2) {
                    Toast.makeText(mContext, "The number of service should be greater than 2!", Toast.LENGTH_LONG).show();
                    txtPackageNum.setBackgroundResource(R.drawable.red_border_background);
                    serviceType = 2;
                }
                onNextButtonDisable();
            }
        }
    }

    private void onYellowButtonColor(TextView button) {
        button.setBackgroundResource(R.drawable.yellow_background);
        button.setTextColor(ContextCompat.getColor(mContext, R.color.JGGBlack));
    }

    private void onGreenButtonColor(TextView button) {
        button.setBackgroundResource(R.drawable.green_border_background);
        button.setTextColor(ContextCompat.getColor(mContext, R.color.JGGGreen));
    }

    private void onNextButtonEnable() {
        btnNext.setVisibility(View.VISIBLE);
        lblNext.setTextColor(ContextCompat.getColor(mContext, R.color.JGGWhite));
        btnNext.setBackgroundResource(R.drawable.green_background);
        btnNext.setOnClickListener(this);
    }

    private void onNextButtonDisable() {
        btnNext.setVisibility(View.VISIBLE);
        lblNext.setTextColor(ContextCompat.getColor(mContext, R.color.JGGGrey2));
        btnNext.setBackgroundResource(R.drawable.grey_background);
        btnNext.setClickable(false);
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
