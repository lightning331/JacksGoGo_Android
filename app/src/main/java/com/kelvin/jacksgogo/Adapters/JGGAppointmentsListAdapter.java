package com.kelvin.jacksgogo.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.Models.JGGEventModel;
import com.kelvin.jacksgogo.Models.JGGServiceModel;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Models.JGGAppointmentBaseModel;

import java.util.ArrayList;

import static com.kelvin.jacksgogo.R.color.color_jobs;

/**
 * Created by PUMA on 10/26/2017.
 */

public class JGGAppointmentsListAdapter extends ArrayAdapter<JGGAppointmentBaseModel> {
    private ArrayList<JGGAppointmentBaseModel> dataSet;
    Context mContext;

    private static class AppointmentList {
        TextView lbl_Day;
        TextView lbl_Month;
        TextView lbl_Title;
        TextView lbl_Comment;
        TextView lbl_Status;
        TextView lbl_BadgeNumber;
        ImageView img_Profile;
        RelativeLayout mViewBadgeGroup;
        RelativeLayout mViewStatusBar;
    }

    public JGGAppointmentsListAdapter(ArrayList<JGGAppointmentBaseModel> data, Context context) {
        super(context, R.layout.appointment_list_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        JGGAppointmentBaseModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        // view lookup cache stored in tag
        AppointmentList appointmentList;

        final View result;

        if (convertView == null) {

            appointmentList = new AppointmentList();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.appointment_list_item, parent, false);

            appointmentList.lbl_Day = (TextView) convertView.findViewById(R.id.lblDay);
            appointmentList.lbl_Month = (TextView) convertView.findViewById(R.id.lblMonth);
            appointmentList.lbl_Title = (TextView) convertView.findViewById(R.id.lblTitle);
            appointmentList.lbl_Comment = (TextView) convertView.findViewById(R.id.lblComment);
            appointmentList.lbl_Status = (TextView) convertView.findViewById(R.id.lblStatus);
            appointmentList.lbl_BadgeNumber = (TextView) convertView.findViewById(R.id.lblBadgeCount);
            appointmentList.mViewBadgeGroup = (RelativeLayout) convertView.findViewById(R.id.appointment_badgeLayout);
            appointmentList.mViewStatusBar = (RelativeLayout) convertView.findViewById(R.id.appointment_statusLayout);

            appointmentList.lbl_Title.setText(dataModel.getTitle());
            appointmentList.lbl_Comment.setText(dataModel.getComment());
            appointmentList.lbl_Day.setText(dataModel.getAppointmentDay());
            appointmentList.lbl_Month.setText(dataModel.getAppointmentMonth());

            if (dataModel.getStatus() == JGGAppointmentBaseModel.AppointmentStatus.CANCELLED) {
                appointmentList.lbl_Status.setText("Cancelled");
            } else if (dataModel.getStatus() == JGGAppointmentBaseModel.AppointmentStatus.WITHDRAWN) {
                appointmentList.lbl_Status.setText("Withdrawn");
            } else {
                appointmentList.lbl_Status.setText("");
            }
            if (dataModel.getBadgeNumber() < 1) {
                // Badge view hide when count is less than 1
                appointmentList.mViewBadgeGroup.setVisibility(View.INVISIBLE);
                appointmentList.mViewStatusBar.setVisibility(View.INVISIBLE);
            } else {
                appointmentList.mViewBadgeGroup.setVisibility(View.VISIBLE);
                appointmentList.mViewStatusBar.setVisibility(View.VISIBLE);
                // Show Badge Count
                Integer badgeCount = dataModel.getBadgeNumber();
                appointmentList.lbl_BadgeNumber.setText(String.valueOf(badgeCount));
            }

            appointmentList.lbl_Day.setTextColor(ContextCompat.getColor(getContext(), R.color.color_jobs));
            appointmentList.lbl_Month.setTextColor(ContextCompat.getColor(getContext(), R.color.color_jobs));
            if (dataModel instanceof JGGServiceModel) {
                appointmentList.lbl_Day.setTextColor(ContextCompat.getColor(getContext(), R.color.color_services));
                appointmentList.lbl_Month.setTextColor(ContextCompat.getColor(getContext(), R.color.color_services));
            } else if (dataModel instanceof JGGEventModel){
                appointmentList.lbl_Day.setTextColor(ContextCompat.getColor(getContext(), R.color.color_goclub));
                appointmentList.lbl_Month.setTextColor(ContextCompat.getColor(getContext(), R.color.color_goclub));
            }

            result=convertView;

            convertView.setTag(appointmentList);
        } else {
            appointmentList = (AppointmentList) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

//        viewHolder.info.setOnClickListener(this);
//        viewHolder.info.setTag(position);

        // Return the completed view to render on screen
        return convertView;
    }

//    @Override
//    public void onClick(View v) {
//
//        int position=(Integer) v.getTag();
//        Object object= getItem(position);
//        DataModel dataModel=(DataModel)object;
//
//        switch (v.getId())
//        {
//            case R.id.item_info:
//                Snackbar.make(v, "Release date " +dataModel.getFeature(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();
//                break;
//        }
//    }
}
