package com.kelvin.jacksgogo.Adapter.Services;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.jacksgogo.Activities.Search.ActiveServiceActivity;
import com.kelvin.jacksgogo.Activities.Search.PostServiceActivity;
import com.kelvin.jacksgogo.Activities.Search.ServiceDetailActivity;
import com.kelvin.jacksgogo.Activities.Search.ServiceListingActivity;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services.SearchCategoryListView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services.SearchHomeHeaderView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services.ServiceListDetailCell;
import com.kelvin.jacksgogo.CustomView.Views.SectionTitleView;
import com.kelvin.jacksgogo.Models.Jobs_Services_Events.JGGAppBaseModel;
import com.kelvin.jacksgogo.R;

import static android.content.ContentValues.TAG;

/**
 * Created by PUMA on 11/14/2017.
 */

public class SearchServicesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private int HEADER_TYPE = 0;
    private int CATEGORY_SECTION_TITLE_TYPE = 1;
    private int CATEGORY_SECTION_TYPE = 2;
    private int RECOMMEND_SECTION_TITLE_TYPE = 3;

    public SearchServicesAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == HEADER_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_search_home_header, parent, false);
            SearchHomeHeaderView categoryListView = new SearchHomeHeaderView(view, JGGAppBaseModel.AppointmentType.SERVICES, mContext);
            categoryListView.setOnClickListener(this);
            return categoryListView;
        } else if (viewType == CATEGORY_SECTION_TITLE_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_section_title, parent, false);
            SectionTitleView sectionView = new SectionTitleView(view);
            sectionView.txtTitle.setText("All Categories");
            sectionView.txtTitle.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
            return sectionView;
        } else if (viewType == CATEGORY_SECTION_TYPE) {
            View listView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_search_category_list, parent, false);
            SearchCategoryListView categoryListView = new SearchCategoryListView(listView);
            categoryListView.setOnClickListener(this);
            return categoryListView;
        } else if (viewType == RECOMMEND_SECTION_TITLE_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_section_title, parent, false);
            SectionTitleView sectionView = new SectionTitleView(view);
            sectionView.txtTitle.setText("Recommended For You");
            sectionView.txtTitle.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
            return sectionView;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_service_list_detail, parent, false);
            ServiceListDetailCell cell = new ServiceListDetailCell(view);
            cell.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ServiceDetailActivity.class);
                    intent.putExtra("is_service", true);
                    mContext.startActivity(intent);
                }
            });
            return cell;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 7;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_view_my_service) {
            Log.d(TAG, "view my services ========= : ");
            Intent intent = new Intent(mContext.getApplicationContext(), ServiceListingActivity.class);
            view.getContext().startActivity(intent);
        } else if (view.getId() == R.id.btn_view_all || view.getId() == R.id.btn_other_professions) {
            Log.d(TAG, "view all services ========= : ");
            Intent intent = new Intent(mContext.getApplicationContext(), ActiveServiceActivity.class);
            view.getContext().startActivity(intent);
        } else if (view.getId() == R.id.btn_post_new) {
            Log.d(TAG, "post new ========= : ");
            Intent intent = new Intent(mContext.getApplicationContext(), PostServiceActivity.class);
            //intent.putExtra("EDIT_STATUS", "None");
            view.getContext().startActivity(intent);
        }
    }
}
