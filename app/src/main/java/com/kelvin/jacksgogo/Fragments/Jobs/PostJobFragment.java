package com.kelvin.jacksgogo.Fragments.Jobs;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.kelvin.jacksgogo.Adapter.Services.CategoryGridAdapter;
import com.kelvin.jacksgogo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostJobFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    private GridView gridView;
    private ArrayList<Map<String, Object>> datas = new ArrayList<>();

    public PostJobFragment() {
        // Required empty public constructor
    }

    public static PostJobFragment newInstance(String param1, String param2) {
        PostJobFragment fragment = new PostJobFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        View view = inflater.inflate(R.layout.fragment_post_job, container, false);

        gridView = (GridView) view.findViewById(R.id.post_job_category_grid_view);
        gridView.setNumColumns(4);
        CategoryGridAdapter adapter = new CategoryGridAdapter(mContext, datas, "JOBS");
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the GridView selected/clicked item text
                String name = datas.get(position).get("name").toString();
                Toast.makeText(getActivity(), name,
                        Toast.LENGTH_LONG).show();
            }
        });
        gridView.setAdapter(adapter);

        return view;
    }

    private void addCategoryData() {
        datas.add(createMap("Quick Jobs", R.mipmap.icon_cat_quickjob));
        datas.add(createMap("Favourited Services", R.mipmap.icon_cat_favourites));
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
