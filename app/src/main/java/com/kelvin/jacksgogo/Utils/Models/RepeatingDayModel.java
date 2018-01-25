package com.kelvin.jacksgogo.Utils.Models;

/**
 * Created by PUMA on 1/23/2018.
 */

public class RepeatingDayModel {

    private int id;
    private String title;

    public RepeatingDayModel(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
