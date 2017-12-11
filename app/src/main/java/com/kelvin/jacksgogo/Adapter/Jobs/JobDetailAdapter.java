package com.kelvin.jacksgogo.Adapter.Jobs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.jacksgogo.Activities.Appointment.AppMapViewActivity;
import com.kelvin.jacksgogo.Activities.Search.ServiceDetailActivity;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Appointment.AppFilterOptionCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail.JobDetailDescriptionCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail.JobDetailImageCarouselCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail.JobDetailInviteButtonCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail.JobDetailLocationCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail.JobDetailReferenceNoCell;
import com.kelvin.jacksgogo.Fragments.Jobs.JobDetailFragment;
import com.kelvin.jacksgogo.R;


/**
 * Created by PUMA on 11/3/2017.
 */

public class JobDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    public JobDetailAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case 0:
                View imgPageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_image_carousel, parent, false);
                JobDetailImageCarouselCell jobDetailImageCarouselCell = new JobDetailImageCarouselCell(imgPageView);

                return jobDetailImageCarouselCell;
            case 1:
                View priceView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_description, parent, false);
                JobDetailDescriptionCell priceViewHolder = new JobDetailDescriptionCell(priceView);
                return priceViewHolder;
            case 2:
                View descriptionView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_description, parent, false);
                JobDetailDescriptionCell jobDetailDescriptionCell = new JobDetailDescriptionCell(descriptionView);
                return jobDetailDescriptionCell;
            case 3:
                View addressView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_location, parent, false);
                JobDetailLocationCell jobDetailLocationCell = new JobDetailLocationCell(addressView);
                return jobDetailLocationCell;
            case 4:
                View schedulView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_description, parent, false);
                JobDetailDescriptionCell schedulViewHolder = new JobDetailDescriptionCell(schedulView);
                return schedulViewHolder;
            case 5:
                View cancellView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_description, parent, false);
                JobDetailDescriptionCell cancellViewHolder = new JobDetailDescriptionCell(cancellView);
                return cancellViewHolder;
            case 6:
                View requestView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_description, parent, false);
                JobDetailDescriptionCell requestViewHolder = new JobDetailDescriptionCell(requestView);
                return requestViewHolder;
            case 7:
                View referenceView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_reference_no, parent, false);
                JobDetailReferenceNoCell jobDetailReferenceNoCell = new JobDetailReferenceNoCell(referenceView);
                return jobDetailReferenceNoCell;
            case 8:
                View originalView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_invite_button, parent, false);
                JobDetailInviteButtonCell originalViewHolder = new JobDetailInviteButtonCell(originalView);
                return originalViewHolder;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (position) {
            case 0:
                JobDetailImageCarouselCell cell = (JobDetailImageCarouselCell)holder;
                int[] array = {R.drawable.carousel01, R.drawable.carousel02, R.drawable.carousel03, R.drawable.carousel01,
                        R.drawable.carousel02, R.drawable.carousel03, R.drawable.carousel02, R.drawable.carousel01};

                cell.imageArray = array;
                cell.carouselView.setPageCount(array.length);
                cell.carouselView.setImageListener(cell.imageListener);
                break;
            case 1:
                JobDetailDescriptionCell priceViewHolder = (JobDetailDescriptionCell)holder;
                priceViewHolder.descriptionImage.setImageResource(R.mipmap.icon_budget);
                priceViewHolder.description.setText("$50-$100");
                priceViewHolder.description.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
                break;
            case 2:
                JobDetailDescriptionCell jobDetailDescriptionCell = (JobDetailDescriptionCell)holder;
                jobDetailDescriptionCell.descriptionImage.setImageResource(R.mipmap.icon_info);
                jobDetailDescriptionCell.description.setText("We are experts at gardening & landscaping. Please state in your quotation:size of your garden, " +
                        "what tasks you need done, and any special requirements.");
                break;
            case 3:
                JobDetailLocationCell jobDetailLocationCell = (JobDetailLocationCell)holder;
                jobDetailLocationCell.description.setText("2 Jurong West Avenue 5 64386");
                jobDetailLocationCell.location.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mContext.startActivity(new Intent(mContext, AppMapViewActivity.class));
                    }
                });
                break;
            case 4:
                JobDetailDescriptionCell schedulViewHolder = (JobDetailDescriptionCell)holder;
                schedulViewHolder.descriptionImage.setImageResource(R.mipmap.icon_reschedule);
                schedulViewHolder.setTitle("Rescheduling:", true);
                schedulViewHolder.setDescription("at least 6 hours before.");
                break;
            case 5:
                JobDetailDescriptionCell cancellViewHolder = (JobDetailDescriptionCell)holder;
                cancellViewHolder.descriptionImage.setImageResource(R.mipmap.icon_cancellation);
                cancellViewHolder.setTitle("Cancelling:", true);
                cancellViewHolder.setDescription("at least 1 day before.");
                break;
            case 6:
                JobDetailDescriptionCell requestViewHolder = (JobDetailDescriptionCell)holder;
                requestViewHolder.descriptionImage.setImageResource(R.mipmap.icon_completion);
                requestViewHolder.setTitle("Rescheduling:", true);
                requestViewHolder.setDescription("Before & After photos");
                break;
            case 7:
                JobDetailReferenceNoCell jobDetailReferenceNoCell = (JobDetailReferenceNoCell)holder;
                break;
            case 8:
                JobDetailInviteButtonCell originalViewHolder = (JobDetailInviteButtonCell)holder;
                originalViewHolder.inviteButton.setText("View Original Job Post");
                originalViewHolder.inviteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, ServiceDetailActivity.class);
                        intent.putExtra("is_service", false);
                        mContext.startActivity(intent);
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 9;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}