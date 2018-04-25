package com.kelvin.jacksgogo.Adapter.Profile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Profile.CreditCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Profile.CreditHeaderCell;
import com.kelvin.jacksgogo.R;

/**
 * Created by storm2 on 4/16/2018.
 */

public class CreditAdapter extends RecyclerView.Adapter {
    private Context mContext;

    public static final int HEADER_TYPE = 0;
    public static final int ITEM_TYPE = 1;

    public CreditAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.credit_header, parent, false);
            CreditHeaderCell header = new CreditHeaderCell(view);
            return header;
        } else if (viewType == ITEM_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.credit_cell, parent, false);
            CreditCell cell = new CreditCell(view);
            return cell;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder instanceof CreditHeaderCell) {
            ((CreditHeaderCell) holder).txtAdded.setText(mContext.getString(R.string.credit_added));
            ((CreditHeaderCell) holder).txtUsed.setText(mContext.getString(R.string.credit_used));
        }
        if (holder instanceof CreditCell) {}
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER_TYPE;
        } else
            return ITEM_TYPE;
    }

    private ProfileHomeAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(RecyclerView.ViewHolder holder, int position);
    }

    public void setOnItemClickListener(ProfileHomeAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
