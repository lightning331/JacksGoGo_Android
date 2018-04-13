package com.kelvin.jacksgogo.Adapter.GoClub_Event;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.jacksgogo.Activities.GoClub_Event.GoClubMembersActivity;
import com.kelvin.jacksgogo.Activities.GoClub_Event.PastEventsActivity;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Appointment.AppInviteProviderCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.GoClub_Events.GoClubDetailEventView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobDetailAverageQuoteCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobDetailDescriptionCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobDetailImageCarouselCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services.ServiceDetailCategoryCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services.ServiceDetailReferenceNoCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services.ServiceDetailTagListCell;
import com.kelvin.jacksgogo.R;

public class GoClubDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    public GoClubDetailAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            // GoClub Photo Cell
            View imgPageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_image_carousel, parent, false);
            JobDetailImageCarouselCell pageViewHolder = new JobDetailImageCarouselCell(imgPageView, mContext);

            return pageViewHolder;
        } else if (viewType == 1) {
            // Category & GoClub Title view
            View postCategoryView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_service_detail_header, parent, false);
            ServiceDetailCategoryCell categoryViewHolder = new ServiceDetailCategoryCell(postCategoryView);

            return categoryViewHolder;
        } else if (viewType == 2) {
            // Description view
            View descriptionView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_description, parent, false);
            JobDetailDescriptionCell descriptionViewHolder = new JobDetailDescriptionCell(descriptionView);
            descriptionViewHolder.descriptionImage.setImageResource(R.mipmap.icon_info);
            descriptionViewHolder.description.setText("Football fan club and sessions in Victory Park.");
            return descriptionViewHolder;
        } else if (viewType == 3) {
            // Member count view
            View membersView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_description, parent, false);
            JobDetailDescriptionCell membersViewHolder = new JobDetailDescriptionCell(membersView);
            membersViewHolder.descriptionImage.setImageResource(R.mipmap.icon_group);
            membersViewHolder.btnViewAllMemebers.setVisibility(View.VISIBLE);
            membersViewHolder.btnViewAllMemebers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, GoClubMembersActivity.class));
                }
            });
            membersViewHolder.description.setText("1032");
            return membersViewHolder;
        } else if (viewType == 4){
            // Past Events list view
            View eventView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_go_club_detail_event, parent, false);
            GoClubDetailEventView eventViewHolder = new GoClubDetailEventView(eventView, mContext);
            eventViewHolder.btnViewPastEvents.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContext.startActivity(new Intent(mContext, PastEventsActivity.class));
                }
            });
            eventViewHolder.btnCreateEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            return eventViewHolder;
        } else if (viewType == 5) {
            // Tag list view
            View tagListView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_service_detail_tag_list, parent, false);
            ServiceDetailTagListCell tagListViewHolder = new ServiceDetailTagListCell(tagListView);
            tagListViewHolder.setTagList("football, discussion, Ole");
            return tagListViewHolder;
        } else if (viewType == 6) {
            // Active Group view
            View activeGroupView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_average_quote, parent, false);
            JobDetailAverageQuoteCell activeGroup = new JobDetailAverageQuoteCell(activeGroupView);
            activeGroup.lblResponseCount.setText("Active Group");
            activeGroup.budgetLayout.setVisibility(View.GONE);
            return activeGroup;
        } else {
            // GoClub reference no view
            View bookedInfoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_service_reference_no, parent, false);
            ServiceDetailReferenceNoCell referenceNo = new ServiceDetailReferenceNoCell(bookedInfoView);
            referenceNo.lblTitle.setText("GoClub reference no: ");
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
        return 8;
    }
}
