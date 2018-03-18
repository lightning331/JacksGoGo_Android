package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserProfileModel;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by PUMA on 11/13/2017.
 */

public class UserNameRatingCell extends RecyclerView.ViewHolder {

    private Context mContext;

    public TextView name;
    public MaterialRatingBar ratingBar;
    public RoundedImageView avatar;
    public LinearLayout btnReviews;
    public RelativeLayout likeButtonLayout;

    public UserNameRatingCell(Context context, View itemView) {
        super(itemView);
        mContext = context;

        this.name = itemView.findViewById(R.id.lbl_username);
        this.ratingBar = itemView.findViewById(R.id.user_ratingbar);
        this.avatar = itemView.findViewById(R.id.img_avatar);
        this.btnReviews = itemView.findViewById(R.id.btn_view_all_services);
        this.likeButtonLayout = itemView.findViewById(R.id.like_button_layout);
    }

    public void setData(JGGUserProfileModel user) {
        Picasso.with(mContext)
                .load(user.getUser().getPhotoURL())
                .placeholder(R.mipmap.icon_profile)
                .into(avatar);
        if (user.getUser().getGivenName() == null)
            name.setText(user.getUser().getUserName());
        else
            name.setText(user.getUser().getFullName());
        Double rating = user.getUser().getRate();
        if (rating == null)
            ratingBar.setRating(0);
        else
            ratingBar.setRating(rating.floatValue());
    }
}
