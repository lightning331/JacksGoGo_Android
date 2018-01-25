package com.kelvin.jacksgogo.CustomView.Views;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.Search.PostServiceActivity;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.Models.RepeatingDayModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PUMA on 1/19/2018.
 */

public class RepeatingDayDialog extends android.app.AlertDialog.Builder implements View.OnClickListener {

    private Context mContext;

    private RecyclerView recyclerView;
    private TextView btnCancel;
    private TextView btnDone;

    private Global.JGGRepetitionType repeatingType;
    private boolean isMultiSelect = false;
    private List<Integer> selectedIds = new ArrayList<>();
    private RepeatingJobWeeklyAdapter weeklyAdapter;
    private RepeatingJobMonthlyAdapter monthlyAdapter;
    private RepeatingDayModel data;

    public RepeatingDayDialog(Context context, Global.JGGRepetitionType type) {
        super(context);
        mContext = context;
        repeatingType = type;

        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.dialog_repeating_day, null);
        btnCancel = (TextView) view.findViewById(R.id.btn_alert_cancel);
        btnDone = (TextView) view.findViewById(R.id.btn_alert_ok);
        btnCancel.setOnClickListener(this);
        btnDone.setOnClickListener(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.post_job_time_day_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.VERTICAL, false));

        if (repeatingType == Global.JGGRepetitionType.weekly) {
            weeklyAdapter = new RepeatingJobWeeklyAdapter(mContext, getList());
            weeklyAdapter.setSelectedIds(selectedIds);
            recyclerView.setAdapter(weeklyAdapter);
        } else if (repeatingType == Global.JGGRepetitionType.monthly) {
            monthlyAdapter = new RepeatingJobMonthlyAdapter(mContext, getList());
            monthlyAdapter.setSelectedIds(selectedIds);
            recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
            recyclerView.setAdapter(monthlyAdapter);
        }
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(mContext, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (!isMultiSelect){
                    selectedIds = new ArrayList<>();
                    isMultiSelect = true;
                }
                multiSelect(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));

        this.setView(view);
    }

    private void multiSelect(int position) {

        if (repeatingType == Global.JGGRepetitionType.weekly) {
            data = weeklyAdapter.getItem(position);
        } else if (repeatingType == Global.JGGRepetitionType.monthly) {
            data = monthlyAdapter.getItem(position);
        }

        if (data != null){

            if (repeatingType == Global.JGGRepetitionType.weekly) {
                if (selectedIds.contains(data.getId()))
                    selectedIds.remove(Integer.valueOf(data.getId()));
                else
                    selectedIds.add(data.getId());
                weeklyAdapter.setSelectedIds(selectedIds);
            } else if (repeatingType == Global.JGGRepetitionType.monthly) {
                if (selectedIds.contains(data.getId()))
                    selectedIds.remove(Integer.valueOf(data.getId()));
                else
                    selectedIds.add(data.getId());
                monthlyAdapter.setSelectedIds(selectedIds);
            }
        }
    }

    private List<RepeatingDayModel> getList() {
        List<RepeatingDayModel> list = new ArrayList<>();
        if (repeatingType == Global.JGGRepetitionType.monthly) {
            list.add(new RepeatingDayModel(1,"1"));
            list.add(new RepeatingDayModel(2,"2"));
            list.add(new RepeatingDayModel(3,"3"));
            list.add(new RepeatingDayModel(4,"4"));
            list.add(new RepeatingDayModel(5,"5"));
            list.add(new RepeatingDayModel(6,"6"));
            list.add(new RepeatingDayModel(7,"7"));
            list.add(new RepeatingDayModel(8,"8"));
            list.add(new RepeatingDayModel(9,"9"));
            list.add(new RepeatingDayModel(10,"10"));
            list.add(new RepeatingDayModel(11,"11"));
            list.add(new RepeatingDayModel(12,"12"));
            list.add(new RepeatingDayModel(13,"13"));
            list.add(new RepeatingDayModel(14,"14"));
            list.add(new RepeatingDayModel(15,"15"));
            list.add(new RepeatingDayModel(16,"16"));
            list.add(new RepeatingDayModel(17,"17"));
            list.add(new RepeatingDayModel(18,"18"));
            list.add(new RepeatingDayModel(19,"19"));
            list.add(new RepeatingDayModel(20,"20"));
            list.add(new RepeatingDayModel(21,"21"));
            list.add(new RepeatingDayModel(22,"22"));
            list.add(new RepeatingDayModel(23,"23"));
            list.add(new RepeatingDayModel(24,"24"));
            list.add(new RepeatingDayModel(25,"25"));
            list.add(new RepeatingDayModel(26,"26"));
            list.add(new RepeatingDayModel(27,"27"));
            list.add(new RepeatingDayModel(28,"28"));
            list.add(new RepeatingDayModel(29,"29"));
            list.add(new RepeatingDayModel(30,"30"));
            list.add(new RepeatingDayModel(31,"31"));
        } else if (repeatingType == Global.JGGRepetitionType.weekly) {
            list.add(new RepeatingDayModel(1,"Sunday"));
            list.add(new RepeatingDayModel(2,"Monday"));
            list.add(new RepeatingDayModel(3,"Tuesday"));
            list.add(new RepeatingDayModel(4,"Wednesday"));
            list.add(new RepeatingDayModel(5,"Thursday"));
            list.add(new RepeatingDayModel(6,"Friday"));
            list.add(new RepeatingDayModel(7,"Saturday"));
        }
        return list;
    }

    @Override
    public void onClick(View view) {
        listener.onDoneButtonClick(view, selectedIds);
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onDoneButtonClick(View view, List<Integer> days);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}

class RepeatingJobWeeklyAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<RepeatingDayModel> list;
    private List<Integer> selectedIds = new ArrayList<>();

    public RepeatingJobWeeklyAdapter(Context context, List<RepeatingDayModel> list) {
        mContext = context;
        this.list = list;
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
        viewHolder.btnWeekly.setText(list.get(position).getTitle());
        int id = list.get(position).getId();

        if (selectedIds.contains(id)){
            //if item is selected then,set foreground color of FrameLayout.
            viewHolder.btnWeekly.setBackgroundResource(R.drawable.cyan_background);
            viewHolder.btnWeekly.setTextColor(ContextCompat.getColor(mContext, R.color.JGGWhite));
        }
        else {
            //else remove selected item color.
            viewHolder.btnWeekly.setBackgroundResource(R.drawable.cyan_border_background);
            viewHolder.btnWeekly.setTextColor(ContextCompat.getColor(mContext, R.color.JGGCyan));
        }
    }

    public void setSelectedIds(List<Integer> selectedIds) {
        this.selectedIds = selectedIds;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public RepeatingDayModel getItem(int position){
        return list.get(position);
    }

    class RepeatingJobWeeklyViewHolder extends RecyclerView.ViewHolder {

        public TextView btnWeekly;

        public RepeatingJobWeeklyViewHolder(View itemView) {
            super(itemView);

            btnWeekly = (TextView) itemView.findViewById(R.id.btn_post_job_time_weekly);
        }
    }
}

class RepeatingJobMonthlyAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<RepeatingDayModel> list;
    private List<Integer> selectedIds = new ArrayList<>();

    public RepeatingJobMonthlyAdapter(Context context, List<RepeatingDayModel> list) {
        mContext = context;
        this.list = list;
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
        viewHolder.btnMonthly.setText(list.get(position).getTitle());
        int id = list.get(position).getId();

        if (selectedIds.contains(id)){
            //if item is selected then,set foreground color of FrameLayout.
            viewHolder.btnMonthly.setBackgroundResource(R.drawable.cyan_background);
            viewHolder.btnMonthly.setTextColor(ContextCompat.getColor(mContext, R.color.JGGWhite));
        }
        else {
            //else remove selected item color.
            viewHolder.btnMonthly.setBackgroundResource(R.drawable.cyan_border_background);
            viewHolder.btnMonthly.setTextColor(ContextCompat.getColor(mContext, R.color.JGGCyan));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public RepeatingDayModel getItem(int position){
        return list.get(position);
    }

    public void setSelectedIds(List<Integer> selectedIds) {
        this.selectedIds = selectedIds;
        notifyDataSetChanged();
    }

    class RepeatingJobMonthlyViewHolder extends RecyclerView.ViewHolder {

        public TextView btnMonthly;

        public RepeatingJobMonthlyViewHolder(View itemView) {
            super(itemView);

            btnMonthly = (TextView) itemView.findViewById(R.id.btn_post_job_time_monthly);
        }
    }
}

