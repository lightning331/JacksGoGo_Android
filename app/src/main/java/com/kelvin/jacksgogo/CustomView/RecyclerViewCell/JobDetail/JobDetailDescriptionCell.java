package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/13/2017.
 */

public class JobDetailDescriptionCell extends RecyclerView.ViewHolder {

    public ImageView descriptionImage;
    public TextView description;

    public JobDetailDescriptionCell(View itemView) {
        super(itemView);

        descriptionImage = itemView.findViewById(R.id.img_description);
        description = itemView.findViewById(R.id.lbl_description);
    }
}
