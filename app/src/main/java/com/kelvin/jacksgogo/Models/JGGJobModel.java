package com.kelvin.jacksgogo.Models;

import java.util.Date;

/**
 * Created by PUMA on 10/28/2017.
 */

public class JGGJobModel extends JGGAppointmentBaseModel {

    String jobTitle;

    public JGGJobModel(Date appointmentDate, String title, AppointmentStatus status, String comment, Integer badgeNumber) {
        super(appointmentDate, title, status, comment, badgeNumber);
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
}
