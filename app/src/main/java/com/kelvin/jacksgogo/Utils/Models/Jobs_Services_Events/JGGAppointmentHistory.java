package com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events;

import com.kelvin.jacksgogo.Utils.Global.AppointmentHistoryStatus;

public class JGGAppointmentHistory {

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

    public AppointmentHistoryStatus getStatus() {
        return AppointmentHistoryStatus.valueOf(Status);
    }

    public void setStatus(AppointmentHistoryStatus status) {
        Status = status.getValue();
    }

    public String getActiveOn() {
        return ActiveOn;
    }

    public void setActiveOn(String activeOn) {
        ActiveOn = activeOn;
    }
}
