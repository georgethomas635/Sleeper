package com.geo.sleeper.model;

import java.util.List;

/**
 * Created by george
 * on 24/01/19.
 */
public class UserSleepindDetails {

    private List<OneNightSleepingModel> sleepingDetails;

    public List<OneNightSleepingModel> getSleepingDetails() {
        return sleepingDetails;
    }

    public void setSleepingDetails(List<OneNightSleepingModel> sleepingDetails) {
        this.sleepingDetails = sleepingDetails;
    }
}
