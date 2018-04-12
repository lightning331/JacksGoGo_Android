package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.GoClub_Events;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.GoClub_Event.EventDetailActivity;
import com.kelvin.jacksgogo.Adapter.GoClub_Event.EventsListingAdapter;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Appointment.AppInviteProviderCell;
import com.kelvin.jacksgogo.R;

public class GoClubDetailEventView extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Context mContext;

    private RecyclerView eventRecyclerView;
    private RecyclerView memberRecyclerView;
    public TextView btnViewPastEvents;
    public TextView btnCreateEvent;

    public GoClubDetailEventView(View itemView, Context context) {
        super(itemView);
        mContext = context;

        // Todo - Events
        eventRecyclerView = itemView.findViewById(R.id.go_club_detail_event_recycler_view);
        memberRecyclerView = itemView.findViewById(R.id.go_club_detail_member_recycler_view);
        btnViewPastEvents = itemView.findViewById(R.id.btn_past_event);
        btnCreateEvent = itemView.findViewById(R.id.btn_create_event);

        if (eventRecyclerView != null) {
            eventRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.VERTICAL, false));
        }
        EventsListingAdapter adapter = new EventsListingAdapter(mContext);
        adapter.setOnItemClickListener(new EventsListingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick() {
                mContext.startActivity(new Intent(mContext, EventDetailActivity.class));
            }
        });
        eventRecyclerView.setAdapter(adapter);

        // Todo - Members
        if (memberRecyclerView != null)
            memberRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.VERTICAL, false));
        GoClubDetailMemberAdapter memberAdapter = new GoClubDetailMemberAdapter();
        memberRecyclerView.setAdapter(memberAdapter);
    }

    @Override
    public void onClick(View view) {

    }

    public class GoClubDetailMemberAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View posterView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_app_invite_provider, parent, false);
            AppInviteProviderCell posterViewHolder = new AppInviteProviderCell(mContext, posterView);
            posterViewHolder.lblUserType.setVisibility(View.VISIBLE);
            posterViewHolder.btnInvite.setVisibility(View.GONE);
            posterViewHolder.ratingBar.setVisibility(View.GONE);
            return posterViewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}
