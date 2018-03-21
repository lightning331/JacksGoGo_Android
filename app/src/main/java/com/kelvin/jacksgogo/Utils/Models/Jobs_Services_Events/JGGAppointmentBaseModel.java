package com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events;

import com.kelvin.jacksgogo.Utils.Global.BiddingStatus;
import com.kelvin.jacksgogo.Utils.Models.System.JGGAddressModel;
import com.kelvin.jacksgogo.Utils.Models.System.JGGCurrencyModel;
import com.kelvin.jacksgogo.Utils.Models.System.JGGRegionModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserProfileModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by PUMA on 1/18/2018.
 */

public class JGGAppointmentBaseModel {

    private String Title;
    private String Description;
    private String Tags;
    private String RegionID;
    private JGGRegionModel Region = new JGGRegionModel();
    private JGGCurrencyModel Currency = new JGGCurrencyModel();
    private String CurrencyCode;
    private String UserProfileID;
    private JGGUserProfileModel UserProfile = new JGGUserProfileModel();
    private String PostOn;  // Originally date type
    private int Status;
    private JGGAddressModel Address = new JGGAddressModel();
    private JGGAddressModel DAddress = new JGGAddressModel();

    public static enum JGGProposalStatus {

        none(0),
        pending(1),
        newproposal(2),
        accepted(3),
        rejected(4),
        declined(5),
        notresponded(6),
        withdrawn(7),
        completed(8),
        waitingforreview(9);

        private int value;
        private static Map map = new HashMap<>();

        JGGProposalStatus(final int value) {
            this.value = value;
        }

        static {
            for (JGGProposalStatus status : JGGProposalStatus.values()) {
                map.put(status.value, status);
            }
        }

        public static JGGProposalStatus valueOf(int status) {
            return (JGGProposalStatus) map.get(status);
        }

        public int getValue() {
            return value;
        }
    }


    public JGGAppointmentBaseModel() {
        super();
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getTags() {
        return Tags;
    }

    public void setTags(String tags) {
        Tags = tags;
    }

    public String getRegionID() {
        return RegionID;
    }

    public void setRegionID(String regionID) {
        RegionID = regionID;
    }

    public JGGRegionModel getRegion() {
        return Region;
    }

    public void setRegion(JGGRegionModel region) {
        Region = region;
    }

    public JGGCurrencyModel getCurrency() {
        return Currency;
    }

    public void setCurrency(JGGCurrencyModel currency) {
        Currency = currency;
    }

    public String getCurrencyCode() {
        return CurrencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        CurrencyCode = currencyCode;
    }

    public String getUserProfileID() {
        return UserProfileID;
    }

    public void setUserProfileID(String userProfileID) {
        UserProfileID = userProfileID;
    }

    public JGGUserProfileModel getUserProfile() {
        return UserProfile;
    }

    public void setUserProfile(JGGUserProfileModel userProfile) {
        UserProfile = userProfile;
    }

    public String getPostOn() {
        return PostOn;
    }

    public void setPostOn(String postOn) {
        PostOn = postOn;
    }

    public JGGProposalStatus getStatus() {
        return JGGProposalStatus.valueOf(Status);
    }

    public void setStatus(JGGProposalStatus status) {
        Status = status.getValue();
    }

    public JGGAddressModel getAddress() {
        return Address;
    }

    public void setAddress(JGGAddressModel address) {
        Address = address;
    }

    public JGGAddressModel getDAddress() {
        return DAddress;
    }

    public void setDAddress(JGGAddressModel DAddress) {
        this.DAddress = DAddress;
    }
}
