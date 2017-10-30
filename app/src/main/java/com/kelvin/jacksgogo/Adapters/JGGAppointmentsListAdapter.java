package com.kelvin.jacksgogo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Models.Appointment;

import java.util.ArrayList;

/**
 * Created by PUMA on 10/26/2017.
 */

public class AppointmentsListAdapter extends ArrayAdapter<Appointment> {
    private ArrayList<Appointment> dataSet;
    Context mContext;

    private static class ViewHolder {
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

    public AppointmentsListAdapter(ArrayList<Appointment> data, Context context) {
        super(context, R.layout.appointment_list_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Appointment dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        // view lookup cache stored in tag
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.appointment_list_item, parent, false);

            viewHolder.lbl_Day = (TextView) convertView.findViewById(R.id.lblDay);
            viewHolder.lbl_Month = (TextView) convertView.findViewById(R.id.lblMonth);
            viewHolder.lbl_Title = (TextView) convertView.findViewById(R.id.lblTitle);
            viewHolder.lbl_Comment = (TextView) convertView.findViewById(R.id.lblComment);
            viewHolder.lbl_Status = (TextView) convertView.findViewById(R.id.lblStatus);
            viewHolder.lbl_BadgeNumber = (TextView) convertView.findViewById(R.id.lblBadgeCount);
            viewHolder.mViewBadgeGroup = (RelativeLayout) convertView.findViewById(R.id.appointment_badgeLayout);
            viewHolder.mViewStatusBar = (RelativeLayout) convertView.findViewById(R.id.appointment_statusLayout);

            viewHolder.lbl_Title.setText(dataModel.getTitle());
            viewHolder.lbl_Comment.setText(dataModel.getComment());
            if (dataModel.getStatus() == Appointment.AppointmentStatus.CANCELLED) {
                viewHolder.lbl_Status.setText("Cancelled");
            } else if (dataModel.getStatus() == Appointment.AppointmentStatus.WITHDRAWN) {
                viewHolder.lbl_Status.setText("Withdrawn");
            } else {
                viewHolder.lbl_Status.setText("");
            }
            if (dataModel.getBadgeNumber() < 1) {
                // Badge view hide when count is less than 1
                viewHolder.mViewBadgeGroup.setVisibility(View.INVISIBLE);
                viewHolder.mViewStatusBar.setVisibility(View.INVISIBLE);
            } else {
                viewHolder.mViewBadgeGroup.setVisibility(View.VISIBLE);
                viewHolder.mViewStatusBar.setVisibility(View.VISIBLE);
                // Show Badge Count
                Integer badgeCount = dataModel.getBadgeNumber();
                viewHolder.lbl_BadgeNumber.setText(String.valueOf(badgeCount));
            }

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
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
