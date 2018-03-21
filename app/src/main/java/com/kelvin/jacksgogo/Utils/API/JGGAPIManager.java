package com.kelvin.jacksgogo.Utils.API;

import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGQuotationModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGBaseResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGCategoryResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGGetAppResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGGetAppsResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGInviteUsersResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGPostAppResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGProposalResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGRegionResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGSendInviteResponse;
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
    Call<JGGUserProfileResponse> accountLogin(@Field("Email") String email,
                                              @Field("Password") String password);

    @POST("api/Account/Logout")
    Call<JGGBaseResponse> accountSignOut();

    @GET("api/Region/GetRegions")
    Call<JGGRegionResponse> getRegions();

    @FormUrlEncoded
    @POST("api/Account/Register")
    Call<JGGBaseResponse> accountSignUp(@Field("Email") String email,
                                        @Field("Password") String password,
                                        @Field("RegionID") String regionID);

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
     *  Appointment Job
     */
    @POST("api/Appointment/PostJob")
    Call<JGGPostAppResponse> postNewJob(@Body JGGAppointmentModel creatingJob);

    @POST("api/Appointment/EditJob")
    Call<JGGPostAppResponse> editJob(@Body JGGAppointmentModel editingJob);

    @GET("api/Appointment/DeleteJob")
    Call<JGGBaseResponse> deleteJob(@Query("JobID") String jobID,
                                    @Query("Reason") String reason);

    @FormUrlEncoded
    @POST("api/Appointment/SearchJob")
    Call<JGGGetAppsResponse> searchJob(@Field("RegionID") String regionID,
                                           @Field("UserProfileID") String userProfileID,
                                           @Field("Query") String query,
                                           @Field("CategoryID") String categoryID,
                                           @Field("Tag") Integer tag,
                                           @Field("PostedOn") Integer postedOn,
                                           @Field("Lat") String lat,
                                           @Field("Lon") String lon,
                                           @Field("Distance") String distance,
                                           @Field("PageIndex") String pageIndex,
                                           @Field("PageSize") Integer pageSize);

    /*
     *  Appointment Service
     */
    @POST("api/Appointment/PostService")
    Call<JGGPostAppResponse> postNewService(@Body JGGAppointmentModel creatingService);

    @POST("api/Appointment/EditService")
    Call<JGGPostAppResponse> editService(@Body JGGAppointmentModel editingService);

    @GET("api/Appointment/DeleteService")
    Call<JGGBaseResponse> deleteService(@Query("ServiceID") String serviceID);

    @FormUrlEncoded
    @POST("api/Appointment/SearchService")
    Call<JGGGetAppsResponse> searchService(@Field("RegionID") String regionID,
                                           @Field("UserProfileID") String userProfileID,
                                           @Field("Query") String query,
                                           @Field("CategoryID") String categoryID,
                                           @Field("Tag") Integer tag,
                                           @Field("PostedOn") Integer postedOn,
                                           @Field("Lat") String lat,
                                           @Field("Lon") String lon,
                                           @Field("Distance") String distance,
                                           @Field("PageIndex") String pageIndex,
                                           @Field("PageSize") Integer pageSize);

    /*
     *  Appointment Quotation
     */
    @POST("api/Appointment/SendQuotation")
    Call<JGGPostAppResponse> sendQuotation(@Body JGGQuotationModel quotation);

    /*
     *  Appointment
     */
    @GET("api/Appointment/GetPendingAppointments")
    Call<JGGGetAppsResponse> getPendingAppointments(@Query("UserProfileID") String userProfileID,
                                                    @Query("pageIndex") Integer pageIndex,
                                                    @Query("pageSize") Integer pageSize);

    @GET("api/Appointment/GetConfirmedAppointments")
    Call<JGGGetAppsResponse> getConfirmedAppointments(@Query("UserProfileID") String userProfileID,
                                                      @Query("pageIndex") Integer pageIndex,
                                                      @Query("pageSize") Integer pageSize);

    @GET("api/Appointment/GetAppointmentHistory")
    Call<JGGGetAppsResponse> getAppointmentHistory(@Query("UserProfileID") String userProfileID,
                                                   @Query("pageIndex") Integer pageIndex,
                                                   @Query("pageSize") Integer pageSize);

    @GET("api/Appointment/GetJobByID")
    Call<JGGGetAppResponse> getJobByID(@Query("JobID") String jobID);

    /*
     *  Proposal
     */
    @FormUrlEncoded
    @POST("api/Proposal/SendInvite")
    Call<JGGSendInviteResponse> sendInvite(@Field("AppointmentID") String appointmentID,
                                           @Field("UserProfileID") String userProfileID);

    @FormUrlEncoded
    @POST("api/Proposal/GetUsersForInvite")
    Call<JGGInviteUsersResponse> getUsersForInvite(@Field("CategoryID") String categoryID,
                                                   @Field("City") String city,
                                                   @Field("State") String state,
                                                   @Field("PostalCode") String postalCode,
                                                   @Field("pageIndex") Integer pageIndex,
                                                   @Field("pageSize") Integer pageSize);

    @GET("api/Proposal/GetProposalsByJob")
    Call<JGGProposalResponse> getProposalsByJob(@Query("JobID") String jobID,
                                                @Query("PageIndex") Integer pageIndex,
                                                @Query("PageSize") Integer pageSize);

    @POST("api/Proposal/PostProposal")
    Call<JGGPostAppResponse> postNewProposal(@Body JGGProposalModel proposal);

    @POST("api/Proposal/EditProposal")
    Call<JGGPostAppResponse> editProposal(@Body JGGProposalModel proposal);

    @GET("api/Proposal/DeleteProposal")
    Call<JGGBaseResponse> deleteProposal(@Query("ProposalID") String proposalID);
}
