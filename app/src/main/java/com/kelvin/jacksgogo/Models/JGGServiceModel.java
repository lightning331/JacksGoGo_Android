package com.kelvin.jacksgogo.Models;

import java.util.Date;

/**
 * Created by PUMA on 10/28/2017.
 */

public class JGGServiceModel extends JGGAppointmentBaseModel {

    String serviceTitle;

    public JGGServiceModel(Date appointmentDate, String title, AppointmentStatus status, String comment, Integer badgeNumber) {
        super(appointmentDate, title, status, comment, badgeNumber);
    }

    public String getServiceTitle() {
        return serviceTitle;
    }

    public void setServiceTitle(String serviceTitle) {
        this.serviceTitle = serviceTitle;
    }
}
