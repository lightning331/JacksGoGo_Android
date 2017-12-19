package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/13/2017.
 */

public class SearchCategoryCell extends RelativeLayout {

    private Context mContext;

    public ImageView categoryIcon;
    public TextView categoryTitle;

    public SearchCategoryCell(Context context) {
        super(context);
        mContext = context;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView = inflater.inflate(R.layout.cell_search_category, null);

        categoryIcon = (ImageView) gridView.findViewById(R.id.img_search_category);
        categoryTitle = (TextView) gridView.findViewById(R.id.lbl_search_category);
    }

    public void setData(int icons[], String names[]) {
        for (int icon : icons) {
            this.categoryIcon.setImageResource(icon);
        }
        for (String name : names) {
            this.categoryTitle.setText(name);
        }
    }
}
