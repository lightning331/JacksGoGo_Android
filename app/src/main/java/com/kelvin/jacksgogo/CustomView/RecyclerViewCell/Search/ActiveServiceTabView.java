package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Search;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.Fragments.Search.ActiveServiceMapFragment;
import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/13/2017.
 */

public class ActiveServiceTabView extends RelativeLayout implements View.OnClickListener {

    Context mContext;
    LayoutInflater mLayoutInflater;
    View activeServiceTabView;

    public LinearLayout distanceButton;
    public LinearLayout ratingButton;
    public LinearLayout filterButton;
    public LinearLayout mapViewButton;
    public TextView lblDistance;
    public TextView lblRating;

    public ActiveServiceTabView(Context context) {
        super(context);
        mContext = context;

        initView();
    }

    public void initView() {

        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        activeServiceTabView  = mLayoutInflater.inflate(R.layout.search_active_service_tab_view, this);

        distanceButton = (LinearLayout) activeServiceTabView.findViewById(R.id.btn_active_service_distance);
        ratingButton = (LinearLayout) activeServiceTabView.findViewById(R.id.btn_active_service_ratings);
        filterButton = (LinearLayout) activeServiceTabView.findViewById(R.id.btn_active_service_filter);
        mapViewButton = (LinearLayout) activeServiceTabView.findViewById(R.id.btn_active_service_mapview);
        lblDistance = (TextView) activeServiceTabView.findViewById(R.id.lbl_active_service_distance);
        lblRating = (TextView) activeServiceTabView.findViewById(R.id.lbl_active_service_rating);

        distanceButton.setOnClickListener(this);
        ratingButton.setOnClickListener(this);
        filterButton.setOnClickListener(this);
        mapViewButton.setOnClickListener(this);
        distanceButton.setTag("DISTANCE");
        ratingButton.setTag("RATING");
    }

    private OnTabbarItemClickListener listener;

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_active_service_filter) {

        } else if (view.getId() == R.id.btn_active_service_mapview) {

        } else {
            lblDistance.setTextColor(getResources().getColor(R.color.JGGGrey1));
            lblRating.setTextColor(getResources().getColor(R.color.JGGGrey1));
            if (view.getId() == R.id.btn_active_service_distance) {
                lblDistance.setTextColor(getResources().getColor(R.color.JGGGreen));
            } else if (view.getId() == R.id.btn_active_service_ratings) {
                lblRating.setTextColor(getResources().getColor(R.color.JGGGreen));
            }
        }

        listener.onTabbarItemClick(view);
    }

    public interface OnTabbarItemClickListener {
        void onTabbarItemClick(View item);
    }

    public void setTabbarItemClickListener(OnTabbarItemClickListener listener) {
        this.listener = listener;
    }
}
