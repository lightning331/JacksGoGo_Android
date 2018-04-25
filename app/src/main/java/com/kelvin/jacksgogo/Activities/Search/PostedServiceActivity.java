package com.kelvin.jacksgogo.Activities.Search;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.Activities.MainActivity;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.CustomView.Views.JGGShareIntentDialog;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGBaseResponse;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.lujun.androidtagview.TagContainerLayout;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.DUPLICATE;
import static com.kelvin.jacksgogo.Utils.Global.EDIT;
import static com.kelvin.jacksgogo.Utils.Global.EDIT_STATUS;
import static com.kelvin.jacksgogo.Utils.Global.SERVICES;
import static com.kelvin.jacksgogo.Utils.Global.createProgressDialog;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.appointmentMonthDate;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getDayMonthYear;

public class PostedServiceActivity extends AppCompatActivity {

    @BindView(R.id.posted_service_actionbar) Toolbar mToolbar;
    @BindView(R.id.posted_service_status_layout) LinearLayout verifyStatusLayout;
    @BindView(R.id.posted_service_chat_layout) LinearLayout chatLayout;
    @BindView(R.id.posted_time) TextView lblPostedTime;
    @BindView(R.id.posted_service_tag_list) TagContainerLayout tagList;
    @BindView(R.id.btn_posted_service_view_time_slots) LinearLayout btnViewTimeSlots;
    @BindView(R.id.img_posted_service_category) ImageView imgCategory;
    @BindView(R.id.lbl_posted_service_category_name) TextView lblCategory;
    @BindView(R.id.lbl_posted_service_title) TextView lblTitle;
    @BindView(R.id.lbl_posted_service_price) TextView lblBudget;
    @BindView(R.id.lbl_posted_service_description) TextView lblDescription;
    @BindView(R.id.lbl_posted_service_location) TextView lblAddress;
    @BindView(R.id.img_posted_service_user_avatar) RoundedImageView imgAvatar;
    @BindView(R.id.lbl_posted_service_username) TextView lblUserName;
    @BindView(R.id.posted_service_user_rating) MaterialRatingBar ratingBar;

    private JGGActionbarView actionbarView;
    private ProgressDialog progressDialog;

    private JGGAppointmentModel mService;
    private boolean isVerified;
    private boolean isPost = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posted_service);

        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        isPost = bundle.getBoolean("is_post");

        // Top Navigationbar View
        actionbarView = new JGGActionbarView(this);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        actionbarView.setStatus(JGGActionbarView.EditStatus.POSTED, AppointmentType.SERVICES);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        mService = JGGAppManager.getInstance().getSelectedAppointment();
        if (mService != null)
            initView();
    }

    private void initView() {
        if (isVerified) {
            verifyStatusLayout.setVisibility(View.GONE);
            chatLayout.setVisibility(View.GONE);
        }
        // Posted Time
        String time = getDayMonthYear(appointmentMonthDate(mService.getPostOn()));
        lblPostedTime.setText("Submitted on " + time + ". Pending verification.");
        // Category
        Picasso.with(this)
                .load(mService.getCategory().getImage())
                .placeholder(null)
                .into(imgCategory);
        lblCategory.setText(mService.getCategory().getName());
        lblTitle.setText(mService.getTitle());
        // Description
        lblDescription.setText(mService.getDescription());
        // Address
        lblAddress.setText(mService.getAddress().getFullAddress());
        // Price
        String price = "";
        if (mService.getAppointmentType() == 1) {
            if (mService.getBudget() == null && mService.getBudgetFrom() == null)
                price = "No limit";
            else {
                if (mService.getBudget() != null)
                    price = "Fixed $ " + mService.getBudget().toString();
                else if (mService.getBudgetFrom() != null)
                    price = "From $ " + mService.getBudgetFrom().toString()
                            + " "
                            + "to $ " + mService.getBudgetTo().toString();
            }
        } else if (mService.getAppointmentType() >= 2) {
            price = String.valueOf(mService.getAppointmentType()) + " Services, ";
            if (mService.getBudget() != null)
                price = price + "$ " + String.valueOf(mService.getBudget()) + " per service";
            else
                price = "$ " + String.valueOf(mService.getBudget());
        }
        lblBudget.setText(price);
        // User
        Picasso.with(this)
                .load(mService.getUserProfile().getUser().getPhotoURL())
                .placeholder(R.mipmap.icon_profile)
                .into(imgAvatar);
        lblUserName.setText(mService.getUserProfile().getUser().getFullName());
        if (mService.getUserProfile().getUser().getRate() == null)
            ratingBar.setRating(0);
        else
            ratingBar.setRating(mService.getUserProfile().getUser().getRate().floatValue());
        // Tag View
        String tags = mService.getTags();
        if (tags != null && tags.length() > 0) {
            String [] strings = tags.split(",");
            tagList.setTags(Arrays.asList(strings));
        }
        // Time Slot
        if (mService.getSessions() == null)
            btnViewTimeSlots.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_posted_service_view_time_slots) void viewTimeSlot() {
        startActivity(new Intent(this, ServiceTimeSlotsActivity.class));
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
        if (isPost) {
            Intent intent = new Intent(this, ServiceListingDetailActivity.class);
            intent.putExtra("is_post", true);
            startActivity(intent);
        } else {
            onBackPressed();
        }
    }

    private void openShareDialog() {
        JGGShareIntentDialog dialogShare = new JGGShareIntentDialog.Builder(this)
                .setDialogTitle("Share with your friends")
                .setShareLink(null)
                .build();
        dialogShare.show();
    }

    private void onMorePopUpMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(PostedServiceActivity.this, view);
        popupMenu.inflate(R.menu.posted_service_menu);
        popupMenu.setOnDismissListener(new OnDismissListener());
        popupMenu.setOnMenuItemClickListener(new PostedServiceActivity.OnMenuItemClickListener());
        if (!isVerified) {
            Menu m = popupMenu.getMenu();
            m.removeItem(R.id.posted_service_menu_share);
        }

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

            if (menuItem.getItemId() == R.id.posted_service_menu_share) {  // Share the Service
                openShareDialog();
            } else if (menuItem.getItemId() == R.id.posted_service_menu_edit) {    // Edit Service
                Intent intent = new Intent(PostedServiceActivity.this, PostServiceActivity.class);
                intent.putExtra(EDIT_STATUS, EDIT);
                intent.putExtra(APPOINTMENT_TYPE, SERVICES);
                startActivity(intent);
            } else if (menuItem.getItemId() == R.id.posted_service_menu_duplicate) {    // Duplicate Service
                Intent intent = new Intent(PostedServiceActivity.this, PostServiceActivity.class);
                intent.putExtra(EDIT_STATUS, DUPLICATE);
                intent.putExtra(APPOINTMENT_TYPE, SERVICES);
                startActivity(intent);
            } else if (menuItem.getItemId() == R.id.posted_service_menu_delete) {    // Delete Service
                onDeleteButtonClick();
            }
            return true;
        }
    }

    private void onDeleteButtonClick() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(PostedServiceActivity.this);
        LayoutInflater inflater = (PostedServiceActivity.this).getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.jgg_alert_view, null);
        builder.setView(dialogView);
        TextView cancelButton = (TextView) dialogView.findViewById(R.id.btn_alert_cancel);
        TextView deleteButton = (TextView) dialogView.findViewById(R.id.btn_alert_ok);
        TextView title = (TextView) dialogView.findViewById(R.id.lbl_alert_titile);
        TextView desc = (TextView) dialogView.findViewById(R.id.lbl_alert_description);

        title.setText(R.string.alert_posted_service_delete_title);
        desc.setText(R.string.alert_posted_service_delete_desc);

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
                onDeleteService();
            }
        });
        alertDialog.show();
    }

    private void onDeleteService() {
        progressDialog = createProgressDialog(this);
        JGGAPIManager manager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGBaseResponse> call = manager.deleteService(mService.getID());
        call.enqueue(new Callback<JGGBaseResponse>() {
            @Override
            public void onResponse(Call<JGGBaseResponse> call, Response<JGGBaseResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        Intent intent = new Intent(PostedServiceActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(PostedServiceActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode  = response.code();
                    Toast.makeText(PostedServiceActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGBaseResponse> call, Throwable t) {
                Toast.makeText(PostedServiceActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}
