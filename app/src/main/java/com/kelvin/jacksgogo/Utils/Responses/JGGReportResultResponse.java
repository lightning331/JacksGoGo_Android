package com.kelvin.jacksgogo.Utils.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by PUMA on 1/25/2018.
 */

public class JGGReportResultResponse extends JGGBaseResponse {

    @SerializedName("Value")
    @Expose
    private String Value;

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }
}
