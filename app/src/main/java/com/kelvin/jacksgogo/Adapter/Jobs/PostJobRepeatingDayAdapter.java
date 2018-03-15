package com.kelvin.jacksgogo.Adapter.Jobs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global;
import static com.kelvin.jacksgogo.Utils.Global.JGGRepetitionType;
import java.util.ArrayList;
import java.util.List;

import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getDayName;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getWeekName;

/**
 * Created by PUMA on 1/20/2018.
 */

public class PostJobRepeatingDayAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<Integer> selectedRepeatingDays = new ArrayList<Integer>();
    private Global.JGGRepetitionType mType;

    public PostJobRepeatingDayAdapter(Context context, List<Integer> selectedDays, Global.JGGRepetitionType type) {
        mContext = context;
        selectedRepeatingDays = selectedDays;
        mType = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_post_job_repeating_day, parent, false);
        RepeatingDayViewHolder holder = new RepeatingDayViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RepeatingDayViewHolder cell = (RepeatingDayViewHolder) holder;
        if (selectedRepeatingDays.size() == 0) {
            cell.btnClose.setVisibility(View.GONE);
            cell.btnDay.setText(null);
            cell.btnDay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(view, position);
                }
            });
        }
        else {
            Integer day = selectedRepeatingDays.get(position);
            if (mType == JGGRepetitionType.weekly) {
                cell.btnDay.setText("Every " + getWeekName(day.intValue()));
            } else if (mType == JGGRepetitionType.monthly) {
                cell.btnDay.setText("Every " + getDayName(day.intValue()) + " of the month.");
            }
            cell.btnClose.setVisibility(View.VISIBLE);
            cell.btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(view, position);
                    selectedRepeatingDays.remove(position);
                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (selectedRepeatingDays.size() == 0) return 1;
        return selectedRepeatingDays.size();
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    class RepeatingDayViewHolder extends RecyclerView.ViewHolder {

        public TextView btnDay;
        public ImageView btnClose;

        public RepeatingDayViewHolder(View itemView) {
            super(itemView);

            btnDay = (TextView) itemView.findViewById(R.id.btn_post_job_repeating_day);
            btnClose = (ImageView) itemView.findViewById(R.id.btn_post_job_repeating_day_close);
        }
    }
}