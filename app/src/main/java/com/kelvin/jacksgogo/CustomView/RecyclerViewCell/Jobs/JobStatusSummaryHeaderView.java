package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global.JGGUserType;

/**
 * Created by PUMA on 12/12/2017.
 */

public class JobStatusSummaryHeaderView extends RelativeLayout {

    private Context mContext;
    private int mColor;

    public LinearLayout reportLayout;
    public LinearLayout invoiceLayout;
    public LinearLayout reviewLayout;
    public LinearLayout tipLayout;
    public LinearLayout rehireLayout;

    public JobStatusSummaryHeaderView(Context context, JGGUserType userType) {
        super(context);
        this.mContext = context;

        if (userType == JGGUserType.CLIENT)
            mColor = ContextCompat.getColor(getContext(), R.color.JGGGreen);
        else if (userType == JGGUserType.PROVIDER)
            mColor = ContextCompat.getColor(getContext(), R.color.JGGCyan);

        initView();
    }

    private void initView() {
        LayoutInflater mLayoutInflater       = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view                            = mLayoutInflater.inflate(R.layout.view_job_status_summary_header, this);

        reportLayout = (LinearLayout) view.findViewById(R.id.job_report_layout);
        invoiceLayout = (LinearLayout) view.findViewById(R.id.job_invoice_layout);
        reviewLayout = (LinearLayout) view.findViewById(R.id.job_review_layout);
        tipLayout = (LinearLayout) view.findViewById(R.id.job_tip_layout);
        rehireLayout = (LinearLayout) view.findViewById(R.id.job_rehire_layout);
    }
}
