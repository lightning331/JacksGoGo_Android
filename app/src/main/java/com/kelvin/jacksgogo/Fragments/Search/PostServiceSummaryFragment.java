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

import com.kelvin.jacksgogo.Activities.Search.PostServiceActivity;
import com.kelvin.jacksgogo.Activities.Search.PostedServiceActivity;
import com.kelvin.jacksgogo.CustomView.Views.PostServiceTabbarView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCreatingJobModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGPostJobResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import co.lujun.androidtagview.TagContainerLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Fragments.Jobs.PostJobSummaryFragment.getDateString;
import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.SERVICES;

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
    private TextView lblDescribeTitle;
    private TextView lblDescribeDesc;
    private TextView lblPrice;
    private TextView lblTimeSlot;
    private TextView lblAddress;
    private TagContainerLayout describeTagView;
    private AlertDialog alertDialog;

    private PostEditStatus editStatus;
    private JGGCategoryModel selectedCategory;
    private JGGCreatingJobModel creatingService;
    private ProgressDialog progressDialog;
    private ArrayList<String> attachmentURLs;
    private String postedServiceID;

    public enum PostEditStatus {
        NONE,
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
        attachmentURLs = new ArrayList<>();
        if (editStatus == PostEditStatus.NONE) {
            selectedCategory = ((PostServiceActivity)mContext).selectedCategory;
            ((PostServiceActivity)mContext).creatingAppointment.setCategoryID(selectedCategory.getID());
            creatingService = ((PostServiceActivity)mContext).creatingAppointment;
            creatingService.setAttachmentURLs(attachmentURLs);
        } else if (editStatus == PostEditStatus.EDIT
                || editStatus == PostEditStatus.DUPLICATE) {

        }
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

        btnDescribe.setOnClickListener(this);
        btnPrice.setOnClickListener(this);
        btnTimeSlot.setOnClickListener(this);
        btnAddress.setOnClickListener(this);
        btnPostService.setOnClickListener(this);
    }

    private void setDatas() {
        if (creatingService != null) {
            // Category
            Picasso.with(mContext)
                    .load(selectedCategory.getImage())
                    .placeholder(null)
                    .into(imgCategory);
            lblCategory.setText(selectedCategory.getName());
            // Describe
            lblDescribeTitle.setText(creatingService.getTitle());
            lblDescribeDesc.setText(creatingService.getDescription());
            String [] strings = creatingService.getTags().split(",");
            describeTagView.setTags(Arrays.asList(strings));
            // Price
            String price = "";
            if (creatingService.getSelectedServiceType() == 1) {
                if (creatingService.getSelectedPriceType() == 1)
                    price = "Fixed $ " + creatingService.getBudget().toString();
                else if (creatingService.getSelectedPriceType() == 2)
                    price = "From $ " + creatingService.getBudgetFrom().toString()
                            + " "
                            + "to $ " + creatingService.getBudgetTo().toString();
                else
                    price = "No set";
            } else {
                price = String.valueOf(creatingService.getServiceType()) + " Services, ";
                if (creatingService.getBudget() != null)
                    price = price + "$ " + String.valueOf(creatingService.getBudget()) + " per service";
            }
            lblPrice.setText(price);
            // Address
            lblAddress.setText(creatingService.getAddress().getFullAddress());
        } else {
            lblDescribeTitle.setText("No title");
            lblDescribeDesc.setText("");
            describeTagView.removeAllTags();
            lblPrice.setText("No set");
            lblTimeSlot.setText("View Time Slots");
            lblAddress.setText("No set");
        }
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
                + postedServiceID
                + '\n'
                + "Our team will verify your submission and get back to you soon! ";
        desc.setText(message);
        okButton.setText(R.string.alert_view_service_button);
        okButton.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGGreen));
        cancelButton.setVisibility(View.GONE);

        okButton.setOnClickListener(this);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    private void onPostService() {
        progressDialog = Global.createProgressDialog(mContext);
        JGGAPIManager manager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        Call<JGGPostJobResponse> call = manager.postNewService(creatingService);
        call.enqueue(new Callback<JGGPostJobResponse>() {
            @Override
            public void onResponse(Call<JGGPostJobResponse> call, Response<JGGPostJobResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    postedServiceID = response.body().getValue();
                    showAlertDialog();
                } else {
                    int statusCode  = response.code();
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGPostJobResponse> call, Throwable t) {
                Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btn_post_service) {
            switch (editStatus) {
                case NONE:
                    onPostService();
                    break;
                case EDIT:
                    showAlertDialog();
                    break;
                case DUPLICATE:
                    Intent intent = new Intent(mContext, PostServiceActivity.class);
                    intent.putExtra("EDIT_STATUS", "None");
                    intent.putExtra(APPOINTMENT_TYPE, SERVICES);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        } else if (view.getId() == R.id.btn_alert_ok) {
            alertDialog.dismiss();
            Intent intent = new Intent(mContext, PostedServiceActivity.class);
            intent.putExtra("is_post", true);
            mContext.startActivity(intent);
        } else if (view.getId() == R.id.btn_post_main_describe) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.post_service_container, PostServiceMainTabFragment.newInstance(PostServiceTabbarView.TabName.DESCRIBE))
                    .addToBackStack("post_service")
                    .commit();
        } else if (view.getId() == R.id.btn_post_main_price) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.post_service_container, PostServiceMainTabFragment.newInstance(PostServiceTabbarView.TabName.TIME))
                    .addToBackStack("post_service")
                    .commit();
        } else if (view.getId() == R.id.btn_post_main_time_slot) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.post_service_container, PostServiceMainTabFragment.newInstance(PostServiceTabbarView.TabName.ADDRESS))
                    .addToBackStack("post_service")
                    .commit();
        } else if (view.getId() == R.id.btn_post_main_address) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.post_service_container, PostServiceMainTabFragment.newInstance(PostServiceTabbarView.TabName.REPORT))
                    .addToBackStack("post_service")
                    .commit();
        }
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
