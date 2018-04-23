package com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events;

/**
 * Created by PUMA on 1/17/2018.
 */

public class JGGCategoryModel {

    private String ID;
    private String Code;
    private String Name;
    private String Image;
    private boolean IsAbleService;
    private boolean IsAbleJob;
    private boolean IsAbleGoClub;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public boolean isAbleService() {
        return IsAbleService;
    }

    public void setAbleService(boolean ableService) {
        IsAbleService = ableService;
    }

    public boolean isAbleJobs() {
        return IsAbleJob;
    }

    public void setAbleJobs(boolean ableJobs) {
        IsAbleJob = ableJobs;
    }

    public boolean isAbleGoClub() {
        return IsAbleGoClub;
    }

    public void setAbleGoClub(boolean ableGoClub) {
        IsAbleGoClub = ableGoClub;
    }
}
