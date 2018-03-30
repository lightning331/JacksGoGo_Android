package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;
import com.squareup.picasso.Picasso;

/**
 * Created by PUMA on 11/13/2017.
 */

public class ServiceListCell extends RecyclerView.ViewHolder {

    private Context mContext;

    public ImageView categoryIcon;
    public TextView categoryName;
    public TextView verifiedDate;
    public TextView totalListedServices;
    public TextView jobCompleted;
    public LinearLayout btnServiceListDetail;

    public ServiceListCell(View itemView, Context context) {
        super(itemView);
        mContext = context;

        categoryIcon = itemView.findViewById(R.id.img_category_icon);
        categoryName = itemView.findViewById(R.id.lbl_category_name);
        verifiedDate = itemView.findViewById(R.id.lbl_verified_date);
        totalListedServices = itemView.findViewById(R.id.lbl_total_listed_services);
        jobCompleted = itemView.findViewById(R.id.lbl_job_completed);
        btnServiceListDetail = itemView.findViewById(R.id.btn_service_listing_next);
    }

    public void setData(JGGCategoryModel category) {
        categoryName.setText(category.getName());
        Picasso.with(mContext)
                .load(category.getImage())
                .placeholder(null)
                .into(categoryIcon);
    }
}
