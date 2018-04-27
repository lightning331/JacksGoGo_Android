package com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events;

import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by storm2 on 4/27/2018.
 */

public class JGGBillableModel {
    private String itemDescription;
    private Double price;
    private ArrayList<AlbumFile> itemPictures = new ArrayList<>();
    private String requestSentDate;         // 18 Jul, 2017 2:45 PM
    private String requestApproveData;

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ArrayList<AlbumFile> getItemPictures() {
        return itemPictures;
    }

    public void setItemPictures(ArrayList<AlbumFile> itemPictures) {
        this.itemPictures = itemPictures;
    }

    public String getRequestSentDate() {
        return requestSentDate;
    }

    public void setRequestSentDate(String requestSentDate) {
        this.requestSentDate = requestSentDate;
    }

    public String getRequestApproveData() {
        return requestApproveData;
    }

    public void setRequestApproveData(String requestApproveData) {
        this.requestApproveData = requestApproveData;
    }
}

