package com.geo.sleeper.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.geo.sleeper.R;
import com.geo.sleeper.app.SleeperService;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Intent intent = new Intent(this,SleeperService.class);
        intent.putExtra("action","com.example.neotavraham.PLAY");
        startService(intent);
    }
}
