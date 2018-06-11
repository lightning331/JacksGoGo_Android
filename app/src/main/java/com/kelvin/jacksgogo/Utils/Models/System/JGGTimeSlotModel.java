package com.kelvin.jacksgogo.Utils.Models.System;

import com.kelvin.jacksgogo.Utils.Global.JGGTimeSlotBookedStatus;

import java.util.Date;

import static com.kelvin.jacksgogo.Utils.JGGTimeManager.appointmentMonthDate;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.convertCalendarDate;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.convertUTCTimeString;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.convertUTCTimeToLocalTime;

/**
 * Created by PUMA on 1/18/2018.
 */

public class JGGTimeSlotModel {

    private String ID;
    private String StartOn;
    private String EndOn;
    private Boolean IsSpecific;
    private Integer Peoples;
    private Integer Status;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Date getStartOn() {
        return convertUTCTimeToLocalTime(StartOn);
    }

    public void setStartOn(String startOn) {
        StartOn = convertUTCTimeString(startOn);
    }

    public void setStartOn(String startOn, Boolean isLocal) {
        if (isLocal) {
            Date localTime = appointmentMonthDate(startOn);
            StartOn = convertUTCTimeString(localTime);
        } else {
            setStartOn(startOn);
        }
    }

    public Date getEndOn() {
        return convertUTCTimeToLocalTime(EndOn);
    }

    public void setEndOn(String endOn) {
        EndOn = convertUTCTimeString(endOn);
    }

    public void setEndOn(String endOn, Boolean isLocal) {
        if (isLocal) {
            Date localTime = appointmentMonthDate(endOn);
            EndOn = convertUTCTimeString(localTime);
        } else {
            setEndOn(endOn);
        }
    }

    public Integer getPeoples() {
        return Peoples;
    }

    public void setPeoples(Integer peoples) {
        Peoples = peoples;
    }

    public void setSpecific(Boolean specific) {
        IsSpecific = specific;
    }

    public Boolean getSpecific() {
        return IsSpecific;
    }

    public JGGTimeSlotBookedStatus getStatus() {
        return JGGTimeSlotBookedStatus.valueOf(Status);
    }

    public void setStatus(JGGTimeSlotBookedStatus status) {
        Status = status.getValue();
    }

    public boolean isEqualSlotDate (Date date) {
        Date slotDate = appointmentMonthDate(StartOn);
        String slotDateStr = convertCalendarDate(slotDate);
        String dateStr = convertCalendarDate(date);
        if (slotDateStr.equals(dateStr)) {
            return true;
        }
        return false;
    }
}
