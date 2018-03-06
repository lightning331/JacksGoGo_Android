package com.kelvin.jacksgogo.Fragments.Appointments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kelvin.jacksgogo.Activities.Jobs.JobStatusSummaryActivity;
import com.kelvin.jacksgogo.Adapter.Appointment.AppointmentMainAdapter;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGEventModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGJobModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGServiceModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGGetJobResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.creatingAppointment;
import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.currentUser;
import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.selectedCategory;


public class AppMainFragment extends Fragment implements SearchView.OnQueryTextListener {

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    private RecyclerView recyclerView;
    private SearchView searchView;
    private Object searchTag;
    private ProgressDialog progressDialog;

    ArrayList<JGGJobModel> arrayAllPendingAppointments = new ArrayList<>();
    ArrayList<JGGJobModel> arrayLoadedQuickAppointments = new ArrayList<>();
    ArrayList<JGGJobModel> arrayLoadedServicePackages = new ArrayList<>();
    ArrayList<JGGJobModel> arrayLoadedPendingAppointments = new ArrayList<>();
    ArrayList<JGGJobModel> arrayConfirmedAppointments = new ArrayList<>();
    ArrayList<JGGJobModel> arrayHistoryAppointments = new ArrayList<>();
    ArrayList<JGGJobModel> filteredArrayList = new ArrayList<>();

    private static AppointmentMainAdapter pendingListAdapter;
    private static AppointmentMainAdapter confirmedListAdapter;
    private static AppointmentMainAdapter historyListAdapter;

    public AppMainFragment() {
        // Required empty public constructor
    }

    public static AppMainFragment newInstance() {
        AppMainFragment fragment = new AppMainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_app_main, container, false);
        searchView = (SearchView) view.findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(this);
        pendingListAdapter = new AppointmentMainAdapter(getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.appointment_recycler_view);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        }

        // create list and custom adapter
        refreshFragment("PENDING");
        return view;
    }

    private void resetData() {
        arrayAllPendingAppointments.clear();
        arrayLoadedQuickAppointments.clear();
        arrayLoadedServicePackages.clear();
        arrayLoadedPendingAppointments.clear();
        arrayConfirmedAppointments.clear();
        arrayHistoryAppointments.clear();
        filteredArrayList.clear();
    }

    private void loadPendingAppointments() {
        progressDialog = Global.createProgressDialog(mContext);
        JGGAPIManager apiManager = JGGURLManager.getClient().create(JGGAPIManager.class);
        retrofit2.Call<JGGGetJobResponse> call = apiManager.getPendingAppointments();
        call.enqueue(new Callback<JGGGetJobResponse>() {
            @Override
            public void onResponse(Call<JGGGetJobResponse> call, Response<JGGGetJobResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    resetData();
                    arrayAllPendingAppointments = response.body().getValue();
                    filterJobs(response.body().getValue());
                } else {
                    int statusCode  = response.code();
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGGetJobResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterJobs(ArrayList<JGGJobModel> jobs) {
        for (JGGJobModel job : jobs) {
            if (job.isQuickJob()) {
                arrayLoadedQuickAppointments.add(job);
            } else if (job.isRequest() == false && job.getAppointmentType() > 1) {
                arrayLoadedServicePackages.add(job);
            } else {
                arrayLoadedPendingAppointments.add(job);
            }
        }
        if (arrayLoadedQuickAppointments.size() > 0) pendingListAdapter.addSection("Quick Jobs", arrayLoadedQuickAppointments);
        if (arrayLoadedServicePackages.size() > 0) pendingListAdapter.addSection("Service Packages", arrayLoadedServicePackages);
        if (arrayLoadedPendingAppointments.size() > 0) pendingListAdapter.addSection("Pending Jobs", arrayLoadedPendingAppointments);
        recyclerView.setAdapter(pendingListAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.refreshDrawableState();

        pendingListAdapter.setOnItemClickListener(new AppointmentMainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object object) {
                // ListView item select
                onSelectListViewItem(position, object);
            }
        });
    }

    private void loadConfirmedAppointments() {
        progressDialog = Global.createProgressDialog(mContext);
        JGGAPIManager apiManager = JGGURLManager.getClient().create(JGGAPIManager.class);
        retrofit2.Call<JGGGetJobResponse> call = apiManager.getConfirmedAppointments(currentUser.getID());
        call.enqueue(new Callback<JGGGetJobResponse>() {
            @Override
            public void onResponse(Call<JGGGetJobResponse> call, Response<JGGGetJobResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    resetData();
                    arrayConfirmedAppointments = response.body().getValue();

                    setDataToAdapter("Confirmed",
                            arrayConfirmedAppointments, false);
                } else {
                    int statusCode  = response.code();
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGGetJobResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadAppointmentsHistory() {
        progressDialog = Global.createProgressDialog(mContext);
        JGGAPIManager apiManager = JGGURLManager.getClient().create(JGGAPIManager.class);
        String userID = currentUser.getID();
        retrofit2.Call<JGGGetJobResponse> call = apiManager.getAppointmentHistory(userID);
        call.enqueue(new Callback<JGGGetJobResponse>() {
            @Override
            public void onResponse(Call<JGGGetJobResponse> call, Response<JGGGetJobResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    resetData();
                    arrayHistoryAppointments = response.body().getValue();

                    setDataToAdapter("History",
                            arrayHistoryAppointments, false);
                } else {
                    int statusCode  = response.code();
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGGetJobResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void refreshFragment(Object textView) {

        //Log.d("Refresh Fragement", "=====================" + searchTag);

        searchTag = textView;
        if (textView == "PENDING") {
            searchView.setQueryHint("Search through Pending list");
            loadPendingAppointments();

        } else if (textView == "CONFIRM") {
            searchView.setQueryHint("Search through Confirmed list");
            loadConfirmedAppointments();

        } else if (textView == "HISTORY") {
            searchView.setQueryHint("Search through History list");
            loadAppointmentsHistory();
        }
    }

    public void setDataToAdapter(String sectionTitle,
                                 ArrayList<JGGJobModel> arrayList, Boolean isFilter) {
        AppointmentMainAdapter adapter = new AppointmentMainAdapter(getContext());

        if (arrayList.size() > 0) {
            adapter.addSection(sectionTitle, arrayList);
        }
        if (isFilter) {
            adapter.setFilter(filteredArrayList);
        }
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.refreshDrawableState();

        // RecyclerView Item select
        adapter.setOnItemClickListener(new AppointmentMainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object object) {
                onSelectListViewItem(position, object);
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        Log.d("Search Tag", "==========" + searchTag + "==========");

        if (searchTag == "PENDING") {
            filteredArrayList = filter(arrayAllPendingAppointments, newText);

            pendingListAdapter = new AppointmentMainAdapter(getContext());
            pendingListAdapter.addSection("Pending Jobs", filteredArrayList);
            pendingListAdapter.setFilter(filteredArrayList);
            recyclerView.setAdapter(pendingListAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.refreshDrawableState();

            pendingListAdapter.setOnItemClickListener(new AppointmentMainAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position, Object object) {
                    onSelectListViewItem(position, object);
                }
            });

        } else if (searchTag == "CONFIRM") {
            filteredArrayList = filter(arrayConfirmedAppointments, newText);

            setDataToAdapter("Confirmed",
                    filteredArrayList, true);

        } else if (searchTag == "HISTORY") {
            filteredArrayList = filter(arrayHistoryAppointments, newText);

            setDataToAdapter("History",
                    filteredArrayList, true);
        }

        return true;
    }

    private ArrayList<JGGJobModel> filter(ArrayList<JGGJobModel> models, String query) {
        query = query.toLowerCase();
        final ArrayList<JGGJobModel> filteredModelList = new ArrayList<>();
        for (JGGJobModel model : models) {
            final String text = model.getTitle().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    private void onSelectListViewItem(int position, Object object) {
        if (object instanceof JGGJobModel) {
            Log.d("Service Model Selected", "==========" + object + "============");

            JGGJobModel appointment = (JGGJobModel) object;
            selectedCategory = appointment.getCategory();
            creatingAppointment = appointment;
            if (appointment.isRequest()) {
                Intent intent = new Intent(getActivity(), JobStatusSummaryActivity.class);
                startActivity(intent);
            }
        } else if (object instanceof JGGServiceModel) {
            Log.d("Job Model Selected", "==========" + object + "============");
        } else if (object instanceof JGGEventModel) {
            Log.d("Event Model Selected", "==========" + object + "============");
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
