package com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events;

import android.media.Image;

import java.util.ArrayList;
import java.util.List;

public class JGGCreatingJobModel extends JGGJobModel {

    private ArrayList<Image> attachmentImages;
    private int selectedPriceType = 0;
    private List<Integer> selectedRepeatingDays = new ArrayList<>();

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
