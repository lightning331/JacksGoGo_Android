package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.GoClub_Events;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Created by PUMA on 12/18/2017.
 */

public class EventListDetailCell extends RecyclerView.ViewHolder {

    private Context mContext;

    private ImageView imgEvent;
    private RelativeLayout likeLayout;
    private ImageView imgLike;
    private ImageView imgEventCategory;
    private TextView lblEventTime;
    private RoundedImageView imgEventGroup;
    private TextView lblJoinedCount;

    public EventListDetailCell(View itemView, Context context) {
        super(itemView);
        mContext = context;

        imgEvent = itemView.findViewById(R.id.img_event_detail_photo);
        likeLayout = itemView.findViewById(R.id.like_button_layout);
        imgLike = itemView.findViewById(R.id.img_like);
        imgEventCategory = itemView.findViewById(R.id.img_event_detail_category);
        lblEventTime = itemView.findViewById(R.id.lbl_event_detail_time);
        imgEventGroup = itemView.findViewById(R.id.img_event_group);
        lblJoinedCount = itemView.findViewById(R.id.lbl_event_detail_joined_count);
    }
}
