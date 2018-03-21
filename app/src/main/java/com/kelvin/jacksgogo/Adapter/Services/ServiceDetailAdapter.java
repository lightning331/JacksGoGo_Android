package com.kelvin.jacksgogo.Adapter.Services;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.jacksgogo.Activities.JGGMapViewActivity;
import com.kelvin.jacksgogo.Activities.Search.ActiveServiceActivity;
import com.kelvin.jacksgogo.Activities.Search.ServiceReviewsActivity;
import com.kelvin.jacksgogo.Activities.Search.ServiceTimeSlotsActivity;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobDetailDescriptionCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobDetailImageCarouselCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobDetailLocationCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.UserNameRatingCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services.ServiceDetailCategoryCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services.ServiceDetailReferenceNoCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services.ServiceDetailTagListCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services.ServiceDetailTimeSlotsCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services.ServiceDetailTotalReviewCell;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.squareup.picasso.Picasso;

import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.selectedAppointment;
import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.EDIT_STATUS;
import static com.kelvin.jacksgogo.Utils.Global.POST;
import static com.kelvin.jacksgogo.Utils.Global.SERVICES;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.appointmentMonthDate;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.convertBudgetOnly;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getDayMonthYear;
import static com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel.getDaysString;

/**
 * Created by PUMA on 11/7/2017.
 */

public class ServiceDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context mContext;

    private JGGAppointmentModel mService;

    /*
     *  Service BudgetType
     *  If Fixed Budget, Can buy Service
     *  If Package Budget, Can Request to Service
     */
    private boolean isFixedBudget;
    private int ITEM_COUNT = 11;

    public ServiceDetailAdapter(Context context) {
        this.mContext = context;
        mService = selectedAppointment;
        if (mService.getBudget() != null) isFixedBudget = true;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case 0:     // Service Photo Cell
                View imgPageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_image_carousel, parent, false);
                JobDetailImageCarouselCell pageViewHolder = new JobDetailImageCarouselCell(imgPageView);

                int[] array = {R.drawable.carousel03, R.drawable.carousel01,
                        R.drawable.carousel04, R.drawable.carousel05, R.drawable.carousel06, R.drawable.carousel01, R.drawable.carousel03, R.drawable.carousel02};

                pageViewHolder.imageArray = array;
                pageViewHolder.carouselView.setPageCount(array.length);
                pageViewHolder.carouselView.setImageListener(pageViewHolder.imageListener);
                return pageViewHolder;
            case 1:     // Service Category Cell
                View postCategoryView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_service_detail_header, parent, false);
                ServiceDetailCategoryCell categoryViewHolder = new ServiceDetailCategoryCell(postCategoryView);
                Picasso.with(mContext)
                        .load(mService.getCategory().getImage())
                        .placeholder(null)
                        .into(categoryViewHolder.imgCategory);
                categoryViewHolder.lblCategory.setText(mService.getCategory().getName());
                categoryViewHolder.title.setText(mService.getTitle());
                return categoryViewHolder;
            case 2:     // Budget Cell
                View priceView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_description, parent, false);
                JobDetailDescriptionCell priceViewHolder = new JobDetailDescriptionCell(priceView);
                priceViewHolder.descriptionImage.setImageResource(R.mipmap.icon_budget);
                priceViewHolder.description.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
                priceViewHolder.description.setText(convertBudgetOnly(mService));
                if (mService.getBudget() == null) {

                } else {
                    priceViewHolder.title.setText("Package");
                    priceViewHolder.title.setVisibility(View.VISIBLE);
                }
                return priceViewHolder;
            case 3:     // Description Cell
                View descriptionView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_description, parent, false);
                JobDetailDescriptionCell descriptionViewHolder = new JobDetailDescriptionCell(descriptionView);
                descriptionViewHolder.descriptionImage.setImageResource(R.mipmap.icon_info);
                descriptionViewHolder.description.setText(mService.getDescription());
                return descriptionViewHolder;
            case 4:     // Address Cell
                View addressView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_location, parent, false);
                JobDetailLocationCell addressViewHolder = new JobDetailLocationCell(addressView);
                String street;
                if (mService.getAddress().getStreet() == null)
                    street = mService.getAddress().getAddress();
                else
                    street = mService.getAddress().getStreet();
                addressViewHolder.lblDescription.setText(street + ", 0.4 km away");
                addressViewHolder.location.setVisibility(View.GONE);

                if (isFixedBudget) {
                    addressViewHolder.lblDescription.setText("Eunos Swim Club");
                    addressViewHolder.lblDescription.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
                    addressViewHolder.address.setVisibility(View.VISIBLE);
                    addressViewHolder.address.setText(street);
                    addressViewHolder.location.setVisibility(View.VISIBLE);
                    addressViewHolder.location.setOnClickListener(this);
                }

                return addressViewHolder;
            case 5:     // Rescheduling Cell
                if (isFixedBudget) {
                    View requestView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_description, parent, false);
                    JobDetailDescriptionCell rescheduleViewHolder = new JobDetailDescriptionCell(requestView);
                    rescheduleViewHolder.descriptionImage.setImageResource(R.mipmap.icon_reschedule);
                    if (mService.getProposal() != null)
                        if (mService.getProposal().isRescheduleAllowed())
                            rescheduleViewHolder.description.setText(getDaysString(Long.valueOf(mService.getProposal().getRescheduleDate())));
                        else
                            rescheduleViewHolder.description.setText("No rescheduling allowed.");
                    rescheduleViewHolder.setTitle("Rescheduling:", true);

                    return rescheduleViewHolder;
                } else {     // Time Slots Cell
                    View timeSlotsView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_service_detail_time_slots, parent, false);
                    ServiceDetailTimeSlotsCell timeSlotsAvailableViewHolder = new ServiceDetailTimeSlotsCell(timeSlotsView);
                    timeSlotsAvailableViewHolder.btnViewTimeSlots.setOnClickListener(this);
                    return timeSlotsAvailableViewHolder;
                }
            case 6:     // Time Slots Cell
                if (isFixedBudget) {
                    View timeSlotsView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_service_detail_time_slots, parent, false);
                    ServiceDetailTimeSlotsCell timeSlotsAvailableViewHolder = new ServiceDetailTimeSlotsCell(timeSlotsView);
                    timeSlotsAvailableViewHolder.btnViewTimeSlots.setOnClickListener(this);
                    return timeSlotsAvailableViewHolder;
                } else {     // Review Cell
                    View reviewView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_service_detail_total_review, parent, false);
                    ServiceDetailTotalReviewCell reviewViewHolder = new ServiceDetailTotalReviewCell(reviewView);
                    reviewViewHolder.ratingBar.setRating(mService.getUserProfile().getUser().getRate().floatValue());
                    reviewViewHolder.btnReviews.setOnClickListener(this);
                    return reviewViewHolder;
                }
            case 7:     // Review Cell
                if (isFixedBudget) {
                    View reviewView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_service_detail_total_review, parent, false);
                    ServiceDetailTotalReviewCell reviewViewHolder = new ServiceDetailTotalReviewCell(reviewView);
                    reviewViewHolder.ratingBar.setRating(mService.getUserProfile().getUser().getRate().floatValue());
                    reviewViewHolder.btnReviews.setOnClickListener(this);
                    return reviewViewHolder;
                } else {     // Service Provider Cell
                    View posterInfoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_user_name_rating, parent, false);
                    UserNameRatingCell posterInfoViewHolder = new UserNameRatingCell(mContext, posterInfoView);
                    posterInfoViewHolder.setData(mService.getUserProfile());
                    return posterInfoViewHolder;
                }
            case 8:     // Service Provider Cell
                if (isFixedBudget) {
                    View posterInfoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_user_name_rating, parent, false);
                    UserNameRatingCell posterInfoViewHolder = new UserNameRatingCell(mContext, posterInfoView);
                    posterInfoViewHolder.setData(mService.getUserProfile());
                    posterInfoViewHolder.btnReviews.setVisibility(View.VISIBLE);
                    posterInfoViewHolder.btnReviews.setOnClickListener(this);
                    return posterInfoViewHolder;
                } else {
                    if (mService.getTags().length() > 0) {
                        View tagListView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_service_detail_tag_list, parent, false);
                        ServiceDetailTagListCell tagListViewHolder = new ServiceDetailTagListCell(tagListView);
                        tagListViewHolder.setTagList(mService.getTags());
                        return tagListViewHolder;
                    } else {     // Service Reference Cell
                        View bookedInfoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_service_reference_no, parent, false);
                        ServiceDetailReferenceNoCell reserenceNo = new ServiceDetailReferenceNoCell(bookedInfoView);
                        reserenceNo.lblNumber.setText(mService.getID());
                        reserenceNo.lblPostedTime.setText(getDayMonthYear(appointmentMonthDate(mService.getPostOn())));
                        return reserenceNo;
                    }
                }
            case 9:     // TagList Cell
                if (isFixedBudget) {
                    if (mService.getTags().length() > 0) {
                        View tagListView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_service_detail_tag_list, parent, false);
                        ServiceDetailTagListCell tagListViewHolder = new ServiceDetailTagListCell(tagListView);
                        tagListViewHolder.setTagList(mService.getTags());
                        return tagListViewHolder;
                    } else {     // Service Reference Cell
                        View bookedInfoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_service_reference_no, parent, false);
                        ServiceDetailReferenceNoCell reserenceNo = new ServiceDetailReferenceNoCell(bookedInfoView);
                        reserenceNo.lblNumber.setText(mService.getID());
                        reserenceNo.lblPostedTime.setText(getDayMonthYear(appointmentMonthDate(mService.getPostOn())));
                        return reserenceNo;
                    }
                } else {
                    View bookedInfoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_service_reference_no, parent, false);
                    ServiceDetailReferenceNoCell reserenceNo = new ServiceDetailReferenceNoCell(bookedInfoView);
                    reserenceNo.lblNumber.setText(mService.getID());
                    reserenceNo.lblPostedTime.setText(getDayMonthYear(appointmentMonthDate(mService.getPostOn())));
                    return reserenceNo;
                }
            case 10:     // Service Reference Cell
                if (isFixedBudget) {
                    View bookedInfoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_service_reference_no, parent, false);
                    ServiceDetailReferenceNoCell reserenceNo = new ServiceDetailReferenceNoCell(bookedInfoView);
                    reserenceNo.lblNumber.setText(mService.getID());
                    reserenceNo.lblPostedTime.setText(getDayMonthYear(appointmentMonthDate(mService.getPostOn())));
                    return reserenceNo;
                }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if (!isFixedBudget) {
            if (mService.getTags().length() == 0)
                return 9;
            return 10;
        }
        return ITEM_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_location) {
            mContext.startActivity(new Intent(mContext, JGGMapViewActivity.class));
        } else if (view.getId() == R.id.btn_time_slots) {
            mContext.startActivity(new Intent(mContext, ServiceTimeSlotsActivity.class));
        } else if (view.getId() == R.id.btn_see_all_reviews) {
            mContext.startActivity(new Intent(mContext, ServiceReviewsActivity.class));
        } else if (view.getId() == R.id.btn_view_all_services) {
            Intent mIntent = new Intent(mContext, ActiveServiceActivity.class);
            mIntent.putExtra(APPOINTMENT_TYPE, SERVICES);
            mIntent.putExtra(EDIT_STATUS, POST);
            mContext.startActivity(mIntent);
        }
    }
}

