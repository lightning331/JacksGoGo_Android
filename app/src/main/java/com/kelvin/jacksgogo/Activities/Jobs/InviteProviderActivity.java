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
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserProfileModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGInviteUsersResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGSendInviteResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.Global.createProgressDialog;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getAppointmentTime;

public class InviteProviderActivity extends AppCompatActivity {

    @BindView(R.id.app_invite_actionbar) Toolbar mToolbar;
    @BindView(R.id.invite_provider_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.img_detail) ImageView imgCategory;
    @BindView(R.id.lbl_title) TextView lblCategory;
    @BindView(R.id.lbl_date) TextView lblTime;

    private JGGActionbarView actionbarView;
    private InviteProviderAdapter adapter;
    private ProgressDialog progressDialog;

    private JGGAppointmentModel mJob;
    private JGGCategoryModel mCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_provider);
        ButterKnife.bind(this);

        // Top Navigationbar View
        actionbarView = new JGGActionbarView(this);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        actionbarView.setStatus(JGGActionbarView.EditStatus.INVITE, AppointmentType.UNKNOWN);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        mJob = JGGAppManager.getInstance().getSelectedAppointment();
        mCategory = mJob.getCategory();
        if (mCategory != null)
            setCategory();

        getInviteUsers();
    }

    private void updateRecyclerView(ArrayList<JGGUserProfileModel> users) {
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(InviteProviderActivity.this, LinearLayout.VERTICAL, false));
        }

        adapter = new InviteProviderAdapter(InviteProviderActivity.this, users);
        adapter.setOnItemClickListener(new InviteProviderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(JGGUserProfileModel u) {
                sendInvite(u);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void setCategory() {
        // Category
        Picasso.with(this)
                .load(mCategory.getImage())
                .placeholder(null)
                .into(imgCategory);
        lblCategory.setText(mCategory.getName());
        // Time
        lblTime.setText(getAppointmentTime(mJob));
    }

    private void getInviteUsers() {
        JGGUserProfileModel currentUser = JGGAppManager.getInstance().getCurrentUser();
        if (currentUser == null) return;
        progressDialog = createProgressDialog(this);
        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGInviteUsersResponse> call = apiManager.getUsersForInvite(mCategory.getID(), null, null, null, 0, 50);
        call.enqueue(new Callback<JGGInviteUsersResponse>() {
            @Override
            public void onResponse(Call<JGGInviteUsersResponse> call, Response<JGGInviteUsersResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        ArrayList<JGGUserProfileModel> users = response.body().getValue();
                        updateRecyclerView(users);
                    } else {
                        Toast.makeText(InviteProviderActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

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

    private void sendInvite(JGGUserProfileModel user) {
        progressDialog = createProgressDialog(this);
        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGSendInviteResponse> call = apiManager.sendInvite(mJob.getID(), user.getID());
        call.enqueue(new Callback<JGGSendInviteResponse>() {
            @Override
            public void onResponse(Call<JGGSendInviteResponse> call, Response<JGGSendInviteResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        String proposalID = response.body().getValue();
                        Toast.makeText(InviteProviderActivity.this, "Invitation sent!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(InviteProviderActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    int statusCode  = response.code();
                    Toast.makeText(InviteProviderActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGSendInviteResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(InviteProviderActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void actionbarViewItemClick(View view) {
        if (view.getId() == R.id.btn_back) {
            FragmentManager manager = getSupportFragmentManager();
            if (manager.getBackStackEntryCount() == 0) {
                this.finish();
            } else {
                manager.popBackStack();
            }
        } else if (view.getId() == R.id.btn_more) {
            Intent intent = new Intent(this, ServiceProviderActivity.class);
            startActivity(intent);
        }
    }
}
