package com.kelvin.jacksgogo.Utils.API;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;
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
    private static final String PASSWORD_KEY = "password";
    private static final String REGION_KEY = "region";

    private static JGGAppManager appManager = new JGGAppManager();
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public JGGUserProfileModel currentUser;
    public ArrayList<JGGCategoryModel> categories;
    public ArrayList<JGGRegionModel> regions;
    public JGGRegionModel currentRegion;

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

    public void setCurrentRegion(JGGRegionModel currentRegion) {
        this.currentRegion = currentRegion;
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

    public void saveUser(String username, String password) {
        editor.putString(USERNAME_KEY, username);
        editor.putString(PASSWORD_KEY, password);
        editor.commit();
    }

    public String[] getUsernamePassword() {
        String[] v = new String[2];
        v[0] = sharedPreferences.getString(USERNAME_KEY, "");
        v[1] = sharedPreferences.getString(PASSWORD_KEY, "");
        return v;
    }

    public void removeToken() {
        editor.remove(TOKEN);
        editor.remove(EXPIRE_IN);
        editor.commit();
    }

    public Date getDateCurrentTimeZone(long timestamp) {
        try{
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getDefault();
            calendar.setTimeInMillis(timestamp * 1000);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currenTimeZone = (Date) calendar.getTime();
            return currenTimeZone;
        }catch (Exception e) {
        }
        return null;
    }

    public void clearAll() {
        editor.clear();
        editor.commit();
    }
}
