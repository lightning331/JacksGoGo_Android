package com.kelvin.jacksgogo.Fragments.Search;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.Search.SearchResultActivity;
import com.kelvin.jacksgogo.R;

public class ServiceSearchMainFragment extends Fragment implements View.OnClickListener {

    private Context mContext;
    private OnFragmentInteractionListener mListener;

    private EditText txtSearch;
    private ImageView btnSearch;
    private TextView btnAdvanced;
    private LinearLayout recyclerViewLayout;
    private RecyclerView recyclerView;
    private TextView btnJobs;
    private TextView btnGoClub;
    private TextView btnEvent;
    private TextView btnUser;

    public ServiceSearchMainFragment() {
        // Required empty public constructor
    }

    public static ServiceSearchMainFragment newInstance(String param1, String param2) {
        ServiceSearchMainFragment fragment = new ServiceSearchMainFragment();
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
        View view = inflater.inflate(R.layout.fragment_service_search_main, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        txtSearch = view.findViewById(R.id.txt_service_search);
        btnSearch = view.findViewById(R.id.btn_service_search);
        btnAdvanced = view.findViewById(R.id.btn_service_search_advanced);
        recyclerViewLayout = view.findViewById(R.id.service_search_recycler_view_layout);
        recyclerView = view.findViewById(R.id.service_search_recycler_view);
        btnJobs = view.findViewById(R.id.btn_service_search_jobs);
        btnGoClub = view.findViewById(R.id.btn_service_search_go_club);
        btnEvent = view.findViewById(R.id.btn_service_search_event);
        btnUser = view.findViewById(R.id.btn_service_search_user);

        btnSearch.setOnClickListener(this);
        btnAdvanced.setOnClickListener(this);
        btnJobs.setOnClickListener(this);
        btnGoClub.setOnClickListener(this);
        btnEvent.setOnClickListener(this);
        btnUser.setOnClickListener(this);
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
    public void onClick(View view) {
        if (view.getId() == R.id.btn_service_search_advanced) {
            ServiceSearchAdvanceFragment frag = new ServiceSearchAdvanceFragment();
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.service_search_container, frag, frag.getTag());
            ft.addToBackStack("advance_search");
            ft.commit();
        } else if (view.getId() == R.id.btn_service_search) {
            Intent intent = new Intent(mContext, SearchResultActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.btn_service_search_jobs) {

        } else if (view.getId() == R.id.btn_service_search_go_club) {

        } else if (view.getId() == R.id.btn_service_search_event) {

        } else if (view.getId() == R.id.btn_service_search_user) {

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
