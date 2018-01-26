package com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events;

import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.Models.JGGBiddingProviderModel;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel;
import com.kelvin.jacksgogo.Utils.Models.System.JGGJobTimeModel;
import com.kelvin.jacksgogo.Utils.Models.System.JGGTimeSlotModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGProviderUserModel;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by PUMA on 10/28/2017.
 */

public class JGGJobModel extends JGGAppointmentBaseModel {

    private String CategoryID;
    private JGGCategoryModel Category;
    private boolean IsRequest;
    private int ServiceType = 0;
    private ArrayList<String> AttachmentURLs;
    private Double BudgetFrom;
    private Double BudgetTo;
    private Double Budget;
    private Date ExpiredOn;
    private int ReportType = 0;
    private boolean IsRescheduled;
    private String Repetition;
    private boolean IsQuickJob;
    private int ViewCount = 0;
    private JGGJobTimeModel JobTime;
    private ArrayList<JGGTimeSlotModel> Sessions;
    private JGGProposalModel Proposal;
    private Integer JobType;
    private Integer RepetitionType;

    private ArrayList<JGGBiddingProviderModel> biddingProviders;
    private ArrayList<JGGProviderUserModel> invitedProviders;

    public JGGJobModel() {
        super();
    }

    @Override
    public AppointmentType getType() {
        return AppointmentType.JOBS;
    }

    @Override
    public void setType(AppointmentType type) {
        super.setType(type);
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }

    public JGGCategoryModel getCategory() {
        return Category;
    }

    public void setCategory(JGGCategoryModel category) {
        Category = category;
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

    public ArrayList<String> getAttachmentURLs() {
        return AttachmentURLs;
    }

    public void setAttachmentURLs(ArrayList<String> attachmentURLs) {
        AttachmentURLs = attachmentURLs;
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

    public boolean isQuickJob() {
        return IsQuickJob;
    }

    public void setQuickJob(boolean quickJob) {
        IsQuickJob = quickJob;
    }

    public int getViewCount() {
        return ViewCount;
    }

    public void setViewCount(int viewCount) {
        ViewCount = viewCount;
    }

    public ArrayList<JGGTimeSlotModel> getSessions() {
        return Sessions;
    }

    public void setSessions(ArrayList<JGGTimeSlotModel> sessions) {
        Sessions = sessions;
    }

    public JGGProposalModel getProposal() {
        return Proposal;
    }

    public void setProposal(JGGProposalModel proposal) {
        Proposal = proposal;
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
        return Global.JGGJobType.valueOf(JobType);
    }

    public void setJobType(Global.JGGJobType jobType) {
        JobType = jobType.getValue();
    }

    public Global.JGGRepetitionType getRepetitionType() {
        return Global.JGGRepetitionType.valueOf(RepetitionType);
    }

    public void setRepetitionType(Global.JGGRepetitionType repetitionType) {
        RepetitionType = repetitionType.getValue();
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
}

