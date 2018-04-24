package com.kelvin.jacksgogo.Fragments.Profile;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.Activities.MainActivity;
import com.kelvin.jacksgogo.Activities.Profile.SignUpPhoneActivity;
import com.kelvin.jacksgogo.CustomView.Views.JGGAlertView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserProfileModel;
import com.kelvin.jacksgogo.Utils.Prefs.JGGSharedPrefs;
import com.kelvin.jacksgogo.Utils.Responses.JGGUserProfileResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.JGGAppManager.currentUser;

public class SignInFragment extends Fragment implements View.OnClickListener, TextWatcher {

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    private EditText txtEmail;
    private EditText txtPassword;
    private ImageView btnForgotPassword;
    private LinearLayout btnSignIn;
    private TextView lblSignIn;
    private LinearLayout btnFacebook;
    private LinearLayout btnSignUp;

    private String strEmail;
    private String strPassword;
    private AlertDialog alertDialog;
    private ProgressDialog progressDialog;

    public SignInFragment() {
        // Required empty public constructor
    }

    public static SignInFragment newInstance() {
        SignInFragment fragment = new SignInFragment();
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
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        this.txtEmail = view.findViewById(R.id.txt_sign_in_email);
        this.txtEmail.addTextChangedListener(this);
        this.txtPassword = view.findViewById(R.id.txt_sign_in_password);
        this.txtPassword.addTextChangedListener(this);
        this.btnForgotPassword = view.findViewById(R.id.btn_forgot_password);
        this.btnForgotPassword.setOnClickListener(this);
        this.btnSignIn = view.findViewById(R.id.btn_sign_in);
        this.lblSignIn = view.findViewById(R.id.lbl_sign_in);
        this.btnFacebook = view.findViewById(R.id.btn_sign_in_facebook);
        this.btnFacebook.setOnClickListener(this);
        this.btnSignUp = view.findViewById(R.id.btn_sign_up);
        this.btnSignUp.setOnClickListener(this);

        txtEmail.setText("cristina.p@jgg.com");
        txtPassword.setText("Asd123!@#");

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_sign_up) {
            SignUp();
        } else if (view.getId() == R.id.btn_sign_in) {
            SignIn();
        } else if (view.getId() == R.id.btn_sign_in_facebook) {

        } else if (view.getId() == R.id.btn_forgot_password) {
            onShowForgotPassDialog();
        } else if (view.getId() == R.id.btn_alert_send) {
            alertDialog.dismiss();
            onShowAlertDialog();
        }
    }

    private void SignIn() {
        progressDialog = Global.createProgressDialog(mContext);

        JGGAPIManager signInManager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        Call<JGGUserProfileResponse> loginCall = signInManager.accountLogin(strEmail, strPassword);
        loginCall.enqueue(new Callback<JGGUserProfileResponse>() {
            @Override
            public void onResponse(Call<JGGUserProfileResponse> call, Response<JGGUserProfileResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {

                    if (response.body().getSuccess()) {

                        // Save Current User
                        JGGUserProfileModel user = response.body().getValue();
                        JGGSharedPrefs.getInstance(mContext).saveUser(user.getUser().getUserName(), user.getUser().getEmail(), strPassword);
                        currentUser = user;
                        // Save Access Token
                        String access_token = response.body().getToken().getAccess_token();
                        Long expire_in = response.body().getToken().getExpires_in();
                        JGGSharedPrefs.getInstance(mContext).saveToken(access_token, expire_in);

                        if (user.getUser().getPhoneNumberConfirmed()) {
                            loggedIn();
                        } else {
                            onShowPhoneVerifyDialog();
                        }
                    } else {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    int statusCode  = response.code();
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGUserProfileResponse> call, Throwable t) {
                Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void loggedIn() {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, ProfileHomeFragment.newInstance())
                .commit();
        // Refresh MainActivity
        ((MainActivity)getActivity()).selectFragment();
    }

    private void SignUp() {
        SignUpRegionFragment frag = SignUpRegionFragment.newInstance();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, frag, frag.getTag());
        ft.addToBackStack("SignUp");
        ft.commit();
    }

    private void onShowForgotPassDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = this.getLayoutInflater();

        View alertView = inflater.inflate(R.layout.jgg_forgot_password_view, null);
        builder.setView(alertView);
        alertDialog = builder.create();
        TextView cancelButton = (TextView) alertView.findViewById(R.id.btn_alert_cancel);
        TextView okButton = (TextView) alertView.findViewById(R.id.btn_alert_send);

        okButton.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGOrange));

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    private void onShowPhoneVerifyDialog() {
        JGGAlertView builder = new JGGAlertView(mContext,
                "Warning",
                "You have not verified account. Would you verify with your phone number?",
                false,
                mContext.getResources().getString(R.string.alert_cancel),
                R.color.JGGOrange,
                R.color.JGGOrange10Percent,
                mContext.getResources().getString(R.string.alert_ok),
                R.color.JGGOrange);
        alertDialog = builder.create();
        builder.setOnItemClickListener(new JGGAlertView.OnItemClickListener() {
            @Override
            public void onDoneButtonClick(View view) {
                if (view.getId() == R.id.btn_alert_cancel)
                    alertDialog.dismiss();
                else {
                    alertDialog.dismiss();
                    Intent intent = new Intent(mContext, SignUpPhoneActivity.class);
                    startActivity(intent);
                }
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    private void onShowAlertDialog() {
        JGGAlertView builder = new JGGAlertView(mContext,
                mContext.getResources().getString(R.string.alert_email_sent_title),
                mContext.getResources().getString(R.string.alert_email_sent_desc),
                false,
                "",
                R.color.JGGOrange,
                R.color.JGGOrange10Percent,
                mContext.getResources().getString(R.string.alert_ok),
                R.color.JGGOrange);
        alertDialog = builder.create();
        builder.setOnItemClickListener(new JGGAlertView.OnItemClickListener() {
            @Override
            public void onDoneButtonClick(View view) {
                if (view.getId() == R.id.btn_alert_cancel)
                    alertDialog.dismiss();
                else
                    alertDialog.dismiss();
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
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

            lblSignIn.setTextColor(ContextCompat.getColor(mContext, R.color.JGGWhite));
            btnSignIn.setBackgroundResource(R.drawable.orange_background);
            btnSignIn.setOnClickListener(this);
        } else {
            lblSignIn.setTextColor(ContextCompat.getColor(mContext, R.color.JGGGrey2));
            btnSignIn.setBackgroundResource(R.drawable.grey_background);
            btnSignIn.setOnClickListener(null);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public boolean validate() {
        boolean valid = true;

        String userEmail = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        if (userEmail.isEmpty()) {
            txtEmail.setError("please enter user email");
            valid = false;
        } else {
            strEmail = userEmail;
            txtEmail.setError(null);
        }

        if (password.isEmpty()) {
            txtPassword.setError("please enter password");
            valid = false;
        } else {
            strPassword = password;
            txtPassword.setError(null);
        }

        return valid;
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
