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
        EVENTS,
        GOCLUB,
        USERS,
        UNKNOWN
    }

    public static enum JGGUserType {
        PROVIDER,
        CLIENT
    }

    public enum PostStatus {
        POST,
        EDIT,
        DUPLICATE
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
        open(0),
        started(1),
        paused(2),
        held(3),
        end(4),
        flagged(5);

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
        none(null),
        pending(0),
        approved(1),
        rejected(2);

        private Integer value;
        private static Map map = new HashMap<>();

        JobReportStatus(final Integer value) {
            this.value = value;
        }

        static {
            for (JobReportStatus status : JobReportStatus.values()) {
                map.put(status.value, status);
            }
        }

        public static JobReportStatus valueOf(Integer status) {
            return (JobReportStatus) map.get(status);
        }

        public int getValue() {
            return value;
        }
    }

    public static enum JoinGoClubStatus {
        none(null),
        pending(0),
        approved(1),
        withdraw(2),
        rejected(3);

        private Integer value;
        private static Map map = new HashMap<>();

        JoinGoClubStatus(final Integer value) {
            this.value = value;
        }

        static {
            for (JoinGoClubStatus status : JoinGoClubStatus.values()) {
                map.put(status.value, status);
            }
        }

        public static JoinGoClubStatus valueOf(Integer status) {
            return (JoinGoClubStatus) map.get(status);
        }

        public int getValue() {
            return value;
        }
    }

    public static enum AppointmentActivityStatus {
        none(0),
        unknown(99999),

        job_created(100),
        job_edited(101),
        job_closed(102),
        job_confirmed(103),
        job_flagged(104),
        job_deleted(105),
        job_reported(106),
        job_awarded(107),

        service_created(200),
        service_edited(201),
        service_closed(202),
        service_confirmed(203),
        service_flagged(204),
        service_deleted(205),
        service_reported(206),
        service_reschedule_requested(207),
        service_reschedule_declined(208),
        service_reschedule_agreed(208),
        service_rescheduled(210),

        quotation_created(300),
        quotation_edited(301),
        quotation_closed(302),
        quotation_confirmed(303),
        quotation_flagged(304),
        quotation_deleted(305),
        quotation_reported(306),

        proposal_sent(400),
        proposal_edited(401),
        proposal_rejected(402),
        proposal_withdraw(403),
        proposal_approved(404),
        proposal_flagged(405),
        proposal_deleted(406),

        invite_sent(407),
        invite_accepted(408),
        invite_rejected(409),

        contract_created(500),
        contract_started(501),
        contract_paused(502),
        contract_held(503),
        contract_end(504),
        contract_flagged(505),

        result_reported(600),
        result_accepted(601),
        result_rejected(602),

        invoice_sent(603),
        invoice_approved(604),
        give_tip(605),

        client_feedback(620),
        provider_feedback(621);

        private int value;
        private static Map map = new HashMap<>();

        AppointmentActivityStatus(final int value) {
            this.value = value;
        }

        static {
            for (AppointmentActivityStatus status : AppointmentActivityStatus.values()) {
                map.put(status.value, status);
            }
        }

        public static AppointmentActivityStatus valueOf(int status) {
            return (AppointmentActivityStatus) map.get(status);
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
