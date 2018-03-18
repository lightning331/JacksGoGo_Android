package com.kelvin.jacksgogo.Utils.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGJobModel;

/**
 * Created by PUMA on 3/19/2018.
 */

public class JGGGetAppResponse extends JGGBaseResponse {

    @SerializedName("Value")
    @Expose
    private JGGJobModel Value;

    public JGGJobModel getValue() {
        return Value;
    }

    public void setValue(JGGJobModel value) {
        Value = value;
    }
}
