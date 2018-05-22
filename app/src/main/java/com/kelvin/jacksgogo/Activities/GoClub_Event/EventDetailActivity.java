package com.kelvin.jacksgogo.Activities.GoClub_Event;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewBehavior;
import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewHelper;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.CustomView.Views.JGGAlertView;
import com.kelvin.jacksgogo.CustomView.Views.JGGShareIntentDialog;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.kelvin.jacksgogo.Utils.Global.EventUserStatus;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.GoClub_Event.JGGEventModel;
import com.kelvin.jacksgogo.Utils.Models.System.JGGAddressModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGEventUserModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGGoClubUserModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserBaseModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserProfileModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGGetEventResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGGetEventsResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGGetGoClubResponse;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;

import org.w3c.dom.Text;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.lujun.androidtagview.TagContainerLayout;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.JGGTimeManager.appointmentDate;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getDayMonthYear;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getEventTime;

public class EventDetailActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    @BindView(R.id.event_detail_actionbar)      Toolbar mToolbar;

    @BindView(R.id.lbl_original_post_title)     TextView txtEventTitle;
    @BindView(R.id.img_original_post_title)     ImageView imgCategory;
    @BindView(R.id.lbl_original_post_image_name)TextView txtCategoryName;

    @BindView(R.id.detail_images_carousel_view) CarouselView carouselView;
    @BindView(R.id.txt_event_type)              TextView txtEventType;
    @BindView(R.id.txt_event_time)              TextView txtEventTime;
    @BindView(R.id.txt_info)                    TextView txtInfo;

    @BindView(R.id.txt_location)                TextView txtLocation;
    @BindView(R.id.txt_location_description)    TextView txtLocationDescription;
    @BindView(R.id.img_location_right)          ImageView imgLocation;

    @BindView(R.id.txt_associate_group)         TextView txtAssciatedGroup;

    @BindView(R.id.img_avatar)                  RoundedImageView imgAvatar;
    @BindView(R.id.txt_user_type)               TextView txtUserType;
    @BindView(R.id.txt_username)                TextView txtUsername;
    @BindView(R.id.user_ratingbar)              MaterialRatingBar userRatingBar;

    @BindView(R.id.rl_progress)                 RelativeLayout progressLayout;
    @BindView(R.id.btn_view_attendees)          Button btnViewAttendees;
    @BindView(R.id.txt_joined_count)            TextView txtJoinedCount;

    @BindView(R.id.updates_event_recycler_view) RecyclerView updateEvetnRecyclerView;
    @BindView(R.id.original_post_tag_list)      TagContainerLayout tagContainerLayout;

    @BindView(R.id.txt_response)                TextView txtResponse;
    @BindView(R.id.txt_latest_response)         TextView txtLatestResponse;

    @BindView(R.id.lbl_reference_no_title)      TextView txtReferenceTitle;
    @BindView(R.id.lbl_reference_no)            TextView txtReferenceNo;
    @BindView(R.id.lbl_posted_on)               TextView txtCreatedTitle;
    @BindView(R.id.lbl_posted_time)             TextView txtCreatedTime;
    // bottom view
    @BindView(R.id.btn_join_event)              TextView btnJoinGoClub;
    @BindView(R.id.lbl_joined_count)            TextView lblJoinedCount;
    @BindView(R.id.lbl_viewing_count)           TextView lblViewingCount;
    @BindView(R.id.owner_layout)                LinearLayout ownerLayout;

    private JGGActionbarView actionbarView;
    private BottomNavigationView mbtmView;
    private AlertDialog alertDialog;

    private EventUserStatus joinGoClubStatus;
    private JGGEventModel eventModel;
    private JGGUserProfileModel currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        ButterKnife.bind(this);

        // Get Event Model
        Gson gson = new Gson();
        String jsonEvent = getIntent().getStringExtra("clubEventModel");
        this.eventModel = gson.fromJson(jsonEvent, JGGEventModel.class);

        currentUser = JGGAppManager.getInstance().getCurrentUser();

        // Hide Bottom NavigationView and ToolBar
        mbtmView = findViewById(R.id.event_detail_bottom);
        BottomNavigationViewHelper.disableShiftMode(mbtmView);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mbtmView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());
        btnJoinGoClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onShowJoinDialog();
            }
        });

        // Top ActionbarView
        actionbarView = new JGGActionbarView(this);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);
        actionbarView.setStatus(JGGActionbarView.EditStatus.DETAILS, AppointmentType.EVENTS);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarItemClick(view);
            }
        });

        /*if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        }
        EventDetailAdapter adapter = new EventDetailAdapter(this);
        mRecyclerView.setAdapter(adapter);*/
        initView();

        getEventDetail(this.eventModel.getID());
    }

    private void getEventDetail(final String eventId) {
        JGGAPIManager manager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGGetEventResponse> call = manager.getEventsByID(eventId);
        call.enqueue(new Callback<JGGGetEventResponse>() {
            @Override
            public void onResponse(Call<JGGGetEventResponse> call, Response<JGGGetEventResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        // Todo - Set Events Data
                        eventModel = response.body().getValue();
                        getClubByID(eventModel.getClubID());
                    } else {
                        Toast.makeText(EventDetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EventDetailActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGGetEventResponse> call, Throwable t) {
                Toast.makeText(EventDetailActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Get GoClub detail by ClubID
     */
    private void getClubByID(String clubId) {
        JGGAPIManager manager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGGetGoClubResponse> call = manager.getClubByID(clubId);
        call.enqueue(new Callback<JGGGetGoClubResponse>() {
            @Override
            public void onResponse(Call<JGGGetGoClubResponse> call, Response<JGGGetGoClubResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        // Todo - Set GoClub Data
                        eventModel.setClub(response.body().getValue());

                        updateUI(eventModel);
                        // Todo - Set Bottom View
                        showJoinToGoClubStatus();

                    } else {
                        //swipeContainer.setRefreshing(false);
                        Toast.makeText(EventDetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EventDetailActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGGetGoClubResponse> call, Throwable t) {
                Toast.makeText(EventDetailActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        userRatingBar.setVisibility(View.GONE);
        txtReferenceTitle.setText("Event reference no:");
        txtCreatedTitle.setText("Created on");
    }

    private void setTagList(String tag) {
        if (tag != null && tag.length() > 0) {
            String [] strings = tag.split(",");
            List<String> list = new ArrayList<>();
            for (String str : strings) {
                if (str.contains("\n")) {
                    String[] subStrs = str.split("\n");
                    for (String substr : subStrs) {
                        list.add(substr);
                    }
                } else {
                    list.add(str);
                }
            }
            tagContainerLayout.setTags(list);
        }

        Typeface typeface = Typeface.create("muliregular", Typeface.NORMAL);
        tagContainerLayout.setTagTypeface(typeface);
    }

    private void updateUI(JGGEventModel event) {
        txtEventTitle.setText(event.getTitle());
        Picasso.with(this)
                .load(event.getCategory().getImage())
                .placeholder(null)
                .into(imgCategory);
        txtCategoryName.setText(event.getCategory().getName());

        if (event.getOnetime()) {
            txtEventType.setText("One-time Event");
        } else {
            txtEventType.setText("Repeating-time Event");
        }
        // Event Time
        if (event.getSessions() != null && event.getSessions().size() > 0) {
            txtEventTime.setText(getEventTime(event));
        }

        txtInfo.setText(event.getDescription());

        if (event.getAddress() != null && event.getAddress().size() > 0) {
            JGGAddressModel addressModel = event.getAddress().get(0);
            txtLocation.setText(addressModel.getPlaceName());
            txtLocationDescription.setText(addressModel.getAddress());
        }

        txtAssciatedGroup.setText("No associated GoClub");

        if (event.getClub() != null) {
            JGGUserBaseModel userBaseModel = eventModel.getClub().getUserProfile().getUser();
            String avatarURL = userBaseModel.getPhotoURL();
            if (avatarURL != null) {
                Picasso.with(this)
                        .load(avatarURL)
                        .placeholder(null)
                        .into(imgAvatar);
            }
            txtUserType.setText("Organiser");
            txtUsername.setText(userBaseModel.getFullName());

            Integer totalCount = event.getClub().getLimit();
            Integer joindCount = event.getClub().getClubUsers().size();
            txtJoinedCount.setText(joindCount + "/" + totalCount + " people have joined!");
        }

        this.setTagList(event.getTags());
        txtReferenceNo.setText(event.getID());

        // TODO - Need to fix server time response
        String created = getDayMonthYear(appointmentDate(event.getCreatedOn()));
        txtCreatedTime.setText(created);
    }

    public void showJoinToGoClubStatus() {
        mbtmView.setVisibility(View.VISIBLE);
        btnJoinGoClub.setVisibility(View.VISIBLE);
        ownerLayout.setVisibility(View.GONE);
        btnViewAttendees.setVisibility(View.GONE);

        String clubOwnerId = eventModel.getClub().getUserProfile().getID();
        for (JGGEventUserModel userModel : this.eventModel.getJoinedUsers()) {
            if (userModel.getUserProfileID().equals(currentUser.getUserID())) {
                // Already joined
                mbtmView.setVisibility(View.GONE);
                btnViewAttendees.setVisibility(View.VISIBLE);
            }
        }
        if (clubOwnerId.equals(currentUser.getID())) {
            // Current user is Owner of the Club
            mbtmView.setVisibility(View.GONE);
            ownerLayout.setVisibility(View.VISIBLE);
            btnViewAttendees.setVisibility(View.VISIBLE);
        }
    }


    // Todo - Send Report request
    private void onSendReport() {
        alertDialog.dismiss();
        onShowReportDialog(true);
    }

    // Todo - Send join request to the Event
    private void onSendJoinRequest() {
        alertDialog.dismiss();
    }

    // Todo - Withdraw from Event
    private void onWithdrawFromEvent() {
        alertDialog.dismiss();
    }

    private void actionbarItemClick(View view) {
        if (view.getId() == R.id.btn_like_original) {
            actionbarView.setLikeButtonClicked(actionbarView.mLikeButtonSelected);
        } else if (view.getId() == R.id.btn_more) {
            actionbarView.setMoreButtonClicked(true);
            // Show Edit PopUp Menu
            showEditPopUpMenu(view);
        } else if (view.getId() == R.id.btn_back) {
            this.finish();
        }
    }

    private void showEditPopUpMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        if (joinGoClubStatus == EventUserStatus.none)
            popupMenu.inflate(R.menu.event_share_menu);
        else if (joinGoClubStatus == EventUserStatus.approved)
            popupMenu.inflate(R.menu.event_joined_menu);

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

    private class OnMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            if (menuItem.getItemId() == R.id.menu_option_share) {  // Share Event
                openShareDialog();
            } else if (menuItem.getItemId() == R.id.menu_option_leave_event) {    // Withdraw Event
                onShowWithdrawEventDialog();
            } else if (menuItem.getItemId() == R.id.menu_option_report_event) {    // Report Event
                onShowReportDialog(false);
            }
            return true;
        }
    }

    private void onShowReportDialog(final boolean reportFlag) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (this).getLayoutInflater();

        final View alertView = inflater.inflate(R.layout.jgg_alert_view, null);
        builder.setView(alertView);
        alertDialog = builder.create();
        TextView cancelButton = alertView.findViewById(R.id.btn_alert_cancel);
        TextView reportButton = alertView.findViewById(R.id.btn_alert_ok);
        TextView title = alertView.findViewById(R.id.lbl_alert_titile);
        TextView description = alertView.findViewById(R.id.lbl_alert_description);

        title.setText(R.string.alert_report_event_title);
        description.setText(R.string.alert_report_event_desc);
        reportButton.setText(R.string.alert_report_service_ok);
        reportButton.setBackgroundColor(getResources().getColor(R.color.JGGPurple));
        cancelButton.setBackgroundColor(getResources().getColor(R.color.JGGPurple10Percent));
        cancelButton.setTextColor(getResources().getColor(R.color.JGGPurple));
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reportFlag)
                    alertDialog.dismiss();
                else
                    onSendReport();
            }
        });
        if (reportFlag) {
            alertDialog.setCanceledOnTouchOutside(false);
            cancelButton.setVisibility(View.GONE);
            title.setText(R.string.alert_report_service_thanks_title);
            description.setText(R.string.alert_report_service_thanks_desc);
            reportButton.setText(R.string.alert_done);
        }
        cancelButton.setOnClickListener(this);
        alertDialog.show();
    }

    private void onShowJoinDialog() {
        final JGGAlertView builder = new JGGAlertView(this,
                "Join This Events?",
                "",
                false,
                getResources().getString(R.string.alert_cancel),
                R.color.JGGPurple,
                R.color.JGGPurple10Percent,
                getResources().getString(R.string.alert_join),
                R.color.JGGPurple);
        alertDialog = builder.create();
        builder.setOnItemClickListener(new JGGAlertView.OnItemClickListener() {
            @Override
            public void onDoneButtonClick(View view) {
                if (view.getId() == R.id.btn_alert_cancel)
                    alertDialog.dismiss();
                else {
                    onSendJoinRequest();
                }
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    // Todo - Leave GoClub
    private void onShowWithdrawEventDialog() {
        final JGGAlertView builder = new JGGAlertView(this,
                "Withdraw From Event?",
                "Let Clarence.Tan know why you are withdrawing from the event.",
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
                    onWithdrawFromEvent();
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    private void openShareDialog() {
        JGGShareIntentDialog shareDialog = new JGGShareIntentDialog.Builder(this)
                .setDialogTitle("Share with your friends")
                .setShareLink(null)
                .build();
        shareDialog.show();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_alert_cancel) {
            alertDialog.dismiss();
        }
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
