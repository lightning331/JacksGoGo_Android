package com.kelvin.jacksgogo.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.kelvin.jacksgogo.Fragments.Appointments.EditJobFragment;
import com.kelvin.jacksgogo.Fragments.Appointments.EditJobMainFragment;
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
                View jobTitleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_detail_header_view, parent, false);
                SectionTitleViewHolder sectionTitleViewHolder = new SectionTitleViewHolder(jobTitleView);
                sectionTitleViewHolder.title.setText("Give your job a short title.");
                return sectionTitleViewHolder;
            case 1:
                View titleBorderTextView = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_job_text_view_cell, parent, false);
                BorderTextViewHolder borderTextViewHolder = new BorderTextViewHolder(titleBorderTextView);
                return borderTextViewHolder;
            case 2:
                View descTitleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_detail_header_view, parent, false);
                SectionTitleViewHolder descTitleViewHolder = new SectionTitleViewHolder(descTitleView);
                descTitleViewHolder.title.setText("Describe the job you need done.");
                return descTitleViewHolder;
            case 3:
                View descBorderTextView = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_job_text_view_cell, parent, false);
                BorderTextViewHolder descBorderTextViewHolder = new BorderTextViewHolder(descBorderTextView);
                descBorderTextViewHolder.editText.setText("Need help with moving the lawn and weeding the garden.");
                return descBorderTextViewHolder;
            case 4:
                View photoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_job_phpto_image, parent, false);
                PhotoImageViewHolder photoViewHolder = new PhotoImageViewHolder(photoView);
                return photoViewHolder;
            case 5:
                View takePhotoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_job_take_photo_cell, parent, false);
                TakePhotoViewHolder takePhotoViewHolder = new TakePhotoViewHolder(takePhotoView);
                return takePhotoViewHolder;
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

class BorderTextViewHolder extends RecyclerView.ViewHolder {

    EditText editText;

    public BorderTextViewHolder(View itemView) {
        super(itemView);
        editText = itemView.findViewById(R.id.txt_title);
    }
}

class TakePhotoViewHolder extends RecyclerView.ViewHolder {

    public TakePhotoViewHolder(View itemView) {
        super(itemView);
    }
}

class PhotoImageViewHolder extends RecyclerView.ViewHolder {

    public PhotoImageViewHolder(View itemView) {
        super(itemView);
    }
}
