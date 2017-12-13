package com.kelvin.jacksgogo.Models.Base;

/**
 * Created by PUMA on 11/3/2017.
 */


public class Global {
    public enum BiddingStatus {
        PENDING("Pending"),
        NEWPROPOSAL("NewProposal"),
        ACCEPTED("Accepted"),
        REJECTED("Rejected"),
        DECLINED("Declined"),
        NOTRESPONDED("NotResponed");

        private String value;

        BiddingStatus(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return this.getValue();
        }
    }
}
