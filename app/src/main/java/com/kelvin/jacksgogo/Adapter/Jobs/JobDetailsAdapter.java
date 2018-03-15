package com.kelvin.jacksgogo.Adapter.Jobs;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Appointment.AppInviteProviderCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobDetailAverageQuoteCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobDetailDescriptionCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobDetailImageCarouselCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobDetailLocationCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services.ServiceDetailCategoryCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services.ServiceDetailReferenceNoCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services.ServiceDetailTagListCell;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGJobModel;
import com.squareup.picasso.Picasso;

import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.selectedAppointment;
import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.selectedCategory;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.appointmentMonthDate;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.convertJobTimeString;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getDayMonthYear;

/**
 * Created by PUMA on 3/9/2018.
 */

public class JobDetailsAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private JGGJobModel mJob;
    private JGGCategoryModel mCategory;

    public JobDetailsAdapter (Context context) {
        this.mContext = context;
        mCategory = selectedCategory;
        mJob = selectedAppointment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {    // Job Photo Cell
            View imgPageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_image_carousel, parent, false);
            JobDetailImageCarouselCell pageViewHolder = new JobDetailImageCarouselCell(imgPageView);

            int[] array = {R.drawable.carousel03, R.drawable.carousel01,
                    R.drawable.carousel04, R.drawable.carousel05, R.drawable.carousel06, R.drawable.carousel01, R.drawable.carousel03, R.drawable.carousel02};

            pageViewHolder.imageArray = array;
            pageViewHolder.carouselView.setPageCount(array.length);
            pageViewHolder.carouselView.setImageListener(pageViewHolder.imageListener);
            return pageViewHolder;
        } else if (viewType == 1) {    // Category Cell
            View postCategoryView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_service_detail_header, parent, false);
            ServiceDetailCategoryCell categoryViewHolder = new ServiceDetailCategoryCell(postCategoryView);

            Picasso.with(mContext)
                    .load(mCategory.getImage())
                    .placeholder(null)
                    .into(categoryViewHolder.imgCategory);
            categoryViewHolder.lblCategory.setText(mCategory.getName());
            categoryViewHolder.title.setText(mJob.getTitle());

            return categoryViewHolder;
        } else if (viewType == 2) {    // Job Time Cell
            View addressView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_location, parent, false);
            JobDetailLocationCell jobTimeViewHolder = new JobDetailLocationCell(addressView);

            if (mJob.getBudget() != null) {
                jobTimeViewHolder.description.setText("Package Job");
                jobTimeViewHolder.description.setVisibility(View.VISIBLE);
                jobTimeViewHolder.description.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
            }
            jobTimeViewHolder.location.setVisibility(View.INVISIBLE);
            jobTimeViewHolder.imgLocation.setImageResource(R.mipmap.icon_time);
            jobTimeViewHolder.address.setVisibility(View.VISIBLE);
            jobTimeViewHolder.address.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
            jobTimeViewHolder.address.setText(convertJobTimeString(mJob));
            return jobTimeViewHolder;
        } else if (viewType == 3) {    // Job Description Cell
            View descriptionView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_description, parent, false);
            JobDetailDescriptionCell descriptionViewHolder = new JobDetailDescriptionCell(descriptionView);
            descriptionViewHolder.descriptionImage.setImageResource(R.mipmap.icon_info);
            descriptionViewHolder.description.setText(mJob.getDescription());
            return descriptionViewHolder;
        } else if (viewType == 4) {    // Job Address Cell
            View addressView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_location, parent, false);
            JobDetailLocationCell addressViewHolder = new JobDetailLocationCell(addressView);
            addressViewHolder.description.setText(mJob.getAddress().getAddress() + ", 0.4 km away");
            addressViewHolder.location.setVisibility(View.INVISIBLE);
            return addressViewHolder;
        } else if (viewType == 5) {    // Job Budget Cell
            View priceView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_location, parent, false);
            JobDetailLocationCell jobTimeViewHolder = new JobDetailLocationCell(priceView);

            jobTimeViewHolder.description.setVisibility(View.VISIBLE);
            jobTimeViewHolder.address.setVisibility(View.VISIBLE);
            jobTimeViewHolder.location.setVisibility(View.INVISIBLE);
            jobTimeViewHolder.lblQuotePrice.setVisibility(View.VISIBLE);

            jobTimeViewHolder.imgLocation.setImageResource(R.mipmap.icon_budget);
            String budget = "";
            if (selectedAppointment.getBudget() == null && selectedAppointment.getBudgetFrom() == null)
                budget =  "Budget No limit";
            else if (selectedAppointment.getBudget() != null) {
                jobTimeViewHolder.description.setText("Fixed");
                budget = "Budget " + selectedAppointment.getBudget().toString() + "/month";
            } else if (selectedAppointment.getBudgetFrom() != null && selectedAppointment.getBudgetTo() != null) {
                jobTimeViewHolder.description.setText("Package");
                budget = ("Budget $ " + selectedAppointment.getBudgetFrom().toString()
                        + " "
                        + "$ " + selectedAppointment.getBudgetTo().toString()
                        + "/month");
            }
            jobTimeViewHolder.address.setText(budget);
            return jobTimeViewHolder;
        } else if (viewType == 6) {    // Job ReportType Cell
            View requestView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_description, parent, false);
            JobDetailDescriptionCell requestViewHolder = new JobDetailDescriptionCell(requestView);
            requestViewHolder.descriptionImage.setImageResource(R.mipmap.icon_completion);
            requestViewHolder.setTitle("Requests:", true);
            requestViewHolder.setDescription(Global.reportTypeName(mJob.getReportType()));
            return requestViewHolder;
        } else if (viewType == 7) {    // Job Poster Cell
            View posterView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_app_invite_provider, parent, false);
            AppInviteProviderCell posterViewHolder = new AppInviteProviderCell(mContext, posterView);
            posterViewHolder.btnInvite.setVisibility(View.GONE);
            posterViewHolder.setData(mJob.getUserProfile());
            return posterViewHolder;
        } else if (viewType == 8) {    // Taglist Cell
            View tagListView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_service_detail_tag_list, parent, false);
            ServiceDetailTagListCell tagListViewHolder = new ServiceDetailTagListCell(tagListView);
            tagListViewHolder.setTagList(mJob.getTags());
            return tagListViewHolder;
        } else if (viewType == 9) {    // Proposal status Cell
            View average = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_average_quote, parent, false);
            JobDetailAverageQuoteCell averageViewHolder = new JobDetailAverageQuoteCell(average);
            return averageViewHolder;
        } else if (viewType == 10) {    // Job no cell
            View bookedInfoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_service_reference_no, parent, false);
            ServiceDetailReferenceNoCell referenceNo = new ServiceDetailReferenceNoCell(bookedInfoView);
            referenceNo.lblTitle.setText("Job reference no: ");
            referenceNo.lblNumber.setText(mJob.getID());
            referenceNo.lblPostedTime.setText(getDayMonthYear(appointmentMonthDate(mJob.getPostOn())));
            return referenceNo;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return 11;
    }
}
