package com.kelvin.jacksgogo.Adapter.Service;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.jacksgogo.Activities.Appointment.AppMapViewActivity;
import com.kelvin.jacksgogo.Activities.Search.ActiveServiceActivity;
import com.kelvin.jacksgogo.Activities.Search.ServiceReviewsActivity;
import com.kelvin.jacksgogo.Activities.Search.ServiceTimeSlotsActivity;
import com.kelvin.jacksgogo.CustomView.CustomTypefaceSpan;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail.JobDetailDescriptionCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail.JobDetailImageCarouselCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail.JobDetailLocationCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail.JobDetailUserNameRatingCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Search.ServiceDetailReferenceNoCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Search.ServiceDetailCategoryCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Search.ServiceDetailTagListCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Search.ServiceDetailTimeSlotsCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Search.ServiceDetailTotalReviewCell;
import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/7/2017.
 */

public class ServiceDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context mContext;

    boolean isService;

    int ITEM_COUNT = 11;

    public ServiceDetailAdapter(Context context, boolean serviceStatus) {
        this.mContext = context;
        isService = serviceStatus;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case 0:
                View imgPageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_image_carousel, parent, false);
                JobDetailImageCarouselCell pageViewHolder = new JobDetailImageCarouselCell(imgPageView);

                int[] array = {R.drawable.carousel03, R.drawable.carousel01,
                        R.drawable.carousel02, R.drawable.carousel03, R.drawable.carousel02, R.drawable.carousel01, R.drawable.carousel03, R.drawable.carousel02};

                pageViewHolder.imageArray = array;
                pageViewHolder.carouselView.setPageCount(array.length);
                pageViewHolder.carouselView.setImageListener(pageViewHolder.imageListener);
                return pageViewHolder;
            case 1:
                View postCategoryView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_service_detail_header, parent, false);
                ServiceDetailCategoryCell postCategoryViewHolder = new ServiceDetailCategoryCell(postCategoryView);
                return postCategoryViewHolder;
            case 2:
                View priceView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_description, parent, false);
                JobDetailDescriptionCell priceViewHolder = new JobDetailDescriptionCell(priceView);
                priceViewHolder.descriptionImage.setImageResource(R.mipmap.icon_budget);
                priceViewHolder.description.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
                priceViewHolder.description.setText("$50-$100");

                if (isService) priceViewHolder.title.setVisibility(View.VISIBLE);

                return priceViewHolder;
            case 3:
                View descriptionView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_description, parent, false);
                JobDetailDescriptionCell descriptionViewHolder = new JobDetailDescriptionCell(descriptionView);
                descriptionViewHolder.descriptionImage.setImageResource(R.mipmap.icon_info);
                descriptionViewHolder.description.setText("We are experts at gardening & landscaping. Please state in your quotation:size of your garden, " +
                        "what tasks you need done, and any special requirements.");
                return descriptionViewHolder;
            case 4:
                View addressView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_location, parent, false);
                JobDetailLocationCell jobDetailLocationCell = new JobDetailLocationCell(addressView);
                jobDetailLocationCell.description.setText("2 Jurong West Avenue 5 64386");
                jobDetailLocationCell.location.setOnClickListener(this);
                jobDetailLocationCell.location.setVisibility(View.INVISIBLE);

                if (isService) {
                    jobDetailLocationCell.description.setText("Eunos Swim Club");
                    jobDetailLocationCell.description.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
                    jobDetailLocationCell.address.setVisibility(View.VISIBLE);
                    jobDetailLocationCell.location.setVisibility(View.VISIBLE);
                }

                return jobDetailLocationCell;
            case 5:
                View statusView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_description, parent, false);
                JobDetailDescriptionCell statusViewHolder = new JobDetailDescriptionCell(statusView);
                statusViewHolder.descriptionImage.setImageResource(R.mipmap.icon_reschedule);

                statusViewHolder.description.setText("");

                String boldText = "Rescheduling: ";
                String normalText = "at least 1 day before.";

                Typeface muliBold = Typeface.create("mulibold", Typeface.BOLD);
                SpannableString spannableString = new SpannableString(boldText);
                spannableString.setSpan(new CustomTypefaceSpan("", muliBold), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                statusViewHolder.description.append(spannableString);
                statusViewHolder.description.append(normalText);
                //if (!isService) statusViewHolder.itemView.setVisibility(View.GONE);
                return statusViewHolder;
            case 6:
                View timeSlotsView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_service_detail_time_slots, parent, false);
                ServiceDetailTimeSlotsCell timeSlotsAvailableViewHolder = new ServiceDetailTimeSlotsCell(timeSlotsView);
                timeSlotsAvailableViewHolder.btnViewTimeSlots.setOnClickListener(this);
                return timeSlotsAvailableViewHolder;
            case 7:
                View reviewView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_service_detail_total_review, parent, false);
                ServiceDetailTotalReviewCell reviewViewHolder = new ServiceDetailTotalReviewCell(reviewView);
                reviewViewHolder.ratingBar.setRating((float)4.6);
                reviewViewHolder.btnReviews.setOnClickListener(this);
                return reviewViewHolder;
            case 8:
                View posterInfoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_user_name_rating, parent, false);
                JobDetailUserNameRatingCell posterInfoViewHolder = new JobDetailUserNameRatingCell(posterInfoView);
                posterInfoViewHolder.ratingBar.setRating((float)4.8);
                if (isService) {
                    posterInfoViewHolder.btnReviews.setVisibility(View.VISIBLE);
                    posterInfoViewHolder.btnReviews.setOnClickListener(this);
                }
                return posterInfoViewHolder;
            case 9:
                View tagListView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_service_detail_tag_list, parent, false);
                ServiceDetailTagListCell tagListViewHolder = new ServiceDetailTagListCell(tagListView);
                return tagListViewHolder;
            case 10:
                View bookedInfoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_service_reference_no, parent, false);
                ServiceDetailReferenceNoCell bookedInfoViewHolder = new ServiceDetailReferenceNoCell(bookedInfoView);
                return bookedInfoViewHolder;
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_location) {
            mContext.startActivity(new Intent(mContext, AppMapViewActivity.class));
        } else if (view.getId() == R.id.btn_time_slots) {
            mContext.startActivity(new Intent(mContext, ServiceTimeSlotsActivity.class));
        } else if (view.getId() == R.id.btn_see_all_reviews) {
            mContext.startActivity(new Intent(mContext, ServiceReviewsActivity.class));
        } else if (view.getId() == R.id.btn_view_all_services) {
            mContext.startActivity(new Intent(mContext, ActiveServiceActivity.class));
        }
    }
}

