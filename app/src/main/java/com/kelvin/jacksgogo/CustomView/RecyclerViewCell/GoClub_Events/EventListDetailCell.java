package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.GoClub_Events;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.GoClub_Event.JGGEventModel;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getEventTime;

/**
 * Created by PUMA on 12/18/2017.
 */

public class EventListDetailCell extends RecyclerView.ViewHolder {

    private Context mContext;

    private ImageView imgEvent;
    private RelativeLayout likeLayout;
    private ImageView imgLike;
    private ImageView imgEventCategory;
    private TextView lblEventTitle;
    private TextView lblEventTime;
    private RoundedImageView imgEventGroup;
    private TextView lblGroup;
    private TextView lblJoinedCount;

    public EventListDetailCell(View itemView, Context context) {
        super(itemView);
        mContext = context;

        imgEvent = itemView.findViewById(R.id.img_event_detail_photo);
        likeLayout = itemView.findViewById(R.id.like_button_layout);
        imgLike = itemView.findViewById(R.id.img_like);
        imgEventCategory = itemView.findViewById(R.id.img_event_detail_category);
        lblEventTitle = itemView.findViewById(R.id.lbl_event_title);
        lblEventTime = itemView.findViewById(R.id.lbl_event_detail_time);
        imgEventGroup = itemView.findViewById(R.id.img_event_group);
        lblGroup = itemView.findViewById(R.id.lbl_event_group_name);
        lblJoinedCount = itemView.findViewById(R.id.lbl_event_detail_joined_count);
    }

    public void setEvent(JGGEventModel event) {
        // Event Image
        if (event.getAttachmentURLs().size() > 0)
            Picasso.with(mContext)
                    .load(event.getAttachmentURLs().get(0))
                    .placeholder(R.mipmap.placeholder)
                    .into(imgEvent);
        else
            Picasso.with(mContext)
                    .load(R.mipmap.placeholder)
                    .placeholder(R.mipmap.placeholder)
                    .into(imgEvent);
        // Event Category
        Picasso.with(mContext)
                .load(event.getCategory().getImage())
                .placeholder(null)
                .into(imgEventCategory);
        // Event Title
        lblEventTitle.setText(event.getTitle());
        // Event Time
        lblEventTime.setText(getEventTime(event));
        // Event Group name and address
        String placeName = event.getAddress().get(0).getPlaceName();
        String address = event.getAddress().get(0).getFullAddress();
        if (placeName == null) {}
        else
            address = placeName + ", " + address;
        lblGroup.setText(address);

    }
}
