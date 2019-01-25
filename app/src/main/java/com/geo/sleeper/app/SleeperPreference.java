package com.geo.sleeper.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geo.sleeper.model.OneNightSleepingModel;
import com.geo.sleeper.model.UserSleepindDetails;

import static com.geo.sleeper.app.SleeperConstants.ZERO;

/**
 * Created by george
 * on 24/01/19.
 */
public class SleeperPreference {

    public static final String KEY_SLEEPING_HISTORY = "SLEEPING DATA";

    @SuppressLint("StaticFieldLeak")
    private static SleeperPreference sSharedManager;
    private SharedPreferences sSharedPref;

    private SleeperPreference(Context context) {
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

    /**
     * Convert String to Object
     *
     * @param message:
     * @param mapperClassName : class name
     * @return : Json object
     */
    private static Object toJsonObject(String message,
                                       Class mapperClassName) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(message,
                    mapperClassName);
        } catch (Exception ex) {
            Log.e(ex.getMessage(), ex.getMessage(), ex);
            return null;
        }
    }

    /**
     * @param object: object
     * @return : JSON string
     */
    private static String toJsonString(Object object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            Log.e(e.getMessage(), e.getMessage(), e);
            return null;
        }
    }

    public void saveTodaysSleepTime(UserSleepindDetails userDetails, String key) {
        saveDataAsJson(key, userDetails);
    }

    public UserSleepindDetails getUserSleepHistory(String key) {
        Object response = getDataAsJson(key,
                UserSleepindDetails.class);
        if (response != null) {
            return (UserSleepindDetails) response;
        }
        return null;
    }

    /**
     * Save Data As Json in Preference
     *
     * @param key:  the key value
     * @param data: the data to be stored
     */
    private void saveDataAsJson(String key, Object data) {
        if (sSharedPref != null) {
            SharedPreferences.Editor editor = sSharedPref.edit();
            String json = toJsonString(data);
            editor.putString(key, json);
            editor.apply();
        }
    }

    private Object getDataAsJson(String key, Class className) {
        if (sSharedPref != null) {
            String json = sSharedPref.getString(key, null);
            if (json != null && json.length() > ZERO) {
                return toJsonObject(json, className);
            } else {
                return null;
            }
        }
        return null;
    }

    public String getTodaySleepTime(String key) {
        UserSleepindDetails userDetails = getUserSleepHistory(key);
        if (userDetails != null) {
            OneNightSleepingModel sleepingModel = userDetails.getSleepingDetails().
                    get(userDetails.getSleepingDetails().size() - 1);
            return sleepingModel.getTime();
        } else {
            return null;
        }
    }
}
