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
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.Adapter.CategoryAdapter;
import com.kelvin.jacksgogo.CustomView.Views.PostEventTabView;
import com.kelvin.jacksgogo.CustomView.Views.PostGoClubTabView;
import com.kelvin.jacksgogo.CustomView.Views.PostJobTabView;
import com.kelvin.jacksgogo.Fragments.GoClub_Event.CreateEventMainFragment;
import com.kelvin.jacksgogo.Fragments.GoClub_Event.CreateGoClubMainFragment;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.GoClub_Event.JGGGoClubModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGCategoryResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.EVENTS;
import static com.kelvin.jacksgogo.Utils.Global.GOCLUB;
import static com.kelvin.jacksgogo.Utils.Global.JOBS;
import static com.kelvin.jacksgogo.Utils.Global.createProgressDialog;

public class PostJobCategoryFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    private RecyclerView recyclerView;
    private TextView lblTitle;

    private CategoryAdapter adapter;
    private ProgressDialog progressDialog;
    private View view;

    private ArrayList<JGGCategoryModel> categories;
    private ArrayList<JGGCategoryModel> mCategories = new ArrayList<>();
    private AppointmentType appType;
    private String title = "Choose a category for your ";

    public PostJobCategoryFragment() {
        // Required empty public constructor
    }

    public static PostJobCategoryFragment newInstance(String type) {
        PostJobCategoryFragment fragment = new PostJobCategoryFragment();
        Bundle args = new Bundle();
        args.putString(APPOINTMENT_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String type = getArguments().getString(APPOINTMENT_TYPE);
            if (type == null)
                appType = AppointmentType.JOBS;
            else {
                if (type.equals(JOBS)) {
                    appType = AppointmentType.JOBS;
                    title = title + "Job:";
                } else if (type.equals(GOCLUB)) {
                    appType = AppointmentType.GOCLUB;
                    title = title + "GoClub:";
                } else if (type.equals(EVENTS)) {
                    appType = AppointmentType.EVENTS;
                    title = title + "Event:";
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_post_job_category, container, false);
        lblTitle = view.findViewById(R.id.lbl_title);
        lblTitle.setText(title);

        categories = JGGAppManager.getInstance().getCategories();

        if (categories == null)
            loadCategories();
        else {
            if (categories.size() == 0)
                loadCategories();
            else
                mCategories = categories;
        }
        initRecyclerView(view);
        return view;
    }

    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.post_job_category_recycler_view);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        }
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        adapter = new CategoryAdapter(mContext, mCategories, appType);
        adapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                if (position >= 0) {
                    JGGCategoryModel categoryModel = mCategories.get(position);
                    JGGAppManager.getInstance().setSelectedCategory(categoryModel);

                    if (appType == AppointmentType.JOBS) {
                        PostJobMainTabFragment frag = PostJobMainTabFragment.newInstance(PostJobTabView.PostJobTabName.DESCRIBE, Global.PostStatus.POST);

                        JGGAppointmentModel appointmentModel = JGGAppManager.getInstance().getSelectedAppointment();
                        appointmentModel.setCategory(categoryModel);
                        appointmentModel.setCategoryID(categoryModel.getID());
                        JGGAppManager.getInstance().setSelectedAppointment(appointmentModel);

                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.post_service_container, frag)
                                .addToBackStack("post_job")
                                .commit();
                    } else {
                        if (appType == AppointmentType.GOCLUB) {
                            JGGGoClubModel creatingClub = JGGAppManager.getInstance().getSelectedClub();
                            creatingClub.setCategoryID(categoryModel.getID());
                            creatingClub.setCategory(categoryModel);
                            JGGAppManager.getInstance().setSelectedClub(creatingClub);
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.post_go_club_container, CreateGoClubMainFragment.newInstance(PostGoClubTabView.GoClubTabName.DESCRIBE, Global.PostStatus.POST))
                                    .addToBackStack("post_go_club")
                                    .commit();
                        } else if (appType == AppointmentType.EVENTS) {
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.post_go_club_container, CreateEventMainFragment.newInstance(PostEventTabView.EventTabName.DESCRIBE, Global.PostStatus.POST))
                                    .addToBackStack("post_event")
                                    .commit();
                        }
                    }
                } else {
                    // TODO - Quick Job
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void loadCategories() {
        progressDialog = createProgressDialog(mContext);

        final JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        Call<JGGCategoryResponse> call = apiManager.getCategory();
        call.enqueue(new Callback<JGGCategoryResponse>() {
            @Override
            public void onResponse(Call<JGGCategoryResponse> call, Response<JGGCategoryResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mCategories = response.body().getValue();
                        categories = mCategories;
                        adapter.refreshData(mCategories);
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
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
