package com.kelvin.jacksgogo.Utils.API;

import android.content.Context;
import android.text.TextUtils;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by PUMA on 1/6/2018.
 */

public class JGGURLManager {

    private static Retrofit retrofit = null;

//    private static final String BASE_URL = "http://192.168.0.30:50370/";
    private static final String BASE_URL = "http://www.meridians2.com/JGGDev/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
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

        String authToken = "Bearer " + JGGAppManager.getInstance(mContext).getToken();

        if (!TextUtils.isEmpty(authToken)) {
            JGGAuthenticationInterceptor interceptor =
                    new JGGAuthenticationInterceptor(authToken);

            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);

                builder.client(httpClient.build());
                retrofit = builder.build();
            }
        }

        return retrofit.create(serviceClass);
    }
}
