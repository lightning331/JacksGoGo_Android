package com.kelvin.jacksgogo.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.WindowManager;

import com.kelvin.jacksgogo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by PUMA on 11/3/2017.
 */


public class Global {

    public static final String APPOINTMENT_TYPE = "APPOINTMENT_TYPE";
    public static final String SERVICES = "SERVICES";
    public static final String JOBS = "JOBS";
    public static final String EVENTS = "EVENTS";
    public static final String GOCLUB = "GOCLUB";
    public static final String USERS = "USERS";
    public static final String SIGNUP_FINISHED = "SIGNUP_FINISHED";
    public static final String EDIT_STATUS = "EDIT_STATUS";
    public static final String POST = "POST";
    public static final String EDIT = "EDIT";
    public static final String DUPLICATE = "DUPLICATE";
    public static final String NONE = "NONE";

    public static final int MINUTES_IN_AN_HOUR = 60;
    public static final int SECONDS_IN_A_MINUTE = 60;


    public static enum BiddingStatus {

        none(0),
        pending(1),
        newproposal(2),
        accepted(3),
        rejected(4),
        declined(5),
        notresponded(6);

        private int value;
        private static Map map = new HashMap<>();

        BiddingStatus(final int value) {
            this.value = value;
        }

        static {
            for (BiddingStatus status : BiddingStatus.values()) {
                map.put(status.value, status);
            }
        }

        public static BiddingStatus valueOf(int status) {
            return (BiddingStatus) map.get(status);
        }

        public int getValue() {
            return value;
        }
    }

    public static enum JGGBudgetType {
        none(null),
        nolimit(1),
        fixed(2),
        from(3);

        private Integer value;
        private static Map map = new HashMap<>();

        JGGBudgetType(final Integer value) {
            this.value = value;
        }

        static {
            for (JGGBudgetType jobType : JGGBudgetType.values()) {
                map.put(jobType.value, jobType);
            }
        }

        public static JGGBudgetType valueOf(Integer jobType) {
            return (JGGBudgetType) map.get(jobType);
        }

        public Integer getValue() {
            return value;
        }
    }

    public static enum JGGAppointmentType {
        none(null),     // Not Selected
        repeating(0),   // Repeating JOB
        onetime(1);     // One-time JOB, One person at a time(in Service TimeSlot)
                        // If AppointmentType greater than 1(onetime), It's Multiple people at a time(in Service TimeSlot)

        private Integer value;
        private static Map map = new HashMap<>();

        JGGAppointmentType(final Integer value) {
            this.value = value;
        }

        static {
            for (JGGAppointmentType jobTimeSlotType : JGGAppointmentType.values()) {
                map.put(jobTimeSlotType.value, jobTimeSlotType);
            }
        }

        public static JGGAppointmentType valueOf(Integer jobTimeSlotType) {
            return (JGGAppointmentType) map.get(jobTimeSlotType);
        }

        public Integer getValue() {
            return value;
        }
    }

    public static enum JGGRepetitionType {
        none(null),
        weekly(0),
        monthly(1);

        private Integer value;
        private static Map map = new HashMap<>();

        JGGRepetitionType(final Integer value) {
            this.value = value;
        }

        static {
            for (JGGRepetitionType repetitionType : JGGRepetitionType.values()) {
                map.put(repetitionType.value, repetitionType);
            }
        }

        public static JGGRepetitionType valueOf(Integer repetitionType) {
            return (JGGRepetitionType) map.get(repetitionType);
        }

        public Integer getValue() {
            return value;
        }
    }

    public static enum JGGProposalStatus {
        none(null),
        weekly(0),
        monthly(1);

        private Integer value;
        private static Map map = new HashMap<>();

        JGGProposalStatus(final Integer value) {
            this.value = value;
        }

        static {
            for (JGGProposalStatus proposalStatus : JGGProposalStatus.values()) {
                map.put(proposalStatus.value, proposalStatus);
            }
        }

        public static JGGProposalStatus valueOf(Integer proposalStatus) {
            return (JGGProposalStatus) map.get(proposalStatus);
        }

        public Integer getValue() {
            return value;
        }
    }

    public static enum TimeSlotSelectionStatus {
        none(null),
        progress(0),
        now(1),
        done(2);

        private Integer value;
        private static Map map = new HashMap<>();

        TimeSlotSelectionStatus(final Integer value) {
            this.value = value;
        }

        static {
            for (TimeSlotSelectionStatus status : TimeSlotSelectionStatus.values()) {
                map.put(status.value, status);
            }
        }

        public static TimeSlotSelectionStatus valueOf(Integer status) {
            return (TimeSlotSelectionStatus) map.get(status);
        }

        public Integer getValue() {
            return value;
        }
    }

    public static String getBiddingStatus(BiddingStatus status) {
        switch (status) {
            case none:
                return "";
            case pending:
                return "Pending";
            case newproposal:
                return "New Proposal";
            case accepted:
                return "Accepted";
            case rejected:
                return "Rejected";
            case declined:
                return "Declined";
            case notresponded:
                return "Not Responded";
            default:
                return "";
        }
    }

    public static String reportTypeName(int reportType) {
        switch (reportType) {
            case 1:
                return "Before & After Photo";
            case 2:
                return "Geotracking";
            case 3:
                return "Before & After Photo" + ", " + "Geotracking";
            case 4:
                return "PIN Code";
            case 5:
                return "Before & After Photo" + ", " + "PIN Code";
            case 6:
                return "Geotracking" + ", " + "PIN Code";
            case 7:
                return "Before & After Photo" + ", " + "Geotracking" + ", " + "PIN Code";
            default:
                return "No set";
        }
    }

    public static List<Integer> selectedCategoryID(int reportType) {
        List<Integer> array = new ArrayList<>();
        switch (reportType) {
            case 1:
                array.add(1);
                return array;
            case 2:
                array.add(2);
                return array;
            case 3:
                array.add(1);
                array.add(2);
                return array;
            case 4:
                array.add(3);
                return array;
            case 5:
                array.add(1);
                array.add(3);
                return array;
            case 6:
                array.add(2);
                array.add(3);
                return array;
            case 7:
                array.add(1);
                array.add(2);
                array.add(3);
                return array;
            default:
                return array;
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
