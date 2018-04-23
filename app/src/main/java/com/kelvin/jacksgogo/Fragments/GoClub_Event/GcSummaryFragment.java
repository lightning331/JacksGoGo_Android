package com.kelvin.jacksgogo.Fragments.GoClub_Event;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.GoClub_Event.GoClubDetailActivity;
import com.kelvin.jacksgogo.Adapter.GoClub_Event.GcSummaryAdapter;
import com.kelvin.jacksgogo.CustomView.Views.JGGAlertView;
import com.kelvin.jacksgogo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.lujun.androidtagview.TagContainerLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class GcSummaryFragment extends Fragment {

    @BindView(R.id.img_category)
    ImageView imgCategory;
    @BindView(R.id.lbl_category_name)
    TextView lblCategory;

    @BindView(R.id.ll_main_describe)
    LinearLayout ll_main_describe;
    @BindView(R.id.txt_describe_title)                  TextView txtDescribeTitle;
    @BindView(R.id.txt_describe_description)            TextView txtDescribeDesc;
    @BindView(R.id.gc_main_tag_view)
    TagContainerLayout describeTagView;

    @BindView(R.id.ll_limit)                            LinearLayout ll_limit;
    @BindView(R.id.txt_pax)                             TextView txtPax;

    @BindView(R.id.txt_admin)                           TextView txtAdmin;

    @BindView(R.id.summary_recycler_view)
    RecyclerView summaryRecyclerView;

    private Context mContext;
    private GcSummaryAdapter adapter;
    AlertDialog alertDialog;

    public GcSummaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gc_summary, container, false);
        ButterKnife.bind(this, view);

        if (summaryRecyclerView != null) {
            summaryRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.VERTICAL, false));
        }

        // Added Recycler View
        adapter = new GcSummaryAdapter(mContext);
        summaryRecyclerView.setAdapter(adapter);

        return view;
    }

    private void showAlertDialog() {
        String message = "GoClub reference no.: "
                + "12345678"
                + '\n'
                + "Your GoClub is now active.";
        JGGAlertView builder = new JGGAlertView(mContext,
                mContext.getResources().getString(R.string.alert_goclub_created),
                message,
                false,
                "",
                R.color.JGGPurple,
                R.color.JGGPurple10Percent,
                mContext.getResources().getString(R.string.view_goclub_details),
                R.color.JGGPurple);
        alertDialog = builder.create();
        builder.setOnItemClickListener(new JGGAlertView.OnItemClickListener() {
            @Override
            public void onDoneButtonClick(View view) {
                if (view.getId() == R.id.btn_alert_cancel)
                    alertDialog.dismiss();
                else {
                    alertDialog.dismiss();
                    Intent intent = new Intent(mContext, GoClubDetailActivity.class);
                    intent.putExtra("is_post", true);
                    mContext.startActivity(intent);
                }
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    @OnClick(R.id.ll_create_club)
    public void onClickCreateClub() {
        showAlertDialog();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
}
