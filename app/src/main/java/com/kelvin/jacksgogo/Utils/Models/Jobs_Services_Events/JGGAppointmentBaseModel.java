package com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events;

import com.google.gson.annotations.SerializedName;
import com.kelvin.jacksgogo.Utils.Models.System.JGGAddressModel;
import com.kelvin.jacksgogo.Utils.Models.System.JGGCurrencyModel;
import com.kelvin.jacksgogo.Utils.Models.System.JGGRegionModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserProfileModel;

import java.util.Date;

/**
 * Created by PUMA on 1/18/2018.
 */

public class JGGAppointmentBaseModel {

    private String Title;
    private String Description;
    private String Tags;
    private String RegionID;
    private JGGRegionModel Region;
    private JGGCurrencyModel Currency;
    private String CurrencyCode;
    private String UserProfileID;
    private JGGUserProfileModel UserProfile;
    private Date PostOn;
    private int Status = 0;
    private JGGAddressModel Address;
    private JGGAddressModel DAddress;
    private AppointmentType type;

    public enum AppointmentStatus {
        NONE,
        PENDING,
        WORKINPROGRESS,
        REJECTED,
        CANCELLED,
        WITHDRAWN,
        COMPLETED,
        WAITINGFORREVIEW
    }

    public enum AppointmentType {
        JOBS,
        SERVICES,
        EVENT,
        GOCLUB,
        UNKNOWN
    }

    public JGGAppointmentBaseModel() {
        super();

        type = AppointmentType.UNKNOWN;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getTags() {
        return Tags;
    }

    public void setTags(String tags) {
        Tags = tags;
    }

    public String getRegionID() {
        return RegionID;
    }

    public void setRegionID(String regionID) {
        RegionID = regionID;
    }

    public JGGRegionModel getRegion() {
        return Region;
    }

    public void setRegion(JGGRegionModel region) {
        Region = region;
    }

    public JGGCurrencyModel getCurrency() {
        return Currency;
    }

    public void setCurrency(JGGCurrencyModel currency) {
        Currency = currency;
    }

    public String getCurrencyCode() {
        return CurrencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        CurrencyCode = currencyCode;
    }

    public String getUserProfileID() {
        return UserProfileID;
    }

    public void setUserProfileID(String userProfileID) {
        UserProfileID = userProfileID;
    }

    public JGGUserProfileModel getUserProfile() {
        return UserProfile;
    }

    public void setUserProfile(JGGUserProfileModel userProfile) {
        UserProfile = userProfile;
    }

    public Date getPostOn() {
        return PostOn;
    }

    public void setPostOn(Date postOn) {
        PostOn = postOn;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public JGGAddressModel getAddress() {
        return Address;
    }

    public void setAddress(JGGAddressModel address) {
        Address = address;
    }

    public JGGAddressModel getDAddress() {
        return DAddress;
    }

    public void setDAddress(JGGAddressModel DAddress) {
        this.DAddress = DAddress;
    }

    public AppointmentType getType() {
        return type;
    }

    public void setType(AppointmentType type) {
        this.type = type;
    }
}
