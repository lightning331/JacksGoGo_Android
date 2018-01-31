package com.kelvin.jacksgogo.Fragments.Jobs;

import android.app.ProgressDialog;
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

import com.kelvin.jacksgogo.Adapter.CategoryCellAdapter;
import com.kelvin.jacksgogo.CustomView.Views.PostJobTabbarView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGAppManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppBaseModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGCategoryResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.selectedCategory;

public class PostJobCategoryFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    private RecyclerView recyclerView;
    private CategoryCellAdapter adapter;
    private View view;
    private ArrayList<JGGCategoryModel> categories;

    private ProgressDialog progressDialog;

    public PostJobCategoryFragment() {
        // Required empty public constructor
    }

    public static PostJobCategoryFragment newInstance(String param1, String param2) {
        PostJobCategoryFragment fragment = new PostJobCategoryFragment();
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
        view = inflater.inflate(R.layout.fragment_post_job_category, container, false);

        loadCategories();
        initRecyclerView(view);
        return view;
    }

    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.post_job_category_recycler_view);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        }
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        adapter = new CategoryCellAdapter(mContext, categories, JGGAppBaseModel.AppointmentType.JOBS);
        adapter.setOnItemClickListener(new CategoryCellAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position == 0) {

                } else {
                    PostJobMainTabFragment frag = PostJobMainTabFragment.newInstance(PostJobTabbarView.TabName.DESCRIBE);
                    selectedCategory = categories.get(position - 1);
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.post_service_container, frag)
                            .addToBackStack("post_job")
                            .commit();
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void loadCategories() {
        if (JGGAppManager.getInstance(mContext).categories != null) {
            categories = JGGAppManager.getInstance(mContext).categories;
        } else {
            progressDialog = Global.createProgressDialog(mContext);

            final JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, mContext);
            Call<JGGCategoryResponse> call = apiManager.getCategory();
            call.enqueue(new Callback<JGGCategoryResponse>() {
                @Override
                public void onResponse(Call<JGGCategoryResponse> call, Response<JGGCategoryResponse> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        JGGAppManager.getInstance(mContext).categories = response.body().getValue();
                        categories = JGGAppManager.getInstance(mContext).categories;
                        adapter.refreshData(categories);
                        adapter.notifyDataSetChanged();
                    } else {
                        int statusCode  = response.code();
                        Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JGGCategoryResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
                }
            });
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
