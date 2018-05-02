package com.kelvin.jacksgogo.Utils.API;

import com.kelvin.jacksgogo.Utils.Global.JGGDeviceType;
import com.kelvin.jacksgogo.Utils.Models.GoClub_Event.JGGGoClubModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGReportResultModel;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGQuotationModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserProfileModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGAppTotalCountResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGAppointmentActivityResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGBaseResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGCategoryResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGGetAppResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGGetAppsResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGGetContractResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGGetGoClubResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGGetGoClubsResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGGetJobInfoResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGGetReportResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGGetReportsResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGGoclubusersResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGInviteUsersResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGPostAppResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGProposalResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGRegionResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGSendInviteResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGUserProfileResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by PUMA on 1/6/2018.
 */

public interface JGGAPIManager {

    /*
     *  ACCOUNT
     */
    @FormUrlEncoded
    @POST("api/Account/Login")
    Call<JGGUserProfileResponse> accountLogin(@Field("Username") String username,
                                              @Field("Password") String password,
                                              @Field("DeviceType") JGGDeviceType DeviceType,
                                              @Field("DeviceAddress") String DeviceAddress,
                                              @Field("DeviceUUID") String DeviceUUID);

    @POST("api/Account/Logout")
    Call<JGGBaseResponse> accountSignOut();

    @GET("api/Region/GetRegions")
    Call<JGGRegionResponse> getRegions();

    @FormUrlEncoded
    @POST("api/Account/Register")
    Call<JGGBaseResponse> accountSignUp(@Field("Username") String username,
                                        @Field("Email") String email,
                                        @Field("Password") String password,
                                        @Field("RegionID") String regionID);

    @FormUrlEncoded
    @POST("api/Account/AddPhoneNumber")
    Call<JGGBaseResponse> accountAddPhone(@Field("Username") String username,
                                          @Field("Number") String phoneNumber);

    @FormUrlEncoded
    @POST("api/Account/VerifyCode")
    Call<JGGUserProfileResponse> verifyPhoneNumber(@Field("Username") String username,
                                                   @Field("Password") String password,
                                                   @Field("Provider") String provider,
                                                   @Field("Code") String code);

    /*
     *  System
     */
    @GET("api/Category/GetAllCategories")
    Call<JGGCategoryResponse> getCategory();

    @Multipart
    @POST("api/UpLoad/UploadAttachmentFile")
    Call<JGGPostAppResponse> uploadAttachmentFile(@Part MultipartBody.Part file);

    /*
     * User
     */
    @POST("api/User/EditProfile")
    Call<JGGUserProfileResponse> editProfile(@Body JGGUserProfileModel user);

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

    /*
     *  Appointment Service
     */
    @POST("api/Appointment/PostService")
    Call<JGGPostAppResponse> postNewService(@Body JGGAppointmentModel creatingService);

    @POST("api/Appointment/EditService")
    Call<JGGPostAppResponse> editService(@Body JGGAppointmentModel editingService);

    @GET("api/Appointment/DeleteService")
    Call<JGGBaseResponse> deleteService(@Query("ServiceID") String serviceID);

    @GET("api/Appointment/GetServicesByCategory")
    Call<JGGGetAppsResponse> getServicesByCategory(@Query("CategoryID") String categoryID,
                                                   @Query("PageIndex") Integer pageIndex,
                                                   @Query("PageSize") Integer pageSize);

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

    @GET("api/Appointment/GetWastedAppointments")
    Call<JGGGetAppsResponse> getWastedAppointments(@Query("UserProfileID") String userProfileID,
                                                   @Query("pageIndex") Integer pageIndex,
                                                   @Query("pageSize") Integer pageSize);

    @GET("api/Appointment/GetAppointmentHistory")
    Call<JGGAppointmentActivityResponse> getAppointmentActivities(@Query("AppointmentID") String appointmentID);

    @GET("api/Appointment/GetTotalAppointmentsCount")
    Call<JGGAppTotalCountResponse> getTotalAppointmentsCount(@Query("Hours") Integer hours);

    @GET("api/Appointment/GetJobByID")
    Call<JGGGetAppResponse> getJobByID(@Query("JobID") String jobID);

    @FormUrlEncoded
    @POST("api/Appointment/SearchAppointment")
    Call<JGGGetAppsResponse> searchAppointment(@Field("RegionID") String regionID,
                                               @Field("UserProfileID") String userProfileID,
                                               @Field("Query") String query,
                                               @Field("CategoryID") String categoryID,
                                               @Field("Tag") String tag,
                                               @Field("PostedOn") String postedOn,
                                               @Field("Lat") Double lat,
                                               @Field("Lon") Double lon,
                                               @Field("Distance") Double distance,
                                               @Field("IsNearBy") Boolean isNearBy,
                                               @Field("IsRequest") Boolean isRequest,
                                               @Field("PageIndex") Integer pageIndex,
                                               @Field("PageSize") Integer pageSize);

    @GET("api/Appointment/GetInformationOfAppointment")
    Call<JGGGetJobInfoResponse> getInformationOfAppointment(@Query("AppointmentID") String appointmentID);

    /*
     *  Proposal
     */
    @POST("api/Proposal/PostProposal")
    Call<JGGPostAppResponse> postNewProposal(@Body JGGProposalModel proposal);

    @POST("api/Proposal/EditProposal")
    Call<JGGPostAppResponse> editProposal(@Body JGGProposalModel proposal);

    @GET("api/Proposal/DeleteProposal")
    Call<JGGBaseResponse> deleteProposal(@Query("ProposalID") String proposalID);

    @POST("api/Proposal/AcceptInvite")
    Call<JGGPostAppResponse> acceptInvite(@Body JGGProposalModel proposal);

    @GET("api/Proposal/RejectInvite")
    Call<JGGBaseResponse> rejectInvite(@Query("ProposalID") String proposalID);

    @GET("api/Proposal/AcceptAward")
    Call<JGGPostAppResponse> acceptAward(@Query("AppointmentID") String appointmentID);

    @GET("api/Proposal/RejectAward")
    Call<JGGPostAppResponse> rejectAward(@Query("AppointmentID") String appointmentID);

    @GET("api/Proposal/GetProposalsByJob")
    Call<JGGProposalResponse> getProposalsByJob(@Query("JobID") String jobID,
                                                @Query("PageIndex") Integer pageIndex,
                                                @Query("PageSize") Integer pageSize);

    @GET("api/Proposal/GetProposalsByUser")
    Call<JGGProposalResponse> getProposalsByUser(@Query("UserProfileID") String profileID,
                                                 @Query("PageIndex") Integer pageIndex,
                                                 @Query("PageSize") Integer pageSize);
    @GET("api/Proposal/WithdrawProposal")
    Call<JGGProposalResponse> withdrawProposal(@Query("ProposalID") String proposalID);

    @GET("api/Proposal/GetProposedStatus")
    Call<JGGProposalResponse> getProposedStatus(@Query("JobID") String jobID,
                                                @Query("UserProfileID") String userProfileID);

    @GET("api/Proposal/RejectProposal")
    Call<JGGBaseResponse> rejectProposal(@Query("ProposalID") String proposalID);

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

    @FormUrlEncoded
    @POST("api/Proposal/ApproveProposal")
    Call<JGGPostAppResponse> approveProposal(@Field("AppointmentID") String appointmentID,
                                             @Field("ProposalID") String proposalID,
                                             @Field("GrossAmt") Double grossAmt,
                                             @Field("CurrencyCode") String postalCode);

    /*
     * Contract
     */
    @GET("api/Contract/StartContract")
    Call<JGGPostAppResponse> startContract(@Query("ContractID") String contractID);

    @GET("api/Contract/GetContractByAppointment")
    Call<JGGGetContractResponse> getContractByAppointment(@Query("AppointmentID") String appointmentID);

    /**
     *  Report
    **/
    @POST("api/Contract/ReportResult")
    Call<JGGPostAppResponse> reportResult(@Body JGGReportResultModel reportResultModel);

    @GET("api/Contract/GetReportByID")
    Call<JGGGetReportResponse> getReportByID(@Query("ReportID") String reportID);

    @GET("api/Contract/GetReportsByContract")
    Call<JGGGetReportsResponse> getReportsByContract(@Query("ContractID") String contractID);

    @GET("api/Contract/ApproveReport")
    Call<JGGPostAppResponse> approveReport(@Query("ReportID") String reportID);

    @GET("api/Contract/RejectReport")
    Call<JGGPostAppResponse> rejectReport(@Query("ReportID") String reportID);

    /*
     * GoClubs
     */
    @POST("api/Event/CreateClubs")
    Call<JGGPostAppResponse> createClubs(@Body JGGGoClubModel club);

    @POST("api/Event/EditClubs")
    Call<JGGPostAppResponse> editClubs(@Body JGGGoClubModel club);

    @FormUrlEncoded
    @POST("api/Event/SearchGoClub")
    Call<JGGGetGoClubsResponse> searchGoClub(@Field("Query") String query,
                                             @Field("CategoryID") String categoryID,
                                             @Field("Tag") String tag,
                                             @Field("PageIndex") Integer pageIndex,
                                             @Field("PageSize") Integer pageSize);

    @GET("api/Event/GetUsersByClub")
    Call<JGGGoclubusersResponse> getUsersByClub(@Query("ClubID") String clubID);

    @GET("api/Event/GetClubsByUser")
    Call<JGGGetGoClubsResponse> getClubsByUser(@Query("UserProfileID") String userProfileID,
                                               @Query("PageIndex") Integer pageIndex,
                                               @Query("PageSize") Integer pageSize);

    @GET("api/Event/GetClubByID")
    Call<JGGGetGoClubResponse> getClubByID(@Query("ClubID") String clubID);

    @FormUrlEncoded
    @POST("api/Event/LeaveGoClub")
    Call<JGGBaseResponse> leaveGoClub(@Field("ClubID") String clubID,
                                      @Field("UserProfileID") String userProfileID,
                                      @Field("Reason") String reason);

    @DELETE("api/Event/DeleteGoClub")
    Call<JGGBaseResponse> deleteGoClub(@Query("ClubID") String clubID);
}
