package com.kelvin.jacksgogo.Models.Jobs_Services_Events;

import com.google.android.gms.maps.model.LatLng;
import com.kelvin.jacksgogo.Models.User.JGGClientUserModel;

import java.util.Date;

/**
 * Created by PUMA on 10/28/2017.
 */

public class JGGServiceModel extends JGGAppBaseModel {

    public JGGClientUserModel invitingClient;
    public AppointmentType type;
    public String unit;
    public String street;
    public String postcode;
    public LatLng latLng = new LatLng(0, 0);
    public int reportType = 0;

    public JGGServiceModel(Date appointmentDate, String title, AppointmentStatus status, String comment, Integer badgeNumber) {
        super(appointmentDate, title, status, comment, badgeNumber, AppointmentType.SERVICES);
    }

    public JGGServiceModel() {
        super();
        init();
    }

    public void init() {
        this.type = AppointmentType.SERVICES;
    }

    public int getReportType() {
        return reportType;
    }

    public void setReportType(int reportType) {
        this.reportType = reportType;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
