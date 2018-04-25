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

import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.Prefs.JGGSharedPrefs;
import com.kelvin.jacksgogo.Utils.Responses.JGGBaseResponse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpEmailActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    @BindView(R.id.btn_sign_up_email_back) LinearLayout btnBack;
    @BindView(R.id.txt_sign_up_user_name) EditText txtUserName;
    @BindView(R.id.txt_sign_up_email) EditText txtEmail;
    @BindView(R.id.txt_sign_up_password) EditText txtPassword;
    @BindView(R.id.txt_sign_up_confirm_password) EditText txtConfirmPassword;
    @BindView(R.id.btn_sign_up) LinearLayout btnSignUp;
    @BindView(R.id.lbl_sign_up) TextView lblSignUp;
    @BindView(R.id.btn_sign_up_facebook) LinearLayout btnSignUpFacebook;

    private ProgressDialog progressDialog;
    private String strEmail;
    private String strPassword;
    private String regionID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_email);
        ButterKnife.bind(this);

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            regionID = extra.getString("SELECTED_REGION_ID");
        }
        initView();
    }

    private void initView() {
        this.txtEmail.addTextChangedListener(this);
        this.txtPassword.addTextChangedListener(this);
        this.txtConfirmPassword.addTextChangedListener(this);
        this.btnSignUpFacebook = findViewById(R.id.btn_sign_up_facebook);
        this.btnSignUpFacebook.setOnClickListener(this);
        this.btnBack.setOnClickListener(this);
    }

    private void signUp() {

        String pass = txtPassword.getText().toString();
        String cpass = txtConfirmPassword.getText().toString();
        if (!emailValidator(strEmail)) {
            Toast.makeText(this,"Invalid Email Address.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!pass.equals(cpass)){
            Toast.makeText(this,"Password Not matching",Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (strPassword.length() <= 5) {
                Toast.makeText(this,"Passwords must have at least 6 digit.",
                        Toast.LENGTH_SHORT).show();
                return;
            }
        }

        progressDialog = Global.createProgressDialog(this);

        JGGAPIManager signInManager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGBaseResponse> signUpCall = signInManager.accountSignUp(txtUserName.getText().toString(), strEmail, strPassword, regionID);
        signUpCall.enqueue(new Callback<JGGBaseResponse>() {
            @Override
            public void onResponse(Call<JGGBaseResponse> call, Response<JGGBaseResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {

                        JGGSharedPrefs.getInstance(SignUpEmailActivity.this).saveUser(txtUserName.getText().toString(), strEmail, strPassword);

                        onShowPhoneVerify();

                    } else {
                        Toast.makeText(SignUpEmailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    int statusCode  = response.code();
                    Toast.makeText(SignUpEmailActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGBaseResponse> call, Throwable t) {
                Toast.makeText(SignUpEmailActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    /**
     * validate email address format.
     */
    public static boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * validate password format.
     */
    public static boolean passwordValidator(String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\\\d)(?=.*[$@$!%*#?&])[A-Za-z\\\\d$@$!%*#?&]{6,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private void onShowPhoneVerify() {
        Intent intent = new Intent(this, SignUpPhoneActivity.class);
        startActivity(intent);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (txtEmail.length() > 0
                && txtPassword.length() > 0) {

            strEmail = txtEmail.getText().toString();
            strPassword = txtPassword.getText().toString();

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
            signUp();
            //onShowPhoneVerify();
        }
    }
}
