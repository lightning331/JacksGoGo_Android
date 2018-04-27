package com.kelvin.jacksgogo.Utils.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kelvin.jacksgogo.Utils.Models.GoClub_Event.JGGGoClubModel;

import java.util.ArrayList;

public class JGGGetGoClubResponse extends JGGBaseResponse {

    @SerializedName("Value")
    @Expose
    private ArrayList<JGGGoClubModel> Value;

    public ArrayList<JGGGoClubModel> getValue() {
        return Value;
    }

    public void setValue(ArrayList<JGGGoClubModel> value) {
        Value = value;
    }
}
