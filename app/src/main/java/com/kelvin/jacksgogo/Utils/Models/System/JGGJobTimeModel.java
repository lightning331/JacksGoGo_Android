package com.kelvin.jacksgogo.Utils.Models.System;

/**
 * Created by PUMA on 1/18/2018.
 */

public class JGGJobTimeModel {

    private String StartOn;
    private String EndOn;
    private Boolean IsSpecific;
    private int Peoples;

    public String getStartOn() {
        return StartOn;
    }

    public void setStartOn(String startOn) {
        StartOn = startOn;
    }

    public String getEndOn() {
        return EndOn;
    }

    public void setEndOn(String endOn) {
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
