package com.kelvin.jacksgogo.Utils.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;

import java.util.ArrayList;

/**
 * Created by PUMA on 3/2/2018.
 */

public class JGGGetAppsResponse extends JGGBaseResponse {

    @SerializedName("Value")
    @Expose
    private ArrayList<JGGAppointmentModel> Value;

    public ArrayList<JGGAppointmentModel> getValue() {
        return Value;
    }

    public void setValue(ArrayList<JGGAppointmentModel> value) {
        Value = value;
    }
}
