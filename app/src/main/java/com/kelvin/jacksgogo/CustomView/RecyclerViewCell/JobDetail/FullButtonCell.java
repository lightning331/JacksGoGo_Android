package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/13/2017.
 */

public class FullButtonCell extends RecyclerView.ViewHolder {

    public LinearLayout btnBackground;
    public TextView title;

    public FullButtonCell(View itemView) {
        super(itemView);

        btnBackground = itemView.findViewById(R.id.btn_full);
        title = itemView.findViewById(R.id.full_button_title);
    }
}
