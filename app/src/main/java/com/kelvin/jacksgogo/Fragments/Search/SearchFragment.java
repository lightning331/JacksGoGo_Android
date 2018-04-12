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

import com.kelvin.jacksgogo.Activities.GoClub_Event.AllGoClubsActivity;
import com.kelvin.jacksgogo.Activities.GoClub_Event.JoinedGoClubsActivity;
import com.kelvin.jacksgogo.Activities.Jobs.JobDetailActivity;
import com.kelvin.jacksgogo.Activities.Search.ActiveServiceActivity;
import com.kelvin.jacksgogo.Activities.Search.PostServiceActivity;
import com.kelvin.jacksgogo.Activities.Search.ServiceListingActivity;
import com.kelvin.jacksgogo.Adapter.Events.SearchGoClubAdapter;
import com.kelvin.jacksgogo.Adapter.Jobs.SearchJobsAdapter;
import com.kelvin.jacksgogo.Adapter.Services.SearchServicesAdapter;
import com.kelvin.jacksgogo.CustomView.Views.JGGAlertView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGCategoryResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGGetAppsResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.EDIT;
import static com.kelvin.jacksgogo.Utils.Global.EDIT_STATUS;
import static com.kelvin.jacksgogo.Utils.Global.EVENTS;
import static com.kelvin.jacksgogo.Utils.Global.GOCLUB;
import static com.kelvin.jacksgogo.Utils.Global.JOBS;
import static com.kelvin.jacksgogo.Utils.Global.POST;
import static com.kelvin.jacksgogo.Utils.Global.SERVICES;
import static com.kelvin.jacksgogo.Utils.JGGAppManager.categories;
import static com.kelvin.jacksgogo.Utils.JGGAppManager.selectedCategory;

public class SearchFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    private RecyclerView recyclerView;
    private ArrayList<JGGCategoryModel> mCategories;
    private SearchJobsAdapter jobAdapter;
    private SearchServicesAdapter serviceAdapter;
    private ProgressDialog progressDialog;
    private android.app.AlertDialog alertDialog;

    private ArrayList<JGGAppointmentModel> mServices = new ArrayList<>();
    private ArrayList<JGGAppointmentModel> mJobs = new ArrayList<>();
    private String appType = SERVICES;
    private Intent mIntent;

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

    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint()) {
            return;
        }
    }

    public void refreshFragment(String textView) {

        progressDialog = Global.createProgressDialog(mContext);
        if (categories == null)
            loadCategories();
        else {
            if (categories.size() == 0)
                loadCategories();
            else
                mCategories = categories;
        }
        appType = textView;
        if (appType.equals(SERVICES)) {
            serviceAdapter = new SearchServicesAdapter(mContext, mCategories, mServices);
            serviceAdapter.setOnItemClickLietener(new SearchServicesAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view) {
                    onViewHolderItemClick(view);
                }
            });
            searchServices();
        } else if (appType.equals(JOBS)) {
            jobAdapter = new SearchJobsAdapter(mContext, mCategories);
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
            searchJobs();
        } else if (appType.equals(GOCLUB)) {
            progressDialog.dismiss();
            SearchGoClubAdapter goClubAdapter = new SearchGoClubAdapter(mContext, mCategories);
            goClubAdapter.setOnGoClubClickListener(new SearchGoClubAdapter.OnGoClubHeaderViewClickListener() {
                @Override
                public void onItemClick(View view) {
                    if (view.getId() == R.id.btn_view_my_service) {
                        mIntent = new Intent(mContext, JoinedGoClubsActivity.class);
                    } else if (view.getId() == R.id.btn_view_all) {
                        mIntent = new Intent(mContext, AllGoClubsActivity.class);
                        mIntent.putExtra("is_category", false);
                    } else if (view.getId() == R.id.btn_post_new) {

                    }
                    mContext.startActivity(mIntent);
                }
            });
            goClubAdapter.setOnEventClickListener(new SearchGoClubAdapter.OnEventHeaderViewClickListener() {
                @Override
                public void onItemClick(View view) {
                    if (view.getId() == R.id.btn_view_my_service) {
                        mIntent = new Intent(mContext, ActiveServiceActivity.class);
                        mIntent.putExtra(APPOINTMENT_TYPE, EVENTS);
                        mIntent.putExtra(EDIT_STATUS, EDIT);
                        mIntent.putExtra("active_status", 2);

                    } else if (view.getId() == R.id.btn_view_all) {
                        mIntent = new Intent(mContext, ActiveServiceActivity.class);
                        mIntent.putExtra(APPOINTMENT_TYPE, EVENTS);
                        mIntent.putExtra(EDIT_STATUS, POST);
                        mIntent.putExtra("active_status", 1);

                    } else if (view.getId() == R.id.btn_post_new) {

                    }
                    mContext.startActivity(mIntent);
                }
            });
            recyclerView.setAdapter(goClubAdapter);
        }
    }

    private void searchServices() {
        final JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        Call<JGGGetAppsResponse> call = apiManager.searchService(null, null,
                null, null, null, null, null,
                null, null, 0, 50);
        call.enqueue(new Callback<JGGGetAppsResponse>() {
            @Override
            public void onResponse(Call<JGGGetAppsResponse> call, Response<JGGGetAppsResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mServices = response.body().getValue();

                        serviceAdapter.notifyDataChanged(mCategories, mServices);
                        serviceAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(serviceAdapter);
                    } else {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode  = response.code();
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGGetAppsResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchJobs() {
        final JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        Call<JGGGetAppsResponse> call = apiManager.searchJob(null, null,
                null, null, null, null, null, null,
                null, 0, 50);
        call.enqueue(new Callback<JGGGetAppsResponse>() {
            @Override
            public void onResponse(Call<JGGGetAppsResponse> call, Response<JGGGetAppsResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mJobs = response.body().getValue();

                        jobAdapter.notifyDataChanged(mCategories, mJobs);
                        jobAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(jobAdapter);
                    } else {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode  = response.code();
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGGetAppsResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadCategories() {
        final JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        Call<JGGCategoryResponse> call = apiManager.getCategory();
        call.enqueue(new Callback<JGGCategoryResponse>() {
            @Override
            public void onResponse(Call<JGGCategoryResponse> call, Response<JGGCategoryResponse> response) {
                //progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mCategories = response.body().getValue();
                        categories = mCategories;
                    } else {
                        progressDialog.dismiss();
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

    private void onViewHolderItemClick(View view) {

        if (view.getId() == R.id.btn_view_my_service) {
            mIntent = new Intent(mContext, ServiceListingActivity.class);
        } else if (view.getId() == R.id.btn_view_all) {
            selectedCategory = null;
            mIntent = new Intent(mContext, ActiveServiceActivity.class);
            mIntent.putExtra("active_status", 1);
        } else if (view.getId() == R.id.btn_post_new) {
            if (!JGGAppManager.getInstance(mContext).getUsernamePassword()[0].equals("")) {
                mIntent = new Intent(mContext, PostServiceActivity.class);
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
        JGGAlertView builder = new JGGAlertView(mContext,
                "Information",
                mContext.getResources().getString(R.string.alert_post_failed_desc),
                false,
                mContext.getResources().getString(R.string.alert_cancel),
                R.color.JGGOrange,
                R.color.JGGOrange10Percent,
                mContext.getResources().getString(R.string.alert_ok),
                R.color.JGGOrange);
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
