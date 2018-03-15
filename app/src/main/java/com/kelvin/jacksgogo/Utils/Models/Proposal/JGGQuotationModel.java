package com.kelvin.jacksgogo.Utils.Models.Proposal;

import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentBaseModel;
import com.kelvin.jacksgogo.Utils.Models.System.JGGTimeSlotModel;

import java.util.ArrayList;

/**
 * Created by PUMA on 3/15/2018.
 */

public class JGGQuotationModel extends JGGAppointmentBaseModel {

    private String ProviderProfileID;
    private String CategoryID;
    private String Repetition;
    private Double BudgetFrom;
    private Double BudgetTo;
    private Double Budget;
    private Integer AppointmentType;    // 0: Repeating, 1: One-time
    private Integer RepetitionType;
    private int BudgetType = 0;
    private int ReportType = 0;;
    private boolean IsQuckJob;
    private ArrayList<JGGTimeSlotModel> Sessions = new ArrayList<>();
    private ArrayList<String> AttachmentURLs;

    public String getProviderProfileID() {
        return ProviderProfileID;
    }

    public void setProviderProfileID(String providerProfileID) {
        ProviderProfileID = providerProfileID;
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }

    public String getRepetition() {
        return Repetition;
    }

    public void setRepetition(String repetition) {
        Repetition = repetition;
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

    public Integer getAppointmentType() {
        return AppointmentType;
    }

    public void setAppointmentType(Integer appointmentType) {
        AppointmentType = appointmentType;
    }

    public Integer getRepetitionType() {
        return RepetitionType;
    }

    public void setRepetitionType(Integer repetitionType) {
        RepetitionType = repetitionType;
    }

    public int getBudgetType() {
        return BudgetType;
    }

    public void setBudgetType(int budgetType) {
        BudgetType = budgetType;
    }

    public int getReportType() {
        return ReportType;
    }

    public void setReportType(int reportType) {
        ReportType = reportType;
    }

    public boolean isQuckJob() {
        return IsQuckJob;
    }

    public void setQuckJob(boolean quckJob) {
        IsQuckJob = quckJob;
    }

    public ArrayList<JGGTimeSlotModel> getSessions() {
        return Sessions;
    }

    public void setSessions(ArrayList<JGGTimeSlotModel> sessions) {
        Sessions = sessions;
    }

    public ArrayList<String> getAttachmentURLs() {
        return AttachmentURLs;
    }

    public void setAttachmentURLs(ArrayList<String> attachmentURLs) {
        AttachmentURLs = attachmentURLs;
    }
}
