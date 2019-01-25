package com.geo.sleeper.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.geo.sleeper.R;
import com.geo.sleeper.model.OneNightSleepingModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.geo.sleeper.app.SleeperConstants.ZERO;

/**
 * Created by george
 * on 25/01/19.
 */
public class UserSleepReportAdapter extends RecyclerView.Adapter<UserSleepReportAdapter.ViewHolder> {

    private List<OneNightSleepingModel> userSleepHistory;
    public UserSleepReportAdapter(List<OneNightSleepingModel> userSleepHistory) {
        this.userSleepHistory=userSleepHistory;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_user_details,
                        viewGroup,
                        false);
        return new UserSleepReportAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (i == ZERO) {
            viewHolder.viewTop.setVisibility(View.GONE);
        }
        if (i == userSleepHistory.size()- 1) {
            viewHolder.viewBottom.setVisibility(View.GONE);
        }
        viewHolder.tvFirstRecord.setText(userSleepHistory.get(i).getDate());
        viewHolder.tvSecondRecord.setText(userSleepHistory.get(i).getTime());
    }

    @Override
    public int getItemCount() {
        return userSleepHistory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_date)
        TextView tvFirstRecord;

        @BindView(R.id.tv_sleep_time)
        TextView tvSecondRecord;

        @BindView(R.id.view1)
        View viewTop;

        @BindView(R.id.view2)
        View viewBottom;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
