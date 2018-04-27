package com.kelvin.jacksgogo.Utils.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGJobInfoModel;

public class JGGGetJobInfoResponse extends JGGBaseResponse {

    @SerializedName("Value")
    @Expose
    private JGGJobInfoModel Value;

    public JGGJobInfoModel getValue() {
        return Value;
    }

    public void setValue(JGGJobInfoModel value) {
        Value = value;
    }
}
