package com.kelvin.jacksgogo.CustomView.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/10/2017.
 */

public class EditJobTabbarView extends RelativeLayout implements View.OnClickListener {

    Context mContext;

    LinearLayout mDescribeButton;
    LinearLayout mTimeButton;
    LinearLayout mAddressButton;
    LinearLayout mReportButton;
    ImageView imgTimeLine;
    ImageView imgAddressLine;
    ImageView imgRreportLine;
    public ImageView mDescribeImage;
    public ImageView mTimeImage;
    public ImageView mAddressImage;
    public ImageView mReportImage;
    public TextView mDescribeText;
    public TextView mTimeText;
    public TextView mAddressText;
    public TextView mReportText;

    private EditTabStatus editTabStatus;

    public enum EditTabStatus {
        DESCRIBE,
        TIME,
        ADDRESS,
        REPORT
    }

    public EditJobTabbarView(Context context) {
        super(context);
        this.mContext = context;

        initView();
    }

    private void initView() {

        LayoutInflater mLayoutInflater      = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mTabbarView                 = mLayoutInflater.inflate(R.layout.view_edit_job_tabbar, this);

        mDescribeButton     = (LinearLayout) mTabbarView.findViewById(R.id.btn_describe);
        mTimeButton         = (LinearLayout) mTabbarView.findViewById(R.id.btn_time);
        mAddressButton      = (LinearLayout) mTabbarView.findViewById(R.id.btn_address);
        mReportButton       = (LinearLayout) mTabbarView.findViewById(R.id.btn_report);
        mDescribeImage      = (ImageView) mTabbarView.findViewById(R.id.img_describe);
        mTimeImage          = (ImageView) mTabbarView.findViewById(R.id.img_time);
        mAddressImage       = (ImageView) mTabbarView.findViewById(R.id.img_address);
        mReportImage        = (ImageView) mTabbarView.findViewById(R.id.img_report);
        mDescribeText       = (TextView) mTabbarView.findViewById(R.id.lbl_describe);
        mTimeText           = (TextView) mTabbarView.findViewById(R.id.lbl_time);
        mAddressText        = (TextView) mTabbarView.findViewById(R.id.lbl_address);
        mReportText         = (TextView) mTabbarView.findViewById(R.id.lbl_report);
        imgTimeLine         = (ImageView) mTabbarView.findViewById(R.id.img_time_line);
        imgAddressLine      = (ImageView) mTabbarView.findViewById(R.id.img_address_line);
        imgRreportLine      = (ImageView) mTabbarView.findViewById(R.id.img_report_line);

        mDescribeButton.setOnClickListener(this);
        mTimeButton.setOnClickListener(this);
        mAddressButton.setOnClickListener(this);
        mReportButton.setOnClickListener(this);
    }

    public EditTabStatus getEditTabStatus() {
        return editTabStatus;
    }

    public void setEditTabStatus(EditTabStatus status, boolean isRequest) {
        this.editTabStatus = status;

        mDescribeImage.setImageResource(R.mipmap.counter_greytick);
        mDescribeText.setTextColor(getResources().getColor(R.color.JGGGrey1));
        mAddressImage.setImageResource(R.mipmap.counter_greytick);
        mAddressText.setTextColor(getResources().getColor(R.color.JGGGrey1));
        mTimeImage.setImageResource(R.mipmap.counter_greytick);
        mTimeText.setTextColor(getResources().getColor(R.color.JGGGrey1));
        mReportImage.setImageResource(R.mipmap.counter_greytick);
        mReportText.setTextColor(getResources().getColor(R.color.JGGGrey1));
        imgTimeLine.setImageResource(R.mipmap.line_dotted);
        imgRreportLine.setImageResource(R.mipmap.line_dotted);
        imgAddressLine.setImageResource(R.mipmap.line_dotted);
        if (isRequest) {
            mDescribeImage.setImageResource(R.mipmap.counter_grey);
            mAddressImage.setImageResource(R.mipmap.counter_grey);
            mTimeImage.setImageResource(R.mipmap.counter_grey);
            mReportImage.setImageResource(R.mipmap.counter_grey);
        }

        switch (status) {
            case DESCRIBE:
                mDescribeImage.setImageResource(R.mipmap.counter_greentick);
                mDescribeText.setTextColor(getResources().getColor(R.color.JGGGreen));
                if (isRequest) {
                    mDescribeImage.setImageResource(R.mipmap.counter_greenactive);
                }
                break;
            case TIME:
                imgTimeLine.setImageResource(R.mipmap.line_full);
                mTimeImage.setImageResource(R.mipmap.counter_greentick);
                mTimeText.setTextColor(getResources().getColor(R.color.JGGGreen));
                if (isRequest) {
                    mTimeImage.setImageResource(R.mipmap.counter_greenactive);
                    mDescribeImage.setImageResource(R.mipmap.counter_greytick);
                }
                break;
            case ADDRESS:
                imgTimeLine.setImageResource(R.mipmap.line_full);
                imgAddressLine.setImageResource(R.mipmap.line_full);
                mAddressImage.setImageResource(R.mipmap.counter_greentick);
                mAddressText.setTextColor(getResources().getColor(R.color.JGGGreen));
                if (isRequest) {
                    mAddressImage.setImageResource(R.mipmap.counter_greenactive);
                    mDescribeImage.setImageResource(R.mipmap.counter_greytick);
                    mTimeImage.setImageResource(R.mipmap.counter_greytick);
                }
                break;
            case REPORT:
                imgTimeLine.setImageResource(R.mipmap.line_full);
                imgAddressLine.setImageResource(R.mipmap.line_full);
                imgRreportLine.setImageResource(R.mipmap.line_full);
                mReportImage.setImageResource(R.mipmap.counter_greentick);
                mReportText.setTextColor(getResources().getColor(R.color.JGGGreen));
                if (isRequest) {
                    mReportImage.setImageResource(R.mipmap.counter_greenactive);
                    mDescribeImage.setImageResource(R.mipmap.counter_greytick);
                    mTimeImage.setImageResource(R.mipmap.counter_greytick);
                    mAddressImage.setImageResource(R.mipmap.counter_greytick);
                }
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

    public void setTabItemClickLietener(OnTabItemClickListener listener) {
        this.listener = listener;
    }
}
