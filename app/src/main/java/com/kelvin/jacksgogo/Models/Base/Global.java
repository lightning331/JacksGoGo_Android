package com.kelvin.jacksgogo.Models.Base;

/**
 * Created by PUMA on 11/3/2017.
 */


public class Global {
    public enum BiddingStatus {
        PENDING("pending"),
        ACCEPTED("accepted"),
        REJECTED("rejected"),
        DECLINED("declined"),
        NOTRESPONDED("notResponed");

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
