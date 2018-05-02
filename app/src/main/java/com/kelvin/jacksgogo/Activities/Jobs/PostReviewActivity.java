package com.kelvin.jacksgogo.Activities.Jobs;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGContractModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserBaseModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGBaseResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGPostAppResponse;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView.EditStatus.REVIEW;
import static com.kelvin.jacksgogo.Utils.Global.createProgressDialog;

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

    public JGGActionbarView actionbarView;
    public JGGContractModel mContract;
    private ProgressDialog progressDialog;
    private boolean isClient;
    private float rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_review);
        ButterKnife.bind(this);

        String contractJson = getIntent().getStringExtra("contract");
        this.isClient = getIntent().getBooleanExtra("isClient", false);
        Gson gson = new Gson();
        JGGContractModel contractModel = gson.fromJson(contractJson, JGGContractModel.class);
        mContract = contractModel;

        actionbarView = new JGGActionbarView(this);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);
        actionbarView.setStatus(REVIEW, Global.AppointmentType.UNKNOWN);

        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        initUserView();

        reviewRatingBar.setOnRatingChangeListener(new MaterialRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChanged(MaterialRatingBar ratingBar, float rating) {
                PostReviewActivity.this.rating = rating;
                String rateStr = String.format("%.2f", rating);
                Toast.makeText(PostReviewActivity.this, "Rating " + rateStr, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initUserView() {
        JGGUserBaseModel userBaseModel = mContract.getProposal().getUserProfile().getUser();
        Picasso.with(this).load(userBaseModel.getPhotoURL())
                .placeholder(R.mipmap.icon_profile)
                .into(imgAvatar);
        lblUserName.setText(userBaseModel.getEmail());
        if (userBaseModel.getRate() != null)
            userRatingBar.setRating(userBaseModel.getRate().floatValue());
    }

    private void actionbarViewItemClick(View view) {
        if (view.getId() == R.id.btn_back) {
            finish();
        }
    }

    @OnClick(R.id.btn_post_review)
    public void onClickPostReview() {
        postReview();
    }

    public void postReview() {

        String contractID = mContract.getID();
        String userProfileID = mContract.getProposal().getUserProfile().getUser().getId();
        String comment = txtDescription.getText().toString();

        progressDialog = createProgressDialog(this);
        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGPostAppResponse> call = apiManager.giveFeedback(contractID,
                                                                userProfileID,
                                                                this.isClient,
                                                                this.rating,
                                                                comment);

        call.enqueue(new Callback<JGGPostAppResponse>() {
            @Override
            public void onResponse(Call<JGGPostAppResponse> call, Response<JGGPostAppResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        finish();
                    } else {
                        Toast.makeText(PostReviewActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode  = response.code();
                    Toast.makeText(PostReviewActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGPostAppResponse> call, Throwable t) {
                Toast.makeText(PostReviewActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}
