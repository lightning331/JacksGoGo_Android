package com.kelvin.jacksgogo.CustomView;

import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.GoClub_Event.JGGEventModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.kelvin.jacksgogo.Utils.Global.setBoldText;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getEventTime;

public class JGGEventInfoWindow implements GoogleMap.InfoWindowAdapter {

    private final View contentView;
    private final Context mContext;

    public ImageView imgCategory;
    public ImageView imgPhoto;
    public TextView txtEventTitle;
    public TextView txtTime;
    public TextView txtClubName;
    public TextView txtEventMembers;

    private ArrayList<JGGEventModel> mEvents = new ArrayList<>();

    public JGGEventInfoWindow(Context context, ArrayList<JGGEventModel> events) {
        contentView = getContentView(LayoutInflater.from(context));
        mContext = context;
        mEvents = events;

        imgCategory = contentView.findViewById(R.id.img_category);
        imgPhoto = contentView.findViewById(R.id.img_event_photo);
        txtEventTitle = contentView.findViewById(R.id.txt_event_title);
        txtTime = contentView.findViewById(R.id.txt_event_end_time);
        txtClubName = contentView.findViewById(R.id.txt_club_name);
        txtEventMembers = contentView.findViewById(R.id.txt_event_members);
    }

    public void setEvent(JGGEventModel event) {
        if (event.getAttachmentURLs().size() != 0) {
            Picasso.with(mContext)
                    .load(event.getAttachmentURLs().get(0))
                    .placeholder(R.mipmap.placeholder)
                    .into(imgPhoto);
        }
        // Category
        Picasso.with(mContext)
                .load(event.getCategory().getImage())
                .placeholder(null)
                .into(imgCategory);
        // Event title
        txtEventTitle.setText(event.getTitle());
        txtTime.setText(getEventTime(event));
        // Club name
        txtClubName.setText("");

        txtEventMembers.setText("");
        String boldText = "32";
        String normalText = " people have to bid on this job!";
        txtEventMembers.append(setBoldText(boldText));
        txtEventMembers.append(normalText);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        if (render(marker, contentView)) {
            contentView.setLayoutParams(getLayoutParams());
            return contentView;
        } else
            return null;
    }

    private View getContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.jgg_event_info_window, null);
    }

    private LinearLayout.LayoutParams getLayoutParams() {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int width = display.getWidth();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)(width * 0.8), LinearLayout.LayoutParams.WRAP_CONTENT);
        return params;
    }

    private boolean render(Marker marker, View view) {

        if (marker.getSnippet() != null) {
            int index = Integer.parseInt(marker.getSnippet());
            JGGEventModel service = mEvents.get(index);
            setEvent(service);
            return true;
        } else {
            return false;
        }
    }
}
