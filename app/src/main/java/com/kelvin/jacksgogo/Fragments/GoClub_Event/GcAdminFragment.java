package com.kelvin.jacksgogo.Fragments.GoClub_Event;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.Activities.GoClub_Event.GcAddAdminActivity;
import com.kelvin.jacksgogo.Adapter.GoClub_Event.GcAddedAdminAdapter;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class GcAdminFragment extends Fragment {

    @BindView(R.id.txt_admin_desc)
    TextView txt_admin_desc;
    @BindView(R.id.btn_sole_admin)
    Button btnSoleAdmin;
    @BindView(R.id.btn_others)
    Button btnOthers;
    @BindView(R.id.ll_admin)
    LinearLayout ll_admin;
    @BindView(R.id.admin_recycler_view)
    RecyclerView adminRecyclerView;

    Context mContext;
    private GcAddedAdminAdapter adapter;
    private boolean isOthers = false;

    public GcAdminFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gc_admin, container, false);
        ButterKnife.bind(this, view);

        if (adminRecyclerView != null) {
            adminRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.VERTICAL, false));
        }

        // Added Recycler View
        adapter = new GcAddedAdminAdapter(mContext);
        adapter.setOnItemClickListener(new GcAddedAdminAdapter.OnItemClickListener() {
            @Override
            public void onDeleteItemClick(int position) {
                Toast.makeText(mContext, "Delete button clicked "+position, Toast.LENGTH_SHORT).show();
            }
        });
        adminRecyclerView.setAdapter(adapter);

        ll_admin.setVisibility(View.GONE);

        return view;
    }

    private void onYellowButtonColor(Button button) {
        button.setBackgroundResource(R.drawable.yellow_background);
        button.setTextColor(ContextCompat.getColor(mContext, R.color.JGGBlack));
    }

    private void onPurpleButtonColor(Button button) {
        button.setBackgroundResource(R.drawable.purple_border_background);
        button.setTextColor(ContextCompat.getColor(mContext, R.color.JGGPurple));
    }

    @OnClick(R.id.btn_sole_admin)
    public void onClickSoleAdmin() {
        listener.onNextButtonClick();
    }

    @OnClick(R.id.btn_summary)
    public void onClickSummary() {
        listener.onNextButtonClick();
    }

    @OnClick(R.id.btn_others)
    public void onClickOthers() {
        isOthers  = !isOthers;
        Intent intent = new Intent(mContext, GcAddAdminActivity.class);
        startActivityForResult(intent, Global.REQUEST_CODE);
//        if (isOthers) {
//            btnSoleAdmin.setVisibility(View.GONE);
//            this.onYellowButtonColor(btnOthers);
//            ll_admin.setVisibility(View.VISIBLE);
//        } else {
//            btnSoleAdmin.setVisibility(View.VISIBLE);
//            this.onPurpleButtonColor(btnOthers);
//            ll_admin.setVisibility(View.GONE);
//        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Global.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                btnSoleAdmin.setVisibility(View.GONE);
                this.onYellowButtonColor(btnOthers);
                ll_admin.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }


    // TODO : Next Click Listener
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onNextButtonClick();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
