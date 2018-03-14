package com.kelvin.jacksgogo.Adapter.Jobs;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Appointment.AppFilterOptionCell;
import com.kelvin.jacksgogo.CustomView.Views.SectionTitleView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGReportModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PUMA on 11/10/2017.
 */

public class AppointmentReportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private int ITEM_COUNT = 4;
    public final static int TYPE_SECTION_HEADER = 0;

    private boolean isRequest;
    private String appointmentType;

    private List<JGGReportModel> list = new ArrayList<>();
    private List<Integer> selectedIds = new ArrayList<>();

    public AppointmentReportAdapter(Context context, boolean b, String appType) {
        this.mContext = context;
        this.isRequest = b;
        appointmentType = appType;

        this.list.add(new JGGReportModel(1, context.getString(R.string.edit_job_report_before_title),context.getString(R.string.edit_job_report_before_desc)));
        this.list.add(new JGGReportModel(2, context.getString(R.string.edit_job_report_geo_titles),context.getString(R.string.edit_job_report_geo_desc)));
        this.list.add(new JGGReportModel(3, context.getString(R.string.edit_job_report_pin_titles),context.getString(R.string.edit_job_report_pin_desc)));

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_SECTION_HEADER) {
            View sectionTitle = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_section_title, parent, false);
            SectionTitleView sectionTitleViewHolder = new SectionTitleView(sectionTitle);
            sectionTitleViewHolder.txtTitle.setText(R.string.edit_job_report_title);
            sectionTitleViewHolder.txtTitle.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
            return sectionTitleViewHolder;
        } else if (viewType > TYPE_SECTION_HEADER && viewType <= list.size()){
            View beforeViewHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_edit_job_report, parent, false);
            ReportViewHolder descTitleViewHolder = new ReportViewHolder(beforeViewHolder);
            return descTitleViewHolder;
        } else if (viewType == list.size() + 1){
            View originalView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_app_filter_option, parent, false);
            AppFilterOptionCell nextButtonCell = new AppFilterOptionCell(originalView);
            return nextButtonCell;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position > TYPE_SECTION_HEADER && position <= list.size()) {

            ReportViewHolder viewHolder = (ReportViewHolder) holder;
            viewHolder.title.setText(this.list.get(position - 1).getTitle());
            viewHolder.description.setText(this.list.get(position - 1).getDescription());
            int id = list.get(position - 1).getId();

            if (selectedIds.contains(id)){
                //if item is selected then,set foreground color of FrameLayout.
                viewHolder.btnBackground.setBackgroundResource(R.drawable.yellow_background);
                viewHolder.title.setTextColor(ContextCompat.getColor(mContext, R.color.JGGBlack));
            }
            else {
                //else remove selected item color.
                viewHolder.btnBackground.setBackgroundResource(R.drawable.green_border_background);
                viewHolder.title.setTextColor(ContextCompat.getColor(mContext, R.color.JGGGreen));
                if (appointmentType.equals("JOBS")) {
                    viewHolder.btnBackground.setBackgroundResource(R.drawable.cyan_border_background);
                    viewHolder.title.setTextColor(ContextCompat.getColor(mContext, R.color.JGGCyan));
                }
            }
        } else if (position == list.size() + 1) {
            AppFilterOptionCell nextButtonCell = (AppFilterOptionCell)holder;
            nextButtonCell.title.setText(R.string.go_to_summary);
            nextButtonCell.title.setTextColor(ContextCompat.getColor(mContext, R.color.JGGWhite));
            nextButtonCell.btnOriginal.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGGreen));
            if (appointmentType.equals("JOBS")) nextButtonCell.btnOriginal.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGCyan));
            nextButtonCell.btnOriginal.setBorderWidth((float) 0);
        }
    }

    public void setSelectedIds(List<Integer> selectedIds) {
        this.selectedIds = selectedIds;
        notifyDataSetChanged();
    }

    public JGGReportModel getItem(int position){
        return list.get(position);
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
    }
}