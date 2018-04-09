package com.kelvin.jacksgogo.Adapter.Jobs;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobListDetailCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services.CategoryRecyclerView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services.SearchHomeHeaderView;
import com.kelvin.jacksgogo.CustomView.Views.SectionTitleView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;

import java.util.ArrayList;

import static com.kelvin.jacksgogo.Utils.JGGAppManager.selectedAppointment;

/**
 * Created by PUMA on 12/19/2017.
 */

public class SearchJobsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private ArrayList<JGGCategoryModel> mCategories;
    private ArrayList<JGGAppointmentModel> mJobs;

    public SearchJobsAdapter(Context context, ArrayList<JGGCategoryModel> data) {
        this.mContext = context;
        mCategories = data;
    }

    public void notifyDataChanged(ArrayList<JGGCategoryModel> data, ArrayList<JGGAppointmentModel> jobs) {
        mCategories = data;
        mJobs = jobs;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_search_home_header, parent, false);
            SearchHomeHeaderView headerView = new SearchHomeHeaderView(view, AppointmentType.JOBS, mContext);
            headerView.totalServiceCount.setText(String.valueOf(mJobs.size()));
            headerView.setOnClickListener(this);
            return headerView;
        } else if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_section_title, parent, false);
            SectionTitleView sectionView = new SectionTitleView(view);
            sectionView.txtTitle.setText("All Categories");
            sectionView.txtTitle.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
            return sectionView;
        } else if (viewType == 2) {
            View listView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_category_list, parent, false);
            CategoryRecyclerView categoryListView = new CategoryRecyclerView(listView, mContext, AppointmentType.JOBS, mCategories);
            //categoryListView.setOnClickListener(this);
            return categoryListView;
        } else if (viewType == 3) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_section_title, parent, false);
            SectionTitleView sectionView = new SectionTitleView(view);
            sectionView.txtTitle.setText("Recommended For You");
            sectionView.txtTitle.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
            return sectionView;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_list_detail, parent, false);
            JobListDetailCell cell = new JobListDetailCell(view, mContext);
            return cell;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (position > 3) {
            JobListDetailCell cell = (JobListDetailCell) holder;
            JGGAppointmentModel job = mJobs.get(position - 4);

            cell.setJob(job);
            cell.btnBackGround.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedAppointment = null;
                    selectedAppointment = mJobs.get(position - 4);;
                    listener.onItemClick(view);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mJobs.size() + 4;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_view_all || view.getId() == R.id.btn_post_new) {
            listener.onItemClick(view);
        }
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View view);
    }

    public void setOnItemClickLietener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
