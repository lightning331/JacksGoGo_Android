package com.kelvin.jacksgogo.Adapter.Jobs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Appointment.AppInviteProviderCell;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserProfileModel;

import java.util.ArrayList;

/**
 * Created by PUMA on 12/13/2017.
 */

public class InviteProviderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<JGGUserProfileModel> users;
    private ArrayList<JGGUserProfileModel> invitedUsers = new ArrayList<>();

    public InviteProviderAdapter(Context context, ArrayList<JGGUserProfileModel> users) {
        this.mContext = context;
        this.users = users;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inviteProviderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_app_invite_provider, parent, false);
        return new AppInviteProviderCell(mContext, inviteProviderView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AppInviteProviderCell cell = (AppInviteProviderCell) holder;
        final JGGUserProfileModel user = users.get(position);
        cell.setUser(user);
        cell.disableInviteButton(false);
        cell.btnInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(user);
                invitedUsers.add(user);
                notifyDataSetChanged();
            }
        });

        for (JGGUserProfileModel u: invitedUsers) {
            if (user.getID() == u.getID()) {
                cell.disableInviteButton(true);
            }
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(JGGUserProfileModel user);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
