package com.kelvin.jacksgogo.Utils.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentActivityModel;

import java.util.ArrayList;

public class JGGAppointmentActivityResponse extends JGGBaseResponse {

    @SerializedName("Value")
    @Expose
    private ArrayList<JGGAppointmentActivityModel> Value;

    public ArrayList<JGGAppointmentActivityModel> getValue() {
        return Value;
    }

    public void setValue(ArrayList<JGGAppointmentActivityModel> value) {
        Value = value;
    }
}
