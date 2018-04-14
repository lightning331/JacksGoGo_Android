package com.kelvin.jacksgogo.Utils.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kelvin.jacksgogo.Utils.Models.System.JGGTokenModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserProfileModel;

/**
 * Created by PUMA on 1/25/2018.
 */

public class JGGUserProfileResponse extends JGGBaseResponse {

    @SerializedName("Value")
    @Expose
    private JGGUserProfileModel Value;

    @SerializedName("Token")
    @Expose
    private JGGTokenModel Token;

    public JGGUserProfileModel getValue() {
        return Value;
    }

    public void setValue(JGGUserProfileModel value) {
        Value = value;
    }

    public JGGTokenModel getToken() {
        return Token;
    }

    public void setToken(JGGTokenModel token) {
        Token = token;
    }
}
