package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Search;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/14/2017.
 */

public class SearchCategoryListView extends RecyclerView.ViewHolder {

    public LinearLayout otherButton;

    public SearchCategoryListView(View itemView) {
        super(itemView);

        otherButton = itemView.findViewById(R.id.btn_other_professions);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        otherButton.setOnClickListener(listener);
    }
}
