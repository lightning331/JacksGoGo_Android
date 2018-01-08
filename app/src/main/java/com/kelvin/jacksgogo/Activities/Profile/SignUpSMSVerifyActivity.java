package com.kelvin.jacksgogo.Activities.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.MainActivity;
import com.kelvin.jacksgogo.R;

public class SignUpSMSVerifyActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private LinearLayout btnBack;
    private TextView lblDetectCount;
    private EditText txtOTP;
    private LinearLayout btnResendOTP;
    private LinearLayout btnSubmit;
    private TextView lblSubmit;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_smsverify);
        initView();

        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                lblDetectCount.setText(String.format("%02d s", millisUntilFinished / 1000));
            }

            public void onFinish() {
                lblDetectCount.setText("00 s");
            }

        }.start();
    }

    private void initView() {
        this.lblDetectCount = findViewById(R.id.lblsign_up_sms_detect_count);
        this.txtOTP = findViewById(R.id.txt_sign_up_sms_otp);
        this.txtOTP.addTextChangedListener(this);
        this.btnResendOTP = findViewById(R.id.btn_sign_up_sms_resend);
        this.btnResendOTP.setOnClickListener(this);
        this.lblSubmit = findViewById(R.id.lbl_sign_up_sms_submit);
        this.btnSubmit = findViewById(R.id.btn_sign_up_sms_submit);
        this.btnBack = findViewById(R.id.btn_sign_up_sms_back);
        this.btnBack.setOnClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (txtOTP.length() > 0) {
            lblSubmit.setTextColor(ContextCompat.getColor(this, R.color.JGGWhite));
            btnSubmit.setBackgroundResource(R.drawable.orange_background);
            btnSubmit.setOnClickListener(this);
        } else {
            lblSubmit.setTextColor(ContextCompat.getColor(this, R.color.JGGGrey2));
            btnSubmit.setBackgroundResource(R.drawable.grey_background);
            btnSubmit.setOnClickListener(null);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btn_sign_up_sms_back) {
            this.finish();
        } else if (view.getId() == R.id.btn_sign_up_sms_submit) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
