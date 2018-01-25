package com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events;

import android.media.Image;

import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.Models.JGGBiddingProviderModel;
import com.kelvin.jacksgogo.Utils.Models.System.JGGJobTimeModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGProviderUserModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by PUMA on 10/28/2017.
 */

public class JGGJobModel extends JGGAppointmentBaseModel {

    private String JobCategoryID;
    private boolean IsRequest;
    private int ServiceType = 0;
    private ArrayList<String> AttachmentURL;
    private Double BudgetFrom;
    private Double BudgetTo;
    private Double Budget;
    private Date ExpiredOn;
    private int ReportType = 0;
    private boolean IsRescheduled;
    private String Repetition;
    private JGGJobTimeModel JobTime;
    private Global.JGGJobType JobType = Global.JGGJobType.none;
    private Global.JGGRepetitionType RepetitionType = Global.JGGRepetitionType.none;

    private ArrayList<JGGBiddingProviderModel> biddingProviders;
    private ArrayList<JGGProviderUserModel> invitedProviders;
    private AppointmentType type;

    public JGGJobModel() {
        super();

        type = AppointmentType.JOBS;
        biddingProviders = new ArrayList<>();
        invitedProviders = new ArrayList<>();
    }

    public String reportTypeName() {
        switch (ReportType) {
            case 1:
                return "Before & After Photo";
            case 2:
                return "Geotracking";
            case 3:
                return "Before & After Photo" + ", " + "Geotracking";
            case 4:
                return "PIN Code";
            case 5:
                return "Before & After Photo" + ", " + "PIN Code";
            case 6:
                return "Geotracking" + ", " + "PIN Code";
            case 7:
                return "Before & After Photo" + ", " + "Geotracking" + ", " + "PIN Code";
            default:
                return "No set";
        }
    }

    public List<Integer> selectedID() {
        List<Integer> array = new ArrayList<>();
        switch (ReportType) {
            case 1:
                array.add(1);
                return array;
            case 2:
                array.add(2);
                return array;
            case 3:
                array.add(1);
                array.add(2);
                return array;
            case 4:
                array.add(3);
                return array;
            case 5:
                array.add(1);
                array.add(3);
                return array;
            case 6:
                array.add(2);
                array.add(3);
                return array;
            case 7:
                array.add(1);
                array.add(2);
                array.add(3);
                return array;
            default:
                return array;
        }
    }

    public String getJobCategoryID() {
        return JobCategoryID;
    }

    public void setJobCategoryID(String jobCategoryID) {
        JobCategoryID = jobCategoryID;
    }

    public boolean isRequest() {
        return IsRequest;
    }

    public void setRequest(boolean request) {
        IsRequest = request;
    }

    public int getServiceType() {
        return ServiceType;
    }

    public void setServiceType(int serviceType) {
        ServiceType = serviceType;
    }

    public ArrayList<String> getAttachmentURL() {
        return AttachmentURL;
    }

    public void setAttachmentURL(ArrayList<String> attachmentURL) {
        AttachmentURL = attachmentURL;
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

    public Date getExpiredOn() {
        return ExpiredOn;
    }

    public void setExpiredOn(Date expiredOn) {
        ExpiredOn = expiredOn;
    }

    public int getReportType() {
        return ReportType;
    }

    public void setReportType(int reportType) {
        ReportType = reportType;
    }

    public boolean isRescheduled() {
        return IsRescheduled;
    }

    public void setRescheduled(boolean rescheduled) {
        IsRescheduled = rescheduled;
    }

    public String getRepetition() {
        return Repetition;
    }

    public void setRepetition(String repetition) {
        Repetition = repetition;
    }

    public JGGJobTimeModel getJobTime() {
        return JobTime;
    }

    public void setJobTime(JGGJobTimeModel jobTime) {
        JobTime = jobTime;
    }

    public Global.JGGJobType getJobType() {
        return JobType;
    }

    public void setJobType(Global.JGGJobType jobType) {
        JobType = jobType;
    }

    public Global.JGGRepetitionType getRepetitionType() {
        return RepetitionType;
    }

    public void setRepetitionType(Global.JGGRepetitionType repetitionType) {
        RepetitionType = repetitionType;
    }

    public ArrayList<JGGBiddingProviderModel> getBiddingProviders() {
        return biddingProviders;
    }

    public void setBiddingProviders(ArrayList<JGGBiddingProviderModel> biddingProviders) {
        this.biddingProviders = biddingProviders;
    }

    public ArrayList<JGGProviderUserModel> getInvitedProviders() {
        return invitedProviders;
    }

    public void setInvitedProviders(ArrayList<JGGProviderUserModel> invitedProviders) {
        this.invitedProviders = invitedProviders;
    }

    @Override
    public AppointmentType getType() {
        return type;
    }

    @Override
    public void setType(AppointmentType type) {
        this.type = type;
    }
}

