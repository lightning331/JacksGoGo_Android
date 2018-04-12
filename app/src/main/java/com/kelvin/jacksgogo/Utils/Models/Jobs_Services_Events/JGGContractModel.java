package com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events;

import com.kelvin.jacksgogo.Utils.Global.ContractStatus;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel;
import com.kelvin.jacksgogo.Utils.Models.System.JGGCurrencyModel;

public class JGGContractModel {

    private String ID;
    private String AppointmentID;
    private JGGAppointmentModel Appointment;
    private String ProposalID;
    private JGGProposalModel Proposal;
    private double GrossAmt;
    private double AdditionalAmt;
    private double PaidAmt;
    private String CurrencyCode;
    private JGGCurrencyModel Currency;
    private String StartOn;
    private String EndOn;
    private Integer Status;
    private boolean IsDeleted;
    private String ReasonID;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public JGGAppointmentModel getAppointment() {
        return Appointment;
    }

    public void setAppointment(JGGAppointmentModel appointment) {
        Appointment = appointment;
    }

    public JGGProposalModel getProposal() {
        return Proposal;
    }

    public void setProposal(JGGProposalModel proposal) {
        Proposal = proposal;
    }

    public JGGCurrencyModel getCurrency() {
        return Currency;
    }

    public void setCurrency(JGGCurrencyModel currency) {
        Currency = currency;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public String getAppointmentID() {
        return AppointmentID;
    }

    public void setAppointmentID(String appointmentID) {
        AppointmentID = appointmentID;
    }

    public String getProposalID() {
        return ProposalID;
    }

    public void setProposalID(String proposalID) {
        ProposalID = proposalID;
    }

    public double getGrossAmt() {
        return GrossAmt;
    }

    public void setGrossAmt(double grossAmt) {
        GrossAmt = grossAmt;
    }

    public double getAdditionalAmt() {
        return AdditionalAmt;
    }

    public void setAdditionalAmt(double additionalAmt) {
        AdditionalAmt = additionalAmt;
    }

    public double getPaidAmt() {
        return PaidAmt;
    }

    public void setPaidAmt(double paidAmt) {
        PaidAmt = paidAmt;
    }

    public String getCurrencyCode() {
        return CurrencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        CurrencyCode = currencyCode;
    }

    public String getStartOn() {
        return StartOn;
    }

    public void setStartOn(String startOn) {
        StartOn = startOn;
    }

    public String getEndOn() {
        return EndOn;
    }

    public void setEndOn(String endOn) {
        EndOn = endOn;
    }

    public ContractStatus getStatus() {
        return ContractStatus.valueOf(Status);
    }

    public void setStatus(ContractStatus status) {
        Status = status.getValue();
    }

    public boolean isDeleted() {
        return IsDeleted;
    }

    public void setDeleted(boolean deleted) {
        IsDeleted = deleted;
    }

    public String getReasonID() {
        return ReasonID;
    }

    public void setReasonID(String reasonID) {
        ReasonID = reasonID;
    }
}
