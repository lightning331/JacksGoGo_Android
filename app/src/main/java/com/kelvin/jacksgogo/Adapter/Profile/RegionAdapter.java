package com.kelvin.jacksgogo.Adapter.Profile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Profile.SignupRegionCell;
import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 1/5/2018.
 */

public class RegionAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private Context mContext;

    public RegionAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View regionView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_sign_up_region, parent, false);
        SignupRegionCell cell = new SignupRegionCell(regionView);
        return cell;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        SignupRegionCell cell = (SignupRegionCell)holder;
        cell.itemView.setOnClickListener(this);

        if (position == 1) {
            cell.lblRegion.setText("Malaysia");
            cell.imgRegion.setImageResource(R.mipmap.icon_malaysia);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public void onClick(View view) {
        listener.onItemClick();
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
