package com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events;

import android.media.Image;

import com.kelvin.jacksgogo.Utils.Global;

import java.util.ArrayList;
import java.util.List;

public class JGGCreatingJobModel extends JGGJobModel {

    private ArrayList<Image> attachmentImages;
    private int selectedServiceType = 0;
    private int selectedPriceType = 0;
    private Integer timeSlotType;
    private List<Integer> selectedRepeatingDays = new ArrayList<>();

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
}
