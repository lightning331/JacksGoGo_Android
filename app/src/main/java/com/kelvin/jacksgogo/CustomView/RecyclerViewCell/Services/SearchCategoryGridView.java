package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.google.android.gms.vision.text.Line;
import com.kelvin.jacksgogo.Adapter.Services.CategoryGridAdapter;
import com.kelvin.jacksgogo.Models.Jobs_Services_Events.JGGAppBaseModel;
import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/14/2017.
 */

public class SearchCategoryGridView extends RecyclerView.ViewHolder {

    private Context mContext;
    private JGGAppBaseModel.AppointmentType type;

    private GridView gridView;

    public SearchCategoryGridView(View itemView, Context context, JGGAppBaseModel.AppointmentType type) {
        super(itemView);
        this.mContext = context;
        this.type = type;

        gridView = itemView.findViewById(R.id.category_grid_view);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) gridView.getLayoutParams();

        if (this.type == JGGAppBaseModel.AppointmentType.SERVICES) {
            gridView.setNumColumns(3);
        } else if (this.type == JGGAppBaseModel.AppointmentType.JOBS) {
            gridView.setNumColumns(4);
            params.height = 990;
            params.width = LinearLayout.LayoutParams.MATCH_PARENT;
        }
        gridView.setLayoutParams(params);
        CategoryGridAdapter adapter = new CategoryGridAdapter(mContext, this.type);
        gridView.setAdapter(adapter);
    }

//    public void setOnClickListener(View.OnClickListener listener) {
//        otherButton.setOnClickListener(listener);
//    }
}
