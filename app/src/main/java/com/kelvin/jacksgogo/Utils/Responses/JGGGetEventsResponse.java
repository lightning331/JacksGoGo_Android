package com.kelvin.jacksgogo.Utils.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kelvin.jacksgogo.Utils.Models.GoClub_Event.JGGEventModel;

import java.util.ArrayList;

public class JGGGetEventsResponse extends JGGBaseResponse {

    @SerializedName("Value")
    @Expose
    private ArrayList<JGGEventModel> Value;

    public ArrayList<JGGEventModel> getValue() {
        return Value;
    }

    public void setValue(ArrayList<JGGEventModel> value) {
        Value = value;
    }
}
