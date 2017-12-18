package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.kelvin.jacksgogo.R;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by PUMA on 11/13/2017.
 */

public class ServiceDetailTotalReviewCell extends RecyclerView.ViewHolder {

    public MaterialRatingBar ratingBar;
    public LinearLayout btnReviews;

    public ServiceDetailTotalReviewCell(View itemView) {
        super(itemView);
        ratingBar = (MaterialRatingBar) itemView.findViewById(R.id.user_total_review_ratingbar);
        btnReviews= (LinearLayout) itemView.findViewById(R.id.btn_see_all_reviews);
    }
}
