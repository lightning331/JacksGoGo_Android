package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.GoClub_Events;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.kelvin.jacksgogo.Activities.GoClub_Event.GoClubDetailActivity;
import com.kelvin.jacksgogo.Adapter.GoClub_Event.GoClubMainAdapter;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.GoClub_Event.JGGGoClubModel;

import java.util.ArrayList;

public class GoClubRecyclerView extends RecyclerView.ViewHolder {

    private Context mContext;

    private RecyclerView recyclerView;
    private GoClubMainAdapter adapter;

    private ArrayList<JGGGoClubModel> mClubs = new ArrayList<>();
    private JGGGoClubModel mClub;

    public GoClubRecyclerView(View itemView, Context context) {
        super(itemView);
        mContext = context;

        recyclerView = itemView.findViewById(R.id.go_club_recycler_view);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.HORIZONTAL, false));
        }
        adapter = new GoClubMainAdapter(context, mClubs);
        adapter.setOnItemClickListener(new GoClubMainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                mClub = mClubs.get(position);
                JGGAppManager.getInstance().setSelectedClub(mClub);
                // TODO : GoClub detail screen
                mContext.startActivity(new Intent(mContext, GoClubDetailActivity.class));
            }
        });
        recyclerView.setAdapter(adapter);
    }

    public void setGoClubs(ArrayList<JGGGoClubModel> clubs) {
        this.mClubs = clubs;
        adapter.notifyDataChanged(mClubs);
    }
}
