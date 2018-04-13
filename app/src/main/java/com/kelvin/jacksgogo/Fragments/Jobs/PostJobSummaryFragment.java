package com.kelvin.jacksgogo.Fragments.Jobs;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.Activities.Jobs.PostedJobActivity;
import com.kelvin.jacksgogo.Activities.Search.PostServiceActivity;
import com.kelvin.jacksgogo.CustomView.Views.PostJobTabView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGGetAppResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGPostAppResponse;
import com.squareup.picasso.Picasso;
import com.yanzhenjie.album.AlbumFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import co.lujun.androidtagview.TagContainerLayout;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.Global.PostStatus.EDIT;
import static com.kelvin.jacksgogo.Utils.Global.PostStatus.POST;
import static com.kelvin.jacksgogo.Utils.Global.reportTypeName;
import static com.kelvin.jacksgogo.Utils.Global.PostStatus;
import static com.kelvin.jacksgogo.Utils.JGGAppManager.selectedAppointment;
import static com.kelvin.jacksgogo.Utils.JGGAppManager.selectedCategory;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.appointmentNewDate;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getAppointmentBudgetWithString;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getAppointmentTime;

public class PostJobSummaryFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private Context mContext;
    private PostServiceActivity mActivity;

    private ImageView imgCategory;
    private TextView lblCategory;
    private LinearLayout btnDescribe;
    private LinearLayout btnTime;
    private LinearLayout btnAddress;
    private LinearLayout btnBudget;
    private LinearLayout btnReport;
    private LinearLayout btnPostJob;
    private TextView lblDescribeTitle;
    private TextView lblDescribeDesc;
    private TextView lblTime;
    private TextView lblAddress;
    private TextView lblBudget;
    private TextView lblReport;
    private TagContainerLayout describeTagView;
    private TextView lblPostJob;

    private AlertDialog alertDialog;
    private PostStatus jobStatus;
    private JGGCategoryModel category;
    private JGGAppointmentModel creatingJob;
    private ProgressDialog progressDialog;
    private ArrayList<AlbumFile> mAlbumFiles = new ArrayList<>();
    private ArrayList<String> attachmentURLs = new ArrayList<>();
    private String postedJobID;

    private PostJobMainTabFragment fragment;

    public void setEditStatus(PostStatus editStatus) {
        this.jobStatus = editStatus;
    }

    public PostJobSummaryFragment() {
        // Required empty public constructor
    }

    public static PostJobSummaryFragment newInstance(String param1, String param2) {
        PostJobSummaryFragment fragment = new PostJobSummaryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_job_summary, container, false);

        initView(view);
        setDatas();

        return view;
    }

    private void initView(View view) {
        imgCategory = view.findViewById(R.id.img_category);
        lblCategory = view.findViewById(R.id.lbl_category_name);
        btnDescribe = view.findViewById(R.id.btn_post_job_summary_describe);
        btnTime = view.findViewById(R.id.btn_post_job_summary_time);
        btnAddress = view.findViewById(R.id.btn_post_job_summary_address);
        btnBudget = view.findViewById(R.id.btn_post_job_summary_budget);
        btnReport = view.findViewById(R.id.btn_post_job_summary_report);
        describeTagView = view.findViewById(R.id.post_job_summary_tag_view);
        lblDescribeTitle = view.findViewById(R.id.lbl_post_job_describe_title);
        lblDescribeDesc = view.findViewById(R.id.lbl_post_job_describe_desc);
        lblTime = view.findViewById(R.id.lbl_post_job_time);
        lblAddress = view.findViewById(R.id.lbl_post_job_address);
        lblBudget = view.findViewById(R.id.lbl_post_job_budget);
        lblReport = view.findViewById(R.id.lbl_post_job_report);
        btnPostJob = view.findViewById(R.id.btn_post_job);
        lblPostJob = view.findViewById(R.id.lbl_post_job);

        if (jobStatus == EDIT) lblPostJob.setText("Save Changes");

        category = selectedCategory;
        String postTime = appointmentNewDate(new Date());
        selectedAppointment.setPostOn(postTime);
        mAlbumFiles = selectedAppointment.getAlbumFiles();
        creatingJob = selectedAppointment;

        btnDescribe.setOnClickListener(this);
        btnTime.setOnClickListener(this);
        btnAddress.setOnClickListener(this);
        btnBudget.setOnClickListener(this);
        btnReport.setOnClickListener(this);
        btnPostJob.setOnClickListener(this);
    }

    private void setDatas() {

        if (creatingJob != null) {
            // Category
            Picasso.with(mContext)
                    .load(category.getImage())
                    .placeholder(null)
                    .into(imgCategory);
            lblCategory.setText(category.getName());
            // Describe
            lblDescribeTitle.setText(creatingJob.getTitle());
            lblDescribeDesc.setText(creatingJob.getDescription());
            String tags = creatingJob.getTags();
            if (tags != null && tags.length() > 0) {
                String [] strings = tags.split(",");
                describeTagView.setTags(Arrays.asList(strings));
            }
            // Address
            lblAddress.setText(creatingJob.getAddress().getFullAddress());
            // Budget
            lblBudget.setText(getAppointmentBudgetWithString(creatingJob));
            // Report
            lblReport.setText(reportTypeName(creatingJob.getReportType()));
            // Time
            lblTime.setText(getAppointmentTime(creatingJob));
        } else {
            lblDescribeTitle.setText("No title");
            lblDescribeDesc.setText("");
            describeTagView.removeAllTags();
            lblTime.setText("No set");
            lblAddress.setText("No set");
            lblBudget.setText("No set");
            lblReport.setText("No set");
        }
    }

    private void uploadImage(final int index) {
        if (mAlbumFiles == null) {
            if (jobStatus == POST)
                onPostJob();
            else if (jobStatus == EDIT)
                onEditJob();
        } else {
            progressDialog = Global.createProgressDialog(mContext);
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
                if (jobStatus == POST)
                    onPostJob();
                else if (jobStatus == EDIT)
                    onEditJob();
            }
        }
    }

    private void onPostButtonClicked() {
        if (attachmentURLs.size() == 0) {
            progressDialog = Global.createProgressDialog(mContext);
            uploadImage(0);
        } else {
            onPostJob();
        }
    }

    private void onPostJob() {
        selectedAppointment.setAttachmentURLs(attachmentURLs);
        creatingJob.setAttachmentURLs(attachmentURLs);
        JGGAPIManager manager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        Call<JGGPostAppResponse> call = manager.postNewJob(creatingJob);
        call.enqueue(new Callback<JGGPostAppResponse>() {
            @Override
            public void onResponse(Call<JGGPostAppResponse> call, Response<JGGPostAppResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        postedJobID = response.body().getValue();
                        selectedAppointment.setID(postedJobID);
                        showPostJobAlertDialog(false);
                    } else {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode  = response.code();
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGPostAppResponse> call, Throwable t) {
                Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onEditButtonClicked() {
        if (attachmentURLs.size() == 0) {
            progressDialog = Global.createProgressDialog(mContext);
            uploadImage(0);
        } else {
            onEditJob();
        }
    }

    private void onEditJob() {
        JGGAPIManager manager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        Call<JGGPostAppResponse> call = manager.editJob(creatingJob);
        call.enqueue(new Callback<JGGPostAppResponse>() {
            @Override
            public void onResponse(Call<JGGPostAppResponse> call, Response<JGGPostAppResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        postedJobID = response.body().getValue();
                        selectedAppointment.setID(postedJobID);
                        showPostJobAlertDialog(true);
                    } else {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode  = response.code();
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGPostAppResponse> call, Throwable t) {
                Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getJobByID() {
        progressDialog = Global.createProgressDialog(mContext);
        JGGAPIManager apiManager = JGGURLManager.getClient().create(JGGAPIManager.class);
        Call<JGGGetAppResponse> call = apiManager.getJobByID(selectedAppointment.getID());
        call.enqueue(new Callback<JGGGetAppResponse>() {
            @Override
            public void onResponse(Call<JGGGetAppResponse> call, Response<JGGGetAppResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {

                    } else {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode  = response.code();
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGGetAppResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showPostJobAlertDialog(final boolean isEdit) {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
        LayoutInflater inflater = this.getLayoutInflater();

        View alertView = inflater.inflate(R.layout.jgg_alert_view, null);
        builder.setView(alertView);
        alertDialog = builder.create();

        TextView cancelButton = (TextView) alertView.findViewById(R.id.btn_alert_cancel);
        TextView okButton = (TextView) alertView.findViewById(R.id.btn_alert_ok);
        TextView title = (TextView) alertView.findViewById(R.id.lbl_alert_titile);
        TextView desc = (TextView) alertView.findViewById(R.id.lbl_alert_description);

        if (isEdit) {
            title.setText(R.string.alert_job_edit_title);
            desc.setText(R.string.alert_job_edit_desc);
            okButton.setText(R.string.alert_ok);
            okButton.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGGreen));
        } else {
            title.setText(R.string.alert_job_posted_title);
            desc.setText("Job reference no: " + postedJobID + '\n' +  '\n' + "Good luck!");
            okButton.setText(R.string.alert_view_job_button);
            okButton.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGCyan));
        }
        cancelButton.setVisibility(View.GONE);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                //getActivity().finish();
                Intent intent = new Intent(mContext, PostedJobActivity.class);
                mContext.startActivity(intent);
            }
        });

        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_post_job) {
            switch (jobStatus) {
                case POST:
                    onPostButtonClicked();
                    //showPostJobAlertDialog(false);
                    break;
                case EDIT:
                    onEditButtonClicked();
                    //showPostJobAlertDialog(true);
                    break;
                case DUPLICATE:
//                    Intent intent = new Intent(mContext, PostServiceActivity.class);
//                    intent.putExtra(EDIT_STATUS, POST);
//                    intent.putExtra(APPOINTMENT_TYPE, SERVICES);
//                    startActivity(intent);
                    break;
                default:
                    break;
            }
            return;
        } else if (view.getId() == R.id.btn_post_job_summary_describe) {
            fragment = PostJobMainTabFragment.newInstance(PostJobTabView.PostJobTabName.DESCRIBE, POST);
            if (jobStatus == EDIT) {
                fragment = PostJobMainTabFragment.newInstance(PostJobTabView.PostJobTabName.DESCRIBE, EDIT);
            }
        } else if (view.getId() == R.id.btn_post_job_summary_time) {
            fragment = PostJobMainTabFragment.newInstance(PostJobTabView.PostJobTabName.TIME, POST);
            if (jobStatus == EDIT) {
                fragment = PostJobMainTabFragment.newInstance(PostJobTabView.PostJobTabName.TIME, EDIT);
            }
        } else if (view.getId() == R.id.btn_post_job_summary_address) {
            fragment = PostJobMainTabFragment.newInstance(PostJobTabView.PostJobTabName.ADDRESS, POST);
            if (jobStatus == EDIT) {
                fragment = PostJobMainTabFragment.newInstance(PostJobTabView.PostJobTabName.ADDRESS, EDIT);
            }
        }  else if (view.getId() == R.id.btn_post_job_summary_budget) {
            fragment = PostJobMainTabFragment.newInstance(PostJobTabView.PostJobTabName.BUDGET, POST);
            if (jobStatus == EDIT) {
                fragment = PostJobMainTabFragment.newInstance(PostJobTabView.PostJobTabName.BUDGET, EDIT);
            }
        } else if (view.getId() == R.id.btn_post_job_summary_report) {
            fragment = PostJobMainTabFragment.newInstance(PostJobTabView.PostJobTabName.REPORT, POST);
            if (jobStatus == EDIT) {
                fragment = PostJobMainTabFragment.newInstance(PostJobTabView.PostJobTabName.REPORT, EDIT);
            }
        }
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.post_service_container, fragment)
                .addToBackStack("post_job")
                .commit();
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mActivity = ((PostServiceActivity) mContext);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
