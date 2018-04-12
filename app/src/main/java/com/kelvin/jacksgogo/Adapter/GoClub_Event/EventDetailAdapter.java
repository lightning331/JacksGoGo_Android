package com.kelvin.jacksgogo.Adapter.GoClub_Event;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.jacksgogo.Activities.Appointment.AppMapViewActivity;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Appointment.AppInviteProviderCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.GoClub_Events.UpdatesEventView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobDetailAverageQuoteCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobDetailDescriptionCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobDetailImageCarouselCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobDetailLocationCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services.ServiceDetailCategoryCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services.ServiceDetailReferenceNoCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services.ServiceDetailTagListCell;
import com.kelvin.jacksgogo.R;

public class EventDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context mContext;

    public EventDetailAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            // Evemt Photo Cell
            View imgPageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_image_carousel, parent, false);
            JobDetailImageCarouselCell pageViewHolder = new JobDetailImageCarouselCell(imgPageView, mContext);

            return pageViewHolder;
        } else if (viewType == 1) {
            // Category & Event Title view
            View postCategoryView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_service_detail_header, parent, false);
            ServiceDetailCategoryCell categoryViewHolder = new ServiceDetailCategoryCell(postCategoryView);

            return categoryViewHolder;
        } else if (viewType == 2) {
            // Event Time Cell
            View addressView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_location, parent, false);
            JobDetailLocationCell jobTimeViewHolder = new JobDetailLocationCell(addressView);

            jobTimeViewHolder.lblDescription.setText("One-time Event");
            jobTimeViewHolder.lblDescription.setVisibility(View.VISIBLE);
            jobTimeViewHolder.lblDescription.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
            jobTimeViewHolder.location.setVisibility(View.GONE);
            jobTimeViewHolder.imgLocation.setImageResource(R.mipmap.icon_time);
            jobTimeViewHolder.address.setVisibility(View.VISIBLE);
            jobTimeViewHolder.address.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
            jobTimeViewHolder.address.setText("16 Jul, 2018 10:00 AM - 12:30 PM");
            return jobTimeViewHolder;
        } else if (viewType == 3) {
            // Description view
            View descriptionView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_description, parent, false);
            JobDetailDescriptionCell descriptionViewHolder = new JobDetailDescriptionCell(descriptionView);
            descriptionViewHolder.descriptionImage.setImageResource(R.mipmap.icon_info);
            descriptionViewHolder.description.setText("Seeking volunteers to clean up our neighbourhood. Lunch provided.");
            return descriptionViewHolder;
        } else if (viewType == 4) {
            // Address view
            View addressView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_location, parent, false);
            JobDetailLocationCell addressViewHolder = new JobDetailLocationCell(addressView);
            String street;
            addressViewHolder.lblDescription.setText("Jurong Community Center");
            addressViewHolder.lblDescription.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
            addressViewHolder.address.setVisibility(View.VISIBLE);
            addressViewHolder.address.setText("10 Jurong Road 8 #05-33 408600");
            addressViewHolder.location.setVisibility(View.VISIBLE);
            addressViewHolder.imgLocationRight.setImageResource(R.mipmap.button_location_purple);
            addressViewHolder.location.setOnClickListener(this);

            return addressViewHolder;
        } else if (viewType == 5) {
            // Member count view
            View membersView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_description, parent, false);
            JobDetailDescriptionCell membersViewHolder = new JobDetailDescriptionCell(membersView);
            membersViewHolder.descriptionImage.setImageResource(R.mipmap.icon_group);
            membersViewHolder.description.setText("1032");
            return membersViewHolder;
        } else if (viewType == 6) {
            // Event Poster view
            View posterView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_app_invite_provider, parent, false);
            AppInviteProviderCell posterViewHolder = new AppInviteProviderCell(mContext, posterView);
            posterViewHolder.lblUserType.setVisibility(View.VISIBLE);
            posterViewHolder.btnInvite.setVisibility(View.GONE);
            posterViewHolder.ratingBar.setVisibility(View.GONE);
            return posterViewHolder;
        } else if (viewType == 7) {
            // Updates event view
            View eventView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_updates_event_list, parent, false);
            UpdatesEventView eventViewHolder = new UpdatesEventView(eventView, mContext);
            return eventViewHolder;
        } else if (viewType == 8) {
            // Tag list view
            View tagListView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_service_detail_tag_list, parent, false);
            ServiceDetailTagListCell tagListViewHolder = new ServiceDetailTagListCell(tagListView);
            tagListViewHolder.setTagList("football, discussion, Ole");
            return tagListViewHolder;
        } else if (viewType == 9) {
            // Active Group view
            View activeGroupView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_average_quote, parent, false);
            JobDetailAverageQuoteCell activeGroup = new JobDetailAverageQuoteCell(activeGroupView);
            activeGroup.lblResponseCount.setText("Active Event");
            activeGroup.budgetLayout.setVisibility(View.GONE);
            return activeGroup;
        } else {
            // GoClub reference no view
            View bookedInfoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_service_reference_no, parent, false);
            ServiceDetailReferenceNoCell referenceNo = new ServiceDetailReferenceNoCell(bookedInfoView);
            referenceNo.lblTitle.setText("Event reference no: ");
            referenceNo.lblNumber.setText("C39852");
            referenceNo.lblPostedTime.setText("15 Apr, 2018");
            return referenceNo;
        }
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_location) {
            mContext.startActivity(new Intent(mContext, AppMapViewActivity.class));
        }
    }
}
