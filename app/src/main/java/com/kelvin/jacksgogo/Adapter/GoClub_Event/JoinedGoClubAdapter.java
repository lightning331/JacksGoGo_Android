package com.kelvin.jacksgogo.Adapter.GoClub_Event;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.GoClub_Event.GoClubDetailActivity;
import com.kelvin.jacksgogo.R;

public class JoinedGoClubAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    public JoinedGoClubAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_joined_go_clubs, parent, false);
        JoinedGoClubViewHolder joined = new JoinedGoClubViewHolder(view);
        return joined;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class JoinedGoClubViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView mRecyclerView;
        private ImageView imgCategory;
        private TextView lblCategory;

        public JoinedGoClubViewHolder(View itemView) {
            super(itemView);

            mRecyclerView = itemView.findViewById(R.id.joined_recycler_view);
            imgCategory = itemView.findViewById(R.id.img_category);
            lblCategory = itemView.findViewById(R.id.lbl_category_name);

            if (mRecyclerView != null) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.HORIZONTAL, false));
            }
            GoClubMainAdapter adapter = new GoClubMainAdapter(mContext);
            adapter.setOnItemClickListener(new GoClubMainAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    mContext.startActivity(new Intent(mContext, GoClubDetailActivity.class));
                }
            });
            mRecyclerView.setAdapter(adapter);
        }
    }
}
