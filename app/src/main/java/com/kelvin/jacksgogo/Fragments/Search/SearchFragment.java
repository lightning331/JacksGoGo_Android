package com.kelvin.jacksgogo.Fragments.Search;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kelvin.jacksgogo.Activities.GoClub_Event.AllGoClubsActivity;
import com.kelvin.jacksgogo.Activities.GoClub_Event.CreateGoClubActivity;
import com.kelvin.jacksgogo.Activities.GoClub_Event.JoinedGoClubsActivity;
import com.kelvin.jacksgogo.Activities.Jobs.JobDetailActivity;
import com.kelvin.jacksgogo.Activities.MainActivity;
import com.kelvin.jacksgogo.Activities.Search.ActiveServiceActivity;
import com.kelvin.jacksgogo.Activities.Search.PostServiceActivity;
import com.kelvin.jacksgogo.Activities.Search.ServiceListingActivity;
import com.kelvin.jacksgogo.Adapter.GoClub_Event.SearchGoClubAdapter;
import com.kelvin.jacksgogo.Adapter.Jobs.SearchJobsAdapter;
import com.kelvin.jacksgogo.Adapter.Services.SearchServicesAdapter;
import com.kelvin.jacksgogo.CustomView.Views.JGGAlertView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.GoClub_Event.JGGEventModel;
import com.kelvin.jacksgogo.Utils.Models.GoClub_Event.JGGGoClubModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;
import com.kelvin.jacksgogo.Utils.Prefs.JGGSharedPrefs;
import com.kelvin.jacksgogo.Utils.Responses.JGGAppTotalCountResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGCategoryResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGGetAppsResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGGetGoClubsResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.EDIT;
import static com.kelvin.jacksgogo.Utils.Global.EDIT_STATUS;
import static com.kelvin.jacksgogo.Utils.Global.EVENTS;
import static com.kelvin.jacksgogo.Utils.Global.GOCLUB;
import static com.kelvin.jacksgogo.Utils.Global.JOBS;
import static com.kelvin.jacksgogo.Utils.Global.POST;
import static com.kelvin.jacksgogo.Utils.Global.SERVICES;


public class SearchFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    private SwipeRefreshLayout swipeContainer;
    private RecyclerView recyclerView;
    private SearchJobsAdapter jobAdapter;
    private SearchServicesAdapter serviceAdapter;
    private SearchGoClubAdapter goClubAdapter;
    private JGGAPIManager apiManager;
    private android.app.AlertDialog alertDialog;

    private ArrayList<JGGCategoryModel> mCategories;
    private ArrayList<JGGCategoryModel> categories = new ArrayList<>();
    private ArrayList<JGGAppointmentModel> mServices = new ArrayList<>();
    private ArrayList<JGGAppointmentModel> mJobs = new ArrayList<>();
    private ArrayList<JGGGoClubModel> mGoClubs = new ArrayList<>();
    private ArrayList<JGGEventModel> mEvents = new ArrayList<>();
    private String appType = SERVICES;
    private Intent mIntent;

    private String token;
    private static final Integer hours = 0;

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

        token = JGGSharedPrefs.getInstance(mContext).getToken();
        categories = JGGAppManager.getInstance().getCategories();

        Retrofit retrofit = JGGURLManager.getClient();
        apiManager = retrofit.create(JGGAPIManager.class);

        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeSearchContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchTimelineAsync(0);
            }
        });

        recyclerView = view.findViewById(R.id.search_main_recycler_view);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        }
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        refreshFragment(SERVICES);

        return view;
    }

    public void fetchTimelineAsync(int page) {
        // Send the network request to fetch the updated data
        // `client` here is an instance of Android Async HTTP
        // getHomeTimeline is an example endpoint.

        Log.d("Type", this.appType);
        this.refreshFragment(this.appType);
    }

    public void refreshFragment(final String textView) {

        swipeContainer.post(new Runnable() {
                @Override
                public void run() {
                    swipeContainer.setRefreshing(true);

                    fetchData(textView);
                }
            }
        );
    }

    private void fetchData(String type) {

        appType = type;

        if (categories == null)
            loadCategories();
        else {
            if (categories.size() == 0)
                loadCategories();
            else {
                mCategories = categories;
                swipeContainer.setRefreshing(false);
            }
        }

        if (appType.equals(SERVICES)) {
            // Configure the refreshing colors
            swipeContainer.setColorSchemeResources(R.color.JGGGreen,
                    R.color.JGGGreen,
                    R.color.JGGGreen,
                    R.color.JGGGreen);
            getTotalAppointmentsCount(false);
            updateServiceAdapter();
        } else if (appType.equals(JOBS)) {
            // Configure the refreshing colors
            swipeContainer.setColorSchemeResources(R.color.JGGCyan,
                    R.color.JGGCyan,
                    R.color.JGGCyan,
                    R.color.JGGCyan);
            getTotalAppointmentsCount(true);
            updateJobAdapter();
        } else if (appType.equals(GOCLUB)) {
            // Configure the refreshing colors
            swipeContainer.setColorSchemeResources(R.color.JGGPurple,
                    R.color.JGGPurple,
                    R.color.JGGPurple,
                    R.color.JGGPurple);
            updateGoClubAdapter();
        }
    }

    // TODO : Service search Adapter
    private void updateServiceAdapter() {
        serviceAdapter = new SearchServicesAdapter(mContext, mServices);
        serviceAdapter.setOnItemClickLietener(new SearchServicesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                onViewHolderItemClick(view);
            }
        });

        //if (!token.equals("")){
            if (mServices.size() > 0)
                serviceAdapter.setLoadMoreListener(new SearchServicesAdapter.OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {

                        recyclerView.post(new Runnable() {
                            @Override
                            public void run() {
                                int index = mServices.size() - 1;
                                onLoadServiceMore(index);
                            }
                        });
                    }
                });
            onLoadServices();
        //}
        recyclerView.setAdapter(serviceAdapter);
    }

    // TODO : Job search Adapter
    private void updateJobAdapter() {
        jobAdapter = new SearchJobsAdapter(mContext, mJobs);
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

        //if (!token.equals("")){
            if (mJobs.size() > 0)
                jobAdapter.setLoadMoreListener(new SearchJobsAdapter.OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {

                        recyclerView.post(new Runnable() {
                            @Override
                            public void run() {
                                int index = mServices.size() - 1;
                                onLoadJobsMore(index);
                            }
                        });
                    }
                });
            onLoadJobs();
        //}
        recyclerView.setAdapter(jobAdapter);
    }

    // TODO : GoClub & Event search Adapter
    private void updateGoClubAdapter() {
        goClubAdapter = new SearchGoClubAdapter(mContext);
        // GoClub
        goClubAdapter.setOnGoClubClickListener(new SearchGoClubAdapter.OnGoClubHeaderViewClickListener() {
            @Override
            public void onItemClick(View view) {
                if (view.getId() == R.id.btn_view_my_service) {
                    mIntent = new Intent(mContext, JoinedGoClubsActivity.class);
                } else if (view.getId() == R.id.btn_view_all) {
                    mIntent = new Intent(mContext, AllGoClubsActivity.class);
                    mIntent.putExtra("is_category", false);
                } else if (view.getId() == R.id.btn_post_new) {
                    if (!JGGSharedPrefs.getInstance(mContext).getUsernamePassword()[0].equals("")) {
                        mIntent = new Intent(mContext, CreateGoClubActivity.class);
                        mIntent.putExtra(EDIT_STATUS, POST);
                        mIntent.putExtra(APPOINTMENT_TYPE, GOCLUB);
                    } else {
                        showAlertDialog();
                        return;
                    }
                }
                mContext.startActivity(mIntent);
            }
        });
        onLoadGoClubs();

        // Event
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
                    if (!JGGSharedPrefs.getInstance(mContext).getUsernamePassword()[0].equals("")) {
                        mIntent = new Intent(mContext, CreateGoClubActivity.class);
                        mIntent.putExtra(EDIT_STATUS, POST);
                        mIntent.putExtra(APPOINTMENT_TYPE, EVENTS);
                    } else {
                        showAlertDialog();
                        return;
                    }
                }
                mContext.startActivity(mIntent);
            }
        });
        if (mEvents.size() > 0)
            goClubAdapter.setLoadMoreListener(new SearchGoClubAdapter.OnLoadMoreListener() {
                @Override
                public void onLoadMore() {

                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            int index = mEvents.size() - 1;
                            onLoadMoreEvents(index);
                        }
                    });
                }
            });
        onLoadEvents();
        recyclerView.setAdapter(goClubAdapter);
        swipeContainer.setRefreshing(false);
    }

    // TODO : Get Total Appointment Count
    private void getTotalAppointmentsCount(boolean isJob) {
        Call<JGGAppTotalCountResponse> call = apiManager.getTotalAppointmentsCount(hours);
        call.enqueue(new Callback<JGGAppTotalCountResponse>() {
            @Override
            public void onResponse(Call<JGGAppTotalCountResponse> call, Response<JGGAppTotalCountResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {

                    } else {
                        //Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode  = response.code();
                }
            }

            @Override
            public void onFailure(Call<JGGAppTotalCountResponse> call, Throwable t) {
                Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // TODO : Get Recommended Service
    private void onLoadServices() {
        Call<JGGGetAppsResponse> call = apiManager.searchAppointment(null, null,
                null, null, null, null, null,
                null, null, null, false, 0, 10);
        call.enqueue(new Callback<JGGGetAppsResponse>() {
            @Override
            public void onResponse(Call<JGGGetAppsResponse> call, Response<JGGGetAppsResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mServices = response.body().getValue();

                        serviceAdapter.notifyDataChanged(mServices);
                        serviceAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(serviceAdapter);

                        // Now we call setRefreshing(false) to signal refresh has finished
                        swipeContainer.setRefreshing(false);
                    } else {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGGetAppsResponse> call, Throwable t) {
                Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // TODO : Get more Recommended Service
    private void onLoadServiceMore(int index){

        //add loading progress view
        mServices.add(new JGGAppointmentModel());
        serviceAdapter.notifyItemInserted(mServices.size() - 1);

        //int pageIndex = (int)(index/10);

        Call<JGGGetAppsResponse> call = apiManager.searchAppointment(null, null,
                null, null, null, null, null,
                null, null, null, false, index, 10);
        call.enqueue(new Callback<JGGGetAppsResponse>() {
            @Override
            public void onResponse(Call<JGGGetAppsResponse> call, Response<JGGGetAppsResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {

                        //remove loading view
                        mServices.remove(mServices.size() - 1);

                        List<JGGAppointmentModel> result = response.body().getValue();
                        if (result.size() > 0) {
                            mServices.addAll(result);
                        } else { //result size 0 means there is no more data available at server
                            serviceAdapter.setMoreDataAvailable(false);
                            //telling adapter to stop calling load more as no more server data available
                            Toast.makeText(mContext,"No More Data Available",Toast.LENGTH_SHORT).show();
                        }
                        serviceAdapter.notifyDataChanged(mServices);
                        serviceAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGGetAppsResponse> call, Throwable t) {
                Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // TODO : Get Recommended Job
    private void onLoadJobs() {
        Call<JGGGetAppsResponse> call = apiManager.searchAppointment(null, null,
                null, null, null, null, null, null,
                null, null, true, 0, 10);
        call.enqueue(new Callback<JGGGetAppsResponse>() {
            @Override
            public void onResponse(Call<JGGGetAppsResponse> call, Response<JGGGetAppsResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mJobs = response.body().getValue();

                        jobAdapter.notifyDataChanged(mJobs);
                        jobAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(jobAdapter);

                        // Now we call setRefreshing(false) to signal refresh has finished
                        swipeContainer.setRefreshing(false);

                    } else {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGGetAppsResponse> call, Throwable t) {
                Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // TODO : Get more Recommended Job
    private void onLoadJobsMore(int index) {
        //add loading progress view
        mJobs.add(new JGGAppointmentModel());
        jobAdapter.notifyItemInserted(mJobs.size() - 1);

        //int pageIndex = (int)(index/10);

        Call<JGGGetAppsResponse> call = apiManager.searchAppointment(null, null,
                null, null, null, null, null, null,
                null, null, true, index, 10);
        call.enqueue(new Callback<JGGGetAppsResponse>() {
            @Override
            public void onResponse(Call<JGGGetAppsResponse> call, Response<JGGGetAppsResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {

                        mJobs.remove(mJobs.size() - 1);

                        List<JGGAppointmentModel> result = response.body().getValue();
                        if (result.size() > 0) {
                            mJobs.addAll(result);
                        } else { //result size 0 means there is no more data available at server
                            jobAdapter.setMoreDataAvailable(false);
                            //telling adapter to stop calling load more as no more server data available
                            Toast.makeText(mContext,"No More Data Available",Toast.LENGTH_SHORT).show();
                        }
                        jobAdapter.notifyDataChanged(mJobs);
                        jobAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode  = response.code();
                    //Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGGetAppsResponse> call, Throwable t) {
                Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // TODO : Get Recommended GoClubs
    private void onLoadGoClubs() {
        Call<JGGGetGoClubsResponse> call = apiManager.searchGoClub(null, null,
                null,  0, 10);
        call.enqueue(new Callback<JGGGetGoClubsResponse>() {
            @Override
            public void onResponse(Call<JGGGetGoClubsResponse> call, Response<JGGGetGoClubsResponse> response) {
                swipeContainer.setRefreshing(false);
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mGoClubs = response.body().getValue();

                        goClubAdapter.notifyGoClubDataChanged(mGoClubs);
                        goClubAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(goClubAdapter);

                    } else {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGGetGoClubsResponse> call, Throwable t) {
                swipeContainer.setRefreshing(false);
                Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // TODO : Get Recommended Events
    private void onLoadEvents() {

    }

    // TODO : Get Recommended Events
    private void onLoadMoreEvents(int index) {

    }

    // TODO : Get All Categories
    private void loadCategories() {
        final JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        Call<JGGCategoryResponse> call = apiManager.getCategory();
        call.enqueue(new Callback<JGGCategoryResponse>() {
            @Override
            public void onResponse(Call<JGGCategoryResponse> call, Response<JGGCategoryResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mCategories = response.body().getValue();
                        categories = mCategories;
                        JGGAppManager.getInstance().setCategories(categories);
                        swipeContainer.setRefreshing(false);
                    } else {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        swipeContainer.setRefreshing(false);
                    }
                } else {
                    int statusCode  = response.code();
                    //Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                    swipeContainer.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<JGGCategoryResponse> call, Throwable t) {
                Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
                swipeContainer.setRefreshing(false);
            }
        });
    }

    private void onViewHolderItemClick(View view) {

        if (view.getId() == R.id.btn_view_my_service) {
            mIntent = new Intent(mContext, ServiceListingActivity.class);
        } else if (view.getId() == R.id.btn_view_all) {
            JGGAppManager.getInstance().setSelectedCategory(null);
            mIntent = new Intent(mContext, ActiveServiceActivity.class);
            mIntent.putExtra("active_status", 1);
        } else if (view.getId() == R.id.btn_post_new) {
            if (!JGGSharedPrefs.getInstance(mContext).getUsernamePassword()[0].equals("")) {
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
                else {
                    alertDialog.dismiss();
                    ((MainActivity)mContext).setProfilePage();
                }
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
