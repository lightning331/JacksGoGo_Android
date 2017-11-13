package com.kelvin.jacksgogo.Models.Jobs_Services;

import java.util.Date;

/**
 * Created by PUMA on 10/28/2017.
 */

public class JGGServiceModel extends JGGAppBaseModel {

    public JGGServiceModel(Date appointmentDate, String title, AppointmentStatus status, String comment, Integer badgeNumber) {
        super(appointmentDate, title, status, comment, badgeNumber, AppointmentType.SERVICES);
    }
}
