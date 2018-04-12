package com.kelvin.jacksgogo.Fragments.Search;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.Adapter.Services.JGGImageGalleryAdapter;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Edit.EditJobMainListCell;
import com.kelvin.jacksgogo.CustomView.Views.JGGAlertView;
import com.kelvin.jacksgogo.CustomView.Views.PostServiceTabbarView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGQuotationModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGPostAppResponse;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.JGGAppManager.selectedQuotation;
import static com.kelvin.jacksgogo.Utils.Global.reportTypeName;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.appointmentMonthDate;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.appointmentNewDate;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getDayMonthYear;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getTimePeriodString;

public class PostQuotationSummaryFragment extends Fragment implements PostQuotationMainTabFragment.OnFragmentInteractionListener, View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    private RecyclerView recyclerView;
    private LinearLayout btnDescribe;
    private TextView lblTitle;
    private TextView lblDescribe;
    private TextView btnPostQuotation;

    private PostQuotationMainTabFragment postQuotationMainTabFragment;
    private JGGQuotationModel mQuotation;
    private JGGImageGalleryAdapter mAdapter;
    private ArrayList<AlbumFile> mAlbumFiles;
    private String postedQuotationID;
    private boolean isRequest; // Request Or Edit
    private android.app.AlertDialog alertDialog;
    private ProgressDialog progressDialog;

    public PostQuotationSummaryFragment() {
        // Required empty public constructor
    }

    public static PostQuotationSummaryFragment newInstance(boolean b) {
        PostQuotationSummaryFragment fragment = new PostQuotationSummaryFragment();
        Bundle args = new Bundle();
        args.putBoolean("isRequest", b);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_post_quotation_summary, container, false);

        mQuotation = selectedQuotation;
        String postTime = appointmentNewDate(new Date());
        mQuotation.setPostOn(postTime);

        initView(view);
        initRecyclerView(view);

        return view;
    }

    private void initView(View view) {

        btnDescribe = view.findViewById(R.id.btn_edit_job_tag);
        lblTitle = view.findViewById(R.id.lbl_quotation_desc_title);
        lblDescribe = view.findViewById(R.id.lbl_quotation_desc);
        btnPostQuotation = view.findViewById(R.id.btn_post_quotation);
        btnDescribe.setOnClickListener(this);
        btnPostQuotation.setOnClickListener(this);

        // Quotation Describe
        lblTitle.setText(mQuotation.getTitle());
        lblDescribe.setText(mQuotation.getDescription());

        // Quotation Time
        LinearLayout timeLayout = (LinearLayout)view.findViewById(R.id.job_type_layout);
        EditJobMainListCell timeView = new EditJobMainListCell(mContext);
        String day = getDayMonthYear(appointmentMonthDate(mQuotation.getSessions().get(0).getStartOn()));
        String startTime = getTimePeriodString(appointmentMonthDate(mQuotation.getSessions().get(0).getStartOn()));
        String endTime = getTimePeriodString(appointmentMonthDate(mQuotation.getSessions().get(0).getEndOn()));
        String date = day + " " + startTime + " - " + endTime;
        timeView.setData("Time", "One-time Job", date);
        timeView.setOnItemClickListener(new EditJobMainListCell.OnItemClickListener() {
            @Override
            public void onItemClick(View item) {
                onShowEditJobFragment(PostServiceTabbarView.PostServiceTabName.TIME);
            }
        });
        timeLayout.addView(timeView);

        // Quotation Address
        LinearLayout addressLayout = (LinearLayout)view.findViewById(R.id.job_address_layout);
        EditJobMainListCell addressView = new EditJobMainListCell(mContext);
        String address = mQuotation.getAddress().getFullAddress();
        addressView.setData("Address", null, address);
        addressView.setOnItemClickListener(new EditJobMainListCell.OnItemClickListener() {
            @Override
            public void onItemClick(View item) {
                onShowEditJobFragment(PostServiceTabbarView.PostServiceTabName.ADDRESS);
            }
        });
        addressLayout.addView(addressView);

        // Quotation Report
        LinearLayout reportLayout = (LinearLayout)view.findViewById(R.id.job_report_type_layout);
        EditJobMainListCell reportView = new EditJobMainListCell(mContext);
        String reportType = reportTypeName(mQuotation.getReportType());
        reportView.setData("Report", null, reportType);
        reportView.setOnItemClickListener(new EditJobMainListCell.OnItemClickListener() {
            @Override
            public void onItemClick(View item) {
                onShowEditJobFragment(PostServiceTabbarView.PostServiceTabName.REPORT);
            }
        });
        reportLayout.addView(reportView);

    }

    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.edit_job_main_recycler_view);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));

        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int width = display.getWidth();

        int itemSize = (width) / 4;
        mAdapter = new JGGImageGalleryAdapter(mContext, itemSize, true, new com.yanzhenjie.album.impl.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
        mAdapter.notifyDataChanged(mAlbumFiles);
        recyclerView.setAdapter(mAdapter);
    }

    private void onShowEditJobFragment(PostServiceTabbarView.PostServiceTabName status) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();

        if (isRequest) {
            postQuotationMainTabFragment = PostQuotationMainTabFragment.newInstance(status, true);
            ft.replace(R.id.request_quotation_container, postQuotationMainTabFragment, postQuotationMainTabFragment.getTag());
        } else {
            postQuotationMainTabFragment = PostQuotationMainTabFragment.newInstance(status, false);
            ft.replace(R.id.app_detail_container, postQuotationMainTabFragment, postQuotationMainTabFragment.getTag());
        }
        postQuotationMainTabFragment.setmListener(PostQuotationSummaryFragment.this);
        ft.addToBackStack("edit_job");
        ft.commit();
    }

    private void onRequestQuotation() {
        progressDialog = Global.createProgressDialog(mContext);
        JGGAPIManager manager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        Call<JGGPostAppResponse> call = manager.sendQuotation(mQuotation);
        call.enqueue(new Callback<JGGPostAppResponse>() {
            @Override
            public void onResponse(Call<JGGPostAppResponse> call, Response<JGGPostAppResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        postedQuotationID = response.body().getValue();
                        selectedQuotation.setID(postedQuotationID);
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
                progressDialog.dismiss();
            }
        });
    }

    private void showAlertDialog() {
        JGGAlertView builder = new JGGAlertView(mContext,
                mContext.getResources().getString(R.string.alert_job_posted_title),
                "Job reference no: " + postedQuotationID,
                false,
                "",
                R.color.JGGGreen,
                R.color.JGGGreen10Percent,
                mContext.getResources().getString(R.string.alert_view_job_button),
                R.color.JGGGreen);
        alertDialog = builder.create();
        builder.setOnItemClickListener(new JGGAlertView.OnItemClickListener() {
            @Override
            public void onDoneButtonClick(View view) {
                if (view.getId() == R.id.btn_alert_cancel)
                    alertDialog.dismiss();
                else
                    alertDialog.dismiss();
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_edit_job_tag) {
            onShowEditJobFragment(PostServiceTabbarView.PostServiceTabName.DESCRIBE);
        } else if (view.getId() == R.id.btn_post_quotation) {
            onRequestQuotation();
            //showAlertDialog();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
        if (getArguments() != null) {
            isRequest = getArguments().getBoolean("isRequest");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
