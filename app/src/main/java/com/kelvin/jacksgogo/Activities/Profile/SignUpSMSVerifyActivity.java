package com.kelvin.jacksgogo.Activities.Profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.Activities.MainActivity;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGAppManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserProfileModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGBaseResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGUserProfileResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.Global.SIGNUP_FINISHED;
import static com.kelvin.jacksgogo.Utils.Global.createProgressDialog;

public class SignUpSMSVerifyActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private LinearLayout btnBack;
    private TextView lblDetectCount;
    private EditText txtOTP;
    private LinearLayout btnResendOTP;
    private LinearLayout btnSubmit;
    private TextView lblSubmit;

    private ProgressDialog progressDialog;
    private String strPhoneNumber;
    private String strOTP;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_smsverify);
        initView();

        strPhoneNumber = getIntent().getStringExtra("phone_number");

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

    private void reSendOTP() {
        progressDialog = createProgressDialog(this);

        JGGAPIManager signInManager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGBaseResponse> signUpCall = signInManager.accountAddPhone(strPhoneNumber);
        signUpCall.enqueue(new Callback<JGGBaseResponse>() {
            @Override
            public void onResponse(Call<JGGBaseResponse> call, Response<JGGBaseResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {

                    } else {
                        Toast.makeText(SignUpSMSVerifyActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode  = response.code();
                    Toast.makeText(SignUpSMSVerifyActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGBaseResponse> call, Throwable t) {
                Log.d("SignUpEmailActivity", t.getMessage());
                Toast.makeText(SignUpSMSVerifyActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void submit() {
        progressDialog = createProgressDialog(this);

        JGGAPIManager signInManager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGUserProfileResponse> signUpCall = signInManager.verifyPhoneNumber(strPhoneNumber, strOTP);
        signUpCall.enqueue(new Callback<JGGUserProfileResponse>() {
            @Override
            public void onResponse(Call<JGGUserProfileResponse> call, Response<JGGUserProfileResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        JGGUserProfileModel user = response.body().getValue();
                        JGGAppManager.getInstance(SignUpSMSVerifyActivity.this).currentUser = user;
                        onShowMainActivity();
                    } else {
                        Toast.makeText(SignUpSMSVerifyActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode  = response.code();
                    Toast.makeText(SignUpSMSVerifyActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGUserProfileResponse> call, Throwable t) {
                Log.d("SignUpSMSVerifyActivity", t.getMessage());
                Toast.makeText(SignUpSMSVerifyActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void onShowMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(SIGNUP_FINISHED, true);
        startActivity(intent);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (txtOTP.length() > 0) {
            strOTP = txtOTP.getText().toString();
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
        } else if (view.getId() == R.id.btn_sign_up_sms_resend) {
            reSendOTP();
        } else if (view.getId() == R.id.btn_sign_up_sms_submit) {
            //onShowMainActivity();
            submit();
        }
    }
}
