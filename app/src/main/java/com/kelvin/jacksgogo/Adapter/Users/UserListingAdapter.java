package com.kelvin.jacksgogo.Adapter.Users;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.UserNameRatingCell;
import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 12/19/2017.
 */

public class UserListingAdapter extends RecyclerView.Adapter {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View biderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_user_name_rating, parent, false);
        UserNameRatingCell userCell = new UserNameRatingCell(biderView);
        userCell.likeButtonLayout.setVisibility(View.VISIBLE);
        userCell.ratingBar.setRating((float)4.8);
        return userCell;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 9;
    }
}
