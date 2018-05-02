package com.kelvin.jacksgogo.Utils.Models.Proposal;

import com.kelvin.jacksgogo.Utils.Global.JGGProposalStatus;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentBaseModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;

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

    private JGGAppointmentModel Appointment;
    private String ID;
    private String AppointmentID;
    private Double BudgetFrom;
    private Double BudgetTo;
    private Double Budget;
    private String Breakdown;
    private Boolean RescheduleAllowed;
    private Integer RescheduleTime;
    private String RescheduleNote;
    private Boolean CancellationAllowed;
    private Integer CancellationTime;
    private String CancellationNote;
    private Boolean IsInvited;
    private Boolean AcceptedInvite;
    private String SubmitOn;
    private String LastUpdatedOn;
    private Integer Status;
    private boolean IsViewed;
    private boolean IsDeleted;
    private String Note;

    // Dumy field
    private int MessageCount;

    public int getMessageCount() {
        return MessageCount;
    }

    public void setMessageCount(int messageCount) {
        MessageCount = messageCount;
    }

    public JGGProposalModel() {
        super();
    }

    public JGGAppointmentModel getAppointment() {
        return Appointment;
    }

    public void setAppointment(JGGAppointmentModel appointment) {
        Appointment = appointment;
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

    public String getBreakdown() {
        return Breakdown;
    }

    public void setBreakdown(String breakdown) {
        Breakdown = breakdown;
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

    public Integer getRescheduleTime() {
        return RescheduleTime;
    }

    public void setRescheduleTime(Integer rescheduleTime) {
        RescheduleTime = rescheduleTime;
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

    public Integer getCancellationTime() {
        return CancellationTime;
    }

    public void setCancellationTime(Integer cancellationTime) {
        CancellationTime = cancellationTime;
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

    public Boolean getAcceptedInvite() {
        return AcceptedInvite;
    }

    public void setAcceptedInvite(Boolean acceptedInvite) {
        AcceptedInvite = acceptedInvite;
    }

    public Date getSubmitOn() {
        return appointmentMonthDate(SubmitOn);
    }

    public void setSubmitOn(Date submitOn) {
        SubmitOn = appointmentMonthDateString(submitOn);
    }

    public Date getLastUpdatedOn() {
        return appointmentMonthDate(LastUpdatedOn);
    }

    public void setLastUpdatedOn(Date lastUpdatedOn) {
        LastUpdatedOn = appointmentMonthDateString(lastUpdatedOn);
    }

    public JGGProposalStatus getStatus() {
        return JGGProposalStatus.valueOf(Status);
    }

    public void setStatus(JGGProposalStatus status) {
        Status = status.getValue();
    }

    public boolean isViewed() {
        return IsViewed;
    }

    public void setViewed(boolean viewed) {
        IsViewed = viewed;
    }

    public boolean isDeleted() {
        return IsDeleted;
    }

    public void setDeleted(boolean deleted) {
        IsDeleted = deleted;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
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
