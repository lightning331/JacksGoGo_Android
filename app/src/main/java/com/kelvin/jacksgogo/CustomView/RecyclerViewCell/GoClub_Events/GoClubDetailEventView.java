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

import com.google.gson.Gson;
import com.kelvin.jacksgogo.Activities.GoClub_Event.CreateGoClubActivity;
import com.kelvin.jacksgogo.Activities.GoClub_Event.EventDetailActivity;
import com.kelvin.jacksgogo.Activities.GoClub_Event.PastEventsActivity;
import com.kelvin.jacksgogo.Activities.Profile.PublickProfileActivity;
import com.kelvin.jacksgogo.Adapter.GoClub_Event.EventsListingAdapter;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.UserNameRatingCell;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.GoClub_Event.JGGEventModel;
import com.kelvin.jacksgogo.Utils.Models.GoClub_Event.JGGGoClubModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGGoClubUserModel;

import java.util.ArrayList;

import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.EDIT_STATUS;
import static com.kelvin.jacksgogo.Utils.Global.EVENTS;
import static com.kelvin.jacksgogo.Utils.Global.POST;
import static com.kelvin.jacksgogo.Utils.Global.getClubAdminUsers;

public class GoClubDetailEventView extends RecyclerView.ViewHolder {

    private Context mContext;

    private RecyclerView eventRecyclerView;
    private RecyclerView memberRecyclerView;
    public TextView btnViewPastEvents;
    public TextView btnCreateEvent;

    private JGGGoClubModel mClub;
    private ArrayList<JGGGoClubUserModel> adminUsers;

    public GoClubDetailEventView(View itemView, Context context) {
        super(itemView);
        mContext = context;
        mClub = JGGAppManager.getInstance().getSelectedClub();
        // Todo - Make ClubUser list
        adminUsers = getClubAdminUsers(mClub.getClubUsers());

        // Todo - Events
        initEventView();
        // Todo - Members
        initMembersView();
    }

    private void initEventView() {
        eventRecyclerView = itemView.findViewById(R.id.go_club_detail_event_recycler_view);
        memberRecyclerView = itemView.findViewById(R.id.go_club_detail_member_recycler_view);
        btnViewPastEvents = itemView.findViewById(R.id.btn_past_event);
        btnCreateEvent = itemView.findViewById(R.id.btn_create_event);

        if (eventRecyclerView != null) {
            eventRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.VERTICAL, false));
        }
        EventsListingAdapter adapter = new EventsListingAdapter(mContext, mClub.getEvents());
        adapter.setOnItemClickListener(new EventsListingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Gson gson = new Gson();
                JGGEventModel eventModel = mClub.getEvents().get(position);
                String jsonEvent = gson.toJson(eventModel);

                Intent intent = new Intent(mContext, EventDetailActivity.class);
                intent.putExtra("clubEventModel", jsonEvent);
                mContext.startActivity(intent);
            }
        });
        eventRecyclerView.setAdapter(adapter);
    }

    private void initMembersView() {
        // Todo - Members RecyclerView
        if (memberRecyclerView != null)
            memberRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.VERTICAL, false));
        GoClubDetailMemberAdapter memberAdapter = new GoClubDetailMemberAdapter();
        memberRecyclerView.setAdapter(memberAdapter);

        // Todo - View Posted Events
        btnViewPastEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PastEventsActivity.class);
                intent.putExtra("clubID", mClub.getID());
                mContext.startActivity(intent);
            }
        });

        // Todo - Create New Event
        String currentUserID = JGGAppManager.getInstance().getCurrentUser().getID();
        if (mClub.getUserProfileID().equals(currentUserID)) {
            btnCreateEvent.setVisibility(View.VISIBLE);
            btnCreateEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCreateNewClub();
                }
            });
        } else {
            for (JGGGoClubUserModel clubUser : adminUsers) {
                switch (clubUser.getUserType()) {
                    case admin:
                        btnCreateEvent.setVisibility(View.VISIBLE);
                        btnCreateEvent.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onCreateNewClub();
                            }
                        });
                        break;
                    case user:
                    case none:
                        btnCreateEvent.setVisibility(View.GONE);
                        break;
                }
            }
        }
    }

    private void onCreateNewClub() {
        Intent mIntent = new Intent(mContext, CreateGoClubActivity.class);
        mIntent.putExtra(EDIT_STATUS, POST);
        mIntent.putExtra(APPOINTMENT_TYPE, EVENTS);
        mContext.startActivity(mIntent);
    }

    public class GoClubDetailMemberAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View clubUserView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_user_name_rating, parent, false);
            UserNameRatingCell clubUserViewHolder = new UserNameRatingCell(mContext, clubUserView);
            return clubUserViewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            UserNameRatingCell viewHolder = (UserNameRatingCell) holder;
            final JGGGoClubUserModel userProfileModel = adminUsers.get(position);
            viewHolder.setClubAdminUser(userProfileModel);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JGGAppManager.getInstance().setCurrentUser(userProfileModel.getUserProfile());
                    Intent intent = new Intent(mContext, PublickProfileActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return adminUsers.size();
        }
    }
}
