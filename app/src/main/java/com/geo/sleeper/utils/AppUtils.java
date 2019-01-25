package com.geo.sleeper.utils;

import android.annotation.SuppressLint;

import com.geo.sleeper.app.SleeperConstants;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.geo.sleeper.app.SleeperConstants.ZERO;

/**
 * Created by george
 * on 24/01/19.
 */
public class AppUtils {

    private static final String TIME_SEPERATOR = " : ";
    private static final long SECOND_IN_MILLISECONDS = 1000;
    private static final long MINUTE_IN_MILLISECONDS = 60000;
    private static final long HOURS_IN_MILLISECONDS = 3600000;
    private static final String TWO_CHAR_FORMATTER = "00";

    public static String getFormattedTime(long elapsedTime) {
        String formattedTime;
        long hours = ZERO;
        long minuts;
        if (elapsedTime >= HOURS_IN_MILLISECONDS) {
            hours = elapsedTime / HOURS_IN_MILLISECONDS;
        }
        minuts = (elapsedTime % HOURS_IN_MILLISECONDS) / MINUTE_IN_MILLISECONDS;
        String minutesText = (TWO_CHAR_FORMATTER + String.valueOf(hours)).
                substring(String.valueOf(hours).length());
        String secondsText = (TWO_CHAR_FORMATTER + String.valueOf(minuts)).
                substring(String.valueOf(minuts).length());
        formattedTime = minutesText + TIME_SEPERATOR + secondsText;
        return formattedTime;
    }

    public static long getTimeInMilliSeconds(String time) {
        long duration = ZERO;
        if (time != null) {
            String[] timeDuration = time.split(TIME_SEPERATOR);
            duration = (Long.parseLong(timeDuration[ZERO]) * HOURS_IN_MILLISECONDS) +
                    (Long.parseLong(timeDuration[1]) * MINUTE_IN_MILLISECONDS);

        }
        return duration;
    }

    public static String getToday() {
        Date today = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat =
                new SimpleDateFormat(SleeperConstants.SCHEDULE_DATE_FORMAT);
        return String.valueOf(dateFormat.format(today));
    }
}
