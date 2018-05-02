package com.kelvin.jacksgogo.Utils.API;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.kelvin.jacksgogo.Utils.Prefs.JGGSharedPrefs;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by PUMA on 1/6/2018.
 */

public class JGGURLManager {

    private static Retrofit retrofit = null;

    //private static final String BASE_URL = "http://192.168.0.30:50370/";
    private static final String BASE_URL = "http://www.meridians2.com/JGGDev/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static Retrofit getClient() {
        if (retrofit==null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(
                    new HttpLoggingInterceptor.Logger() {
                        @Override
                        public void log(String message) {

                            Log.e("OkHttp", message);
                            //Utilities.appendLog(message);
                        }
                    });
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass, Context mContext) {

        String authToken = "Bearer " + JGGSharedPrefs.getInstance(mContext).getToken();

        if (!TextUtils.isEmpty(authToken)) {
            JGGAuthenticationInterceptor interceptor =
                    new JGGAuthenticationInterceptor(authToken);
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(
                    new HttpLoggingInterceptor.Logger() {
                        @Override
                        public void log(String message) {

                            Log.e("OkHttp", message);
                            //Utilities.appendLog(message);
                        }
                    });
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor)
                    .addInterceptor(logging);

                builder.client(httpClient.build());
                retrofit = builder.build();
            }
        }

        return retrofit.create(serviceClass);
    }
}
