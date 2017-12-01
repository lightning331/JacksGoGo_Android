package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Edit;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/13/2017.
 */

public class EditJobTimeSlotsCell extends RecyclerView.ViewHolder {

    public TextView lblSlots;
    public TextView btnSlots;

    public EditJobTimeSlotsCell(View itemView) {
        super(itemView);

        lblSlots = itemView.findViewById(R.id.lbl_time_slots_time);
        btnSlots = itemView.findViewById(R.id.btn_time_slots_status);
    }
}
