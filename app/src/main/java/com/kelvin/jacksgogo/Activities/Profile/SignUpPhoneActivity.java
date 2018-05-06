package com.kelvin.jacksgogo.Activities.Profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Prefs.JGGSharedPrefs;
import com.kelvin.jacksgogo.Utils.Responses.JGGBaseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.Global.createProgressDialog;

public class SignUpPhoneActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private LinearLayout btnBack;
    private CountryCodePicker ccp;
    private EditText txtPhone;
    private LinearLayout btnNext;
    private TextView lblNext;

    private ProgressDialog progressDialog;
    private String strPhoneNumber;

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

        // AutoDetectCountry enabled
        // Phone Number Auto Formatting
        ccp.registerCarrierNumberEditText(txtPhone);
        ccp.setNumberAutoFormattingEnabled(true);
    }

    private void sendSMS() {
        progressDialog = createProgressDialog(this);

        String username = JGGSharedPrefs.getInstance(this).getUsernamePassword()[2];

        JGGAPIManager signInManager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGBaseResponse> signUpCall = signInManager.accountAddPhone(username, strPhoneNumber);
        signUpCall.enqueue(new Callback<JGGBaseResponse>() {
            @Override
            public void onResponse(Call<JGGBaseResponse> call, Response<JGGBaseResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess())
                        onShowSMSVerify();
                    else
                        Toast.makeText(SignUpPhoneActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignUpPhoneActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGBaseResponse> call, Throwable t) {
                Toast.makeText(SignUpPhoneActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void onShowSMSVerify() {
        Intent intent = new Intent(this, SignUpSMSVerifyActivity.class);
        intent.putExtra("phone_number", strPhoneNumber);
        startActivity(intent);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (txtPhone.length() > 0) {
            strPhoneNumber = "+" + ccp.getSelectedCountryCode() + txtPhone.getText().toString();

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
            sendSMS();
            //onShowSMSVerify();
        }
    }
}
