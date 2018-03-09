package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/13/2017.
 */

public class ServiceDetailCategoryCell extends RecyclerView.ViewHolder {

    public ImageView imgCategory;
    public TextView lblCategory;
    public TextView title;

    public ServiceDetailCategoryCell(View itemView) {
        super(itemView);

        imgCategory = itemView.findViewById(R.id.img_original_post_title);
        lblCategory = itemView.findViewById(R.id.lbl_original_post_image_name);
        title = itemView.findViewById(R.id.lbl_original_post_title);
    }
}
