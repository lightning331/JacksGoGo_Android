package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Profile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

/**
 * Created by PUMA on 1/5/2018.
 */

public class SignupRegionCell extends RecyclerView.ViewHolder {

    private Context mContext;

    public RoundedImageView imgRegion;
    public TextView lblRegion;

    public SignupRegionCell(View itemView, Context context) {
        super(itemView);
        this.mContext = context;

        imgRegion = itemView.findViewById(R.id.img_region);
        lblRegion = itemView.findViewById(R.id.lbl_region);
    }

    public void setImage(String imgUrl) {
        Picasso.with(mContext)
                .load(imgUrl)
                .placeholder(null)
                .into(imgRegion);
    }
}
