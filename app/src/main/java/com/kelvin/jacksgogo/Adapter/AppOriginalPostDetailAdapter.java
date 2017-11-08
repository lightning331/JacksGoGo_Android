package com.kelvin.jacksgogo.Adapter;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kelvin.jacksgogo.Fragments.Appointments.AppOriginalPostFragment;
import com.kelvin.jacksgogo.R;

import java.util.ArrayList;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by PUMA on 11/7/2017.
 */

public class AppOriginalPostDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    AppOriginalPostFragment mContext;

    int ITEM_COUNT = 11;

    public AppOriginalPostDetailAdapter(AppOriginalPostFragment context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case 0:
                View imgPageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_info_image_carousel_cell, parent, false);
                PageViewHolder pageViewHolder = new PageViewHolder(imgPageView);

                int[] array = {R.drawable.carousel03, R.drawable.carousel01,
                        R.drawable.carousel02, R.drawable.carousel03, R.drawable.carousel02, R.drawable.carousel01, R.drawable.carousel03, R.drawable.carousel02};

                pageViewHolder.imageArray = array;
                pageViewHolder.carouselView.setPageCount(array.length);
                pageViewHolder.carouselView.setImageListener(pageViewHolder.imageListener);
                return pageViewHolder;
            case 1:
                View postCategoryView = LayoutInflater.from(parent.getContext()).inflate(R.layout.original_post_detail_category_cell, parent, false);
                PostCategoryViewHolder postCategoryViewHolder = new PostCategoryViewHolder(postCategoryView);
                return postCategoryViewHolder;
            case 2:
                View priceView = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_info_description_cell, parent, false);
                PriceViewHolder priceViewHolder = new PriceViewHolder(priceView);
                priceViewHolder.descriptionImage.setImageResource(R.mipmap.icon_budget);
                priceViewHolder.description.setText("$50-$100");
                return priceViewHolder;
            case 3:
                View descriptionView = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_info_description_cell, parent, false);
                DescriptionViewHolder descriptionViewHolder = new DescriptionViewHolder(descriptionView);
                descriptionViewHolder.descriptionImage.setImageResource(R.mipmap.icon_info);
                descriptionViewHolder.description.setText("We are experts at gardening & landscaping. Please state in your quotation:size of your garden, " +
                        "what tasks you need deon, and any special requirements.");
                return descriptionViewHolder;
            case 4:
                View addressView = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_info_description_cell, parent, false);
                PostAddressViewHolder addressViewHolder = new PostAddressViewHolder(addressView);
                addressViewHolder.descriptionImage.setImageResource(R.mipmap.icon_location);
                addressViewHolder.description.setText("Smith Street, 0.4km away");
                return addressViewHolder;
            case 5:
                View timeSlotsView = LayoutInflater.from(parent.getContext()).inflate(R.layout.original_post_time_slots_cell, parent, false);
                TimeSlotsViewHolder timeSlotsViewHolder = new TimeSlotsViewHolder(timeSlotsView);
                return timeSlotsViewHolder;
            case 6:
                View reviewView = LayoutInflater.from(parent.getContext()).inflate(R.layout.original_post_total_review_cell, parent, false);
                ReviewViewHolder reviewViewHolder = new ReviewViewHolder(reviewView);
                reviewViewHolder.ratingBar.setRating((float)4.6);
                return reviewViewHolder;
            case 7:
                View posterInfoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_info_user_name_rating_cell, parent, false);
                UserInfoViewHolder posterInfoViewHolder = new UserInfoViewHolder(posterInfoView);
                posterInfoViewHolder.ratingBar.setRating((float)4.8);
                return posterInfoViewHolder;
            case 8:
                View tagListView = LayoutInflater.from(parent.getContext()).inflate(R.layout.original_post_tag_list_cell, parent, false);
                TagListViewHolder tagListViewHolder = new TagListViewHolder(tagListView);
                return tagListViewHolder;
            case 9:
                View bookedInfoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.original_post_detail_booked_info_cell, parent, false);
                BookedInfoViewHolder bookedInfoViewHolder = new BookedInfoViewHolder(bookedInfoView);
                return bookedInfoViewHolder;
            case 10:
                View quotationView = LayoutInflater.from(parent.getContext()).inflate(R.layout.full_button_cell, parent, false);
                QuotationViewHolder quotationViewHolder = new QuotationViewHolder(quotationView);
                return quotationViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return ITEM_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}

class PostCategoryViewHolder extends RecyclerView.ViewHolder {

    ImageView postImage;
    TextView postImageTitle;
    TextView title;

    public PostCategoryViewHolder(View itemView) {
        super(itemView);

        postImage = itemView.findViewById(R.id.img_original_post_title);
        postImageTitle = itemView.findViewById(R.id.lbl_original_post_image_name);
        title = itemView.findViewById(R.id.lbl_original_post_title);
    }
}

class PostAddressViewHolder extends RecyclerView.ViewHolder {

    ImageView descriptionImage;
    TextView description;

    public PostAddressViewHolder(View itemView) {
        super(itemView);

        descriptionImage = itemView.findViewById(R.id.img_description);
        description = itemView.findViewById(R.id.lbl_description);
    }
}

class TimeSlotsViewHolder extends RecyclerView.ViewHolder {

    public TimeSlotsViewHolder (View itemView) {
        super(itemView);
    }
}

class ReviewViewHolder extends RecyclerView.ViewHolder {

    MaterialRatingBar ratingBar;
    public ReviewViewHolder(View itemView) {
        super(itemView);
        ratingBar = (MaterialRatingBar) itemView.findViewById(R.id.user_ratingbar);
    }
}

class BookedInfoViewHolder extends RecyclerView.ViewHolder {

    public BookedInfoViewHolder(View itemView) {
        super(itemView);
    }
}

class TagListViewHolder extends RecyclerView.ViewHolder {

    TagContainerLayout tagList;

    public TagListViewHolder(View itemView) {
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

class QuotationViewHolder extends RecyclerView.ViewHolder {

    public QuotationViewHolder(View itemView) {
        super(itemView);
    }
}


