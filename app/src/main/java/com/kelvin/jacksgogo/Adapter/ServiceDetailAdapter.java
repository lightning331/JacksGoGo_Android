package com.kelvin.jacksgogo.Adapter;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail.FullButtonCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail.JobDetailDescriptionCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail.JobDetailImageCarouselCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetailUserNameRatingCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail.ServiceDetailBookedInfoCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail.ServiceDetailCategoryCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail.ServiceDetailTagListCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail.ServiceDetailTimeSlotsCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail.ServiceDetailTotalReviewCell;
import com.kelvin.jacksgogo.Fragments.Appointments.ServiceDetailFragment;
import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/7/2017.
 */

public class ServiceDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ServiceDetailFragment mContext;

    int ITEM_COUNT = 11;

    public ServiceDetailAdapter(ServiceDetailFragment context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case 0:
                View imgPageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_detail_image_carousel_cell, parent, false);
                JobDetailImageCarouselCell pageViewHolder = new JobDetailImageCarouselCell(imgPageView);

                int[] array = {R.drawable.carousel03, R.drawable.carousel01,
                        R.drawable.carousel02, R.drawable.carousel03, R.drawable.carousel02, R.drawable.carousel01, R.drawable.carousel03, R.drawable.carousel02};

                pageViewHolder.imageArray = array;
                pageViewHolder.carouselView.setPageCount(array.length);
                pageViewHolder.carouselView.setImageListener(pageViewHolder.imageListener);
                return pageViewHolder;
            case 1:
                View postCategoryView = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_detail_category_cell, parent, false);
                ServiceDetailCategoryCell postCategoryViewHolder = new ServiceDetailCategoryCell(postCategoryView);
                return postCategoryViewHolder;
            case 2:
                View priceView = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_detail_description_cell, parent, false);
                JobDetailDescriptionCell priceViewHolder = new JobDetailDescriptionCell(priceView);
                priceViewHolder.descriptionImage.setImageResource(R.mipmap.icon_budget);
                priceViewHolder.description.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
                priceViewHolder.description.setText("$50-$100");
                return priceViewHolder;
            case 3:
                View descriptionView = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_detail_description_cell, parent, false);
                JobDetailDescriptionCell descriptionViewHolder = new JobDetailDescriptionCell(descriptionView);
                descriptionViewHolder.descriptionImage.setImageResource(R.mipmap.icon_info);
                descriptionViewHolder.description.setText("We are experts at gardening & landscaping. Please state in your quotation:size of your garden, " +
                        "what tasks you need deon, and any special requirements.");
                return descriptionViewHolder;
            case 4:
                View addressView = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_detail_description_cell, parent, false);
                JobDetailDescriptionCell addressViewHolder = new JobDetailDescriptionCell(addressView);
                addressViewHolder.descriptionImage.setImageResource(R.mipmap.icon_location);
                addressViewHolder.description.setText("Smith Street, 0.4km away");
                return addressViewHolder;
            case 5:
                View timeSlotsView = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_detail_time_slots_cell, parent, false);
                ServiceDetailTimeSlotsCell timeSlotsAvailableViewHolder = new ServiceDetailTimeSlotsCell(timeSlotsView);
                return timeSlotsAvailableViewHolder;
            case 6:
                View reviewView = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_detail_total_review_cell, parent, false);
                ServiceDetailTotalReviewCell reviewViewHolder = new ServiceDetailTotalReviewCell(reviewView);
                reviewViewHolder.ratingBar.setRating((float)4.6);
                return reviewViewHolder;
            case 7:
                View posterInfoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_detail_user_name_rating_cell, parent, false);
                JobDetailUserNameRatingCell posterInfoViewHolder = new JobDetailUserNameRatingCell(posterInfoView);
                posterInfoViewHolder.ratingBar.setRating((float)4.8);
                return posterInfoViewHolder;
            case 8:
                View tagListView = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_detail_tag_list_cell, parent, false);
                ServiceDetailTagListCell tagListViewHolder = new ServiceDetailTagListCell(tagListView);
                return tagListViewHolder;
            case 9:
                View bookedInfoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_detail_booked_info_cell, parent, false);
                ServiceDetailBookedInfoCell bookedInfoViewHolder = new ServiceDetailBookedInfoCell(bookedInfoView);
                return bookedInfoViewHolder;
            case 10:
                View quotationView = LayoutInflater.from(parent.getContext()).inflate(R.layout.full_button_cell, parent, false);
                FullButtonCell quotationViewHolder = new FullButtonCell(quotationView);
                return quotationViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return ITEM_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}

