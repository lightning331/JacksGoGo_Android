package com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events;

/**
 * Created by PUMA on 10/28/2017.
 */

public class JGGReportModel {
    private String title;
    private String description;

    public JGGReportModel(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
