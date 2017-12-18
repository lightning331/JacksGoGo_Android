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

import com.kelvin.jacksgogo.Activities.Search.PostedServiceActivity;
import com.kelvin.jacksgogo.Adapter.Services.ActiveServiceAdapter;
import com.kelvin.jacksgogo.CustomView.Views.ActiveServiceTabView;
import com.kelvin.jacksgogo.R;

public class SearchResultMainFragment extends Fragment {

    private Context mContext;
    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerView;
    ActiveServiceTabView tabView;

    public SearchResultMainFragment() {
        // Required empty public constructor
    }

    public static SearchResultMainFragment newInstance(String param1, String param2) {
        SearchResultMainFragment fragment = new SearchResultMainFragment();
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
        View view = inflater.inflate(R.layout.fragment_search_result_main, container, false);

        initRecyclerView(view);
        initTabView(view);

        return view;
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.search_result_recycler_view);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        }
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        ActiveServiceAdapter adapter = new ActiveServiceAdapter();
        adapter.setOnItemClickListener(new ActiveServiceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick() {
                Intent intent = new Intent(getActivity(), PostedServiceActivity.class);
                intent.putExtra("is_post", false);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);
    }

    private void initTabView(View view) {

        tabView = new ActiveServiceTabView(getContext());
        LinearLayout tabbarLayout = (LinearLayout) view.findViewById(R.id.search_result_tab_view_layout);
        tabbarLayout.addView(tabView);
        tabView.setTabbarItemClickListener(new ActiveServiceTabView.OnTabbarItemClickListener() {
            @Override
            public void onTabbarItemClick(View view) {
                if (view.getId() == R.id.btn_active_service_filter) {
//                    Intent intent = new Intent(getActivity(), ServiceFilterActivity.class);
//                    startActivity(intent);
                } else if (view.getId() == R.id.btn_active_service_mapview) {
                    ActiveServiceMapFragment activeServiceMapFragment = new ActiveServiceMapFragment();

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
