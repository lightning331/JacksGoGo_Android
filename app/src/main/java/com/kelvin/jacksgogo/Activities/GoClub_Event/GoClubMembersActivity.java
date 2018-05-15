package com.kelvin.jacksgogo.Activities.GoClub_Event;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kelvin.jacksgogo.Adapter.GoClub_Event.UserListingAdapter;
import com.kelvin.jacksgogo.Adapter.GoClub_Event.UserListingOfOwnerAdapter;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.GoClub_Event.JGGGoClubModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGGoClubUserModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserProfileModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGBaseResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGGoclubusersResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGPostAppResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.Global.EventUserType.admin;
import static com.kelvin.jacksgogo.Utils.Global.getClubAllUsers;

public class GoClubMembersActivity extends AppCompatActivity {

    @BindView(R.id.go_club_members_actionbar) Toolbar mToolbar;
    @BindView(R.id.swipeSearchContainer) SwipeRefreshLayout swipeContainer;
    @BindView(R.id.go_club_members_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.search_bar) SearchView searchView;

    private JGGActionbarView actionbarView;
    private UserListingAdapter membersAdapter;
    private UserListingOfOwnerAdapter ownerAdapter;

    private JGGGoClubModel mClub;
    private JGGUserProfileModel currentUser;
    private ArrayList<JGGGoClubUserModel> allClubUsers = new ArrayList<>();
    private String selectedUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_club_members);
        ButterKnife.bind(this);

        mClub = JGGAppManager.getInstance().getSelectedClub();
        currentUser = JGGAppManager.getInstance().getCurrentUser();

        // Top NavigationBar View
        actionbarView = new JGGActionbarView(this);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);
        actionbarView.setPurpleBackButton(R.string.title_view_members, R.string.title_empty);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                onBackPressed();
            }
        });

        // Setup refresh listener which triggers new data loading
        swipeContainer.setColorSchemeResources(R.color.JGGPurple,
                R.color.JGGPurple,
                R.color.JGGPurple,
                R.color.JGGPurple);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (mClub.getClubUsers().size() > 0) {
                                                swipeContainer.setRefreshing(false);
                                                if (mClub.getUserProfileID().equals(currentUser.getID())) {
                                                    updateOwnersAdapter(mClub.getClubUsers());
                                                } else {
                                                    updateMembersAdapter(mClub.getClubUsers());
                                                }
                                            } else
                                                getGoClubUsersByClub();
                                        }
                                    }
                );
            }
        });

        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        }
        if (mClub.getClubUsers().size() > 0) {
            swipeContainer.setRefreshing(false);
            if (mClub.getUserProfileID().equals(currentUser.getID())) {
                updateOwnersAdapter(mClub.getClubUsers());
            } else {
                updateMembersAdapter(mClub.getClubUsers());
            }
        } else
            getGoClubUsersByClub();
    }

    private void getGoClubUsersByClub() {
        swipeContainer.setRefreshing(true);
        JGGAPIManager manager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGGoclubusersResponse> call = manager.getUsersByClub(mClub.getID());
        call.enqueue(new Callback<JGGGoclubusersResponse>() {
            @Override
            public void onResponse(Call<JGGGoclubusersResponse> call, Response<JGGGoclubusersResponse> response) {
                swipeContainer.setRefreshing(false);
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        ArrayList<JGGGoClubUserModel> users = response.body().getValue();
                        mClub.setClubUsers(users);
                        JGGAppManager.getInstance().setSelectedClub(mClub);

                        if (mClub.getUserProfileID().equals(currentUser.getID())) {
                            updateOwnersAdapter(users);
                        } else {
                            updateMembersAdapter(users);
                        }

                    } else {
                        Toast.makeText(GoClubMembersActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(GoClubMembersActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGGoclubusersResponse> call, Throwable t) {
                swipeContainer.setRefreshing(false);
                Toast.makeText(GoClubMembersActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateMembersAdapter(ArrayList<JGGGoClubUserModel> users) {
        sortClubUserByUserType(users);
        membersAdapter = new UserListingAdapter(this, allClubUsers);
        recyclerView.setAdapter(membersAdapter);
    }

    private void updateOwnersAdapter(ArrayList<JGGGoClubUserModel> users) {
        allClubUsers = users;
        ownerAdapter = new UserListingOfOwnerAdapter(this, allClubUsers);
        ownerAdapter.setPendingItemClickListener(new UserListingOfOwnerAdapter.OnPendingItemClickListener() {
            @Override
            public void onItemClick(View view, String userID) {
                selectedUserID = userID;
                if (view.getId() == R.id.lbl_reviews)
                    onApproveRequest();
                else
                    onDeclineRequest();
            }
        });
        ownerAdapter.setApproveItemClickListener(new UserListingOfOwnerAdapter.OnApproveItemClickListener() {
            @Override
            public void onItemClick(View view, JGGGoClubUserModel user) {
                selectedUserID = user.getUserProfileID();
                if (user.getUserType() == admin)
                    onDemoteRequest();
                else {
                    if (view.getId() == R.id.lbl_reviews)
                        onPromoteRequest();
                    else
                        onRemove();
                }
            }
        });
        recyclerView.setAdapter(ownerAdapter);
    }

    /**
     * Owner approve user's join request
     **/
    private void onApproveRequest() {
        swipeContainer.setRefreshing(true);
        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGPostAppResponse> call = apiManager.approveJoinRequestInClub(mClub.getID(), selectedUserID);
        call.enqueue(new Callback<JGGPostAppResponse>() {
            @Override
            public void onResponse(Call<JGGPostAppResponse> call, Response<JGGPostAppResponse> response) {
                swipeContainer.setRefreshing(false);
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        // Todo - Refresh View
                        String clubUserID = response.body().getValue();
                        getGoClubUsersByClub();
                    } else {
                        Toast.makeText(GoClubMembersActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(GoClubMembersActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGPostAppResponse> call, Throwable t) {
                swipeContainer.setRefreshing(false);
                Toast.makeText(GoClubMembersActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Owner decline user's join request
     **/
    private void onDeclineRequest() {
        swipeContainer.setRefreshing(true);
        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGPostAppResponse> call = apiManager.declineClubJoinRequest(mClub.getID(), selectedUserID);
        call.enqueue(new Callback<JGGPostAppResponse>() {
            @Override
            public void onResponse(Call<JGGPostAppResponse> call, Response<JGGPostAppResponse> response) {
                swipeContainer.setRefreshing(false);
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        // Todo - Refresh View
                        String clubUserID = response.body().getValue();
                        getGoClubUsersByClub();
                    } else {
                        Toast.makeText(GoClubMembersActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(GoClubMembersActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGPostAppResponse> call, Throwable t) {
                swipeContainer.setRefreshing(false);
                Toast.makeText(GoClubMembersActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Owner demote admin to general user
     **/
    private void onDemoteRequest() {
        swipeContainer.setRefreshing(true);
        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGPostAppResponse> call = apiManager.demotAdminToUserInClub(mClub.getID(), selectedUserID);
        call.enqueue(new Callback<JGGPostAppResponse>() {
            @Override
            public void onResponse(Call<JGGPostAppResponse> call, Response<JGGPostAppResponse> response) {
                swipeContainer.setRefreshing(false);
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        // Todo - Refresh View
                        String clubUserID = response.body().getValue();
                        getGoClubUsersByClub();
                    } else {
                        Toast.makeText(GoClubMembersActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(GoClubMembersActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGPostAppResponse> call, Throwable t) {
                swipeContainer.setRefreshing(false);
                Toast.makeText(GoClubMembersActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Owner promote general user to admin
     **/
    private void onPromoteRequest() {
        swipeContainer.setRefreshing(true);
        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGPostAppResponse> call = apiManager.promotUserToAdminInClub(mClub.getID(), selectedUserID);
        call.enqueue(new Callback<JGGPostAppResponse>() {
            @Override
            public void onResponse(Call<JGGPostAppResponse> call, Response<JGGPostAppResponse> response) {
                swipeContainer.setRefreshing(false);
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        // Todo - Refresh View
                        String clubUserID = response.body().getValue();
                        getGoClubUsersByClub();
                    } else {
                        Toast.makeText(GoClubMembersActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(GoClubMembersActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGPostAppResponse> call, Throwable t) {
                swipeContainer.setRefreshing(false);
                Toast.makeText(GoClubMembersActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Owner remove general user from GoClub
     **/
    private void onRemove() {
        swipeContainer.setRefreshing(true);
        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGBaseResponse> call = apiManager.deleteUserFromClub(mClub.getID(), selectedUserID, null);
        call.enqueue(new Callback<JGGBaseResponse>() {
            @Override
            public void onResponse(Call<JGGBaseResponse> call, Response<JGGBaseResponse> response) {
                swipeContainer.setRefreshing(false);
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        // Todo - Refresh View
                        getGoClubUsersByClub();
                    } else {
                        Toast.makeText(GoClubMembersActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(GoClubMembersActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGBaseResponse> call, Throwable t) {
                swipeContainer.setRefreshing(false);
                Toast.makeText(GoClubMembersActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Todo - Sort Admin and General Users
    private void sortClubUserByUserType(ArrayList<JGGGoClubUserModel> users) {
        allClubUsers.clear();
        ArrayList<JGGGoClubUserModel> adminUsers = new ArrayList<>();
        ArrayList<JGGGoClubUserModel> generalUsers = new ArrayList<>();
        for (JGGGoClubUserModel user : users) {
            if (user.getUserType() == admin)
                adminUsers.add(user);
            else
                generalUsers.add(user);
        }
        allClubUsers = getClubAllUsers(allClubUsers);
        allClubUsers.addAll(adminUsers);
        allClubUsers.addAll(generalUsers);
    }
}
