package com.kelvin.jacksgogo.Utils.Models.System;

import android.util.Log;

import com.kelvin.jacksgogo.Utils.Global.JGGTimeSlotBookedStatus;

import java.util.Date;

import static com.kelvin.jacksgogo.Utils.JGGTimeManager.appointmentMonthDate;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.convertCalendarDate;

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
        Log.d("date", slotDate.getYear() + " " + slotDate.getMonth() + " " + slotDate.getDay());
        String slotDateStr = convertCalendarDate(slotDate);
        String dateStr = convertCalendarDate(date);
        if (slotDateStr.equals(dateStr)) {
            return true;
        }
        return false;
    }
}
