package com.kelvin.jacksgogo.Adapter.Services;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.Service.PackageServiceTimeSlotActivity;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global.JGGTimeSlotBookedStatus;
import com.kelvin.jacksgogo.Utils.Models.System.JGGTimeSlotModel;

import java.util.ArrayList;

/**
 * Created by storm on 5/14/2018.
 */

public class BookedServiceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<JGGTimeSlotModel> bookingDates;

    public BookedServiceAdapter(Context context, ArrayList<JGGTimeSlotModel> dates) {
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

        JGGTimeSlotModel timeSlot = bookingDates.get(position);
        bookingViewHolder.setBookedStatus(timeSlot);
    }

    @Override
    public int getItemCount() {
        return bookingDates.size();
    }

    private static class BookingViewHolder extends RecyclerView.ViewHolder {

        private Context mContext;
        public LinearLayout backLayout;
        public TextView txtDay;
        public TextView txtWeekDay;

        public BookingViewHolder(Context context, View itemView) {
            super(itemView);
            mContext = context;

            backLayout = itemView.findViewById(R.id.ll_back);
            txtDay = itemView.findViewById(R.id.txt_day);
            txtWeekDay = itemView.findViewById(R.id.txt_weekday);
        }

        // TODO - Need to fix ===========================

        public void setBookedStatus(JGGTimeSlotModel timeSlot) {
            JGGTimeSlotBookedStatus status = timeSlot.getStatus();
            switch (status) {
                case none:
                case not_booked:
                    backLayout.setBackgroundResource(R.drawable.book_background);
                    txtDay.setVisibility(View.GONE);
                    txtWeekDay.setText("Book Time Slot");
                    txtWeekDay.setTextColor(ContextCompat.getColor(mContext, R.color.JGGWhite));
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, PackageServiceTimeSlotActivity.class);
                            mContext.startActivity(intent);
                        }
                    });
                    break;
                case booking:
                    break;
                case booked:
                    backLayout.setBackgroundResource(R.drawable.grey_border_background);
                    txtDay.setTextColor(ContextCompat.getColor(mContext, R.color.JGGGrey3));
                    txtWeekDay.setTextColor(ContextCompat.getColor(mContext, R.color.JGGGrey3));
                    break;
                default:
                    break;
            }
        }
    }
}
