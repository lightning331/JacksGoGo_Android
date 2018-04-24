package com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events;

import com.kelvin.jacksgogo.Utils.Global.AppointmentActivityStatus;

import java.util.Date;

import static com.kelvin.jacksgogo.Utils.JGGTimeManager.appointmentMonthDate;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.appointmentMonthDateString;

public class JGGAppointmentActivityModel {

    private String ID;
    private String AppointmentID;
    private JGGAppointmentModel Appointment;
    private String ReferenceID;
    private String Reference;
    private Integer Status;
    private String ActiveOn;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getAppointmentID() {
        return AppointmentID;
    }

    public void setAppointmentID(String appointmentID) {
        AppointmentID = appointmentID;
    }

    public JGGAppointmentModel getAppointment() {
        return Appointment;
    }

    public void setAppointment(JGGAppointmentModel appointment) {
        Appointment = appointment;
    }

    public String getReferenceID() {
        return ReferenceID;
    }

    public void setReferenceID(String referenceID) {
        ReferenceID = referenceID;
    }

    public String getReference() {
        return Reference;
    }

    public void setReference(String reference) {
        Reference = reference;
    }

    public AppointmentActivityStatus getStatus() {
        return AppointmentActivityStatus.valueOf(Status);
    }

    public void setStatus(AppointmentActivityStatus status) {
        Status = status.getValue();
    }

    public Date getActiveOn() {
        return appointmentMonthDate(ActiveOn);
    }

    public void setActiveOn(Date activeOn) {
        ActiveOn = appointmentMonthDateString(activeOn);
    }
}
