package com.kelvin.jacksgogo.Utils.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGContractModel;

public class JGGGetContractResponse extends JGGBaseResponse {

    @SerializedName("Value")
    @Expose
    private JGGContractModel Value;

    public JGGContractModel getValue() {
        return Value;
    }

    public void setValue(JGGContractModel value) {
        Value = value;
    }
}
