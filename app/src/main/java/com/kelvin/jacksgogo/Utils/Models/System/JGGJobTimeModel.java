package com.kelvin.jacksgogo.Utils.Models.System;

import java.util.Date;

/**
 * Created by PUMA on 1/18/2018.
 */

public class JGGJobTimeModel {

    private Date JobStartOn;
    private Date JobEndOn;
    private Boolean IsSpecific;
    private int Peoples;

    public Date getJobStartOn() {
        return JobStartOn;
    }

    public void setJobStartOn(Date jobStartOn) {
        JobStartOn = jobStartOn;
    }

    public Date getJobEndOn() {
        return JobEndOn;
    }

    public void setJobEndOn(Date jobEndOn) {
        JobEndOn = jobEndOn;
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
