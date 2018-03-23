package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserBaseModel;
import com.squareup.picasso.Picasso;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

import static com.kelvin.jacksgogo.Utils.JGGTimeManager.convertBudgetOnly;

/**
 * Created by PUMA on 11/13/2017.
 */

public class ServiceListDetailCell extends RecyclerView.ViewHolder {

    private Context mContext;

    public RelativeLayout likeButtonLayout;
    public ImageView btnLike;
    public ImageView imgCategoryDetail;
    public ImageView imgCategory;
    public TextView lblCategoryName;
    public MaterialRatingBar rateBar;
    public TextView lblScore;
    public TextView lblScoreStatus;
    public TextView lblReviewCount;
    public TextView lblAddress;
    public TextView price;
    public TextView bookedCount;
    public LinearLayout btnNext;

    public ServiceListDetailCell(View itemView, Context context) {
        super(itemView);
        mContext = context;

        likeButtonLayout = itemView.findViewById(R.id.like_button_layout);
        btnLike = itemView.findViewById(R.id.btn_like);
        imgCategoryDetail = itemView.findViewById(R.id.img_service_detail_photo);
        imgCategory = itemView.findViewById(R.id.img_service_detail_category);
        lblCategoryName = itemView.findViewById(R.id.lbl_service_detail_category_name);
        rateBar = itemView.findViewById(R.id.service_detail_user_ratingbar);
        lblScore = itemView.findViewById(R.id.lbl_service_detail_score);
        lblScoreStatus = itemView.findViewById(R.id.lbl_service_detail_score_status);
        lblReviewCount = itemView.findViewById(R.id.lbl_service_detail_review_count);
        lblAddress = itemView.findViewById(R.id.lbl_service_detail_address);
        price = itemView.findViewById(R.id.lbl_service_detail_price);
        bookedCount = itemView.findViewById(R.id.service_detail_booked_count);
        btnNext = itemView.findViewById(R.id.btn_service_detail_next);
    }

    public void setService(JGGAppointmentModel service) {
        // Category
        Picasso.with(mContext)
                .load(service.getCategory().getImage())
                .placeholder(null)
                .into(imgCategory);
        lblCategoryName.setText(service.getCategory().getName());
        // Rating
        JGGUserBaseModel user = service.getUserProfile().getUser();
        rateBar.setRating(user.getRate().floatValue());
        // Score
        lblScoreStatus.setText("");
        if (user.getRate() == null) {
            lblScore.setText("");
        } else {
            lblScore.setText(String.valueOf(user.getRate()));
            // Score Status
            float f1 = user.getRate().floatValue();
            float f2 = 4.5f;
            int retval = Float.compare(f1, f2);
            if (retval > 0)
                lblScoreStatus.setText("Very Good");
        }
        // View Count
        lblReviewCount.setText("(327)");
        // Address
        if (service.getAddress().getStreet() == null)
            lblAddress.setText(service.getAddress().getAddress());
        else
            lblAddress.setText(service.getAddress().getStreet());
        // Budget
        price.setText(convertBudgetOnly(service));
    }
}
