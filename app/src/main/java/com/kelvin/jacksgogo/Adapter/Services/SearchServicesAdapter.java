package com.kelvin.jacksgogo.Adapter.Services;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.jacksgogo.Activities.Search.ServiceDetailActivity;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services.SearchCategoryCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services.SearchHomeHeaderView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services.ServiceListDetailCell;
import com.kelvin.jacksgogo.CustomView.Views.SectionTitleView;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppBaseModel;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;

import java.util.ArrayList;

import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.SERVICES;

/**
 * Created by PUMA on 11/14/2017.
 */

public class SearchServicesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private ArrayList<JGGCategoryModel> mCategories;

    private int HEADER_TYPE = 0;
    private int CATEGORY_SECTION_TITLE_TYPE = 1;
    private int CATEGORY_SECTION_TYPE = 2;
    private int RECOMMEND_SECTION_TITLE_TYPE = 3;

    public SearchServicesAdapter(Context context, ArrayList<JGGCategoryModel> data) {
        mContext = context;
        mCategories = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == HEADER_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_search_home_header, parent, false);
            SearchHomeHeaderView categoryListView = new SearchHomeHeaderView(view, JGGAppBaseModel.AppointmentType.SERVICES, mContext);
            return categoryListView;
        } else if (viewType == CATEGORY_SECTION_TITLE_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_section_title, parent, false);
            SectionTitleView sectionView = new SectionTitleView(view);
            return sectionView;
        } else if (viewType == CATEGORY_SECTION_TYPE) {
            View listView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_search_category_list, parent, false);
            SearchCategoryCell categoryListView = new SearchCategoryCell(listView, mContext, JGGAppBaseModel.AppointmentType.SERVICES, mCategories);
            return categoryListView;
        } else if (viewType == RECOMMEND_SECTION_TITLE_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_section_title, parent, false);
            SectionTitleView sectionView = new SectionTitleView(view);
            return sectionView;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_service_list_detail, parent, false);
            ServiceListDetailCell cell = new ServiceListDetailCell(view);
            return cell;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == HEADER_TYPE) {
            SearchHomeHeaderView categoryListView = (SearchHomeHeaderView) holder;
            categoryListView.setOnClickListener(this);
        } else if (position == CATEGORY_SECTION_TITLE_TYPE) {
            SectionTitleView sectionView = (SectionTitleView) holder;
            sectionView.txtTitle.setText("All Categories");
            sectionView.txtTitle.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
        } else if (position == CATEGORY_SECTION_TYPE) {
            SearchCategoryCell categoryListView = (SearchCategoryCell) holder;
        } else if (position == RECOMMEND_SECTION_TITLE_TYPE) {
            SectionTitleView sectionView = (SectionTitleView) holder;
            sectionView.txtTitle.setText("Recommended For You");
            sectionView.txtTitle.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
        } else {
            ServiceListDetailCell cell = (ServiceListDetailCell) holder;
            cell.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ServiceDetailActivity.class);
                    intent.putExtra("is_service", true);
                    intent.putExtra(APPOINTMENT_TYPE, SERVICES);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 10;
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
