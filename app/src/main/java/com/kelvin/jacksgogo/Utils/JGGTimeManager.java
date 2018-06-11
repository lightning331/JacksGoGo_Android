package com.kelvin.jacksgogo.Utils;

import com.kelvin.jacksgogo.Utils.Models.GoClub_Event.JGGEventModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel;
import com.kelvin.jacksgogo.Utils.Models.System.JGGTimeSlotModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static com.kelvin.jacksgogo.Utils.Global.JGGRepetitionType;

/**
 * Created by PUMA on 3/15/2018.
 */

public class JGGTimeManager {

    public static String TIMESLOT_MORNING = "10:00 AM";
    public static String TIMESLOT_AFTERNOON = "01:00 PM";
    public static String TIMESLOT_EVEN = "04:00 PM";

    public static String getAppointmentDay(Date date) {
        if (date != null) {
            SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
            String day = dayFormat.format(date);
            return day;
        }
        return "";
    }

    public static String getAppointmentMonth(Date date) {
        if (date != null) {
            SimpleDateFormat monthFormat = new SimpleDateFormat("MMM");
            String month = monthFormat.format(date);
            return month;
        }
        return "";
    }

    public static String getAppointmentYear(Date date) {
        if (date != null) {
            SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
            String year = yearFormat.format(date);
            return year;
        }
        return "";
    }

    public static Date appointmentDate(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMM-dd'T'HH:mm:ss");
        try {

            if (dateString != null) {
                Date date = formatter.parse(dateString);
                return date;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date appointmentMonthDate(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {

            if (dateString != null) {
                Date date = formatter.parse(dateString);
                return date;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date convertUTCTimeToLocalTime(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {

            if (dateString != null) {
                Date date = formatter.parse(dateString);
                return date;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertUTCTimeString(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {

            if (dateString != null) {
                Date date = formatter.parse(dateString);
                String timeString = appointmentMonthDateString(date);
                return timeString;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertUTCTimeString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        if (date != null) {
            String timeString = formatter.format(date);
            return timeString;
        }

        return null;
    }

    public static String appointmentDateString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd'T'HH:mm:ss");
        if (date != null) {
            String dateString = dateFormat.format(date);
            return dateString;
        }
        return "";
    }

    public static String appointmentMonthDateString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        if (date != null) {
            String dateString = dateFormat.format(date);
            return dateString;
        }
        return "";
    }

    public static String appointmentNewDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd");
        String newTime = "";
        try {
            Date varDate = dateFormat.parse(appointmentDateString(date));
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String newHour = getTimeString(date);
            newTime = dateFormat.format(varDate);
            newTime = newTime + "T" + newHour;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return newTime;
    }

    public static String convertCalendarDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd");
        String month = "";
        try {
            Date varDate = dateFormat.parse(appointmentDateString(date));
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            month = dateFormat.format(varDate);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return month;
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
                hour = Integer.toString(Integer.parseInt(hour) - 12);
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
            String hour = hourFormat.format(date);
            String minute = minuteFormat.format(date);
            String second = secondFormat.format(date);
            String time = hour + ":" + minute + ":" + second;// + "." + mSecond;
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

    public static JGGTimeSlotModel getTimeSlot(int position, Date date) {
        JGGTimeSlotModel timeSlot = new JGGTimeSlotModel();
        String month = convertCalendarDate(date);
        switch (position) {
            case 1:
                timeSlot.setStartOn(month + "T" + "10:00:00");
                timeSlot.setEndOn(month + "T" + "12:00:00");
                return timeSlot;
            case 2:
                timeSlot.setStartOn(month + "T" + "13:00:00");
                timeSlot.setEndOn(month + "T" + "15:00:00");
                return timeSlot;
            case 3:
                timeSlot.setStartOn(month + "T" + "16:00:00");
                timeSlot.setEndOn(month + "T" + "18:00:00");
                return timeSlot;
            default:
                return timeSlot;
        }
    }

    public static int getTimeSlotPosition(JGGTimeSlotModel time) {
        String period = getTimePeriodString(time.getStartOn());
        if (period.equals(TIMESLOT_MORNING))
            return 1;
        else if (period.equals((TIMESLOT_AFTERNOON)))
            return 2;
        else if (period.equals((TIMESLOT_EVEN)))
            return 3;

        return 0;
    }

    public static String getAppointmentTime(JGGAppointmentModel job) {
        String time = "";
        if (job.getAppointmentType() == 1) {
            if (job.getSessions() != null
                    && job.getSessions().size() > 0) {
                if (job.getSessions().get(0).getSpecific() == null) {
                    if (job.getSessions().get(0).getEndOn() != null)
                        time = "on "
                                + getDayMonthYear(job.getSessions().get(0).getStartOn())
                                + " " + getTimePeriodString(job.getSessions().get(0).getStartOn())
                                + " - "
                                + getTimePeriodString(job.getSessions().get(0).getEndOn());
                    else
                        time = "on "
                                + getDayMonthYear(job.getSessions().get(0).getStartOn())
                                + " " + getTimePeriodString(job.getSessions().get(0).getStartOn());
                } else {
                    if (job.getSessions().get(0).getSpecific()) {
                        if (job.getSessions().get(0).getEndOn() != null)
                            time = "on "
                                    + getDayMonthYear(job.getSessions().get(0).getStartOn())
                                    + " " + getTimePeriodString(job.getSessions().get(0).getStartOn())
                                    + " - "
                                    + getTimePeriodString(job.getSessions().get(0).getEndOn());
                        else
                            time = "on "
                                    + getDayMonthYear(job.getSessions().get(0).getStartOn())
                                    + " " + getTimePeriodString(job.getSessions().get(0).getStartOn());
                    } else {
                        if (job.getSessions().get(0).getEndOn() == null
                                && job.getSessions().get(0).getStartOn() == null)
                            time = "Any Day";
                        else {
                            if (job.getSessions().get(0).getEndOn() == null)
                                time = "any time until "
                                        + getDayMonthYear(job.getSessions().get(0).getStartOn())
                                        + " " + getTimePeriodString(job.getSessions().get(0).getStartOn());
                            else
                                time = "any time until "
                                        + getDayMonthYear(job.getSessions().get(0).getStartOn())
                                        + " " + getTimePeriodString(job.getSessions().get(0).getStartOn())
                                        + " - "
                                        + getTimePeriodString(job.getSessions().get(0).getEndOn());
                        }
                    }
                }
            }
        } else if (job.getAppointmentType() == 0) {
            String dayString = job.getRepetition();
            if (dayString != null && dayString.length() > 0) {
                String [] items;
                items = dayString.split(",");
                if (job.getRepetitionType() == JGGRepetitionType.weekly) {
                    for (int i = 0; i < items.length; i ++) {
                        if (time.equals(""))
                            time = "Every " + getWeekName(Integer.parseInt(items[i]) - 1);
                        else
                            time = time + ", " + "Every " + getWeekName(Integer.parseInt(items[i]) - 1);
                    }
                } else if (job.getRepetitionType() == JGGRepetitionType.monthly) {
                    for (int i = 0; i < items.length; i ++) {
                        if (time.equals(""))
                            time = "Every " + getDayName(Integer.parseInt(items[i]) - 1) + " of the month";
                        else
                            time = time + ", " + "Every " + getDayName(Integer.parseInt(items[i]) - 1) + " of the month";
                    }
                }
            }
        }
        return time;
    }

    public static String getEventTime(JGGEventModel event) {
        String time = "";

        //if (event.getOnetime() == null) {

            // Todo - Repeating Event

            if (event.getSessions().get(0).getEndOn() == null)
                time = getDayMonthYear(event.getSessions().get(0).getStartOn())
                        + " " + getTimePeriodString(event.getSessions().get(0).getStartOn());
            else
                time = getDayMonthYear(event.getSessions().get(0).getStartOn())
                        + " " + getTimePeriodString(event.getSessions().get(0).getStartOn())
                        + " - "
                        + getTimePeriodString(event.getSessions().get(0).getEndOn());
        //} else {

            // Todo - One-time Event

        //}

        return time;
    }

    public static String getAppointmentBudgetWithString(JGGAppointmentModel jobModel) {
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

    public static String getAppointmentBudget(JGGAppointmentModel app) {
        String budget = "";
        if (app.getBudget() == null && app.getBudgetFrom() == null)
            budget =  "No limit";
        else {
            if (app.getBudget() != null) {
                if (app.getAppointmentType() == null) {
                    budget = "$ " + app.getBudget().toString();
                } else {
                    if (app.getAppointmentType() == 1)
                        budget = "$ " + app.getBudget().toString();
                    else if (app.getAppointmentType() >= 2)
                        budget = "$ " + app.getBudget().toString() + " for " + String.valueOf(app.getAppointmentType() + " sessions");
                }
            } else if (app.getBudgetFrom() != null && app.getBudgetTo() != null) {
                budget = ("$ " + app.getBudgetFrom().toString()
                        + " - "
                        + "$ " + app.getBudgetTo().toString());
            }
        }
        return budget;
    }

    public static String getProposalBudget(JGGProposalModel proposal) {
        String budget = "";
        if (proposal.getBudget() == null && proposal.getBudgetFrom() == null)
            budget =  "No limit";
        else {
            if (proposal.getBudget() != null) {
                budget = "$ " + proposal.getBudget().toString();
            } else if (proposal.getBudgetFrom() != null && proposal.getBudgetTo() != null) {
                budget = ("$ " + proposal.getBudgetFrom().toString()
                        + " - "
                        + "$ " + proposal.getBudgetTo().toString());
            }
        }
        return budget;
    }
}
