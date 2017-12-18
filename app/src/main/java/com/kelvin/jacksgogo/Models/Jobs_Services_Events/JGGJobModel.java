package com.kelvin.jacksgogo.Models.Jobs_Services_Events;

import com.kelvin.jacksgogo.Models.JGGBiddingProviderModel;
import com.kelvin.jacksgogo.Models.User.JGGProviderUserModel;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by PUMA on 10/28/2017.
 */

public class JGGJobModel extends JGGAppBaseModel {

    private ArrayList<JGGBiddingProviderModel> biddingProviders;
    private ArrayList<JGGProviderUserModel> invitedProviders;
    private AppointmentType type;

    public JGGJobModel(Date date, String title, AppointmentStatus status, String comment, Integer badgeNumber) {
        super(date, title, status, comment, badgeNumber, AppointmentType.JOBS);
    }

    public JGGJobModel() {
        super();

        init();
    }

    public void init() {
        type = AppointmentType.JOBS;
        biddingProviders = new ArrayList<>();
        invitedProviders = new ArrayList<>();
    }

    public ArrayList<JGGBiddingProviderModel> getBiddingProviders() {
        return biddingProviders;
    }

    public void setBiddingProviders(ArrayList<JGGBiddingProviderModel> biddingProviders) {
        this.biddingProviders = biddingProviders;
    }

    public ArrayList<JGGProviderUserModel> getInvitedProviders() {
        return invitedProviders;
    }

    public void setInvitedProviders(ArrayList<JGGProviderUserModel> invitedProviders) {
        this.invitedProviders = invitedProviders;
    }

    @Override
    public AppointmentType getType() {
        return type;
    }

    @Override
    public void setType(AppointmentType type) {
        this.type = type;
    }
}
