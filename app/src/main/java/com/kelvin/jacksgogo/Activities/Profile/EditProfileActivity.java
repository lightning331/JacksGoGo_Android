package com.kelvin.jacksgogo.Activities.Profile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kelvin.jacksgogo.Activities.JGGMapViewActivity;
import com.kelvin.jacksgogo.Activities.Search.JGGImageCropActivity;
import com.kelvin.jacksgogo.Adapter.Services.JGGImageGalleryAdapter;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.Models.System.JGGAddressModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserBaseModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserProfileModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGUserProfileResponse;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.JGGAppManager.currentUser;
import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.REQUEST_CODE;
import static com.kelvin.jacksgogo.Utils.Global.USERS;

public class EditProfileActivity extends AppCompatActivity implements
        View.OnClickListener,
        TextWatcher {

    @BindView(R.id.edit_profile_actionbar) Toolbar mToolbar;
    @BindView(R.id.edit_profile_photo_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.btn_location) TextView btnLocation;
    @BindView(R.id.txt_unit) EditText txtUnit;
    @BindView(R.id.txt_street) EditText txtStreet;
    @BindView(R.id.txt_postcode) EditText txtPostCode;
    @BindView(R.id.lbl_coordinate) TextView lblCoordinate;
    @BindView(R.id.txt_mobile_no) EditText txtMobile;
    @BindView(R.id.txt_email) EditText txtEmail;
    @BindView(R.id.txt_description) EditText txtDescription;
    @BindView(R.id.txt_company_name) EditText txtCompanyName;
    @BindView(R.id.txt_credentials) EditText txtCredential;
    @BindView(R.id.txt_tags) EditText txtTags;

    private JGGActionbarView actionbarView;
    private JGGImageGalleryAdapter mAdapter;
    private ProgressDialog progressDialog;

    private ArrayList<AlbumFile> mAlbumFiles;
    private JGGUserProfileModel mUserProfile = new JGGUserProfileModel();
    private JGGUserBaseModel mUser = new JGGUserBaseModel();
    private JGGAddressModel mAddress = new JGGAddressModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);

        // Top Navigationbar View
        actionbarView = new JGGActionbarView(this);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);
        actionbarView.setStatus(JGGActionbarView.EditStatus.EDIT_PROFILE, Global.AppointmentType.UNKNOWN);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                if (view.getId() == R.id.btn_back)
                    onBackPressed();
                else if (view.getId() == R.id.btn_more)
                    onSaveUserData();
            }
        });

        mUserProfile = currentUser;
        mUser = mUserProfile.getUser();
        mAddress.setUnit(mUserProfile.getResidentialAddress_Unit());
        mAddress.setStreet(mUserProfile.getResidentialAddress_State());
        mAddress.setPostalCode(mUserProfile.getResidentialAddress_PostalCode());
        if (mUserProfile.getResidentialAddress_Lat() == null)
            mAddress.setLat(0.0);
        else
            mAddress.setLat(mUserProfile.getResidentialAddress_Lat());
        if (mUserProfile.getResidentialAddress_Lon() == null)
            mAddress.setLon(0.0);
        else
            mAddress.setLon(mUserProfile.getResidentialAddress_Lon());

        initRecyclerView();
        initView();
    }

    private void initView() {
        btnLocation.setOnClickListener(this);
        txtUnit.addTextChangedListener(this); txtStreet.addTextChangedListener(this);
        txtPostCode.addTextChangedListener(this); txtMobile.addTextChangedListener(this);
        txtEmail.addTextChangedListener(this); txtDescription.addTextChangedListener(this);
        txtCompanyName.addTextChangedListener(this); txtCredential.addTextChangedListener(this);
        txtTags.addTextChangedListener(this);

        // Cover Photo

        // Unit
        txtUnit.setText(mUserProfile.getResidentialAddress_Unit());
        // Street
        txtStreet.setText(mUserProfile.getResidentialAddress_Address());
        // PostCode
        txtPostCode.setText(mUserProfile.getResidentialAddress_PostalCode());
        // Coordinate
        String coordinate;
        if (mUserProfile.getResidentialAddress_Lat() == null)
            coordinate = "0.0° N, " + "0.0° E";
        else
            coordinate = mUserProfile.getResidentialAddress_Lat() + "° N, " + mUserProfile.getResidentialAddress_Lon() + "° E";
        lblCoordinate.setText(coordinate);
        // Mobile No
        txtMobile.setText(mUser.getPhoneNumber());
        // Email
        txtEmail.setText(mUser.getEmail());
        // Description
        txtDescription.setText(mUser.getOverview());
        if (mUser.getBusinessDetail() == null && mUser.getCredentialDetail() == null) {
            // Company Name
            txtCompanyName.setText("");
            // Credential
            txtCredential.setText("");
        } else {
            // Company Name
            txtCompanyName.setText(mUser.getBusinessDetail());
            // Credential
            txtCredential.setText(mUser.getCredentialDetail());
        }
        // Tags
        if (mUser.getTagList() == null)
            txtTags.setHint("Separate tags with a comma");
        else {
            if (mUser.getTagList().length() == 0)
                txtTags.setHint("Separate tags with a comma");
            else
                txtTags.setText(mUser.getTagList());
        }
    }

    private void setData() {
        txtUnit.setText(mAddress.getUnit());
        txtStreet.setText(mAddress.getStreet());
        txtPostCode.setText(mAddress.getPostalCode());
        lblCoordinate.setText(String.valueOf(mAddress.getLat()) + "° N, " + String.valueOf(mAddress.getLon()) + "° E");
    }

    private void onSaveUserData() {
        mUserProfile.setResidentialAddress_Unit(txtUnit.getText().toString());
        mUserProfile.setResidentialAddress_State(txtStreet.getText().toString());
        mUserProfile.setResidentialAddress_PostalCode(txtPostCode.getText().toString());
        mUserProfile.setResidentialAddress_Lat(mAddress.getLat());
        mUserProfile.setResidentialAddress_Lon(mAddress.getLon());
        mUser.setPhoneNumber(txtMobile.getText().toString());
        mUser.setOverview(txtDescription.getText().toString());
        mUser.setBusinessDetail(txtCompanyName.getText().toString());
        mUser.setCredentialDetail(txtCredential.getText().toString());
        mUser.setTagList(txtTags.getText().toString());
        mUserProfile.setUser(mUser);

        progressDialog = Global.createProgressDialog(this);
        JGGAPIManager manager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGUserProfileResponse> call = manager.editProfile(mUserProfile);
        call.enqueue(new Callback<JGGUserProfileResponse>() {
            @Override
            public void onResponse(Call<JGGUserProfileResponse> call, Response<JGGUserProfileResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {

                    } else {
                        Toast.makeText(EditProfileActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode  = response.code();
                    Toast.makeText(EditProfileActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGUserProfileResponse> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int width = display.getWidth();

        int itemSize = (width) / 4;
        mAdapter = new JGGImageGalleryAdapter(this, itemSize, false, new com.yanzhenjie.album.impl.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position < mAlbumFiles.size()) {
                    String name = (String)mAlbumFiles.get(position).getPath();
                    Uri imageUri = Uri.parse(new File(name).toString());
                    Intent intent = new Intent(EditProfileActivity.this, JGGImageCropActivity.class);
                    intent.putExtra("imageUri", name);
                    intent.putExtra(APPOINTMENT_TYPE, USERS);
                    startActivityForResult(intent, 1);
                } else {
                    selectImage();
                }
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    private void selectImage() {
        Album.image(this)
                .multipleChoice()
                .requestCode(200)
                .camera(true)
                .columnCount(3)
                .selectCount(7)
                .checkedList(mAlbumFiles)
                .widget(
                        Widget.newLightBuilder(this)
                                .title("Include Photos") // Title.
                                .statusBarColor(ContextCompat.getColor(this, R.color.JGGGrey1)) // StatusBar color.
                                .toolBarColor(Color.WHITE) // Toolbar color.
                                .navigationBarColor(Color.GREEN) // Virtual NavigationBar color of Android5.0+.
                                .mediaItemCheckSelector(ContextCompat.getColor(this, R.color.JGGGreen), Color.GREEN) // Image or video selection box.
                                .bucketItemCheckSelector(ContextCompat.getColor(this, R.color.JGGGreen), ContextCompat.getColor(this, R.color.JGGGreen)) // Select the folder selection box.
                                .buttonStyle( // Used to configure the style of button when the image/video is not found.
                                        Widget.ButtonStyle.newLightBuilder(this) // With Widget's Builder model.
                                                .setButtonSelector(ContextCompat.getColor(this, R.color.JGGGreen), Color.WHITE) // Button selector.
                                                .build()
                                )
                                .build()
                )
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(int requestCode, @NonNull ArrayList<AlbumFile> result) {
                        mAlbumFiles = result;
                        recyclerView.setVisibility(View.VISIBLE);
                        mAdapter.notifyDataSetChanged(mAlbumFiles);
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(int requestCode, @NonNull String result) {
                        //Toast.makeText(mContext, R.string.canceled, Toast.LENGTH_LONG).show();
                    }
                })
                .start();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_location) {
            Intent intent = new Intent(this, JGGMapViewActivity.class);
            intent.putExtra(APPOINTMENT_TYPE, USERS);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK){
                Gson gson = new Gson();
                String result=data.getStringExtra("result");
                mAddress = gson.fromJson(result, JGGAddressModel.class);
                setData();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
