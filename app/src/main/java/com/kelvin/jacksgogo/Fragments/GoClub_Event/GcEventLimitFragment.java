package com.kelvin.jacksgogo.Fragments.GoClub_Event;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.kelvin.jacksgogo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class GcEventLimitFragment extends Fragment implements TextWatcher {

    @BindView(R.id.btn_no_limit)                    Button btnNoLimits;
    @BindView(R.id.btn_limit)                       Button btnLimitTo;
    @BindView(R.id.edit_pax)                        EditText editPax;
    @BindView(R.id.btn_next)                        Button btnNext;

    private Context mContext;
    private boolean isLimit = false;

    public GcEventLimitFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gc_event_limit, container, false);
        ButterKnife.bind(this, view);

        initView();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    private void initView() {
        editPax.addTextChangedListener(this);

        editPax.setVisibility(View.GONE);
        btnNext.setClickable(false);
        btnNext.setVisibility(View.GONE);
    }

    private void onYellowButtonColor(Button button) {
        button.setBackgroundResource(R.drawable.yellow_background);
        button.setTextColor(ContextCompat.getColor(mContext, R.color.JGGBlack));
    }

    private void onPurpleButtonColor(Button button) {
        button.setBackgroundResource(R.drawable.purple_border_background);
        button.setTextColor(ContextCompat.getColor(mContext, R.color.JGGPurple));
    }

    @OnClick(R.id.btn_no_limit)
    public void onClickNoLimit() {
        this.listener.onNextButtonClick();
    }

    @OnClick(R.id.btn_limit)
    public void onClickLimitTo() {
        isLimit  = !isLimit;
        if (isLimit) {
            btnNoLimits.setVisibility(View.GONE);
            this.onYellowButtonColor(btnLimitTo);
            editPax.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
        } else {
            btnNoLimits.setVisibility(View.VISIBLE);
            this.onPurpleButtonColor(btnLimitTo);
            editPax.setVisibility(View.GONE);
            btnNext.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.btn_next)
    public void onClickNext() {
        this.listener.onNextButtonClick();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (editPax.length() > 0) {
            btnNext.setBackgroundResource(R.drawable.purple_background);
            btnNext.setTextColor(ContextCompat.getColor(mContext, R.color.JGGWhite));
            btnNext.setClickable(true);
        } else {
            btnNext.setBackgroundResource(R.drawable.grey_background);
            btnNext.setTextColor(ContextCompat.getColor(mContext, R.color.JGGGrey3));
            btnNext.setClickable(false);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

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
