package com.kelvin.jacksgogo.CustomView;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.view.View;

import com.kelvin.jacksgogo.Activities.Appointment.AppOriginalPostDetailActivity;
import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/7/2017.
 */

public class AppOriginalPostActionbarView extends RelativeLayout implements View.OnClickListener {

    Context mContext;
    LayoutInflater mLayoutInflater;

    LinearLayout backButton;
    LinearLayout moreDetailButton;
    LinearLayout likeButton;
    View actionbarView;

    public AppOriginalPostActionbarView(Context context) {
        super(context);
        mContext = context;

        initView();
    }

    private void initView(){

        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        actionbarView  = mLayoutInflater.inflate(R.layout.original_post_detail_actionbar_view, this);

        backButton = (LinearLayout) actionbarView.findViewById(R.id.btn_back_original_post);
        likeButton = (LinearLayout) actionbarView.findViewById(R.id.btn_like_original);
        moreDetailButton = (LinearLayout) actionbarView.findViewById(R.id.btn_more_original);

        backButton.setOnClickListener(this);
        moreDetailButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_more_original) {

        } else if (view.getId() == R.id.btn_like_original) {

        } else {
            // back to previous view
            ((AppOriginalPostDetailActivity)mContext).finish();
        }
    }
}
