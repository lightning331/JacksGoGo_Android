package com.kelvin.jacksgogo.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.WindowManager;

import com.kelvin.jacksgogo.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public static Date getDate(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");
        try {

            Date date = formatter.parse(dateString);
            return date;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getWeekName(int position) {
        String[] weekNames = {
                "Sunday",
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday",
                "Saturday"
        };
        return weekNames[position];
    }

    public static String getDayName(int position) {
        String[] dayNames = {
                "1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th", "9th", "10th",
                "11th", "12th", "13th", "14th", "15th", "16th", "17th", "18th", "19th", "20th",
                "21st", "22nd", "23rd", "24th", "25th", "26th", "27th", "28th", "29th", "30th", "31st"
        };
        return dayNames[position];
    }

    public static String getTimeString(Date date) {
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
        SimpleDateFormat minuteFormat = new SimpleDateFormat("mm");
        boolean period = true;
        String hour = hourFormat.format(date);
        if (Integer.parseInt(hour) > 12) {
            hour = "0" + Integer.toString(Integer.parseInt(hour) - 12);
            period = false;
        }
        if (Integer.parseInt(hour) == 12)
            period = false;
        String minute = minuteFormat.format(date);
        String time = "";
        if (period)
            time = hour + ":" + minute + " AM";
        else
            time = hour + ":" + minute + " PM";
        return time;
    }

    public static String getPeriod(boolean status) {
        if (status)
            return "AM";
        return "PM";
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
