package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.User.JGGGoClubUserModel;
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
    public LinearLayout ll_background;
    public LinearLayout ll_user_detail;
    public LinearLayout btnReviews;
    public TextView lblUserType;
    public TextView lblReviews;
    public RelativeLayout likeButtonLayout;
    public LinearLayout btnDecline;
    public TextView lblRightUserType;
    public TextView lblCenterUserType;
    public TextView lblDecline;

    private int txtColor;
    private LinearLayout.LayoutParams param;
    private LinearLayout.LayoutParams param1;

    public UserNameRatingCell(Context context, View itemView) {
        super(itemView);
        mContext = context;

        name = itemView.findViewById(R.id.lbl_username);
        ratingBar = itemView.findViewById(R.id.user_ratingbar);
        avatar = itemView.findViewById(R.id.img_avatar);
        ll_background = itemView.findViewById(R.id.ll_background);
        ll_user_detail = itemView.findViewById(R.id.ll_user_detail);
        btnReviews = itemView.findViewById(R.id.btn_view_all_services);
        lblReviews = itemView.findViewById(R.id.lbl_reviews);
        likeButtonLayout = itemView.findViewById(R.id.like_button_layout);
        lblUserType = itemView.findViewById(R.id.lbl_user_type);
        lblCenterUserType = itemView.findViewById(R.id.lbl_center_user_type);
        lblRightUserType = itemView.findViewById(R.id.lbl_right_user_type);
        btnDecline = itemView.findViewById(R.id.btn_decline);
        lblDecline = itemView.findViewById(R.id.lbl_decline);

        btnReviews.setVisibility(View.GONE);
        btnDecline.setVisibility(View.GONE);
        likeButtonLayout.setVisibility(View.GONE);
        lblUserType.setVisibility(View.GONE);
        lblRightUserType.setVisibility(View.GONE);
        lblCenterUserType.setVisibility(View.GONE);

        txtColor = ContextCompat.getColor(mContext, R.color.JGGPurple);
    }

    public void setData(JGGUserProfileModel user) {
        param = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT, 55);
        param1 = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT, 45);
        ll_user_detail.setLayoutParams(param);
        btnReviews.setLayoutParams(param1);
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

    public void setClubAdminUser(JGGGoClubUserModel clubUser) {
        lblUserType.setVisibility(View.VISIBLE);
        ratingBar.setVisibility(View.GONE);
        btnDecline.setVisibility(View.GONE);
        Picasso.with(mContext)
                .load(clubUser.getUserProfile().getUser().getPhotoURL())
                .placeholder(R.mipmap.icon_profile)
                .into(avatar);
        if (clubUser.getUserProfile().getUser().getGivenName() == null)
            name.setText(clubUser.getUserProfile().getUser().getUserName());
        else
            name.setText(clubUser.getUserProfile().getUser().getFullName());

        switch (clubUser.getUserType()) {
            case owner:
                lblUserType.setText("Group Owner");
                break;
            case admin:
                lblUserType.setText("Admin");
                break;
        }
    }

    public void setClubAllUser(JGGGoClubUserModel clubUser) {
        lblRightUserType.setVisibility(View.VISIBLE);
        btnDecline.setVisibility(View.VISIBLE);
        lblDecline.setVisibility(View.GONE);
        Picasso.with(mContext)
                .load(clubUser.getUserProfile().getUser().getPhotoURL())
                .placeholder(R.mipmap.icon_profile)
                .into(avatar);
        if (clubUser.getUserProfile().getUser().getGivenName() == null)
            name.setText(clubUser.getUserProfile().getUser().getUserName());
        else
            name.setText(clubUser.getUserProfile().getUser().getFullName());

        switch (clubUser.getUserType()) {
            case owner:
                lblRightUserType.setText("Group Owner");
                break;
            case admin:
                if (clubUser.getUserProfileID().equals(JGGAppManager.getInstance().getCurrentUser().getID()))
                    lblRightUserType.setText("You");
                else
                    lblRightUserType.setText("Admin");
                break;
            case user:
                if (clubUser.getUserProfileID().equals(JGGAppManager.getInstance().getCurrentUser().getID()))
                    lblRightUserType.setText("You");
                else
                    lblRightUserType.setText("");
                break;
            case none:
                if (clubUser.getUserProfileID().equals(JGGAppManager.getInstance().getCurrentUser().getID()))
                    lblRightUserType.setText("You");
                else
                    lblRightUserType.setText("");
                break;
        }
    }

    public void setPendingUser(JGGGoClubUserModel clubUser) {
        ll_background.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGPurple10Percent));
        btnReviews.setVisibility(View.VISIBLE);
        btnDecline.setVisibility(View.VISIBLE);
        lblReviews.setTextColor(txtColor);
        lblReviews.setText("Approve");
        lblDecline.setTextColor(txtColor);
        lblDecline.setText("Decline");
        Picasso.with(mContext)
                .load(clubUser.getUserProfile().getUser().getPhotoURL())
                .placeholder(R.mipmap.icon_profile)
                .into(avatar);
        if (clubUser.getUserProfile().getUser().getGivenName() == null)
            name.setText(clubUser.getUserProfile().getUser().getUserName());
        else
            name.setText(clubUser.getUserProfile().getUser().getFullName());
    }

    public void setApprovedUser(JGGGoClubUserModel clubUser) {
        btnReviews.setVisibility(View.VISIBLE);
        btnDecline.setVisibility(View.VISIBLE);
        lblReviews.setTextColor(txtColor);
        lblReviews.setText("Promote");
        lblDecline.setTextColor(txtColor);
        lblDecline.setText("Remove");
        Picasso.with(mContext)
                .load(clubUser.getUserProfile().getUser().getPhotoURL())
                .placeholder(R.mipmap.icon_profile)
                .into(avatar);
        if (clubUser.getUserProfile().getUser().getGivenName() == null)
            name.setText(clubUser.getUserProfile().getUser().getUserName());
        else
            name.setText(clubUser.getUserProfile().getUser().getFullName());

        switch (clubUser.getUserType()) {
            case owner:
                btnReviews.setVisibility(View.GONE);
                lblDecline.setVisibility(View.GONE);
                lblRightUserType.setVisibility(View.VISIBLE);
                lblRightUserType.setText("Owner\nYou");
                break;
            case admin:
                lblReviews.setVisibility(View.GONE);
                lblCenterUserType.setVisibility(View.VISIBLE);
                lblCenterUserType.setText("Admin");
                lblDecline.setText("Demote");
                break;
        }
    }
}
