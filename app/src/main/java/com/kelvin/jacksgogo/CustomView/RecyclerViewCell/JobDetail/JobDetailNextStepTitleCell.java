package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Created by PUMA on 11/13/2017.
 */

public class JobDetailNextStepTitleCell extends RecyclerView.ViewHolder {

    public TextView title;
    public RoundedImageView avatar;
    public LinearLayout markLine;

    public JobDetailNextStepTitleCell(View itemView) {
        super(itemView);

        this.title = itemView.findViewById(R.id.lbl_next_step_title);
        this.avatar = itemView.findViewById(R.id.next_step_img_avatar);
        this.markLine = itemView.findViewById(R.id.next_step_mark_line);
    }
}
