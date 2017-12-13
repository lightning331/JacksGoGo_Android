package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 12/12/2017.
 */

public class JobMainTipView extends RelativeLayout {

    private Context mContext;

    public JobMainTipView(Context context) {
        super(context);
        this.mContext = context;

        initView();
    }

    private void initView() {
        LayoutInflater mLayoutInflater       = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view                            = mLayoutInflater.inflate(R.layout.view_job_main_tip, this);

    }
}