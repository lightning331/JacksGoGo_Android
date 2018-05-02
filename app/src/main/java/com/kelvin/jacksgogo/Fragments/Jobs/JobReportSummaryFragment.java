package com.kelvin.jacksgogo.Fragments.Jobs;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.kelvin.jacksgogo.Activities.Jobs.JobReportActivity;
import com.kelvin.jacksgogo.Adapter.Services.JGGImageGalleryAdapter;
import com.kelvin.jacksgogo.CustomView.Views.JGGAlertView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGReportResultModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGPostAppResponse;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.Global.JGGUserType.PROVIDER;
import static com.kelvin.jacksgogo.Utils.Global.createProgressDialog;

public class JobReportSummaryFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private Context mContext;
    private JobReportActivity mActivity;

    private RecyclerView startRecyclerView;
    private RecyclerView endRecyclerView;
    private RecyclerView billableRecyclerView;
    private LinearLayout verifyBtnLayout;
    private LinearLayout startJobLayout;
    private LinearLayout endJobLayout;
    private TextView btnVerify;
    private TextView btnDispute;

    private AlertDialog alertDialog;
    private ProgressDialog progressDialog;

    private List<AlbumFile> startJobImage;
    private AlbumFile startJob;
    private String mUserType;
    private ArrayList<JGGReportResultModel> mReportResults;

    public JobReportSummaryFragment() {
        // Required empty public constructor
    }

    public static JobReportSummaryFragment newInstance(String userType) {
        JobReportSummaryFragment fragment = new JobReportSummaryFragment();
        Bundle args = new Bundle();
        args.putString("jgg_usertype", userType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    public void setReportResult(ArrayList<JGGReportResultModel> reportResults) {
        mReportResults = reportResults;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_job_report_summary, container, false);

        initView(view);
        initStartRecyclerView(view);

        return view;
    }

    private void initView(View view) {
        verifyBtnLayout = view.findViewById(R.id.verify_button_layout);
        startJobLayout = view.findViewById(R.id.start_job_layout);
        endJobLayout = view.findViewById(R.id.end_job_layout);
        btnVerify = view.findViewById(R.id.btn_verify_completed);
        btnVerify.setOnClickListener(this);
        btnDispute = view.findViewById(R.id.btn_dispute);

        if (mUserType.equals(PROVIDER.toString())) {
            verifyBtnLayout.setVisibility(View.GONE);
            startJobLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGCyan10Percent));
            endJobLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGCyan10Percent));
        }
    }

    private void initStartRecyclerView(View view) {
        startRecyclerView = view.findViewById(R.id.job_report_start_recycler_view);
        startRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));

        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int width = display.getWidth();

        int itemSize = (width) / 4;
        JGGImageGalleryAdapter mAdapter = new JGGImageGalleryAdapter(mContext, itemSize, true, new com.yanzhenjie.album.impl.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
        //mAdapter.notifyDataSetChanged();
        startRecyclerView.setAdapter(mAdapter);
    }

    private void onApproveReportResult() {
        progressDialog = createProgressDialog(mContext);
        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        Call<JGGPostAppResponse> call = apiManager.approveReport(mReportResults.get(0).getID());
        call.enqueue(new Callback<JGGPostAppResponse>() {
            @Override
            public void onResponse(Call<JGGPostAppResponse> call, Response<JGGPostAppResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        String reportID = response.body().getValue();
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

    private void onDisputeReportResult() {
        progressDialog = createProgressDialog(mContext);
        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        Call<JGGPostAppResponse> call = apiManager.rejectReport(mReportResults.get(0).getID());
        call.enqueue(new Callback<JGGPostAppResponse>() {
            @Override
            public void onResponse(Call<JGGPostAppResponse> call, Response<JGGPostAppResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        String reportID = response.body().getValue();
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

    private void onShowVerifyReportDialog() {
        JGGAlertView builder = new JGGAlertView(mContext,
                mContext.getResources().getString(R.string.alert_verify_job_completed_title),
                mContext.getResources().getString(R.string.alert_verify_job_completed_desc),
                false,
                mContext.getResources().getString(R.string.alert_cancel),
                R.color.JGGGreen,
                R.color.JGGGreen10Percent,
                mContext.getResources().getString(R.string.alert_verify_job_completed_ok),
                R.color.JGGGreen);
        alertDialog = builder.create();
        builder.setOnItemClickListener(new JGGAlertView.OnItemClickListener() {
            @Override
            public void onDoneButtonClick(View view) {
                if (view.getId() == R.id.btn_alert_cancel)
                    alertDialog.dismiss();
                else {
                    alertDialog.dismiss();
                    onApproveReportResult();
                }
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_verify_completed) {
            onShowVerifyReportDialog();
        } else if (view.getId() == R.id.btn_dispute) {

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
        mActivity = ((JobReportActivity) mContext);
        if (getArguments() != null) {
            mUserType = getArguments().getString("jgg_usertype");
        }
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
