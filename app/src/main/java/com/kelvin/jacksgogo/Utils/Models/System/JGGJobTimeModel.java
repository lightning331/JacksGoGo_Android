package com.kelvin.jacksgogo.Utils.Models.System;

import java.util.Date;

/**
 * Created by PUMA on 1/18/2018.
 */

public class JGGJobTimeModel {

    private Date StartOn;
    private Date EndOn;
    private Boolean IsSpecific;
    private int Peoples;

    public Date getStartOn() {
        return StartOn;
    }

    public void setStartOn(Date startOn) {
        StartOn = startOn;
    }

    public Date getEndOn() {
        return EndOn;
    }

    public void setEndOn(Date endOn) {
        EndOn = endOn;
    }

    public Boolean isSpecific() {
        return IsSpecific;
    }

    public void setSpecific(Boolean specific) {
        IsSpecific = specific;
    }

    public int getPeoples() {
        return Peoples;
    }

    public void setPeoples(int peoples) {
        Peoples = peoples;
    }
}
