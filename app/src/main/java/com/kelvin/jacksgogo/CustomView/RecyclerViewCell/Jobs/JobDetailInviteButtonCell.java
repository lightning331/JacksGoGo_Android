package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/13/2017.
 */

public class JobDetailInviteButtonCell extends RecyclerView.ViewHolder {

    public TextView inviteButton;

    public JobDetailInviteButtonCell(View itemView) {
        super(itemView);

        inviteButton = itemView.findViewById(R.id.btn_invite);
    }
}
