package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kelvin.jacksgogo.Adapter.JobDetailAdapter;
import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/13/2017.
 */

public class JobDetailExpanableCell extends RecyclerView.ViewHolder {

    public LinearLayout btnExpand;
    public ImageView imgExpand;

    public JobDetailExpanableCell(View itemView) {
        super(itemView);

        this.btnExpand = itemView.findViewById(R.id.btn_expand);
        this.imgExpand = itemView.findViewById(R.id.img_expand);
    }

    public void bind(final JobDetailAdapter adapter) {

        btnExpand.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                adapter.setExpandState(!adapter.isExpandState());
                if (!adapter.isExpandState()) imgExpand.setImageResource(R.mipmap.button_showless_green);
                else imgExpand.setImageResource(R.mipmap.button_showmore_green);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
