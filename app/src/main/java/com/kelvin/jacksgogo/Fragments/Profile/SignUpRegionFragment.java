package com.kelvin.jacksgogo.Fragments.Profile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kelvin.jacksgogo.Activities.Profile.SignUpEmailActivity;
import com.kelvin.jacksgogo.Adapter.Profile.RegionAdapter;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Models.System.JGGRegionModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGRegionResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.Global.createProgressDialog;

public class SignUpRegionFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;

    private Context mContext;
    private RecyclerView recyclerView;
    private RegionAdapter mAdapter;
    private LinearLayout btnSignIn;
    private ArrayList<JGGRegionModel> regions = new ArrayList<JGGRegionModel>();
    private ProgressDialog progressDialog;
    
    public static SignUpRegionFragment newInstance() {
        SignUpRegionFragment fragment = new SignUpRegionFragment();
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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up_region, container, false);

        getRegionData();

        recyclerView = (RecyclerView) view.findViewById(R.id.region_recycler_view);
        btnSignIn = (LinearLayout) view.findViewById(R.id.btn_sign_in);
        btnSignIn.setOnClickListener(this);

        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        }
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        mAdapter = new RegionAdapter(mContext, regions);
        mAdapter.setOnItemClickListener(new RegionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String regionID) {
                Intent intent = new Intent(mContext, SignUpEmailActivity.class);
                intent.putExtra("SELECTED_REGION_ID", regionID);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    private void getRegionData() {
        progressDialog = createProgressDialog(mContext);

        JGGAPIManager regionManager = JGGURLManager.getClient().create(JGGAPIManager.class);
        Call<JGGRegionResponse> regionCall = regionManager.getRegions();
        regionCall.enqueue(new Callback<JGGRegionResponse>() {
            @Override
            public void onResponse(Call<JGGRegionResponse> call, Response<JGGRegionResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        regions = response.body().getValue();
                        mAdapter.setData(regions);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode  = response.code();
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGRegionResponse> call, Throwable t) {
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

    @Override
    public void onClick(View view) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        if (view.getId() == (R.id.btn_sign_in)) {
            manager.popBackStack();
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
