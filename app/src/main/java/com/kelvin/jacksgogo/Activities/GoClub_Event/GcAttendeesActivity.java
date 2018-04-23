package com.kelvin.jacksgogo.Activities.GoClub_Event;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.kelvin.jacksgogo.Adapter.GoClub_Event.GcAttendeesAdapter;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GcAttendeesActivity extends AppCompatActivity {

    @BindView(R.id.attendees_actionbar)
    Toolbar mToolbar;
    @BindView(R.id.attendess_recycler_view)
    RecyclerView recyclerView;

    private JGGActionbarView actionbarView;
    private GcAttendeesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gc_attendees);
        ButterKnife.bind(this);

        actionbarView = new JGGActionbarView(this);

        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        actionbarView.setStatus(JGGActionbarView.EditStatus.GO_CLUB_ATTENDEES, Global.AppointmentType.UNKNOWN);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        }
        adapter = new GcAttendeesAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    private void actionbarViewItemClick(View view) {
        if (view.getId() == R.id.btn_back) {
            onBackPressed();
        }
    }
}
