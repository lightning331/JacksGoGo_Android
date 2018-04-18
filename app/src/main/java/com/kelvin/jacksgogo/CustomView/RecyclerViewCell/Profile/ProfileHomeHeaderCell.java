package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Profile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hbb20.CountryCodePicker;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserProfileModel;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

/**
 * Created by PUMA on 1/27/2018.
 */

public class ProfileHomeHeaderCell extends RecyclerView.ViewHolder {

    private Context mContext;

    public TextView lblCredit;
    public TextView lblPoint;
    public LinearLayout btnViewProfile;
    public RoundedImageView avatar;
    public TextView lblUserName;
    public CountryCodePicker picker;
    public LinearLayout btnChangeRegion;
    public ImageView imgBackground;
    public RelativeLayout rlCredit;
    public RelativeLayout rlPoint;
    public TextView txtPoint;

    public ProfileHomeHeaderCell(View itemView, Context context) {
        super(itemView);
        mContext = context;

        lblCredit = (TextView) itemView.findViewById(R.id.lbl_profile_home_credit);
        lblPoint = (TextView) itemView.findViewById(R.id.lbl_profile_home_point);
        btnViewProfile = (LinearLayout) itemView.findViewById(R.id.btn_view_profile);
        avatar = (RoundedImageView) itemView.findViewById(R.id.img_avatar);
        lblUserName = (TextView) itemView.findViewById(R.id.lbl_username);
        picker = (CountryCodePicker) itemView.findViewById(R.id.ccp);
        btnChangeRegion = (LinearLayout) itemView.findViewById(R.id.btn_change_region);
        imgBackground = (ImageView) itemView.findViewById(R.id.img_background);

        rlCredit = (RelativeLayout) itemView.findViewById(R.id.rl_credit);
        rlPoint = (RelativeLayout) itemView.findViewById(R.id.rl_point);
        txtPoint = (TextView) itemView.findViewById(R.id.txtPoint);
    }

    public void setData(JGGUserProfileModel user) {
        Picasso.with(mContext)
                .load(user.getUser().getPhotoURL())
                .placeholder(R.mipmap.icon_profile)
                .into(avatar);
        Picasso.with(mContext)
                .load(user.getUser().getPhotoURL())
                .placeholder(null)
                .into(imgBackground);
        lblUserName.setText(user.getUser().getFullName());
    }
}
