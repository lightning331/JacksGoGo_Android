package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.kelvin.jacksgogo.R;

public class HomePostAppointment extends RelativeLayout implements View.OnClickListener {

    private Context mContext;

    public LinearLayout titleLayout;
    public LinearLayout btnPostService;
    public LinearLayout btnPostJob;
    public LinearLayout btnPostEvent;

    public HomePostAppointment(Context context) {
        super(context);
        mContext = context;

        initView();
    }

    private void initView() {
        LayoutInflater mLayoutInflater      = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view                           = mLayoutInflater.inflate(R.layout.view_home_post_app, this);

        titleLayout = view.findViewById(R.id.lbl_title);
        btnPostService = view.findViewById(R.id.btn_post_service);
        btnPostJob = view.findViewById(R.id.btn_post_job);
        btnPostEvent = view.findViewById(R.id.btn_post_event);
        btnPostService.setOnClickListener(this);
        btnPostJob.setOnClickListener(this);
        //btnPostEvent.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (listener != null)
            listener.onItemClick(view);
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
