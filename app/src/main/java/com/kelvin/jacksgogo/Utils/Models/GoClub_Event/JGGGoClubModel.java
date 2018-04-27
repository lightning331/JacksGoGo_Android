package com.kelvin.jacksgogo.Utils.Models.GoClub_Event;

import com.kelvin.jacksgogo.Utils.Global.ClubStatus;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;
import com.kelvin.jacksgogo.Utils.Models.System.JGGRegionModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGGoClubUserModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserProfileModel;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;

/**
 * Created by storm on 4/24/2018.
 */

public class JGGGoClubModel {
    private String ID;
    private String UserProfileID;
    private String RegionID;
    private String CategoryID;
    private String Name;
    private String Description;
    private String Tags;
    private boolean IsSole;
    private Integer Limit;
    private Integer Status;
    private String CreatedOn;
    private ArrayList<String> AttachmentURLs = new ArrayList<>();
    private ArrayList<JGGGoClubUserModel> ClubUsers = new ArrayList<>();
    private ArrayList<JGGEventModel> Events = new ArrayList<>();
    private ArrayList<String> UserProfileIDs = new ArrayList<>();

    private JGGCategoryModel Category = new JGGCategoryModel();
    private JGGUserProfileModel UserProfile = new JGGUserProfileModel();
    private JGGRegionModel Region = new JGGRegionModel();

    private ArrayList<JGGUserProfileModel> Users = new ArrayList<>();

    // temp data
    private ArrayList<AlbumFile> albumFiles;

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

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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

    public boolean isSole() {
        return IsSole;
    }

    public void setSole(boolean sole) {
        IsSole = sole;
    }

    public Integer getLimit() {
        return Limit;
    }

    public void setLimit(Integer limit) {
        Limit = limit;
    }

    public ClubStatus getStatus() {
        return ClubStatus.valueOf(Status);
    }

    public void setStatus(ClubStatus status) {
        Status = status.getValue();
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public ArrayList<String> getAttachmentURLs() {
        return AttachmentURLs;
    }

    public void setAttachmentURLs(ArrayList<String> attachmentURLs) {
        AttachmentURLs = attachmentURLs;
    }

    public ArrayList<JGGGoClubUserModel> getClubUsers() {
        return ClubUsers;
    }

    public void setClubUsers(ArrayList<JGGGoClubUserModel> users) {
        ClubUsers = users;
    }

    public ArrayList<JGGEventModel> getEvents() {
        return Events;
    }

    public void setEvents(ArrayList<JGGEventModel> events) {
        Events = events;
    }

    public ArrayList<String> getUserProfileIDs() {
        return UserProfileIDs;
    }

    public void setUserProfileIDs(ArrayList<String> userProfileIDs) {
        UserProfileIDs = userProfileIDs;
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

    public ArrayList<AlbumFile> getAlbumFiles() {
        return albumFiles;
    }

    public void setAlbumFiles(ArrayList<AlbumFile> albumFiles) {
        this.albumFiles = albumFiles;
    }

    public ArrayList<JGGUserProfileModel> getUsers() {
        return Users;
    }

    public void setUsers(ArrayList<JGGUserProfileModel> users) {
        Users = users;
    }
}
