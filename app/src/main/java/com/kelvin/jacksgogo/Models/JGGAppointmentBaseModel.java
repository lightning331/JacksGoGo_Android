package com.kelvin.jacksgogo.Models;

import java.util.Date;

/**
 * Created by PUMA on 10/26/2017.
 */


public class Appointment {

    public enum AppointmentStatus {
        NONE,
        PENDING,
        WORKINPROGRESS,
        REJECTED,
        CANCELLED,
        WITHDRAWN,
        COMPLETED,
        WAITINGFORREVIEW
    }

    Date appointmentDate;
    String title;
    AppointmentStatus status;
    String comment;
    Integer badgeNumber;

    public Appointment(Date appointmentDate, String title, AppointmentStatus status, String comment, Integer badgeNumber) {
        this.title=title;
        this.status = status;
        this.comment = comment;
        this.badgeNumber = badgeNumber;
        this.appointmentDate = appointmentDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public int getBadgeNumber() {
        return badgeNumber;
    }

    public void setBadgeNumber(int badgeNumber) {
        this.badgeNumber = badgeNumber;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }
}
