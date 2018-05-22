package com.kelvin.jacksgogo.Fragments.Appointments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.Activities.Jobs.IncomingJobActivity;
import com.kelvin.jacksgogo.Activities.Jobs.OutgoingJobActivity;
import com.kelvin.jacksgogo.Activities.MainActivity;
import com.kelvin.jacksgogo.Activities.Service.BookedServiceActivity;
import com.kelvin.jacksgogo.Adapter.Appointment.AppointmentMainAdapter;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserProfileModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGGetAppsResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.Global.CONFIRMED;
import static com.kelvin.jacksgogo.Utils.Global.HISTORY;
import static com.kelvin.jacksgogo.Utils.Global.PENDING;


public class AppMainFragment extends Fragment implements SearchView.OnQueryTextListener {

    private OnFragmentInteractionListener mListener;
    private Context mContext;
    @BindView(R.id.swipeSearchContainer) SwipeRefreshLayout swipeContainer;

    private RecyclerView recyclerView;
    private SearchView searchView;
    private LinearLayout loginLayout;
    private LinearLayout recyclerViewLayout;
    private TextView btnLogin;

    private String mStatus;
    private AlertDialog alertDialog;

    ArrayList<JGGAppointmentModel> arrayAllPendingAppointments = new ArrayList<>();
    ArrayList<JGGAppointmentModel> arrayLoadedQuickAppointments = new ArrayList<>();
    ArrayList<JGGAppointmentModel> arrayLoadedServicePackages = new ArrayList<>();
    ArrayList<JGGAppointmentModel> arrayLoadedPendingAppointments = new ArrayList<>();
    ArrayList<JGGAppointmentModel> arrayConfirmedAppointments = new ArrayList<>();
    ArrayList<JGGAppointmentModel> arrayHistoryAppointments = new ArrayList<>();
    ArrayList<JGGAppointmentModel> filteredArrayList = new ArrayList<>();

    private static AppointmentMainAdapter pendingListAdapter;
    private static AppointmentMainAdapter confirmedListAdapter;
    private static AppointmentMainAdapter historyListAdapter;
    private String userID;
    private JGGUserProfileModel currentUser;

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
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint()) {
            return;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_app_main, container, false);
        ButterKnife.bind(this, view);

        currentUser = JGGAppManager.getInstance().getCurrentUser();

        searchView = (SearchView) view.findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(this);
        pendingListAdapter = new AppointmentMainAdapter(getContext());

        loginLayout = view.findViewById(R.id.login_layout);
        recyclerViewLayout = view.findViewById(R.id.recycler_view_layout);
        btnLogin = view.findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)mContext).setProfilePage();
            }
        });

        // Setup refresh listener which triggers new data loading
        swipeContainer.setColorSchemeResources(R.color.JGGOrange,
                R.color.JGGOrange,
                R.color.JGGOrange,
                R.color.JGGOrange);

        recyclerView = (RecyclerView) view.findViewById(R.id.appointment_recycler_view);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        }

        refreshFragment(PENDING);

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
        swipeContainer.setRefreshing(true);
        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        Call<JGGGetAppsResponse> call = apiManager.getPendingAppointments(userID, 0, 50);
        call.enqueue(new Callback<JGGGetAppsResponse>() {
            @Override
            public void onResponse(Call<JGGGetAppsResponse> call, Response<JGGGetAppsResponse> response) {
                swipeContainer.setRefreshing(false);
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        resetData();
                        arrayAllPendingAppointments = response.body().getValue();
                        filterJobs(response.body().getValue());
                    } else {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGGetAppsResponse> call, Throwable t) {
                swipeContainer.setRefreshing(false);
                Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterJobs(ArrayList<JGGAppointmentModel> jobs) {
        for (JGGAppointmentModel job : jobs) {
            if (job.isQuickJob()) {
                arrayLoadedQuickAppointments.add(job);
            } else if (job.isRequest() && job.getAppointmentType() > 1) {
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
            public void onItemClick(JGGAppointmentModel appointment) {
                // ListView item select
                onSelectListViewItem(appointment);
            }
        });
    }

    private void loadConfirmedAppointments() {
        swipeContainer.setRefreshing(true);
        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        retrofit2.Call<JGGGetAppsResponse> call = apiManager.getConfirmedAppointments(userID, 0, 50);
        call.enqueue(new Callback<JGGGetAppsResponse>() {
            @Override
            public void onResponse(Call<JGGGetAppsResponse> call, Response<JGGGetAppsResponse> response) {
                swipeContainer.setRefreshing(false);
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        resetData();
                        arrayConfirmedAppointments = response.body().getValue();

                        setDataToAdapter("Confirmed", arrayConfirmedAppointments, false);
                    } else {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGGetAppsResponse> call, Throwable t) {
                swipeContainer.setRefreshing(false);
                Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadAppointmentsHistory() {
        swipeContainer.setRefreshing(true);
        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        retrofit2.Call<JGGGetAppsResponse> call = apiManager.getWastedAppointments(userID, 0, 50);
        call.enqueue(new Callback<JGGGetAppsResponse>() {
            @Override
            public void onResponse(Call<JGGGetAppsResponse> call, Response<JGGGetAppsResponse> response) {
                swipeContainer.setRefreshing(false);
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        resetData();
                        arrayHistoryAppointments = response.body().getValue();

                        setDataToAdapter("History", arrayHistoryAppointments, false);
                    } else {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGGetAppsResponse> call, Throwable t) {
                swipeContainer.setRefreshing(false);
                Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isLoggedIn() {
        if (currentUser == null) {
            return false;
        } else {
            userID = currentUser.getID();
            return true;
        }
    }

    public void refreshFragment(final String status) {
        mStatus = status;
        if (isLoggedIn()) {
            loginLayout.setVisibility(View.GONE);
            recyclerViewLayout.setVisibility(View.VISIBLE);

            swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeContainer.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (status.equals(PENDING)) {
                                                    searchView.setQueryHint("Search through Pending list");
                                                    loadPendingAppointments();

                                                } else if (status.equals(CONFIRMED)) {
                                                    searchView.setQueryHint("Search through Confirmed list");
                                                    loadConfirmedAppointments();

                                                } else if (status.equals(HISTORY)) {
                                                    searchView.setQueryHint("Search through History list");
                                                    loadAppointmentsHistory();
                                                }
                                            }
                                        }
                    );
                }
            });
            if (status.equals(PENDING)) {
                searchView.setQueryHint("Search through Pending list");
                loadPendingAppointments();

            } else if (status.equals(CONFIRMED)) {
                searchView.setQueryHint("Search through Confirmed list");
                loadConfirmedAppointments();

            } else if (status.equals(HISTORY)) {
                searchView.setQueryHint("Search through History list");
                loadAppointmentsHistory();
            }
        } else {
            recyclerViewLayout.setVisibility(View.GONE);
            loginLayout.setVisibility(View.VISIBLE);
        }
    }

    public void setDataToAdapter(String sectionTitle,
                                 ArrayList<JGGAppointmentModel> arrayList, Boolean isFilter) {
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
            public void onItemClick(JGGAppointmentModel appointment) {
                onSelectListViewItem(appointment);
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (mStatus.equals(PENDING)) {
            filteredArrayList = filter(arrayAllPendingAppointments, newText);

            pendingListAdapter = new AppointmentMainAdapter(getContext());
            pendingListAdapter.addSection("Pending Jobs", filteredArrayList);
            pendingListAdapter.setFilter(filteredArrayList);
            recyclerView.setAdapter(pendingListAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.refreshDrawableState();

            pendingListAdapter.setOnItemClickListener(new AppointmentMainAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(JGGAppointmentModel appointment) {
                    onSelectListViewItem(appointment);
                }
            });

        } else if (mStatus.equals(CONFIRMED)) {
            filteredArrayList = filter(arrayConfirmedAppointments, newText);

            setDataToAdapter("Confirmed",
                    filteredArrayList, true);

        } else if (mStatus.equals(HISTORY)) {
            filteredArrayList = filter(arrayHistoryAppointments, newText);

            setDataToAdapter("History",
                    filteredArrayList, true);
        }

        return true;
    }

    private ArrayList<JGGAppointmentModel> filter(ArrayList<JGGAppointmentModel> models, String query) {
        query = query.toLowerCase();
        final ArrayList<JGGAppointmentModel> filteredModelList = new ArrayList<>();
        for (JGGAppointmentModel model : models) {
            final String text = model.getTitle().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    private void onSelectListViewItem(JGGAppointmentModel appointment) {

        JGGAppManager.getInstance().setSelectedCategory(appointment.getCategory());
        JGGAppManager.getInstance().setSelectedAppointment(appointment);

        if (appointment.isQuickJob()) {

        } else if (appointment.isRequest() && appointment.getAppointmentType() > 1) {
            mContext.startActivity(new Intent(mContext, BookedServiceActivity.class));
        } else {
            if (appointment.getUserProfileID().equals(currentUser.getID())) {
                // TODO - The job which I posted
                Intent intent = new Intent(mContext, OutgoingJobActivity.class);
                mContext.startActivity(intent);
            } else {
                // TODO - The job which I sent proposal or get invited
                Intent intent = new Intent(mContext, IncomingJobActivity.class);
                mContext.startActivity(intent);
            }
        }

        if (mStatus.equals(PENDING)) {

        } else if (mStatus.equals(CONFIRMED)) {

        } else if (mStatus.equals(HISTORY)) {

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
