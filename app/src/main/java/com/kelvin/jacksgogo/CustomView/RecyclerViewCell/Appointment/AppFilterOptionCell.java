package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Appointment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Created by PUMA on 11/13/2017.
 */

public class AppFilterOptionCell extends RecyclerView.ViewHolder {

    public RoundedImageView btnOriginal;
    public TextView title;

    @SuppressLint("RestrictedApi")
    public AppFilterOptionCell(View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.lblFilterTitle);
        title.setTextColor(Color.parseColor("#20BF3B"));
        title.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

        btnOriginal = itemView.findViewById(R.id.view_filter_bg);
        btnOriginal.setBackgroundColor(Color.parseColor("#FFFFFF"));
        btnOriginal.setBorderColor(Color.parseColor("#20BF3B"));
        btnOriginal.setBorderWidth((float) 4);
        btnOriginal.setCornerRadius((float) 5);
        btnOriginal.setOval(false);
        btnOriginal.mutateBackground(true);
    }
}
