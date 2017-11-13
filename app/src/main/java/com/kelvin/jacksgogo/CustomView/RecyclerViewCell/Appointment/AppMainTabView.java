package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Appointment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.Appointment.AppFilterActivity;
import com.kelvin.jacksgogo.R;


public class AppMainTabView extends RelativeLayout implements View.OnClickListener {

    Context mContext;
    LayoutInflater mLayoutInflater;

    TextView pendingTextView;
    TextView confirmTextView;
    TextView historyTextView;
    ImageView pendingDotImageView;
    ImageView confirmDotImageView;
    ImageView historyDotImageView;
    ImageButton filterButton;
    View actionbarView;


    public AppMainTabView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    private void initView(){

        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        actionbarView  = mLayoutInflater.inflate(R.layout.app_main_tab_view, this);

        pendingTextView = (TextView) actionbarView.findViewById(R.id.lbl_pending);
        confirmTextView = (TextView) actionbarView.findViewById(R.id.lbl_confirmed);
        historyTextView = (TextView) actionbarView.findViewById(R.id.lbl_history);
        pendingDotImageView = (ImageView) actionbarView.findViewById(R.id.img_pending_cirle);
        confirmDotImageView = (ImageView) actionbarView.findViewById(R.id.img_confirmed_circle);
        historyDotImageView = (ImageView) actionbarView.findViewById(R.id.img_history_circle);
        filterButton = (ImageButton) actionbarView.findViewById(R.id.btn_filter);

        filterButton.setOnClickListener(this);
        pendingTextView.setOnClickListener(this);
        confirmTextView.setOnClickListener(this);
        historyTextView.setOnClickListener(this);
        confirmTextView.setTag("CONFIRM");
        pendingTextView.setTag("PENDING");
        historyTextView.setTag("HISTORY");
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btn_filter) {
            // back to previous view
            Intent intent = new Intent(view.getContext(), AppFilterActivity.class);
            mContext.startActivity(intent);
        } else {
            pendingDotImageView.setVisibility(View.INVISIBLE);
            confirmDotImageView.setVisibility(View.INVISIBLE);
            historyDotImageView.setVisibility(View.INVISIBLE);

            pendingTextView.setTextColor(getResources().getColor(R.color.JGGGrey1));
            confirmTextView.setTextColor(getResources().getColor(R.color.JGGGrey1));
            historyTextView.setTextColor(getResources().getColor(R.color.JGGGrey1));

            if (view.getId() == R.id.lbl_pending) {
                pendingTextView.setTextColor(getResources().getColor(R.color.JGGOrange));
                pendingDotImageView.setVisibility(View.VISIBLE);
                listener.onTabbarItemClick(pendingTextView);
            } else if (view.getId() == R.id.lbl_confirmed) {
                confirmTextView.setTextColor(getResources().getColor(R.color.JGGOrange));
                confirmDotImageView.setVisibility(View.VISIBLE);
                listener.onTabbarItemClick(confirmTextView);
            } else if (view.getId() == R.id.lbl_history) {
                historyTextView.setTextColor(getResources().getColor(R.color.JGGOrange));
                historyDotImageView.setVisibility(View.VISIBLE);
                listener.onTabbarItemClick(historyTextView);
            }
        }
    }

    private OnTabbarItemClickListener listener;

    public interface OnTabbarItemClickListener {
        void onTabbarItemClick(TextView item);
    }

    public void setTabbarItemClickListener(OnTabbarItemClickListener listener) {
        this.listener = listener;
    }
}
