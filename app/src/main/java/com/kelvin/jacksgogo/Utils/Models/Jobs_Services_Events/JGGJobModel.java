package com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events;

import android.media.Image;

import com.kelvin.jacksgogo.Utils.Global.JGGBudgetType;
import com.kelvin.jacksgogo.Utils.Global.JGGRepetitionType;
import com.kelvin.jacksgogo.Utils.Global.TimeSlotSelectionStatus;
import com.kelvin.jacksgogo.Utils.Models.JGGBiddingProviderModel;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel;
import com.kelvin.jacksgogo.Utils.Models.System.JGGTimeSlotModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGProviderUserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PUMA on 10/28/2017.
 */

public class JGGJobModel extends JGGAppointmentBaseModel {

    private String ID;
    private String CategoryID;
    private String ExpiredOn;   // Originally date type
    private String Repetition;
    private Double BudgetFrom;
    private Double BudgetTo;
    private Double Budget;
    private boolean IsRequest;
    private boolean IsRescheduled;
    private boolean IsQuickJob;
    private Integer AppointmentType;
    private Integer RepetitionType;
    private Integer BudgetType;
    private int ReportType = 0;
    private int ViewCount = 0;
    private JGGCategoryModel Category = new JGGCategoryModel();
    private JGGProposalModel Proposal = new JGGProposalModel();
    private ArrayList<JGGTimeSlotModel> Sessions = new ArrayList<>();
    private ArrayList<String> AttachmentURLs;

    // Dump Data
    private ArrayList<Image> attachmentImages;
    private Integer timeSlotType;

    private ArrayList<JGGBiddingProviderModel> biddingProviders;
    private ArrayList<JGGProviderUserModel> invitedProviders;
    public JGGJobModel() {
        super();
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public TimeSlotSelectionStatus getTimeSlotType() {
        return TimeSlotSelectionStatus.valueOf(timeSlotType);
    }

    public void setTimeSlotType(TimeSlotSelectionStatus timeSlotType) {
        this.timeSlotType = timeSlotType.getValue();
    }

    public ArrayList<Image> getAttachmentImages() {
        return attachmentImages;
    }

    public void setAttachmentImages(ArrayList<Image> attachmentImages) {
        this.attachmentImages = attachmentImages;
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

    public ArrayList<String> getAttachmentURLs() {
        return AttachmentURLs;
    }

    public void setAttachmentURLs(ArrayList<String> attachmentURLs) {
        AttachmentURLs = attachmentURLs;
    }

    public JGGBudgetType getBudgetType() {
        return JGGBudgetType.valueOf(BudgetType);
    }

    public void setBudgetType(JGGBudgetType budgetType) {
        BudgetType = budgetType.getValue();
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

    public String getExpiredOn() {
        return ExpiredOn;
    }

    public void setExpiredOn(String expiredOn) {
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

    public Integer getAppointmentType() {
        return AppointmentType;
    }

    public void setAppointmentType(Integer appType) {
        AppointmentType = appType;
    }

    public JGGRepetitionType getRepetitionType() {
        return JGGRepetitionType.valueOf(RepetitionType);
    }

    public void setRepetitionType(JGGRepetitionType repetitionType) {
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

