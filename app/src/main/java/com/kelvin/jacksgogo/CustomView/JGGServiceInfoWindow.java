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
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserBaseModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

import static com.kelvin.jacksgogo.Utils.Global.setBoldText;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getAppointmentBudget;

/**
 * Created by PUMA on 11/24/2017.
 */

public class JGGServiceInfoWindow implements GoogleMap.InfoWindowAdapter {

    private final View contentView;
    private final Context mContext;

    private ImageView imgPhoto;
    private ImageView imgCategory;
    private TextView lblServiceTitle;
    private MaterialRatingBar rateBar;
    private TextView lblReviewCount;
    private TextView lblAddress;
    private TextView lblPrice;
    private TextView lblBookedCount;

    private ArrayList<JGGAppointmentModel> mServices = new ArrayList<>();

    public JGGServiceInfoWindow(Context context, ArrayList<JGGAppointmentModel> services) {
        contentView = getContentView(LayoutInflater.from(context));
        mContext = context;
        mServices = services;

        imgPhoto = contentView.findViewById(R.id.img_service_info_window_photo);
        imgCategory = contentView.findViewById(R.id.img_service_info_window_category);
        lblServiceTitle = contentView.findViewById(R.id.lbl_service_info_title);
        rateBar = contentView.findViewById(R.id.service_info_window_user_ratingbar);
        lblReviewCount = contentView.findViewById(R.id.lbl_service_info_window_review_count);
        lblAddress = contentView.findViewById(R.id.lbl_service_info_window_address);
        lblPrice = contentView.findViewById(R.id.lbl_service_info_window_price);
        lblBookedCount = contentView.findViewById(R.id.lbl_service_info_window_booked_count);

        lblBookedCount.setText("");
        String boldText = "352";
        String normalText = " people have booked this services!";
        lblBookedCount.append(setBoldText(boldText));
        lblBookedCount.append(normalText);
    }

    private void setData(JGGAppointmentModel service) {
        if (service.getAttachmentURLs().size() != 0) {
            Picasso.with(mContext)
                    .load(service.getAttachmentURLs().get(0))
                    .placeholder(R.mipmap.appointment_placeholder)
                    .into(imgPhoto);
        }
        // Category
        Picasso.with(mContext)
                .load(service.getCategory().getImage())
                .placeholder(null)
                .into(imgCategory);
        lblServiceTitle.setText(service.getTitle());
        // Rating
        JGGUserBaseModel user = service.getUserProfile().getUser();
        if (user.getRate() == null)
            rateBar.setRating(0);
        else
            rateBar.setRating(user.getRate().floatValue());
        // View Count
        lblReviewCount.setText("(327 reviews)");
        // Address
        if (service.getAddress().getStreet() == null)
            lblAddress.setText(service.getAddress().getAddress());
        else
            lblAddress.setText(service.getAddress().getStreet());
        // Budget
        lblPrice.setText(getAppointmentBudget(service));
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
        return inflater.inflate(R.layout.jgg_service_info_window, null);
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
            JGGAppointmentModel service = mServices.get(index);
            setData(service);
            return true;
        } else {
            return false;
        }
    }
}
