package com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events;

import com.kelvin.jacksgogo.Utils.Models.System.JGGAddressModel;
import com.kelvin.jacksgogo.Utils.Models.System.JGGCurrencyModel;
import com.kelvin.jacksgogo.Utils.Models.System.JGGRegionModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserProfileModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by PUMA on 1/18/2018.
 */

public class JGGAppointmentBaseModel {

    private String Title;
    private String Description;
    private String Tags;
    private String RegionID;
    private JGGRegionModel Region = new JGGRegionModel();
    private JGGCurrencyModel Currency = new JGGCurrencyModel();
    private String CurrencyCode;
    private String UserProfileID;
    private JGGUserProfileModel UserProfile = new JGGUserProfileModel();
    private String PostOn;  // Originally date type
    private int Status = 0;
    private JGGAddressModel Address = new JGGAddressModel();
    private JGGAddressModel DAddress = new JGGAddressModel();
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

    public String getPostOn() {
        return PostOn;
    }

    public void setPostOn(String postOn) {
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

    public static String appointmentDay(Date date) {
        if (date != null) {
            SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
            String day = dayFormat.format(date);
            return day;
        }
        return null;
    }

    public static String appointmentMonth(Date date) {
        if (date != null) {
            SimpleDateFormat monthFormat = new SimpleDateFormat("MMM");
            String month = monthFormat.format(date);
            return month;
        }
        return null;
    }

    public static Date appointmentDate(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        try {

            Date date = formatter.parse(dateString);
            return date;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
