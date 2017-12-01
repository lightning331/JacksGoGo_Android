package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Search;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/13/2017.
 */

public class ServiceDetailCategoryCell extends RecyclerView.ViewHolder {

    ImageView postImage;
    TextView postImageTitle;
    TextView title;

    public ServiceDetailCategoryCell(View itemView) {
        super(itemView);

        postImage = itemView.findViewById(R.id.img_original_post_title);
        postImageTitle = itemView.findViewById(R.id.lbl_original_post_image_name);
        title = itemView.findViewById(R.id.lbl_original_post_title);
    }
}
