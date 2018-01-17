package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kelvin.jacksgogo.Adapter.Services.CategoryGridAdapter;
import com.kelvin.jacksgogo.Utils.API.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppBaseModel;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by PUMA on 11/14/2017.
 */

public class SearchCategoryGridView extends RecyclerView.ViewHolder {

    private Context mContext;
    private JGGAppBaseModel.AppointmentType mType;

    private GridView gridView;
    private ArrayList<Map<String, Object>> datas = new ArrayList<>();

    public SearchCategoryGridView(View itemView, Context context, JGGAppBaseModel.AppointmentType type) {
        super(itemView);
        this.mContext = context;
        this.mType = type;

        gridView = itemView.findViewById(R.id.category_grid_view);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) gridView.getLayoutParams();
        if (this.mType == JGGAppBaseModel.AppointmentType.SERVICES) {
            gridView.setNumColumns(3);
        } else if (this.mType == JGGAppBaseModel.AppointmentType.JOBS) {
            gridView.setNumColumns(4);
            params.height = 990;
            params.width = LinearLayout.LayoutParams.MATCH_PARENT;
        }
        gridView.setLayoutParams(params);

        ArrayList<JGGCategoryModel> categories = JGGAppManager.getInstance(mContext).categories;
        CategoryGridAdapter adapter = new CategoryGridAdapter(mContext, categories, mType.toString());
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the GridView selected/clicked item text
                String name = datas.get(position).get("name").toString();
                Toast.makeText(mContext, name,
                        Toast.LENGTH_LONG).show();
            }
        });
        gridView.setAdapter(adapter);
    }

    private void addCategoryData() {
        if (mType == JGGAppBaseModel.AppointmentType.SERVICES) {
            datas.add(createMap("Favourited Services", R.mipmap.icon_cat_favourites));
            datas.add(createMap("Cooking & Baking", R.mipmap.icon_cat_cooking_baking));
            datas.add(createMap("Education", R.mipmap.icon_cat_education));
            datas.add(createMap("Handyman", R.mipmap.icon_cat_handyman));
            datas.add(createMap("Household Chores", R.mipmap.icon_cat_householdchores));
            datas.add(createMap("Messenger", R.mipmap.icon_cat_messenger));
            datas.add(createMap("Running Man", R.mipmap.icon_cat_runningman));
            datas.add(createMap("Sports", R.mipmap.icon_cat_sports));
            datas.add(createMap("Other Professions", R.mipmap.icon_cat_other));

        } else if (mType == JGGAppBaseModel.AppointmentType.JOBS) {
            datas.add(createMap("Quick Jobs", R.mipmap.icon_cat_quickjob));
            datas.add(createMap("Favourited Services", R.mipmap.icon_cat_favourites));
            datas.add(createMap("Cooking & Baking", R.mipmap.icon_cat_cooking_baking));
            datas.add(createMap("Education", R.mipmap.icon_cat_education));
            datas.add(createMap("Handyman", R.mipmap.icon_cat_handyman));
            datas.add(createMap("Household Chores", R.mipmap.icon_cat_householdchores));
            datas.add(createMap("Messenger", R.mipmap.icon_cat_messenger));
            datas.add(createMap("Running Man", R.mipmap.icon_cat_runningman));
            datas.add(createMap("Sports", R.mipmap.icon_cat_sports));
            datas.add(createMap("Other Professions", R.mipmap.icon_cat_other));
        }
    }

    private Map<String, Object> createMap(String name, int iconId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("icon", iconId);
        return map;
    }
}
