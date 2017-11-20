package com.kelvin.jacksgogo.Models.Jobs_Services;

import com.google.android.gms.maps.model.LatLng;
import com.kelvin.jacksgogo.Models.User.JGGClientUserModel;

import java.util.Date;

/**
 * Created by PUMA on 10/28/2017.
 */

public class JGGServiceModel extends JGGAppBaseModel {

    public JGGClientUserModel invitingClient;
    public LatLng latLng = new LatLng(0, 0);
    public String title;
    public String subTitle;
    public String name;

    public JGGServiceModel(Date appointmentDate, String title, AppointmentStatus status, String comment, Integer badgeNumber) {
        super(appointmentDate, title, status, comment, badgeNumber, AppointmentType.SERVICES);
    }
}
