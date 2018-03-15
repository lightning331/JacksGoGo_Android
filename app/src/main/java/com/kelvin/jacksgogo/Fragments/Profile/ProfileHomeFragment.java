package com.kelvin.jacksgogo.Fragments.Profile;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.kelvin.jacksgogo.Adapter.Profile.ProfileHomeAdapter;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Profile.ProfileHomeCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Profile.ProfileHomeHeaderCell;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGAppManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.Responses.JGGBaseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Adapter.Profile.ProfileHomeAdapter.ABOUT_TYPE;
import static com.kelvin.jacksgogo.Adapter.Profile.ProfileHomeAdapter.JOINED_GOCLUB_TYPE;
import static com.kelvin.jacksgogo.Adapter.Profile.ProfileHomeAdapter.SETTINGS_TYPE;
import static com.kelvin.jacksgogo.Adapter.Profile.ProfileHomeAdapter.SIGNOUT_TYPE;

public class ProfileHomeFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    private RecyclerView recyclerView;
    private ProfileHomeAdapter adapter;
    private ProgressDialog progressDialog;

    public ProfileHomeFragment() {
        // Required empty public constructor
    }

    public static ProfileHomeFragment newInstance() {
        ProfileHomeFragment fragment = new ProfileHomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile_home, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.profile_home_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        adapter = new ProfileHomeAdapter(mContext);
        adapter.setOnItemClickListener(new ProfileHomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder holder, int position) {
                if (holder instanceof ProfileHomeHeaderCell) {
                    ProfileHomeHeaderCell header = (ProfileHomeHeaderCell) holder;

                } else if (holder instanceof ProfileHomeCell) {
                    if (position == JOINED_GOCLUB_TYPE) {

                    } else if (position == SETTINGS_TYPE) {

                    } else if (position == ABOUT_TYPE) {

                    }
                } else if (position == SIGNOUT_TYPE) {
                    accountSignOut();
                }
            }
        });

        recyclerView.setAdapter(adapter);

        return view;
    }

    private void accountSignOut() {
        progressDialog = Global.createProgressDialog(mContext);
        JGGAPIManager signInManager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        Call<JGGBaseResponse> call = signInManager.accountSignOut();
        call.enqueue(new Callback<JGGBaseResponse>() {
            @Override
            public void onResponse(Call<JGGBaseResponse> call, Response<JGGBaseResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        JGGAppManager.clearAll();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, SignInFragment.newInstance())
                                .commit();
                    } else {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode  = response.code();
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGBaseResponse> call, Throwable t) {
                Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
