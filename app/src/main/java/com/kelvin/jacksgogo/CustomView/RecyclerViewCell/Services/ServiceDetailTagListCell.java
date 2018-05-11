package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kelvin.jacksgogo.R;

import java.util.ArrayList;
import java.util.Arrays;
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
    }

    public void setTagList(String tag) {
        if (tag != null && tag.length() > 0) {
            String [] strings = tag.split(",");
            List<String> list = new ArrayList<>();
            for (String str : strings) {
                if (str.contains("\n")) {
                    String[] subStrs = str.split("\n");
                    for (String substr : subStrs) {
                        list.add(substr);
                    }
                } else {
                    list.add(str);
                }
            }
            tagList.setTags(list);
        }

        Typeface typeface = Typeface.create("muliregular", Typeface.NORMAL);
        tagList.setTagTypeface(typeface);
    }
}
