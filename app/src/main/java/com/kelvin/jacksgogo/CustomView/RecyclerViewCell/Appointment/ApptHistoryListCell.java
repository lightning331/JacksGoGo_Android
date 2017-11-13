package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Appointment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Created by PUMA on 11/13/2017.
 */

public class ApptHistoryListCell extends RecyclerView.ViewHolder {

    public TextView lbl_Day;
    public TextView lbl_Month;
    public TextView lbl_Title;
    public TextView lbl_Comment;
    public TextView lbl_Status;
    public TextView lbl_BadgeNumber;
    public ImageView img_Profile;
    public RelativeLayout mViewStatusBar;
    public LinearLayout mAppointmentsHomeListCell;

    public ApptHistoryListCell(View itemView) {
        super(itemView);

        lbl_Day = (TextView) itemView.findViewById(R.id.lblDay);
        lbl_Month = (TextView) itemView.findViewById(R.id.lblMonth);
        lbl_Title = (TextView) itemView.findViewById(R.id.lblTitle);
        lbl_Comment = (TextView) itemView.findViewById(R.id.lblComment);
        lbl_Status = (TextView) itemView.findViewById(R.id.lblStatus);
        lbl_BadgeNumber = (TextView) itemView.findViewById(R.id.lblBadgeCount);
        img_Profile = (RoundedImageView) itemView.findViewById(R.id.imgAvatar);
        mViewStatusBar = (RelativeLayout) itemView.findViewById(R.id.appointment_statusLayout);
    }
}
