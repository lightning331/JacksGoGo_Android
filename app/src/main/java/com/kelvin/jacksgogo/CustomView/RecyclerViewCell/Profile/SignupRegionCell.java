package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Profile;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Created by PUMA on 1/5/2018.
 */

public class SignupRegionCell extends RecyclerView.ViewHolder {

    public RoundedImageView imgRegion;
    public TextView lblRegion;

    public SignupRegionCell(View itemView) {
        super(itemView);

        imgRegion = itemView.findViewById(R.id.img_region);
        lblRegion = itemView.findViewById(R.id.lbl_region);
    }
}
