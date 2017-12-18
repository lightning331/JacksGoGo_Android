package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/13/2017.
 */

public class SearchCategoryCell extends RecyclerView.ViewHolder {

    ImageView categoryIcon;
    TextView categoryTitle;

    public SearchCategoryCell(View itemView) {
        super(itemView);

        categoryIcon = itemView.findViewById(R.id.img_search_category);
        categoryTitle = itemView.findViewById(R.id.lbl_search_category);
    }
}
