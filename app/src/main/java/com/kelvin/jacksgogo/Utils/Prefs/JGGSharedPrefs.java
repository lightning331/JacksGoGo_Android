package com.kelvin.jacksgogo.Utils.Prefs;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by storm on 4/24/2018.
 */

public class JGGSharedPrefs {
    /**
     * PREFS_NAME is a file name which generates inside data folder of application
     */
    private static final String PREFS_NAME = "JacksGoGoPrefs";

    private static final String TOKEN = "token";
    private static final String EXPIRE_IN = "expire_in";
    private static final String USERNAME_KEY = "username";
    private static final String EMAIL_KEY = "email";
    private static final String PASSWORD_KEY = "password";
    private static final String REGION_KEY = "region";

    static SharedPreferences sp;
    static SharedPreferences.Editor prefEditor = null;

    private static Context mContext = null;
    public static JGGSharedPrefs instance = null;

    public static JGGSharedPrefs getInstance(Context context) {
        mContext = context;
        if (instance == null) {
            instance = new JGGSharedPrefs();
        }
        sp = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefEditor = sp.edit();
        return instance;
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

    public void saveToken(String token, Long expire_in) {
        prefEditor.putString(TOKEN, token);
        prefEditor.putLong(EXPIRE_IN, expire_in);
        prefEditor.commit();
    }

    public String getToken() {
        long expire_in = sp.getLong(EXPIRE_IN, 0);
        if (new Date().after(getDateCurrentTimeZone(expire_in))) {
            return sp.getString(TOKEN, "");
        }
        return null;
    }

    public void saveUser(String username, String email, String password) {
        prefEditor.putString(EMAIL_KEY, email);
        prefEditor.putString(PASSWORD_KEY, password);
        prefEditor.putString(USERNAME_KEY, username);
        prefEditor.commit();
    }

    public String[] getUsernamePassword() {
        String[] v = new String[3];
        v[0] = sp.getString(EMAIL_KEY, "");
        v[1] = sp.getString(PASSWORD_KEY, "");
        v[2] = sp.getString(USERNAME_KEY, "");
        return v;
    }

    public void removeToken() {
        prefEditor.remove(TOKEN);
        prefEditor.remove(EXPIRE_IN);
        prefEditor.commit();
    }

    public void clearAll() {
        prefEditor.clear();
        prefEditor.commit();
    }
}
