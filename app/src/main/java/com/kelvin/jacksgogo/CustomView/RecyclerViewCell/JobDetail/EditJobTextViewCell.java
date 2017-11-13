package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/13/2017.
 */

public class EditJobTextViewCell extends RecyclerView.ViewHolder {

    public EditText editText;

    public EditJobTextViewCell(View itemView) {
        super(itemView);
        editText = itemView.findViewById(R.id.txt_title);
    }
}
