package com.kelvin.jacksgogo.Utils.Models.GoClub_Event;

import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;

import java.util.ArrayList;

/**
 * Created by storm on 4/24/2018.
 */

public class JGGGoclubModel {
    private String UserProfileID;
    private String RegionID;
    private String CategoryID;
    private String Name;
    private String Description;
    private String Tags;
    private boolean IsSole;
    private int Limit;
    private ArrayList<String> AttachmentURLs = new ArrayList<>();
    private ArrayList<String> UserProfileIDs = new ArrayList<>();

    private JGGCategoryModel Category = new JGGCategoryModel();

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

    public int getLimit() {
        return Limit;
    }

    public void setLimit(int limit) {
        Limit = limit;
    }

    public ArrayList<String> getAttachmentURLs() {
        return AttachmentURLs;
    }

    public void setAttachmentURLs(ArrayList<String> attachmentURLs) {
        AttachmentURLs = attachmentURLs;
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
}
