package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Profile;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

/**
 * Created by storm2 on 4/17/2018.
 */

public class CreditCell extends RecyclerView.ViewHolder {
    public TextView txtDate;
    public TextView txtSource;
    public TextView txtUsername;
    public TextView txtJobRef;

    public CreditCell(View itemView) {
        super(itemView);

        txtDate = (TextView) itemView.findViewById(R.id.txtDate);
        txtSource = (TextView) itemView.findViewById(R.id.txtSource);
        txtUsername = (TextView) itemView.findViewById(R.id.txtUsername);
        txtJobRef = (TextView) itemView.findViewById(R.id.txtJobRef);
    }
}
