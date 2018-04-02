package com.kelvin.jacksgogo.Adapter.Jobs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobDetailDescriptionCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobDetailReferenceNoCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.UserNameRatingCell;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel;

import static com.kelvin.jacksgogo.Utils.JGGAppManager.selectedProposal;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getDayMonthYear;
import static com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel.getDaysString;

/**
 * Created by PUMA on 12/14/2017.
 */

public class BidDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private JGGProposalModel mProposal;

    public BidDetailAdapter(Context context, JGGProposalModel proposal) {
        this.mContext = context;
        mProposal = proposal;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View biderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_user_name_rating, parent, false);
            UserNameRatingCell biderCell = new UserNameRatingCell(mContext, biderView);
            biderCell.setData(mProposal.getUserProfile());
            return biderCell;
        } else if (viewType == 1) {
            View descriptionView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_description, parent, false);
            JobDetailDescriptionCell descriptionCell = new JobDetailDescriptionCell(descriptionView);
            descriptionCell.descriptionImage.setImageResource(R.mipmap.icon_info);
            descriptionCell.description.setText(mProposal.getDescription());
            return descriptionCell;
        } else if (viewType == 2) {
            View descriptionView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_description, parent, false);
            JobDetailDescriptionCell budgetCell = new JobDetailDescriptionCell(descriptionView);
            budgetCell.titleLayout.setVisibility(View.VISIBLE);
            budgetCell.titleLayout.setOrientation(LinearLayout.VERTICAL);
            budgetCell.setTitle("$ " + String.valueOf(mProposal.getBudget()), true);
            budgetCell.description.setText("- Our own supplies $20.");
            return budgetCell;
//        } else if (viewType == 3) {
//            View descriptionView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_description, parent, false);
//            JobDetailDescriptionCell descriptionCell = new JobDetailDescriptionCell(descriptionView);
//            descriptionCell.titleLayout.setVisibility(View.VISIBLE);
//            descriptionCell.titleLayout.setOrientation(LinearLayout.VERTICAL);
//            descriptionCell.setTitle("$800.00 for 10", true);
//            descriptionCell.setDescription("- Min. 3 days prior booking required. Booking subject to availability." +
//                    "- Must be same address.");
//            return descriptionCell;
        } else if (viewType == 3) {
            View descriptionView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_description, parent, false);
            JobDetailDescriptionCell rescheduleCell = new JobDetailDescriptionCell(descriptionView);
            rescheduleCell.descriptionImage.setImageResource(R.mipmap.icon_reschedule);
            rescheduleCell.titleLayout.setVisibility(View.VISIBLE);
            rescheduleCell.setTitle("Rescheduling:", true);
            if (selectedProposal.isRescheduleAllowed())
                rescheduleCell.description.setText(getDaysString(Long.valueOf(selectedProposal.getRescheduleTime())));
            else
                rescheduleCell.description.setText("No rescheduling allowed.");
            return rescheduleCell;
        } else if (viewType == 4) {
            View descriptionView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_description, parent, false);
            JobDetailDescriptionCell cancellationCell = new JobDetailDescriptionCell(descriptionView);
            cancellationCell.descriptionImage.setImageResource(R.mipmap.icon_cancellation);
            cancellationCell.titleLayout.setVisibility(View.VISIBLE);
            cancellationCell.setTitle("Cancellation:", true);
            if (selectedProposal.isCancellationAllowed())
                cancellationCell.description.setText(getDaysString(Long.valueOf(selectedProposal.getCancellationTime())));
            else
                cancellationCell.description.setText("No cancellation allowed.");
            return cancellationCell;
        } else if (viewType == 5) {
            View referenceView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_reference_no, parent, false);
            JobDetailReferenceNoCell cancellationCell = new JobDetailReferenceNoCell(referenceView);
            cancellationCell.lblReferenceNo.setText("Proposal reference no: " + mProposal.getID());
            cancellationCell.lblPostedDate.setText("Posted on " + getDayMonthYear(mProposal.getSubmitOn()));
            return cancellationCell;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 6;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
