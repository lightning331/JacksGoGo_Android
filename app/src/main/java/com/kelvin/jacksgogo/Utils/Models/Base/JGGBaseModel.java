package com.kelvin.jacksgogo.Utils.Models.Base;

/**
 * Created by PUMA on 10/28/2017.
 */

public class JGGBaseModel {

    private String CreateBy;
    private String CreateByID;
    private String CreateAt;
    private String ModifyBy;
    private String ModifyByID;
    private String ModifyAt;
    private String IsActive;

    public String getCreateBy() {
        return CreateBy;
    }

    public void setCreateBy(String createBy) {
        CreateBy = createBy;
    }

    public String getCreateByID() {
        return CreateByID;
    }

    public void setCreateByID(String createByID) {
        CreateByID = createByID;
    }

    public String getCreateAt() {
        return CreateAt;
    }

    public void setCreateAt(String createAt) {
        CreateAt = createAt;
    }

    public String getModifyBy() {
        return ModifyBy;
    }

    public void setModifyBy(String modifyBy) {
        ModifyBy = modifyBy;
    }

    public String getModifyByID() {
        return ModifyByID;
    }

    public void setModifyByID(String modifyByID) {
        ModifyByID = modifyByID;
    }

    public String getModifyAt() {
        return ModifyAt;
    }

    public void setModifyAt(String modifyAt) {
        ModifyAt = modifyAt;
    }

    public String getIsActive() {
        return IsActive;
    }

    public void setIsActive(String isActive) {
        IsActive = isActive;
    }
}
