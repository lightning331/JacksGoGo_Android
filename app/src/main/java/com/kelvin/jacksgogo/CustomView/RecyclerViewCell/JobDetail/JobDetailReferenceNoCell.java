package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/13/2017.
 */

public class JobDetailReferenceNoCell extends RecyclerView.ViewHolder {

    public TextView lblReferenceNo;
    public TextView lblPostedDate;

    public JobDetailReferenceNoCell(View itemView) {
        super(itemView);

        lblReferenceNo = itemView.findViewById(R.id.lbl_reference_no);
        lblPostedDate = itemView.findViewById(R.id.lbl_reference_posted_date);
    }
}
