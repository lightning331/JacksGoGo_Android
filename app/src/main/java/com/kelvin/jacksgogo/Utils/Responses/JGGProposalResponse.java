package com.kelvin.jacksgogo.Utils.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel;

import java.util.ArrayList;

/**
 * Created by PUMA on 3/7/2018.
 */

public class JGGProposalResponse extends JGGBaseResponse {

    @SerializedName("Value")
    @Expose
    private ArrayList<JGGProposalModel> Value;

    public ArrayList<JGGProposalModel> getValue() {
        return Value;
    }

    public void setValue(ArrayList<JGGProposalModel> value) {
        Value = value;
    }
}
