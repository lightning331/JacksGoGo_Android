package com.kelvin.jacksgogo.Utils.Models.System;

import java.util.Date;

/**
 * Created by PUMA on 1/18/2018.
 */

public class JGGTimeSlotModel {

    private String SessionStartOn;
    private String SessionEndOn;
    private Boolean IsSpecific;
    private Integer Peoples;

    public String getSessionStartOn() {
        return SessionStartOn;
    }

    public void setSessionStartOn(String sessionStartOn) {
        SessionStartOn = sessionStartOn;
    }

    public String getSessionEndOn() {
        return SessionEndOn;
    }

    public void setSessionEndOn(String sessionEndOn) {
        SessionEndOn = sessionEndOn;
    }

    public Integer getPeoples() {
        return Peoples;
    }

    public void setPeoples(Integer peoples) {
        Peoples = peoples;
    }

    public Boolean isSpecific() {
        return IsSpecific;
    }

    public void setSpecific(Boolean specific) {
        IsSpecific = specific;
    }
}
