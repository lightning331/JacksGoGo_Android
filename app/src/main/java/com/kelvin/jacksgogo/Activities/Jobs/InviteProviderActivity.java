package com.kelvin.jacksgogo.Activities.Jobs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.Adapter.Jobs.InviteProviderAdapter;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppBaseModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserProfileModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGInviteUsersResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.creatingAppointment;
import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.currentUser;
import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.selectedCategory;
import static com.kelvin.jacksgogo.Utils.Global.getDayMonthYear;
import static com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentBaseModel.appointmentMonthDate;

public class InviteProviderActivity extends AppCompatActivity {

    @BindView(R.id.app_invite_actionbar) Toolbar mToolbar;
    @BindView(R.id.invite_provider_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.img_detail) ImageView imgCategory;
    @BindView(R.id.lbl_title) TextView lblCategory;
    @BindView(R.id.lbl_date) TextView lblTime;

    private JGGActionbarView actionbarView;
    private InviteProviderAdapter adapter;
    private ProgressDialog progressDialog;

    private ArrayList<JGGUserProfileModel> inviteUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_provider);
        ButterKnife.bind(this);

        // Top Navigationbar View
        actionbarView = new JGGActionbarView(this);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        actionbarView.setStatus(JGGActionbarView.EditStatus.INVITE, JGGAppBaseModel.AppointmentType.UNKNOWN);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        if (selectedCategory != null && creatingAppointment != null)
            setCategory();

        getInviteUsers();
    }

    private void setCategory() {
        // Category
        Picasso.with(this)
                .load(selectedCategory.getImage())
                .placeholder(null)
                .into(imgCategory);
        lblCategory.setText(selectedCategory.getName());
        // Time
        if (creatingAppointment.getAppointmentType() == 1) {    // One-time Job
            String time = "";
            if (creatingAppointment.getSessions() != null
                    && creatingAppointment.getSessions().size() > 0) {
                if (creatingAppointment.getSessions().get(0).isSpecific()) {
                    if (creatingAppointment.getSessions().get(0).getEndOn() != null)
                        time = "on "
                                + getDayMonthYear(appointmentMonthDate(creatingAppointment.getSessions().get(0).getStartOn()))
                                + " " + Global.getTimePeriodString(appointmentMonthDate(creatingAppointment.getSessions().get(0).getStartOn()))
                                + " - "
                                + Global.getTimePeriodString(appointmentMonthDate(creatingAppointment.getSessions().get(0).getEndOn()));
                    else
                        time = "on "
                                + getDayMonthYear(appointmentMonthDate(creatingAppointment.getSessions().get(0).getStartOn()))
                                + " " + Global.getTimePeriodString(appointmentMonthDate(creatingAppointment.getSessions().get(0).getStartOn()));
                } else {
                    if (creatingAppointment.getSessions().get(0).getEndOn() != null)
                        time = "any time until "
                                + getDayMonthYear(appointmentMonthDate(creatingAppointment.getSessions().get(0).getStartOn()))
                                + " " + Global.getTimePeriodString(appointmentMonthDate(creatingAppointment.getSessions().get(0).getStartOn()))
                                + " - "
                                + Global.getTimePeriodString(appointmentMonthDate(creatingAppointment.getSessions().get(0).getEndOn()));
                    else
                        time = "any time until "
                                + getDayMonthYear(appointmentMonthDate(creatingAppointment.getSessions().get(0).getStartOn()))
                                + " " + Global.getTimePeriodString(appointmentMonthDate(creatingAppointment.getSessions().get(0).getStartOn()));
                }
            }
            lblTime.setText(time);
        } else if (creatingAppointment.getAppointmentType() == 0) {     // Repeating Job
            String time = "";
            String dayString = creatingAppointment.getRepetition();
            String[] items = new String[0];
            if (dayString != null) {
                items = dayString.split(",");
            }
            if (creatingAppointment.getRepetitionType() == Global.JGGRepetitionType.weekly) {
                for (int i = 0; i < items.length; i ++) {
                    if (time.equals(""))
                        time = "Every " + Global.getWeekName(Integer.parseInt(items[i]));
                    else
                        time = time + ", " + "Every " + Global.getWeekName(Integer.parseInt(items[i]));
                }
            } else if (creatingAppointment.getRepetitionType() == Global.JGGRepetitionType.monthly) {
                for (int i = 0; i < items.length; i ++) {
                    if (time.equals(""))
                        time = "Every " + Global.getDayName(Integer.parseInt(items[i])) + " of the month";
                    else
                        time = time + ", " + "Every " + Global.getDayName(Integer.parseInt(items[i])) + " of the month";
                }
            }
            lblTime.setText(time);
        }
    }

    private void getInviteUsers() {
        if (currentUser == null) return;
        progressDialog = Global.createProgressDialog(this);
        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGInviteUsersResponse> call = apiManager.getUsersForInvite(selectedCategory.getID().toString(),
                currentUser.getResidentialAddress_City().toString(),
                currentUser.getResidentialAddress_State().toString(),
                currentUser.getResidentialAddress_PostalCode().toString(),
                0,
                0);
        call.enqueue(new Callback<JGGInviteUsersResponse>() {
            @Override
            public void onResponse(Call<JGGInviteUsersResponse> call, Response<JGGInviteUsersResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    inviteUsers = response.body().getValue();

                    if (recyclerView != null) {
                        recyclerView.setLayoutManager(new LinearLayoutManager(InviteProviderActivity.this, LinearLayout.VERTICAL, false));
                    }

                    adapter = new InviteProviderAdapter(InviteProviderActivity.this, inviteUsers);
                    recyclerView.setAdapter(adapter);
                } else {
                    int statusCode  = response.code();
                    Toast.makeText(InviteProviderActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGInviteUsersResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(InviteProviderActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void actionbarViewItemClick(View view) {
        if (view.getId() == R.id.btn_back) {
            FragmentManager manager = getSupportFragmentManager();
            if (manager.getBackStackEntryCount() == 0) {
                super.onBackPressed();
            } else {
                manager.popBackStack();
            }
        } else if (view.getId() == R.id.btn_more) {
            Intent intent = new Intent(this, ServiceProviderActivity.class);
            startActivity(intent);
        }
    }
}
