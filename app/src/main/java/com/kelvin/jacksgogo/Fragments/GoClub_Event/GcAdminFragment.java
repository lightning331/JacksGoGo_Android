package com.kelvin.jacksgogo.Fragments.GoClub_Event;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kelvin.jacksgogo.Activities.GoClub_Event.GcAddAdminActivity;
import com.kelvin.jacksgogo.Adapter.GoClub_Event.GcAddedAdminAdapter;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.GoClub_Event.JGGGoClubModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGGoClubUserModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserProfileModel;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class GcAdminFragment extends Fragment {

    @BindView(R.id.txt_admin_desc) TextView txt_admin_desc;
    @BindView(R.id.btn_sole_admin) Button btnSoleAdmin;
    @BindView(R.id.btn_others) Button btnOthers;
    @BindView(R.id.ll_admin) LinearLayout ll_admin;
    @BindView(R.id.admin_recycler_view) RecyclerView adminRecyclerView;

    private Context mContext;
    private GcAddedAdminAdapter adapter;
    private JGGGoClubModel creatingClub;
    private ArrayList<JGGUserProfileModel> invitedUsers = new ArrayList<>();
    private ArrayList<String> invitedUserIDs = new ArrayList<>();

    public GcAdminFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gc_admin, container, false);
        ButterKnife.bind(this, view);

        creatingClub = JGGAppManager.getInstance().getSelectedClub();

        if (creatingClub.getClubUsers().size() > 0) {
            ArrayList<JGGUserProfileModel> tmpUser = new ArrayList<>();
            ArrayList<String> tmpUserID = new ArrayList<>();
            for (JGGGoClubUserModel user : creatingClub.getClubUsers()) {
                tmpUser.add(user.getUserProfile());
                tmpUserID.add(user.getUserProfile().getID());
            }
            invitedUsers.addAll(tmpUser);
            invitedUserIDs.addAll(tmpUserID);
            creatingClub.setUsers(invitedUsers);
            creatingClub.setUserProfileIDs(invitedUserIDs);
            JGGAppManager.getInstance().setSelectedClub(creatingClub);
        } else {
            invitedUsers = creatingClub.getUsers();
            invitedUserIDs = creatingClub.getUserProfileIDs();
        }

        // Added Recycler View
        if (adminRecyclerView != null) {
            adminRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.VERTICAL, false));
        }
        adapter = new GcAddedAdminAdapter(mContext, invitedUsers);
        adapter.setOnItemClickListener(new GcAddedAdminAdapter.OnItemClickListener() {
            @Override
            public void onDeleteItemClick(int position) {
                ArrayList<JGGUserProfileModel> tmpUsers = invitedUsers;
                tmpUsers.remove(position);
                invitedUsers = tmpUsers;
                invitedUserIDs.remove(position);
                adapter.notifyDataChanged(invitedUsers);
            }
        });
        adminRecyclerView.setAdapter(adapter);

        updateView();

        return view;
    }

    private void updateView() {
        ll_admin.setVisibility(View.GONE);
        if (invitedUsers.size() > 0) {
            btnSoleAdmin.setVisibility(View.GONE);
            this.onYellowButtonColor(btnOthers);
            ll_admin.setVisibility(View.VISIBLE);
        } else {
            btnSoleAdmin.setVisibility(View.VISIBLE);
            this.onPurpleButtonColor(btnOthers);
            ll_admin.setVisibility(View.GONE);
        }
    }

    private void setGoClubData() {
        if (invitedUsers.size() > 0) {
            creatingClub.setSole(false);
            creatingClub.setUsers(invitedUsers);
            creatingClub.setUserProfileIDs(invitedUserIDs);

            ArrayList<JGGGoClubUserModel> clubUsers = new ArrayList<>();
            JGGGoClubUserModel clubUser = new JGGGoClubUserModel();
            for (JGGUserProfileModel user : invitedUsers) {
                clubUser.setUserProfile(user);
                clubUser.setClubID(user.getID());
                clubUser.setUserProfileID(user.getID());
                clubUser.setUserType(Global.EventUserType.admin);
                clubUser.setUserStatus(Global.EventUserStatus.approved);

                clubUsers.add(clubUser);
            }
            creatingClub.setClubUsers(clubUsers);
        } else {
            creatingClub.getClubUsers().clear();
            creatingClub.getUserProfileIDs().clear();
            creatingClub.setSole(true);
        }

        JGGAppManager.getInstance().setSelectedClub(creatingClub);

        listener.onNextButtonClick();
    }

    private void onYellowButtonColor(Button button) {
        button.setBackgroundResource(R.drawable.yellow_background);
        button.setTextColor(ContextCompat.getColor(mContext, R.color.JGGBlack));
    }

    private void onPurpleButtonColor(Button button) {
        button.setBackgroundResource(R.drawable.purple_border_background);
        button.setTextColor(ContextCompat.getColor(mContext, R.color.JGGPurple));
    }

    @OnClick(R.id.btn_sole_admin)
    public void onClickSoleAdmin() {
        invitedUserIDs.clear();
        invitedUsers.clear();
        setGoClubData();
    }

    @OnClick(R.id.btn_others)
    public void onClickOthers() {
        Intent intent = new Intent(mContext, GcAddAdminActivity.class);
        startActivityForResult(intent, Global.REQUEST_CODE);
    }

    @OnClick(R.id.btn_summary)
    public void onClickSummary() {
        setGoClubData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Global.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                String usersStr = data.getStringExtra("invitedUsers");
                String userIdStr = data.getStringExtra("invitedUserIDs");
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<JGGUserProfileModel>>() {}.getType();
                Type idType = new TypeToken<ArrayList<String>>() {}.getType();

                ArrayList<JGGUserProfileModel> users = gson.fromJson(usersStr, type);
                ArrayList<String> userIDs = gson.fromJson(userIdStr, idType);

                this.invitedUsers = users;
                this.invitedUserIDs = userIDs;
                adapter.notifyDataChanged(invitedUsers);

                updateView();
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }


    // TODO : Next Click Listener
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onNextButtonClick();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
