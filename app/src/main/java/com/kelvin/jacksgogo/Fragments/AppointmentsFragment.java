package com.kelvin.jacksgogo.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.kelvin.jacksgogo.Adapters.JGGSeparatedListAdapter;
import com.kelvin.jacksgogo.Models.JGGEventModel;
import com.kelvin.jacksgogo.Models.JGGJobModel;
import com.kelvin.jacksgogo.Models.JGGServiceModel;
import com.kelvin.jacksgogo.Models.JGGServicePackageModel;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Adapters.JGGAppointmentsListAdapter;
import com.kelvin.jacksgogo.Models.JGGAppointmentBaseModel;

import java.util.ArrayList;
import java.util.Date;

import static com.kelvin.jacksgogo.Models.JGGAppointmentBaseModel.AppointmentStatus.CANCELLED;
import static com.kelvin.jacksgogo.Models.JGGAppointmentBaseModel.AppointmentStatus.NONE;
import static com.kelvin.jacksgogo.Models.JGGAppointmentBaseModel.AppointmentStatus.PENDING;
import static com.kelvin.jacksgogo.Models.JGGAppointmentBaseModel.AppointmentStatus.WITHDRAWN;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AppointmentsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AppointmentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppointmentsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ListView listView;
    ArrayList<JGGAppointmentBaseModel> arrayQuickJobs;
    ArrayList<JGGAppointmentBaseModel> arrayServicePackages;
    ArrayList<JGGAppointmentBaseModel> arrayPendingJobs;

    private static JGGAppointmentsListAdapter quickJobsAdapter;
    private static JGGAppointmentsListAdapter servicePackagesAdapter;
    private static JGGAppointmentsListAdapter pendingJobsAdapter;

    public AppointmentsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AppointmentsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AppointmentsFragment newInstance(String param1, String param2) {
        AppointmentsFragment fragment = new AppointmentsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_appointments, container, false);
        listView = (ListView) view.findViewById(R.id.appointment_list_view);

        arrayQuickJobs = new ArrayList<>();
        arrayServicePackages = new ArrayList<>();
        arrayPendingJobs = new ArrayList<>();
        // Quick Jobs
        arrayQuickJobs.add(new JGGServicePackageModel(new Date(), "Fast Food Delivery", NONE, "Needed before 12:00 PM", 0));
        // Service Packages
        arrayServicePackages.add(new JGGJobModel(null, "Group Swimming Class", NONE, "1 slot remaining", 0));
        // Pending Jobs
        arrayPendingJobs.add(new JGGServiceModel(new Date(), "Bring My Dog To Her Grooming Apartment", NONE, "Needed on 21 Dec, 2017", 1));
        arrayPendingJobs.add(new JGGJobModel(new Date(), "Maid Needed", NONE, "Needed on 18 Dec, 2017", 3));
        arrayPendingJobs.add(new JGGEventModel(new Date(), "Delivery - Small Parcel", CANCELLED, "Needed on 25 Dec, 2017", 0));
        arrayPendingJobs.add(new JGGJobModel(new Date(), "Gardening", NONE, "Needed on 18 Dec, 2017", 99));
        arrayPendingJobs.add(new JGGJobModel(new Date(), "Neighbourhood Clean Up", WITHDRAWN, "Needed on 27 Dec, 2017", 0));
        arrayPendingJobs.add(new JGGServiceModel(new Date(), "Help With The Garden", NONE, "We love Badminton\\nEvent on 19 Jul, 2017 10:00 AM - 12:00 PM", 0));
        arrayPendingJobs.add(new JGGServiceModel(new Date(), "Gardening", CANCELLED, "Needed on 29 Dec, 2017", 7));

        quickJobsAdapter = new JGGAppointmentsListAdapter(arrayQuickJobs, getContext());
        servicePackagesAdapter = new JGGAppointmentsListAdapter(arrayServicePackages, getContext());
        pendingJobsAdapter = new JGGAppointmentsListAdapter(arrayPendingJobs, getContext());

        // create our list and custom adapter
        JGGSeparatedListAdapter adapter = new JGGSeparatedListAdapter(this.getContext());

        adapter.addSection("Quick Jobs", quickJobsAdapter);
        adapter.addSection("Service Packages", servicePackagesAdapter);
        adapter.addSection("Pending Jobs", pendingJobsAdapter);

        listView.setAdapter(adapter);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
