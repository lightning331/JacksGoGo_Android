package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Appointment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserProfileModel;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by PUMA on 11/13/2017.
 */

public class AppInviteProviderCell extends RecyclerView.ViewHolder {

    private Context mContext;

    public RoundedImageView avatarImage;
    public TextView userName;
    public MaterialRatingBar ratingBar;
    public LinearLayout btnInvite;

    public AppInviteProviderCell(Context context, View itemView) {
        super(itemView);

        avatarImage = itemView.findViewById(R.id.img_invite_provider_avatar);
        userName = itemView.findViewById(R.id.lbl_invite_provider_username);
        ratingBar = itemView.findViewById(R.id.invite_provider_ratingBar);
        btnInvite = itemView.findViewById(R.id.btn_invite);
    }

    public void setData(JGGUserProfileModel user) {
        Picasso.with(mContext)
                .load(user.getUser().getPhotoURL())
                .placeholder(R.mipmap.icon_profile)
                .into(avatarImage);
        if (user.getUser().getGivenName() == null)
            userName.setText(user.getUser().getUserName());
        else
            userName.setText(user.getUser().getFullName());
        Double rating = user.getUser().getRate();
        if (rating == null)
            ratingBar.setRating(0);
        else
            ratingBar.setRating(rating.floatValue());
    }
}
