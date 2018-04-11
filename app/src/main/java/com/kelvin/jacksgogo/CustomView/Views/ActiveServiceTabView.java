package com.kelvin.jacksgogo.CustomView.Views;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

import static com.kelvin.jacksgogo.Utils.Global.EVENTS;
import static com.kelvin.jacksgogo.Utils.Global.GOCLUB;
import static com.kelvin.jacksgogo.Utils.Global.JOBS;
import static com.kelvin.jacksgogo.Utils.Global.SERVICES;

/**
 * Created by PUMA on 11/13/2017.
 */

public class ActiveServiceTabView extends RelativeLayout implements View.OnClickListener {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private View tabView;
    private String appType;
    private int mColor;

    public LinearLayout distanceButton;
    public LinearLayout ratingButton;
    public LinearLayout filterButton;
    public ImageView imgFilter;
    public LinearLayout mapViewButton;
    public ImageView imgMapView;
    public TextView lblDistance;
    public TextView lblRating;

    public ActiveServiceTabView(Context context, String type) {
        super(context);
        mContext = context;
        appType = type;

        initView();
    }

    public void initView() {

        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        tabView = mLayoutInflater.inflate(R.layout.view_search_active_service_tab, this);

        distanceButton = (LinearLayout) tabView.findViewById(R.id.btn_active_service_distance);
        ratingButton = (LinearLayout) tabView.findViewById(R.id.btn_active_service_ratings);
        filterButton = (LinearLayout) tabView.findViewById(R.id.btn_active_service_filter);
        imgFilter = (ImageView) tabView.findViewById(R.id.img_active_filter);
        mapViewButton = (LinearLayout) tabView.findViewById(R.id.btn_active_service_mapview);
        imgMapView = (ImageView) tabView.findViewById(R.id.img_active_mapview);
        lblDistance = (TextView) tabView.findViewById(R.id.lbl_active_service_distance);
        lblRating = (TextView) tabView.findViewById(R.id.lbl_active_service_rating);

        if (appType.equals(SERVICES)) {
            mColor = ContextCompat.getColor(mContext, R.color.JGGGreen);
            lblDistance.setTextColor(mColor);
            imgMapView.setImageResource(R.mipmap.button_mapview_green);
            imgFilter.setImageResource(R.mipmap.button_filter_green);
        } else if (appType.equals(JOBS)) {
            mColor = ContextCompat.getColor(mContext, R.color.JGGCyan);
            lblDistance.setTextColor(mColor);
            imgMapView.setImageResource(R.mipmap.button_mapview_cyan);
            imgFilter.setImageResource(R.mipmap.button_filter_cyan);
        } else if (appType.equals(GOCLUB) || appType.equals(EVENTS)) {
            mColor = ContextCompat.getColor(mContext, R.color.JGGPurple);
            lblDistance.setTextColor(mColor);
            imgMapView.setImageResource(R.mipmap.button_mapview_purple);
            imgFilter.setImageResource(R.mipmap.button_filter_purple);
        }

        distanceButton.setOnClickListener(this);
        ratingButton.setOnClickListener(this);
        filterButton.setOnClickListener(this);
        mapViewButton.setOnClickListener(this);
        distanceButton.setTag("DISTANCE");
        ratingButton.setTag("RATING");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_active_service_filter) {

        } else if (view.getId() == R.id.btn_active_service_mapview) {

        } else {
            lblDistance.setTextColor(getResources().getColor(R.color.JGGGrey1));
            lblRating.setTextColor(getResources().getColor(R.color.JGGGrey1));
            if (view.getId() == R.id.btn_active_service_distance) {
                lblDistance.setTextColor(mColor);
            } else if (view.getId() == R.id.btn_active_service_ratings) {
                lblRating.setTextColor(mColor);
            }
        }

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
