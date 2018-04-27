package com.kelvin.jacksgogo.Adapter.GoClub_Event;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.GoClub_Event.JGGGoClubModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GoClubMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<JGGGoClubModel> mClubs;

    public GoClubMainAdapter(Context context, ArrayList<JGGGoClubModel> clubs) {
        mContext = context;
        mClubs = clubs;
    }

    public void notifyDataChanged(ArrayList<JGGGoClubModel> clubs) {
        mClubs = clubs;
        super.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_goclub_list_detail, parent, false);
        GoClubListViewHolder goClubView = new GoClubListViewHolder(view, mContext);
        return goClubView;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        GoClubListViewHolder viewHolder = (GoClubListViewHolder) holder;
        viewHolder.setGoClub(mClubs.get(position));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mClubs.size();
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class GoClubListViewHolder extends RecyclerView.ViewHolder {

        private Context mContext;
        private ImageView imgGoClub;
        private ImageView imgCategory;
        private TextView lblGoClubName;
        private TextView lblGoClubOverview;
        private TextView lblMemberCount;

        public GoClubListViewHolder(View itemView, Context context) {
            super(itemView);
            mContext = context;

            imgGoClub = itemView.findViewById(R.id.img_go_club);
            imgCategory = itemView.findViewById(R.id.img_go_club_category);
            lblGoClubName = itemView.findViewById(R.id.lbl_go_club_name);
            lblGoClubOverview = itemView.findViewById(R.id.lbl_go_club_overview);
            lblMemberCount = itemView.findViewById(R.id.lbl_go_club_member_count);
        }

        public void setGoClub(JGGGoClubModel goClub) {
            // Club Image
            if (goClub.getAttachmentURLs().size() > 0)
                Picasso.with(mContext)
                        .load(goClub.getAttachmentURLs().get(0))
                        .placeholder(R.mipmap.appointment_placeholder)
                        .into(imgGoClub);
            else
                Picasso.with(mContext)
                        .load(R.mipmap.appointment_placeholder)
                        .placeholder(R.mipmap.appointment_placeholder)
                        .into(imgGoClub);
            // Category
            Picasso.with(mContext)
                    .load(goClub.getCategory().getImage())
                    .placeholder(null)
                    .into(imgCategory);
            // Club Name
            lblGoClubName.setText(goClub.getName());
            // Club Overview
            lblGoClubOverview.setText(goClub.getDescription());
            // Members
            lblMemberCount.setText(String.valueOf(goClub.getClubUsers().size()));
        }
    }
}
