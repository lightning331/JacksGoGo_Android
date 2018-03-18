package com.kelvin.jacksgogo.Fragments.Favourite;

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

import com.kelvin.jacksgogo.Activities.Jobs.JobDetailActivity;
import com.kelvin.jacksgogo.Activities.Search.ServiceDetailActivity;
import com.kelvin.jacksgogo.Adapter.Events.EventsListingAdapter;
import com.kelvin.jacksgogo.Adapter.Jobs.JobsListingAdapter;
import com.kelvin.jacksgogo.Adapter.Services.ActiveServiceAdapter;
import com.kelvin.jacksgogo.Adapter.Users.UserListingAdapter;
import com.kelvin.jacksgogo.R;

import static com.kelvin.jacksgogo.Utils.Global.EVENTS;
import static com.kelvin.jacksgogo.Utils.Global.JOBS;
import static com.kelvin.jacksgogo.Utils.Global.SERVICES;
import static com.kelvin.jacksgogo.Utils.Global.USERS;

public class FavouriteFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    private RecyclerView recyclerView;
    private String appType = SERVICES;

    public FavouriteFragment() {
        // Required empty public constructor
    }

    public static FavouriteFragment newInstance() {
        FavouriteFragment fragment = new FavouriteFragment();
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
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.favourite_recycler_view);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        }
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        refreshFragment(SERVICES);

        return view;
    }

    public void refreshFragment(String textView) {

        appType = textView;

        if (textView == SERVICES) {
            ActiveServiceAdapter adapter = new ActiveServiceAdapter();
            adapter.setOnItemClickListener(new ActiveServiceAdapter.OnItemClickListener() {
                @Override
                public void onItemClick() {
                    Intent intent = new Intent(mContext, ServiceDetailActivity.class);
                    intent.putExtra("is_service", true);
                    mContext.startActivity(intent);
                }
            });
            recyclerView.setAdapter(adapter);
        } else if (textView == JOBS) {
            JobsListingAdapter adapter = new JobsListingAdapter();
            adapter.setOnItemClickListener(new JobsListingAdapter.OnItemClickListener() {
                @Override
                public void onItemClick() {
                    Intent intent = new Intent(mContext, JobDetailActivity.class);
                    mContext.startActivity(intent);
                }
            });
            recyclerView.setAdapter(adapter);
        } else if (textView == EVENTS) {
            EventsListingAdapter adapter = new EventsListingAdapter();
            adapter.setOnItemClickListener(new EventsListingAdapter.OnItemClickListener() {
                @Override
                public void onItemClick() {

                }
            });
            recyclerView.setAdapter(adapter);
        } else if (textView == USERS) {
            UserListingAdapter adapter = new UserListingAdapter(mContext);
            recyclerView.setAdapter(adapter);
        }
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
