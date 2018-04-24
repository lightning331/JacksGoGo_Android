package com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events;

public class JGGReportResultModel {

    private String ID;
    private String AppointmentID;
    private String GeoData;
    private String PinCode;
    private String BeforePhotoURL;
    private String AfterPhotoURL;
    private String BeforeComment;
    private String AfterComment;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getAppointmentID() {
        return AppointmentID;
    }

    public void setAppointmentID(String appointmentID) {
        AppointmentID = appointmentID;
    }

    public String getGeoData() {
        return GeoData;
    }

    public void setGeoData(String geoData) {
        GeoData = geoData;
    }

    public String getPinCode() {
        return PinCode;
    }

    public void setPinCode(String pinCode) {
        PinCode = pinCode;
    }

    public String getBeforePhotoURL() {
        return BeforePhotoURL;
    }

    public void setBeforePhotoURL(String beforePhotoURL) {
        BeforePhotoURL = beforePhotoURL;
    }

    public String getAfterPhotoURL() {
        return AfterPhotoURL;
    }

    public void setAfterPhotoURL(String afterPhotoURL) {
        AfterPhotoURL = afterPhotoURL;
    }

    public String getBeforeComment() {
        return BeforeComment;
    }

    public void setBeforeComment(String beforeComment) {
        BeforeComment = beforeComment;
    }

    public String getAfterComment() {
        return AfterComment;
    }

    public void setAfterComment(String afterComment) {
        AfterComment = afterComment;
    }
}
