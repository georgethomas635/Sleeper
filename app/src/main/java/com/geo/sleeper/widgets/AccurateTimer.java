package com.geo.sleeper.widgets;

import android.os.Handler;

import static com.geo.sleeper.app.SleeperConstants.ZERO;

/**
 * Created by george
 * on 24/01/19.
 */
public class AccurateTimer {


    private Handler handler;
    private long delay;
    private long totalTime;
    private Runnable runnable;
    private boolean countUp;

    public AccurateTimer(long totalTime, long delay, boolean countUp) {
        this.delay = delay;
        this.totalTime = totalTime;
        handler = new Handler();
        this.countUp = countUp;
    }


    public void startTimer() {
        handler.postDelayed(new Runnable() {
            public void run() {
                runnable = this;
                if (!countUp) {
                    totalTime = totalTime - delay;
                    if (totalTime < ZERO) {
                        stopTimer();
                        return;
                    }
                } else {
                    totalTime = totalTime + delay;
                }
                handler.postDelayed(runnable, delay);
            }
        }, delay);

    }

    public void stopTimer() {
        handler.removeCallbacks(runnable);
    }

    public long getTotalTime() {
        return totalTime != 0 ? totalTime : ZERO;
    }
}
