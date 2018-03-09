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

import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.selectedCategory;

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
                .placeholder(null)
                .into(avatarImage);
        userName.setText(user.getUser().getFullName());
        ratingBar.setRating(user.getUser().getRate().floatValue());
    }
}
