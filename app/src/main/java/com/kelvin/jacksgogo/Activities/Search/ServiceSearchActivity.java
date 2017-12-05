package com.kelvin.jacksgogo.Activities.Search;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kelvin.jacksgogo.CustomView.JGGActionbarView;
import com.kelvin.jacksgogo.Fragments.Search.ServiceSearchMainFragment;
import com.kelvin.jacksgogo.R;

public class ServiceSearchActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    JGGActionbarView actionbarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_search);

        actionbarView = new JGGActionbarView(this);
        mToolbar = (Toolbar) findViewById(R.id.service_search_actionbar);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        actionbarView.setStatus(JGGActionbarView.EditStatus.SERVICE_SEARCH);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        ServiceSearchMainFragment frag = new ServiceSearchMainFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.service_search_container, frag, frag.getTag());
        ft.commit();
    }

    private void actionbarViewItemClick(View view) {
        if (view.getId() == R.id.btn_back) {
            FragmentManager manager = getSupportFragmentManager();
            if (manager.getBackStackEntryCount() == 0) {
                super.onBackPressed();
            } else {
                manager.popBackStack();
            }
        }
    }
}
