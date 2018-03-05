package com.kelvin.jacksgogo.Utils.API;

import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGJobModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGBaseResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGCategoryResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGGetJobResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGPostJobResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGRegionResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGTokenResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGUserProfileResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by PUMA on 1/6/2018.
 */

public interface JGGAPIManager {

    /*
     *  ACCOUNT
     */
    @FormUrlEncoded
    @POST("oauth/Token")
    Call<JGGTokenResponse> authTocken(@Field("username") String email,
                                      @Field("password") String password,
                                      @Field("grant_type") String grant_type);

    @FormUrlEncoded
    @POST("api/Account/Login")
    Call<JGGUserProfileResponse> accountLogin(@Field("email") String email,
                                              @Field("password") String password);

    @POST("api/Account/Logout")
    Call<JGGBaseResponse> accountSignOut();

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
    Call<JGGUserProfileResponse> verifyPhoneNumber(@Field("Provider") String phoneNumber,
                                                @Field("Code") String code);

    /*
     *  System
     */
    @GET("api/Category/GetAllCategories")
    Call<JGGCategoryResponse> getCategory();

    /*
     *  Appointment
     */
    @POST("api/Appointment/PostJob")
    Call<JGGPostJobResponse> postNewJob(@Body JGGJobModel creatingJob);

    @GET("api/Appointment/DeleteJob")
    Call<JGGBaseResponse> deleteJob(@Query("ID") String jobID);

    @POST("api/Appointment/PostService")
    Call<JGGPostJobResponse> postNewService(@Body JGGJobModel creatingService);

    @GET("api/Appointment/DeleteJob")
    Call<JGGBaseResponse> deleteService(@Query("ID") String serviceID);

    @GET("api/Appointment/GetPendingAppointments")
    Call<JGGGetJobResponse> getPendingAppointments();

    @GET("api/Appointment/GetConfirmedAppointments")
    Call<JGGGetJobResponse> getConfirmedAppointments(@Query("ID") String userProfileID);

    @GET("api/Appointment/GetAppointmentHistory")
    Call<JGGGetJobResponse> getAppointmentHistory(@Query("ID") String userProfileID);
}
