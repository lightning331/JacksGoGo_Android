package com.kelvin.jacksgogo.Fragments.Appointments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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

import com.kelvin.jacksgogo.Activities.Jobs.ProgressJobSummaryActivity;
import com.kelvin.jacksgogo.Activities.Search.ServiceDetailActivity;
import com.kelvin.jacksgogo.Adapter.Appointment.AppointmentMainAdapter;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGGetAppsResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.currentUser;
import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.selectedAppointment;
import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.selectedCategory;
import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.selectedProposal;
import static com.kelvin.jacksgogo.Utils.Global.CONFIRMED;
import static com.kelvin.jacksgogo.Utils.Global.HISTORY;
import static com.kelvin.jacksgogo.Utils.Global.PENDING;
import static com.kelvin.jacksgogo.Utils.Global.createProgressDialog;


public class AppMainFragment extends Fragment implements SearchView.OnQueryTextListener {

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    private RecyclerView recyclerView;
    private SearchView searchView;
    private String mStatus;
    private ProgressDialog progressDialog;
    private android.app.AlertDialog alertDialog;

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
        // create list and custom adapter
        refreshFragment(PENDING);
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
        if (isLoggedIn("")) {
            progressDialog = createProgressDialog(mContext);
            JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, mContext);
            Call<JGGGetAppsResponse> call = apiManager.getPendingAppointments(userID, 0, 50);
            call.enqueue(new Callback<JGGGetAppsResponse>() {
                @Override
                public void onResponse(Call<JGGGetAppsResponse> call, Response<JGGGetAppsResponse> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body().getSuccess()) {
                            resetData();
                            arrayAllPendingAppointments = response.body().getValue();
                            filterJobs(response.body().getValue());
                        } else {
                            Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        int statusCode = response.code();
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
    }

    private void filterJobs(ArrayList<JGGAppointmentModel> jobs) {
        for (JGGAppointmentModel job : jobs) {
            if (job.isQuickJob()) {
                arrayLoadedQuickAppointments.add(job);
            } else if (!job.isRequest() && job.getAppointmentType() > 1) {
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
        if (isLoggedIn("Confirmed")) {
            progressDialog = createProgressDialog(mContext);
            JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, mContext);
            retrofit2.Call<JGGGetAppsResponse> call = apiManager.getConfirmedAppointments(userID, 0, 50);
            call.enqueue(new Callback<JGGGetAppsResponse>() {
                @Override
                public void onResponse(Call<JGGGetAppsResponse> call, Response<JGGGetAppsResponse> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body().getSuccess()) {
                            resetData();
                            arrayConfirmedAppointments = response.body().getValue();

                            setDataToAdapter("Confirmed", arrayConfirmedAppointments, false);
                        } else {
                            Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        int statusCode = response.code();
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
    }

    private void loadAppointmentsHistory() {
        if (isLoggedIn("History")) {
            progressDialog = createProgressDialog(mContext);
            JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, mContext);
            retrofit2.Call<JGGGetAppsResponse> call = apiManager.getAppointmentHistory(userID, 0, 50);
            call.enqueue(new Callback<JGGGetAppsResponse>() {
                @Override
                public void onResponse(Call<JGGGetAppsResponse> call, Response<JGGGetAppsResponse> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body().getSuccess()) {
                            resetData();
                            arrayHistoryAppointments = response.body().getValue();

                            setDataToAdapter("History", arrayHistoryAppointments, false);
                        } else {
                            Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        int statusCode = response.code();
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
    }

    private boolean isLoggedIn(String type) {
        if (currentUser == null) {
            if (type.equals("History"))
                setDataToAdapter("History", arrayHistoryAppointments, false);
            else if (type.equals("Confirmed"))
                setDataToAdapter("Confirmed", arrayConfirmedAppointments, false);

            showAlertDialog();
            return false;
        } else {
            userID = currentUser.getID();
            return true;
        }
    }

    public void refreshFragment(String status) {
        mStatus = status;
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
                public void onItemClick(int position, Object object) {
                    onSelectListViewItem(position, object);
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

    private void onSelectListViewItem(int position, Object object) {
        JGGAppointmentModel appointment = (JGGAppointmentModel) object;
        selectedCategory = appointment.getCategory();
        selectedAppointment = appointment;
        if (appointment.isRequest()) {
            Intent intent = new Intent(getActivity(), ProgressJobSummaryActivity.class);
            startActivity(intent);
        } else if (!appointment.isRequest()) {
            Intent intent = new Intent(getActivity(), ServiceDetailActivity.class);
            intent.putExtra("is_service", false);
            startActivity(intent);
        }

        if (mStatus.equals(PENDING)) {

        } else if (mStatus.equals(CONFIRMED)) {

        } else if (mStatus.equals(HISTORY)) {

        }
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
