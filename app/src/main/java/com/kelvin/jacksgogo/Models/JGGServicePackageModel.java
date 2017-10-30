package com.kelvin.jacksgogo.Models;

import java.util.Date;

/**
 * Created by PUMA on 10/28/2017.
 */

public class JGGServicePackageModel extends JGGAppointmentBaseModel {
    public JGGServicePackageModel(Date appointmentDate, String title, AppointmentStatus status, String comment, Integer badgeNumber) {
        super(appointmentDate, title, status, comment, badgeNumber);
    }

    public Integer getRemainSlots() {
        return remainSlots;
    }

    public void setRemainSlots(Integer remainSlots) {
        this.remainSlots = remainSlots;
    }

    Integer remainSlots;
}
