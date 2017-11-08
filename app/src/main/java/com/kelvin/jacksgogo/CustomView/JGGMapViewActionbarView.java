package com.kelvin.jacksgogo.CustomView;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.view.View;

import com.kelvin.jacksgogo.Activities.Appointment.JGGMapViewActivity;
import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/7/2017.
 */

public class JGGMapViewActionbarView extends RelativeLayout implements View.OnClickListener {

    Context mContext;
    LayoutInflater mLayoutInflater;

    View toolBarView;
    LinearLayout backButton;
    private FragmentManager supportFragmentManager;

    public JGGMapViewActionbarView(Context context) {
        super(context);

        mContext = context;
        initView();
    }

    private void initView() {

        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        toolBarView = mLayoutInflater.inflate(R.layout.jgg_map_actionbar_view, this);
        backButton = (LinearLayout) toolBarView.findViewById(R.id.btn_back_map_view);

        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_back_map_view) {
            ((JGGMapViewActivity) mContext).finish();
        }
    }
}
