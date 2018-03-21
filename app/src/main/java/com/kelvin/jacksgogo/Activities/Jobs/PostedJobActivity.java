package com.kelvin.jacksgogo.Activities.Jobs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.Activities.MainActivity;
import com.kelvin.jacksgogo.Activities.Search.PostServiceActivity;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.CustomView.Views.JGGShareIntentDialog;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global.JGGRepetitionType;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGBaseResponse;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.lujun.androidtagview.TagContainerLayout;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.selectedAppointment;
import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.selectedCategory;
import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.DUPLICATE;
import static com.kelvin.jacksgogo.Utils.Global.EDIT;
import static com.kelvin.jacksgogo.Utils.Global.EDIT_STATUS;
import static com.kelvin.jacksgogo.Utils.Global.JOBS;
import static com.kelvin.jacksgogo.Utils.Global.createProgressDialog;
import static com.kelvin.jacksgogo.Utils.Global.reportTypeName;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.appointmentMonthDate;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.convertJobBudgetString;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getDayMonthYear;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getDayName;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getTimePeriodString;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getWeekName;

public class PostedJobActivity extends AppCompatActivity {

    @BindView(R.id.posted_job_actionbar) Toolbar mToolbar;
    @BindView(R.id.posted_service_tag_list) TagContainerLayout tagList;
    @BindView(R.id.posted_job_viewd_layout) LinearLayout viewedLayout;
    @BindView(R.id.img_posted_job_category) ImageView imgCategory;
    @BindView(R.id.lbl_posted_job_category_name) TextView lblCategory;
    @BindView(R.id.lbl_posted_job_title) TextView lblTitle;
    @BindView(R.id.lbl_posted_job_time_type) TextView lblType;
    @BindView(R.id.lbl_posted_job_time) TextView lblTime;
    @BindView(R.id.lbl_posted_job_description) TextView lblDescription;
    @BindView(R.id.lbl_posted_job_address) TextView lblAddress;
    @BindView(R.id.lbl_posted_job_budget) TextView lblBudget;
    @BindView(R.id.lbl_posted_job_report_type) TextView lblReportType;
    @BindView(R.id.img_posted_job_user_avatar) RoundedImageView imgAvatar;
    @BindView(R.id.lbl_posted_job_username) TextView lblUserName;
    @BindView(R.id.posted_job_user_rating) MaterialRatingBar ratingBar;

    private JGGActionbarView actionbarView;
    private ProgressDialog progressDialog;
    private JGGAppointmentModel mJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posted_job);

        ButterKnife.bind(this);

        // Top Navigationbar View
        actionbarView = new JGGActionbarView(this);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        actionbarView.setStatus(JGGActionbarView.EditStatus.POSTED, AppointmentType.JOBS);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        mJob = selectedAppointment;
        if (mJob != null)
            setData();
    }

    private void setData() {
        // Category
        Picasso.with(this)
                .load(selectedAppointment.getCategory().getImage())
                .placeholder(null)
                .into(imgCategory);
        lblCategory.setText(selectedAppointment.getCategory().getName());
        lblTitle.setText(mJob.getTitle());
        // Time
        String time = "";
        String type = "";
        if (mJob.getAppointmentType() == 1) {    // One-time
            if (mJob.getSessions() != null
                    && mJob.getSessions().size() > 0) {
                if (mJob.getSessions().get(0).isSpecific()) {
                    type = "on";
                    if (mJob.getSessions().get(0).getEndOn() != null)
                        time = getDayMonthYear(appointmentMonthDate(mJob.getSessions().get(0).getStartOn()))
                                + " " + getTimePeriodString(appointmentMonthDate(mJob.getSessions().get(0).getStartOn()))
                                + " - "
                                + getTimePeriodString(appointmentMonthDate(mJob.getSessions().get(0).getEndOn()));
                    else
                        time = getDayMonthYear(appointmentMonthDate(mJob.getSessions().get(0).getStartOn()))
                                + " " + getTimePeriodString(appointmentMonthDate(mJob.getSessions().get(0).getStartOn()));
                } else {
                    type = "any time until";
                    if (mJob.getSessions().get(0).getEndOn() != null)
                        time = getDayMonthYear(appointmentMonthDate(mJob.getSessions().get(0).getStartOn()))
                                + " " + getTimePeriodString(appointmentMonthDate(mJob.getSessions().get(0).getStartOn()))
                                + " - "
                                + getTimePeriodString(appointmentMonthDate(mJob.getSessions().get(0).getEndOn()));
                    else
                        time = getDayMonthYear(appointmentMonthDate(mJob.getSessions().get(0).getStartOn()))
                                + " " + getTimePeriodString(appointmentMonthDate(mJob.getSessions().get(0).getStartOn()));
                }
            }
            lblType.setText(type);
            lblTime.setText(time);
        } else if (mJob.getAppointmentType() == 0) {     // Repeating
            String dayString = mJob.getRepetition();
            if (dayString != null && dayString.length() > 0) {
                String [] items;
                items = dayString.split(",");
                if (mJob.getRepetitionType() == JGGRepetitionType.weekly) {
                    for (int i = 0; i < items.length; i ++) {
                        if (time.equals(""))
                            time = "Every " + getWeekName(Integer.parseInt(items[i]));
                        else
                            time = time + ", " + "Every " + getWeekName(Integer.parseInt(items[i]));
                    }
                } else if (mJob.getRepetitionType() == JGGRepetitionType.monthly) {
                    for (int i = 0; i < items.length; i ++) {
                        if (time.equals(""))
                            time = "Every " + getDayName(Integer.parseInt(items[i])) + " of the month";
                        else
                            time = time + ", " + "Every " + getDayName(Integer.parseInt(items[i])) + " of the month";
                    }
                }
            }
            lblType.setText(time);
            lblTime.setVisibility(View.GONE);
        }
        // Description
        lblDescription.setText(mJob.getDescription());
        // Address
        lblAddress.setText(mJob.getAddress().getFullAddress());
        // Price
        lblBudget.setText(convertJobBudgetString(mJob));
        // Report
        lblReportType.setText(reportTypeName(mJob.getReportType()));
        // User
        Picasso.with(this)
                .load(mJob.getUserProfile().getUser().getPhotoURL())
                .placeholder(R.mipmap.icon_profile)
                .into(imgAvatar);
        lblUserName.setText(mJob.getUserProfile().getUser().getFullName());
        ratingBar.setRating(mJob.getUserProfile().getUser().getRate().floatValue());
        // Tag View
        String tags = mJob.getTags();
        if (tags != null && tags.length() > 0) {
            String [] strings = tags.split(",");
            tagList.setTags(Arrays.asList(strings));
        }
    }

    private void actionbarViewItemClick(View view) {
        if (view.getId() == R.id.btn_back) {
            onBackClick();
        } else if (view.getId() == R.id.btn_more) {
            actionbarView.setMoreButtonClicked(true);
            onMorePopUpMenu(view);
        }
    }

    private void onBackClick() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void openShareDialog() {
        JGGShareIntentDialog dialogShare = new JGGShareIntentDialog.Builder(this)
                .setDialogTitle("Share with your friends")
                .setShareLink(null)
                .build();
        dialogShare.show();
    }

    private void onMorePopUpMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.posted_job_menu);
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

            if (menuItem.getItemId() == R.id.posted_job_menu_share) {  // Share the Service
                openShareDialog();
            } else if (menuItem.getItemId() == R.id.posted_job_menu_edit) {    // Edit Service
                Intent intent = new Intent(PostedJobActivity.this, PostServiceActivity.class);
                intent.putExtra(EDIT_STATUS, EDIT);
                intent.putExtra(APPOINTMENT_TYPE, JOBS);
                startActivity(intent);
            } else if (menuItem.getItemId() == R.id.posted_job_menu_duplicate) {    // Duplicate Service
                Intent intent = new Intent(PostedJobActivity.this, PostServiceActivity.class);
                intent.putExtra(EDIT_STATUS, DUPLICATE);
                intent.putExtra(APPOINTMENT_TYPE, JOBS);
                startActivity(intent);
            } else if (menuItem.getItemId() == R.id.posted_job_menu_delete) {    // Delete Service
                onDeleteButtonClick();
            }
            return true;
        }
    }

    private void onDeleteButtonClick() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (this).getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.jgg_alert_view, null);
        builder.setView(dialogView);
        TextView cancelButton = (TextView) dialogView.findViewById(R.id.btn_alert_cancel);
        cancelButton.setBackgroundColor(ContextCompat.getColor(this, R.color.JGGCyan10Percent));
        cancelButton.setTextColor(ContextCompat.getColor(this, R.color.JGGCyan));
        TextView deleteButton = (TextView) dialogView.findViewById(R.id.btn_alert_ok);
        TextView title = (TextView) dialogView.findViewById(R.id.lbl_alert_titile);
        TextView desc = (TextView) dialogView.findViewById(R.id.lbl_alert_description);

        title.setText(R.string.alert_posted_job_delete_title);
        desc.setText(R.string.alert_posted_job_delete_desc);

        final AlertDialog alertDialog = builder.create();
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                deleteJob("");

            }
        });
        alertDialog.show();
    }

    public void deleteJob(String reason) {
        progressDialog = createProgressDialog(this);

        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, this);
        String jobID = mJob.getID();
        Call<JGGBaseResponse> call = apiManager.deleteJob(jobID, reason);
        call.enqueue(new Callback<JGGBaseResponse>() {
            @Override
            public void onResponse(Call<JGGBaseResponse> call, Response<JGGBaseResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        onBackClick();
                    } else {
                        Toast.makeText(PostedJobActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode  = response.code();
                    Toast.makeText(PostedJobActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGBaseResponse> call, Throwable t) {
                Toast.makeText(PostedJobActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}
