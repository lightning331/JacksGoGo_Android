package com.kelvin.jacksgogo.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGQuotationModel;
import com.kelvin.jacksgogo.Utils.Models.System.JGGRegionModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserProfileModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by PUMA on 1/6/2018.
 */

public class JGGAppManager {

    private static final String TOKEN = "token";
    private static final String EXPIRE_IN = "expire_in";
    private static final String USERNAME_KEY = "username";
    private static final String EMAIL_KEY = "email";
    private static final String PASSWORD_KEY = "password";
    private static final String REGION_KEY = "region";

    private static JGGAppManager appManager = new JGGAppManager();
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    /*
     *  User
     */
    public static JGGUserProfileModel currentUser;
    public static ArrayList<JGGCategoryModel> categories;
    public static ArrayList<JGGRegionModel> regions;
    public static JGGRegionModel currentRegion;

    /*
     *  Appointment
     */
    public static JGGCategoryModel selectedCategory;
    public static JGGAppointmentModel selectedAppointment;
    public static JGGQuotationModel selectedQuotation;

    /*
     *  Proposal
     */
    public static JGGProposalModel selectedProposal;

    private JGGAppManager() {

    }

    public static JGGAppManager getInstance(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
        return appManager;
    }

    public JGGRegionModel getCurrentRegion() {
        currentRegion = regions.get(0);
        return currentRegion;
    }

    public void setCurrentRegion(JGGRegionModel region) {
        this.currentRegion = region;
    }

    public static JGGCategoryModel getSelectedCategory() {
        return selectedCategory;
    }

    public static void setSelectedCategory(JGGCategoryModel category) {
        selectedCategory = category;
    }

    public static JGGAppointmentModel getSelectedAppointment() {
        return selectedAppointment;
    }

    public static void setSelectedAppointment(JGGAppointmentModel appointment) {
        selectedAppointment = appointment;
    }

    public static JGGProposalModel getSelectedProposal() {
        return selectedProposal;
    }

    public static void setSelectedProposal(JGGProposalModel proposal) {
        selectedProposal = proposal;
    }

    public void saveToken(String token, Long expire_in) {
        editor.putString(TOKEN, token);
        editor.putLong(EXPIRE_IN, expire_in);
        editor.commit();
    }

    public String getToken() {
        long expire_in = sharedPreferences.getLong(EXPIRE_IN, 0);
        if (new Date().after(getDateCurrentTimeZone(expire_in))) {
            return sharedPreferences.getString(TOKEN, "");
        }
        return null;
    }

    public void saveUser(String username, String email, String password) {
        editor.putString(EMAIL_KEY, email);
        editor.putString(PASSWORD_KEY, password);
        editor.putString(USERNAME_KEY, username);
        editor.commit();
    }

    public String[] getUsernamePassword() {
        String[] v = new String[3];
        v[0] = sharedPreferences.getString(EMAIL_KEY, "");
        v[1] = sharedPreferences.getString(PASSWORD_KEY, "");
        v[2] = sharedPreferences.getString(USERNAME_KEY, "");
        return v;
    }

    public void removeToken() {
        editor.remove(TOKEN);
        editor.remove(EXPIRE_IN);
        editor.commit();
    }

    private Date getDateCurrentTimeZone(long timestamp) {
        try {
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getDefault();
            calendar.setTimeInMillis(timestamp * 1000);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currenTimeZone = (Date) calendar.getTime();
            return currenTimeZone;
        } catch (Exception e) {
        }
        return null;
    }

    public static void clearAll() {
        editor.clear();
        editor.commit();
        currentUser = null;
    }
}
