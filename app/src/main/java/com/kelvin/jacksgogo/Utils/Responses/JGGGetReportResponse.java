package com.kelvin.jacksgogo.Utils.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGReportResultModel;

/**
 * Created by PUMA on 1/25/2018.
 */

public class JGGGetReportResponse extends JGGBaseResponse {

    @SerializedName("Value")
    @Expose
    private JGGReportResultModel Value;

    public JGGReportResultModel getValue() {
        return Value;
    }

    public void setValue(JGGReportResultModel value) {
        Value = value;
    }
}
