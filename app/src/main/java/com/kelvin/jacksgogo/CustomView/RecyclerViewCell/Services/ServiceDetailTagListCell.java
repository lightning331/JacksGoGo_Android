package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kelvin.jacksgogo.R;

import java.util.ArrayList;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;

/**
 * Created by PUMA on 11/13/2017.
 */

public class ServiceDetailTagListCell extends RecyclerView.ViewHolder {

    public TagContainerLayout tagList;

    public ServiceDetailTagListCell(View itemView) {
        super(itemView);

        tagList = itemView.findViewById(R.id.original_post_tag_list);
        Typeface typeface = Typeface.create("muliregular", Typeface.NORMAL);
        tagList.setTagTypeface(typeface);
        List<String> tags = new ArrayList<String>();
        tags.add("gardening");
        tags.add("landscaping");
        tags.add("horticulture");
        tags.add("plants");
        tags.add("landscaping");
        tags.add("horticulture");
        tags.add("gardener");
        tags.add("plants");
        tags.add("landscaping");
        tags.add("horticulture");
        tags.add("gardener");
        tagList.setTags(tags);
    }
}
