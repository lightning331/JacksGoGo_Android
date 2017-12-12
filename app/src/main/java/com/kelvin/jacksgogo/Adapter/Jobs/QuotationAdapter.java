package com.kelvin.jacksgogo.Adapter.Jobs;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Appointment.AppBiddingProviderCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.SectionTitleView;
import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 12/12/2017.
 */

public class QuotationAdapter extends RecyclerView.Adapter {

    private Context mContext;

    public QuotationAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_section_title, parent, false);
            return new SectionTitleView(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_app_bidding_provider, parent, false);
            return new AppBiddingProviderCell(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            SectionTitleView sectionView = (SectionTitleView) holder;
            sectionView.txtTitle.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
            sectionView.setTitle("Bidding service providers:");
        } else {
            AppBiddingProviderCell cell = (AppBiddingProviderCell) holder;
        }
    }

    @Override
    public int getItemCount() {
        return 9;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
