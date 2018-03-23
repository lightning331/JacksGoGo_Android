package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 3/22/2018.
 */

public class QuotationCoordinateCell extends RecyclerView.ViewHolder {

    public TextView lblCoordinate;

    public QuotationCoordinateCell(View itemView) {
        super(itemView);

        lblCoordinate = itemView.findViewById(R.id.lbl_coordinate);
    }
}
