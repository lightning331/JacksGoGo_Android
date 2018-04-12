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
        flagged(4),     // Provider declined Client's invite
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
        no_limit(1),
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

    public static enum ContractStatus {
        OPEN(0),
        STARTED(1),
        PAUSED(2),
        HOLD(3),
        END(4),
        FLAGGED(5);

        private int value;
        private static Map map = new HashMap<>();

        ContractStatus(final int value) {
            this.value = value;
        }

        static {
            for (ContractStatus status : ContractStatus.values()) {
                map.put(status.value, status);
            }
        }

        public static ContractStatus valueOf(int status) {
            return (ContractStatus) map.get(status);
        }

        public int getValue() {
            return value;
        }
    }

    public static enum JobReportStatus {
        PENDING(0),
        APPROVED(1),
        REJECTED(2);

        private int value;
        private static Map map = new HashMap<>();

        JobReportStatus(final int value) {
            this.value = value;
        }

        static {
            for (JobReportStatus status : JobReportStatus.values()) {
                map.put(status.value, status);
            }
        }

        public static JobReportStatus valueOf(int status) {
            return (JobReportStatus) map.get(status);
        }

        public int getValue() {
            return value;
        }
    }

    public static enum AppointmentHistoryStatus {
        JOB_CREATED(100),
        JOB_EDITED(101),
        JOB_CLOSED(102),
        JOB_CONFIRMED(103),
        JOB_FLAGGED(104),
        JOB_DELETED(105),
        JOB_REPORTED(106),

        SERVICE_CREATED(200),
        SERVICE_EDITED(201),
        SERVICE_CLOSED(202),
        SERVICE_CONFIRMED(203),
        SERVICE_FLAGGED(204),
        SERVICE_DELETED(205),
        SERVICE_REPORTED(206),
        SERVICE_RESCHEDULE_REQUESTED(207),
        SERVICE_RESCHEDULE_DECLINED(208),
        SERVICE_RESCHEDULE_AGREED(208),
        SERVICE_RESCHEDULED(210),

        QUOTATION_CREATED(300),
        QUOTATION_EDITED(301),
        QUOTATION_CLOSED(302),
        QUOTATION_CONFIRMED(303),
        QUOTATION_FLAGGED(304),
        QUOTATION_DELETED(305),
        QUOTATION_REPORTED(306),

        PROPOSAL_SENT(400),
        PROPOSAL_EDITED(401),
        PROPOSAL_REJECTED(402),
        PROPOSAL_WITHDRAW(403),
        PROPOSAL_APPROVED(404),
        PROPOSAL_FLAGGED(405),
        PROPOSAL_DELETED(406),
        INVITE_SENT(407),
        INVITE_ACCEPTED(408),
        INVITE_REJECTED(409),

        CONTRACT_CREATED(500),
        CONTRACT_STARTED(501),
        CONTRACT_PAUSED(502),
        CONTRACT_HOLD(503),
        CONTRACT_END(504),
        CONTRACT_FLAGGED(505),

        RESULT_REPORTED(600),
        RESULT_ACCEPTED(601),
        RESULT_REJECTED(602),
        INVOICE_SENT(603),
        INVOICE_APPROVED(604),
        CLIENT_FEEDBACK(620),
        PROVIDER_FEEDBACK(621);

        private int value;
        private static Map map = new HashMap<>();

        AppointmentHistoryStatus(final int value) {
            this.value = value;
        }

        static {
            for (AppointmentHistoryStatus status : AppointmentHistoryStatus.values()) {
                map.put(status.value, status);
            }
        }

        public static AppointmentHistoryStatus valueOf(int status) {
            return (AppointmentHistoryStatus) map.get(status);
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
