package com.kelvin.jacksgogo.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by PUMA on 11/13/2017.
 */

public class JGGAlertView extends RelativeLayout implements View.OnClickListener {

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

    }

    @Override
    public void onClick(View view) {

    }
}
