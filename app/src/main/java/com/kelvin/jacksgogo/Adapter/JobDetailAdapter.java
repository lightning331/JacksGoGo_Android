package com.kelvin.jacksgogo.Adapter;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.jacksgogo.Activities.Appointment.AppMapViewActivity;
import com.kelvin.jacksgogo.Activities.Appointment.ServiceDetailActivity;
import com.kelvin.jacksgogo.CustomView.CustomTypefaceSpan;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Appointment.AppFilterOptionCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail.JobDetailDescriptionCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail.JobDetailExpanableCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail.JobDetailFooterCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.SectionTitleView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail.JobDetailImageCarouselCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail.JobDetailLocationCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail.JobDetailNextStepTitleCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail.JobDetailReferenceNoCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetailUserNameRatingCell;
import com.kelvin.jacksgogo.Fragments.Appointments.JobDetailFragment;
import com.kelvin.jacksgogo.R;


/**
 * Created by PUMA on 11/3/2017.
 */

public class JobDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    JobDetailFragment mContext;
    private boolean expandState = false; // true: expanded, false: not expanded

    public JobDetailAdapter(JobDetailFragment context) {
        this.mContext = context;
    }

    public void setExpandState(boolean expandState) {
        this.expandState = expandState;
    }

    public boolean isExpandState() {
        return expandState;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case 0:
                View nextStepTitleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_detail_next_step_title_cell, parent, false);
                JobDetailNextStepTitleCell jobDetailNextStepTitleCell = new JobDetailNextStepTitleCell(nextStepTitleView);
                jobDetailNextStepTitleCell.title.setText("Waiting for service provider...");
                return jobDetailNextStepTitleCell;
            case 1:
                View typeTitleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_title_view, parent, false);
                SectionTitleView sectionHeaderView = new SectionTitleView(typeTitleView);
                sectionHeaderView.txtTitle.setText("Invited service provider:");
                sectionHeaderView.txtTitle.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
                return sectionHeaderView;
            case 2:
                View sectionTitleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_detail_user_name_rating_cell, parent, false);
                JobDetailUserNameRatingCell jobDetailUserNameRatingCell;
                jobDetailUserNameRatingCell = new JobDetailUserNameRatingCell(sectionTitleView);
                jobDetailUserNameRatingCell.ratingBar.setRating((float)4.8);
                return jobDetailUserNameRatingCell;
            case 3:
                View expandableView = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_detail_expandable_cell, parent, false);
                JobDetailExpanableCell jobDetailExpanableCell = new JobDetailExpanableCell(expandableView);
                jobDetailExpanableCell.bind(this);
                return jobDetailExpanableCell;
            case 4:
                View imgPageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_detail_image_carousel_cell, parent, false);
                JobDetailImageCarouselCell jobDetailImageCarouselCell = new JobDetailImageCarouselCell(imgPageView);

                int[] array = {R.drawable.carousel01, R.drawable.carousel02, R.drawable.carousel03, R.drawable.carousel01,
                        R.drawable.carousel02, R.drawable.carousel03, R.drawable.carousel02, R.drawable.carousel01};

                jobDetailImageCarouselCell.imageArray = array;
                jobDetailImageCarouselCell.carouselView.setPageCount(array.length);
                jobDetailImageCarouselCell.carouselView.setImageListener(jobDetailImageCarouselCell.imageListener);
                return jobDetailImageCarouselCell;
            case 5:
                View priceView = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_detail_description_cell, parent, false);
                JobDetailDescriptionCell priceViewHolder = new JobDetailDescriptionCell(priceView);
                priceViewHolder.descriptionImage.setImageResource(R.mipmap.icon_budget);
                priceViewHolder.description.setText("$50-$100");
                priceViewHolder.description.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
                return priceViewHolder;
            case 6:
                View descriptionView = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_detail_description_cell, parent, false);
                JobDetailDescriptionCell jobDetailDescriptionCell = new JobDetailDescriptionCell(descriptionView);
                jobDetailDescriptionCell.descriptionImage.setImageResource(R.mipmap.icon_info);
                jobDetailDescriptionCell.description.setText("We are experts at gardening & landscaping. Please state in your quotation:size of your garden, " +
                        "what tasks you need deon, and any special requirements.");
                return jobDetailDescriptionCell;
            case 7:
                View addressView = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_detail_location_cell, parent, false);
                JobDetailLocationCell jobDetailLocationCell = new JobDetailLocationCell(addressView);
                jobDetailLocationCell.description.setText("2 Jurong West Avenue 5 64386");
                jobDetailLocationCell.location.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mContext.getActivity().startActivity(new Intent(mContext.getActivity(), AppMapViewActivity.class));
                    }
                });
                return jobDetailLocationCell;
            case 8:
                View statusView = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_detail_description_cell, parent, false);
                JobDetailDescriptionCell statusViewHolder = new JobDetailDescriptionCell(statusView);
                statusViewHolder.descriptionImage.setImageResource(R.mipmap.icon_completion);

                statusViewHolder.description.setText("");

                String boldText = "Requests: ";
                String normalText = "Before & After photos";

                Typeface muliBold = Typeface.create("mulibold", Typeface.BOLD);
                SpannableString spannableString = new SpannableString(boldText);
                spannableString.setSpan(new CustomTypefaceSpan("", muliBold), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                statusViewHolder.description.append(spannableString);
                statusViewHolder.description.append(normalText);
                return statusViewHolder;
            case 9:
                View referenceView = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_detail_reference_no_cell, parent, false);
                JobDetailReferenceNoCell jobDetailReferenceNoCell = new JobDetailReferenceNoCell(referenceView);
                return jobDetailReferenceNoCell;
            case 10:
                View originalView = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_filter_option_cell, parent, false);
                AppFilterOptionCell originalViewHolder = new AppFilterOptionCell(originalView);
                originalViewHolder.title.setText("View Original Service Post");
                originalViewHolder.btnOriginal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mContext.getActivity().startActivity(new Intent(mContext.getActivity(), ServiceDetailActivity.class));
                    }
                });
                return originalViewHolder;
            case 11:
                View footerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_detail_footer_cell, parent, false);
                JobDetailFooterCell footerViewHolder = new JobDetailFooterCell(footerView);
                footerViewHolder.title.setText("Job posted on 7 Jul, 2017 8:15PM");
                return footerViewHolder;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if (!expandState) return 5;
        return 12;
    }

    @Override
    public int getItemViewType(int position) {

        if (!expandState && position == 4) {
            return  11;
        } else {
            return position;
        }
    }
}