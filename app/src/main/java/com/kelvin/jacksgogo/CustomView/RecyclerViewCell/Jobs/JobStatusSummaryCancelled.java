package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 3/7/2018.
 */

public class JobStatusSummaryCancelled extends RelativeLayout {

    private Context mContext;

    public JobStatusSummaryCancelled(Context context) {
        super(context);
        this.mContext = context;

        initView();
    }

    private void initView() {
        LayoutInflater mLayoutInflater       = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view                            = mLayoutInflater.inflate(R.layout.view_job_status_summary_cancelled, this);
    }
}
