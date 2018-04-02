package com.kelvin.jacksgogo.Utils.Models.User;

import com.google.gson.annotations.SerializedName;
import com.kelvin.jacksgogo.Utils.Models.System.JGGRegionModel;

/**
 * Created by PUMA on 1/18/2018.
 */

public class JGGUserProfileModel {

    private JGGRegionModel Region;
    private JGGUserBaseModel User;
    private String ID;
    private String RegionID;
    private String UserID;
    private String DisplayName;
    private String LocalNo_CountryCode;
    private String LocalNo_AreaCode;
    private String LocalNo_Number;
    private String LocalNo_Extension;
    private String MobileNo_CountryCode;
    private String MobileNo_AreaCode;
    private String MobileNo_Number;
    private String SecurityMobileNo_CountryCode;
    private String SecurityMobileNo_AreaCode;
    private String SecurityMobileNo_Number;
    private String ResidentialAddress_Unit;
    private String ResidentialAddress_Floor;
    private String ResidentialAddress_Address;
    private String ResidentialAddress_City;
    private String ResidentialAddress_State;
    private String ResidentialAddress_PostalCode;
    private Double ResidentialAddress_Lat;
    private Double ResidentialAddress_Lon;
    private String ResidentialAddress_CountryCode;
    private String BillingAddress_Unit;
    private String BillingAddress_Floor;
    private String BillingAddress_Address;
    private String BillingAddress_City;
    private String BillingAddress_State;
    private String BillingAddress_PostalCode;
    private Double BillingAddress_Lat;
    private Double BillingAddress_Lon;
    private String BillingAddress_CountryCode;
    private String BillingContact;
    private String BillingContractNo_CountryCode;
    private String BillingContractNo_AreaCode;
    private String BillingContractNo_Number;
    private String BillingContractNo_Extension;
    private String BillingMobileNo_CountryCode;
    private String BillingMobileNo_AreaCode;
    private String BillingMobileNo_Number;
    private String BillingMobileNo_Extension;

    public String getFullAddress() {
        String fullAddress = "";
        if (getResidentialAddress_State() == null) {
            fullAddress = fullAddress + getResidentialAddress_Address() + ", " + getResidentialAddress_PostalCode();
        } else {
            if (getResidentialAddress_Unit() != null)
                fullAddress = fullAddress + getResidentialAddress_Unit() + " ";
            if (getResidentialAddress_Address() != null)
                fullAddress = fullAddress + getResidentialAddress_Address() + ", ";
            if (getResidentialAddress_PostalCode() != null)
                fullAddress = fullAddress + getResidentialAddress_PostalCode();
        }
        return fullAddress;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public JGGRegionModel getRegion() {
        return Region;
    }

    public void setRegion(JGGRegionModel region) {
        Region = region;
    }

    public JGGUserBaseModel getUser() {
        return User;
    }

    public void setUser(JGGUserBaseModel user) {
        User = user;
    }

    public String getRegionID() {
        return RegionID;
    }

    public void setRegionID(String regionID) {
        RegionID = regionID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public String getLocalNo_CountryCode() {
        return LocalNo_CountryCode;
    }

    public void setLocalNo_CountryCode(String localNo_CountryCode) {
        LocalNo_CountryCode = localNo_CountryCode;
    }

    public String getLocalNo_AreaCode() {
        return LocalNo_AreaCode;
    }

    public void setLocalNo_AreaCode(String localNo_AreaCode) {
        LocalNo_AreaCode = localNo_AreaCode;
    }

    public String getLocalNo_Number() {
        return LocalNo_Number;
    }

    public void setLocalNo_Number(String localNo_Number) {
        LocalNo_Number = localNo_Number;
    }

    public String getLocalNo_Extension() {
        return LocalNo_Extension;
    }

    public void setLocalNo_Extension(String localNo_Extension) {
        LocalNo_Extension = localNo_Extension;
    }

    public String getMobileNo_CountryCode() {
        return MobileNo_CountryCode;
    }

    public void setMobileNo_CountryCode(String mobileNo_CountryCode) {
        MobileNo_CountryCode = mobileNo_CountryCode;
    }

    public String getMobileNo_AreaCode() {
        return MobileNo_AreaCode;
    }

    public void setMobileNo_AreaCode(String mobileNo_AreaCode) {
        MobileNo_AreaCode = mobileNo_AreaCode;
    }

    public String getMobileNo_Number() {
        return MobileNo_Number;
    }

    public void setMobileNo_Number(String mobileNo_Number) {
        MobileNo_Number = mobileNo_Number;
    }

    public String getSecurityMobileNo_CountryCode() {
        return SecurityMobileNo_CountryCode;
    }

    public void setSecurityMobileNo_CountryCode(String securityMobileNo_CountryCode) {
        SecurityMobileNo_CountryCode = securityMobileNo_CountryCode;
    }

    public String getSecurityMobileNo_AreaCode() {
        return SecurityMobileNo_AreaCode;
    }

    public void setSecurityMobileNo_AreaCode(String securityMobileNo_AreaCode) {
        SecurityMobileNo_AreaCode = securityMobileNo_AreaCode;
    }

    public String getSecurityMobileNo_Number() {
        return SecurityMobileNo_Number;
    }

    public void setSecurityMobileNo_Number(String securityMobileNo_Number) {
        SecurityMobileNo_Number = securityMobileNo_Number;
    }

    public String getResidentialAddress_Unit() {
        return ResidentialAddress_Unit;
    }

    public void setResidentialAddress_Unit(String residentialAddress_Unit) {
        ResidentialAddress_Unit = residentialAddress_Unit;
    }

    public String getResidentialAddress_Floor() {
        return ResidentialAddress_Floor;
    }

    public void setResidentialAddress_Floor(String residentialAddress_Floor) {
        ResidentialAddress_Floor = residentialAddress_Floor;
    }

    public String getResidentialAddress_Address() {
        return ResidentialAddress_Address;
    }

    public void setResidentialAddress_Address(String residentialAddress_Address) {
        ResidentialAddress_Address = residentialAddress_Address;
    }

    public String getResidentialAddress_City() {
        return ResidentialAddress_City;
    }

    public void setResidentialAddress_City(String residentialAddress_City) {
        ResidentialAddress_City = residentialAddress_City;
    }

    public String getResidentialAddress_State() {
        return ResidentialAddress_State;
    }

    public void setResidentialAddress_State(String residentialAddress_State) {
        ResidentialAddress_State = residentialAddress_State;
    }

    public String getResidentialAddress_PostalCode() {
        return ResidentialAddress_PostalCode;
    }

    public void setResidentialAddress_PostalCode(String residentialAddress_PostalCode) {
        ResidentialAddress_PostalCode = residentialAddress_PostalCode;
    }

    public Double getResidentialAddress_Lat() {
        return ResidentialAddress_Lat;
    }

    public void setResidentialAddress_Lat(Double residentialAddress_Lat) {
        ResidentialAddress_Lat = residentialAddress_Lat;
    }

    public Double getResidentialAddress_Lon() {
        return ResidentialAddress_Lon;
    }

    public void setResidentialAddress_Lon(Double residentialAddress_Lon) {
        ResidentialAddress_Lon = residentialAddress_Lon;
    }

    public String getResidentialAddress_CountryCode() {
        return ResidentialAddress_CountryCode;
    }

    public void setResidentialAddress_CountryCode(String residentialAddress_CountryCode) {
        ResidentialAddress_CountryCode = residentialAddress_CountryCode;
    }

    public String getBillingAddress_Unit() {
        return BillingAddress_Unit;
    }

    public void setBillingAddress_Unit(String billingAddress_Unit) {
        BillingAddress_Unit = billingAddress_Unit;
    }

    public String getBillingAddress_Floor() {
        return BillingAddress_Floor;
    }

    public void setBillingAddress_Floor(String billingAddress_Floor) {
        BillingAddress_Floor = billingAddress_Floor;
    }

    public String getBillingAddress_Address() {
        return BillingAddress_Address;
    }

    public void setBillingAddress_Address(String billingAddress_Address) {
        BillingAddress_Address = billingAddress_Address;
    }

    public String getBillingAddress_City() {
        return BillingAddress_City;
    }

    public void setBillingAddress_City(String billingAddress_City) {
        BillingAddress_City = billingAddress_City;
    }

    public String getBillingAddress_State() {
        return BillingAddress_State;
    }

    public void setBillingAddress_State(String billingAddress_State) {
        BillingAddress_State = billingAddress_State;
    }

    public String getBillingAddress_PostalCode() {
        return BillingAddress_PostalCode;
    }

    public void setBillingAddress_PostalCode(String billingAddress_PostalCode) {
        BillingAddress_PostalCode = billingAddress_PostalCode;
    }

    public Double getBillingAddress_Lat() {
        return BillingAddress_Lat;
    }

    public void setBillingAddress_Lat(Double billingAddress_Lat) {
        BillingAddress_Lat = billingAddress_Lat;
    }

    public Double getBillingAddress_Lon() {
        return BillingAddress_Lon;
    }

    public void setBillingAddress_Lon(Double billingAddress_Lon) {
        BillingAddress_Lon = billingAddress_Lon;
    }

    public String getBillingAddress_CountryCode() {
        return BillingAddress_CountryCode;
    }

    public void setBillingAddress_CountryCode(String billingAddress_CountryCode) {
        BillingAddress_CountryCode = billingAddress_CountryCode;
    }

    public String getBillingContact() {
        return BillingContact;
    }

    public void setBillingContact(String billingContact) {
        BillingContact = billingContact;
    }

    public String getBillingContractNo_CountryCode() {
        return BillingContractNo_CountryCode;
    }

    public void setBillingContractNo_CountryCode(String billingContractNo_CountryCode) {
        BillingContractNo_CountryCode = billingContractNo_CountryCode;
    }

    public String getBillingContractNo_AreaCode() {
        return BillingContractNo_AreaCode;
    }

    public void setBillingContractNo_AreaCode(String billingContractNo_AreaCode) {
        BillingContractNo_AreaCode = billingContractNo_AreaCode;
    }

    public String getBillingContractNo_Number() {
        return BillingContractNo_Number;
    }

    public void setBillingContractNo_Number(String billingContractNo_Number) {
        BillingContractNo_Number = billingContractNo_Number;
    }

    public String getBillingContractNo_Extension() {
        return BillingContractNo_Extension;
    }

    public void setBillingContractNo_Extension(String billingContractNo_Extension) {
        BillingContractNo_Extension = billingContractNo_Extension;
    }

    public String getBillingMobileNo_CountryCode() {
        return BillingMobileNo_CountryCode;
    }

    public void setBillingMobileNo_CountryCode(String billingMobileNo_CountryCode) {
        BillingMobileNo_CountryCode = billingMobileNo_CountryCode;
    }

    public String getBillingMobileNo_AreaCode() {
        return BillingMobileNo_AreaCode;
    }

    public void setBillingMobileNo_AreaCode(String billingMobileNo_AreaCode) {
        BillingMobileNo_AreaCode = billingMobileNo_AreaCode;
    }

    public String getBillingMobileNo_Number() {
        return BillingMobileNo_Number;
    }

    public void setBillingMobileNo_Number(String billingMobileNo_Number) {
        BillingMobileNo_Number = billingMobileNo_Number;
    }

    public String getBillingMobileNo_Extension() {
        return BillingMobileNo_Extension;
    }

    public void setBillingMobileNo_Extension(String billingMobileNo_Extension) {
        BillingMobileNo_Extension = billingMobileNo_Extension;
    }
}
