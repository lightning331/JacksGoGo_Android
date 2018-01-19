package com.kelvin.jacksgogo.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.WindowManager;

import com.kelvin.jacksgogo.R;

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

    public enum JGGJobType {
        none(-1),
        repeating(0),
        oneTime(1);

        private int value;

        JGGJobType(final int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "JGGJobType{" +
                    "value=" + value +
                    '}';
        }
    }

    public enum JGGRepetitionType {
        none(-1),
        weekly(0),
        monthly(1);

        private int value;

        JGGRepetitionType(final int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "JGGRepetitionType{" +
                    "value=" + value +
                    '}';
        }
    }

    public static ProgressDialog createProgressDialog(Context mContext) {
        ProgressDialog dialog = new ProgressDialog(mContext);
        try {
            dialog.show();
        } catch (WindowManager.BadTokenException e) {

        }
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.progressdialog);
        //dialog.setMessage(Message);
        return dialog;
    }
}
