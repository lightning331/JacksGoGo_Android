package com.kelvin.jacksgogo.Adapter.GoClub_Event;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.UserNameRatingCell;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.User.JGGGoClubUserModel;

import java.util.ArrayList;

/**
 * Created by PUMA on 12/19/2017.
 */

public class UserListingAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private ArrayList<JGGGoClubUserModel> mClubUsers;

    public UserListingAdapter(Context context, ArrayList<JGGGoClubUserModel> clubUsers) {
        this.mContext = context;
        mClubUsers = clubUsers;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View clubUserView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_user_name_rating, parent, false);
        UserNameRatingCell clubUserViewHolder = new UserNameRatingCell(mContext, clubUserView);
        return clubUserViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        UserNameRatingCell viewHolder = (UserNameRatingCell) holder;
        JGGGoClubUserModel userProfileModel = mClubUsers.get(position);
        viewHolder.setClubAllUser(userProfileModel);
    }

    @Override
    public int getItemCount() {
        return mClubUsers.size();
    }
}
