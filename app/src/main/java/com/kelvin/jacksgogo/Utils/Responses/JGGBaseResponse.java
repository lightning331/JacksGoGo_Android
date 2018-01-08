package com.kelvin.jacksgogo.Utils.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by PUMA on 1/6/2018.
 */

public class JGGBaseResponse {
    @SerializedName("Success")
    @Expose
    protected Boolean Success;

    @SerializedName("Message")
    @Expose
    protected String Message;

    public Boolean getSuccess() {
        return Success;
    }

    public void setSuccess(Boolean success) {
        Success = success;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
