package com.kelvin.jacksgogo.Fragments.Profile;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGAppManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserBaseModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGTokenResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGUserBaseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
    private JGGAPIManager tokenService;

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

        txtEmail.setText("rose.lim@jgg.co");
        txtPassword.setText("abc123Q!@#");

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
        } else if (view.getId() == R.id.btn_alert_cancel) {
            alertDialog.dismiss();
        } else if (view.getId() == R.id.btn_alert_ok) {
            alertDialog.dismiss();
        }
    }

    private void SignIn() {
        progressDialog = Global.createProgressDialog(mContext);

        Retrofit retrofit = JGGURLManager.getClient();
        tokenService = retrofit.create(JGGAPIManager.class);
        Call<JGGTokenResponse> call = tokenService.oauthTocken(strEmail, strPassword, "password");
        call.enqueue(new Callback<JGGTokenResponse>() {
            @Override
            public void onResponse(Call<JGGTokenResponse> call, Response<JGGTokenResponse> response) {
                if (response.isSuccessful()) {

                    String access_token = response.body().getAccess_token();
                    Long expire_in = response.body().getExpires_in();

                    JGGAppManager.getInstance(mContext).saveToken(access_token, expire_in);

                    JGGAPIManager signInManager = JGGURLManager.createService(JGGAPIManager.class, mContext);
                    Call<JGGUserBaseResponse> loginCall = signInManager.accountLogin(strEmail, strPassword);
                    loginCall.enqueue(new Callback<JGGUserBaseResponse>() {
                        @Override
                        public void onResponse(Call<JGGUserBaseResponse> call, Response<JGGUserBaseResponse> response) {
                            progressDialog.dismiss();
                            if (response.isSuccessful()) {

                                JGGUserBaseModel user = response.body().getValue();
                                JGGAppManager.getInstance(mContext).currentUser = user;
                                JGGAppManager.getInstance(mContext).saveUser(strEmail, strPassword);

                                getActivity().getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.container, ProfileHomeFragment.newInstance())
                                        .commit();
                                // Refresh MainActivity
                                ((MainActivity)getActivity()).selectFragment();
                            } else {
                                int statusCode  = response.code();
                                Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<JGGUserBaseResponse> call, Throwable t) {
                            Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });

                } else {
                    progressDialog.dismiss();
                    int statusCode  = response.code();
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGTokenResponse> call, Throwable t) {
                Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void SignUp() {
        SignUpRegionFragment frag = SignUpRegionFragment.newInstance();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, frag, frag.getTag());
        ft.addToBackStack("SignUp");
        ft.commit();
    }

    private void onShowForgotPassDialog() {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
        LayoutInflater inflater = this.getLayoutInflater();

        View alertView = inflater.inflate(R.layout.jgg_forgot_password_view, null);
        builder.setView(alertView);
        alertDialog = builder.create();
        TextView cancelButton = (TextView) alertView.findViewById(R.id.btn_alert_cancel);
        TextView okButton = (TextView) alertView.findViewById(R.id.btn_alert_send);

        okButton.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGOrange));

        okButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    private void onShowAlertDialog() {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
        LayoutInflater inflater = this.getLayoutInflater();

        View alertView = inflater.inflate(R.layout.jgg_alert_view, null);
        builder.setView(alertView);
        alertDialog = builder.create();
        TextView cancelButton = (TextView) alertView.findViewById(R.id.btn_alert_cancel);
        TextView okButton = (TextView) alertView.findViewById(R.id.btn_alert_ok);
        TextView title = (TextView) alertView.findViewById(R.id.lbl_alert_titile);
        TextView desc = (TextView) alertView.findViewById(R.id.lbl_alert_description);

        title.setText(R.string.alert_email_sent_title);
        desc.setText(R.string.alert_email_sent_desc);
        okButton.setText(R.string.alert_ok);
        okButton.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGOrange));
        cancelButton.setVisibility(View.GONE);

        okButton.setOnClickListener(this);
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
