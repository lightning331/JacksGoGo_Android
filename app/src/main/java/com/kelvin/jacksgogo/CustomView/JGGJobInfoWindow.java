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
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.kelvin.jacksgogo.Utils.Global.setBoldText;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getAppointmentBudget;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getAppointmentTime;

public class JGGJobInfoWindow implements GoogleMap.InfoWindowAdapter {

    private final View contentView;
    private final Context mContext;

    public ImageView imgCategory;
    public ImageView imgPhoto;
    public TextView lblJobTitle;
    public TextView lblTime;
    public TextView lblAddress;
    public TextView price;
    public TextView bookedCount;

    private ArrayList<JGGAppointmentModel> mJobs = new ArrayList<>();

    public JGGJobInfoWindow(Context context, ArrayList<JGGAppointmentModel> jobs) {
        contentView = getContentView(LayoutInflater.from(context));
        mContext = context;
        mJobs = jobs;

        imgCategory = contentView.findViewById(R.id.img_category);
        imgPhoto = contentView.findViewById(R.id.img_job_detail_photo);
        lblJobTitle = contentView.findViewById(R.id.lbl_job_title);
        lblTime = contentView.findViewById(R.id.lbl_job_detail_end_time);
        lblAddress = contentView.findViewById(R.id.lbl_service_detail_address);
        price = contentView.findViewById(R.id.lbl_service_detail_price);
        bookedCount = contentView.findViewById(R.id.service_detail_booked_count);
    }

    public void setJob(JGGAppointmentModel job) {
        if (job.getAttachmentURLs().size() != 0) {
            Picasso.with(mContext)
                    .load(job.getAttachmentURLs().get(0))
                    .placeholder(R.mipmap.placeholder)
                    .into(imgPhoto);
        }
        // Category
        Picasso.with(mContext)
                .load(job.getCategory().getImage())
                .placeholder(null)
                .into(imgCategory);
        // Job title
        lblJobTitle.setText(job.getTitle());
        // Address
        if (job.getAddress().getStreet() == null)
            lblAddress.setText(job.getAddress().getAddress());
        else
            lblAddress.setText(job.getAddress().getStreet());
        // Budget
        price.setText(getAppointmentBudget(job));
        // Delivery Time
        lblTime.setText(getAppointmentTime(job));

        bookedCount.setText("");
        String boldText = "32";
        String normalText = " people have to bid on this job!";
        bookedCount.append(setBoldText(boldText));
        bookedCount.append(normalText);
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
        return inflater.inflate(R.layout.jgg_job_info_window, null);
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
            JGGAppointmentModel service = mJobs.get(index);
            setJob(service);
            return true;
        } else {
            return false;
        }
    }
}
