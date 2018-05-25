package com.kelvin.jacksgogo.Activities.GoClub_Event;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kelvin.jacksgogo.Adapter.GoClub_Event.GcAddedAdminAdapter;
import com.kelvin.jacksgogo.Adapter.GoClub_Event.GcAdminAdapter;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.GoClub_Event.JGGGoClubModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserProfileModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGInviteUsersResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GcAddAdminActivity extends AppCompatActivity {

    @BindView(R.id.admin_actionbar) Toolbar mToolbar;
    @BindView(R.id.added_recycler_view) RecyclerView addedRecyclerView;
    @BindView(R.id.user_recycler_view) RecyclerView userRecyclerView;

    @BindView(R.id.txt_search)    EditText editSearch;
    @BindView(R.id.btn_search)    ImageView imgSearch;

    private JGGActionbarView actionbarView;
    private SwipeRefreshLayout swipeContainer;

    private GcAddedAdminAdapter adapter;
    private GcAdminAdapter adminAdapter;
    private JGGGoClubModel creatingClub;
    private String categoryID;
    private ArrayList<JGGUserProfileModel> allUsers;
    private ArrayList<JGGUserProfileModel> invitedUsers;
    private ArrayList<String> invitedUserIDs;
    private ArrayList<JGGUserProfileModel> searchedUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gc_add_admin);
        ButterKnife.bind(this);

        creatingClub = JGGAppManager.getInstance().getSelectedClub();
        invitedUsers = creatingClub.getUsers();
        invitedUserIDs = creatingClub.getUserProfileIDs();
        categoryID = creatingClub.getCategoryID();

        actionbarView = new JGGActionbarView(this);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        actionbarView.setPurpleBackButton(R.string.title_create_go_club, R.string.title_empty);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        if (addedRecyclerView != null) {
            addedRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
            userRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        }

        // TODO : Init Invited user recyclerView
        updateInvitedUsersRecyclerView();

        // TODO : Add Refresh Container
        setRefreshContainer();

        // TODO : Get Users for invite to the Club
        getInviteUsers();

    }

    private void setRefreshContainer() {
        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeSearchContainer);
        swipeContainer.setColorSchemeResources(R.color.JGGPurple,
                R.color.JGGPurple,
                R.color.JGGPurple,
                R.color.JGGPurple);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                refreshFragment();
            }
        });
    }

    public void refreshFragment() {
        swipeContainer.post(new Runnable() {
                                @Override
                                public void run() {
                                    swipeContainer.setRefreshing(true);

                                    getInviteUsers();
                                }
                            }
        );
    }

    private void getInviteUsersMore(int index) {
        //add loading progress view
        allUsers.add(new JGGUserProfileModel());
        adminAdapter.notifyItemInserted(allUsers.size()-1);

        JGGUserProfileModel currentUser = JGGAppManager.getInstance().getCurrentUser();
        if (currentUser == null) return;
        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGInviteUsersResponse> call = apiManager.getUsersForInvite(categoryID, null, null, null, index, 5);
        call.enqueue(new Callback<JGGInviteUsersResponse>() {
            @Override
            public void onResponse(Call<JGGInviteUsersResponse> call, Response<JGGInviteUsersResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {

                        //remove loading view
                        allUsers.remove(allUsers.size() - 1);

                        ArrayList<JGGUserProfileModel> result = response.body().getValue();
                        if (result.size() > 0) {
                            allUsers.addAll(result);
                        } else { //result size 0 means there is no more data available at server
                            adminAdapter.setMoreDataAvailable(false);
                            //telling adapter to stop calling load more as no more server data available
                            Toast.makeText(GcAddAdminActivity.this,"No More Data Available",Toast.LENGTH_SHORT).show();
                        }
                        updateUserRecyclerView();
                    } else {
                        Toast.makeText(GcAddAdminActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(GcAddAdminActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGInviteUsersResponse> call, Throwable t) {
                Toast.makeText(GcAddAdminActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // TODO : User list for invite to the GoClub
    private void updateUserRecyclerView() {
        adminAdapter = new GcAdminAdapter(this, allUsers);
        adminAdapter.setOnItemClickListener(new GcAdminAdapter.OnItemClickListener() {
            @Override
            public void onPlusItemClick(int position, JGGUserProfileModel user) {

                // Add user to Invited Users
                invitedUsers.add(user);
                invitedUserIDs.add(user.getID());

                // Remove selected User from All Inevitable Users
                allUsers.remove(position);
                adminAdapter.notifyDataChanged(allUsers);
                updateInvitedUsersRecyclerView();

                setUserRecyclerViewLayout();
            }
        });
        adminAdapter.setLoadMoreListener(new GcAdminAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                userRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        int index = allUsers.size() - 1;
                        getInviteUsersMore(index);
                    }
                });
            }
        });
        setUserRecyclerViewLayout();
        userRecyclerView.setAdapter(adminAdapter);
    }

    private void setUserRecyclerViewLayout() {
        ViewGroup.LayoutParams params = addedRecyclerView.getLayoutParams();
        if (invitedUsers.size() >= 2) {
            params.height = 640;
        } else {
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        addedRecyclerView.setLayoutParams(params);
    }

    // TODO : Invited user list to the GoClub
    private void updateInvitedUsersRecyclerView() {
        adapter = new GcAddedAdminAdapter(this,invitedUsers);
        adapter.setOnItemClickListener(new GcAddedAdminAdapter.OnItemClickListener() {
            @Override
            public void onDeleteItemClick(int position) {

                // Add again the user to All Inevitable Users
                ArrayList<JGGUserProfileModel> removedInviteUser = new ArrayList<>();
                removedInviteUser.add(invitedUsers.get(position));
                allUsers.addAll(removedInviteUser);
                updateUserRecyclerView();

                // Remove the User from Invited Users
                invitedUsers.remove(position);
                invitedUserIDs.remove(position);
                adapter.notifyDataChanged(invitedUsers);

                setUserRecyclerViewLayout();
            }
        });
        setUserRecyclerViewLayout();
        addedRecyclerView.setAdapter(adapter);
    }


    private void getInviteUsers() {
        swipeContainer.setRefreshing(true);

        JGGUserProfileModel currentUser = JGGAppManager.getInstance().getCurrentUser();
        if (currentUser == null) return;
        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGInviteUsersResponse> call = apiManager.getUsersForInvite(null, null, null, null, 0, 50);
        call.enqueue(new Callback<JGGInviteUsersResponse>() {
            @Override
            public void onResponse(Call<JGGInviteUsersResponse> call, Response<JGGInviteUsersResponse> response) {
                swipeContainer.setRefreshing(false);
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        allUsers = response.body().getValue();
                        updateUserRecyclerView();
                    } else {
                        Toast.makeText(GcAddAdminActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(GcAddAdminActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGInviteUsersResponse> call, Throwable t) {
                swipeContainer.setRefreshing(false);
                Toast.makeText(GcAddAdminActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.btn_add_as_admin)
    public void onClickAddAsAdmin() {
        Gson gson = new Gson();
        String users = gson.toJson(invitedUsers);
        String userIDs = gson.toJson(invitedUserIDs);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("invitedUsers", users);
        returnIntent.putExtra("invitedUserIDs", userIDs);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private void actionbarViewItemClick(View view) {
        if (view.getId() == R.id.btn_back) {
            finish();
        }
    }
    private ArrayList<JGGUserProfileModel> getNotAddedUsers(ArrayList<JGGUserProfileModel> users) {
        if (invitedUsers.size() == 0)
            return allUsers;
        ArrayList<JGGUserProfileModel> tmpList = users;
        for (JGGUserProfileModel user : invitedUsers) {
            if(allUsers.indexOf(user) != -1) {
                int index = allUsers.indexOf(user);
                tmpList.remove(index);
            }
        }
        return tmpList;
    }
    @OnClick(R.id.btn_search)
    public void searchUsers() {
        String query = editSearch.getText().toString();
        if (query.equals("")) {
            adminAdapter.notifyDataChanged(getNotAddedUsers(allUsers));
        } else {
            final ArrayList<JGGUserProfileModel> filteredModelList = filter(getNotAddedUsers(allUsers), query);
            adminAdapter.notifyDataChanged(filteredModelList);
        }
    }

    private ArrayList<JGGUserProfileModel> filter(ArrayList<JGGUserProfileModel> models, String query) {
        final String lowerCaseQuery = query.toLowerCase();


        final ArrayList<JGGUserProfileModel> filteredModelList = new ArrayList<>();
        for (JGGUserProfileModel model : models) {
            String name = "";
            if (model.getUser().getGivenName() == null)
                name = model.getUser().getUserName().toLowerCase();
            else
                name = model.getUser().getFullName().toLowerCase();

            if (name.contains(lowerCaseQuery)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}
