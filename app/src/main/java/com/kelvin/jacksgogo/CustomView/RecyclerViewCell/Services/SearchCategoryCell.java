package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kelvin.jacksgogo.Activities.Search.ActiveServiceActivity;
import com.kelvin.jacksgogo.Adapter.CategoryCellAdapter;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;

import java.util.ArrayList;

import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.AppointmentType.JOBS;
import static com.kelvin.jacksgogo.Utils.Global.AppointmentType.SERVICES;
import static com.kelvin.jacksgogo.Utils.Global.EDIT_STATUS;
import static com.kelvin.jacksgogo.Utils.Global.POST;
import static com.kelvin.jacksgogo.Utils.JGGAppManager.selectedCategory;

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
        if (mType == SERVICES) {
            recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        } else if (mType == JOBS) {
            recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        } else if (mType == AppointmentType.GOCLUB) {

        }

        adapter = new CategoryCellAdapter(mContext, mCategories, mType);
        adapter.setOnItemClickListener(new CategoryCellAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(mContext, ActiveServiceActivity.class);
                if (mType == SERVICES) {
                    if (mCategories != null) {
                        selectedCategory = mCategories.get(position);
                    }
                } else if (mType == JOBS) {
                    if (mCategories != null) {
                        String name = "";
                        if (position == 0) {
                            name = "Quick Jobs";
                            Toast.makeText(mContext, name,
                                    Toast.LENGTH_LONG).show();
                        } else {
                            selectedCategory = mCategories.get(position - 1);
                        }
                    }
                }
                intent.putExtra(APPOINTMENT_TYPE, mType.toString());
                intent.putExtra(EDIT_STATUS, POST);
                intent.putExtra("is_active", false);
                mContext.startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
