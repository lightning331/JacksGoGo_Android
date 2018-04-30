package com.kelvin.jacksgogo.Utils.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kelvin.jacksgogo.Utils.Models.User.JGGGoClubUserModel;

import java.util.ArrayList;

public class JGGGoclubusersResponse extends JGGBaseResponse {

    @SerializedName("Value")
    @Expose
    private ArrayList<JGGGoClubUserModel> Value;

    public ArrayList<JGGGoClubUserModel> getValue() {
        return Value;
    }

    public void setValue(ArrayList<JGGGoClubUserModel> value) {
        Value = value;
    }
}
