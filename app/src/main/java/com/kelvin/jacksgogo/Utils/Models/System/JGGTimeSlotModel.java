package com.kelvin.jacksgogo.Utils.Models.System;

import java.util.Date;

/**
 * Created by PUMA on 1/18/2018.
 */

public class JGGTimeSlotModel {

    private Date SessionStartOn;
    private Date SessionEndOn;
    private Integer Peoples;

    public Date getSessionStartOn() {
        return SessionStartOn;
    }

    public void setSessionStartOn(Date sessionStartOn) {
        SessionStartOn = sessionStartOn;
    }

    public Date getSessionEndOn() {
        return SessionEndOn;
    }

    public void setSessionEndOn(Date sessionEndOn) {
        SessionEndOn = sessionEndOn;
    }

    public Integer getPeoples() {
        return Peoples;
    }

    public void setPeoples(Integer peoples) {
        Peoples = peoples;
    }
}
