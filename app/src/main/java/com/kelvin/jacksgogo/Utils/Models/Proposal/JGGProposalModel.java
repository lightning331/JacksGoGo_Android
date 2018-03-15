package com.kelvin.jacksgogo.Utils.Models.Proposal;

import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentBaseModel;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.kelvin.jacksgogo.Utils.Global.MINUTES_IN_AN_HOUR;
import static com.kelvin.jacksgogo.Utils.Global.SECONDS_IN_A_MINUTE;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.appointmentMonthDate;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.appointmentMonthDateString;

/**
 * Created by PUMA on 1/26/2018.
 */

public class JGGProposalModel extends JGGAppointmentBaseModel {

    private String ID;
    private String AppointmentID;
    private Double BudgetFrom;
    private Double BudgetTo;
    private Double Budget;
    private String BidBreakDown;
    private Boolean RescheduleAllowed;
    private Integer RescheduleDate;
    private String RescheduleNote;
    private Boolean CancellationAllowed;
    private Integer CancellationDate;
    private String CancellationNote;
    private Boolean IsInvited;
    private String SubmitOn;
    private String ExpireOn;
    private boolean IsViewed;

    public JGGProposalModel() {
        super();
    }

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

    public Double getBudgetFrom() {
        return BudgetFrom;
    }

    public void setBudgetFrom(Double budgetFrom) {
        BudgetFrom = budgetFrom;
    }

    public Double getBudgetTo() {
        return BudgetTo;
    }

    public void setBudgetTo(Double budgetTo) {
        BudgetTo = budgetTo;
    }

    public String getBidBreakDown() {
        return BidBreakDown;
    }

    public void setBidBreakDown(String bidBreakDown) {
        BidBreakDown = bidBreakDown;
    }

    public Double getBudget() {
        return Budget;
    }

    public void setBudget(Double budget) {
        Budget = budget;
    }

    public Boolean isRescheduleAllowed() {
        return RescheduleAllowed;
    }

    public void setRescheduleAllowed(Boolean rescheduleAllowed) {
        RescheduleAllowed = rescheduleAllowed;
    }

    public Integer getRescheduleDate() {
        return RescheduleDate;
    }

    public void setRescheduleDate(Integer rescheduleDate) {
        RescheduleDate = rescheduleDate;
    }

    public String getRescheduleNote() {
        return RescheduleNote;
    }

    public void setRescheduleNote(String rescheduleNote) {
        RescheduleNote = rescheduleNote;
    }

    public Boolean isCancellationAllowed() {
        return CancellationAllowed;
    }

    public void setCancellationAllowed(Boolean cancellationAllowed) {
        CancellationAllowed = cancellationAllowed;
    }

    public Integer getCancellationDate() {
        return CancellationDate;
    }

    public void setCancellationDate(Integer cancellationDate) {
        CancellationDate = cancellationDate;
    }

    public String getCancellationNote() {
        return CancellationNote;
    }

    public void setCancellationNote(String cancellationNote) {
        CancellationNote = cancellationNote;
    }

    public Boolean isInvited() {
        return IsInvited;
    }

    public void setInvited(Boolean invited) {
        IsInvited = invited;
    }

    public Date getSubmitOn() {
        return appointmentMonthDate(SubmitOn);
    }

    public void setSubmitOn(Date submitOn) {
        SubmitOn = appointmentMonthDateString(submitOn);
    }

    public Date getExpireOn() {
        return appointmentMonthDate(ExpireOn);
    }

    public void setExpireOn(Date expireOn) {
        ExpireOn = appointmentMonthDateString(expireOn);
    }

    public boolean isViewed() {
        return IsViewed;
    }

    public void setViewed(boolean viewed) {
        IsViewed = viewed;
    }

    public static Integer getSeconds(String days, String hours, String min) {
        int secondsDay = Integer.parseInt(days) * 24 * MINUTES_IN_AN_HOUR * SECONDS_IN_A_MINUTE;
        int secondsHour = Integer.parseInt(hours) * MINUTES_IN_AN_HOUR * SECONDS_IN_A_MINUTE;
        int secondsMinute = Integer.parseInt(min) * SECONDS_IN_A_MINUTE;
        int totalSeconds = secondsDay + secondsHour + secondsMinute;
        return Integer.valueOf(totalSeconds);
    }

    public static String getDays(Integer seconds) {
        int day = (int) TimeUnit.SECONDS.toDays(seconds);
        return String.valueOf(day);
    }

    public static String getDaysString(long seconds) {
        int day = (int) TimeUnit.SECONDS.toDays(seconds);
        if (day == 1)
            return "At least " + day + " day before.";
        else
            return "At least " + day + " days before.";
    }

    public static String getHours(Integer seconds) {
        int day = (int) TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) - (day *24);
        return String.valueOf(hours);
    }

    public static String getMinutes(Integer seconds) {
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds)* 60);
        return String.valueOf(minute);
    }

}
