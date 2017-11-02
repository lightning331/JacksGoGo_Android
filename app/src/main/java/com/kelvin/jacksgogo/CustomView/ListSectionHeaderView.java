package com.kelvin.jacksgogo.CustomView;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 10/31/2017.
 */

public class ListSectionHeaderView extends RecyclerView.ViewHolder {

    public TextView txtTitle;

    public ListSectionHeaderView(View itemView) {
        super(itemView);

        txtTitle = (TextView) itemView.findViewById(R.id.list_header_title);
    }

    public void setTitle(String title) {
        txtTitle.setText(title);
    }

    public void setTitle(CharSequence title) {
        txtTitle.setText(title);
    }

    public void setTitle(int resid) {
        txtTitle.setText(resid);
    }

    public void setBackgroundColor(int color) {
        txtTitle.setBackgroundColor(color);
    }
}
