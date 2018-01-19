package com.kelvin.jacksgogo.CustomView.Views;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global;

/**
 * Created by PUMA on 1/19/2018.
 */

public class RepeatingDayDialog extends android.app.AlertDialog.Builder {

    private Context mContext;

    private RecyclerView recyclerView;
    private TextView btnCancel;
    private TextView btnDone;

    private Global.JGGRepetitionType repeatingType;

    public RepeatingDayDialog(Context context, Global.JGGRepetitionType type) {
        super(context);
        mContext = context;
        repeatingType = type;

        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.dialog_repeating_day, null);

        recyclerView = (RecyclerView) view.findViewById(R.id.post_job_time_day_recycler_view);
        btnCancel = (TextView) view.findViewById(R.id.btn_alert_cancel);
        btnDone = (TextView) view.findViewById(R.id.btn_alert_ok);

        if (repeatingType == Global.JGGRepetitionType.weekly) {
            RepeatingJobWeeklyAdapter weeklyAdapter = new RepeatingJobWeeklyAdapter(mContext);
            recyclerView.setAdapter(weeklyAdapter);
        } else if (repeatingType == Global.JGGRepetitionType.monthly) {
            RepeatingJobMonthlyAdapter monthlyAdapter = new RepeatingJobMonthlyAdapter(mContext);
            recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
            recyclerView.setAdapter(monthlyAdapter);
        }

        this.setView(view);
    }
}

class RepeatingJobWeeklyAdapter extends RecyclerView.Adapter {

    private Context mContext;

    public String[] weekNames = {
            "Sunday",
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday"
    };

    public RepeatingJobWeeklyAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_post_job_time_weekly, parent, false);
        RepeatingJobWeeklyViewHolder holder = new RepeatingJobWeeklyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RepeatingJobWeeklyViewHolder viewHolder = (RepeatingJobWeeklyViewHolder) holder;
        viewHolder.btnWeekly.setText(weekNames[position]);
    }

    @Override
    public int getItemCount() {
        return weekNames.length;
    }
}

class RepeatingJobWeeklyViewHolder extends RecyclerView.ViewHolder {

    public TextView btnWeekly;

    public RepeatingJobWeeklyViewHolder(View itemView) {
        super(itemView);

        btnWeekly = (TextView) itemView.findViewById(R.id.btn_post_job_time_weekly);
    }
}

class RepeatingJobMonthlyAdapter extends RecyclerView.Adapter {

    private Context mContext;

    public String[] dayNames = {
            "1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th", "9th", "10th",
            "11th", "12th", "13th", "14th", "15th", "16th", "17th", "18th", "19th", "20th",
            "21st", "22nd", "23rd", "24th", "25th", "26th", "27th", "28th", "29th", "30th", "31st"
    };

    public RepeatingJobMonthlyAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_post_job_time_monthly, parent, false);
        RepeatingJobMonthlyViewHolder holder = new RepeatingJobMonthlyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RepeatingJobMonthlyViewHolder viewHolder = (RepeatingJobMonthlyViewHolder) holder;
        viewHolder.btnMonthly.setText(dayNames[position]);
    }

    @Override
    public int getItemCount() {
        return dayNames.length;
    }
}

class RepeatingJobMonthlyViewHolder extends RecyclerView.ViewHolder {

    public TextView btnMonthly;

    public RepeatingJobMonthlyViewHolder(View itemView) {
        super(itemView);

        btnMonthly = (TextView) itemView.findViewById(R.id.btn_post_job_time_monthly);
    }
}
