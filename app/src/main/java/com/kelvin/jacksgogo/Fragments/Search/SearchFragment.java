package com.kelvin.jacksgogo.Fragments.Search;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.Activities.Jobs.JobDetailActivity;
import com.kelvin.jacksgogo.Activities.Search.ActiveServiceActivity;
import com.kelvin.jacksgogo.Activities.Search.PostServiceActivity;
import com.kelvin.jacksgogo.Activities.Search.ServiceListingActivity;
import com.kelvin.jacksgogo.Adapter.Jobs.SearchJobsAdapter;
import com.kelvin.jacksgogo.Adapter.Services.SearchServicesAdapter;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGAppManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGCategoryResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.EDIT_STATUS;
import static com.kelvin.jacksgogo.Utils.Global.GOCLUB;
import static com.kelvin.jacksgogo.Utils.Global.JOBS;
import static com.kelvin.jacksgogo.Utils.Global.POST;
import static com.kelvin.jacksgogo.Utils.Global.SERVICES;

public class SearchFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    private RecyclerView recyclerView;
    private ArrayList<JGGCategoryModel> categories;
    private SearchJobsAdapter jobAdapter;
    private SearchServicesAdapter serviceAdapter;
    private ProgressDialog progressDialog;

    private String appType = SERVICES;
    private Intent mIntent;
    private int mPercentColor;
    private int mColor;

    private android.app.AlertDialog alertDialog;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = view.findViewById(R.id.search_main_recycler_view);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        }
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        refreshFragment(SERVICES);

        return view;
    }

    public void refreshFragment(String textView) {

        loadCategories();
        appType = textView;
        if (appType.equals(SERVICES)) {
            mPercentColor = ContextCompat.getColor(mContext, R.color.JGGGreen10Percent);
            mColor = ContextCompat.getColor(mContext, R.color.JGGGreen);
            serviceAdapter = new SearchServicesAdapter(mContext, categories);
            serviceAdapter.setOnItemClickLietener(new SearchServicesAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view) {
                    onViewHolderItemClick(view);
                }
            });
            recyclerView.setAdapter(serviceAdapter);
        } else if (appType.equals(JOBS)) {
            mPercentColor = ContextCompat.getColor(mContext, R.color.JGGCyan10Percent);
            mColor = ContextCompat.getColor(mContext, R.color.JGGCyan);
            jobAdapter = new SearchJobsAdapter(mContext, categories);
            jobAdapter.setOnItemClickLietener(new SearchJobsAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view) {
                    if (view.getId() == R.id.btn_view_all || view.getId() == R.id.btn_post_new) {
                        onViewHolderItemClick(view);
                    } else if (view.getId() == R.id.btn_background) {
                        Intent intent = new Intent(mContext, JobDetailActivity.class);
                        mContext.startActivity(intent);
                    }
                }
            });
            recyclerView.setAdapter(jobAdapter);
        } else if (appType.equals(GOCLUB)) {
            mPercentColor = ContextCompat.getColor(mContext, R.color.JGGPurple10Percent);
            mColor = ContextCompat.getColor(mContext, R.color.JGGPurple);

        }
    }

    private void loadCategories() {
        if (JGGAppManager.getInstance(mContext).categories != null) {
            categories = JGGAppManager.getInstance(mContext).categories;
        } else {
            progressDialog = Global.createProgressDialog(mContext);

            final JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, mContext);
            Call<JGGCategoryResponse> call = apiManager.getCategory();
            call.enqueue(new Callback<JGGCategoryResponse>() {
                @Override
                public void onResponse(Call<JGGCategoryResponse> call, Response<JGGCategoryResponse> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body().getSuccess()) {
                            JGGAppManager.getInstance(mContext).categories = response.body().getValue();
                            categories = JGGAppManager.getInstance(mContext).categories;

                            if (appType.equals(SERVICES)) {
                                serviceAdapter.notifyDataChanged(categories);
                                serviceAdapter.notifyDataSetChanged();
                                recyclerView.setAdapter(serviceAdapter);
                            } else if (appType.equals(JOBS)) {
                                jobAdapter.notifyDataChanged(categories);
                                jobAdapter.notifyDataSetChanged();
                                recyclerView.setAdapter(jobAdapter);
                            } else if (appType.equals(GOCLUB)) {

                            }
                        } else {
                            Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        int statusCode  = response.code();
                        Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JGGCategoryResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void onViewHolderItemClick(View view) {

        if (view.getId() == R.id.btn_view_my_service) {
            mIntent = new Intent(mContext.getApplicationContext(), ServiceListingActivity.class);
        } else if (view.getId() == R.id.btn_view_all) {
            mIntent = new Intent(mContext.getApplicationContext(), ActiveServiceActivity.class);
        } else if (view.getId() == R.id.btn_post_new) {
            if (!JGGAppManager.getInstance(mContext).getUsernamePassword()[0].equals("")) {
                mIntent = new Intent(mContext.getApplicationContext(), PostServiceActivity.class);
            } else {
                showAlertDialog();
                return;
            }
        }
        mIntent.putExtra(APPOINTMENT_TYPE, appType);
        mIntent.putExtra(EDIT_STATUS, POST);
        mContext.startActivity(mIntent);
    }

    private void showAlertDialog() {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
        LayoutInflater inflater = this.getLayoutInflater();

        View alertView = inflater.inflate(R.layout.jgg_alert_view, null);
        builder.setView(alertView);
        alertDialog = builder.create();
        TextView cancelButton = (TextView) alertView.findViewById(R.id.btn_alert_cancel);
        TextView okButton = (TextView) alertView.findViewById(R.id.btn_alert_ok);
        TextView title = (TextView) alertView.findViewById(R.id.lbl_alert_titile);
        TextView desc = (TextView) alertView.findViewById(R.id.lbl_alert_description);

        title.setText("Information");
        desc.setText(R.string.alert_post_failed_desc);
        okButton.setText(R.string.alert_ok);
        okButton.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGOrange));
        cancelButton.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGOrange10Percent));
        cancelButton.setTextColor(ContextCompat.getColor(mContext, R.color.JGGOrange));
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
//                ((MainActivity)mContext).bSmsVeryfyKey = true;
//                ((MainActivity)mContext).initView();
//                getActivity().getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.container, new SignInFragment())
//                        .commit();
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
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
