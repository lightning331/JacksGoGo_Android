package com.kelvin.jacksgogo.Adapter.Services;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.jacksgogo.Activities.Search.ServiceDetailActivity;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services.CategoryRecyclerView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services.SearchHomeHeaderView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services.ServiceListDetailCell;
import com.kelvin.jacksgogo.CustomView.Views.LoadingViewHolder;
import com.kelvin.jacksgogo.CustomView.Views.SectionTitleView;
import com.kelvin.jacksgogo.Listeners.OnLoadMoreListener;
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

    private final int HEADER_TYPE = 0;
    private final int VIEW_TYPE_ITEM = 1;
    private final int VIEW_TYPE_LOADING = 2;

    OnLoadMoreListener loadMoreListener;
    // TODO - load more
    boolean isLoading = false, isMoreDataAvailable = true;
    /*
    * isLoading - to set the remote loading and complete status to fix back to back load more call
    * isMoreDataAvailable - to set whether more data from server available or not.
    * It will prevent useless load more request even after all the server data loaded
    * */

    public SearchServicesAdapter(Context context, ArrayList<JGGCategoryModel> data, ArrayList<JGGAppointmentModel> services) {
        mContext = context;
        mCategories = data;
        mServices = services;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == HEADER_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_search_home_header, parent, false);
            SearchHomeHeaderView headerView = new SearchHomeHeaderView(view, AppointmentType.SERVICES, mContext, mCategories);
            headerView.totalServiceCount.setText(String.valueOf(mServices.size()));
            headerView.setOnClickListener(this);
            return headerView;
        } else if (viewType == VIEW_TYPE_ITEM){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_service_list_detail, parent, false);
            ServiceListDetailCell cell = new ServiceListDetailCell(view, mContext);
            return cell;
        }
        else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.load_more_view, parent, false);
            LoadingViewHolder viewHolder = new LoadingViewHolder(view);
            return viewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(position>=getItemCount()-1 && isMoreDataAvailable && !isLoading && loadMoreListener!=null){
            isLoading = true;
            loadMoreListener.onLoadMore();
        }

        if (getItemViewType(position)==VIEW_TYPE_ITEM) {
            ServiceListDetailCell cell = (ServiceListDetailCell) holder;
            JGGAppointmentModel service = mServices.get(position - 1);

            cell.setService(service);
            cell.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedAppointment = null;
                    selectedAppointment = mServices.get(position - 1);
                    Intent intent = new Intent(mContext, ServiceDetailActivity.class);
                    intent.putExtra("is_service", true);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mServices.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER_TYPE;
        } else  {
            if(mServices.get(position-1).getID() != null){
                return VIEW_TYPE_ITEM;
            }else{
                return VIEW_TYPE_LOADING;
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_view_my_service
                || view.getId() == R.id.btn_view_all
                || view.getId() == R.id.btn_post_new) {

            listener.onItemClick(view);

        }
    }

    /* notifyDataSetChanged is final method so we can't override it
         call adapter.notifyDataChanged(); after update the list
         */
    public void notifyDataChanged(ArrayList<JGGCategoryModel> data, ArrayList<JGGAppointmentModel> services) {
        mCategories = data;
        mServices = services;

        notifyDataSetChanged();
        isLoading = false;
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View view);
    }

    public void setOnItemClickLietener(OnItemClickListener listener) {
        this.listener = listener;
    }

    /*
    ** OnLoadMoreListener
     */
    public interface OnLoadMoreListener{
        void onLoadMore();
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }
}
