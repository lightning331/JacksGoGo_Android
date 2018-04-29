package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    public ImageView imgDone;
    public LinearLayout dotLayout;

    public LinearLayout firstLayout;
    public TextView txtFirstTime;
    public TextView txtFirstDescription;

    public LinearLayout secondLayout;
    public TextView txtSecondTime;
    public TextView txtSecondDescription;

    public LinearLayout thirdLayout;
    public TextView txtThirdTime;
    public TextView txtThirdDescription;

    public Button btnReport;

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

        imgDone = (ImageView) view.findViewById(R.id.img_done);
        dotLayout = (LinearLayout) view.findViewById(R.id.ll_dot);

        firstLayout = (LinearLayout) view.findViewById(R.id.ll_first);
        txtFirstTime = (TextView) view.findViewById(R.id.txt_first_time);
        txtFirstDescription = (TextView) view.findViewById(R.id.txt_first_desc);

        secondLayout = (LinearLayout) view.findViewById(R.id.ll_second);
        txtSecondTime = (TextView) view.findViewById(R.id.txt_second_time);
        txtSecondDescription = (TextView) view.findViewById(R.id.txt_second_desc);

        thirdLayout = (LinearLayout) view.findViewById(R.id.ll_third);
        txtThirdTime = (TextView) view.findViewById(R.id.txt_third_time);
        txtThirdDescription = (TextView) view.findViewById(R.id.txt_third_desc);

        btnReport                   = (Button) view.findViewById(R.id.btn_report);
        btnReport.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_report) {
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
