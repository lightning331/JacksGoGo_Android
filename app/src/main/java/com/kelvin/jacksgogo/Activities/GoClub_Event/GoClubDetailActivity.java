package com.kelvin.jacksgogo.Activities.GoClub_Event;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewBehavior;
import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewHelper;
import com.kelvin.jacksgogo.Adapter.GoClub_Event.GoClubDetailAdapter;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.CustomView.Views.JGGAlertView;
import com.kelvin.jacksgogo.CustomView.Views.JGGShareIntentDialog;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.kelvin.jacksgogo.Utils.Global.EventUserStatus;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.GoClub_Event.JGGGoClubModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGGoClubUserModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserProfileModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGBaseResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGGetGoClubResponse;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.EDIT;
import static com.kelvin.jacksgogo.Utils.Global.EDIT_STATUS;
import static com.kelvin.jacksgogo.Utils.Global.GOCLUB;
import static com.kelvin.jacksgogo.Utils.Global.createProgressDialog;

public class GoClubDetailActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    @BindView(R.id.go_club_detail_actionbar) Toolbar mToolbar;
    @BindView(R.id.swipeSearchContainer) SwipeRefreshLayout swipeContainer;
    @BindView(R.id.go_club_detail_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.btn_join_go_club) TextView btnJoinGoClub;
    @BindView(R.id.lbl_joined_count) TextView lblJoinedCount;
    @BindView(R.id.lbl_viewing_count) TextView lblViewingCount;
    @BindView(R.id.pending_layout) LinearLayout pendingLayout;
    @BindView(R.id.owner_layout) LinearLayout ownerLayout;
    @BindView(R.id.ll_viewing) LinearLayout ll_viewing;
    @BindView(R.id.lbl_user_type) TextView lblUserType;
    @BindView(R.id.lbl_user_name) TextView lblUserName;
    @BindView(R.id.img_avatar) RoundedImageView imgAvatar;

    private JGGActionbarView actionbarView;
    private BottomNavigationView mbtmView;
    private GoClubDetailAdapter adapter;
    private AlertDialog alertDialog;
    private ProgressDialog progressDialog;

    private JGGGoClubModel mClub;
    private JGGUserProfileModel groupOwner;
    private EventUserStatus joinGoClubStatus;
    private ArrayList<JGGGoClubUserModel> clubUsers;
    private JGGUserProfileModel currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_club_detail);
        ButterKnife.bind(this);

        mClub = JGGAppManager.getInstance().getSelectedClub();
        groupOwner = mClub.getUserProfile();
        clubUsers = mClub.getClubUsers();
        currentUser = JGGAppManager.getInstance().getCurrentUser();

        // Todo - Hide Bottom NavigationView and ToolBar
        mbtmView = findViewById(R.id.go_club_detail_bottom);
        mbtmView.setVisibility(View.GONE);
        btnJoinGoClub.setVisibility(View.GONE);
        pendingLayout.setVisibility(View.GONE);
        ownerLayout.setVisibility(View.GONE);

        // Todo - Top ActionbarView
        actionbarView = new JGGActionbarView(this);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);
        actionbarView.setStatus(JGGActionbarView.EditStatus.DETAILS, AppointmentType.GOCLUB);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarItemClick(view);
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
                                            getClubByID();
                                        }
                                    }
                );
            }
        });

        if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        }
        getClubByID();
    }

    private void getClubByID() {
        swipeContainer.setRefreshing(true);
        JGGAPIManager manager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGGetGoClubResponse> call = manager.getClubByID(mClub.getID());
        call.enqueue(new Callback<JGGGetGoClubResponse>() {
            @Override
            public void onResponse(Call<JGGGetGoClubResponse> call, Response<JGGGetGoClubResponse> response) {
                swipeContainer.setRefreshing(false);
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        // Todo - Set GoClub Data
                        mClub = response.body().getValue();
                        groupOwner = mClub.getUserProfile();
                        clubUsers = mClub.getClubUsers();
                        JGGAppManager.getInstance().setSelectedClub(mClub);

                        // Todo - Update Detail RecyclerView
                        updateRecyclerView();

                        // Todo - Set Bottom View
                        setJoinToGoClubStatus();

                    } else {
                        Toast.makeText(GoClubDetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(GoClubDetailActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGGetGoClubResponse> call, Throwable t) {
                swipeContainer.setRefreshing(false);
                Toast.makeText(GoClubDetailActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateRecyclerView() {
        adapter = new GoClubDetailAdapter(this);
        mRecyclerView.setAdapter(adapter);
    }

    public void setJoinToGoClubStatus() {
        if (groupOwner.getID().equals(currentUser.getID())) {
            // Current user is Owner of the Club
            setBottomNavHideStatus(EventUserStatus.approved);
        } else {
            if (clubUsers.size() > 0) {
                for (JGGGoClubUserModel clubUser : clubUsers) {
                    if (clubUser.getUserProfileID().equals(currentUser.getID())) {
                        // Current user is Admin of the Club
                        btnJoinGoClub.setVisibility(View.GONE);
                        pendingLayout.setVisibility(View.GONE);
                        ownerLayout.setVisibility(View.GONE);
                        setBottomNavHideStatus(clubUser.getUserStatus());
                        return;
                    } else {
                        // Current user is not member of the Club
                        btnJoinGoClub.setVisibility(View.GONE);
                        pendingLayout.setVisibility(View.GONE);
                        ownerLayout.setVisibility(View.GONE);
                        setBottomNavHideStatus(EventUserStatus.none);
                    }
                }
            } else
                // Current user is not member of the Club
                setBottomNavHideStatus(EventUserStatus.none);
        }
    }

    private void setBottomNavHideStatus(EventUserStatus status) {
        joinGoClubStatus = status;

        mbtmView.setVisibility(View.VISIBLE);

        BottomNavigationViewHelper.disableShiftMode(mbtmView);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mbtmView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());
        btnJoinGoClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onJoinToGoClubDialog(false);
            }
        });

        switch (status) {
            case none:
            case declined:
            case leave:
            case removed:
                btnJoinGoClub.setVisibility(View.VISIBLE);
                break;
            case requested:
                pendingLayout.setVisibility(View.VISIBLE);
                break;
            case approved:
                ownerLayout.setVisibility(View.VISIBLE);
                setGroupOwnerLayout();
                break;
            default:
                break;
        }
    }

    // TODO : Owner Layout
    private void setGroupOwnerLayout() {
        Picasso.with(this)
                .load(groupOwner.getUser().getPhotoURL())
                .placeholder(R.mipmap.icon_profile)
                .into(imgAvatar);
        if (groupOwner.getUser().getGivenName() == null)
            lblUserName.setText(groupOwner.getUser().getUserName());
        else
            lblUserName.setText(groupOwner.getUser().getFullName());
        lblUserType.setText("Group Owner");
    }

    // Todo - Send Report request
    private void onSendReport() {
        alertDialog.dismiss();
        onReportDialog(true);
    }

    // Todo - Send GoClub join request
    private void onSendJoinRequest() {
        alertDialog.dismiss();
        onJoinToGoClubDialog(true);
    }

    // Todo - Leave from GoClub
    private void onLeaveGoClub(String reason) {
        alertDialog.dismiss();
        progressDialog = createProgressDialog(this);
        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGBaseResponse> call = apiManager.leaveGoClub(mClub.getID(), currentUser.getID(),reason);
        call.enqueue(new Callback<JGGBaseResponse>() {
            @Override
            public void onResponse(Call<JGGBaseResponse> call, Response<JGGBaseResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        // Todo - Refresh View
                        getClubByID();
                    } else {
                        Toast.makeText(GoClubDetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(GoClubDetailActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGBaseResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(GoClubDetailActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Todo - Delete GoClub
    private void onDeleteGoClub(String reason) {
        alertDialog.dismiss();
        progressDialog = createProgressDialog(this);
        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGBaseResponse> call = apiManager.deleteGoClub(mClub.getID());
        call.enqueue(new Callback<JGGBaseResponse>() {
            @Override
            public void onResponse(Call<JGGBaseResponse> call, Response<JGGBaseResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        // Todo - Refresh View
                        GoClubDetailActivity.super.finish();
                    } else {
                        Toast.makeText(GoClubDetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(GoClubDetailActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGBaseResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(GoClubDetailActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void actionbarItemClick(View view) {
        if (view.getId() == R.id.btn_like_original) {
            actionbarView.setLikeButtonClicked(actionbarView.mLikeButtonSelected);
        } else if (view.getId() == R.id.btn_more) {
            actionbarView.setMoreButtonClicked(true);
            // Show Edit PopUp Menu
            showMorePopUpMenu(view);
        } else if (view.getId() == R.id.btn_back) {
            this.finish();
        }
    }

    // Todo - Edit Popup Menu
    private void showMorePopUpMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        if (groupOwner.getID().equals(currentUser.getID())) {
            // Current user is Owner of the Club
            popupMenu.inflate(R.menu.go_club_owner_share_menu);
        } else {
            // Current user is not member of the Club
            if (joinGoClubStatus == EventUserStatus.none)
                popupMenu.inflate(R.menu.go_club_share_menu);
            else if (joinGoClubStatus == EventUserStatus.approved
                    || joinGoClubStatus == EventUserStatus.requested)
                // Current user is Admin of the Club
                // or
                // Current user requested to join to the Club
                popupMenu.inflate(R.menu.go_club_joined_menu);
        }

        popupMenu.setOnDismissListener(new OnDismissListener());
        popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener());

        // Force icons to show in Custom Overflow Menu
        Object menuHelper;
        Class[] argTypes;
        try {
            Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
            fMenuHelper.setAccessible(true);
            menuHelper = fMenuHelper.get(popupMenu);
            argTypes = new Class[] { boolean.class };
            menuHelper.getClass().getDeclaredMethod("setForceShowIcon", argTypes).invoke(menuHelper, true);
        } catch (Exception e) {
            popupMenu.show();
            return;
        }
        popupMenu.show();

    }

    private class OnDismissListener implements PopupMenu.OnDismissListener {
        @Override
        public void onDismiss(PopupMenu menu) {
            actionbarView.setMoreButtonClicked(false);
        }
    }

    // Todo - Menu item selection
    private class OnMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            if (menuItem.getItemId() == R.id.menu_option_share) {
                openShareDialog();
            } else if (menuItem.getItemId() == R.id.menu_option_report_go_club) {
                onReportDialog(false);
            } else if (menuItem.getItemId() == R.id.menu_option_leave_go_club) {
                onLeaveGoClubDialog();
            } else if (menuItem.getItemId() == R.id.menu_option_edit_go_club) {
                // Todo - Edit Club
                Intent mIntent = new Intent(GoClubDetailActivity.this, CreateGoClubActivity.class);
                mIntent.putExtra(EDIT_STATUS, EDIT);
                mIntent.putExtra(APPOINTMENT_TYPE, GOCLUB);
                startActivity(mIntent);
            } else if (menuItem.getItemId() == R.id.menu_option_delete) {
                onDeleteGoClubDialog();
            }
            return true;
        }
    }

    // Todo - Report GoClub
    private void onReportDialog(final boolean reportFlag) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (this).getLayoutInflater();

        View alertView = inflater.inflate(R.layout.jgg_alert_view, null);
        builder.setView(alertView);
        alertDialog = builder.create();
        TextView cancelButton = alertView.findViewById(R.id.btn_alert_cancel);
        TextView reportButton = alertView.findViewById(R.id.btn_alert_ok);
        TextView title = alertView.findViewById(R.id.lbl_alert_titile);
        TextView description = alertView.findViewById(R.id.lbl_alert_description);

        title.setText(R.string.alert_report_go_club_title);
        description.setText(R.string.alert_report_go_club_desc);
        reportButton.setText(R.string.alert_report_service_ok);
        reportButton.setBackgroundColor(getResources().getColor(R.color.JGGPurple));
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSendReport();
            }
        });
        cancelButton.setBackgroundColor(getResources().getColor(R.color.JGGPurple10Percent));
        cancelButton.setTextColor(getResources().getColor(R.color.JGGPurple));
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        if (reportFlag) {
            alertDialog.setCanceledOnTouchOutside(false);
            cancelButton.setVisibility(View.GONE);
            title.setText(R.string.alert_report_service_thanks_title);
            description.setText(R.string.alert_report_service_thanks_desc);
            reportButton.setText(R.string.alert_done);
            reportButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
        }
        alertDialog.show();
    }

    // Todo - Request to join GoClub
    private void onJoinToGoClubDialog(final boolean reportFlag) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (this).getLayoutInflater();

        View alertView = inflater.inflate(R.layout.jgg_alert_view, null);
        builder.setView(alertView);
        alertDialog = builder.create();
        TextView cancelButton = alertView.findViewById(R.id.btn_alert_cancel);
        TextView joinButton = alertView.findViewById(R.id.btn_alert_ok);
        TextView title = alertView.findViewById(R.id.lbl_alert_titile);
        TextView description = alertView.findViewById(R.id.lbl_alert_description);

        title.setText(R.string.alert_join_go_club_title);
        description.setVisibility(View.GONE);
        joinButton.setText(R.string.alert_join);
        joinButton.setBackgroundColor(getResources().getColor(R.color.JGGPurple));
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSendJoinRequest();
            }
        });
        cancelButton.setBackgroundColor(getResources().getColor(R.color.JGGPurple10Percent));
        cancelButton.setTextColor(getResources().getColor(R.color.JGGPurple));
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        if (reportFlag) {
            alertDialog.setCanceledOnTouchOutside(false);
            description.setVisibility(View.VISIBLE);
            title.setText(R.string.alert_join_request_title);
            description.setText(R.string.alert_join_request_desc);
            cancelButton.setVisibility(View.GONE);
            joinButton.setText(R.string.alert_done);
            joinButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    setBottomNavHideStatus(EventUserStatus.approved);
                }
            });
        }
        alertDialog.show();
    }

    // Todo - Leave GoClub
    private void onLeaveGoClubDialog() {
        String userName;
        if (groupOwner.getUser().getGivenName() == null)
            userName = currentUser.getUser().getUserName();
        else
            userName = currentUser.getUser().getFullName();
        String desc = "Let " + userName + " know why you are withdrawing from the event.";
        final JGGAlertView builder = new JGGAlertView(this,
                "Withdraw From GoClub?",
                desc,
                true,
                getResources().getString(R.string.alert_cancel),
                R.color.JGGPurple,
                R.color.JGGPurple10Percent,
                getResources().getString(R.string.alert_withdraw),
                R.color.JGGRed);
        alertDialog = builder.create();
        builder.txtReason.addTextChangedListener(this);
        builder.setOnItemClickListener(new JGGAlertView.OnItemClickListener() {
            @Override
            public void onDoneButtonClick(View view) {
                if (view.getId() == R.id.btn_alert_cancel)
                    alertDialog.dismiss();
                else
                    onLeaveGoClub(builder.txtReason.getText().toString());
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    // Todo - Leave GoClub
    private void onDeleteGoClubDialog() {
        final JGGAlertView builder = new JGGAlertView(this,
                "Delete GoClub?",
                "Let members know why you are deleting the GoClub.",
                true,
                getResources().getString(R.string.alert_cancel),
                R.color.JGGPurple,
                R.color.JGGPurple10Percent,
                getResources().getString(R.string.alert_delete),
                R.color.JGGRed);
        alertDialog = builder.create();
        builder.txtReason.addTextChangedListener(this);
        builder.setOnItemClickListener(new JGGAlertView.OnItemClickListener() {
            @Override
            public void onDoneButtonClick(View view) {
                if (view.getId() == R.id.btn_alert_cancel)
                    alertDialog.dismiss();
                else
                    onDeleteGoClub(builder.txtReason.getText().toString());
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    // Todo - Share GoClub
    private void openShareDialog() {
        JGGShareIntentDialog shareDialog = new JGGShareIntentDialog.Builder(this)
                .setDialogTitle("Share with your friends")
                .setShareLink(null)
                .build();
        shareDialog.show();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
