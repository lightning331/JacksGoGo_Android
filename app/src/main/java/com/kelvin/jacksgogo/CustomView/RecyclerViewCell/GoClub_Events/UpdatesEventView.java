package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.GoClub_Events;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

public class UpdatesEventView extends RecyclerView.ViewHolder {

    private Context mContext;
    private RecyclerView recyclerView;

    public UpdatesEventView(View itemView, Context context) {
        super(itemView);
        mContext = context;

        recyclerView = itemView.findViewById(R.id.updates_event_recycler_view);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.VERTICAL, false));
        }
        UpdatesEventAdapter adapter = new UpdatesEventAdapter();
        recyclerView.setAdapter(adapter);
    }

    public class UpdatesEventAdapter extends RecyclerView.Adapter {

        public UpdatesEventAdapter() {

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View eventView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_updates_event, parent, false);
            UpdatesEventViewHolder eventViewHolder = new UpdatesEventViewHolder(eventView);
            return eventViewHolder;
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

    public class UpdatesEventViewHolder extends RecyclerView.ViewHolder {

        public TextView lblTitle;
        public TextView lblDesc;

        public UpdatesEventViewHolder(View itemView) {
            super(itemView);

            lblTitle = itemView.findViewById(R.id.lbl_update_event_title);
            lblDesc = itemView.findViewById(R.id.lbl_update_event_desc);
        }
    }
}
