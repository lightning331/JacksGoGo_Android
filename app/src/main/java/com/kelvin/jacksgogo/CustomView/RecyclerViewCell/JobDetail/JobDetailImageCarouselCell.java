package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.kelvin.jacksgogo.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

/**
 * Created by PUMA on 11/13/2017.
 */

public class JobDetailImageCarouselCell extends RecyclerView.ViewHolder {

    public CarouselView carouselView;
    public int[] imageArray = {};

    public JobDetailImageCarouselCell(View itemView) {
        super(itemView);

        carouselView = itemView.findViewById(R.id.detail_images_carousel_view);
    }

    public ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(imageArray[position]);
        }
    };
}
