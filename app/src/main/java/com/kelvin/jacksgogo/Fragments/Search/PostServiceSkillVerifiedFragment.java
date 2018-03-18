package com.kelvin.jacksgogo.Fragments.Search;

import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.Profile.VerifyNewSkillsActivity;
import com.kelvin.jacksgogo.Adapter.CategoryCellAdapter;
import com.kelvin.jacksgogo.CustomView.Views.PostServiceTabbarView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppBaseModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;

import java.util.ArrayList;

import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.selectedAppointment;
import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.selectedCategory;

public class PostServiceSkillVerifiedFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private Context mContext;
    private TextView btnVerify;
    private RecyclerView recyclerView;
    private CategoryCellAdapter adapter;

    private ArrayList<JGGCategoryModel> mCategories;

    public PostServiceSkillVerifiedFragment() {
        // Required empty public constructor
    }

    public static PostServiceSkillVerifiedFragment newInstance(String param1, String param2) {
        PostServiceSkillVerifiedFragment fragment = new PostServiceSkillVerifiedFragment();

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
        View view = inflater.inflate(R.layout.fragment_post_service_skill_verified, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        btnVerify = view.findViewById(R.id.btn_verify_new_skills);
        btnVerify.setOnClickListener(this);
        mCategories = JGGAppManager.getInstance(mContext).categories;

        recyclerView = view.findViewById(R.id.category_recycler_view);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.VERTICAL, false));
        }
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        adapter = new CategoryCellAdapter(mContext, mCategories, JGGAppBaseModel.AppointmentType.SERVICES);
        adapter.setOnItemClickListener(new CategoryCellAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                PostServiceMainTabFragment frag = PostServiceMainTabFragment.newInstance(PostServiceTabbarView.PostServiceTabName.DESCRIBE);
                selectedCategory = mCategories.get(position);
                selectedAppointment.setCategory(selectedCategory);
                selectedAppointment.setCategoryID(selectedCategory.getID());
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.post_service_container, frag)
                        .addToBackStack("post_service")
                        .commit();
            }
        });
        recyclerView.setAdapter(adapter);
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
        if (view.getId() == R.id.btn_verify_new_skills) {
            Intent intent = new Intent(mContext, VerifyNewSkillsActivity.class);
            intent.putExtra("already_verified_skills", true);
            mContext.startActivity(intent);
        } else {

        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
