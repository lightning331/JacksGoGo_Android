package com.kelvin.jacksgogo.Fragments.Jobs;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.kelvin.jacksgogo.Activities.Search.RequestQuotationActivity;
import com.kelvin.jacksgogo.Adapter.Services.JGGImageGalleryAdapter;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Edit.EditJobMainListCell;
import com.kelvin.jacksgogo.CustomView.Views.EditJobTabbarView;
import com.kelvin.jacksgogo.R;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;

public class EditJobMainFragment extends Fragment implements EditJobFragment.OnFragmentInteractionListener, View.OnClickListener {

    private Context mContext;
    private RecyclerView recyclerView;
    private LinearLayout btnDescribe;

    private JGGImageGalleryAdapter mAdapter;
    private ArrayList<AlbumFile> mAlbumFiles;

    private EditJobFragment editJobFragment;
    private boolean isRequest; // Request Or Edit

    private OnFragmentInteractionListener mListener;

    public EditJobMainFragment() {
        // Required empty public constructor
    }

    public static EditJobMainFragment newInstance(boolean b) {
        EditJobMainFragment fragment = new EditJobMainFragment();
        Bundle args = new Bundle();
        args.putBoolean("isRequest", b);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_job_main, container, false);

        initView(view);
        initRecyclerView(view);

        return view;
    }

    private void initView(View view) {

        // Edit Job Describe
        btnDescribe = view.findViewById(R.id.btn_edit_job_tag);
        btnDescribe.setOnClickListener(this);

        // Edit Job Time
        LinearLayout timeLayout = (LinearLayout)view.findViewById(R.id.job_type_layout);
        EditJobMainListCell timeView = new EditJobMainListCell(mContext);
        timeView.setData("Time", "One-time Job", "21 Dec, 2017 100:00AM - 12:00PM");
        timeView.setOnItemClickListener(new EditJobMainListCell.OnItemClickListener() {
            @Override
            public void onItemClick(View item) {
                onShowEditJobFragment(EditJobTabbarView.EditTabStatus.TIME);
            }
        });
        timeLayout.addView(timeView);

        // Edit Job Address
        LinearLayout addressLayout = (LinearLayout)view.findViewById(R.id.job_type_layout);
        EditJobMainListCell addressView = new EditJobMainListCell(mContext);
        addressView.setData("Address", null, "2 Jurong West Avenue 5 649482");
        addressView.setOnItemClickListener(new EditJobMainListCell.OnItemClickListener() {
            @Override
            public void onItemClick(View item) {
                onShowEditJobFragment(EditJobTabbarView.EditTabStatus.ADDRESS);
            }
        });
        addressLayout.addView(addressView);

        // Edit Job Report
        LinearLayout reportLayout = (LinearLayout)view.findViewById(R.id.job_type_layout);
        EditJobMainListCell reportView = new EditJobMainListCell(mContext);
        reportView.setData("Report", null, "Before & After Photo");
        reportView.setOnItemClickListener(new EditJobMainListCell.OnItemClickListener() {
            @Override
            public void onItemClick(View item) {
                onShowEditJobFragment(EditJobTabbarView.EditTabStatus.REPORT);
            }
        });
        reportLayout.addView(reportView);

    }

    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.edit_job_main_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));

        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int width = display.getWidth();

        int itemSize = (width) / 4;
        mAdapter = new JGGImageGalleryAdapter(mContext, itemSize, true, new com.yanzhenjie.album.impl.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
        recyclerView.setVisibility(View.VISIBLE);
        mAdapter.notifyDataSetChanged(mAlbumFiles);
        recyclerView.setAdapter(mAdapter);
    }

    private void onShowEditJobFragment(EditJobTabbarView.EditTabStatus status) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();

        if (isRequest) {
            editJobFragment = EditJobFragment.newInstance(status, true);
            ft.replace(R.id.request_quotation_container, editJobFragment, editJobFragment.getTag());
        } else {
            editJobFragment = EditJobFragment.newInstance(status, false);
            ft.replace(R.id.app_detail_container, editJobFragment, editJobFragment.getTag());
        }
        editJobFragment.setmListener(EditJobMainFragment.this);
        ft.addToBackStack("edit_job");
        ft.commit();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
        if (getArguments() != null) {
            isRequest = getArguments().getBoolean("isRequest");
            if (isRequest) {
                ((RequestQuotationActivity)context).setBottomViewHidden(false);
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_edit_job_tag) {
            onShowEditJobFragment(EditJobTabbarView.EditTabStatus.DESCRIBE);
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
