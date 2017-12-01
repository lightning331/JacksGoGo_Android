package com.kelvin.jacksgogo.Fragments.Appointments;

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

import com.kelvin.jacksgogo.Activities.Appointment.JobDetailActivity;
import com.kelvin.jacksgogo.Adapter.Appointment.AppointmentMainAdapter;
import com.kelvin.jacksgogo.Models.Jobs_Services.JGGEventModel;
import com.kelvin.jacksgogo.Models.Jobs_Services.JGGJobModel;
import com.kelvin.jacksgogo.Models.Jobs_Services.JGGServiceModel;
import com.kelvin.jacksgogo.Models.Jobs_Services.JGGServicePackageModel;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Models.Jobs_Services.JGGAppBaseModel;

import java.util.ArrayList;
import java.util.Date;

import static com.kelvin.jacksgogo.Models.Jobs_Services.JGGAppBaseModel.AppointmentStatus.CANCELLED;
import static com.kelvin.jacksgogo.Models.Jobs_Services.JGGAppBaseModel.AppointmentStatus.NONE;
import static com.kelvin.jacksgogo.Models.Jobs_Services.JGGAppBaseModel.AppointmentStatus.WITHDRAWN;


public class AppMainFragment extends Fragment implements SearchView.OnQueryTextListener {

    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerView;
    SearchView searchView;
    Object searchTag;

    ArrayList<JGGAppBaseModel> arrayQuickJobs = new ArrayList<>();
    ArrayList<JGGAppBaseModel> arrayServicePackages = new ArrayList<>();
    ArrayList<JGGAppBaseModel> arrayPendingJobs = new ArrayList<>();
    ArrayList<JGGAppBaseModel> arrayConfirmedJobs = new ArrayList<>();
    ArrayList<JGGAppBaseModel> arrayHistoryJobs = new ArrayList<>();
    ArrayList<JGGAppBaseModel> filteredArrayList = new ArrayList<>();

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
        View view = inflater.inflate(R.layout.app_main_fragment, container, false);
        searchView = (SearchView) view.findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.appointment_recycler_view);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        }
        // Quick Jobs
        arrayQuickJobs.add(new JGGServicePackageModel(new Date(), "Fast Food Delivery", NONE, "Needed before 12:00 PM", 0));
        // Service Packages
        arrayServicePackages.add(new JGGJobModel(null, "Group Swimming Class", NONE, "1 slot remaining", 0));
        // Pending Jobs
        arrayPendingJobs.add(new JGGServiceModel(new Date(), "Bring My Dog To Her Grooming Apartment", NONE, "Needed on 21 Dec, 2017", 1));
        arrayPendingJobs.add(new JGGJobModel(new Date(), "Maid Needed", NONE, "Needed on 18 Dec, 2017", 3));
        arrayPendingJobs.add(new JGGEventModel(new Date(), "Delivery - Small Parcel", CANCELLED, "Needed on 25 Dec, 2017", 0));
        arrayPendingJobs.add(new JGGServiceModel(new Date(), "Gardening", NONE, "Needed on 18 Dec, 2017", 99));
        arrayPendingJobs.add(new JGGJobModel(new Date(), "Neighbourhood Clean Up", WITHDRAWN, "Needed on 27 Dec, 2017", 0));
        arrayPendingJobs.add(new JGGServiceModel(new Date(), "Help With The Garden", WITHDRAWN, "We love Badminton\\nEvent on 19 Jul, 2017 10:00 AM - 12:00 PM", 0));
        arrayPendingJobs.add(new JGGJobModel(new Date(), "Gardening", NONE, "Needed on 29 Dec, 2017", 7));
        arrayPendingJobs.add(new JGGJobModel(new Date(), "Delivery - Small Parcel", CANCELLED, "Needed on 31 Dec, 2017", 0));
        arrayPendingJobs.add(new JGGServiceModel(new Date(), "Delivery - Small Parcel", WITHDRAWN, "Needed on 31 Dec, 2017", 0));
        arrayPendingJobs.add(new JGGJobModel(new Date(), "Delivery - Small Parcel", NONE, "Needed on 31 Dec, 2017", 0));
        arrayPendingJobs.add(new JGGEventModel(new Date(), "Bring My Dog To Her Grooming Apartment", NONE, "Needed on 21 Dec, 2017", 1));

        // Confirmed Jobs
        arrayConfirmedJobs.add(new JGGJobModel(new Date(), "Neighbourhood Clean Up", WITHDRAWN, "Needed on 27 Dec, 2017", 0));
        arrayConfirmedJobs.add(new JGGServiceModel(new Date(), "Help With The Garden", WITHDRAWN, "We love Badminton\\nEvent on 19 Jul, 2017 10:00 AM - 12:00 PM", 0));
        arrayConfirmedJobs.add(new JGGServiceModel(new Date(), "Gardening", NONE, "Needed on 29 Dec, 2017", 7));
        arrayConfirmedJobs.add(new JGGJobModel(new Date(), "Delivery - Small Parcel", CANCELLED, "Needed on 31 Dec, 2017", 0));
        arrayConfirmedJobs.add(new JGGServiceModel(new Date(), "Delivery - Small Parcel", WITHDRAWN, "Needed on 31 Dec, 2017", 0));
        arrayConfirmedJobs.add(new JGGJobModel(new Date(), "Delivery - Small Parcel", NONE, "Needed on 31 Dec, 2017", 0));
        arrayConfirmedJobs.add(new JGGServiceModel(new Date(), "Bring My Dog To Her Grooming Apartment", NONE, "Needed on 21 Dec, 2017", 1));
        arrayConfirmedJobs.add(new JGGServiceModel(new Date(), "Gardening", NONE, "Needed on 29 Dec, 2017", 7));
        arrayConfirmedJobs.add(new JGGJobModel(new Date(), "Delivery - Small Parcel", CANCELLED, "Needed on 31 Dec, 2017", 0));
        arrayConfirmedJobs.add(new JGGServiceModel(new Date(), "Delivery - Small Parcel", WITHDRAWN, "Needed on 31 Dec, 2017", 0));
        arrayConfirmedJobs.add(new JGGEventModel(new Date(), "Delivery - Small Parcel", NONE, "Needed on 31 Dec, 2017", 0));

        // History Jobs
        arrayHistoryJobs.add(new JGGJobModel(new Date(), "Maid Needed", NONE, "Needed on 18 Dec, 2017", 3));
        arrayHistoryJobs.add(new JGGEventModel(new Date(), "Delivery - Small Parcel", CANCELLED, "Needed on 25 Dec, 2017", 0));
        arrayHistoryJobs.add(new JGGJobModel(new Date(), "Gardening", NONE, "Needed on 18 Dec, 2017", 99));
        arrayHistoryJobs.add(new JGGJobModel(new Date(), "Neighbourhood Clean Up", WITHDRAWN, "Needed on 27 Dec, 2017", 0));
        arrayHistoryJobs.add(new JGGServiceModel(new Date(), "Help With The Garden", WITHDRAWN, "We love Badminton\\nEvent on 19 Jul, 2017 10:00 AM - 12:00 PM", 0));
        arrayHistoryJobs.add(new JGGServiceModel(new Date(), "Gardening", NONE, "Needed on 29 Dec, 2017", 7));
        arrayHistoryJobs.add(new JGGJobModel(new Date(), "Delivery - Small Parcel", CANCELLED, "Needed on 31 Dec, 2017", 0));
        arrayHistoryJobs.add(new JGGServiceModel(new Date(), "Delivery - Small Parcel", WITHDRAWN, "Needed on 31 Dec, 2017", 0));
        arrayHistoryJobs.add(new JGGEventModel(new Date(), "Delivery - Small Parcel", NONE, "Needed on 31 Dec, 2017", 0));

        // create list and custom adapter
        refreshFragment("PENDING");

        return view;
    }

    public void refreshFragment(Object textView) {

        //Log.d("Refresh Fragement", "=====================" + searchTag);

        searchTag = textView;
        if (textView == "PENDING") {
            searchView.setQueryHint("Search through Pending list");

            pendingListAdapter = new AppointmentMainAdapter(getContext());
            if (arrayQuickJobs.size() > 0) pendingListAdapter.addSection("Quick Jobs", arrayQuickJobs);
            if (arrayServicePackages.size() > 0) pendingListAdapter.addSection("Service Packages", arrayServicePackages);
            if (arrayPendingJobs.size() > 0) pendingListAdapter.addSection("Pending Jobs", arrayPendingJobs);
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

        } else if (textView == "CONFIRM") {
            searchView.setQueryHint("Search through Confirmed list");
            setDataToAdapter(confirmedListAdapter, "Confirmed",
                    arrayConfirmedJobs, false);

        } else if (textView == "HISTORY") {
            searchView.setQueryHint("Search through History list");
            setDataToAdapter(historyListAdapter, "History",
                    arrayHistoryJobs, false);
        }
    }

    public void setDataToAdapter(AppointmentMainAdapter adapter, String sectionTitle,
                                 ArrayList<JGGAppBaseModel> arrayList, Boolean isFilter) {
        adapter = new AppointmentMainAdapter(getContext());

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
    public void onAttach(Context context) {
        super.onAttach(context);
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
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        Log.d("Search Tag", "==========" + searchTag + "==========");

        if (searchTag == "PENDING") {
            filteredArrayList = filter(arrayPendingJobs, newText);

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
            filteredArrayList = filter(arrayConfirmedJobs, newText);

            setDataToAdapter(confirmedListAdapter, "Confirmed",
                    filteredArrayList, true);

        } else if (searchTag == "HISTORY") {
            filteredArrayList = filter(arrayHistoryJobs, newText);

            setDataToAdapter(historyListAdapter, "History",
                    filteredArrayList, true);
        }

        return true;
    }

    private ArrayList<JGGAppBaseModel> filter(ArrayList<JGGAppBaseModel> models, String query) {
        query = query.toLowerCase();
        final ArrayList<JGGAppBaseModel> filteredModelList = new ArrayList<>();
        for (JGGAppBaseModel model : models) {
            final String text = model.getTitle().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    private void onSelectListViewItem(int position, Object object) {
        if (object instanceof JGGServiceModel) {
            Log.d("Service Model Selected", "==========" + object + "============");
            Intent intent = new Intent(getActivity(), JobDetailActivity.class);
            startActivity(intent);
        } else if (object instanceof JGGJobModel) {
            Log.d("Job Model Selected", "==========" + object + "============");
        } else if (object instanceof JGGEventModel) {
            Log.d("Event Model Selected", "==========" + object + "============");
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
