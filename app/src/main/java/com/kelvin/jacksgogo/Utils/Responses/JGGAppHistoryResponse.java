package com.kelvin.jacksgogo.Utils.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentHistory;

import java.util.ArrayList;

public class JGGAppHistoryResponse extends JGGBaseResponse {

    @SerializedName("Value")
    @Expose
    private ArrayList<JGGAppointmentHistory> Value;

    public ArrayList<JGGAppointmentHistory> getValue() {
        return Value;
    }

    public void setValue(ArrayList<JGGAppointmentHistory> value) {
        Value = value;
    }
}
