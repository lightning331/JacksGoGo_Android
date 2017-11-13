package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/13/2017.
 */

public class JobDetailFooterCell extends RecyclerView.ViewHolder {

    public TextView title;

    public JobDetailFooterCell(View itemView) {
        super(itemView);
        this.title = itemView.findViewById(R.id.detail_info_footer_title);
    }
}
