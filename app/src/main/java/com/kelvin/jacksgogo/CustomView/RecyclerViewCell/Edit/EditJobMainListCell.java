package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Edit;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/13/2017.
 */

public class EditJobMainListCell extends RecyclerView.ViewHolder {

    public LinearLayout tagButton;
    public TextView tagTitleTextView;
    public TextView jobDescTitleTextView;
    public TextView jobDescriptionTextView;

    public EditJobMainListCell(View itemView) {
        super(itemView);

        this.tagButton = itemView.findViewById(R.id.btn_edit_job_tag1);
        this.tagTitleTextView = itemView.findViewById(R.id.lbl_edit_job_tag_title1);
        this.jobDescTitleTextView = itemView.findViewById(R.id.lbl_edit_job_description_title1);
        this.jobDescriptionTextView = itemView.findViewById(R.id.lbl_edit_job_description1);
    }
}
