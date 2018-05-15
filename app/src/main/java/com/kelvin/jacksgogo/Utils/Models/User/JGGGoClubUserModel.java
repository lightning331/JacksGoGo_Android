package com.kelvin.jacksgogo.Utils.Models.User;

import com.kelvin.jacksgogo.Utils.Global.EventUserStatus;
import com.kelvin.jacksgogo.Utils.Global.EventUserType;

public class JGGGoClubUserModel {

    private String ID;
    private String ClubID;
    private String UserProfileID;
    private Integer UserType;
    private Integer UserStatus;
    private String Reason;
    private String AddedOn;
    private JGGUserProfileModel UserProfile;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getClubID() {
        return ClubID;
    }

    public void setClubID(String clubID) {
        ClubID = clubID;
    }

    public String getUserProfileID() {
        return UserProfileID;
    }

    public void setUserProfileID(String userProfileID) {
        UserProfileID = userProfileID;
    }

    public EventUserType getUserType() {
        return EventUserType.valueOf(UserType);
    }

    public void setUserType(EventUserType userType) {
        UserType = userType.getValue();
    }

    public EventUserStatus getUserStatus() {
        return EventUserStatus.valueOf(UserStatus);
    }

    public void setUserStatus(EventUserStatus userStatus) {
        UserStatus = userStatus.getValue();
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public String getAddedOn() {
        return AddedOn;
    }

    public void setAddedOn(String addedOn) {
        AddedOn = addedOn;
    }

    public JGGUserProfileModel getUserProfile() {
        return UserProfile;
    }

    public void setUserProfile(JGGUserProfileModel userProfile) {
        UserProfile = userProfile;
    }
}
