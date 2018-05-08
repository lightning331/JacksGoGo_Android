package com.kelvin.jacksgogo.Fragments.GoClub_Event;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.Activities.GoClub_Event.CreateGoClubActivity;
import com.kelvin.jacksgogo.Activities.GoClub_Event.GcPostedEventActivity;
import com.kelvin.jacksgogo.CustomView.Views.JGGAlertView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.Global.PostStatus;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.GoClub_Event.JGGEventModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGPostAppResponse;
import com.squareup.picasso.Picasso;
import com.yanzhenjie.album.AlbumFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

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

import static com.kelvin.jacksgogo.CustomView.Views.PostEventTabView.EventTabName.ADDRESS;
import static com.kelvin.jacksgogo.CustomView.Views.PostEventTabView.EventTabName.COST;
import static com.kelvin.jacksgogo.CustomView.Views.PostEventTabView.EventTabName.DESCRIBE;
import static com.kelvin.jacksgogo.CustomView.Views.PostEventTabView.EventTabName.LIMIT;
import static com.kelvin.jacksgogo.CustomView.Views.PostEventTabView.EventTabName.TIME;
import static com.kelvin.jacksgogo.Utils.Global.PostStatus.EDIT;
import static com.kelvin.jacksgogo.Utils.Global.PostStatus.POST;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.appointmentNewDate;

/**
 * A simple {@link Fragment} subclass.
 */
public class GcEventSummaryFragment extends Fragment {

    private Context mContext;

    @BindView(R.id.img_category)                        ImageView imgCategory;
    @BindView(R.id.lbl_category_name)                   TextView lblCategory;

    @BindView(R.id.ll_main_describe)                    LinearLayout ll_main_describe;
    @BindView(R.id.txt_describe_title)                  TextView txtDescribeTitle;
    @BindView(R.id.txt_describe_description)            TextView txtDescribeDesc;
    @BindView(R.id.gc_main_tag_view)                    TagContainerLayout describeTagView;

    @BindView(R.id.ll_time_schedule)                    LinearLayout ll_time_schedule;

    @BindView(R.id.ll_address)                          LinearLayout ll_address;
    @BindView(R.id.txt_place_name)                      TextView txtPlaceName;
    @BindView(R.id.txt_address)                         TextView txtAddress;

    @BindView(R.id.ll_limit)                            LinearLayout ll_limit;
    @BindView(R.id.txt_pax)                             TextView txtPax;

    @BindView(R.id.ll_cost)                             LinearLayout ll_cost;
    @BindView(R.id.txt_cost)                            TextView txtCost;

    @BindView(R.id.ll_post_event)                       LinearLayout ll_post_event;

    private ProgressDialog progressDialog;
    private CreateEventMainFragment fragment;
    private AlertDialog alertDialog;
    private CreateGoClubActivity mActivity;

    private JGGEventModel mEvent;
    private PostStatus editStatus;
    private String postedEventID;
    private ArrayList<AlbumFile> mAlbumFiles = new ArrayList<>();
    private ArrayList<String> attachmentURLs = new ArrayList<>();

    public void setEditStatus(PostStatus editStatus) {
        this.editStatus = editStatus;
    }


    public GcEventSummaryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        String postTime = appointmentNewDate(new Date());
//        selectedAppointment.setPostOn(postTime);
//        mAlbumFiles = selectedAppointment.getAlbumFiles();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gc_event_summary, container, false);
        ButterKnife.bind(this, view);

        mEvent = JGGAppManager.getInstance().getSelectedEvent();
        mAlbumFiles = mEvent.getAlbumFiles();
        attachmentURLs = mEvent.getAttachmentURLs();

        setEventData();

        return view;
    }

    private void setEventData() {
        if (editStatus == EDIT) txtPax.setText("Save Changes");

        if (mEvent == null) {
            txtDescribeTitle.setText("No title");
            txtDescribeDesc.setText("");
            describeTagView.removeAllTags();
            txtPax.setText("No pax Limit");
            txtCost.setText("Free event. No payment needed.");
        } else {
            // Category
            Picasso.with(mContext)
                    .load(mEvent.getCategory().getImage())
                    .placeholder(null)
                    .into(imgCategory);
            lblCategory.setText(mEvent.getCategory().getName());
            // Describe
            txtDescribeTitle.setText(mEvent.getTitle());
            txtDescribeDesc.setText(mEvent.getDescription());
            String tags = mEvent.getTags();
            if (tags != null && tags.length() > 0) {
                String[] strings = tags.split(",");
                describeTagView.setTags(Arrays.asList(strings));
            }
            // Place Name
            if (mEvent.getAddress().getPlaceName() == null)
                txtPlaceName.setVisibility(View.GONE);
            else
                txtPlaceName.setText(mEvent.getAddress().getPlaceName());
            // Address
            txtAddress.setText(mEvent.getAddress().getFullAddress());
            // Limit
            if (mEvent.getLimit() == null)
                txtPax.setText("No pax Limit");
            else
                txtPax.setText(String.valueOf(mEvent.getLimit()) + " pax");
            // Cost
            if (mEvent.getBudget() == null)
                txtCost.setText("Free event. No payment needed.");
            else
                txtCost.setText("$ " + String.valueOf(mEvent.getBudget()) + " / pax");
        }
    }

    private void onPostButtonClicked() {
        if (attachmentURLs.size() == 0) {
            progressDialog = Global.createProgressDialog(mContext);
            uploadImage(0);
        } else {
            onPostEvent();
        }
    }

    private void uploadImage(final int index) {
        if (mAlbumFiles == null) {
            if (editStatus == POST)
                onPostEvent();
            else if (editStatus == EDIT)
                onEditEvent();
        } else {
            if (index < mAlbumFiles.size()) {
                String name = (String) mAlbumFiles.get(index).getPath();
                Uri imageUri = Uri.parse(new File(name).toString());
                File file = new File(String.valueOf(imageUri));

                // Parsing any Media type file
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

                JGGAPIManager manager = JGGURLManager.createService(JGGAPIManager.class, mContext);
                Call<JGGPostAppResponse> call = manager.uploadAttachmentFile(fileToUpload);
                call.enqueue(new Callback<JGGPostAppResponse>() {
                    @Override
                    public void onResponse(Call<JGGPostAppResponse> call, Response<JGGPostAppResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getSuccess()) {
                                String url = response.body().getValue();
                                attachmentURLs.add(url);
                                uploadImage(index + 1);
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressDialog.dismiss();
                            int statusCode = response.code();
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
                progressDialog.dismiss();
                if (editStatus == POST)
                    onPostEvent();
                else if (editStatus == EDIT)
                    onEditEvent();
            }
        }
    }

    private void onPostEvent() {
        progressDialog = Global.createProgressDialog(mContext);

        mEvent.setAttachmentURLs(attachmentURLs);

        JGGAPIManager manager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        Call<JGGPostAppResponse> call = manager.createEvent(mEvent);
        call.enqueue(new Callback<JGGPostAppResponse>() {
            @Override
            public void onResponse(Call<JGGPostAppResponse> call, Response<JGGPostAppResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {

                        postedEventID = response.body().getValue();
                        mEvent.setID(postedEventID);

                        JGGAppManager.getInstance().setSelectedEvent(mEvent);

                        showAlertDialog();
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

    private void onEditEvent() {
        progressDialog = Global.createProgressDialog(mContext);

        mEvent.setAttachmentURLs(attachmentURLs);

        JGGAPIManager manager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        Call<JGGPostAppResponse> call = manager.editEvent(mEvent);
        call.enqueue(new Callback<JGGPostAppResponse>() {
            @Override
            public void onResponse(Call<JGGPostAppResponse> call, Response<JGGPostAppResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {

                        postedEventID = response.body().getValue();
                        mEvent.setID(postedEventID);

                        JGGAppManager.getInstance().setSelectedEvent(mEvent);

                        if (editStatus.equals(POST))
                            showAlertDialog();
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

    private void onEditButtonClicked() {
        if (attachmentURLs.size() == 0) {
            progressDialog = Global.createProgressDialog(mContext);
            uploadImage(0);
        } else {
            onEditEvent();
        }
    }

    private void showAlertDialog() {
        String message = '\n'
                + "Event reference no.: "
                + '\n'
                + postedEventID
                + '\n'
                + "Good luck!";
        JGGAlertView builder = new JGGAlertView(mContext,
                mContext.getResources().getString(R.string.alert_gc_posted_title),
                message,
                false,
                "",
                R.color.JGGPurple,
                R.color.JGGPurple10Percent,
                mContext.getResources().getString(R.string.alert_view_event),
                R.color.JGGPurple);
        alertDialog = builder.create();
        builder.setOnItemClickListener(new JGGAlertView.OnItemClickListener() {
            @Override
            public void onDoneButtonClick(View view) {
                if (view.getId() == R.id.btn_alert_cancel)
                    alertDialog.dismiss();
                else {
                    alertDialog.dismiss();
                    Intent intent = new Intent(mContext, GcPostedEventActivity.class);
                    intent.putExtra("is_post", true);
                    mContext.startActivity(intent);
                }
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    @OnClick(R.id.ll_main_describe)
    public void onClickDescribe() {
        fragment = CreateEventMainFragment.newInstance(DESCRIBE, POST);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.post_go_club_container, fragment)
                .addToBackStack("post_goclub")
                .commit();
    }
    @OnClick(R.id.ll_time_schedule)
    public void onClickViewSchedule() {
        fragment = CreateEventMainFragment.newInstance(TIME, POST);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.post_go_club_container, fragment)
                .addToBackStack("post_goclub")
                .commit();
    }

    @OnClick(R.id.ll_address)
    public void onClickAddress() {
        fragment = CreateEventMainFragment.newInstance(ADDRESS, POST);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.post_go_club_container, fragment)
                .addToBackStack("post_goclub")
                .commit();
    }

    @OnClick(R.id.ll_limit)
    public void onClickLimit() {
        fragment = CreateEventMainFragment.newInstance(LIMIT, POST);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.post_go_club_container, fragment)
                .addToBackStack("post_goclub")
                .commit();
    }

    @OnClick(R.id.ll_cost)
    public void onClickCost() {
        fragment = CreateEventMainFragment.newInstance(COST, POST);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.post_go_club_container, fragment)
                .addToBackStack("post_goclub")
                .commit();
    }

    @OnClick(R.id.ll_post_event)
    public void onClickPostEvent() {
        switch (editStatus) {
            case POST:
                onPostButtonClicked();
                break;
            case EDIT:
                onEditButtonClicked();
                break;
            case DUPLICATE:
                onPostButtonClicked();
                break;
            default:
                break;
        }

        this.showAlertDialog();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mActivity = ((CreateGoClubActivity) context);
    }

}
