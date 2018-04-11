package com.kelvin.jacksgogo.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.WindowManager;

import com.kelvin.jacksgogo.CustomView.CustomTypefaceSpan;
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
    public static final String PENDING = "PENDING";
    public static final String CONFIRMED = "CONFIRMED";
    public static final String HISTORY = "HISTORY";
    public static final String SERVICES = "SERVICES";
    public static final String JOBS = "JOBS";
    public static final String EVENTS = "EVENTS";
    public static final String GOCLUB = "GOCLUB";
    public static final String USERS = "USERS";

    public static final String SIGNUP_FINISHED = "SIGNUP_FINISHED";
    public static final String EDIT_STATUS = "EDIT_STATUS";
    public static final String POST = "POST";
    public static final String INVITE_PROPOSAL = "INVITE";
    public static final String EDIT = "EDIT";
    public static final String ACCEPTED = "ACCEPTED";
    public static final String MY_PROPOSAL = "MY_PROPOSAL";
    public static final String VIEW = "VIEW";
    public static final String DUPLICATE = "DUPLICATE";
    public static final String NONE = "NONE";
    public static final String JGG_USERTYPE = "JGG_USERTYPE";

    public static final int MINUTES_IN_AN_HOUR = 60;
    public static final int SECONDS_IN_A_MINUTE = 60;
    public static final int REQUEST_CODE = 1;

    public enum AppointmentType {
        JOBS,
        SERVICES,
        EVENT,
        GOCLUB,
        USERS,
        UNKNOWN
    }

    public static enum JGGUserType {
        PROVIDER,
        CLIENT
    }

    public static enum JGGJobStatus {

        open(0),        // Posted
        closed(1),      // Client rejected provider's proposal
        confirmed(2),   // Client accepted provider's proposal
        finished(3),    //
        flaged(4),      // Provider declined Client's invite
        deleted(5),     // Provider declined Client's invite
        started(6);     // Provider started the work

        private Integer value;
        private static Map map = new HashMap<>();

        JGGJobStatus(final Integer value) {
            this.value = value;
        }

        static {
            for (JGGJobStatus status : JGGJobStatus.values()) {
                map.put(status.value, status);
            }
        }

        public static JGGJobStatus valueOf(Integer status) {
            return (JGGJobStatus) map.get(status);
        }

        public Integer getValue() {
            return value;
        }
    }

    public static enum JGGProposalStatus {

        open(0),        // Posted
        rejected(1),    // Client rejected provider's proposal
        confirmed(2),   // Client accepted provider's proposal
        withdrawn(3),   //
        declined(4);    // Provider declined Client's invite

        private Integer value;
        private static Map map = new HashMap<>();

        JGGProposalStatus(final Integer value) {
            this.value = value;
        }

        static {
            for (JGGProposalStatus status : JGGProposalStatus.values()) {
                map.put(status.value, status);
            }
        }

        public static JGGProposalStatus valueOf(Integer status) {
            return (JGGProposalStatus) map.get(status);
        }

        public Integer getValue() {
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

    public static enum ActiveAppointmentStatus {
        category(0),
        active(1),
        joined(2);

        private int value;
        private static Map map = new HashMap<>();

        ActiveAppointmentStatus(final int value) {
            this.value = value;
        }

        static {
            for (ActiveAppointmentStatus status : ActiveAppointmentStatus.values()) {
                map.put(status.value, status);
            }
        }

        public static ActiveAppointmentStatus valueOf(int status) {
            return (ActiveAppointmentStatus) map.get(status);
        }

        public int getValue() {
            return value;
        }
    }

    public static String getProposalStatus(JGGProposalStatus status) {
        switch (status) {
            case rejected:
                return "Rejected";
            case confirmed:
                return "Confirmed";
            case withdrawn:
                return "Withdrawn";
            case declined:
                return "Declined";
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

    public static List<Integer> getReportTypeID(int reportType) {
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

    public static SpannableString setBoldText(String s) {
        Typeface muliBold = Typeface.create("mulibold", Typeface.BOLD);
        SpannableString spannableString = new SpannableString(s);
        spannableString.setSpan(new CustomTypefaceSpan("", muliBold), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
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
