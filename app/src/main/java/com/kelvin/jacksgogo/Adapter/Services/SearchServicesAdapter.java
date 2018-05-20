package com.kelvin.jacksgogo.Adapter.Services;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.jacksgogo.Activities.Search.ServiceDetailActivity;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services.CategoryRecyclerView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services.SearchHomeHeaderView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services.ServiceListDetailCell;
import com.kelvin.jacksgogo.CustomView.Views.LoadingViewHolder;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;

import java.util.ArrayList;

/**
 * Created by PUMA on 11/14/2017.
 */

public class SearchServicesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private ArrayList<JGGCategoryModel> mCategories;
    private ArrayList<JGGAppointmentModel> mServices;

    private final int HEADER_TYPE = 0;
    private final int VIEW_TYPE_CATEGORY = 1;
    private final int VIEW_TYPE_ITEM = 2;
    private final int VIEW_TYPE_LOADING = 3;

    OnLoadMoreListener loadMoreListener;
    // TODO - load more
    boolean isLoading = false, isMoreDataAvailable = true;
    /*
    * isLoading - to set the remote loading and complete status to fix back to back load more call
    * isMoreDataAvailable - to set whether more data from server available or not.
    * It will prevent useless load more request even after all the server data loaded
    * */

    public SearchServicesAdapter(Context context, ArrayList<JGGAppointmentModel> services) {
        mContext = context;
        mCategories = JGGAppManager.getInstance().getCategories();
        mServices = services;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == HEADER_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_search_home_header, parent, false);
            SearchHomeHeaderView headerView = new SearchHomeHeaderView(view, AppointmentType.SERVICES, mContext);
            headerView.setData(mServices.size());
            headerView.setOnClickListener(this);
            return headerView;
        } else if (viewType == VIEW_TYPE_CATEGORY) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_category_list, parent, false);
            CategoryRecyclerView categoryCell = new CategoryRecyclerView(view, mContext, AppointmentType.SERVICES, mCategories);
            return categoryCell;
        } else if (viewType == VIEW_TYPE_ITEM){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_service_list_detail, parent, false);
            ServiceListDetailCell cell = new ServiceListDetailCell(view, mContext);
            return cell;
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.load_more_view, parent, false);
            LoadingViewHolder viewHolder = new LoadingViewHolder(view);
            return viewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(position>=getItemCount()-2 && isMoreDataAvailable && !isLoading && loadMoreListener!=null){
            isLoading = true;
            loadMoreListener.onLoadMore();
        }

        if (getItemViewType(position)==VIEW_TYPE_ITEM) {
            ServiceListDetailCell cell = (ServiceListDetailCell) holder;
            JGGAppointmentModel service = mServices.get(position - 2);

            cell.setService(service);
            cell.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JGGAppManager.getInstance().setSelectedAppointment(mServices.get(position - 2));

                    if(JGGAppManager.getInstance().getCurrentUser() == null) {}
                    else {
                        Intent intent = new Intent(mContext, ServiceDetailActivity.class);
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mServices.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER_TYPE;
        } else if (position == 1) {
            return VIEW_TYPE_CATEGORY;
        } else  {
            if (mServices.get(position - 2).getID() != null) {
                return VIEW_TYPE_ITEM;
            } else {
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
    public void notifyDataChanged(ArrayList<JGGAppointmentModel> services) {
        mServices = services;

        super.notifyDataSetChanged();
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
