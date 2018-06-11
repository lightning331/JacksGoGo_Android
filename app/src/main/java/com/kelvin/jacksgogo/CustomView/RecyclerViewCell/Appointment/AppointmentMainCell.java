package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Appointment;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.Date;

import static com.kelvin.jacksgogo.Utils.Global.JGGJobStatus.closed;
import static com.kelvin.jacksgogo.Utils.Global.JGGJobStatus.flagged;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getAppointmentDay;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getAppointmentMonth;

/**
 * Created by PUMA on 11/13/2017.
 */

public class AppointmentMainCell extends RecyclerView.ViewHolder {

    private Context mContext;

    public TextView lbl_Day;
    public TextView lbl_Month;
    public TextView lbl_Title;
    public TextView lbl_Comment;
    public TextView lbl_Status;
    public TextView lbl_BadgeNumber;
    public ImageView img_Profile;
    public RelativeLayout mViewStatusBar;

    public AppointmentMainCell(Context context, View itemView) {
        super(itemView);
        mContext = context;

        lbl_Day = (TextView) itemView.findViewById(R.id.lblDay);
        lbl_Month = (TextView) itemView.findViewById(R.id.lblMonth);
        lbl_Title = (TextView) itemView.findViewById(R.id.lblTitle);
        lbl_Comment = (TextView) itemView.findViewById(R.id.lblComment);
        lbl_Status = (TextView) itemView.findViewById(R.id.lblStatus);
        lbl_BadgeNumber = (TextView) itemView.findViewById(R.id.lblBadgeCount);
        img_Profile = (RoundedImageView) itemView.findViewById(R.id.imgAvatar);
        mViewStatusBar = (RelativeLayout) itemView.findViewById(R.id.appointment_statusLayout);
    }

    public void setAppointment(JGGAppointmentModel appointment) {
        lbl_Title.setText(appointment.getTitle());
        lbl_Comment.setText(appointment.getDescription());
        if (appointment.getSessions() == null || appointment.getSessions().size() == 0) {
            lbl_Day.setText("");
            lbl_Month.setText("");
        } else {
            Date appDay = appointment.getSessions().get(0).getStartOn();
            lbl_Day.setText(getAppointmentDay(appDay));
            lbl_Month.setText(getAppointmentMonth(appDay));
        }
        Picasso.with(mContext)
                .load(appointment.getUserProfile().getUser().getPhotoURL())
                .placeholder(R.mipmap.icon_profile)
                .into(img_Profile);

        if (appointment.getStatus() == closed) {
            // TODO- need to fix
            lbl_Status.setVisibility(View.GONE);
            //lbl_Status.setText("Cancelled");
        } else if (appointment.getStatus() == flagged) {
            //lbl_Status.setText("Withdrawn");
        } else {
            lbl_Status.setVisibility(View.GONE);
        }

        if (appointment.getUserProfileID().equals(appointment.getID())) {
            lbl_Day.setTextColor(ContextCompat.getColor(mContext, R.color.JGGGreen));
            lbl_Month.setTextColor(ContextCompat.getColor(mContext, R.color.JGGGreen));
        } else {
            lbl_Day.setTextColor(ContextCompat.getColor(mContext, R.color.JGGCyan));
            lbl_Month.setTextColor(ContextCompat.getColor(mContext, R.color.JGGCyan));
        }
//            else if (appointment instanceof ) {
//                lbl_Day.setTextColor(ContextCompat.getColor(getContext(), R.color.JGGPurple));
//                lbl_Month.setTextColor(ContextCompat.getColor(getContext(), R.color.JGGPurple));
//            }

//            if (appointment.getBadgeNumber() < 1) {
//                // Badge view hide when count is less than 1
//                lbl_BadgeNumber.setVisibility(View.INVISIBLE);
//                mViewStatusBar.setVisibility(View.INVISIBLE);
//            } else {
//                lbl_BadgeNumber.setVisibility(View.VISIBLE);
//                mViewStatusBar.setVisibility(View.VISIBLE);
//                // Show Badge Count
//                Integer badgeCount = appointment.getBadgeNumber();
//                lbl_BadgeNumber.setText(String.valueOf(badgeCount));
//            }
    }
}
