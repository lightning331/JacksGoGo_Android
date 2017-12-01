package com.kelvin.jacksgogo.Fragments.Search;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kelvin.jacksgogo.Activities.Search.ServiceFilterActivity;
import com.kelvin.jacksgogo.Adapter.Service.SearchActiveServiceAdapter;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Search.ActiveServiceTabView;
import com.kelvin.jacksgogo.R;

public class ActiveServiceMainFragment extends Fragment implements ActiveServiceMapFragment.OnAcitiveServiceFragmentInteractionListener {

    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerView;
    ActiveServiceTabView tabView;

    public ActiveServiceMainFragment() {
        // Required empty public constructor

    }

    public static ActiveServiceMainFragment newInstance(String param1, String param2) {
        ActiveServiceMainFragment fragment = new ActiveServiceMainFragment();
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
        View view = inflater.inflate(R.layout.search_active_service_main_fragment, container, false);

        initRecyclerView(view);
        initTabView(view);

        return view;
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.active_service_recycler_view);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        }
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        SearchActiveServiceAdapter adapter = new SearchActiveServiceAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void initTabView(View view) {

        tabView = new ActiveServiceTabView(getContext());
        LinearLayout tabbarLayout = (LinearLayout) view.findViewById(R.id.active_service_tab_view_layout);
        tabbarLayout.addView(tabView);
        tabView.setTabbarItemClickListener(new ActiveServiceTabView.OnTabbarItemClickListener() {
            @Override
            public void onTabbarItemClick(View view) {
                if (view.getId() == R.id.btn_active_service_filter) {
                    Intent intent = new Intent(getActivity(), ServiceFilterActivity.class);
                    startActivity(intent);
                } else if (view.getId() == R.id.btn_active_service_mapview) {
                    ActiveServiceMapFragment activeServiceMapFragment = new ActiveServiceMapFragment();

                    activeServiceMapFragment.setOnAcitiveServiceFragmentInteractionListener(ActiveServiceMainFragment.this);

                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.active_service_container, activeServiceMapFragment, activeServiceMapFragment.getTag());
                    ft.addToBackStack("active_service");
                    ft.commit();
                } else {
                    if (view.getId() == R.id.btn_active_service_distance) {

                    } else if (view.getId() == R.id.btn_active_service_ratings) {

                    }
                }
            }
        });
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
