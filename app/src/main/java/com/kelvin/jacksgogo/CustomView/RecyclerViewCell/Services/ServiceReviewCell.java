package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.makeramen.roundedimageview.RoundedImageView;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by PUMA on 11/24/2017.
 */

public class ServiceReviewCell extends RecyclerView.ViewHolder {

   public RoundedImageView userAvatar;
    public MaterialRatingBar ratingBar;
    public TextView lblReviewDate;
    public TextView lblReviewDesc;

    public ServiceReviewCell(View itemView) {
        super(itemView);

        userAvatar = itemView.findViewById(R.id.img_service_review_avatar);
        ratingBar = itemView.findViewById(R.id.service_reviews_ratingbar);
        lblReviewDate = itemView.findViewById(R.id.lbl_service_review_date);
        lblReviewDesc = itemView.findViewById(R.id.lbl_service_reviews_description);
    }
}
