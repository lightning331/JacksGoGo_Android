package com.kelvin.jacksgogo.Adapter.Services;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by PUMA on 12/19/2017.
 */

public class CategoryGridAdapter extends BaseAdapter implements View.OnClickListener {

    private Context mContext;
    private View gridViewItem;
    private ImageView categoryIcon;
    private TextView categoryTitle;
    private LinearLayout itemBackground;

    private String mType;
    private ArrayList<Map<String, Object>> category;
    private int position;
    private boolean isSelected = false;

    public CategoryGridAdapter(Context context, ArrayList<Map<String, Object>> data, String type) {
        this.mContext = context;
        this.mType = type;
        this.category = data;
    }

    @Override
    public int getCount() {
        return category.size();
    }

    @Override
    public Object getItem(int i) {
        return category.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        gridViewItem = view;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridViewItem = inflater.inflate(R.layout.cell_search_category, null);
        }
        gridViewItem.setLayoutParams(new GridView.LayoutParams(320, 315));

        categoryIcon = (ImageView) gridViewItem.findViewById(R.id.img_search_category);
        categoryTitle = (TextView) gridViewItem.findViewById(R.id.lbl_search_category);
        itemBackground = (LinearLayout) gridViewItem.findViewById(R.id.cell_background);

        position = i;
        //itemBackground.setOnClickListener(this);

        String name = category.get(i).get("name").toString();
        int iconId = (int) category.get(i).get("icon");
        categoryTitle.setText(name);
        categoryIcon.setImageResource(iconId);

        return gridViewItem;
    }

    @Override
    public void onClick(View view) {
        listener.onItemClick(view, position);
        isSelected = !isSelected;
        if (mType.equals("SERVICES")) {
            if (isSelected) itemBackground.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGGreen10Percent));
            else itemBackground.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGWhite));
        } else if (mType.equals("JOBS")) {
            if (isSelected) itemBackground.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGCyan10Percent));
            else itemBackground.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGWhite));
        } else if (mType.equals("GOCLUB")) {
            if (isSelected) itemBackground.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGPurple10Percent));
            else itemBackground.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGWhite));
        }
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
