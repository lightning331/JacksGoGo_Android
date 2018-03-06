package com.kelvin.jacksgogo.Utils.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserProfileModel;

import java.util.ArrayList;

/**
 * Created by PUMA on 3/6/2018.
 */

public class JGGInviteUsersResponse extends JGGBaseResponse {

    @SerializedName("Value")
    @Expose
    private ArrayList<JGGUserProfileModel> Value;

    public ArrayList<JGGUserProfileModel> getValue() {
        return Value;
    }

    public void setValue(ArrayList<JGGUserProfileModel> value) {
        Value = value;
    }
}
