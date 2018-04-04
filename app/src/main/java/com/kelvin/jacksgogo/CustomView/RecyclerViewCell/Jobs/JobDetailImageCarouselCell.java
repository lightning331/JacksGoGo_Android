package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.kelvin.jacksgogo.R;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;

/**
 * Created by PUMA on 11/13/2017.
 */

public class JobDetailImageCarouselCell extends RecyclerView.ViewHolder {

    private Context mContext;
    public CarouselView carouselView;
    public ArrayList<String> imageArray = new ArrayList<>();

    public JobDetailImageCarouselCell(View itemView, Context context) {
        super(itemView);
        mContext = context;

        carouselView = itemView.findViewById(R.id.detail_images_carousel_view);
    }

    public ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            Picasso.with(mContext)
                    .load(imageArray.get(position))
                    .placeholder(R.mipmap.appointment_placeholder)
                    .into(imageView);
        }
    };
}
