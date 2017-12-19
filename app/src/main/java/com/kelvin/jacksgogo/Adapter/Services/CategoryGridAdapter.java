package com.kelvin.jacksgogo.Adapter.Services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.kelvin.jacksgogo.Models.Jobs_Services_Events.JGGAppBaseModel;
import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 12/19/2017.
 */

public class CategoryGridAdapter extends BaseAdapter {

    private Context mContext;
    private JGGAppBaseModel.AppointmentType mType;
    private View gridViewItem;

    String[] names = {};
    int[] icons = {};

    public CategoryGridAdapter(Context context, JGGAppBaseModel.AppointmentType type) {
        this.mContext = context;
        this.mType = type;

        addDummyData();
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int i) {
        return names[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        gridViewItem = view;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridViewItem = inflater.inflate(R.layout.cell_search_category, null);
        }
        if (mType == JGGAppBaseModel.AppointmentType.SERVICES) {
            gridViewItem.setLayoutParams(new GridView.LayoutParams(320, 315));
        } else if (mType == JGGAppBaseModel.AppointmentType.JOBS) {
            gridViewItem.setLayoutParams(new GridView.LayoutParams(320, 315));
        }

        ImageView categoryIcon = (ImageView) gridViewItem.findViewById(R.id.img_search_category);
        TextView categoryTitle = (TextView) gridViewItem.findViewById(R.id.lbl_search_category);

        categoryIcon.setImageResource(icons[i]);
        categoryTitle.setText(names[i]);

        return gridViewItem;
    }

    private void addDummyData() {
        if (mType == JGGAppBaseModel.AppointmentType.SERVICES) {
            names = new String[]{
                    "Favourited Services",
                    "Cooking & Baking",
                    "Education",
                    "Handyman",
                    "Household Chores",
                    "Messenger",
                    "Running Man",
                    "Sports",
                    "Other Professions"
            };
            icons = new int[]{
                    R.mipmap.icon_cat_favourites,
                    R.mipmap.icon_cat_cooking_baking,
                    R.mipmap.icon_cat_education,
                    R.mipmap.icon_cat_handyman,
                    R.mipmap.icon_cat_householdchores,
                    R.mipmap.icon_cat_messenger,
                    R.mipmap.icon_cat_runningman,
                    R.mipmap.icon_cat_sports,
                    R.mipmap.icon_cat_other
            };
        } else if (mType == JGGAppBaseModel.AppointmentType.JOBS) {
            names = new String[]{
                    "Quick Jobs",
                    "Favourited Services",
                    "Cooking & Baking",
                    "Education",
                    "Handyman",
                    "Household Chores",
                    "Messenger",
                    "Running Man",
                    "Sports",
                    "Other Professions"
            };
            icons = new int[]{
                    R.mipmap.icon_cat_quickjob,
                    R.mipmap.icon_cat_favourites,
                    R.mipmap.icon_cat_cooking_baking,
                    R.mipmap.icon_cat_education,
                    R.mipmap.icon_cat_handyman,
                    R.mipmap.icon_cat_householdchores,
                    R.mipmap.icon_cat_messenger,
                    R.mipmap.icon_cat_runningman,
                    R.mipmap.icon_cat_sports,
                    R.mipmap.icon_cat_other
            };
        }
    }
}
