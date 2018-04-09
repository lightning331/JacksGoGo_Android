package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.GoClub_Events;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.kelvin.jacksgogo.Adapter.GoClub_Event.GoClubAdapter;
import com.kelvin.jacksgogo.R;

public class GoClubRecyclerView extends RecyclerView.ViewHolder {

    private Context mContext;

    private RecyclerView recyclerView;

    public GoClubRecyclerView(View itemView, Context context) {
        super(itemView);
        mContext = context;

        recyclerView = itemView.findViewById(R.id.go_club_recycler_view);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.HORIZONTAL, false));
        }
        GoClubAdapter adapter = new GoClubAdapter(context);
        recyclerView.setAdapter(adapter);
    }
}
