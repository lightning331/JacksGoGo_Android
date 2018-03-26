package com.kelvin.jacksgogo.Utils.Models.User;

/**
 * Created by PUMA on 11/3/2017.
 */

public class JGGUserBaseModel {

    private String Id;
    private String Surname;
    private String GivenName;
    private Boolean Gender;
    private String Birthday;
    private String PhotoURL;
    private String Title;
    private String Overview;
    private Double Rate = 0.0;
    private Double Score = 0.0;
    private String CreatedOn;
    private String LastLoggedOn;
    private Boolean IsActive = false;
    private String Email;
    private Boolean EmailConfirmed = false;
    private String PhoneNumber;
    private Boolean PhoneNumberConfirmed = false;
    private Boolean TwoFactorEnabled = false;
    private int AccessFailedCount = 0;
    private String UserName;
    private String BusinessDetail;
    private String CredentialDetail;
    private String TagList;

    private int avatarUrl;
    private String fullName;

    public String getFullName() {
        return Surname + " " + GivenName;
    }

    public int getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(int avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getGivenName() {
        return GivenName;
    }

    public void setGivenName(String givenName) {
        GivenName = givenName;
    }

    public Boolean getGender() {
        return Gender;
    }

    public void setGender(Boolean gender) {
        Gender = gender;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getPhotoURL() {
        return PhotoURL;
    }

    public void setPhotoURL(String photoURL) {
        PhotoURL = photoURL;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getOverview() {
        return Overview;
    }

    public void setOverview(String overview) {
        Overview = overview;
    }

    public void setRate(Double rate) {
        Rate = rate;
    }

    public Double getScore() {
        return Score;
    }

    public void setScore(Double score) {
        Score = score;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public String getLastLoggedOn() {
        return LastLoggedOn;
    }

    public void setLastLoggedOn(String lastLoggedOn) {
        LastLoggedOn = lastLoggedOn;
    }

    public Boolean getActive() {
        return IsActive;
    }

    public void setActive(Boolean active) {
        IsActive = active;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Boolean getEmailConfirmed() {
        return EmailConfirmed;
    }

    public void setEmailConfirmed(Boolean emailConfirmed) {
        EmailConfirmed = emailConfirmed;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public Boolean getPhoneNumberConfirmed() {
        return PhoneNumberConfirmed;
    }

    public void setPhoneNumberConfirmed(Boolean phoneNumberConfirmed) {
        PhoneNumberConfirmed = phoneNumberConfirmed;
    }

    public Boolean getTwoFactorEnabled() {
        return TwoFactorEnabled;
    }

    public void setTwoFactorEnabled(Boolean twoFactorEnabled) {
        TwoFactorEnabled = twoFactorEnabled;
    }

    public int getAccessFailedCount() {
        return AccessFailedCount;
    }

    public void setAccessFailedCount(int accessFailedCount) {
        AccessFailedCount = accessFailedCount;
    }

    public Double getRate() {
        return Rate;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getBusinessDetail() {
        return BusinessDetail;
    }

    public void setBusinessDetail(String companyName) {
        BusinessDetail = companyName;
    }

    public String getCredentialDetail() {
        return CredentialDetail;
    }

    public void setCredentialDetail(String credentials) {
        CredentialDetail = credentials;
    }

    public String getTagList() {
        return TagList;
    }

    public void setTagList(String tagList) {
        TagList = tagList;
    }
}
