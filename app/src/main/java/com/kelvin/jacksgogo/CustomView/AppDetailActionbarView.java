package com.kelvin.jacksgogo.CustomView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/3/2017.
 */

public class AppDetailActionbarView extends RelativeLayout implements View.OnClickListener {

    Context mContext;
    LayoutInflater mLayoutInflater;
    LinearLayout backButton;
    LinearLayout moreDetailButton;
    public ImageView moreMenuImage;
    View actionbarView;
    private boolean isSelected = false;

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isMenuOpenStatus() {
        return isSelected;
    }

    public AppDetailActionbarView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    private void initView(){

        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        actionbarView  = mLayoutInflater.inflate(R.layout.detail_info_actionbar_view, this);

        backButton = (LinearLayout) actionbarView.findViewById(R.id.btn_back);
        moreDetailButton = (LinearLayout) actionbarView.findViewById(R.id.btn_more);
        moreMenuImage = (ImageView) actionbarView.findViewById(R.id.img_more_menu);

        backButton.setOnClickListener(this);
        moreDetailButton.setOnClickListener(this);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onClick(View view) {
        listener.onTabbarItemClick(view);
    }

    private OnTabbarItemClickListener listener;

    public interface OnTabbarItemClickListener {
        void onTabbarItemClick(View item);
    }

    public void setTabbarItemClickListener(OnTabbarItemClickListener listener) {
        this.listener = listener;
    }
}
