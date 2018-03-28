package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global.JGGUserType;

/**
 * Created by PUMA on 12/12/2017.
 */

public class JobStatusSummaryConfirmedView extends RelativeLayout {

    private Context mContext;
    private int mColor;

    public LinearLayout confirmedLine;
    public ImageView imgConfirmed;
    public TextView lblConfirmedTime;
    public TextView lblConfirmedTitle;
    public TextView lblConfirmedDesc;
    public TextView btnSetAppDate;

    public JobStatusSummaryConfirmedView(Context context, JGGUserType userType) {
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
        View view                            = mLayoutInflater.inflate(R.layout.view_job_status_summary_confirmed, this);

        confirmedLine = view.findViewById(R.id.confirmed_line);
        imgConfirmed = view.findViewById(R.id.img_confirmed);
        lblConfirmedTime = view.findViewById(R.id.lbl_confirmed_time);
        lblConfirmedTitle = view.findViewById(R.id.lbl_confirmed_title);
        lblConfirmedDesc = view.findViewById(R.id.lbl_confirmed_desc);
        btnSetAppDate = view.findViewById(R.id.btn_set_appointment_date);
    }
}
