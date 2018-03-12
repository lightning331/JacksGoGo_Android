package com.kelvin.jacksgogo.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.WindowManager;

import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGJobModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentBaseModel.appointmentMonthDate;

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
        none(null),
        repeating(0),
        oneTime(1);

        private Integer value;
        private static Map map = new HashMap<>();

        JGGJobType(final Integer value) {
            this.value = value;
        }

        static {
            for (JGGJobType jobType : JGGJobType.values()) {
                map.put(jobType.value, jobType);
            }
        }

        public static JGGJobType valueOf(Integer jobType) {
            return (JGGJobType) map.get(jobType);
        }

        public Integer getValue() {
            return value;
        }
    }

    public enum JGGRepetitionType {
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

    public enum JGGProposalStatus {
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

    public enum TimeSlotSelectionStatus {
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

    public static List<Integer> selectedID(int reportType) {
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

    public static String getDays(Date date) {
        if (date != null) {
            SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
            String day = dayFormat.format(date);
            return day;
        }
        return "";
    }

    public static String getDayMonthString(Date date) {
        if (date != null) {
            SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
            SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
            String day = dayFormat.format(date);
            String month = monthFormat.format(date);
            try {
                Date varDate=monthFormat.parse(month);
                monthFormat=new SimpleDateFormat("MMM");
                month = monthFormat.format(varDate);
            }catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

            return day + " " + month;
        }
        return "";
    }

    public static String getDayMonthYear(Date date) {
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");
            String dateString = dateFormat.format(date);
            return dateString;
        }
        return "";
    }

    public static String getTimePeriodString(Date date) {
        if (date != null) {
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
        return "";
    }

    public static String getTimeString(Date date) {
        if (date != null) {
            SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
            SimpleDateFormat minuteFormat = new SimpleDateFormat("mm");
            SimpleDateFormat secondFormat = new SimpleDateFormat("ss");
            //SimpleDateFormat mSecondFormat = new SimpleDateFormat("SSS");
            String hour = hourFormat.format(date);
            String minute = minuteFormat.format(date);
            String second = secondFormat.format(date);
            //String mSecond = mSecondFormat.format(date);
            String time = hour + ":" + minute + ":" + second;// + "." + mSecond;
            return time;
        }
        return "";
    }

    public static String getHourString(Date date) {
        if (date != null) {
            SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
            String hour = hourFormat.format(date);
            String time = hour;
            return time;
        }
        return "";
    }

    public static String getMinuteString(Date date) {
        if (date != null) {
            SimpleDateFormat minuteFormat = new SimpleDateFormat("mm");
            String minute = minuteFormat.format(date);
            //String mSecond = mSecondFormat.format(date);
            String time = minute;
            return time;
        }
        return "";
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

    public static String getPeriod(boolean status) {
        if (status)
            return "AM";
        return "PM";
    }

    public static String convertJobTimeString(JGGJobModel job) {
        String time = "";
        if (job.getAppointmentType() == 1) {
            if (job.getSessions() != null
                    && job.getSessions().size() > 0) {
                if (job.getSessions().get(0).isSpecific()) {
                    if (job.getSessions().get(0).getEndOn() != null)
                        time = "on "
                                + getDayMonthYear(appointmentMonthDate(job.getSessions().get(0).getStartOn()))
                                + " " + getTimePeriodString(appointmentMonthDate(job.getSessions().get(0).getStartOn()))
                                + " - "
                                + getTimePeriodString(appointmentMonthDate(job.getSessions().get(0).getEndOn()));
                    else
                        time = "on "
                                + getDayMonthYear(appointmentMonthDate(job.getSessions().get(0).getStartOn()))
                                + " " + getTimePeriodString(appointmentMonthDate(job.getSessions().get(0).getStartOn()));
                } else {
                    if (job.getSessions().get(0).getEndOn() != null)
                        time = "any time until "
                                + getDayMonthYear(appointmentMonthDate(job.getSessions().get(0).getStartOn()))
                                + " " + getTimePeriodString(appointmentMonthDate(job.getSessions().get(0).getStartOn()))
                                + " - "
                                + getTimePeriodString(appointmentMonthDate(job.getSessions().get(0).getEndOn()));
                    else
                        time = "any time until "
                                + getDayMonthYear(appointmentMonthDate(job.getSessions().get(0).getStartOn()))
                                + " " + getTimePeriodString(appointmentMonthDate(job.getSessions().get(0).getStartOn()));
                }
            }
        } else if (job.getAppointmentType() == 0) {
            String dayString = job.getRepetition();
            if (dayString != null) {
                String[] items = dayString.split(",");
                if (job.getRepetitionType() == Global.JGGRepetitionType.weekly) {
                    for (int i = 0; i < items.length; i ++) {
                        if (time.equals(""))
                            time = "Every " + Global.getWeekName(Integer.parseInt(items[i]));
                        else
                            time = time + ", " + "Every " + Global.getWeekName(Integer.parseInt(items[i]));
                    }
                } else if (job.getRepetitionType() == Global.JGGRepetitionType.monthly) {
                    for (int i = 0; i < items.length; i ++) {
                        if (time.equals(""))
                            time = "Every " + Global.getDayName(Integer.parseInt(items[i])) + " of the month";
                        else
                            time = time + ", " + "Every " + Global.getDayName(Integer.parseInt(items[i])) + " of the month";
                    }
                }
            }
        }
        return time;
    }

    public static String convertJobBudgetString(JGGJobModel jobModel) {
        String budget = "";
        if (jobModel.getBudget() == null && jobModel.getBudgetFrom() == null)
            budget =  "No limit";
        else if (jobModel.getBudget() != null)
            budget =  "Fixed $ " + jobModel.getBudget().toString();
        else if (jobModel.getBudgetFrom() != null && jobModel.getBudgetTo() != null)
            budget =  ("From $ " + jobModel.getBudgetFrom().toString()
                    + " "
                    + "to $ " + jobModel.getBudgetTo().toString());
        return budget;
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
