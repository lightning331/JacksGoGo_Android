package com.kelvin.jacksgogo.Fragments.Jobs;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.Jobs.JobReportActivity;
import com.kelvin.jacksgogo.Adapter.Services.JGGImageGalleryAdapter;
import com.kelvin.jacksgogo.R;
import com.yanzhenjie.album.AlbumFile;

import java.util.List;

import static com.kelvin.jacksgogo.Utils.Global.JGGUserType.PROVIDER;

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

    private List<AlbumFile> startJobImage;
    private AlbumFile startJob;

    private AlertDialog alertDialog;
    private String mUserType;

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

    private void onShowVerifyJobDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View alertView = inflater.inflate(R.layout.jgg_alert_view, null);
        builder.setView(alertView);
        alertDialog = builder.create();
        TextView cancelButton = (TextView) alertView.findViewById(R.id.btn_alert_cancel);
        TextView okButton = (TextView) alertView.findViewById(R.id.btn_alert_ok);
        TextView title = (TextView) alertView.findViewById(R.id.lbl_alert_titile);
        TextView desc = (TextView) alertView.findViewById(R.id.lbl_alert_description);

        title.setText(R.string.alert_verify_job_completed_title);
        desc.setText(R.string.alert_verify_job_completed_desc);
        okButton.setText(R.string.alert_verify_job_completed_ok);
        okButton.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGGreen));
        okButton.setTextColor(ContextCompat.getColor(mContext, R.color.JGGWhite));
        cancelButton.setOnClickListener(this);
        okButton.setOnClickListener(this);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_verify_completed) {
            onShowVerifyJobDialog();
        } else if (view.getId() == R.id.btn_dispute) {

        } else if (view.getId() == R.id.btn_alert_cancel) {
            alertDialog.dismiss();
        } else if (view.getId() == R.id.btn_alert_ok) {
            alertDialog.dismiss();
            mActivity.finish();
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
