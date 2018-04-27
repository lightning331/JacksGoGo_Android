package com.kelvin.jacksgogo.Adapter.GoClub_Event;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserProfileModel;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by storm on 4/21/2018.
 */

public class GcSummaryAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private ArrayList<JGGUserProfileModel> mUsers;

    public GcSummaryAdapter(Context context, ArrayList<JGGUserProfileModel> users) {
        mContext = context;
        mUsers = users;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.cell_gc_summary, null, false);

        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        return new GcSummaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        GcSummaryViewHolder viewHolder = (GcSummaryViewHolder) holder;
        JGGUserProfileModel user = mUsers.get(position);
        viewHolder.setUser(user);
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class GcSummaryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_avatar) RoundedImageView img_avatar;
        @BindView(R.id.txt_name) TextView txt_name;
        @BindView(R.id.ratingbar) MaterialRatingBar ratingBar;
        @BindView(R.id.txt_role) TextView txt_role;

        public GcSummaryViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void setUser(JGGUserProfileModel user) {
            Picasso.with(mContext)
                    .load(user.getUser().getPhotoURL())
                    .placeholder(R.mipmap.icon_profile)
                    .into(img_avatar);
            if (user.getUser().getGivenName() == null)
                txt_name.setText(user.getUser().getUserName());
            else
                txt_name.setText(user.getUser().getFullName());
            Double rating = user.getUser().getRate();
            if (rating == null)
                ratingBar.setRating(0);
            else
                ratingBar.setRating(rating.floatValue());
            if (user.getID().equals(JGGAppManager.getInstance().getCurrentUser().getID()))
                txt_role.setText("Group Owner");
            else
                txt_role.setText("Admin");
        }
    }
}
