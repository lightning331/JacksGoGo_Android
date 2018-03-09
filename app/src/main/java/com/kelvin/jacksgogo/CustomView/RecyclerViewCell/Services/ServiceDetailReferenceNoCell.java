package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/13/2017.
 */

public class ServiceDetailReferenceNoCell extends RecyclerView.ViewHolder {

    public TextView lblTitle;
    public TextView lblNumber;
    public TextView lblPostedTime;

    public ServiceDetailReferenceNoCell(View itemView) {
        super(itemView);

        lblTitle = itemView.findViewById(R.id.lbl_reference_no_title);
        lblNumber = itemView.findViewById(R.id.lbl_reference_no);
        lblPostedTime = itemView.findViewById(R.id.lbl_posted_time);
    }
}
