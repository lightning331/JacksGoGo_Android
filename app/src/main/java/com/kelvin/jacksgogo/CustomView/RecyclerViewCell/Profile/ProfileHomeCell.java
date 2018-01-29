package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Profile;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 1/27/2018.
 */

public class ProfileHomeCell extends RecyclerView.ViewHolder {

    public TextView title1;
    public LinearLayout button1;
    public TextView title2;
    public LinearLayout button2;

    public ProfileHomeCell(View itemView) {
        super(itemView);

        title1 = (TextView) itemView.findViewById(R.id.lbl_profile_home_title_1);
        title2 = (TextView) itemView.findViewById(R.id.lbl_profile_home_title_2);
        button1 = (LinearLayout) itemView.findViewById(R.id.btn_profile_home_1);
        button2 = (LinearLayout) itemView.findViewById(R.id.btn_profile_home_2);
    }
}
