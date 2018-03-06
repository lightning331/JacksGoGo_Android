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
    private ArrayList<JGGUserProfileModel> inviteUsers;

    public InviteProviderAdapter(Context context, ArrayList<JGGUserProfileModel> users) {
        this.mContext = context;
        this.inviteUsers = users;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inviteProviderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_app_invite_provider, parent, false);
        return new AppInviteProviderCell(mContext, inviteProviderView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AppInviteProviderCell cell = (AppInviteProviderCell) holder;
        JGGUserProfileModel user = inviteUsers.get(position);
        cell.setData(user);
    }

    @Override
    public int getItemCount() {
        return inviteUsers.size();
    }
}
