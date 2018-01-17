package com.kelvin.jacksgogo.Utils.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;

import java.util.ArrayList;

/**
 * Created by PUMA on 1/17/2018.
 */

public class JGGCategoryResponse extends JGGBaseResponse {

    @SerializedName("Value")
    @Expose
    private ArrayList<JGGCategoryModel> Value;

    public ArrayList<JGGCategoryModel> getValue() {
        return Value;
    }

    public void setValue(ArrayList<JGGCategoryModel> value) {
        Value = value;
    }
}
