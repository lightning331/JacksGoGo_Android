package com.kelvin.jacksgogo.Utils.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserBaseModel;

/**
 * Created by PUMA on 1/6/2018.
 */

public class JGGUserBaseResponse extends JGGBaseResponse {
    @SerializedName("Value")
    @Expose
    private JGGUserBaseModel Value;

    public JGGUserBaseModel getValue() {
        return Value;
    }

    public void setValue(JGGUserBaseModel value) {
        Value = value;
    }
}
