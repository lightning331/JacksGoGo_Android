package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Edit;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.System.JGGTimeSlotModel;

import static com.kelvin.jacksgogo.Utils.JGGTimeManager.appointmentMonthDate;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getTimePeriodString;

/**
 * Created by PUMA on 11/13/2017.
 */

public class EditJobTimeSlotsCell extends RecyclerView.ViewHolder {

    public TextView lblSlots;
    public TextView btnSlots;

    public EditJobTimeSlotsCell(View itemView) {
        super(itemView);

        lblSlots = (TextView) itemView.findViewById(R.id.lbl_time_slots_time);
        btnSlots = (TextView) itemView.findViewById(R.id.btn_time_slots_status);
    }

    public void setData(JGGTimeSlotModel slotModel) {
        String startTime = getTimePeriodString(appointmentMonthDate(slotModel.getStartOn()));
        String endTime = "";
        if (slotModel.getEndOn() != null) {
            endTime = getTimePeriodString(appointmentMonthDate(slotModel.getEndOn()));
            lblSlots.setText(startTime + " - " + endTime);
        } else {
            lblSlots.setText(startTime);
        }
    }
}
