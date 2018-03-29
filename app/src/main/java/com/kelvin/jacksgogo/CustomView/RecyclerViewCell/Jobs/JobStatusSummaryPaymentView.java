package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global.JGGUserType;

/**
 * Created by PUMA on 12/12/2017.
 */

public class JobStatusSummaryPaymentView extends RelativeLayout implements View.OnClickListener {

    private Context mContext;
    private int mColor;

    public TextView btnReportToVerify;

    public JobStatusSummaryPaymentView(Context context, JGGUserType userType) {
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
        View view                            = mLayoutInflater.inflate(R.layout.view_job_status_summary_payment, this);

        btnReportToVerify                   = (TextView) view.findViewById(R.id.btn_report_to_verify);
        btnReportToVerify.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_report_to_verify) {
            listener.onItemClick(view);
        }
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
