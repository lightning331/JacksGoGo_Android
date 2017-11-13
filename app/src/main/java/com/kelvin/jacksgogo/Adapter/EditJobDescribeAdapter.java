package com.kelvin.jacksgogo.Adapter;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail.EditJobTextViewCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail.EditJobPhotoImageCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.SectionTitleView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail.EditJobTakePhotoCell;
import com.kelvin.jacksgogo.Fragments.Appointments.EditJobFragment;
import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/10/2017.
 */

public class EditJobDescribeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    EditJobFragment mContext;
    int IMTE_COUNT = 6;

    public EditJobDescribeAdapter(EditJobFragment context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case 0:
                View jobTitleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_title_view, parent, false);
                SectionTitleView sectionTitleView = new SectionTitleView(jobTitleView);
                sectionTitleView.txtTitle.setText("Give your job a short title.");
                sectionTitleView.txtTitle.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
                return sectionTitleView;
            case 1:
                View titleBorderTextView = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_job_text_view_cell, parent, false);
                EditJobTextViewCell editJobTextViewCell = new EditJobTextViewCell(titleBorderTextView);
                return editJobTextViewCell;
            case 2:
                View descTitleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_title_view, parent, false);
                SectionTitleView descTitleViewHolder = new SectionTitleView(descTitleView);
                descTitleViewHolder.txtTitle.setText("Describe the job you need done.");
                descTitleViewHolder.txtTitle.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
                return descTitleViewHolder;
            case 3:
                View descBorderTextView = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_job_text_view_cell, parent, false);
                EditJobTextViewCell descEditJobTextViewCell = new EditJobTextViewCell(descBorderTextView);
                descEditJobTextViewCell.editText.setText("Need help with moving the lawn and weeding the garden.");
                return descEditJobTextViewCell;
            case 4:
                View photoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_job_phpto_image, parent, false);
                EditJobPhotoImageCell photoViewHolder = new EditJobPhotoImageCell(photoView);
                return photoViewHolder;
            case 5:
                View takePhotoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_job_take_photo_cell, parent, false);
                EditJobTakePhotoCell editJobTakePhotoCell = new EditJobTakePhotoCell(takePhotoView);
                return editJobTakePhotoCell;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return IMTE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}