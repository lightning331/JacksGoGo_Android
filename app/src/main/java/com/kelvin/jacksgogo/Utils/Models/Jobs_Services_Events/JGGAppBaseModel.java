package com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events;

import com.kelvin.jacksgogo.Utils.Models.Base.JGGBaseModel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by PUMA on 10/26/2017.
 */


public class JGGAppBaseModel extends JGGBaseModel {

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

    public enum AppointmentType {
        JOBS,
        SERVICES,
        EVENT,
        GOCLUB,
        USERS,
        UNKNOWN
    }

    private Date date;
    private String title;
    private AppointmentStatus status;
    private String comment;
    private Integer badgeNumber;
    private String appointmentMonth;
    private String appointmentDay;
    private String tags;
    private AppointmentType type = AppointmentType.UNKNOWN;

    public JGGAppBaseModel() {
        super();
    }

    public JGGAppBaseModel(Date date, String title, AppointmentStatus status, String comment, Integer badgeNumber, AppointmentType type) {
        this.title=title;
        this.status = status;
        this.comment = comment;
        this.badgeNumber = badgeNumber;
        this.date = date;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        // set appointment Day
        simpleDateFormat.applyPattern("dd");
        if (this.date != null) {
            String appointmentDay = simpleDateFormat.format(this.date);
            setAppointmentDay(appointmentDay);
        } else {
            setAppointmentDay(null);
        }

        // set appointment Month
        simpleDateFormat.applyPattern("MMM");
        if (this.date != null) {
            String appointmentMonth = simpleDateFormat.format(this.date);
            setAppointmentMonth(appointmentMonth);
        } else {
            setAppointmentMonth(null);
        }
    }

    public void setAppointmentMonth(String appointmentMonth) {
        this.appointmentMonth = appointmentMonth;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public AppointmentType getType() {
        return type;
    }

    public void setType(AppointmentType type) {
        this.type = type;
    }

}
