package com.kelvin.jacksgogo.Utils.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kelvin.jacksgogo.Utils.Models.User.JGGRegionModel;

import java.util.ArrayList;

/**
 * Created by PUMA on 1/8/2018.
 */

public class JGGRegionResponse extends JGGBaseResponse {

    @SerializedName("Value")
    @Expose
    private ArrayList<JGGRegionModel> Value;

    public ArrayList<JGGRegionModel> getValue() {
        return Value;
    }

    public void setValue(ArrayList<JGGRegionModel> value) {
        Value = value;
    }
}
