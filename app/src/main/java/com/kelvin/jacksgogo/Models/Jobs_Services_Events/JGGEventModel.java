package com.kelvin.jacksgogo.Models.Jobs_Services_Events;

import java.util.Date;

/**
 * Created by PUMA on 10/28/2017.
 */

public class JGGEventModel extends JGGAppBaseModel {

    public JGGEventModel(Date appointmentDate, String title, AppointmentStatus status, String comment, Integer badgeNumber) {
        super(appointmentDate, title, status, comment, badgeNumber, AppointmentType.EVENT);
    }
}
