package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.Models.Jobs_Services_Events.JGGAppBaseModel;
import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/13/2017.
 */

public class SearchHomeHeaderView extends RecyclerView.ViewHolder {

    public LinearLayout leftLine;
    public TextView totalServiceCount;
    public TextView totalServiceCountTitle;
    public TextView newServiceCount;
    public TextView newServiceSinceDate;
    public LinearLayout viewMyServiceButton;
    public LinearLayout viewAllServiceButton;
    public ImageView imgAllService;
    public TextView lblAllService;
    public LinearLayout postNewButton;
    public ImageView imgPostNew;
    public TextView lblPostNew;

    private Context mContext;
    private JGGAppBaseModel.AppointmentType type;

    public SearchHomeHeaderView(View itemView, JGGAppBaseModel.AppointmentType t, Context context) {
        super(itemView);
        mContext = context;
        type = t;

        leftLine = itemView.findViewById(R.id.left_line_layout);
        totalServiceCount = itemView.findViewById(R.id.lbl_total_service_count);
        totalServiceCountTitle = itemView.findViewById(R.id.count_title);
        newServiceCount = itemView.findViewById(R.id.lbl_new_service_count);
        newServiceSinceDate = itemView.findViewById(R.id.lbl_new_service_since_date);
        viewMyServiceButton = itemView.findViewById(R.id.btn_view_my_service);
        viewAllServiceButton = itemView.findViewById(R.id.btn_view_all);
        imgAllService = itemView.findViewById(R.id.img_view_all);
        lblAllService = itemView.findViewById(R.id.lbl_view_all);
        postNewButton = itemView.findViewById(R.id.btn_post_new);
        imgPostNew = itemView.findViewById(R.id.img_post_new);
        lblPostNew = itemView.findViewById(R.id.lbl_post_new);

        initView();
    }

    private void initView() {

        viewMyServiceButton.setVisibility(View.GONE);

        if (type == JGGAppBaseModel.AppointmentType.SERVICES) {
            viewMyServiceButton.setVisibility(View.VISIBLE);
            imgAllService.setImageResource(R.mipmap.button_viewall_round_green);
            imgPostNew.setImageResource(R.mipmap.button_addnew_round_green);
            setViewColor(ContextCompat.getColor(mContext, R.color.JGGGreen), "Services");
        } else if (type == JGGAppBaseModel.AppointmentType.JOBS) {
            imgAllService.setImageResource(R.mipmap.button_viewall_cyan);
            imgPostNew.setImageResource(R.mipmap.button_addnew_round_cyan);
            setViewColor(ContextCompat.getColor(mContext, R.color.JGGCyan), "Jobs");
        } else if (type == JGGAppBaseModel.AppointmentType.EVENT) {

        }
    }

    private void setViewColor(int color, String title) {
        leftLine.setBackgroundColor(color);
        totalServiceCount.setTextColor(color);
        lblAllService.setTextColor(color);
        lblPostNew.setTextColor(color);
        totalServiceCountTitle.setText(title);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        viewMyServiceButton.setOnClickListener(listener);
        viewAllServiceButton.setOnClickListener(listener);
        postNewButton.setOnClickListener(listener);
    }
}
