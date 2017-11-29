package com.kelvin.jacksgogo.Adapter;

import android.content.Context;
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

import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Appointment.AppFilterOptionCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail.EditJobTabbarView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.SectionTitleView;
import com.kelvin.jacksgogo.Fragments.Appointments.EditJobFragment;
import com.kelvin.jacksgogo.Models.Jobs_Services.JGGReportModel;
import com.kelvin.jacksgogo.Models.Jobs_Services.JGGServiceModel;
import com.kelvin.jacksgogo.R;

import java.util.ArrayList;

/**
 * Created by PUMA on 11/10/2017.
 */

public class EditJobReportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    int ITEM_COUNT = 4;
    public final static int TYPE_SECTION_HEADER = 0;

    JGGServiceModel serviceObject;
    boolean isRequest;
    int reportType;

    AppFilterOptionCell nextButtonCell;

    private ArrayList<ReportViewHolder> descTitleViewHolders = new ArrayList<>();
    private ArrayList<JGGReportModel> reportSet = new ArrayList<>();

    public EditJobReportAdapter(Context context, boolean b, JGGServiceModel data) {
        this.mContext = context;
        this.isRequest = b;
        this.serviceObject = data;

        this.reportSet.add(new JGGReportModel(context.getString(R.string.edit_job_report_before_title),context.getString(R.string.edit_job_report_before_desc)));
        this.reportSet.add(new JGGReportModel(context.getString(R.string.edit_job_report_geo_titles),context.getString(R.string.edit_job_report_geo_desc)));
        this.reportSet.add(new JGGReportModel(context.getString(R.string.edit_job_report_pin_titles),context.getString(R.string.edit_job_report_pin_desc)));

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_SECTION_HEADER) {
            View sectionTitle = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_title_view, parent, false);
            SectionTitleView sectionTitleViewHolder = new SectionTitleView(sectionTitle);
            sectionTitleViewHolder.txtTitle.setText(R.string.edit_job_report_title);
            sectionTitleViewHolder.txtTitle.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
            return sectionTitleViewHolder;
        } else if (viewType > TYPE_SECTION_HEADER && viewType <= reportSet.size()){
            View beforeViewHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_job_repor_cell, parent, false);
            ReportViewHolder descTitleViewHolder = new ReportViewHolder(beforeViewHolder);
            return descTitleViewHolder;
        } else if (viewType == reportSet.size() + 1){
            View originalView = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_filter_option_cell, parent, false);
            nextButtonCell = new AppFilterOptionCell(originalView);
            return nextButtonCell;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position > TYPE_SECTION_HEADER && position <= reportSet.size()) {
            ReportViewHolder descTitleViewHolder = (ReportViewHolder)holder;
            descTitleViewHolder.title.setText(this.reportSet.get(position-1).getTitle());
            descTitleViewHolder.description.setText(this.reportSet.get(position-1).getDescription());
            descTitleViewHolder.bind(position, listener);
            descTitleViewHolders.add(descTitleViewHolder);
        } else if (position == reportSet.size() + 1) {
            nextButtonCell = (AppFilterOptionCell)holder;
            nextButtonCell.title.setText(R.string.go_to_summary);
            nextButtonCell.title.setTextColor(ContextCompat.getColor(mContext, R.color.JGGGrey1));
            nextButtonCell.btnOriginal.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGGrey3));
            nextButtonCell.btnOriginal.setBorderWidth((float)0);
        }
    }

    @Override
    public int getItemCount() {
        if (isRequest) {
            return ITEM_COUNT = 5;
        } else {
            return ITEM_COUNT;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position, JGGServiceModel object);
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

                    for (int i=0; i<descTitleViewHolders.size(); i++) {
                        descTitleViewHolders.get(i).btnBackground.setBackground(ContextCompat.getDrawable(mContext, R.drawable.green_border_background));
                        descTitleViewHolders.get(i).title.setTextColor(ContextCompat.getColor(mContext, R.color.JGGGreen));
                    }

                    btnBackground.setBackground(ContextCompat.getDrawable(mContext, R.drawable.yellow_background));
                    title.setTextColor(ContextCompat.getColor(mContext, R.color.JGGBlack));

                    nextButtonCell.title.setTextColor(ContextCompat.getColor(mContext, R.color.JGGWhite));
                    nextButtonCell.btnOriginal.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGGreen));
                    nextButtonCell.btnOriginal.setBorderWidth((float)0);
                    nextButtonCell.btnOriginal.setOnClickListener(this);

                    // Summary Button Clicked
                    if (view.getId() == R.id.view_filter_bg) {
                        listener.onItemClick(position, serviceObject);
                    }
                }
            });
        }
    }
}