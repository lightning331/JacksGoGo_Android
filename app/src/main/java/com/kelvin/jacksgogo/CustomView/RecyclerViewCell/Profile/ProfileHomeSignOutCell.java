package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Profile;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 1/27/2018.
 */

public class ProfileHomeSignOutCell extends RecyclerView.ViewHolder {

    public LinearLayout btnSignOut;

    public ProfileHomeSignOutCell(View itemView) {
        super(itemView);

        btnSignOut = (LinearLayout) itemView.findViewById(R.id.btn_sign_out);
    }
}
