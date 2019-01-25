package com.geo.sleeper.app;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.geo.sleeper.model.OneNightSleepingModel;
import com.geo.sleeper.model.UserSleepindDetails;
import com.geo.sleeper.utils.AppUtils;
import com.geo.sleeper.widgets.AccurateTimer;

import java.util.ArrayList;
import java.util.List;

import static com.geo.sleeper.app.SleeperConstants.ZERO;

/**
 * Created by george
 * on 24/01/19.
 */
public class SleeperService extends Service {

    private static final long COUNT_DOWN_INTERVAL = 1000;
    //10 Hours is equal to 36000000 milliseconds
    private static final long TOTAL_TIME = 36000000;
    private static final long TOUNTDOWN_TIME = 20000;
    //45 Minuts is equal to 2700000 milliseconds
    private static final long MIN_SLEEP_TIME = 120000;
    BroadcastReceiver mReceiver = null;
    private AccurateTimer countUpTimer;
    private AccurateTimer countDownTimer;
    private boolean sleepFlag = false;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Register receiver that handles screen on and screen off logic
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        mReceiver = new SleeperReceiver();
        registerReceiver(mReceiver, filter);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        boolean screenOn = false;

        try {
            screenOn = intent.getBooleanExtra("screen_state", false);

        } catch (Exception e) {
            Log.e(e.getMessage(), e.getMessage(), e);
        }
        if (!screenOn) {
            /*
            Screen OFF
             */
            saveSleepTime();
            startWakeupTimer();
        } else {
            /*
              Screen ON
             */
            stopWakeupTimer();
            startTime();
        }
    }

    private void stopWakeupTimer() {
        long wakeUpTime;
        if (countUpTimer != null) {
            wakeUpTime = countUpTimer.getTotalTime();
            countUpTimer.stopTimer();

            String wakeUpTimeInminuts = AppUtils.getFormattedTime(wakeUpTime);
            if (wakeUpTime != ZERO) {
                sleepFlag = true;
            }
            Log.e("***Wakeup Time***", wakeUpTimeInminuts);
        }
    }

    private void startWakeupTimer() {
        countDownTimer = new AccurateTimer(TOUNTDOWN_TIME, COUNT_DOWN_INTERVAL, false);
        countDownTimer.startTimer();
    }

    private void saveSleepTime() {
        long sleepTime;
        String sleepTimeInminuts;
        if (countUpTimer != null) {
            sleepTime = countUpTimer.getTotalTime();
            countUpTimer.stopTimer();
            /*
              sleepFlag is true when user is wakeup for a short time period in between his sleep
              so add his previous sleep time duration.
             */
            if (sleepFlag) {
                long sleepDuration = AppUtils.getTimeInMilliSeconds(SleeperPreference.getInstance(this).
                        getTodaySleepTime(SleeperPreference.KEY_SLEEPING_HISTORY));
                sleepTime = sleepTime + sleepDuration;
            }
            sleepTimeInminuts = AppUtils.getFormattedTime(sleepTime);
            /*
              Sleep time is saved if it is less than 10 hours and greater than 45 Minuts
             */
            if (sleepTime > MIN_SLEEP_TIME) {//sleepTime < TOTAL_TIME && sleepTime > MIN_SLEEP_TIME
                OneNightSleepingModel sleepindDetails = new OneNightSleepingModel();

                sleepindDetails.setDate(AppUtils.getToday());
                sleepindDetails.setTime(sleepTimeInminuts);
                saveTodaysSleep(sleepindDetails);
            }
            Log.e("***Sleep Time***", sleepTimeInminuts);
        }
    }

    private void saveTodaysSleep(OneNightSleepingModel sleepindDetails) {
        UserSleepindDetails userDetails = SleeperPreference.getInstance(this).
                getUserSleepHistory(SleeperPreference.KEY_SLEEPING_HISTORY);
        List<OneNightSleepingModel> sleepingModel;
        if (userDetails != null) {
            sleepingModel = userDetails.getSleepingDetails();
        } else {
            userDetails = new UserSleepindDetails();
            sleepingModel = new ArrayList<>();
        }
        /*
          Each date have only one entry
         */
        if (sleepingModel.size() != ZERO) {
            if (sleepingModel.get(sleepingModel.size() - 1).getDate().equals(AppUtils.getToday())) {
                sleepingModel.remove(sleepingModel.size() - 1);
            }
        }
        sleepingModel.add(sleepindDetails);
        userDetails.setSleepingDetails(sleepingModel);
        SleeperPreference.getInstance(this).saveTodaysSleepTime(userDetails,
                SleeperPreference.KEY_SLEEPING_HISTORY);
    }

    private void startTime() {
        countUpTimer = new AccurateTimer(ZERO, COUNT_DOWN_INTERVAL, true);
        countUpTimer.startTimer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("ScreenOnOff", "Service  distroy");
        if (mReceiver != null)
            unregisterReceiver(mReceiver);
    }
}
