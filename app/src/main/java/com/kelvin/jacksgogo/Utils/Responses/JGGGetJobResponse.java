package com.kelvin.jacksgogo.Utils.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGJobModel;

import java.util.ArrayList;

/**
 * Created by PUMA on 3/2/2018.
 */

public class JGGGetJobResponse extends JGGBaseResponse {

    @SerializedName("Value")
    @Expose
    private ArrayList<JGGJobModel> Value;

    public ArrayList<JGGJobModel> getValue() {
        return Value;
    }

    public void setValue(ArrayList<JGGJobModel> value) {
        Value = value;
    }
}
