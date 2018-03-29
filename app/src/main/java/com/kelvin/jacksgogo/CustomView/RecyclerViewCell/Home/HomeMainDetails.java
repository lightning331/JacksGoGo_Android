package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.kelvin.jacksgogo.R;

public class HomeMainDetails extends RelativeLayout {

    private Context mContext;



    public HomeMainDetails(Context context) {
        super(context);
        mContext = context;

        initView();
    }

    private void initView() {
        LayoutInflater mLayoutInflater      = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view                           = mLayoutInflater.inflate(R.layout.view_home_detail, this);
    }
}
