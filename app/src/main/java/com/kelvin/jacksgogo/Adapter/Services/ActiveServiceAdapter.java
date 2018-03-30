package com.kelvin.jacksgogo.Adapter.Services;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services.ServiceListDetailCell;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;

import java.util.ArrayList;

/**
 * Created by PUMA on 11/14/2017.
 */

public class ActiveServiceAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private ArrayList<JGGAppointmentModel> mServices = new ArrayList<>();

    public ActiveServiceAdapter(Context context) {
        mContext = context;
    }

    public void notifyDataChanged(ArrayList<JGGAppointmentModel> jobs) {
        mServices = jobs;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.cell_service_list_detail, null, false);

        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        return new ServiceListDetailCell(view, mContext);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ServiceListDetailCell cell = (ServiceListDetailCell)holder;
        JGGAppointmentModel service = mServices.get(position);
        cell.setService(service);
        cell.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mServices.size();
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
