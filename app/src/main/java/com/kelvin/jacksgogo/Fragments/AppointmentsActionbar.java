package com.kelvin.jacksgogo.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
public class AppointmentsActionbar extends RelativeLayout implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

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
        View actionbarView  = mLayoutInflater.inflate(R.layout.appointment_custom_actionbar, this);

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
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btn_filter) {

        } else {
            pendingDotImageView.setVisibility(View.INVISIBLE);
            confirmDotImageView.setVisibility(View.INVISIBLE);
            historyDotImageView.setVisibility(View.INVISIBLE);

            pendingTextView.setTextColor(getResources().getColor(R.color.color_icon_seg));
            confirmTextView.setTextColor(getResources().getColor(R.color.color_icon_seg));
            historyTextView.setTextColor(getResources().getColor(R.color.color_icon_seg));

            if (view.getId() == R.id.lbl_pending) {
                pendingTextView.setTextColor(getResources().getColor(R.color.color_main));
                pendingDotImageView.setVisibility(View.VISIBLE);
            } else if (view.getId() == R.id.lbl_confirmed) {
                confirmTextView.setTextColor(getResources().getColor(R.color.color_main));
                confirmDotImageView.setVisibility(View.VISIBLE);
            } else if (view.getId() == R.id.lbl_history) {
                historyTextView.setTextColor(getResources().getColor(R.color.color_main));
                historyDotImageView.setVisibility(View.VISIBLE);
            }
        }
    }
}
