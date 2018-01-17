package com.kelvin.jacksgogo.Utils.API;

import com.kelvin.jacksgogo.Utils.Responses.JGGBaseResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGCategoryResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGRegionResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGTokenResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGUserBaseResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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


    @GET("api/Region/GetRegions")
    Call<JGGRegionResponse> getRegions();

    @FormUrlEncoded
    @POST("api/Account/Register")
    Call<JGGBaseResponse> accountSignUp(@Field("email") String email,
                                       @Field("password") String password);

    @FormUrlEncoded
    @POST("api/Account/AddPhoneNumber")
    Call<JGGBaseResponse> accountAddPhone(@Field("Number") String phoneNumber);

    @FormUrlEncoded
    @POST("api/Account/VerifyCode")
    Call<JGGUserBaseResponse> verifyPhoneNumber(@Field("Provider") String phoneNumber,
                                                @Field("Code") String code);

    @GET("api/Category/GetAllCategories")
    Call<JGGCategoryResponse> getCategory();
}
