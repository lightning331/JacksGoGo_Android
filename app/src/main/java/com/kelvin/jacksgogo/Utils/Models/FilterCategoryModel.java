package com.kelvin.jacksgogo.Utils.Models;

/**
 * Created by storm on 5/15/2018.
 */

public class FilterCategoryModel {
    private boolean isSelected;
    private String categoryName;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
