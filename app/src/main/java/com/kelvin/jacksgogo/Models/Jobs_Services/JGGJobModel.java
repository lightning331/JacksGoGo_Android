package com.kelvin.jacksgogo.Models.Jobs_Services;

import com.kelvin.jacksgogo.Models.JGGBiddingProviderModel;
import com.kelvin.jacksgogo.Models.User.JGGProviderUserModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by PUMA on 10/28/2017.
 */

public class JGGJobModel extends JGGAppBaseModel {

    public JGGJobModel(Date appointmentDate, String title, AppointmentStatus status, String comment, Integer badgeNumber) {
        super(appointmentDate, title, status, comment, badgeNumber, AppointmentType.JOBS);
    }

    public ArrayList<JGGBiddingProviderModel> biddingProviders = new ArrayList<>();
    public ArrayList<JGGProviderUserModel> invitedProviders = new ArrayList<>();
}
