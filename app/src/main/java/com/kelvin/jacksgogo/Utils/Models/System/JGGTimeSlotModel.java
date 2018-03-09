package com.kelvin.jacksgogo.Utils.Models.System;

/**
 * Created by PUMA on 1/18/2018.
 */

public class JGGTimeSlotModel {

    private String StartOn;
    private String EndOn;
    private Boolean IsSpecific;
    private Integer Peoples;

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
