package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global.JGGUserType;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Created by PUMA on 3/7/2018.
 */

public class JobStatusSummaryCancelled extends RelativeLayout {

    private Context mContext;
    private int mColor;

    public RoundedImageView imgAvatar;
    public TextView lblComment;
    public TextView lblCancelTitle;

    public JobStatusSummaryCancelled(Context context, JGGUserType userType) {
        super(context);
        this.mContext = context;

        if (userType == JGGUserType.CLIENT)
            mColor = ContextCompat.getColor(getContext(), R.color.JGGGreen);
        else if (userType == JGGUserType.PROVIDER)
            mColor = ContextCompat.getColor(getContext(), R.color.JGGCyan);

        initView();
    }

    private void initView() {
        LayoutInflater mLayoutInflater      = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view                           = mLayoutInflater.inflate(R.layout.view_job_status_summary_cancelled, this);
        imgAvatar                           = view.findViewById(R.id.img_avatar);
        lblComment                          = view.findViewById(R.id.lbl_comment);
        lblCancelTitle                      = view.findViewById(R.id.cancel_title);
    }
}
