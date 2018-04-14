package com.kelvin.jacksgogo.CustomView.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

public class PostGoClubTabView extends RelativeLayout implements View.OnClickListener {

    Context mContext;

    LinearLayout mDescribeButton;
    LinearLayout mTimeButton;
    LinearLayout mAddressButton;
    ImageView imgTimeLine;
    ImageView imgAddressLine;
    public ImageView mDescribeImage;
    public ImageView mTimeImage;
    public ImageView mAddressImage;
    public TextView mDescribeText;
    public TextView mTimeText;
    public TextView mAddressText;

    private GoClubTabName goClubTabName;

    public enum GoClubTabName {
        DESCRIBE,
        LIMIT,
        ADMIN
    }

    public PostGoClubTabView(Context context) {
        super(context);
        mContext = context;

        initView();
    }

    private void initView() {

        LayoutInflater mLayoutInflater      = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mTabView                 = mLayoutInflater.inflate(R.layout.view_create_go_club_tab, this);

        mDescribeButton     = (LinearLayout) mTabView.findViewById(R.id.btn_describe);
        mTimeButton         = (LinearLayout) mTabView.findViewById(R.id.btn_time);
        mAddressButton      = (LinearLayout) mTabView.findViewById(R.id.btn_address);
        mDescribeImage      = (ImageView) mTabView.findViewById(R.id.img_my_services);
        mTimeImage          = (ImageView) mTabView.findViewById(R.id.img_time);
        mAddressImage       = (ImageView) mTabView.findViewById(R.id.img_address);
        mDescribeText       = (TextView) mTabView.findViewById(R.id.lbl_describe);
        mTimeText           = (TextView) mTabView.findViewById(R.id.lbl_time);
        mAddressText        = (TextView) mTabView.findViewById(R.id.lbl_address);
        imgTimeLine         = (ImageView) mTabView.findViewById(R.id.img_time_line);
        imgAddressLine      = (ImageView) mTabView.findViewById(R.id.img_address_line);

    }

    public GoClubTabName getTabName() {
        return goClubTabName;
    }

    public void setTabName(GoClubTabName name) {
        this.goClubTabName = name;

        mDescribeText.setTextColor(getResources().getColor(R.color.JGGGrey1));
        mAddressText.setTextColor(getResources().getColor(R.color.JGGGrey1));
        mTimeText.setTextColor(getResources().getColor(R.color.JGGGrey1));
        imgTimeLine.setImageResource(R.mipmap.line_dotted);
        imgAddressLine.setImageResource(R.mipmap.line_dotted);
        mDescribeImage.setImageResource(R.mipmap.counter_grey);
        mAddressImage.setImageResource(R.mipmap.counter_grey);
        mTimeImage.setImageResource(R.mipmap.counter_grey);

        mDescribeButton.setOnClickListener(this);
        mTimeButton.setOnClickListener(this);
        mAddressButton.setOnClickListener(this);
        switch (name) {
            case DESCRIBE:
                mDescribeText.setTextColor(getResources().getColor(R.color.JGGPurple));
                mDescribeImage.setImageResource(R.mipmap.counter_purpleactive);
                break;
            case LIMIT:
                imgTimeLine.setImageResource(R.mipmap.line_full);
                mTimeText.setTextColor(getResources().getColor(R.color.JGGPurple));
                mTimeImage.setImageResource(R.mipmap.counter_purpleactive);
                mDescribeImage.setImageResource(R.mipmap.counter_greytick);
                break;
            case ADMIN:
                imgTimeLine.setImageResource(R.mipmap.line_full);
                imgAddressLine.setImageResource(R.mipmap.line_full);
                mAddressText.setTextColor(getResources().getColor(R.color.JGGPurple));
                mAddressImage.setImageResource(R.mipmap.counter_purpleactive);
                mDescribeImage.setImageResource(R.mipmap.counter_greytick);
                mTimeImage.setImageResource(R.mipmap.counter_greytick);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        listener.onTabItemClick(view);
    }

    private OnTabItemClickListener listener;

    public interface OnTabItemClickListener {
        void onTabItemClick(View view);
    }

    public void setTabItemClickListener(OnTabItemClickListener listener) {
        this.listener = listener;
    }
}
