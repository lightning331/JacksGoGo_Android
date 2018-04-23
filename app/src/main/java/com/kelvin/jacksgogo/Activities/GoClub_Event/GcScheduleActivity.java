package com.kelvin.jacksgogo.Activities.GoClub_Event;

import android.content.Context;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kelvin.jacksgogo.Adapter.GoClub_Event.GcScheduleAdapter;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.CustomView.Views.JGGAlertView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GcScheduleActivity extends AppCompatActivity {

    @BindView(R.id.schedule_actionbar)           Toolbar mToolbar;
    @BindView(R.id.schedule_recycler_view)
    RecyclerView recyclerView;

    private Context mContext;
    private JGGActionbarView actionbarView;
    private AlertDialog alertDialog;
    private GcScheduleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gc_schedule);

        ButterKnife.bind(this);
        mContext = this;

        actionbarView = new JGGActionbarView(this);

        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        actionbarView.setStatus(JGGActionbarView.EditStatus.GO_CLUB_SCHEDULE, Global.AppointmentType.UNKNOWN);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        }
        adapter = new GcScheduleAdapter(this);
        adapter.setOnItemClickListener(new GcScheduleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(GcScheduleActivity.this, "Cancel button clicked "+position, Toast.LENGTH_SHORT).show();
                onShowCancelEventDialog();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void onCancelEvent() {
        alertDialog.dismiss();
    }

    private void onShowCancelEventDialog() {
        final JGGAlertView builder = new JGGAlertView(this,
                "Delete This Day's Event?"
                        + '\n'
                        + '\n'
                        + "30 Dec, 2017",
                "Let attendees know why you are deleting the event for the day.",
                true,
                getResources().getString(R.string.alert_cancel),
                R.color.JGGPurple,
                R.color.JGGPurple10Percent,
                getResources().getString(R.string.alert_delete),
                R.color.JGGRed);
        alertDialog = builder.create();
        builder.setOnItemClickListener(new JGGAlertView.OnItemClickListener() {
            @Override
            public void onDoneButtonClick(View view) {
                if (view.getId() == R.id.btn_alert_cancel)
                    alertDialog.dismiss();
                else {
                    onCancelEvent();
                }
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    private void actionbarViewItemClick(View view) {
        if (view.getId() == R.id.btn_back) {
            onBackPressed();
        }
        if (view.getId() == R.id.btn_more) {
            onBackPressed();
        }
    }

    private void onClickAdd() {

    }
}
