package com.kelvin.jacksgogo.Fragments.Search;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.Activities.Profile.VerifyNewSkillsActivity;
import com.kelvin.jacksgogo.Adapter.Services.CategoryGridAdapter;
import com.kelvin.jacksgogo.CustomView.Views.PostServiceTabbarView;
import com.kelvin.jacksgogo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostServiceSkillVerifiedFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private Context mContext;
    private TextView btnVerify;
    private GridView gridView;
    private ArrayList<Map<String, Object>> datas = new ArrayList<>();

    public static PostServiceSkillVerifiedFragment newInstance(String param1, String param2) {
        PostServiceSkillVerifiedFragment fragment = new PostServiceSkillVerifiedFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        addCategoryData();
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

        gridView = view.findViewById(R.id.post_service_category_grid_view);
        gridView.setNumColumns(4);
        CategoryGridAdapter adapter = new CategoryGridAdapter(mContext, datas, "SERVICES");
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the GridView selected/clicked item text
                String name = datas.get(position).get("name").toString();
                Toast.makeText(getActivity(), name,
                        Toast.LENGTH_LONG).show();

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.post_service_container, PostServiceMainTabFragment.newInstance(PostServiceTabbarView.TabName.DESCRIBE))
                        .addToBackStack("post_service")
                        .commit();
            }
        });
        gridView.setAdapter(adapter);
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

    private void addCategoryData() {
        datas.add(createMap("Messenger", R.mipmap.icon_cat_messenger));
        datas.add(createMap("Running Man", R.mipmap.icon_cat_runningman));
        datas.add(createMap("Sports", R.mipmap.icon_cat_sports));
    }

    private Map<String, Object> createMap(String name, int iconId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("icon", iconId);
        return map;
    }

    public PostServiceSkillVerifiedFragment() {
        // Required empty public constructor
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
