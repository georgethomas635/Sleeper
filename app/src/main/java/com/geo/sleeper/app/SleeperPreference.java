package com.geo.sleeper.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.geo.sleeper.app.SleeperConstants.ZERO;

/**
 * Created by george
 * on 24/01/19.
 */
public class SleeperPreference {

    public static final String KEY_SLEEPING_DATA = "SLEEPING DATA";
    public static final String KEY_WAKEUP_DATA = "WAKEUP DATA";

    @SuppressLint("StaticFieldLeak")
    private static SleeperPreference sSharedManager;
    private Context context;
    private SharedPreferences sSharedPref;

    private SleeperPreference(Context context) {
        this.context = context;
        setUpDefaultSharedPreferences(context);
    }

    public static SleeperPreference getInstance(Context context) {
        if (sSharedManager == null) {
            sSharedManager = new SleeperPreference(context);
        }
        return sSharedManager;
    }

    private void setUpDefaultSharedPreferences(Context context) {
        sSharedPref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void saveTime(int timeInMinit, String key) {
        if (sSharedPref != null) {
            sSharedPref.edit().putInt(key, timeInMinit).apply();
        }
    }

    public int getTime(String key) {
        if(sSharedPref != null){
            return sSharedPref.getInt(key,ZERO);
        } else {
            return ZERO;
        }
    }
}
