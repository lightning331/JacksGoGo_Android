package com.kelvin.jacksgogo.Adapter.Services;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.jacksgogo.Activities.Search.ServiceDetailActivity;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services.CategoryRecyclerView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services.SearchHomeHeaderView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services.ServiceListDetailCell;
import com.kelvin.jacksgogo.CustomView.Views.SectionTitleView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;

import java.util.ArrayList;

import static com.kelvin.jacksgogo.Utils.JGGAppManager.selectedAppointment;

/**
 * Created by PUMA on 11/14/2017.
 */

public class SearchServicesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private ArrayList<JGGCategoryModel> mCategories;
    private ArrayList<JGGAppointmentModel> mServices;

    private int HEADER_TYPE = 0;
    private int CATEGORY_SECTION_TITLE_TYPE = 1;
    private int CATEGORY_SECTION_TYPE = 2;
    private int RECOMMEND_SECTION_TITLE_TYPE = 3;

    public SearchServicesAdapter(Context context, ArrayList<JGGCategoryModel> data, ArrayList<JGGAppointmentModel> services) {
        mContext = context;
        mCategories = data;
        mServices = services;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == HEADER_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_search_home_header, parent, false);
            SearchHomeHeaderView headerView = new SearchHomeHeaderView(view, AppointmentType.SERVICES, mContext);
            headerView.totalServiceCount.setText(String.valueOf(mServices.size()));
            headerView.setOnClickListener(this);
            return headerView;
        } else if (viewType == CATEGORY_SECTION_TITLE_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_section_title, parent, false);
            SectionTitleView sectionView = new SectionTitleView(view);
            sectionView.txtTitle.setText("All Categories");
            sectionView.txtTitle.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
            return sectionView;
        } else if (viewType == CATEGORY_SECTION_TYPE) {
            View listView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_category_list, parent, false);
            CategoryRecyclerView categoryListView = new CategoryRecyclerView(listView, mContext, AppointmentType.SERVICES, mCategories);
            return categoryListView;
        } else if (viewType == RECOMMEND_SECTION_TITLE_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_section_title, parent, false);
            SectionTitleView sectionView = new SectionTitleView(view);
            sectionView.txtTitle.setText("Recommended For You");
            sectionView.txtTitle.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
            return sectionView;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_service_list_detail, parent, false);
            ServiceListDetailCell cell = new ServiceListDetailCell(view, mContext);
            return cell;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (position > RECOMMEND_SECTION_TITLE_TYPE)  {
            ServiceListDetailCell cell = (ServiceListDetailCell) holder;
            JGGAppointmentModel service = mServices.get(position - 4);

            cell.setService(service);
            cell.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedAppointment = null;
                    selectedAppointment = mServices.get(position - 4);;
                    Intent intent = new Intent(mContext, ServiceDetailActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mServices.size() + 4;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_view_my_service
                || view.getId() == R.id.btn_view_all
                || view.getId() == R.id.btn_post_new) {

            listener.onItemClick(view);

        }
    }

    public void notifyDataChanged(ArrayList<JGGCategoryModel> data, ArrayList<JGGAppointmentModel> services) {
        mCategories = data;
        mServices = services;
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View view);
    }

    public void setOnItemClickLietener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
