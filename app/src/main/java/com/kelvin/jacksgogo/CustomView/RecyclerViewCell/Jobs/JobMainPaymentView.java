package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 12/12/2017.
 */

public class JobMainPaymentView extends RelativeLayout implements View.OnClickListener {

    private Context mContext;
    public TextView btnReportToVerify;

    public JobMainPaymentView(Context context) {
        super(context);
        this.mContext = context;

        initView();
    }

    private void initView() {
        LayoutInflater mLayoutInflater       = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view                            = mLayoutInflater.inflate(R.layout.view_job_main_payment, this);

        btnReportToVerify                   = (TextView) view.findViewById(R.id.btn_report_to_verify);
        btnReportToVerify.setOnClickListener(this);
    }

    private OnItemClickListener listener;

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_report_to_verify) {
            listener.onItemClick(view);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
