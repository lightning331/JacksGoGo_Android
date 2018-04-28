package com.kelvin.jacksgogo.Activities.GoClub_Event;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.CustomView.Views.JGGAlertView;
import com.kelvin.jacksgogo.Fragments.GoClub_Event.GcEventSummaryFragment;
import com.kelvin.jacksgogo.Fragments.GoClub_Event.GcSummaryFragment;
import com.kelvin.jacksgogo.Fragments.Jobs.PostJobCategoryFragment;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.kelvin.jacksgogo.Utils.Global.PostStatus;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.GoClub_Event.JGGGoClubModel;
import com.kelvin.jacksgogo.Utils.Models.System.JGGRegionModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserProfileModel;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.DUPLICATE;
import static com.kelvin.jacksgogo.Utils.Global.EDIT;
import static com.kelvin.jacksgogo.Utils.Global.EDIT_STATUS;
import static com.kelvin.jacksgogo.Utils.Global.EVENTS;
import static com.kelvin.jacksgogo.Utils.Global.GOCLUB;
import static com.kelvin.jacksgogo.Utils.Global.POST;

public class CreateGoClubActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.post_goc_club_actionbar) Toolbar mToolbar;

    private JGGActionbarView actionbarView;
    private android.app.AlertDialog alertDialog;

    private String status;
    private AppointmentType appType;
    private int titleText;
    private int alertTitle;

    private JGGUserProfileModel currentUser;
    private JGGGoClubModel goclubModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_go_club);
        ButterKnife.bind(this);

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            status = extra.getString(EDIT_STATUS);
            String type = extra.getString(APPOINTMENT_TYPE);
            if (type == null) {
                appType = AppointmentType.GOCLUB;
            } else {
                if (type.equals(EVENTS)) {
                    appType = AppointmentType.EVENTS;
                    titleText = R.string.title_create_event;
                    alertTitle = R.string.alert_quit_post_event_title;
                } else if (type.equals(GOCLUB)) {
                    appType = AppointmentType.GOCLUB;
                    titleText = R.string.title_create_go_club;
                    alertTitle = R.string.alert_quit_post_go_club_title;
                }
            }
        }

        currentUser = JGGAppManager.getInstance().getCurrentUser();

        actionbarView = new JGGActionbarView(this);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        actionbarView.setPurpleBackButton(titleText, R.string.title_empty);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        initFragment();
    }

    private void initFragment() {

        if (status.equals(POST)) {
            // Create New Goclub Model
            goclubModel = new JGGGoClubModel();
            goclubModel.setUserProfile(currentUser);
            goclubModel.setUserProfileID(currentUser.getID());
            JGGRegionModel currentRegion = JGGAppManager.getInstance().getCurrentRegion();
            goclubModel.setRegion(currentRegion);
            goclubModel.setRegionID(currentRegion.getID());

            JGGAppManager.getInstance().setSelectedClub(goclubModel);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.post_go_club_container, PostJobCategoryFragment.newInstance(appType.toString()))
                    .commit();

        } else if (status.equals(EDIT) || status.equals(DUPLICATE)) {
            switch (appType) {
                case GOCLUB:
                    actionbarView.setPurpleBackButton(R.string.title_edit_go_club, R.string.title_empty);
                    GcSummaryFragment clubSummaryFrag = new GcSummaryFragment();
                    if (status.equals(EDIT))
                        clubSummaryFrag.setEditStatus(PostStatus.EDIT);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.post_go_club_container, clubSummaryFrag)
                            .commit();
                    break;
                case EVENTS:
                    actionbarView.setPurpleBackButton(R.string.title_create_event, R.string.title_empty);
                    GcEventSummaryFragment eventSummaryFrag = new GcEventSummaryFragment();
                    if (status.equals(EDIT))
                        eventSummaryFrag.setEditStatus(PostStatus.EDIT);
                    else if (status.equals(DUPLICATE))
                        eventSummaryFrag.setEditStatus(PostStatus.DUPLICATE);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.post_go_club_container, eventSummaryFrag)
                            .commit();
                    break;
                default:
                    break;
            }
        }
    }

    private void actionbarViewItemClick(View view) {
        if (view.getId() == R.id.btn_back) {
            FragmentManager manager = getSupportFragmentManager();
            if (manager.getBackStackEntryCount() == 0) {
                showAlertDialog();
            } else {
                manager.popBackStack();
            }
        }
    }

    private void showAlertDialog() {
        JGGAlertView builder = new JGGAlertView(this,
                getResources().getString(alertTitle),
                getResources().getString(R.string.alert_quit_quotation_desc),
                false,
                getResources().getString(R.string.alert_cancel),
                R.color.JGGPurple,
                R.color.JGGPurple10Percent,
                getResources().getString(R.string.alert_quit_button),
                R.color.JGGRed);
        alertDialog = builder.create();
        builder.setOnItemClickListener(new JGGAlertView.OnItemClickListener() {
            @Override
            public void onDoneButtonClick(View view) {
                if (view.getId() == R.id.btn_alert_cancel)
                    alertDialog.dismiss();
                else {
                    alertDialog.dismiss();
                    onBackPressed();
                }
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_alert_cancel) {
            alertDialog.dismiss();
        } else if (view.getId() == R.id.btn_alert_ok) {
            alertDialog.dismiss();
            onBackPressed();
        }
    }
}
