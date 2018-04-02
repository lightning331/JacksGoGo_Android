package com.kelvin.jacksgogo.Activities.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.Search.ActiveServiceActivity;
import com.kelvin.jacksgogo.Activities.Search.ServiceReviewsActivity;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserBaseModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserProfileModel;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.lujun.androidtagview.TagContainerLayout;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

import static com.kelvin.jacksgogo.Utils.JGGAppManager.currentUser;
import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.EDIT;
import static com.kelvin.jacksgogo.Utils.Global.EDIT_STATUS;
import static com.kelvin.jacksgogo.Utils.Global.SERVICES;

public class PublickProfileActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.public_profile_actionbar) Toolbar mToolbar;
    @BindView(R.id.detail_images_carousel_view) CarouselView carouselView;
    @BindView(R.id.lbl_username) TextView lblUserName;
    @BindView(R.id.lbl_profile_desc) TextView lblDesc;
    @BindView(R.id.lbl_profile_location) TextView lblAddress;
    @BindView(R.id.user_total_review_ratingbar) MaterialRatingBar ratingBar;
    @BindView(R.id.lbl_review_count) TextView lblReviewCount;
    @BindView(R.id.btn_see_all_reviews) LinearLayout btnAllReview;
    @BindView(R.id.profile_tag_list) TagContainerLayout tagList;
    @BindView(R.id.business_detail_layout) LinearLayout businessDetailLayout;
    @BindView(R.id.lbl_company) TextView lblCompany;
    @BindView(R.id.lbl_degree) TextView lblDegree;
    @BindView(R.id.btn_public_profile) TextView btnViewService;

    private JGGActionbarView actionbarView;
    private JGGUserProfileModel mUserProfile = new JGGUserProfileModel();
    private JGGUserBaseModel mUser = new JGGUserBaseModel();
    private int[] imageArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publick_profile);
        ButterKnife.bind(this);

        // Top Navigationbar View
        actionbarView = new JGGActionbarView(this);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);
        actionbarView.setProfileActionBar();
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        btnAllReview.setOnClickListener(this);
        btnViewService.setOnClickListener(this);

        mUserProfile = currentUser;
        mUser = mUserProfile.getUser();
        if (mUserProfile == null)
            return;
        else
            initView();
    }

    private void initView() {
        // User Photo
        imageArray = new int[]{R.mipmap.carousel03, R.mipmap.carousel01,
                R.mipmap.carousel04, R.mipmap.carousel05, R.mipmap.carousel06, R.mipmap.carousel01, R.mipmap.carousel03, R.mipmap.carousel02};

        carouselView.setPageCount(imageArray.length);
        carouselView.setImageListener(imageListener);
        // Name
        lblUserName.setText(mUser.getFullName());
        // Address
        lblAddress.setText(mUserProfile.getFullAddress());
        // Description
        if (mUser.getOverview() == null)
            lblDesc.setText("");
        else
            lblDesc.setText(mUser.getOverview());
        // Business Detail
        if (mUser.getBusinessDetail() == null && mUser.getCredentialDetail() == null)
            businessDetailLayout.setVisibility(View.GONE);
        else {
            businessDetailLayout.setVisibility(View.VISIBLE);
            lblCompany.setText(mUser.getBusinessDetail());
            lblDegree.setText(mUser.getCredentialDetail());
        }
        // Tag List
        String tags = mUser.getTagList();
        if (tags != null && tags.length() > 0) {
            String [] strings = tags.split(",");
            tagList.setTags(Arrays.asList(strings));
        }
    }

    private void actionbarViewItemClick(View view) {
        if (view.getId() == R.id.btn_back) {
            onBackPressed();
        } else if (view.getId() == R.id.btn_more) {
            Intent intent = new Intent(this, EditProfileActivity.class);
            startActivity(intent);
        }
    }

    public ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(imageArray[position]);
        }
    };

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_see_all_reviews) {
            startActivity(new Intent(this, ServiceReviewsActivity.class));
        } else if (view.getId() == R.id.btn_public_profile) {
            Intent mIntent = new Intent(this, ActiveServiceActivity.class);
            mIntent.putExtra(APPOINTMENT_TYPE, SERVICES);
            mIntent.putExtra(EDIT_STATUS, EDIT);
            startActivity(mIntent);
        }
    }
}
