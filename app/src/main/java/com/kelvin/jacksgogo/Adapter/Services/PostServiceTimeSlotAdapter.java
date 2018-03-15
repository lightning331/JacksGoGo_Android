package com.kelvin.jacksgogo.Adapter.Services;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.System.JGGTimeSlotModel;

import java.util.List;

import static com.kelvin.jacksgogo.Utils.JGGTimeManager.appointmentMonthDate;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getTimePeriodString;

/**
 * Created by PUMA on 1/30/2018.
 */

public class PostServiceTimeSlotAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<JGGTimeSlotModel> mList;

    public PostServiceTimeSlotAdapter(Context context, List<JGGTimeSlotModel> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_post_service_time_slot, parent, false);
        PostServiceTimeSlotViewHolder holder = new PostServiceTimeSlotViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        PostServiceTimeSlotViewHolder cell = (PostServiceTimeSlotViewHolder) holder;
        cell.setData(mList.get(position));
        cell.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(false, position);
            }
        });
        cell.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(true, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(boolean isDelete, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    class PostServiceTimeSlotViewHolder extends RecyclerView.ViewHolder {

        public TextView lblTime;
        public TextView lblPax;
        public ImageView btnEdit;
        public ImageView btnDelete;

        public PostServiceTimeSlotViewHolder(View itemView) {
            super(itemView);

            lblTime = (TextView) itemView.findViewById(R.id.lbl_time_slots_time);
            lblPax = (TextView) itemView.findViewById(R.id.lbl_time_slots_pax);
            btnEdit = (ImageView) itemView.findViewById(R.id.btn_time_slots_edit);
            btnDelete = (ImageView) itemView.findViewById(R.id.btn_time_slots_delete);
        }

        public void setData(JGGTimeSlotModel time) {
            String startTime = getTimePeriodString(appointmentMonthDate(time.getStartOn()));
            String endTime = "";
            if (time.getEndOn() != null) {
                endTime = getTimePeriodString(appointmentMonthDate(time.getEndOn()));
                lblTime.setText(startTime + " - " + endTime);
            } else {
                lblTime.setText(startTime);
            }
            if (time.getPeoples() != null) {
                lblPax.setVisibility(View.VISIBLE);
                lblPax.setText("" + time.getPeoples() + " pax");
            }
        }
    }
}