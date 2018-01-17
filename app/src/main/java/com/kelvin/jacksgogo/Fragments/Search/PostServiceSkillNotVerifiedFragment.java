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
import android.widget.Toast;

import com.kelvin.jacksgogo.Activities.Profile.VerifyNewSkillsActivity;
import com.kelvin.jacksgogo.Adapter.Services.CategoryGridAdapter;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostServiceSkillNotVerifiedFragment extends Fragment implements View.OnClickListener {

    private Context mContext;
    private GridView gridView;
    private ArrayList<Map<String, Object>> datas = new ArrayList<>();

    public static PostServiceSkillNotVerifiedFragment newInstance(String param1, String param2) {
        PostServiceSkillNotVerifiedFragment fragment = new PostServiceSkillNotVerifiedFragment();
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
        View view = inflater.inflate(R.layout.fragment_post_service_skill_not_verified, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        ArrayList<JGGCategoryModel> categories = JGGAppManager.getInstance(mContext).categories;

        gridView = view.findViewById(R.id.post_service_not_verified_category_grid_view);
        gridView.setNumColumns(4);
        CategoryGridAdapter adapter = new CategoryGridAdapter(mContext, categories, "SERVICES");
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the GridView selected/clicked item text
                Intent intent = new Intent(mContext, VerifyNewSkillsActivity.class);
                intent.putExtra("already_verified_skills", false);
                mContext.startActivity(intent);

                String name = datas.get(position).get("name").toString();
                Toast.makeText(getActivity(), name,
                        Toast.LENGTH_LONG).show();
            }
        });
        gridView.setAdapter(adapter);
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

    private OnFragmentInteractionListener mListener;

    public PostServiceSkillNotVerifiedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onClick(View view) {

    }

    private void addCategoryData() {
        datas.add(createMap("Cooking & Baking", R.mipmap.icon_cat_cooking_baking));
        datas.add(createMap("Education", R.mipmap.icon_cat_education));
        datas.add(createMap("Handyman", R.mipmap.icon_cat_handyman));
        datas.add(createMap("Household Chores", R.mipmap.icon_cat_householdchores));
        datas.add(createMap("Messenger", R.mipmap.icon_cat_messenger));
        datas.add(createMap("Running Man", R.mipmap.icon_cat_runningman));
        datas.add(createMap("Sports", R.mipmap.icon_cat_sports));
        datas.add(createMap("Other Professions", R.mipmap.icon_cat_other));
    }

    private Map<String, Object> createMap(String name, int iconId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("icon", iconId);
        return map;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
