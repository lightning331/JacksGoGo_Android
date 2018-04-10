package com.kelvin.jacksgogo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserProfileModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGRegionResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGTokenResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGUserProfileResponse;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.kelvin.jacksgogo.Utils.JGGAppManager.regions;

public class JGGSplashActivity extends AppCompatActivity {

    private int SPLASH_DISPLAY_DURATION = 500;
    private String strEmail;
    private String strPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        strEmail = JGGAppManager.getInstance(this).getUsernamePassword()[0];
        strPassword = JGGAppManager.getInstance(this).getUsernamePassword()[1];

    }

    @Override
    public void onResume(){
        super.onResume();

        loadRegions();
    }

    private void onShowMainActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(JGGSplashActivity.this, MainActivity.class);
                JGGSplashActivity.this.startActivity(mainIntent);
                JGGSplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_DURATION);
    }

    private void loadRegions() {
        JGGAPIManager regionManager = JGGURLManager.getClient().create(JGGAPIManager.class);
        Call<JGGRegionResponse> regionCall = regionManager.getRegions();
        regionCall.enqueue(new Callback<JGGRegionResponse>() {
            @Override
            public void onResponse(Call<JGGRegionResponse> call, Response<JGGRegionResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        regions = response.body().getValue();
                        autoAuthorize();
                    } else {
                        Toast.makeText(JGGSplashActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode  = response.code();
                    Toast.makeText(JGGSplashActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGRegionResponse> call, Throwable t) {
                Toast.makeText(JGGSplashActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void autoAuthorize() {
        String token = JGGAppManager.getInstance(this).getToken();
        if (strEmail.equals("") || strPassword.equals("")) {
            onShowMainActivity();
        } else {
            SPLASH_DISPLAY_DURATION = 100;
            if (token != null || !token.equals("")) {
                onAccountLogin();
            } else {
                onAuthToken();
            }
        }
    }

    private void onAuthToken() {
        Retrofit retrofit = JGGURLManager.getClient();
        JGGAPIManager apiManager = retrofit.create(JGGAPIManager.class);
        Call<JGGTokenResponse> call = apiManager.authTocken(strEmail, strPassword, "password");
        call.enqueue(new Callback<JGGTokenResponse>() {
            @Override
            public void onResponse(Call<JGGTokenResponse> call, Response<JGGTokenResponse> response) {
                if (response.isSuccessful()) {

                    String access_token = response.body().getAccess_token();
                    Long expire_in = response.body().getExpires_in();

                    JGGAppManager.getInstance(JGGSplashActivity.this).saveToken(access_token, expire_in);
                    onAccountLogin();

                } else {
                    int statusCode  = response.code();
                    Toast.makeText(JGGSplashActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGTokenResponse> call, Throwable t) {
                Toast.makeText(JGGSplashActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onAccountLogin() {
        JGGAPIManager signInManager = JGGURLManager.createService(JGGAPIManager.class, JGGSplashActivity.this);
        Call<JGGUserProfileResponse> loginCall = signInManager.accountLogin(strEmail, strPassword);
        loginCall.enqueue(new Callback<JGGUserProfileResponse>() {
            @Override
            public void onResponse(Call<JGGUserProfileResponse> call, Response<JGGUserProfileResponse> response) {
                if (response.isSuccessful()) {

                    JGGUserProfileModel user = response.body().getValue();
                    JGGAppManager.getInstance(JGGSplashActivity.this).currentUser = user;
                    onShowMainActivity();

                } else {
                    int statusCode  = response.code();
                    Toast.makeText(JGGSplashActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGUserProfileResponse> call, Throwable t) {
                Toast.makeText(JGGSplashActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
