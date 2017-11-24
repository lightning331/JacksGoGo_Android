package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/13/2017.
 */

public class ServiceDetailTimeSlotsCell extends RecyclerView.ViewHolder {

    public LinearLayout btnViewTimeSlots;

    public ServiceDetailTimeSlotsCell(View itemView) {
        super(itemView);

        btnViewTimeSlots = itemView.findViewById(R.id.btn_time_slots);
    }
}
