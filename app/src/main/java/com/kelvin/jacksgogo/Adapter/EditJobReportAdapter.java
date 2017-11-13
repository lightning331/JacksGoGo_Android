package com.kelvin.jacksgogo.Adapter;

import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.SectionTitleView;
import com.kelvin.jacksgogo.Fragments.Appointments.EditJobFragment;
import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/10/2017.
 */

public class EditJobReportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    EditJobFragment mContext;

    int ITEM_COUNT = 4;
    Boolean isSelected = false;

    public EditJobReportAdapter(EditJobFragment context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View sectionTitle = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_title_view, parent, false);
            SectionTitleView sectionTitleViewHolder = new SectionTitleView(sectionTitle);
            sectionTitleViewHolder.txtTitle.setText(R.string.edit_job_report_title);
            sectionTitleViewHolder.txtTitle.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
            return sectionTitleViewHolder;
        } else {
            View beforeViewHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_job_repor_cell, parent, false);
            ReportViewHolder descTitleViewHolder = new ReportViewHolder(beforeViewHolder);
            return descTitleViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (position == 1) {
            ReportViewHolder descTitleViewHolder = (ReportViewHolder)holder;
            descTitleViewHolder.title.setText(R.string.edit_job_report_before_title);
            descTitleViewHolder.description.setText(R.string.edit_job_report_before_desc);
            descTitleViewHolder.bind(position, listener);
        } else if (position == 2) {
            ReportViewHolder descTitleViewHolder = (ReportViewHolder)holder;
            descTitleViewHolder.title.setText(R.string.edit_job_report_geo_titles);
            descTitleViewHolder.description.setText(R.string.edit_job_report_geo_desc);
            descTitleViewHolder.bind(position, listener);
        } else if (position == 3) {
            ReportViewHolder descTitleViewHolder = (ReportViewHolder)holder;
            descTitleViewHolder.title.setText(R.string.edit_job_report_pin_titles);
            descTitleViewHolder.description.setText(R.string.edit_job_report_pin_desc);
            descTitleViewHolder.bind(position, listener);
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
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class ReportViewHolder extends RecyclerView.ViewHolder {

        LinearLayout btnBackground;
        TextView title;
        TextView description;

        public ReportViewHolder(View itemView) {
            super(itemView);
            btnBackground = itemView.findViewById(R.id.edit_job_background);
            title = itemView.findViewById(R.id.lbl_edit_job_address_title);
            description = itemView.findViewById(R.id.lbl_edit_job_address_desc);
        }

        public void bind(final int position, final OnItemClickListener listener) {
            btnBackground.setOnClickListener(new View.OnClickListener() {

                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View view) {

                    if (!isSelected) {
                        btnBackground.setBackground(ContextCompat.getDrawable(mContext.getContext(), R.drawable.yellow_background));
                        title.setTextColor(ContextCompat.getColor(mContext.getContext(), R.color.JGGBlack));
                    } else {
                        btnBackground.setBackground(ContextCompat.getDrawable(mContext.getContext(), R.drawable.green_border_background));
                        title.setTextColor(ContextCompat.getColor(mContext.getContext(), R.color.JGGGreen));
                    }
                    isSelected = !isSelected;
                    //listener.onItemClick(position);
                }
            });
        }
    }
}