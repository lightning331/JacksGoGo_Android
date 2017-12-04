package com.kelvin.jacksgogo.CustomView;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/13/2017.
 */

public class JGGAlertView extends RelativeLayout {

    Context mContext;

    public TextView title;
    public TextView description;
    public TextView cancelButton;
    public TextView okButton;

    public JGGAlertView(Context context) {
        super(context);
        this.mContext = context;

        initView();
    }

    public void initView() {
        LayoutInflater mLayoutInflater      = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View alertView                      = mLayoutInflater.inflate(R.layout.jgg_alert_view, this);
        //alertView.setLayoutParams(getLayoutParams());

        title = alertView.findViewById(R.id.lbl_alert_titile);
        description = alertView.findViewById(R.id.lbl_alert_description);
        cancelButton = alertView.findViewById(R.id.btn_alert_cancel);
        okButton = alertView.findViewById(R.id.btn_alert_ok);
    }

    public RelativeLayout.LayoutParams getLayoutParams() {
        return (new RelativeLayout.LayoutParams(dpToPx(310), dpToPx(200)));
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

}
