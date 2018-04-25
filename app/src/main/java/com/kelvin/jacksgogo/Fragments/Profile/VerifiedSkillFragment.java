package com.kelvin.jacksgogo.Fragments.Profile;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kelvin.jacksgogo.Adapter.CategoryAdapter;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;

import java.util.ArrayList;

public class VerifiedSkillFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    private ArrayList<JGGCategoryModel> mCategories;

    public VerifiedSkillFragment() {
        // Required empty public constructor
    }

    public static VerifiedSkillFragment newInstance(String param1, String param2) {
        VerifiedSkillFragment fragment = new VerifiedSkillFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        //addCategoryData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_verified_skill, container, false);

        mCategories = JGGAppManager.getInstance().getCategories();
        recyclerView = view.findViewById(R.id.category_recycler_view);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.VERTICAL, false));
        }
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        adapter = new CategoryAdapter(mContext, mCategories, AppointmentType.SERVICES);
        adapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String name = mCategories.get(position).getName();
                Toast.makeText(mContext, name,
                        Toast.LENGTH_LONG).show();

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.verify_skill_container, new NotVerifiedSkillFragment())
                        .addToBackStack("verify_skill")
                        .commit();
            }
        });
        recyclerView.setAdapter(adapter);

        return view;
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

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
