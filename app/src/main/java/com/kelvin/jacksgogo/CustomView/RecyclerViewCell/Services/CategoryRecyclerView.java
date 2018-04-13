package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kelvin.jacksgogo.Activities.GoClub_Event.AllGoClubsActivity;
import com.kelvin.jacksgogo.Activities.Search.ActiveServiceActivity;
import com.kelvin.jacksgogo.Adapter.CategoryAdapter;
import com.kelvin.jacksgogo.CustomView.Views.SectionTitleView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;

import java.util.ArrayList;

import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.AppointmentType.GOCLUB;
import static com.kelvin.jacksgogo.Utils.Global.AppointmentType.JOBS;
import static com.kelvin.jacksgogo.Utils.Global.AppointmentType.SERVICES;
import static com.kelvin.jacksgogo.Utils.Global.EDIT_STATUS;
import static com.kelvin.jacksgogo.Utils.Global.EVENTS;
import static com.kelvin.jacksgogo.Utils.Global.POST;
import static com.kelvin.jacksgogo.Utils.JGGAppManager.selectedCategory;

/**
 * Created by PUMA on 11/14/2017.
 */

public class CategoryRecyclerView extends RecyclerView.ViewHolder {

    private Context mContext;
    private AppointmentType mType;

    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    private ArrayList<JGGCategoryModel> mCategories;

    // recommedn title view
    private SectionTitleView recommendTitleView;

    public CategoryRecyclerView(View itemView, Context context, AppointmentType type, ArrayList<JGGCategoryModel> data) {
        super(itemView);
        mContext = context;
        mType = type;
        mCategories = data;

        recommendTitleView = (SectionTitleView)itemView.findViewById(R.id.recommendTitleView);

        recyclerView = itemView.findViewById(R.id.category_recycler_view);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.VERTICAL, false));
        }
        if (mType == SERVICES) {
            recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        } else if (mType == JOBS || mType == GOCLUB || mType == AppointmentType.EVENTS) {
            recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        }

        adapter = new CategoryAdapter(mContext, mCategories, mType);
        adapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (mCategories != null) {
                    selectedCategory = mCategories.get(position);
                }
                if (mType == GOCLUB) {
                    Intent intent = new Intent(mContext, AllGoClubsActivity.class);
                    intent.putExtra("is_category", true);
                    mContext.startActivity(intent);
                } else if (mType == AppointmentType.EVENTS) {
                    Intent mIntent = new Intent(mContext, ActiveServiceActivity.class);
                    mIntent.putExtra(APPOINTMENT_TYPE, EVENTS);
                    mIntent.putExtra(EDIT_STATUS, POST);
                    mIntent.putExtra("active_status", 0);
                    mContext.startActivity(mIntent);
                } else {
                    Intent intent = new Intent(mContext, ActiveServiceActivity.class);
                    if (mType == JOBS) {
                        if (mCategories != null) {
                            String name;
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
                    intent.putExtra("active_status", 0);
                    mContext.startActivity(intent);
                }
            }
        });
        recyclerView.setAdapter(adapter);

        initRecommendView();
    }

    private void initRecommendView() {
        recommendTitleView.txtTitle.setText("Recommended For You");
        recommendTitleView.txtTitle.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
    }
}
