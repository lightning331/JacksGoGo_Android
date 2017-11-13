package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Appointment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.makeramen.roundedimageview.RoundedImageView;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by PUMA on 11/13/2017.
 */

public class AppInviteProviderCell extends RecyclerView.ViewHolder {

    public RoundedImageView avatarImage;
    public TextView userName;
    public MaterialRatingBar ratingBar;

    public AppInviteProviderCell(View itemView) {
        super(itemView);

        avatarImage = itemView.findViewById(R.id.img_invite_provider_avatar);
        userName = itemView.findViewById(R.id.lbl_invite_provider_username);
        ratingBar = itemView.findViewById(R.id.invite_provider_ratingBar);
    }
}
