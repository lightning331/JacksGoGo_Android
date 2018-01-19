package com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events;

import android.media.Image;

import java.util.ArrayList;

public class JGGCreatingJobModel extends JGGJobModel {

    public ArrayList<Image> attachmentImages;
    private int selectedPriceType = 0;

    public int getSelectedPriceType() {
        return selectedPriceType;
    }

    public void setSelectedPriceType(int selectedPriceType) {
        this.selectedPriceType = selectedPriceType;
    }
}
