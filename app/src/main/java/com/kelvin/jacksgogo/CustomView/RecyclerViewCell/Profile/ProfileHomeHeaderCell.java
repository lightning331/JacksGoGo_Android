package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Profile;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hbb20.CountryCodePicker;
import com.kelvin.jacksgogo.R;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Created by PUMA on 1/27/2018.
 */

public class ProfileHomeHeaderCell extends RecyclerView.ViewHolder {

    public TextView lblCredit;
    public TextView lblPoint;
    public LinearLayout btnViewProfile;
    public RoundedImageView avatar;
    public TextView lblUserName;
    public CountryCodePicker picker;
    public LinearLayout btnChangeRegion;

    public ProfileHomeHeaderCell(View itemView) {
        super(itemView);

        lblCredit = (TextView) itemView.findViewById(R.id.lbl_profile_home_credit);
        lblPoint = (TextView) itemView.findViewById(R.id.lbl_profile_home_point);
        btnViewProfile = (LinearLayout) itemView.findViewById(R.id.btn_view_profile);
        avatar = (RoundedImageView) itemView.findViewById(R.id.img_avatar);
        lblUserName = (TextView) itemView.findViewById(R.id.lbl_username);
        picker = (CountryCodePicker) itemView.findViewById(R.id.ccp);
        btnChangeRegion = (LinearLayout) itemView.findViewById(R.id.btn_change_region);
    }
}
