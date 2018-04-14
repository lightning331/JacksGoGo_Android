package com.kelvin.jacksgogo.CustomView.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

public class PostEventTabView extends RelativeLayout implements View.OnClickListener {

    Context mContext;

    LinearLayout mDescribeButton;
    LinearLayout mTimeButton;
    LinearLayout mAddressButton;
    LinearLayout mBudgetButton;
    LinearLayout mReportButton;
    ImageView imgTimeLine;
    ImageView imgAddressLine;
    ImageView imgBudgetLine;
    ImageView imgReportLine;
    public ImageView mDescribeImage;
    public ImageView mTimeImage;
    public ImageView mAddressImage;
    public ImageView mBudgetImage;
    public ImageView mReportImage;
    public TextView mDescribeText;
    public TextView mTimeText;
    public TextView mAddressText;
    public TextView mBudgetText;
    public TextView mReportText;

    private EventTabName eventTabName;

    public enum EventTabName {
        DESCRIBE,
        TIME,
        ADDRESS,
        LIMIT,
        COST
    }

    public PostEventTabView(Context context) {
        super(context);

        mContext = context;

        initView();
    }

    private void initView() {

        LayoutInflater mLayoutInflater      = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mTabView                 = mLayoutInflater.inflate(R.layout.view_create_event_tab, this);

        mDescribeButton     = (LinearLayout) mTabView.findViewById(R.id.btn_describe);
        mTimeButton         = (LinearLayout) mTabView.findViewById(R.id.btn_time);
        mAddressButton      = (LinearLayout) mTabView.findViewById(R.id.btn_address);
        mBudgetButton       = (LinearLayout) mTabView.findViewById(R.id.btn_budget);
        mReportButton       = (LinearLayout) mTabView.findViewById(R.id.btn_report);
        mDescribeImage      = (ImageView) mTabView.findViewById(R.id.img_my_services);
        mTimeImage          = (ImageView) mTabView.findViewById(R.id.img_time);
        mAddressImage       = (ImageView) mTabView.findViewById(R.id.img_address);
        mBudgetImage        = (ImageView) mTabView.findViewById(R.id.img_budget);
        mReportImage        = (ImageView) mTabView.findViewById(R.id.img_report);
        mDescribeText       = (TextView) mTabView.findViewById(R.id.lbl_describe);
        mTimeText           = (TextView) mTabView.findViewById(R.id.lbl_time);
        mAddressText        = (TextView) mTabView.findViewById(R.id.lbl_address);
        mBudgetText         = (TextView) mTabView.findViewById(R.id.lbl_budget);
        mReportText         = (TextView) mTabView.findViewById(R.id.lbl_report);
        imgTimeLine         = (ImageView) mTabView.findViewById(R.id.img_time_line);
        imgAddressLine      = (ImageView) mTabView.findViewById(R.id.img_address_line);
        imgBudgetLine       = (ImageView) mTabView.findViewById(R.id.img_budget_line);
        imgReportLine       = (ImageView) mTabView.findViewById(R.id.img_report_line);

    }

    public EventTabName getTabName() {
        return eventTabName;
    }

    public void setTabName(EventTabName name) {
        this.eventTabName = name;

        mDescribeText.setTextColor(getResources().getColor(R.color.JGGGrey1));
        mTimeText.setTextColor(getResources().getColor(R.color.JGGGrey1));
        mAddressText.setTextColor(getResources().getColor(R.color.JGGGrey1));
        mBudgetText.setTextColor(getResources().getColor(R.color.JGGGrey1));
        mReportText.setTextColor(getResources().getColor(R.color.JGGGrey1));
        imgTimeLine.setImageResource(R.mipmap.line_dotted);
        imgAddressLine.setImageResource(R.mipmap.line_dotted);
        imgBudgetLine.setImageResource(R.mipmap.line_dotted);
        imgReportLine.setImageResource(R.mipmap.line_dotted);

        mDescribeImage.setImageResource(R.mipmap.counter_grey);
        mAddressImage.setImageResource(R.mipmap.counter_grey);
        mTimeImage.setImageResource(R.mipmap.counter_grey);
        mBudgetImage.setImageResource(R.mipmap.counter_grey);
        mReportImage.setImageResource(R.mipmap.counter_grey);

        mDescribeButton.setOnClickListener(this);
        mTimeButton.setOnClickListener(this);
        mAddressButton.setOnClickListener(this);
        mBudgetButton.setOnClickListener(this);
        mReportButton.setOnClickListener(this);
        switch (name) {
            case DESCRIBE:
                //mDescribeButton.setOnClickListener(this);
                mDescribeText.setTextColor(getResources().getColor(R.color.JGGPurple));
                mDescribeImage.setImageResource(R.mipmap.counter_purpleactive);
                break;
            case TIME:
                //mDescribeButton.setOnClickListener(this);
                //mTimeButton.setOnClickListener(this);
                imgTimeLine.setImageResource(R.mipmap.line_full);
                mTimeText.setTextColor(getResources().getColor(R.color.JGGPurple));
                mTimeImage.setImageResource(R.mipmap.counter_purpleactive);
                mDescribeImage.setImageResource(R.mipmap.counter_greytick);
                break;
            case ADDRESS:
                //mDescribeButton.setOnClickListener(this);
                //mTimeButton.setOnClickListener(this);
                //mAddressButton.setOnClickListener(this);
                imgTimeLine.setImageResource(R.mipmap.line_full);
                imgAddressLine.setImageResource(R.mipmap.line_full);
                mAddressText.setTextColor(getResources().getColor(R.color.JGGPurple));
                mAddressImage.setImageResource(R.mipmap.counter_purpleactive);
                mDescribeImage.setImageResource(R.mipmap.counter_greytick);
                mTimeImage.setImageResource(R.mipmap.counter_greytick);
                break;
            case LIMIT:
                //mDescribeButton.setOnClickListener(this);
                //mTimeButton.setOnClickListener(this);
                //mAddressButton.setOnClickListener(this);
                //mBudgetButton.setOnClickListener(this);
                imgTimeLine.setImageResource(R.mipmap.line_full);
                imgAddressLine.setImageResource(R.mipmap.line_full);
                imgBudgetLine.setImageResource(R.mipmap.line_full);
                mBudgetText.setTextColor(getResources().getColor(R.color.JGGPurple));
                mBudgetImage.setImageResource(R.mipmap.counter_purpleactive);
                mDescribeImage.setImageResource(R.mipmap.counter_greytick);
                mTimeImage.setImageResource(R.mipmap.counter_greytick);
                mAddressImage.setImageResource(R.mipmap.counter_greytick);
                break;
            case COST:
                //mDescribeButton.setOnClickListener(this);
                //mTimeButton.setOnClickListener(this);
                //mAddressButton.setOnClickListener(this);
                //mBudgetButton.setOnClickListener(this);
                //mReportButton.setOnClickListener(this);
                imgTimeLine.setImageResource(R.mipmap.line_full);
                imgAddressLine.setImageResource(R.mipmap.line_full);
                imgBudgetLine.setImageResource(R.mipmap.line_full);
                imgReportLine.setImageResource(R.mipmap.line_full);
                mReportText.setTextColor(getResources().getColor(R.color.JGGPurple));
                mReportImage.setImageResource(R.mipmap.counter_purpleactive);
                mDescribeImage.setImageResource(R.mipmap.counter_greytick);
                mTimeImage.setImageResource(R.mipmap.counter_greytick);
                mAddressImage.setImageResource(R.mipmap.counter_greytick);
                mBudgetImage.setImageResource(R.mipmap.counter_greytick);
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
