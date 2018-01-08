package com.kelvin.jacksgogo.Utils.API;

import com.kelvin.jacksgogo.Utils.Responses.JGGUserBaseResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGTokenResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by PUMA on 1/6/2018.
 */

public interface JGGAPIManager {

    @FormUrlEncoded
    @POST("oauth/Token")
    Call<JGGTokenResponse> oauthTocken(@Field("username") String email,
                                       @Field("password") String password,
                                       @Field("grant_type") String grant_type);

    @FormUrlEncoded
    @POST("api/Account/Login")
    Call<JGGUserBaseResponse> accountLogin(@Field("email") String email,
                                           @Field("password") String password);

}
