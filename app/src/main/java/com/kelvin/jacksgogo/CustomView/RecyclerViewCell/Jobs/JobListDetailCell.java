package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by PUMA on 12/18/2017.
 */

public class JobListDetailCell extends RecyclerView.ViewHolder {

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

    public JobListDetailCell(View itemView) {
        super(itemView);
    }
}
