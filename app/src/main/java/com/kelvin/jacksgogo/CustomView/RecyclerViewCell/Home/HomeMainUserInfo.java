package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.makeramen.roundedimageview.RoundedImageView;

public class HomeMainUserInfo extends RelativeLayout {

    private Context mContext;

    public RoundedImageView imgAvatar;
    public TextView lblUserName;

    public HomeMainUserInfo(Context context) {
        super(context);
        mContext = context;

        initView();
    }

    private void initView() {
        LayoutInflater mLayoutInflater      = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view                           = mLayoutInflater.inflate(R.layout.view_home_user_info, this);
        imgAvatar                           = view.findViewById(R.id.img_avatar);
        lblUserName                          = view.findViewById(R.id.lbl_username);
    }
}
