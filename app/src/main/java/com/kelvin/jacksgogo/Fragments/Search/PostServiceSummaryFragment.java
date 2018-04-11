package com.kelvin.jacksgogo.Fragments.Search;

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

import com.kelvin.jacksgogo.Activities.Search.PostedServiceActivity;
import com.kelvin.jacksgogo.CustomView.Views.PostServiceTabbarView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global;
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

import static com.kelvin.jacksgogo.Fragments.Search.PostServiceSummaryFragment.PostEditStatus.EDIT;
import static com.kelvin.jacksgogo.Fragments.Search.PostServiceSummaryFragment.PostEditStatus.POST;
import static com.kelvin.jacksgogo.Utils.Global.JGGBudgetType.fixed;
import static com.kelvin.jacksgogo.Utils.Global.JGGBudgetType.from;
import static com.kelvin.jacksgogo.Utils.JGGAppManager.selectedAppointment;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.appointmentNewDate;

public class PostServiceSummaryFragment extends Fragment implements View.OnClickListener {

    private Context mContext;
    private OnFragmentInteractionListener mListener;

    private ImageView imgCategory;
    private TextView lblCategory;
    private LinearLayout btnDescribe;
    private LinearLayout btnPrice;
    private LinearLayout btnTimeSlot;
    private LinearLayout btnAddress;
    private LinearLayout btnPostService;
    private TextView lblPostService;
    private TextView lblDescribeTitle;
    private TextView lblDescribeDesc;
    private TextView lblPrice;
    private TextView lblTimeSlot;
    private TextView lblAddress;
    private TagContainerLayout describeTagView;
    private AlertDialog alertDialog;

    private PostEditStatus editStatus;
    private String postedServiceID;
    private ArrayList<AlbumFile> mAlbumFiles = new ArrayList<>();
    private ArrayList<String> attachmentURLs = new ArrayList<>();

    private ProgressDialog progressDialog;
    private PostServiceMainTabFragment fragment;

    public enum PostEditStatus {
        POST,
        EDIT,
        DUPLICATE
    }

    public void setEditStatus(PostEditStatus editStatus) {
        this.editStatus = editStatus;
    }

    public PostServiceSummaryFragment() {
        // Required empty public constructor
    }

    public static PostServiceSummaryFragment newInstance(String param1, String param2) {
        PostServiceSummaryFragment fragment = new PostServiceSummaryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        String postTime = appointmentNewDate(new Date());
        selectedAppointment.setPostOn(postTime);
        mAlbumFiles = selectedAppointment.getAlbumFiles();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_service_summary, container, false);

        initView(view);
        setDatas();

        return view;
    }

    private void initView(View view) {
        imgCategory = view.findViewById(R.id.img_category);
        lblCategory = view.findViewById(R.id.lbl_category_name);
        btnDescribe = view.findViewById(R.id.btn_post_main_describe);
        btnPrice = view.findViewById(R.id.btn_post_main_price);
        btnTimeSlot = view.findViewById(R.id.btn_post_main_time_slot);
        btnAddress = view.findViewById(R.id.btn_post_main_address);
        describeTagView = view.findViewById(R.id.post_service_main_tag_view);
        lblDescribeTitle = view.findViewById(R.id.lbl_post_describe_title);
        lblDescribeDesc = view.findViewById(R.id.lbl_post_describe_description);
        lblPrice = view.findViewById(R.id.lbl_post_main_price);
        lblTimeSlot = view.findViewById(R.id.lbl_post_main_time_slot);
        lblAddress = view.findViewById(R.id.lbl_post_main_address);
        btnPostService = view.findViewById(R.id.btn_post_service);
        lblPostService = view.findViewById(R.id.lbl_post_service);

        if (editStatus == EDIT) lblPostService.setText("Save Changes");
        btnDescribe.setOnClickListener(this);
        btnPrice.setOnClickListener(this);
        btnTimeSlot.setOnClickListener(this);
        btnAddress.setOnClickListener(this);
        btnPostService.setOnClickListener(this);
    }

    private void setDatas() {
        if (selectedAppointment != null) {
            // Category
            Picasso.with(mContext)
                    .load(selectedAppointment.getCategory().getImage())
                    .placeholder(null)
                    .into(imgCategory);
            lblCategory.setText(selectedAppointment.getCategory().getName());
            // Describe
            lblDescribeTitle.setText(selectedAppointment.getTitle());
            lblDescribeDesc.setText(selectedAppointment.getDescription());
            // Tag
            String tags = selectedAppointment.getTags();
            if (tags != null && tags.length() > 0) {
                String [] strings = tags.split(",");
                describeTagView.setTags(Arrays.asList(strings));
            }
            // Price
            String price = "";
            if (selectedAppointment.getAppointmentType() == 1) {
                if (selectedAppointment.getBudgetType() == fixed)
                    price = "Fixed $ " + selectedAppointment.getBudget().toString();
                else if (selectedAppointment.getBudgetType() == from)
                    price = "From $ " + selectedAppointment.getBudgetFrom().toString()
                            + " "
                            + "to $ " + selectedAppointment.getBudgetTo().toString();
                else
                    price = "No set";
            } else if (selectedAppointment.getAppointmentType() >= 2) {
                price = String.valueOf(selectedAppointment.getAppointmentType()) + " Services, ";
                if (selectedAppointment.getBudget() != null)
                    price = price + "$ " + String.valueOf(selectedAppointment.getBudget()) + " per service";
                else
                    price = "$ " + String.valueOf(selectedAppointment.getBudget());
            }
            lblPrice.setText(price);
            // Address
            lblAddress.setText(selectedAppointment.getAddress().getFullAddress());
        } else {
            lblDescribeTitle.setText("No title");
            lblDescribeDesc.setText("");
            describeTagView.removeAllTags();
            lblPrice.setText("No set");
            lblTimeSlot.setText("View Time Slots");
            lblAddress.setText("No set");
        }
    }

    private void onPostButtonClicked() {
        if (attachmentURLs.size() == 0) {
            progressDialog = Global.createProgressDialog(mContext);
            uploadImage(0);
        } else {
            onPostService();
        }
    }

    private void uploadImage(final int index) {
        if (mAlbumFiles == null) {
            if (editStatus == POST)
                onPostService();
            else if (editStatus == EDIT)
                onEditService();
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
                    onPostService();
                else if (editStatus == EDIT)
                    onEditService();
            }
        }
    }

    private void onPostService() {
        selectedAppointment.setAttachmentURLs(attachmentURLs);
        JGGAPIManager manager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        Call<JGGPostAppResponse> call = manager.postNewService(selectedAppointment);
        call.enqueue(new Callback<JGGPostAppResponse>() {
            @Override
            public void onResponse(Call<JGGPostAppResponse> call, Response<JGGPostAppResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        postedServiceID = response.body().getValue();
                        selectedAppointment.setID(postedServiceID);
                        showAlertDialog();
                    } else {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode = response.code();
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
            onEditService();
        }
    }

    private void onEditService() {
        JGGAPIManager manager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        Call<JGGPostAppResponse> call = manager.editService(selectedAppointment);
        call.enqueue(new Callback<JGGPostAppResponse>() {
            @Override
            public void onResponse(Call<JGGPostAppResponse> call, Response<JGGPostAppResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        postedServiceID = response.body().getValue();
                        selectedAppointment.setID(postedServiceID);
                        showAlertDialog();
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

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btn_post_service) {
            switch (editStatus) {
                case POST:
                    onPostButtonClicked();
                    //showAlertDialog();
                    break;
                case EDIT:
                    onEditButtonClicked();
//                    /showAlertDialog();
                    break;
                case DUPLICATE:
                    onPostButtonClicked();
                    //showAlertDialog();

                    /*Intent intent = new Intent(mContext, PostServiceActivity.class);
                    intent.putExtra(EDIT_STATUS, POST);
                    intent.putExtra(APPOINTMENT_TYPE, SERVICES);
                    startActivity(intent);*/
                    break;
                default:
                    break;
            }
            return;
        } else if (view.getId() == R.id.btn_alert_ok) {
            alertDialog.dismiss();
            Intent intent = new Intent(mContext, PostedServiceActivity.class);
            intent.putExtra("is_post", true);
            mContext.startActivity(intent);
            return;
        } else if (view.getId() == R.id.btn_post_main_describe) {
            fragment = PostServiceMainTabFragment.newInstance(PostServiceTabbarView.PostServiceTabName.DESCRIBE);
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.post_service_container, fragment)
                    .addToBackStack("post_service")
                    .commit();
        } else if (view.getId() == R.id.btn_post_main_price) {
            fragment = PostServiceMainTabFragment.newInstance(PostServiceTabbarView.PostServiceTabName.TIME);
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.post_service_container, fragment)
                    .addToBackStack("post_service")
                    .commit();
        } else if (view.getId() == R.id.btn_post_main_time_slot) {
            fragment = PostServiceMainTabFragment.newInstance(PostServiceTabbarView.PostServiceTabName.ADDRESS);
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.post_service_container, fragment)
                    .addToBackStack("post_service")
                    .commit();
        } else if (view.getId() == R.id.btn_post_main_address) {
            fragment = PostServiceMainTabFragment.newInstance(PostServiceTabbarView.PostServiceTabName.REPORT);
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.post_service_container, fragment)
                    .addToBackStack("post_service")
                    .commit();
        }
        fragment.setEditStatus(editStatus);
    }

    private void showAlertDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = this.getLayoutInflater();

        View alertView = inflater.inflate(R.layout.jgg_alert_view, null);
        builder.setView(alertView);
        alertDialog = builder.create();
        TextView cancelButton = (TextView) alertView.findViewById(R.id.btn_alert_cancel);
        TextView okButton = (TextView) alertView.findViewById(R.id.btn_alert_ok);
        TextView title = (TextView) alertView.findViewById(R.id.lbl_alert_titile);
        TextView desc = (TextView) alertView.findViewById(R.id.lbl_alert_description);

        title.setText(R.string.alert_service_posted_title);
        String message = "Service reference no: "
                + '\n'
                + postedServiceID
                + '\n'
                + '\n'
                + "Our team will verify your submission and get back to you soon! ";
        desc.setText(message);
        okButton.setText(R.string.alert_view_service_button);
        okButton.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGGreen));
        cancelButton.setVisibility(View.GONE);

        okButton.setOnClickListener(this);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
