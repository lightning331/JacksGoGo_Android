package com.kelvin.jacksgogo.CustomView;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.Appointment.AppointmentsFilterActivity;
import com.kelvin.jacksgogo.R;


public class AppointmentsActionbar extends RelativeLayout implements View.OnClickListener {

    Context mContext;
    LayoutInflater mLayoutInflater;
    TextView pendingTextView;
    TextView confirmTextView;
    TextView historyTextView;
    ImageView pendingDotImageView;
    ImageView confirmDotImageView;
    ImageView historyDotImageView;
    ImageButton filterButton;

    public AppointmentsActionbar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        initView();
    }

    public AppointmentsActionbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public AppointmentsActionbar(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    private void initView(){

        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View actionbarView  = mLayoutInflater.inflate(R.layout.appointments_custom_actionbar, this);

        pendingTextView = (TextView) actionbarView.findViewById(R.id.lbl_pending);
        confirmTextView = (TextView) actionbarView.findViewById(R.id.lbl_confirmed);
        historyTextView = (TextView) actionbarView.findViewById(R.id.lbl_history);
        pendingDotImageView = (ImageView) actionbarView.findViewById(R.id.img_pending_cirle);
        confirmDotImageView = (ImageView) actionbarView.findViewById(R.id.img_confirmed_circle);
        historyDotImageView = (ImageView) actionbarView.findViewById(R.id.img_history_circle);
        filterButton = (ImageButton) actionbarView.findViewById(R.id.btn_filter);

        pendingTextView.setOnClickListener(this);
        confirmTextView.setOnClickListener(this);
        historyTextView.setOnClickListener(this);
        filterButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btn_filter) {
            // go to the appointments filter view
            Intent intent = new Intent(view.getContext(), AppointmentsFilterActivity.class);
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
            } else if (view.getId() == R.id.lbl_confirmed) {
                confirmTextView.setTextColor(getResources().getColor(R.color.JGGOrange));
                confirmDotImageView.setVisibility(View.VISIBLE);
            } else if (view.getId() == R.id.lbl_history) {
                historyTextView.setTextColor(getResources().getColor(R.color.JGGOrange));
                historyDotImageView.setVisibility(View.VISIBLE);
            }
        }
    }
}
