package com.kelvin.jacksgogo.Fragments.Appointments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kelvin.jacksgogo.Activities.Search.RequestQuotationActivity;
import com.kelvin.jacksgogo.Adapter.Job.EditJobMainAdapter;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Edit.EditJobTabbarView;
import com.kelvin.jacksgogo.R;

public class EditJobMainFragment extends Fragment implements EditJobFragment.OnFragmentInteractionListener {

    RecyclerView recyclerView;

    EditJobFragment editJobFragment;
    boolean isRequest; // Request Or Edit

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
        recyclerView = (RecyclerView) view.findViewById(R.id.edit_job_recycler_view);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        }
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        EditJobMainAdapter mAdapter = new EditJobMainAdapter(this);
        mAdapter.setItemClickLietener(new EditJobMainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(EditJobTabbarView.EditTabStatus status) {

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
        });

        recyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
