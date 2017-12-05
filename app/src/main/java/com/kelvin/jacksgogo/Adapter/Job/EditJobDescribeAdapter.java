package com.kelvin.jacksgogo.Adapter.Job;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Appointment.AppFilterOptionCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Edit.EditJobPhotoImageCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Edit.EditJobTabbarView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Edit.EditJobTakePhotoCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Edit.EditJobTextViewCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.SectionTitleView;
import com.kelvin.jacksgogo.Models.Jobs_Services.JGGServiceModel;
import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/10/2017.
 */

public class EditJobDescribeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener, TextWatcher {

    Context mContext;

    AppFilterOptionCell nextButtonCell;
    EditJobTextViewCell serviceTitleCell;
    EditJobTextViewCell serviceDescCell;

    JGGServiceModel serviceObject;
    String strJobTitle;
    String strJobDesc;
    boolean isRequest;

    int ITEM_COUNT = 6;

    public EditJobDescribeAdapter(Context context, boolean isRequest, JGGServiceModel data) {
        this.mContext = context;
        this.isRequest = isRequest;
        this.serviceObject = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case 0:
                View jobTitleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_section_title, parent, false);
                SectionTitleView sectionTitleView = new SectionTitleView(jobTitleView);
                return sectionTitleView;
            case 1:
                View titleBorderTextView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_edit_job_text_view, parent, false);
                serviceTitleCell = new EditJobTextViewCell(titleBorderTextView);
                return serviceTitleCell;
            case 2:
                View descTitleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_section_title, parent, false);
                SectionTitleView descTitleViewHolder = new SectionTitleView(descTitleView);
                return descTitleViewHolder;
            case 3:
                View descBorderTextView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_edit_job_text_view, parent, false);
                serviceDescCell = new EditJobTextViewCell(descBorderTextView);
                return serviceDescCell;
            case 4:
                if (!isRequest) {
                    View photoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_edit_job_phpto_image, parent, false);
                    EditJobPhotoImageCell photoViewHolder = new EditJobPhotoImageCell(photoView);
                    return photoViewHolder;
                } else {
                    View takePhotoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_edit_job_take_photo, parent, false);
                    EditJobTakePhotoCell editJobTakePhotoCell = new EditJobTakePhotoCell(takePhotoView);
                    return editJobTakePhotoCell;
                }
            case 5:
                if (!isRequest) {
                    View takePhotoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_edit_job_take_photo, parent, false);
                    EditJobTakePhotoCell editJobTakePhotoCell = new EditJobTakePhotoCell(takePhotoView);
                    return editJobTakePhotoCell;
                } else {
                    View originalView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_app_filter_option, parent, false);
                    nextButtonCell = new AppFilterOptionCell(originalView);
                    return nextButtonCell;
                }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (position) {
            case 0:
                SectionTitleView sectionTitleView = (SectionTitleView)holder;
                sectionTitleView.setTitle("Give your job a short title.");
                sectionTitleView.txtTitle.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
                break;
            case 1:
                serviceTitleCell = (EditJobTextViewCell)holder;
                if (!isRequest) {
                    serviceTitleCell.editText.setText("Gerdening");
                } else {
                    if (serviceObject.getTitle() == null) {
                        serviceTitleCell.editText.setHint("e.g.Gardening");
                    } else {
                        serviceTitleCell.editText.setText(serviceObject.getTitle());
                    }
                    serviceTitleCell.editText.addTextChangedListener(this);
                }
                break;
            case 2:
                SectionTitleView descTitleViewHolder = (SectionTitleView)holder;
                descTitleViewHolder.setTitle("Describe the job you need done.");
                descTitleViewHolder.txtTitle.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
                break;
            case 3:
                serviceDescCell = (EditJobTextViewCell)holder;
                if (!isRequest) serviceDescCell.editText.setText("Need help with moving the lawn and weeding the garden.");
                else {
                    if (serviceObject.getComment() == null) {
                        serviceDescCell.editText.setHint("e.g.My air-cond unit isn't cold.");
                    } else {
                        serviceDescCell.editText.setText(serviceObject.getComment());
                    }
                    serviceDescCell.editText.addTextChangedListener(this);
                }
                break;
            case 4:
                if (!isRequest) {
                    EditJobPhotoImageCell photoViewHolder = (EditJobPhotoImageCell)holder;
                } else {
                    EditJobTakePhotoCell editJobTakePhotoCell = (EditJobTakePhotoCell)holder;
                    editJobTakePhotoCell.btnTakePhoto.setOnClickListener(this);
                }
                break;
            case 5:
                if (!isRequest) {
                    EditJobTakePhotoCell editJobTakePhotoCell = (EditJobTakePhotoCell)holder;
                    editJobTakePhotoCell.btnTakePhoto.setOnClickListener(this);
                } else {
                    nextButtonCell = (AppFilterOptionCell)holder;
                    nextButtonCell.title.setText("Next");
                    nextButtonCell.title.setTextColor(ContextCompat.getColor(mContext, R.color.JGGGrey2));
                    nextButtonCell.btnOriginal.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGGrey4));
                    nextButtonCell.btnOriginal.setBorderWidth((float)0);
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return ITEM_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onNextButtonClick(EditJobTabbarView.EditTabStatus status, String jobTitle, String jobDesc);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.view_filter_bg) {
            listener.onNextButtonClick(EditJobTabbarView.EditTabStatus.TIME, strJobTitle, strJobDesc);
        } else if (view.getId() == R.id.btn_take_photo) {

        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (serviceTitleCell.editText.length() > 0
                && serviceDescCell.editText.length() > 0) {
            strJobTitle = serviceTitleCell.editText.getText().toString();
            strJobDesc = serviceDescCell.editText.getText().toString();
            nextButtonCell.title.setTextColor(ContextCompat.getColor(mContext, R.color.JGGWhite));
            nextButtonCell.btnOriginal.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGGreen));
            nextButtonCell.btnOriginal.setBorderWidth((float)0);
            nextButtonCell.btnOriginal.setOnClickListener(this);
        } else {
            nextButtonCell.title.setTextColor(ContextCompat.getColor(mContext, R.color.JGGGrey2));
            nextButtonCell.btnOriginal.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGGrey4));
            nextButtonCell.btnOriginal.setBorderWidth((float) 0);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}