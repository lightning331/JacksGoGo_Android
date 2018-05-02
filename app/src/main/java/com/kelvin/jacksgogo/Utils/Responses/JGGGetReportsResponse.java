package com.kelvin.jacksgogo.Utils.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGReportResultModel;

import java.util.ArrayList;

public class JGGGetReportsResponse extends JGGBaseResponse {

    @SerializedName("Value")
    @Expose
    private ArrayList<JGGReportResultModel> Value;

    public ArrayList<JGGReportResultModel> getValue() {
        return Value;
    }

    public void setValue(ArrayList<JGGReportResultModel> value) {
        Value = value;
    }
}
