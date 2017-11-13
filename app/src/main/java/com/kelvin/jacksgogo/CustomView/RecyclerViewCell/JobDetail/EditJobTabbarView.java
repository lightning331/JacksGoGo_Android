package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail;

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
    public ImageView mDescribeImage;
    public ImageView mTimeImage;
    public ImageView mAddressImage;
    public ImageView mReportImage;
    public TextView mDescribeText;
    public TextView mTimeText;
    public TextView mAddressText;
    public TextView mReportText;

    public EditTabStatus getEditTabStatus() {
        return editTabStatus;
    }

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
        View mTabbarView                 = mLayoutInflater.inflate(R.layout.edit_job_tabbar_view, this);

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

        mDescribeButton.setOnClickListener(this);
        mTimeButton.setOnClickListener(this);
        mAddressButton.setOnClickListener(this);
        mReportButton.setOnClickListener(this);
    }

    public EditTabStatus getTabEditStatus() {
        return this.editTabStatus;
    }

    public void setEditTabStatus(EditTabStatus status) {
        this.editTabStatus = status;
        mDescribeImage.setImageResource(R.mipmap.counter_greytick);
        mDescribeText.setTextColor(getResources().getColor(R.color.JGGGrey1));
        mAddressImage.setImageResource(R.mipmap.counter_greytick);
        mAddressText.setTextColor(getResources().getColor(R.color.JGGGrey1));
        mTimeImage.setImageResource(R.mipmap.counter_greytick);
        mTimeText.setTextColor(getResources().getColor(R.color.JGGGrey1));
        mReportImage.setImageResource(R.mipmap.counter_greytick);
        mReportText.setTextColor(getResources().getColor(R.color.JGGGrey1));

        switch (status) {
            case DESCRIBE:
                mDescribeImage.setImageResource(R.mipmap.counter_greentick);
                mDescribeText.setTextColor(getResources().getColor(R.color.JGGGreen));
                break;
            case TIME:
                mTimeImage.setImageResource(R.mipmap.counter_greentick);
                mTimeText.setTextColor(getResources().getColor(R.color.JGGGreen));
                break;
            case ADDRESS:
                mAddressImage.setImageResource(R.mipmap.counter_greentick);
                mAddressText.setTextColor(getResources().getColor(R.color.JGGGreen));
                break;
            case REPORT:
                mReportImage.setImageResource(R.mipmap.counter_greentick);
                mReportText.setTextColor(getResources().getColor(R.color.JGGGreen));
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
