package com.kelvin.jacksgogo.Activities.Profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.Responses.JGGBaseResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGTokenResponse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUpEmailActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private LinearLayout btnBack;
    private EditText txtEmail;
    private EditText txtPassword;
    private EditText txtConfirmPassword;
    private LinearLayout btnSignUp;
    private TextView lblSignUp;
    private LinearLayout btnSignUpFacebook;

    private ProgressDialog progressDialog;
    private String strEmail;
    private String strPassword;
    private String regionID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_email);
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            regionID = extra.getString("SELECTED_REGION_ID");
        }
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
        Call<JGGBaseResponse> signUpCall = signInManager.accountSignUp(strEmail, strPassword, regionID);
        signUpCall.enqueue(new Callback<JGGBaseResponse>() {
            @Override
            public void onResponse(Call<JGGBaseResponse> call, Response<JGGBaseResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {

                        Retrofit retrofit = JGGURLManager.getClient();
                        JGGAPIManager apiManager = retrofit.create(JGGAPIManager.class);
                        Call<JGGTokenResponse> tokenCall = apiManager.authTocken(strEmail, strPassword, "password");
                        tokenCall.enqueue(new Callback<JGGTokenResponse>() {
                            @Override
                            public void onResponse(Call<JGGTokenResponse> call, Response<JGGTokenResponse> response) {
                                if (response.isSuccessful()) {
                                    
                                    String access_token = response.body().getAccess_token();
                                    Long expire_in = response.body().getExpires_in();

                                    JGGAppManager.getInstance(SignUpEmailActivity.this).saveToken(access_token, expire_in);
                                    JGGAppManager.getInstance(SignUpEmailActivity.this).saveUser(strEmail, strPassword);

                                    onShowPhoneVerify();
                                } else {
                                    progressDialog.dismiss();
                                    int statusCode  = response.code();
                                    Toast.makeText(SignUpEmailActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<JGGTokenResponse> call, Throwable t) {
                                Toast.makeText(SignUpEmailActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        });
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
                Log.d("SignUpEmailActivity", t.getMessage());
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
