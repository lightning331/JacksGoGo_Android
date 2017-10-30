package com.kelvin.jacksgogo.Models;

import java.util.Date;

/**
 * Created by PUMA on 10/28/2017.
 */

public class JGGEventModel extends JGGAppointmentBaseModel {


    Date start_Time;
    Date end_Time;

    public JGGEventModel(Date appointmentDate, String title, AppointmentStatus status, String comment, Integer badgeNumber) {
        super(appointmentDate, title, status, comment, badgeNumber);
    }

    public Date getStart_Time() {
        return start_Time;
    }

    public void setStart_Time(Date start_Time) {
        this.start_Time = start_Time;
    }

    public Date getEnd_Time() {
        return end_Time;
    }

    public void setEnd_Time(Date end_Time) {
        this.end_Time = end_Time;
    }

}
