package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Search;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.makeramen.roundedimageview.RoundedImageView;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by PUMA on 11/13/2017.
 */

public class ServiceListCell extends RecyclerView.ViewHolder {

    public ImageView imageViewPhoto;
    public TextView serviceTitle;
    public MaterialRatingBar rateBar;
    public RoundedImageView userAvatar;
    public TextView userName;
    public TextView price;

    public ServiceListCell(View itemView) {
        super(itemView);

        imageViewPhoto = itemView.findViewById(R.id.img_service_photo);
        serviceTitle = itemView.findViewById(R.id.lbl_service_title);
        rateBar = itemView.findViewById(R.id.service_user_ratingbar);
        userAvatar = itemView.findViewById(R.id.img_service_user_avatar);
        userName = itemView.findViewById(R.id.lbl_service_username);
        price = itemView.findViewById(R.id.lbl_service_price);
    }
}
