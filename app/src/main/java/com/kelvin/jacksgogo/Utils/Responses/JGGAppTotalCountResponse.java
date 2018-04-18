package com.kelvin.jacksgogo.Utils.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppTotalCountModel;

public class JGGAppTotalCountResponse extends JGGBaseResponse {

    @SerializedName("Value")
    @Expose
    private JGGAppTotalCountModel Value;
}
