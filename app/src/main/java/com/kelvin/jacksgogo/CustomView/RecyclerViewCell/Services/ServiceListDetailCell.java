package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by PUMA on 11/13/2017.
 */

public class ServiceListDetailCell extends RecyclerView.ViewHolder {

    public RelativeLayout likeButtonLayout;
    public ImageView btnLike;
    public ImageView imgCategoryDetail;
    public ImageView imgCategory;
    public TextView lblCategoryName;
    public MaterialRatingBar rateBar;
    public TextView lblScore;
    public TextView lblScoreStatus;
    public TextView lblReviewCount;
    public TextView lblAddress;
    public TextView price;
    public TextView bookedCount;
    public LinearLayout btnNext;

    public ServiceListDetailCell(View itemView) {
        super(itemView);

        likeButtonLayout = itemView.findViewById(R.id.like_button_layout);
        btnLike = itemView.findViewById(R.id.btn_like);
        imgCategoryDetail = itemView.findViewById(R.id.img_service_detail_photo);
        imgCategory = itemView.findViewById(R.id.img_service_detail_category);
        lblCategoryName = itemView.findViewById(R.id.lbl_service_detail_category_name);
        rateBar = itemView.findViewById(R.id.service_detail_user_ratingbar);
        lblScore = itemView.findViewById(R.id.lbl_service_detail_score);
        lblScoreStatus = itemView.findViewById(R.id.lbl_service_detail_score_status);
        lblReviewCount = itemView.findViewById(R.id.lbl_service_detail_review_count);
        lblAddress = itemView.findViewById(R.id.lbl_service_detail_address);
        price = itemView.findViewById(R.id.lbl_service_detail_price);
        bookedCount = itemView.findViewById(R.id.service_detail_booked_count);
        btnNext = itemView.findViewById(R.id.btn_service_detail_next);
    }
}
