package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.squareup.picasso.Picasso;

import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getAppointmentBudget;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getAppointmentTime;

/**
 * Created by PUMA on 12/18/2017.
 */

public class JobListDetailCell extends RecyclerView.ViewHolder {

    private Context mContext;

    public LinearLayout btnBackGround;
    public RelativeLayout likeButtonLayout;
    public ImageView btnLike;
    public ImageView imgCategory;
    public ImageView carouselView;
    public TextView lblJobTitle;
    public TextView lblTime;
    public TextView lblAddress;
    public TextView price;
    public TextView bookedCount;
    public LinearLayout btnNext;

    public JobListDetailCell(View itemView, Context context) {
        super(itemView);
        mContext = context;

        btnBackGround = itemView.findViewById(R.id.btn_background);
        likeButtonLayout = itemView.findViewById(R.id.like_button_layout);
        btnLike = itemView.findViewById(R.id.btn_like);
        imgCategory = itemView.findViewById(R.id.img_category);
        carouselView = itemView.findViewById(R.id.img_job_detail_photo);
        lblJobTitle = itemView.findViewById(R.id.lbl_job_title);
        lblTime = itemView.findViewById(R.id.lbl_job_detail_end_time);
        lblAddress = itemView.findViewById(R.id.lbl_service_detail_address);
        price = itemView.findViewById(R.id.lbl_service_detail_price);
        bookedCount = itemView.findViewById(R.id.service_detail_booked_count);
        btnNext = itemView.findViewById(R.id.btn_service_detail_next);
    }

    public void setJob(JGGAppointmentModel job) {
        // Jobs Image
        if (job.getAttachmentURLs().size() > 0)
            Picasso.with(mContext)
                    .load(job.getAttachmentURLs().get(0))
                    .placeholder(R.mipmap.placeholder)
                    .into(carouselView);
        else
            Picasso.with(mContext)
                    .load(R.mipmap.placeholder)
                    .placeholder(null)
                    .into(carouselView);
        // Category
        Picasso.with(mContext)
                .load(job.getCategory().getImage())
                .placeholder(null)
                .into(imgCategory);
        // Job title
        lblJobTitle.setText(job.getTitle());
        // Address
        if (job.getAddress().getStreet() == null)
            lblAddress.setText(job.getAddress().getAddress());
        else
            lblAddress.setText(job.getAddress().getStreet());
        // Budget
        price.setText(getAppointmentBudget(job));
        // Delivery Time
        lblTime.setText(getAppointmentTime(job));
    }
}
