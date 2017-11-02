package com.kelvin.jacksgogo.Models.Jobs_Services;

import com.kelvin.jacksgogo.Models.Base.JGGBaseModel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by PUMA on 10/26/2017.
 */


public class JGGAppointmentBaseModel extends JGGBaseModel {

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

    private Date appointmentDate;
    private String title;
    private AppointmentStatus status;
    private String comment;
    private Integer badgeNumber;
    private String appointmentMonth;
    private String appointmentDay;

    public JGGAppointmentBaseModel(Date appointmentDate, String title, AppointmentStatus status, String comment, Integer badgeNumber) {
        this.title=title;
        this.status = status;
        this.comment = comment;
        this.badgeNumber = badgeNumber;
        this.appointmentDate = appointmentDate;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        // set appointment Day
        simpleDateFormat.applyPattern("dd");
        if (this.appointmentDate != null) {
            String appointmentDay = simpleDateFormat.format(this.appointmentDate);
            setAppointmentDay(appointmentDay);
        } else {
            setAppointmentDay(null);
        }

        // set appointment Month
        simpleDateFormat.applyPattern("MMM");
        if (this.appointmentDate != null) {
            String appointmentMonth = simpleDateFormat.format(this.appointmentDate);
            setAppointmentMonth(appointmentMonth);
        } else {
            setAppointmentMonth(null);
        }
    }

    public void setAppointmentMonth(String appointmentMonth) {
        this.appointmentMonth = appointmentMonth;
    }

    public String getAppointmentDay() {
        return appointmentDay;
    }

    public void setAppointmentDay(String appointmentDay) {
        this.appointmentDay = appointmentDay;
    }

    public void setBadgeNumber(Integer badgeNumber) {
        this.badgeNumber = badgeNumber;
    }

    public String getAppointmentMonth() {
        return appointmentMonth;
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

    public void setComment(String comment) {
        this.comment = comment;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public int getBadgeNumber() {
        return badgeNumber;
    }

    public void setBadgeNumber(int badgeNumber) {
        this.badgeNumber = badgeNumber;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

}
