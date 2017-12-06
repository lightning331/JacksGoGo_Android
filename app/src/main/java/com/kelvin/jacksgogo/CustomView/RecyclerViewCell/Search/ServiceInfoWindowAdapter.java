package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Search;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.kelvin.jacksgogo.CustomView.CustomTypefaceSpan;
import com.kelvin.jacksgogo.R;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by PUMA on 11/24/2017.
 */

public class ServiceInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View contentView;
    private final Context mContext;

    ImageView imgPhoto;
    ImageView imgCategory;
    TextView lblCategoryName;
    MaterialRatingBar rateBar;
    TextView lblReviewCount;
    TextView lblAddress;
    TextView lblPrice;
    TextView lblBookedCount;

    public ServiceInfoWindowAdapter(Context context) {
        contentView = getContentView(LayoutInflater.from(context));
        mContext = context;

        imgPhoto = (ImageView) contentView.findViewById(R.id.img_service_info_window_photo);
        imgCategory = (ImageView) contentView.findViewById(R.id.img_service_info_window_category);
        lblCategoryName = (TextView) contentView.findViewById(R.id.lbl_service_info_window_category_name);
        rateBar = (MaterialRatingBar) contentView.findViewById(R.id.service_info_window_user_ratingbar);
        lblReviewCount = (TextView) contentView.findViewById(R.id.lbl_service_info_window_review_count);
        lblAddress = (TextView) contentView.findViewById(R.id.lbl_service_info_window_address);
        lblPrice = (TextView) contentView.findViewById(R.id.lbl_service_info_window_price);
        lblBookedCount = (TextView) contentView.findViewById(R.id.lbl_service_info_window_booked_count);

        String boldText = "352";
        String normalText = " people have booked this services!";

        Typeface muliBold = Typeface.create("mulibold", Typeface.BOLD);
        SpannableString spannableString = new SpannableString(boldText);
        spannableString.setSpan(new CustomTypefaceSpan("", muliBold), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        lblBookedCount.append(spannableString);
        lblBookedCount.append(normalText);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        contentView.setLayoutParams(getLayoutParams());
        return contentView;
    }

    private View getContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.view_service_info_window_adapter, null);
    }

    private LinearLayout.LayoutParams getLayoutParams() {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int width = display.getWidth();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)(width * 0.8), LinearLayout.LayoutParams.WRAP_CONTENT);
        return params;
    }
}
