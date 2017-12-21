package com.kelvin.jacksgogo.Activities.Search;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.CustomView.Views.JGGShareIntentDialog;
import com.kelvin.jacksgogo.Models.Jobs_Services_Events.JGGAppBaseModel;
import com.kelvin.jacksgogo.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;

public class PostedServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private JGGActionbarView actionbarView;
    private TagContainerLayout tagList;
    private LinearLayout btnViewTimeSlots;
    private LinearLayout verifyStatusLayout;
    private LinearLayout chatLayout;

    private boolean isVerified = getRandomBoolean();
    private boolean isPost = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posted_service);

        Bundle bundle = getIntent().getExtras();
        isPost = bundle.getBoolean("is_post");

        // Top Navigationbar View
        actionbarView = new JGGActionbarView(this);
        mToolbar = (Toolbar) findViewById(R.id.posted_service_actionbar);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        actionbarView.setStatus(JGGActionbarView.EditStatus.POSTED_SERVICE, JGGAppBaseModel.AppointmentType.UNKNOWN);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        initiView();
    }

    public static boolean getRandomBoolean() {
        return Math.random() < 0.5;
    }

    private void initiView() {
        btnViewTimeSlots = findViewById(R.id.btn_posted_service_view_time_slots);
        verifyStatusLayout = findViewById(R.id.posted_service_status_layout);
        chatLayout = findViewById(R.id.posted_service_chat_layout);
        btnViewTimeSlots.setOnClickListener(this);
        tagList = findViewById(R.id.posted_service_tag_list);
        setTagList();

        if (isVerified) {
            verifyStatusLayout.setVisibility(View.GONE);
            chatLayout.setVisibility(View.GONE);
        }
    }

    private void setTagList() {
        Typeface typeface = Typeface.create("muliregular", Typeface.NORMAL);
        tagList.setTagTypeface(typeface);
        List<String> tags = new ArrayList<String>();
        tags.add("dog walk");
        tags.add("professional");
        tags.add("dog walk");
        tags.add("dog walk");
        tags.add("professional");
        tagList.setTags(tags);
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
                startActivity(intent);
            } else if (menuItem.getItemId() == R.id.posted_service_menu_duplicate) {    // Duplicate Service
                Intent intent = new Intent(PostedServiceActivity.this, PostServiceActivity.class);
                intent.putExtra("EDIT_STATUS", "Duplicate");
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_posted_service_view_time_slots) {
            startActivity(new Intent(this, ServiceTimeSlotsActivity.class));
        }
    }
}
