package com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events;

import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;

public class JGGReportResultModel {

    private String ID;
    private String ContractID;
    private String GeoData;
    private String PinCode;
    private boolean IsPhoto;
    private ArrayList<String> BeforePhotoURLs;
    private ArrayList<String> AfterPhotoURLs;
    private String BeforeComment;
    private String AfterComment;

    // dump data
    private JGGContractModel contractModel;

    private ArrayList<AlbumFile> beforeAlbumFiles = new ArrayList<>();
    private ArrayList<AlbumFile> afterAlbumFiles = new ArrayList<>();

    private JGGBillableModel billableModel;

    private String endJobDate;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getContractID() {
        return ContractID;
    }

    public void setContractID(String contractID) {
        ContractID = contractID;
    }

    public String getGeoData() {
        return GeoData;
    }

    public void setGeoData(String geoData) {
        GeoData = geoData;
    }

    public String getPinCode() {
        return PinCode;
    }

    public boolean isPhoto() {
        return IsPhoto;
    }

    public void setPhoto(boolean photo) {
        IsPhoto = photo;
    }

    public void setPinCode(String pinCode) {
        PinCode = pinCode;
    }

    public ArrayList<String> getBeforePhotoURLs() {
        return BeforePhotoURLs;
    }

    public void setBeforePhotoURLs(ArrayList<String> beforePhotoURLs) {
        BeforePhotoURLs = beforePhotoURLs;
    }

    public ArrayList<String> getAfterPhotoURLs() {
        return AfterPhotoURLs;
    }

    public void setAfterPhotoURLs(ArrayList<String> afterPhotoURLs) {
        AfterPhotoURLs = afterPhotoURLs;
    }

    public String getBeforeComment() {
        return BeforeComment;
    }

    public void setBeforeComment(String beforeComment) {
        BeforeComment = beforeComment;
    }

    public String getAfterComment() {
        return AfterComment;
    }

    public void setAfterComment(String afterComment) {
        AfterComment = afterComment;
    }

    public JGGContractModel getContractModel() {
        return contractModel;
    }

    public void setContractModel(JGGContractModel contractModel) {
        this.contractModel = contractModel;
    }

    public ArrayList<AlbumFile> getBeforeAlbumFiles() {
        return beforeAlbumFiles;
    }

    public void setBeforeAlbumFiles(ArrayList<AlbumFile> beforeAlbumFiles) {
        this.beforeAlbumFiles = beforeAlbumFiles;
    }

    public ArrayList<AlbumFile> getAfterAlbumFiles() {
        return afterAlbumFiles;
    }

    public void setAfterAlbumFiles(ArrayList<AlbumFile> afterAlbumFiles) {
        this.afterAlbumFiles = afterAlbumFiles;
    }

    public JGGBillableModel getBillableModel() {
        return billableModel;
    }

    public void setBillableModel(JGGBillableModel billableModel) {
        this.billableModel = billableModel;
    }

    public String getEndJobDate() {
        return endJobDate;
    }

    public void setEndJobDate(String endJobDate) {
        this.endJobDate = endJobDate;
    }
}
