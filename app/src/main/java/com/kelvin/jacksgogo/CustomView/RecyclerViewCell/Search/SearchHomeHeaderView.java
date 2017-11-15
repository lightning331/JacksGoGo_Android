package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Search;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

import static android.content.ContentValues.TAG;

/**
 * Created by PUMA on 11/13/2017.
 */

public class SearchHomeHeaderView extends RecyclerView.ViewHolder {

    public TextView totalServiceCount;
    public TextView newServiceCount;
    public TextView newServiceSinceDate;
    public LinearLayout viewMyServiceButton;
    public LinearLayout viewAllServiceButton;
    public LinearLayout postNewButton;

    public SearchHomeHeaderView(View itemView) {
        super(itemView);

        totalServiceCount = itemView.findViewById(R.id.lbl_total_service_count);
        newServiceCount = itemView.findViewById(R.id.lbl_new_service_count);
        newServiceSinceDate = itemView.findViewById(R.id.lbl_new_service_since_date);
        viewMyServiceButton = itemView.findViewById(R.id.btn_view_my_service);
        viewAllServiceButton = itemView.findViewById(R.id.btn_view_all);
        postNewButton = itemView.findViewById(R.id.btn_post_new);

    }

    public void setOnClickListener(View.OnClickListener listener) {
        viewMyServiceButton.setOnClickListener(listener);
        viewAllServiceButton.setOnClickListener(listener);
        postNewButton.setOnClickListener(listener);
    }

}
