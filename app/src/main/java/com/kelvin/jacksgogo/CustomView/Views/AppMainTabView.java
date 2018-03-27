package com.kelvin.jacksgogo.CustomView.Views;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.Appointment.AppFilterActivity;
import com.kelvin.jacksgogo.R;


public class AppMainTabView extends RelativeLayout implements View.OnClickListener {

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    private LinearLayout pendingButton;
    private LinearLayout confirmButton;
    private LinearLayout historyButton;
    private TextView pendingTextView;
    private TextView confirmTextView;
    private TextView historyTextView;
    private ImageView pendingDotImageView;
    private ImageView confirmDotImageView;
    private ImageView historyDotImageView;
    private ImageButton filterButton;
    private View actionbarView;

    private String mStatus;

    public AppMainTabView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    private void initView(){

        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        actionbarView  = mLayoutInflater.inflate(R.layout.view_app_main_tab, this);

        pendingButton = (LinearLayout) actionbarView.findViewById(R.id.pending_layout);
        confirmButton = (LinearLayout) actionbarView.findViewById(R.id.confirm_layout);
        historyButton = (LinearLayout) actionbarView.findViewById(R.id.history_layout);
        pendingTextView = (TextView) actionbarView.findViewById(R.id.lbl_pending);
        confirmTextView = (TextView) actionbarView.findViewById(R.id.lbl_confirmed);
        historyTextView = (TextView) actionbarView.findViewById(R.id.lbl_history);
        pendingDotImageView = (ImageView) actionbarView.findViewById(R.id.img_pending_cirle);
        confirmDotImageView = (ImageView) actionbarView.findViewById(R.id.img_confirmed_circle);
        historyDotImageView = (ImageView) actionbarView.findViewById(R.id.img_history_circle);
        filterButton = (ImageButton) actionbarView.findViewById(R.id.btn_filter);

        filterButton.setOnClickListener(this);
        pendingButton.setOnClickListener(this);
        confirmButton.setOnClickListener(this);
        historyButton.setOnClickListener(this);
    }

    private void updateAppointmentTabbar(View view) {
        pendingDotImageView.setVisibility(View.INVISIBLE);
        confirmDotImageView.setVisibility(View.INVISIBLE);
        historyDotImageView.setVisibility(View.INVISIBLE);

        pendingTextView.setTextColor(getResources().getColor(R.color.JGGGrey1));
        confirmTextView.setTextColor(getResources().getColor(R.color.JGGGrey1));
        historyTextView.setTextColor(getResources().getColor(R.color.JGGGrey1));

        if (view.getId() == R.id.pending_layout) {
            pendingTextView.setTextColor(getResources().getColor(R.color.JGGOrange));
            pendingDotImageView.setVisibility(View.VISIBLE);
        } else if (view.getId() == R.id.confirm_layout) {
            confirmTextView.setTextColor(getResources().getColor(R.color.JGGOrange));
            confirmDotImageView.setVisibility(View.VISIBLE);
        } else if (view.getId() == R.id.history_layout) {
            historyTextView.setTextColor(getResources().getColor(R.color.JGGOrange));
            historyDotImageView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btn_filter) {
            // back to previous view
            Intent intent = new Intent(view.getContext(), AppFilterActivity.class);
            mContext.startActivity(intent);
        } else {
            updateAppointmentTabbar(view);
            listener.onTabbarItemClick(view);
        }
    }

    private OnTabbarItemClickListener listener;

    public interface OnTabbarItemClickListener {
        void onTabbarItemClick(View view);
    }

    public void setTabbarItemClickListener(OnTabbarItemClickListener listener) {
        this.listener = listener;
    }
}
