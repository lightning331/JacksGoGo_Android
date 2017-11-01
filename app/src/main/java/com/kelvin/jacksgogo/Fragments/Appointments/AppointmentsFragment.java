package com.kelvin.jacksgogo.Fragments.Appointments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kelvin.jacksgogo.Adapters.AppointmentsRecyclerViewAdapter;
import com.kelvin.jacksgogo.Models.JGGEventModel;
import com.kelvin.jacksgogo.Models.JGGJobModel;
import com.kelvin.jacksgogo.Models.JGGServiceModel;
import com.kelvin.jacksgogo.Models.JGGServicePackageModel;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Models.JGGAppointmentBaseModel;

import java.util.ArrayList;
import java.util.Date;

import static com.kelvin.jacksgogo.Models.JGGAppointmentBaseModel.AppointmentStatus.CANCELLED;
import static com.kelvin.jacksgogo.Models.JGGAppointmentBaseModel.AppointmentStatus.NONE;
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

    private OnFragmentInteractionListener mListener;

    RecyclerView appointmentRecyclerView;
    ArrayList<JGGAppointmentBaseModel> arrayQuickJobs;
    ArrayList<JGGAppointmentBaseModel> arrayServicePackages;
    ArrayList<JGGAppointmentBaseModel> arrayPendingJobs;

    private static AppointmentsRecyclerViewAdapter appointmentListAdapter;

    public AppointmentsFragment() {
        // Required empty public constructor
    }

    public static AppointmentsFragment newInstance() {
        AppointmentsFragment fragment = new AppointmentsFragment();
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
        View view = inflater.inflate(R.layout.appointments_fragment, container, false);

        appointmentRecyclerView = (RecyclerView) view.findViewById(R.id.appointment_recycler_view);
        if (appointmentRecyclerView != null) {
            appointmentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        }
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
        arrayPendingJobs.add(new JGGServiceModel(new Date(), "Help With The Garden", WITHDRAWN, "We love Badminton\\nEvent on 19 Jul, 2017 10:00 AM - 12:00 PM", 0));
        arrayPendingJobs.add(new JGGServiceModel(new Date(), "Gardening", NONE, "Needed on 29 Dec, 2017", 7));
        arrayPendingJobs.add(new JGGJobModel(new Date(), "Delivery - Small Parcel", CANCELLED, "Needed on 31 Dec, 2017", 0));
        arrayPendingJobs.add(new JGGServiceModel(new Date(), "Delivery - Small Parcel", WITHDRAWN, "Needed on 31 Dec, 2017", 0));
        arrayPendingJobs.add(new JGGServicePackageModel(new Date(), "Delivery - Small Parcel", NONE, "Needed on 31 Dec, 2017", 0));
        arrayPendingJobs.add(new JGGServiceModel(new Date(), "Bring My Dog To Her Grooming Apartment", NONE, "Needed on 21 Dec, 2017", 1));

        // create our list and custom adapter
        appointmentListAdapter = new AppointmentsRecyclerViewAdapter(getContext());
        appointmentListAdapter.addSection("Quick Jobs", arrayQuickJobs);
        appointmentListAdapter.addSection("Service Packages", arrayServicePackages);
        appointmentListAdapter.addSection("Pending Jobs", arrayPendingJobs);

        appointmentRecyclerView.setAdapter(appointmentListAdapter);

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
