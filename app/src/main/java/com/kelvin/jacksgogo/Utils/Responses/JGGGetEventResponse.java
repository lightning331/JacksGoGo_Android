package com.kelvin.jacksgogo.Utils.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kelvin.jacksgogo.Utils.Models.GoClub_Event.JGGEventModel;

import java.util.ArrayList;

public class JGGGetEventResponse extends JGGBaseResponse {

    @SerializedName("Value")
    @Expose
    private JGGEventModel Value;

    public JGGEventModel getValue() {
        return Value;
    }

    public void setValue(JGGEventModel value) {
        Value = value;
    }
}
