package com.kelvin.jacksgogo.Fragments.Favourite;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kelvin.jacksgogo.Activities.GoClub_Event.EventDetailActivity;
import com.kelvin.jacksgogo.Activities.Jobs.JobDetailActivity;
import com.kelvin.jacksgogo.Activities.MainActivity;
import com.kelvin.jacksgogo.Activities.Search.ServiceDetailActivity;
import com.kelvin.jacksgogo.Adapter.GoClub_Event.EventsListingAdapter;
import com.kelvin.jacksgogo.Adapter.Jobs.JobsListingAdapter;
import com.kelvin.jacksgogo.Adapter.Services.ActiveServiceAdapter;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.GoClub_Event.JGGEventModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserProfileModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGGetAppsResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.Global.EVENTS;
import static com.kelvin.jacksgogo.Utils.Global.JOBS;
import static com.kelvin.jacksgogo.Utils.Global.SERVICES;
import static com.kelvin.jacksgogo.Utils.Global.USERS;

public class FavouriteFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    private LinearLayout loginLayout;
    private TextView btnLogin;
    private RecyclerView recyclerView;

    private ProgressDialog progressDialog;
    private android.app.AlertDialog alertDialog;
    private ActiveServiceAdapter serviceAdapter;
    private JobsListingAdapter jobAdapter;

    private ArrayList<JGGAppointmentModel> mServices = new ArrayList<>();
    private ArrayList<JGGAppointmentModel> mJobs = new ArrayList<>();
    private ArrayList<JGGEventModel> clubEvents = new ArrayList<JGGEventModel>();
    private String appType = SERVICES;

    public FavouriteFragment() {
        // Required empty public constructor
    }

    public static FavouriteFragment newInstance() {
        FavouriteFragment fragment = new FavouriteFragment();
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
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);

        loginLayout = view.findViewById(R.id.login_layout);
        btnLogin = view.findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) mContext).setProfilePage();
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.favourite_recycler_view);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        }
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        refreshFragment(SERVICES);

        return view;
    }

    public void refreshFragment(String type) {

        appType = type;
        JGGUserProfileModel currentUser = JGGAppManager.getInstance().getCurrentUser();
        if (currentUser == null) {
            if (type.equals(USERS)) {
                updateUsersAdapter();
                loginLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.GONE);
                loginLayout.setVisibility(View.VISIBLE);
            }
        } else {
            loginLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            if (type.equals(SERVICES)) {
                updateServicesAdapter();
            } else if (type.equals(JOBS)) {
                updateJobsAdapter();
            } else if (type.equals(EVENTS)) {
                updateEventsAdapter();
            } else if (type.equals(USERS)) {
                updateUsersAdapter();
            }
        }
    }

    private void updateServicesAdapter() {
        serviceAdapter = new ActiveServiceAdapter(mContext);
        serviceAdapter.setOnItemClickListener(new ActiveServiceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                JGGAppManager.getInstance().setSelectedAppointment(mServices.get(position));
                Intent intent = new Intent(mContext, ServiceDetailActivity.class);
                mContext.startActivity(intent);
            }
        });
        recyclerView.setAdapter(serviceAdapter);
        searchServices();
    }

    private void updateJobsAdapter() {
        jobAdapter = new JobsListingAdapter(mContext);
        jobAdapter.setOnItemClickListener(new JobsListingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                JGGAppManager.getInstance().setSelectedAppointment(mJobs.get(position));
                Intent intent = new Intent(mContext, JobDetailActivity.class);
                mContext.startActivity(intent);
            }
        });
        recyclerView.setAdapter(jobAdapter);
        searchJobs();
    }

    private void updateEventsAdapter() {
        EventsListingAdapter adapter = new EventsListingAdapter(mContext, clubEvents);
        adapter.setOnItemClickListener(new EventsListingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Gson gson = new Gson();
                JGGEventModel eventModel = clubEvents.get(position);
                String jsonEvent = gson.toJson(eventModel);

                Intent intent = new Intent(mContext, EventDetailActivity.class);
                intent.putExtra("clubEventModel", jsonEvent);
                mContext.startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void updateUsersAdapter() {
        //UserListingAdapter adapter = new UserListingAdapter(mContext);
        //recyclerView.setAdapter(adapter);
    }

    private void searchServices() {
        progressDialog = Global.createProgressDialog(mContext);
        final JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        Call<JGGGetAppsResponse> call = apiManager.searchAppointment(null, null,
                null, null, null, null, null,
                null, null, null, false, 0, 50);
        call.enqueue(new Callback<JGGGetAppsResponse>() {
            @Override
            public void onResponse(Call<JGGGetAppsResponse> call, Response<JGGGetAppsResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mServices = response.body().getValue();

                        serviceAdapter.notifyDataChanged(mServices);
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
        progressDialog = Global.createProgressDialog(mContext);
        final JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        Call<JGGGetAppsResponse> call = apiManager.searchAppointment(null, null,
                null, null, null, null, null,
                null, null, null, true, 0, 50);
        call.enqueue(new Callback<JGGGetAppsResponse>() {
            @Override
            public void onResponse(Call<JGGGetAppsResponse> call, Response<JGGGetAppsResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mJobs = response.body().getValue();

                        jobAdapter.notifyDataChanged(mJobs);
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
