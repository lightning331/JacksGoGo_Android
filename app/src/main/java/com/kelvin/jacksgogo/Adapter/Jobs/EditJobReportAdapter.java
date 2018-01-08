package com.kelvin.jacksgogo.Adapter.Jobs;

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
import com.kelvin.jacksgogo.CustomView.Views.SectionTitleView;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGReportModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGServiceModel;
import com.kelvin.jacksgogo.R;

import java.util.ArrayList;

/**
 * Created by PUMA on 11/10/2017.
 */

public class EditJobReportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private int ITEM_COUNT = 4;
    public final static int TYPE_SECTION_HEADER = 0;

    private JGGServiceModel serviceObject;
    private boolean isRequest;
    private boolean isReportTypeBefore = false;
    private boolean isReportTypeGeoTracking = false;
    private boolean isReportTypePinCode = false;

    private int reportType;

    AppFilterOptionCell nextButtonCell;

    private ArrayList<ReportViewHolder> reportTypeArray = new ArrayList<>();
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
            View sectionTitle = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_section_title, parent, false);
            SectionTitleView sectionTitleViewHolder = new SectionTitleView(sectionTitle);
            sectionTitleViewHolder.txtTitle.setText(R.string.edit_job_report_title);
            sectionTitleViewHolder.txtTitle.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
            return sectionTitleViewHolder;
        } else if (viewType > TYPE_SECTION_HEADER && viewType <= reportSet.size()){
            View beforeViewHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_edit_job_repor, parent, false);
            ReportViewHolder descTitleViewHolder = new ReportViewHolder(beforeViewHolder);
            return descTitleViewHolder;
        } else if (viewType == reportSet.size() + 1){
            View originalView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_app_filter_option, parent, false);
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
            reportTypeArray.add(descTitleViewHolder);
        } else if (position == reportSet.size() + 1) {
            nextButtonCell = (AppFilterOptionCell)holder;
            nextButtonCell.title.setText(R.string.go_to_summary);
            nextButtonCell.title.setTextColor(ContextCompat.getColor(mContext, R.color.JGGGrey2));
            nextButtonCell.btnOriginal.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGGrey4));
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

                    if (position == 1) {
                        isReportTypeBefore = !isReportTypeBefore;
                        if (isReportTypeBefore) {
                            reportTypeArray.get(0).btnBackground.setBackground(ContextCompat.getDrawable(mContext, R.drawable.yellow_background));
                            reportTypeArray.get(0).title.setTextColor(ContextCompat.getColor(mContext, R.color.JGGBlack));
                        } else {
                            reportTypeArray.get(0).btnBackground.setBackground(ContextCompat.getDrawable(mContext, R.drawable.green_border_background));
                            reportTypeArray.get(0).title.setTextColor(ContextCompat.getColor(mContext, R.color.JGGGreen));
                        }
                    }
                    if (position == 2) {
                        isReportTypeGeoTracking = !isReportTypeGeoTracking;
                        if (isReportTypeGeoTracking) {
                            reportTypeArray.get(1).btnBackground.setBackground(ContextCompat.getDrawable(mContext, R.drawable.yellow_background));
                            reportTypeArray.get(1).title.setTextColor(ContextCompat.getColor(mContext, R.color.JGGBlack));
                        } else {
                            reportTypeArray.get(1).btnBackground.setBackground(ContextCompat.getDrawable(mContext, R.drawable.green_border_background));
                            reportTypeArray.get(1).title.setTextColor(ContextCompat.getColor(mContext, R.color.JGGGreen));
                        }
                    }
                    if (position == 3) {
                        isReportTypePinCode = !isReportTypePinCode;
                        if (isReportTypePinCode) {
                            reportTypeArray.get(2).btnBackground.setBackground(ContextCompat.getDrawable(mContext, R.drawable.yellow_background));
                            reportTypeArray.get(2).title.setTextColor(ContextCompat.getColor(mContext, R.color.JGGBlack));
                        } else {
                            reportTypeArray.get(2).btnBackground.setBackground(ContextCompat.getDrawable(mContext, R.drawable.green_border_background));
                            reportTypeArray.get(2).title.setTextColor(ContextCompat.getColor(mContext, R.color.JGGGreen));
                        }
                    }

                    if (isRequest) {
                        nextButtonCell.title.setTextColor(ContextCompat.getColor(mContext, R.color.JGGWhite));
                        nextButtonCell.btnOriginal.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGGreen));
                        nextButtonCell.btnOriginal.setBorderWidth((float) 0);
                        nextButtonCell.btnOriginal.setOnClickListener(this);
                    }

                    // Summary Button Clicked
                    if (view.getId() == R.id.view_filter_bg) {
                        listener.onItemClick(position, serviceObject);
                    }
                }
            });
        }
    }
}