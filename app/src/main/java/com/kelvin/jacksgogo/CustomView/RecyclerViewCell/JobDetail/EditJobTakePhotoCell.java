package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/13/2017.
 */

public class EditJobTakePhotoCell extends RecyclerView.ViewHolder {

    public LinearLayout btnTakePhoto;

    public EditJobTakePhotoCell(View itemView) {
        super(itemView);

        btnTakePhoto = itemView.findViewById(R.id.btn_take_photo);
    }
}
