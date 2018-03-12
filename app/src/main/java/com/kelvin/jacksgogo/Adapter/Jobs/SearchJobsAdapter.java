package com.kelvin.jacksgogo.Adapter.Jobs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.jacksgogo.Activities.Jobs.JobDetailActivity;
import com.kelvin.jacksgogo.Activities.Search.ServiceDetailActivity;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobListDetailCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services.SearchCategoryCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services.SearchHomeHeaderView;
import com.kelvin.jacksgogo.CustomView.Views.SectionTitleView;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppBaseModel;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;

import java.util.ArrayList;

import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.JOBS;

/**
 * Created by PUMA on 12/19/2017.
 */

public class SearchJobsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private ArrayList<JGGCategoryModel> mCategories;

    public SearchJobsAdapter(Context context, ArrayList<JGGCategoryModel> data) {
        this.mContext = context;
        mCategories = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_search_home_header, parent, false);
            SearchHomeHeaderView categoryListView = new SearchHomeHeaderView(view, JGGAppBaseModel.AppointmentType.JOBS, mContext);
            categoryListView.setOnClickListener(this);
            return categoryListView;
        } else if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_section_title, parent, false);
            SectionTitleView sectionView = new SectionTitleView(view);
            sectionView.txtTitle.setText("All Categories");
            sectionView.txtTitle.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
            return sectionView;
        } else if (viewType == 2) {
            View listView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_search_category_list, parent, false);
            SearchCategoryCell categoryListView = new SearchCategoryCell(listView, mContext, JGGAppBaseModel.AppointmentType.JOBS, mCategories);
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
            JobListDetailCell cell = new JobListDetailCell(view);
            cell.btnBackGround.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(view);
                }
            });

            return cell;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 11;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_view_all || view.getId() == R.id.btn_post_new) {
            listener.onItemClick(view);
        } if (view.getId() == R.id.btn_background) {
            listener.onItemClick(view);
        }
    }

    public void notifyDataChanged(ArrayList<JGGCategoryModel> data) {
        mCategories = data;
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View view);
    }

    public void setOnItemClickLietener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
