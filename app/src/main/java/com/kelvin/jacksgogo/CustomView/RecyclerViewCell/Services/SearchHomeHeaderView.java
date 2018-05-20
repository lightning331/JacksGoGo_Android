package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.CustomView.Views.SectionTitleView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;

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
    public TextView lblMyService;
    public ImageView imgMyService;
    public LinearLayout viewAllServiceButton;
    public ImageView imgAllService;
    public TextView lblAllService;
    public LinearLayout postNewButton;
    public ImageView imgPostNew;
    public TextView lblPostNew;

    private Context mContext;
    private AppointmentType type;

    private SectionTitleView titleView;


    public SearchHomeHeaderView(View itemView, AppointmentType t, Context context) {
        super(itemView);
        mContext = context;
        type = t;

        leftLine = itemView.findViewById(R.id.left_line_layout);
        totalServiceCount = itemView.findViewById(R.id.lbl_total_service_count);
        totalServiceCountTitle = itemView.findViewById(R.id.count_title);
        newServiceCount = itemView.findViewById(R.id.lbl_new_service_count);
        newServiceSinceDate = itemView.findViewById(R.id.lbl_new_service_since_date);
        viewMyServiceButton = itemView.findViewById(R.id.btn_view_my_service);
        lblMyService = itemView.findViewById(R.id.lbl_view_my_services);
        imgMyService = itemView.findViewById(R.id.img_my_services);
        viewAllServiceButton = itemView.findViewById(R.id.btn_view_all);
        imgAllService = itemView.findViewById(R.id.img_view_all);
        lblAllService = itemView.findViewById(R.id.lbl_view_all);
        postNewButton = itemView.findViewById(R.id.btn_post_new);
        imgPostNew = itemView.findViewById(R.id.img_post_new);
        lblPostNew = itemView.findViewById(R.id.lbl_post_new);

        titleView = (SectionTitleView) itemView.findViewById(R.id.sectionTitleView);

        initHeaderView();
        initSectionView();
    }

    private void initHeaderView() {

        viewMyServiceButton.setVisibility(View.GONE);

        if (type == AppointmentType.SERVICES) {
            viewMyServiceButton.setVisibility(View.VISIBLE);
            imgAllService.setImageResource(R.mipmap.button_viewall_round_green);
            imgPostNew.setImageResource(R.mipmap.button_addnew_round_green);
            setViewColor(ContextCompat.getColor(mContext, R.color.JGGGreen), "Services");
        } else if (type == AppointmentType.JOBS) {
            imgAllService.setImageResource(R.mipmap.button_viewall_cyan);
            imgPostNew.setImageResource(R.mipmap.button_addnew_round_cyan);
            setViewColor(ContextCompat.getColor(mContext, R.color.JGGCyan), "Jobs");
        } else if (type == AppointmentType.EVENTS
                || type == AppointmentType.GOCLUB) {
            viewMyServiceButton.setVisibility(View.VISIBLE);
            imgMyService.setImageResource(R.mipmap.button_tick_round_purple);
            imgAllService.setImageResource(R.mipmap.button_viewall_purple);
            imgPostNew.setImageResource(R.mipmap.button_addnew_round_purple);
            lblMyService.setText("View Joined");
            lblPostNew.setText("Create New");
            if (type == AppointmentType.GOCLUB)
                setViewColor(ContextCompat.getColor(mContext, R.color.JGGPurple), "GoClubs");
            else if (type == AppointmentType.EVENTS)
                setViewColor(ContextCompat.getColor(mContext, R.color.JGGPurple), "Events");
        }
    }

    private void initSectionView() {
        titleView.txtTitle.setText("All Categories");
        titleView.txtTitle.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
    }

    private void setViewColor(int color, String title) {
        lblMyService.setTextColor(color);
        leftLine.setBackgroundColor(color);
        totalServiceCount.setTextColor(color);
        lblAllService.setTextColor(color);
        lblPostNew.setTextColor(color);
        totalServiceCountTitle.setText(title);
    }

    public void setData(int count) {
        totalServiceCount.setText(String.valueOf(count));
    }

    public void setOnClickListener(View.OnClickListener listener) {
        viewMyServiceButton.setOnClickListener(listener);
        viewAllServiceButton.setOnClickListener(listener);
        postNewButton.setOnClickListener(listener);
    }
}
