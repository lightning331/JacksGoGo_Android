package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Appointment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.makeramen.roundedimageview.RoundedImageView;

import org.w3c.dom.Text;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by PUMA on 11/13/2017.
 */

public class AppBiddingProviderCell extends RecyclerView.ViewHolder {

    public RoundedImageView avatar;
    public TextView userName;
    public TextView price;
    public TextView status;
    public MaterialRatingBar ratingBar;

    public AppBiddingProviderCell(View itemView) {
        super(itemView);

        avatar = itemView.findViewById(R.id.img_bidding_provider_avatar);
        userName = itemView.findViewById(R.id.lbl_bidding_provider_username);
        price = itemView.findViewById(R.id.lbl_bidding_provider_price);
        ratingBar = itemView.findViewById(R.id.bidding_provider_ratingBar);

    }
}
