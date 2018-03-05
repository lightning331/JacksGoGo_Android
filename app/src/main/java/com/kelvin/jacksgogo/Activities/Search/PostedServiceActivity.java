package com.kelvin.jacksgogo.Activities.Search;

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

import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.CustomView.Views.JGGShareIntentDialog;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppBaseModel;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.lujun.androidtagview.TagContainerLayout;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.creatingAppointment;
import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.selectedCategory;
import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.SERVICES;

public class PostedServiceActivity extends AppCompatActivity {

    @BindView(R.id.posted_service_actionbar) Toolbar mToolbar;
    @BindView(R.id.posted_service_status_layout) LinearLayout verifyStatusLayout;
    @BindView(R.id.posted_service_chat_layout) LinearLayout chatLayout;
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

    private boolean isVerified = getRandomBoolean();
    private boolean isPost = false;

    public static boolean getRandomBoolean() {
        return Math.random() < 0.5;
    }

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

        actionbarView.setStatus(JGGActionbarView.EditStatus.POSTED, JGGAppBaseModel.AppointmentType.SERVICES);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        if (selectedCategory != null && creatingAppointment != null)
            initView();
    }

    private void initView() {
        if (isVerified) {
            verifyStatusLayout.setVisibility(View.GONE);
            chatLayout.setVisibility(View.GONE);
        }
        // Category
        Picasso.with(this)
                .load(selectedCategory.getImage())
                .placeholder(null)
                .into(imgCategory);
        lblCategory.setText(selectedCategory.getName());
        lblTitle.setText(creatingAppointment.getTitle());
        // Description
        lblDescription.setText(creatingAppointment.getDescription());
        // Address
        lblAddress.setText(creatingAppointment.getAddress().getFullAddress());
        // Price
        if (creatingAppointment.getSelectedServiceType() == 1) lblBudget.setText("No limit");
        else if (creatingAppointment.getSelectedServiceType() == 2) lblBudget.setText("$ " + creatingAppointment.getBudget().toString());
        else if (creatingAppointment.getSelectedServiceType() == 3)
            lblBudget.setText("$ " + creatingAppointment.getBudgetFrom().toString()
                    + " "
                    + "$ " + creatingAppointment.getBudgetTo().toString());
        // User
        Picasso.with(this)
                .load(creatingAppointment.getUserProfile().getUser().getPhotoURL())
                .placeholder(null)
                .into(imgAvatar);
        lblUserName.setText(creatingAppointment.getUserProfile().getUser().getFullName());
        ratingBar.setRating(creatingAppointment.getUserProfile().getUser().getRate().floatValue());
        // Tag View
        String [] strings = creatingAppointment.getTags().split(",");
        tagList.setTags(Arrays.asList(strings));
        // Time Slot
        if (creatingAppointment.getSessions() == null)
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
                intent.putExtra("EDIT_STATUS", "Edit");
                intent.putExtra(APPOINTMENT_TYPE, SERVICES);
                startActivity(intent);
            } else if (menuItem.getItemId() == R.id.posted_service_menu_duplicate) {    // Duplicate Service
                Intent intent = new Intent(PostedServiceActivity.this, PostServiceActivity.class);
                intent.putExtra("EDIT_STATUS", "Duplicate");
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
                onBackClick();
            }
        });
        alertDialog.show();
    }
}
