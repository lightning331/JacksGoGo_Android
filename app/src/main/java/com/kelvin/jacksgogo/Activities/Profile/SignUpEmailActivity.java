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

import com.kelvin.jacksgogo.R;

public class SignUpEmailActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private LinearLayout btnBack;
    private EditText txtEmail;
    private EditText txtPassword;
    private EditText txtConfirmPassword;
    private LinearLayout btnSignUp;
    private TextView lblSignUp;
    private LinearLayout btnSignUpFacebook;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_email);

        initView();
    }

    private void initView() {
        this.txtEmail = findViewById(R.id.txt_sign_up_email);
        this.txtEmail.addTextChangedListener(this);
        this.txtPassword = findViewById(R.id.txt_sign_up_password);
        this.txtPassword.addTextChangedListener(this);
        this.txtConfirmPassword = findViewById(R.id.txt_sign_up_confirm_password);
        this.txtConfirmPassword.addTextChangedListener(this);
        this.btnSignUp = findViewById(R.id.btn_sign_up);
        this.lblSignUp = findViewById(R.id.lbl_sign_up);
        this.btnSignUpFacebook = findViewById(R.id.btn_sign_up_facebook);
        this.btnSignUpFacebook.setOnClickListener(this);
        this.btnBack = findViewById(R.id.btn_sign_up_email_back);
        this.btnBack.setOnClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (txtEmail.length() > 0
                && txtPassword.length() > 0) {

            lblSignUp.setTextColor(ContextCompat.getColor(this, R.color.JGGWhite));
            btnSignUp.setBackgroundResource(R.drawable.orange_background);
            btnSignUp.setOnClickListener(this);
        } else {
            lblSignUp.setTextColor(ContextCompat.getColor(this, R.color.JGGGrey2));
            btnSignUp.setBackgroundResource(R.drawable.grey_background);
            btnSignUp.setOnClickListener(null);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btn_sign_up_email_back) {
            this.finish();
        } else if (view.getId() == R.id.btn_sign_up) {
            Intent intent = new Intent(this, SignUpPhoneActivity.class);
            startActivity(intent);
        }
    }
}
