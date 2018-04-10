package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/13/2017.
 */

public class JobDetailLocationCell extends RecyclerView.ViewHolder {

    public TextView lblDescription;
    public LinearLayout location;
    public TextView address;
    public ImageView imgLocation;
    public ImageView imgLocationRight;
    public TextView lblQuotePrice;

    public JobDetailLocationCell(View itemView) {
        super(itemView);

        lblDescription = itemView.findViewById(R.id.lbl_location);
        location = itemView.findViewById(R.id.btn_location);
        address = itemView.findViewById(R.id.lbl_job_detail_address);
        imgLocation = itemView.findViewById(R.id.img_location);
        imgLocationRight = itemView.findViewById(R.id.img_location_right);
        lblQuotePrice = itemView.findViewById(R.id.lbl_quote_price);
    }
}
