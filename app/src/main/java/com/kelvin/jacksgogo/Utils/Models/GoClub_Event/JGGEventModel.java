package com.kelvin.jacksgogo.Utils.Models.GoClub_Event;

import com.kelvin.jacksgogo.Utils.Global.TimeSlotSelectionStatus;
import com.kelvin.jacksgogo.Utils.Global.EventStatus;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;
import com.kelvin.jacksgogo.Utils.Models.System.JGGAddressModel;
import com.kelvin.jacksgogo.Utils.Models.System.JGGRegionModel;
import com.kelvin.jacksgogo.Utils.Models.System.JGGTimeSlotModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserProfileModel;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;

public class JGGEventModel {

    private String ID;
    private String UserProfileID;
    private String RegionID;
    private String CategoryID;
    private String ClubID;
    private String Title;
    private String Description;
    private String Tags;
    private Integer Limit;
    private Boolean IsOnetime;
    private Boolean IsFree;
    private Double BudgetFrom;
    private Double BudgetTo;
    private Double Budget;
    private String CurrencyCode;
    private String CreatedOn;
    private Integer Status;
    private JGGAddressModel Address;
    private ArrayList<String> AttachmentURLs = new ArrayList<>();
    private ArrayList<JGGTimeSlotModel> Sessions = new ArrayList<>();

    private JGGCategoryModel Category = new JGGCategoryModel();
    private JGGUserProfileModel UserProfile = new JGGUserProfileModel();
    private JGGRegionModel Region = new JGGRegionModel();

    // Dummy data
    private ArrayList<JGGUserProfileModel> Users = new ArrayList<>();
    private ArrayList<AlbumFile> albumFiles;
    private Integer timeSlotType;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUserProfileID() {
        return UserProfileID;
    }

    public void setUserProfileID(String userProfileID) {
        UserProfileID = userProfileID;
    }

    public String getRegionID() {
        return RegionID;
    }

    public void setRegionID(String regionID) {
        RegionID = regionID;
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }

    public String getClubID() {
        return ClubID;
    }

    public void setClubID(String clubID) {
        ClubID = clubID;
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

    public Integer getLimit() {
        return Limit;
    }

    public void setLimit(Integer limit) {
        Limit = limit;
    }

    public Boolean getOnetime() {
        return IsOnetime;
    }

    public void setOnetime(Boolean onetime) {
        IsOnetime = onetime;
    }

    public Boolean getFree() {
        return IsFree;
    }

    public void setFree(Boolean free) {
        IsFree = free;
    }

    public Double getBudgetFrom() {
        return BudgetFrom;
    }

    public void setBudgetFrom(Double budgetFrom) {
        BudgetFrom = budgetFrom;
    }

    public Double getBudgetTo() {
        return BudgetTo;
    }

    public void setBudgetTo(Double budgetTo) {
        BudgetTo = budgetTo;
    }

    public Double getBudget() {
        return Budget;
    }

    public void setBudget(Double budget) {
        Budget = budget;
    }

    public String getCurrencyCode() {
        return CurrencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        CurrencyCode = currencyCode;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public EventStatus getStatus() {
        return EventStatus.valueOf(Status);
    }

    public void setStatus(EventStatus status) {
        Status = status.getValue();
    }

    public JGGAddressModel getAddress() {
        return Address;
    }

    public void setAddress(JGGAddressModel address) {
        Address = address;
    }

    public ArrayList<String> getAttachmentURLs() {
        return AttachmentURLs;
    }

    public void setAttachmentURLs(ArrayList<String> attachmentURLs) {
        AttachmentURLs = attachmentURLs;
    }

    public ArrayList<JGGTimeSlotModel> getSessions() {
        return Sessions;
    }

    public void setSessions(ArrayList<JGGTimeSlotModel> sessions) {
        Sessions = sessions;
    }

    public ArrayList<JGGUserProfileModel> getUsers() {
        return Users;
    }

    public void setUsers(ArrayList<JGGUserProfileModel> users) {
        Users = users;
    }

    public ArrayList<AlbumFile> getAlbumFiles() {
        return albumFiles;
    }

    public void setAlbumFiles(ArrayList<AlbumFile> albumFiles) {
        this.albumFiles = albumFiles;
    }


    public TimeSlotSelectionStatus getTimeSlotType() {
        return TimeSlotSelectionStatus.valueOf(timeSlotType);
    }

    public void setTimeSlotType(TimeSlotSelectionStatus timeSlotType) {
        this.timeSlotType = timeSlotType.getValue();
    }

    public JGGCategoryModel getCategory() {
        return Category;
    }

    public void setCategory(JGGCategoryModel category) {
        Category = category;
    }

    public JGGUserProfileModel getUserProfile() {
        return UserProfile;
    }

    public void setUserProfile(JGGUserProfileModel userProfile) {
        UserProfile = userProfile;
    }

    public JGGRegionModel getRegion() {
        return Region;
    }

    public void setRegion(JGGRegionModel region) {
        Region = region;
    }
}
