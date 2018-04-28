package com.kelvin.jacksgogo.Fragments.GoClub_Event;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.Activities.GoClub_Event.CreateGoClubActivity;
import com.kelvin.jacksgogo.Activities.GoClub_Event.GoClubDetailActivity;
import com.kelvin.jacksgogo.Adapter.GoClub_Event.GcSummaryAdapter;
import com.kelvin.jacksgogo.CustomView.Views.JGGAlertView;
import com.kelvin.jacksgogo.CustomView.Views.PostGoClubTabView.GoClubTabName;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.Global.PostStatus;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.GoClub_Event.JGGGoClubModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGGoClubUserModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserProfileModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGPostAppResponse;
import com.squareup.picasso.Picasso;
import com.yanzhenjie.album.AlbumFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.lujun.androidtagview.TagContainerLayout;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.Global.PostStatus.EDIT;
import static com.kelvin.jacksgogo.Utils.Global.PostStatus.POST;

/**
 * A simple {@link Fragment} subclass.
 */
public class GcSummaryFragment extends Fragment {

    @BindView(R.id.img_category)                        ImageView imgCategory;
    @BindView(R.id.lbl_category_name)                   TextView lblCategory;

    @BindView(R.id.ll_main_describe)                    LinearLayout ll_main_describe;
    @BindView(R.id.txt_describe_title)                  TextView txtDescribeTitle;
    @BindView(R.id.txt_describe_description)            TextView txtDescribeDesc;
    @BindView(R.id.gc_main_tag_view)                    TagContainerLayout describeTagView;

    @BindView(R.id.ll_limit)                            LinearLayout ll_limit;
    @BindView(R.id.txt_pax)                             TextView txtPax;

    @BindView(R.id.ll_admin)                            LinearLayout ll_admin;
    @BindView(R.id.txt_admin)                           TextView txtAdmin;

    @BindView(R.id.txt_create_club)                     TextView txtCreate;
    @BindView(R.id.summary_recycler_view)               RecyclerView summaryRecyclerView;

    private Context mContext;
    private CreateGoClubActivity mActivity;
    private GcSummaryAdapter adapter;
    private AlertDialog alertDialog;
    private ProgressDialog progressDialog;
    private CreateGoClubMainFragment mainFragment;

    private JGGGoClubModel creatingClub;
    private ArrayList<JGGUserProfileModel> invitedUsers = new ArrayList<>();
    private ArrayList<AlbumFile> mAlbumFiles = new ArrayList<>();
    private ArrayList<String> attachmentURLs = new ArrayList<>();
    private String clubID;
    private PostStatus postStatus;

    public GcSummaryFragment() {
        // Required empty public constructor
    }

    public void setEditStatus(PostStatus status) {
        postStatus = status;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gc_summary, container, false);
        ButterKnife.bind(this, view);

        // TODO : Get creating GoClub
        creatingClub = JGGAppManager.getInstance().getSelectedClub();
        mAlbumFiles = creatingClub.getAlbumFiles();
        attachmentURLs = creatingClub.getAttachmentURLs();
        // TODO : Add current user to Invited Users
        ArrayList<JGGUserProfileModel> owner = new ArrayList<>();
        owner.add(JGGAppManager.getInstance().getCurrentUser());
        invitedUsers.addAll(owner);
        if (creatingClub.getClubUsers().size() > 0) {
            ArrayList<JGGUserProfileModel> tmpUser = new ArrayList<>();
            for (JGGGoClubUserModel user : creatingClub.getClubUsers()) {
                tmpUser.add(user.getUserProfile());
            }
            invitedUsers.addAll(tmpUser);
        } else
            invitedUsers.addAll(creatingClub.getUsers());

        // TODO : Init RecyclerView
        if (summaryRecyclerView != null) {
            summaryRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.VERTICAL, false));
        }

        setClubData();

        return view;
    }

    private void setClubData() {
        if (creatingClub == null) {
            txtDescribeTitle.setText("No title");
            txtDescribeDesc.setText("");
            describeTagView.removeAllTags();
            txtPax.setText("No Pax");
            txtAdmin.setText("No Admin");
        } else {
            // Category
            Picasso.with(mContext)
                    .load(creatingClub.getCategory().getImage())
                    .placeholder(null)
                    .into(imgCategory);
            lblCategory.setText(creatingClub.getCategory().getName());
            // Describe
            txtDescribeTitle.setText(creatingClub.getName());
            txtDescribeDesc.setText(creatingClub.getDescription());
            String tags = creatingClub.getTags();
            if (tags != null && tags.length() > 0) {
                String[] strings = tags.split(",");
                describeTagView.setTags(Arrays.asList(strings));
            }
            // Limit
            if (creatingClub.getLimit() == null)
                txtPax.setText("No Limit");
            else
                txtPax.setText(String.valueOf(creatingClub.getLimit()) + " pax");
            // Added Admin Recycler View
            if (invitedUsers.size() > 1) {
                int total = invitedUsers.size();
                txtAdmin.setText("Total " + total + " admins:");
            } else {
                txtAdmin.setText("You are the sole admin:");
            }
            // Post button
            if (postStatus == EDIT) txtCreate.setText("Save Changes");
            // Member recyclerView
            adapter = new GcSummaryAdapter(mContext, invitedUsers);
            summaryRecyclerView.setAdapter(adapter);
        }
    }

    private void onCreateNewClub() {
        progressDialog = Global.createProgressDialog(mContext);

        creatingClub.setAttachmentURLs(attachmentURLs);

        JGGAPIManager manager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        Call<JGGPostAppResponse> call = manager.createClubs(creatingClub);
        call.enqueue(new Callback<JGGPostAppResponse>() {
            @Override
            public void onResponse(Call<JGGPostAppResponse> call, Response<JGGPostAppResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {

                        clubID = response.body().getValue();

                        creatingClub = JGGAppManager.getInstance().getSelectedClub();
                        creatingClub.setID(clubID);

                        JGGAppManager.getInstance().setSelectedClub(creatingClub);

                        showReferenceNoDialog();
                    } else {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGPostAppResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onEditClub() {
        progressDialog = Global.createProgressDialog(mContext);

        creatingClub.setAttachmentURLs(attachmentURLs);

        JGGAPIManager manager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        Call<JGGPostAppResponse> call = manager.editClubs(creatingClub);
        call.enqueue(new Callback<JGGPostAppResponse>() {
            @Override
            public void onResponse(Call<JGGPostAppResponse> call, Response<JGGPostAppResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {

                        clubID = response.body().getValue();

                        creatingClub = JGGAppManager.getInstance().getSelectedClub();
                        creatingClub.setID(clubID);

                        JGGAppManager.getInstance().setSelectedClub(creatingClub);

                        if (postStatus.equals(POST))
                            showReferenceNoDialog();
                        else
                            mActivity.finish();
                    } else {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGPostAppResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadImage(final int index) {
        if (mAlbumFiles == null) {
            if (postStatus.equals(POST)) {
                onCreateNewClub();
            } else {
                onEditClub();
            }
        } else {
            if (index < mAlbumFiles.size()) {
                String name = (String) mAlbumFiles.get(index).getPath();
                Uri imageUri = Uri.parse(new File(name).toString());
                File file = new File(String.valueOf(imageUri));

                // Parsing any Media type file
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

                progressDialog = Global.createProgressDialog(mContext);

                JGGAPIManager manager = JGGURLManager.createService(JGGAPIManager.class, mContext);
                Call<JGGPostAppResponse> call = manager.uploadAttachmentFile(fileToUpload);
                call.enqueue(new Callback<JGGPostAppResponse>() {
                    @Override
                    public void onResponse(Call<JGGPostAppResponse> call, Response<JGGPostAppResponse> response) {
                        progressDialog.dismiss();
                        if (response.isSuccessful()) {
                            if (response.body().getSuccess()) {
                                String url = response.body().getValue();
                                attachmentURLs.add(url);
                                uploadImage(index + 1);
                            } else {
                                Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JGGPostAppResponse> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                if (postStatus.equals(POST)) {
                    onCreateNewClub();
                } else {
                    onEditClub();
                }
            }
        }
    }

    private void showReferenceNoDialog() {
        String message = "GoClub reference no.: "
                + clubID
                + '\n'
                + "Your GoClub is now active.";
        JGGAlertView builder = new JGGAlertView(mContext,
                mContext.getResources().getString(R.string.alert_goclub_created),
                message,
                false,
                "",
                R.color.JGGPurple,
                R.color.JGGPurple10Percent,
                mContext.getResources().getString(R.string.view_goclub_details),
                R.color.JGGPurple);
        alertDialog = builder.create();
        builder.setOnItemClickListener(new JGGAlertView.OnItemClickListener() {
            @Override
            public void onDoneButtonClick(View view) {
                if (view.getId() == R.id.btn_alert_cancel)
                    alertDialog.dismiss();
                else {
                    alertDialog.dismiss();
                    Intent intent = new Intent(mContext, GoClubDetailActivity.class);
                    intent.putExtra("is_post", true);
                    mContext.startActivity(intent);
                }
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    @OnClick(R.id.ll_create_club)
    public void onClickCreateClub() {
        if (attachmentURLs.size() == 0)
            uploadImage(0);
        else {
            if (postStatus.equals(POST)) {
                onCreateNewClub();
            } else {
                onEditClub();
            }
        }
    }

    @OnClick(R.id.ll_main_describe)
    public void onClickDescribe() {
        mainFragment = CreateGoClubMainFragment.newInstance(GoClubTabName.DESCRIBE, postStatus);
        onShowClubMainFragment();
    }

    @OnClick(R.id.ll_limit)
    public void onClickLimit() {
        mainFragment = CreateGoClubMainFragment.newInstance(GoClubTabName.LIMIT, postStatus);
        onShowClubMainFragment();
    }

    @OnClick(R.id.ll_admin)
    public void onClickAdmin() {
        mainFragment = CreateGoClubMainFragment.newInstance(GoClubTabName.ADMIN, postStatus);
        onShowClubMainFragment();
    }

    private void onShowClubMainFragment() {
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.post_go_club_container, mainFragment)
                .addToBackStack("post_club")
                .commit();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mActivity = ((CreateGoClubActivity) context);
    }
}
