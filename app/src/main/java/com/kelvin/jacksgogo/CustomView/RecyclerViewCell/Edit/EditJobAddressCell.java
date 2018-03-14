package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Edit;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/13/2017.
 */

public class EditJobAddressCell extends RecyclerView.ViewHolder {

    public TextView hint;
    public EditText title;
    public LinearLayout titleLayout;
    public LinearLayout txtLayout;

    public EditJobAddressCell(View itemView) {
        super(itemView);
        hint = itemView.findViewById(R.id.lbl_edit_job_address_hint);
        title = itemView.findViewById(R.id.txt_edit_job_address);
        titleLayout = itemView.findViewById(R.id.title_layout);
        txtLayout = itemView.findViewById(R.id.txt_layout);
    }
}