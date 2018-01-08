package com.kelvin.jacksgogo.Activities.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hbb20.CountryCodePicker;
import com.kelvin.jacksgogo.R;

public class SignUpPhoneActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private LinearLayout btnBack;
    private CountryCodePicker ccp;
    private EditText txtPhone;
    private LinearLayout btnNext;
    private TextView lblNext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_phone);
        initView();
    }

    private void initView() {
        this.txtPhone = findViewById(R.id.txt_sign_up_phone);
        this.txtPhone.addTextChangedListener(this);
        this.ccp = findViewById(R.id.ccp);
        this.btnBack = findViewById(R.id.btn_sign_up_phone_back);
        this.btnBack.setOnClickListener(this);
        this.btnNext = findViewById(R.id.btn_sign_up_phone_next);
        this.lblNext = findViewById(R.id.lbl_sign_up_phone_next);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (txtPhone.length() > 0) {
            lblNext.setTextColor(ContextCompat.getColor(this, R.color.JGGWhite));
            btnNext.setBackgroundResource(R.drawable.orange_background);
            btnNext.setOnClickListener(this);
        } else {
            lblNext.setTextColor(ContextCompat.getColor(this, R.color.JGGGrey2));
            btnNext.setBackgroundResource(R.drawable.grey_background);
            btnNext.setOnClickListener(null);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btn_sign_up_phone_back) {
            this.finish();
        } else if (view.getId() == R.id.btn_sign_up_phone_next) {
            Intent intent = new Intent(this, SignUpSMSVerifyActivity.class);
            startActivity(intent);
        }

    }
}
