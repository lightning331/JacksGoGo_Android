package com.kelvin.jacksgogo.Adapter.Jobs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Appointment.AppBiddingProviderCell;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.JGGBiddingProviderModel;

import java.util.ArrayList;

/**
 * Created by PUMA on 12/12/2017.
 */

public class QuotationAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private Context mContext;
    private ArrayList<JGGBiddingProviderModel> providerArray = new ArrayList<>();
    private JGGBiddingProviderModel provider;

    public QuotationAdapter(Context context, ArrayList<JGGBiddingProviderModel> data) {
        this.mContext = context;
        this.providerArray = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_app_bidding_provider, parent, false);
        return new AppBiddingProviderCell(mContext, view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        provider = providerArray.get(position);

        AppBiddingProviderCell cell = (AppBiddingProviderCell) holder;
        cell.setData(provider);
        cell.imgProposal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.img_proposal) {
                    listener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return providerArray.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onClick(View view) {

    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
