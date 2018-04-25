package com.kelvin.jacksgogo.Fragments.Search;

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
import android.widget.Toast;

import com.kelvin.jacksgogo.Activities.GoClub_Event.EventDetailActivity;
import com.kelvin.jacksgogo.Activities.Jobs.PostedJobActivity;
import com.kelvin.jacksgogo.Activities.Search.PostedServiceActivity;
import com.kelvin.jacksgogo.Activities.Search.ServiceFilterActivity;
import com.kelvin.jacksgogo.Adapter.GoClub_Event.EventsListingAdapter;
import com.kelvin.jacksgogo.Adapter.Jobs.JobsListingAdapter;
import com.kelvin.jacksgogo.Adapter.Services.ActiveServiceAdapter;
import com.kelvin.jacksgogo.CustomView.Views.ActiveServiceTabView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGGetAppsResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.Global.EVENTS;
import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.JOBS;
import static com.kelvin.jacksgogo.Utils.Global.SERVICES;

public class ActiveServiceMainFragment extends Fragment implements ActiveServiceMapFragment.OnFragmentInteractionListener {

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    private JGGCategoryModel selectedCategory;
    private RecyclerView recyclerView;
    private ActiveServiceTabView tabView;

    private JobsListingAdapter jobAdapter;
    private ActiveServiceAdapter serviceAdapter;
    private EventsListingAdapter eventAdapter;
    private ProgressDialog progressDialog;

    private ArrayList<JGGAppointmentModel> mServices = new ArrayList<>();
    private ArrayList<JGGAppointmentModel> mJobs = new ArrayList<>();
    private String appType;
    private String mCategoryID;

    public ActiveServiceMainFragment() {
        // Required empty public constructor

    }

    public static ActiveServiceMainFragment newInstance(String appType) {
        ActiveServiceMainFragment fragment = new ActiveServiceMainFragment();
        Bundle args = new Bundle();
        args.putString(APPOINTMENT_TYPE, appType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            appType = getArguments().getString(APPOINTMENT_TYPE);
        } else {
            appType = SERVICES;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_active_service_main, container, false);

        selectedCategory = JGGAppManager.getInstance().getSelectedCategory();

        if (selectedCategory == null)
            mCategoryID = null;
        else
            mCategoryID = selectedCategory.getID();

        initTabView(view);
        initRecyclerView(view);

        return view;
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.active_service_recycler_view);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        }
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        if (appType.equals(SERVICES)) {
            serviceAdapter = new ActiveServiceAdapter(mContext);
            searchServices();
            serviceAdapter.setOnItemClickListener(new ActiveServiceAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    JGGAppManager.getInstance().setSelectedAppointment(mServices.get(position));

                    Intent intent = new Intent(getActivity(), PostedServiceActivity.class);
                    intent.putExtra("is_post", false);
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(serviceAdapter);
        } else if (appType.equals(JOBS)) {
            jobAdapter = new JobsListingAdapter(mContext);
            searchJobs();
            jobAdapter.setOnItemClickListener(new JobsListingAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    JGGAppManager.getInstance().setSelectedAppointment(mJobs.get(position));

                    Intent intent = new Intent(mContext, PostedJobActivity.class);
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(jobAdapter);
        } else if (appType.equals(EVENTS)) {
            eventAdapter = new EventsListingAdapter(mContext);
            eventAdapter.setOnItemClickListener(new EventsListingAdapter.OnItemClickListener() {
                @Override
                public void onItemClick() {
                    mContext.startActivity(new Intent(mContext, EventDetailActivity.class));
                }
            });
            recyclerView.setAdapter(eventAdapter);
        }
    }

    private void searchJobs() {
        progressDialog = Global.createProgressDialog(mContext);
        final JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        Call<JGGGetAppsResponse> call = apiManager.searchJob(null, null,
                null, mCategoryID, null, null, null,
                null, null, null, 0, 50);
        call.enqueue(new Callback<JGGGetAppsResponse>() {
            @Override
            public void onResponse(Call<JGGGetAppsResponse> call, Response<JGGGetAppsResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mJobs = response.body().getValue();

                        jobAdapter.notifyDataChanged(mJobs);
                        jobAdapter.notifyDataSetChanged();
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

    private void searchServices() {
        progressDialog = Global.createProgressDialog(mContext);
        final JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        Call<JGGGetAppsResponse> call = apiManager.searchService(null, null,
                null, mCategoryID, null, null, null, null,
                null, null, 0, 50);
        call.enqueue(new Callback<JGGGetAppsResponse>() {
            @Override
            public void onResponse(Call<JGGGetAppsResponse> call, Response<JGGGetAppsResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mServices = response.body().getValue();

                        serviceAdapter.notifyDataChanged(mServices);
                        serviceAdapter.notifyDataSetChanged();
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

    private void initTabView(View view) {

        tabView = new ActiveServiceTabView(getContext(), appType);
        LinearLayout tabbarLayout = (LinearLayout) view.findViewById(R.id.active_service_tab_view_layout);
        tabbarLayout.addView(tabView);
        tabView.setTabbarItemClickListener(new ActiveServiceTabView.OnTabbarItemClickListener() {
            @Override
            public void onTabbarItemClick(View view) {
                if (view.getId() == R.id.btn_active_service_filter) {
                    Intent intent = new Intent(getActivity(), ServiceFilterActivity.class);
                    intent.putExtra(APPOINTMENT_TYPE, appType);
                    startActivity(intent);
                } else if (view.getId() == R.id.btn_active_service_mapview) {
                    ActiveServiceMapFragment mapFragment = ActiveServiceMapFragment.newInstance(appType);
                    if (appType.equals(SERVICES))
                        mapFragment.setAppointment(mServices);
                    else
                        mapFragment.setAppointment(mJobs);
                    mapFragment.setOnFragmentInteractionListener(ActiveServiceMainFragment.this);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.active_service_container, mapFragment)
                            .addToBackStack("active_service")
                            .commit();
                } else {
                    if (view.getId() == R.id.btn_active_service_distance) {

                    } else if (view.getId() == R.id.btn_active_service_ratings) {

                    }
                }
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
