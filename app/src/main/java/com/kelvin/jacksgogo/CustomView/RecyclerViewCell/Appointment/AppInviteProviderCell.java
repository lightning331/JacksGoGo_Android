package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Appointment;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.User.JGGGoClubUserModel;
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
    public TextView lblUserType;
    public TextView userName;
    public MaterialRatingBar ratingBar;
    public LinearLayout btnInvite;
    public TextView lblInvite;

    public AppInviteProviderCell(Context context, View itemView) {
        super(itemView);
        mContext = context;

        avatarImage = itemView.findViewById(R.id.img_invite_provider_avatar);
        lblUserType = itemView.findViewById(R.id.lbl_user_type);
        userName = itemView.findViewById(R.id.lbl_invite_provider_username);
        ratingBar = itemView.findViewById(R.id.invite_provider_ratingBar);
        btnInvite = itemView.findViewById(R.id.btn_invite);
        lblInvite = itemView.findViewById(R.id.lbl_invite);

        lblUserType.setVisibility(View.GONE);
    }

    public void setUser(JGGUserProfileModel user) {
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

    public void setClubUser(JGGGoClubUserModel clubUser) {
        lblUserType.setVisibility(View.VISIBLE);
        ratingBar.setVisibility(View.GONE);
        btnInvite.setVisibility(View.GONE);
        Picasso.with(mContext)
                .load(clubUser.getUserProfile().getUser().getPhotoURL())
                .placeholder(R.mipmap.icon_profile)
                .into(avatarImage);
        if (clubUser.getUserProfile().getUser().getGivenName() == null)
            userName.setText(clubUser.getUserProfile().getUser().getUserName());
        else
            userName.setText(clubUser.getUserProfile().getUser().getFullName());

        switch (clubUser.getUserType()) {
            case owner:
                lblUserType.setText("Group Owner");
                break;
            case admin:
                lblUserType.setText("Admin");
                break;
            case user:
                lblUserType.setText("");
                break;
            case none:
                lblUserType.setText("");
                break;
        }
    }

    public void disableInviteButton(boolean disable) {
        btnInvite.setClickable(!disable);
        if (disable) {
            lblInvite.setTextColor(ContextCompat.getColor(mContext, R.color.JGGGrey2));
        } else {
            lblInvite.setTextColor(ContextCompat.getColor(mContext, R.color.JGGGreen));
        }
    }
}
