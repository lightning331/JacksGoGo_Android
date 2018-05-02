package com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events;

import java.util.Date;

import static com.kelvin.jacksgogo.Utils.JGGTimeManager.appointmentMonthDate;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.appointmentMonthDateString;

public class JGGJobInfoModel {

    private Integer ProposalCount;
    private Double AveragePrice;
    private String LastRespondOn;

    public Integer getProposalCount() {
        return ProposalCount;
    }

    public void setProposalCount(Integer proposalCount) {
        ProposalCount = proposalCount;
    }

    public Double getAveragePrice() {
        return AveragePrice;
    }

    public void setAveragePrice(Double averagePrice) {
        AveragePrice = averagePrice;
    }

    public Date getLastRespondOn() {
        return appointmentMonthDate(LastRespondOn);
    }

    public void setLastRespondOn(Date submitOn) {
        LastRespondOn = appointmentMonthDateString(submitOn);
    }
}
