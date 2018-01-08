package com.kelvin.jacksgogo.Utils.API;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by PUMA on 1/6/2018.
 */

public class JGGTokenManager {
    private static JGGTokenManager JGGTokenManager = new JGGTokenManager();
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private static final String TOKEN = "token";
    private static final String EXPIRE_IN = "expire_in";

    private JGGTokenManager() {} //prevent creating multiple instances by making the constructor private

    //The context passed into the getInstance should be application level context.
    public static JGGTokenManager getInstance(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
        return JGGTokenManager;
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
//            return sdf.format(currenTimeZone);
        }catch (Exception e) {
        }
        return null;
    }

    public void clearAll() {
        editor.clear();
        editor.commit();
    }
}
