package com.kelvin.jacksgogo.Adapter.GoClub_Event;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

public class GoClubAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    public GoClubAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_goclub_list_detail, parent, false);
        GoClubListViewHolder goClubView = new GoClubListViewHolder(view, mContext);
        return goClubView;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GoClubListViewHolder viewHolder = (GoClubListViewHolder) holder;

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class GoClubListViewHolder extends RecyclerView.ViewHolder {

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

        public void setGoClub() {

        }
    }
}
