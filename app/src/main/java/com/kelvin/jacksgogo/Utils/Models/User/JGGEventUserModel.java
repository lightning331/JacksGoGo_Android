package com.kelvin.jacksgogo.Utils.Models.User;

import com.kelvin.jacksgogo.Utils.Global.EventUserStatus;

/**
 * Created by PUMA on 11/3/2017.
 */

public class JGGEventUserModel extends JGGUserBaseModel {

    private String ID;
    private String EventID;
    private String UserProfileID;
    private String RequestedOn;
    private String EnteredOn;
    private String Reason;
    private Integer Status;
    private JGGUserProfileModel UserProfile;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getEventID() {
        return EventID;
    }

    public void setEventID(String eventID) {
        EventID = eventID;
    }

    public String getUserProfileID() {
        return UserProfileID;
    }

    public void setUserProfileID(String userProfileID) {
        UserProfileID = userProfileID;
    }

    public String getRequestedOn() {
        return RequestedOn;
    }

    public void setRequestedOn(String requestedOn) {
        RequestedOn = requestedOn;
    }

    public String getEnteredOn() {
        return EnteredOn;
    }

    public void setEnteredOn(String enteredOn) {
        EnteredOn = enteredOn;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public EventUserStatus getStatus() {
        return EventUserStatus.valueOf(Status);
    }

    public void setStatus(EventUserStatus status) {
        Status = status.getValue();
    }

    public JGGUserProfileModel getUserProfile() {
        return UserProfile;
    }

    public void setUserProfile(JGGUserProfileModel userProfile) {
        UserProfile = userProfile;
    }
}
