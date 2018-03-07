package com.kelvin.jacksgogo.Utils.Models.Proposal;

import com.kelvin.jacksgogo.Utils.Models.Base.JGGBaseModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGJobModel;
import com.kelvin.jacksgogo.Utils.Models.System.JGGCurrencyModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserProfileModel;

import java.util.Date;

import static com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentBaseModel.appointmentMonthDate;
import static com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentBaseModel.appointmentMonthDateString;

/**
 * Created by PUMA on 1/26/2018.
 */

public class JGGProposalModel extends JGGBaseModel {

    private JGGJobModel Appointment;
    private JGGCurrencyModel Currency;
    private JGGUserProfileModel UserProfile;
    private String  AppointmentID;
    private String UserProfileID;
    private String Title;
    private String Description;
    private Double BudgetFrom;
    private Double BudgetTo;
    private Double Budget;
    private String CurrencyCode;
    private boolean RescheduleAllowed;
    private String RescheduleDate;
    private String RescheduleNote;
    private boolean CancellationAllowed;
    private String CancellationDate;
    private String CancellationNote;
    private boolean IsInvited;
    private String SubmitOn;
    private String ExpireOn;
    private int Status = 0;
    private boolean IsViewed;

    public JGGProposalModel() {
        super();
    }

    public JGGJobModel getAppointment() {
        return Appointment;
    }

    public void setAppointment(JGGJobModel appointment) {
        Appointment = appointment;
    }

    public JGGCurrencyModel getCurrency() {
        return Currency;
    }

    public void setCurrency(JGGCurrencyModel currency) {
        Currency = currency;
    }

    public JGGUserProfileModel getUserProfile() {
        return UserProfile;
    }

    public void setUserProfile(JGGUserProfileModel userProfile) {
        UserProfile = userProfile;
    }

    public String getAppointmentID() {
        return AppointmentID;
    }

    public void setAppointmentID(String appointmentID) {
        AppointmentID = appointmentID;
    }

    public String getUserProfileID() {
        return UserProfileID;
    }

    public void setUserProfileID(String userProfileID) {
        UserProfileID = userProfileID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
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

    public Double getBudget() {
        return Budget;
    }

    public void setBudget(Double budget) {
        Budget = budget;
    }

    public String getCurrencyCode() {
        return CurrencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        CurrencyCode = currencyCode;
    }

    public boolean isRescheduleAllowed() {
        return RescheduleAllowed;
    }

    public void setRescheduleAllowed(boolean rescheduleAllowed) {
        RescheduleAllowed = rescheduleAllowed;
    }

    public Date getRescheduleDate() {
        return appointmentMonthDate(RescheduleDate);
    }

    public void setRescheduleDate(Date rescheduleDate) {
        RescheduleDate = appointmentMonthDateString(rescheduleDate);
    }

    public String getRescheduleNote() {
        return RescheduleNote;
    }

    public void setRescheduleNote(String rescheduleNote) {
        RescheduleNote = rescheduleNote;
    }

    public boolean isCancellationAllowed() {
        return CancellationAllowed;
    }

    public void setCancellationAllowed(boolean cancellationAllowed) {
        CancellationAllowed = cancellationAllowed;
    }

    public Date getCancellationDate() {
        return appointmentMonthDate(CancellationDate);
    }

    public void setCancellationDate(Date cancellationDate) {
        CancellationDate = appointmentMonthDateString(cancellationDate);
    }

    public String getCancellationNote() {
        return CancellationNote;
    }

    public void setCancellationNote(String cancellationNote) {
        CancellationNote = cancellationNote;
    }

    public boolean isInvited() {
        return IsInvited;
    }

    public void setInvited(boolean invited) {
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

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public boolean isViewed() {
        return IsViewed;
    }

    public void setViewed(boolean viewed) {
        IsViewed = viewed;
    }
}
