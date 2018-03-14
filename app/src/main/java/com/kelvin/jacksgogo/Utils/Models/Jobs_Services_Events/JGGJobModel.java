package com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events;

import android.media.Image;

import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.Models.JGGBiddingProviderModel;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel;
import com.kelvin.jacksgogo.Utils.Models.System.JGGJobTimeModel;
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
    private JGGCategoryModel Category = new JGGCategoryModel();
    private boolean IsRequest;      // true: Job, false: Service
    private Integer AppointmentType;    // 0: Repeating, 1: One-time
    private ArrayList<String> AttachmentURLs;
    private int BudgetType = 0;     // 0: None select, 1: No limit, 2: Fixed amount, 3: Package amount
    private Double BudgetFrom;
    private Double BudgetTo;
    private Double Budget;
    private String ExpiredOn;   // Originally date type
    private int ReportType = 0;
    private boolean IsRescheduled;
    private String Repetition;
    private boolean IsQuickJob;
    private int ViewCount = 0;
    private ArrayList<JGGTimeSlotModel> Sessions = new ArrayList<>();
    private JGGProposalModel Proposal = new JGGProposalModel();
    private Integer RepetitionType;

    // Dump Data
    private ArrayList<Image> attachmentImages;
    private int selectedServiceType = 0;
    private int selectedPriceType = 0;
    private Integer timeSlotType;
    private List<Integer> selectedRepeatingDays = new ArrayList<>();

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

    public Global.TimeSlotSelectionStatus getTimeSlotType() {
        return Global.TimeSlotSelectionStatus.valueOf(timeSlotType);
    }

    public void setTimeSlotType(Global.TimeSlotSelectionStatus timeSlotType) {
        this.timeSlotType = timeSlotType.getValue();
    }

    public int getSelectedServiceType() {
        return selectedServiceType;
    }

    public void setSelectedServiceType(int selectedServiceType) {
        this.selectedServiceType = selectedServiceType;
    }

    public int getSelectedPriceType() {
        return selectedPriceType;
    }

    public void setSelectedPriceType(int selectedPriceType) {
        this.selectedPriceType = selectedPriceType;
    }

    public List<Integer> getSelectedRepeatingDays() {
        return selectedRepeatingDays;
    }

    public void setSelectedRepeatingDays(List<Integer> selectedRepeatingDays) {
        this.selectedRepeatingDays = selectedRepeatingDays;
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

    public int getBudgetType() {
        return BudgetType;
    }

    public void setBudgetType(int budgetType) {
        BudgetType = budgetType;
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

    public void setAppointmentType(Integer jobType) {
        AppointmentType = jobType;
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

