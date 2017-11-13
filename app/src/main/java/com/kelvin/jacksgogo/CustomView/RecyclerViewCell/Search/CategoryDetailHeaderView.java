package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Search;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

import org.w3c.dom.Text;

/**
 * Created by PUMA on 11/13/2017.
 */

public class CategoryDetailHeaderView extends RecyclerView.ViewHolder {

    public ImageView categoryIcon;
    public TextView categoryName;
    public TextView verifiedDate;
    public TextView totalListedServices;
    public TextView jobCompleted;
    public LinearLayout categoryExpandButton;

    public CategoryDetailHeaderView(View itemView) {
        super(itemView);

        categoryIcon = itemView.findViewById(R.id.img_category_icon);
        categoryName = itemView.findViewById(R.id.lbl_category_name);
        verifiedDate = itemView.findViewById(R.id.lbl_verified_date);
        totalListedServices = itemView.findViewById(R.id.lbl_total_listed_services);
        jobCompleted = itemView.findViewById(R.id.lbl_job_completed);
        categoryExpandButton = itemView.findViewById(R.id.btn_category_expand);
    }
}
