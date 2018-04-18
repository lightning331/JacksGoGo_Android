package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Profile;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

/**
 * Created by storm2 on 4/17/2018.
 */

public class CreditHeaderCell extends RecyclerView.ViewHolder {
    public TextView txtAdded;
    public TextView txtUsed;

    public CreditHeaderCell(View itemView) {
        super(itemView);
        txtAdded = (TextView) itemView.findViewById(R.id.txt_added);
        txtUsed = (TextView) itemView.findViewById(R.id.txt_used);
    }
}
