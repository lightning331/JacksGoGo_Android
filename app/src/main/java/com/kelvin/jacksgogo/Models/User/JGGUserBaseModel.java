package com.kelvin.jacksgogo.Models.User;

/**
 * Created by PUMA on 11/3/2017.
 */

public class JGGUserBaseModel {

    private String id;
    private String username;
    private String fullname;
    private int avatarUrl;
    private Float rate = 0.0f;

    public JGGUserBaseModel() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(int avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }
}
