package com.kelvin.jacksgogo.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.CustomView.JGGTabbarView;
import com.kelvin.jacksgogo.Fragments.Appointments.EditJobMainFragment;
import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/9/2017.
 */

public class EditJobMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    EditJobMainFragment mContext;
    int ITEM_COUNT = 4;
    JGGTabbarView tabbarView;


    public EditJobMainAdapter(EditJobMainFragment context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {

        tabbarView = new JGGTabbarView(mContext.getContext());
        switch (viewType) {
            case 0:
                View imageCellView = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_job_main_list_image_cell, parent, false);
                EditJobMainListImageViewHolder imageViewHolder = new EditJobMainListImageViewHolder(imageCellView);
                imageViewHolder.tagTitleTextView.setText(R.string.edit_job_tag_describe);

                imageViewHolder.tagButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onItemClick(JGGTabbarView.EditTabStatus.DESCRIBE);
                    }
                });
                return imageViewHolder;
            case 1:
                View timeView = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_job_main_list_cell, parent, false);
                EditJobMainListViewHolder timeViewHolder = new EditJobMainListViewHolder(timeView);
                timeViewHolder.tagTitleTextView.setText(R.string.edit_job_tag_time);
                timeViewHolder.jobDescTitleTextView.setText("One-Time Job");
                timeViewHolder.jobDescriptionTextView.setText("21 Jul,2017 10:00AM - 12:00PM");

                timeViewHolder.tagButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onItemClick(JGGTabbarView.EditTabStatus.TIME);
                    }
                });
                return timeViewHolder;
            case 2:
                View addressView = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_job_main_list_cell, parent, false);
                EditJobMainListViewHolder addressViewHolder = new EditJobMainListViewHolder(addressView);
                addressViewHolder.tagTitleTextView.setText(R.string.edit_job_tag_address);
                addressViewHolder.jobDescTitleTextView.setVisibility(View.GONE);
                addressViewHolder.jobDescriptionTextView.setText("Jurong West Avenue 5 23412424");

                addressViewHolder.tagButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onItemClick(JGGTabbarView.EditTabStatus.ADDRESS);
                    }
                });
                return addressViewHolder;
            case 3:
                View reportView = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_job_main_list_cell, parent, false);
                EditJobMainListViewHolder reportViewHolder = new EditJobMainListViewHolder(reportView);
                reportViewHolder.tagTitleTextView.setText(R.string.edit_job_tag_report);
                reportViewHolder.jobDescTitleTextView.setVisibility(View.GONE);
                reportViewHolder.jobDescriptionTextView.setText("Before & After Photo");

                reportViewHolder.tagButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onItemClick(JGGTabbarView.EditTabStatus.REPORT);
                    }
                });
                return reportViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

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
        void onItemClick(JGGTabbarView.EditTabStatus status);
    }

    public void setItemClickLietener(OnItemClickListener listener) {
        this.listener = listener;
    }

}

class EditJobMainListImageViewHolder extends RecyclerView.ViewHolder {

    LinearLayout tagButton;
    TextView tagTitleTextView;
    TextView jobDescTitleTextView;
    TextView jobDescriptionTextView;

    public EditJobMainListImageViewHolder(View itemView) {
        super(itemView);

        this.tagButton = itemView.findViewById(R.id.btn_edit_job_tag);
        this.tagTitleTextView = itemView.findViewById(R.id.lbl_edit_job_tag_title);
        this.jobDescTitleTextView = itemView.findViewById(R.id.lbl_edit_job_description_title);
        this.jobDescriptionTextView = itemView.findViewById(R.id.lbl_edit_job_description);
    }
}

class EditJobMainListViewHolder extends RecyclerView.ViewHolder {

    LinearLayout tagButton;
    TextView tagTitleTextView;
    TextView jobDescTitleTextView;
    TextView jobDescriptionTextView;

    public EditJobMainListViewHolder(View itemView) {
        super(itemView);

        this.tagButton = itemView.findViewById(R.id.btn_edit_job_tag1);
        this.tagTitleTextView = itemView.findViewById(R.id.lbl_edit_job_tag_title1);
        this.jobDescTitleTextView = itemView.findViewById(R.id.lbl_edit_job_description_title1);
        this.jobDescriptionTextView = itemView.findViewById(R.id.lbl_edit_job_description1);
    }
}
