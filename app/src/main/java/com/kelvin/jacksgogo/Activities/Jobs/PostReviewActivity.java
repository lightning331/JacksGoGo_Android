package com.kelvin.jacksgogo.Activities.Jobs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class PostReviewActivity extends AppCompatActivity {

    @BindView(R.id.review_actionbar) Toolbar mToolbar;
    @BindView(R.id.img_detail) ImageView imgCategory;
    @BindView(R.id.lbl_title) TextView lblCategory;
    @BindView(R.id.lbl_date) TextView lblTime;
    @BindView(R.id.lbl_username) TextView lblUserName;
    @BindView(R.id.img_avatar) RoundedImageView imgAvatar;
    @BindView(R.id.user_ratingbar) MaterialRatingBar userRatingBar;
    @BindView(R.id.review_ratingbar) MaterialRatingBar reviewRatingBar;
    @BindView(R.id.review_description) EditText txtDescription;
    @BindView(R.id.btn_post_review) TextView btnPostReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_review);
        ButterKnife.bind(this);
    }
}
