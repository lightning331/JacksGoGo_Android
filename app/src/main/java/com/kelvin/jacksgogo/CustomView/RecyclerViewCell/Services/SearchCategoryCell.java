package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kelvin.jacksgogo.Adapter.CategoryCellAdapter;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;

import java.util.ArrayList;

/**
 * Created by PUMA on 11/14/2017.
 */

public class SearchCategoryCell extends RecyclerView.ViewHolder {

    private Context mContext;
    private AppointmentType mType;

    private RecyclerView recyclerView;
    private CategoryCellAdapter adapter;
    private ArrayList<JGGCategoryModel> mCategories;

    public SearchCategoryCell(View itemView, Context context, AppointmentType type, ArrayList<JGGCategoryModel> data) {
        super(itemView);
        mContext = context;
        mType = type;
        mCategories = data;

        recyclerView = itemView.findViewById(R.id.category_recycler_view);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.VERTICAL, false));
        }
        if (mType == AppointmentType.SERVICES) {
            recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        } else if (mType == AppointmentType.JOBS) {
            recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        } else if (mType == AppointmentType.GOCLUB) {

        }

        adapter = new CategoryCellAdapter(mContext, mCategories, mType);
        adapter.setOnItemClickListener(new CategoryCellAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (mType == AppointmentType.SERVICES) {
                    if (mCategories != null) {
                        String name = mCategories.get(position).getName();
                        Toast.makeText(mContext, name,
                                Toast.LENGTH_LONG).show();
                    }
                } else if (mType == AppointmentType.JOBS) {
                    if (mCategories != null) {
                        String name = "";
                        if (position == 0)
                            name = "Quick Jobs";
                        else
                            name = mCategories.get(position - 1).getName();

                        Toast.makeText(mContext, name,
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
