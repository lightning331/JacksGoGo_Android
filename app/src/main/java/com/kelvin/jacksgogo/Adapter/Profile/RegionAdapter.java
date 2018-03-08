package com.kelvin.jacksgogo.Adapter.Profile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Profile.SignupRegionCell;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.System.JGGRegionModel;

import java.util.ArrayList;

/**
 * Created by PUMA on 1/5/2018.
 */

public class RegionAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private ArrayList<JGGRegionModel> regions;

    public RegionAdapter(Context context, ArrayList<JGGRegionModel> regions) {
        this.mContext = context;
        this.regions = regions;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View regionView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_sign_up_region, parent, false);
        SignupRegionCell cell = new SignupRegionCell(regionView, mContext);
        return cell;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        SignupRegionCell cell = (SignupRegionCell)holder;

        if (regions.size() > 0) {
            final JGGRegionModel region = regions.get(position);
            cell.lblRegion.setText(region.getRegionName());
            cell.setImage(region.getImage());
            cell.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(region.getID());
                }
            });
        }
    }

    public void setData(ArrayList<JGGRegionModel> regions) {
        this.regions = regions;
    }

    @Override
    public int getItemCount() {
        return regions.size();
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(String regionID);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
