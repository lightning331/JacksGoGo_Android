package com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events;

import java.util.Date;

/**
 * Created by PUMA on 10/28/2017.
 */

public class JGGServicePackageModel extends JGGAppBaseModel {

    public JGGServicePackageModel(Date appointmentDate, String title, AppointmentStatus status, String comment, Integer badgeNumber) {
        super(appointmentDate, title, status, comment, badgeNumber, AppointmentType.UNKNOWN);
    }
}
