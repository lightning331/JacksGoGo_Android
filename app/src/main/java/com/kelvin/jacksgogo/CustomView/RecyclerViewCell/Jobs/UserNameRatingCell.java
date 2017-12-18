package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.makeramen.roundedimageview.RoundedImageView;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by PUMA on 11/13/2017.
 */

public class UserNameRatingCell extends RecyclerView.ViewHolder {

    public TextView name;
    public MaterialRatingBar ratingBar;
    public RoundedImageView avatar;
    public LinearLayout btnReviews;
    public RelativeLayout likeButtonLayout;

    public UserNameRatingCell(View itemView) {
        super(itemView);

        this.name = itemView.findViewById(R.id.lbl_username);
        this.ratingBar = itemView.findViewById(R.id.user_ratingbar);
        this.avatar = itemView.findViewById(R.id.img_avatar);
        this.btnReviews = itemView.findViewById(R.id.btn_view_all_services);
        this.likeButtonLayout = itemView.findViewById(R.id.like_button_layout);
    }
}
