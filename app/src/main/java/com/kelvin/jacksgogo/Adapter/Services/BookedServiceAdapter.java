package com.kelvin.jacksgogo.Adapter.Services;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.Service.PackageServiceTimeSlotActivity;
import com.kelvin.jacksgogo.R;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by storm on 5/14/2018.
 */

public class BookedServiceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<Date> bookingDates;

    public BookedServiceAdapter(Context context, ArrayList<Date> dates) {
        mContext = context;
        bookingDates = dates;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_book, parent, false);
        BookingViewHolder bookingViewHolder = new BookingViewHolder(mContext, view);
        return bookingViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        BookingViewHolder bookingViewHolder = (BookingViewHolder) holder;
        if (position == 2) {
            bookingViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, PackageServiceTimeSlotActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    private static class BookingViewHolder extends RecyclerView.ViewHolder {

        private Context mContext;
        public LinearLayout backLayout;
        public TextView txtDay;
        public TextView txtWeekDay;

        public BookingViewHolder(Context context, View itemView) {
            super(itemView);
            mContext = context;

            backLayout = (LinearLayout) itemView.findViewById(R.id.ll_back);
            txtDay = (TextView) itemView.findViewById(R.id.txt_day);
            txtWeekDay = (TextView) itemView.findViewById(R.id.txt_weekday);
        }
    }
}
