package com.geo.sleeper.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.geo.sleeper.R;
import com.geo.sleeper.adapters.UserSleepReportAdapter;
import com.geo.sleeper.app.SleeperPreference;
import com.geo.sleeper.app.SleeperService;
import com.geo.sleeper.model.UserSleepindDetails;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardActivity extends AppCompatActivity {

    @BindView(R.id.rl_user_list)
    RecyclerView rvUserList;
    @BindView(R.id.image)
    ImageView imgEmpty;

    private UserSleepReportAdapter userReportAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        Intent intent = new Intent(this,SleeperService.class);
        intent.putExtra("action","com.example.neotavraham.PLAY");
        startService(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        showUserSleepRecord(SleeperPreference.getInstance(this).getUserSleepHistory(SleeperPreference.KEY_SLEEPING_HISTORY));
    }

    private void showUserSleepRecord(UserSleepindDetails userSleepHistory) {
        if(userSleepHistory!=null) {
            imgEmpty.setVisibility(View.GONE);
            //Setup grid layout manager
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            rvUserList.setLayoutManager(layoutManager);
            userReportAdapter = new UserSleepReportAdapter(userSleepHistory.getSleepingDetails());
            rvUserList.setAdapter(userReportAdapter);
        }else {
            imgEmpty.setVisibility(View.VISIBLE);
        }
    }
}
