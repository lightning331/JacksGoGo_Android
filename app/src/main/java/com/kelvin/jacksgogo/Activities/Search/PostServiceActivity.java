package com.kelvin.jacksgogo.Activities.Search;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kelvin.jacksgogo.CustomView.JGGActionbarView;
import com.kelvin.jacksgogo.Fragments.Search.PostServiceNotVerifiedFragment;
import com.kelvin.jacksgogo.Fragments.Search.PostServiceVerifiedFragment;
import com.kelvin.jacksgogo.R;

public class PostServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private JGGActionbarView actionbarView;

    PostServiceVerifiedFragment verifiedFragment;
    private boolean alreadyVerifiedSkills = getRandomBoolean();

    public static boolean getRandomBoolean() {
        return Math.random() < 0.5;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_service_activity);

        actionbarView = new JGGActionbarView(this);
        mToolbar = (Toolbar) findViewById(R.id.post_service_actionbar);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        actionbarView.setStatus(JGGActionbarView.EditStatus.POST_SERVICE);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (alreadyVerifiedSkills) {
            verifiedFragment = new PostServiceVerifiedFragment();
            ft.replace(R.id.post_service_container, verifiedFragment, verifiedFragment.getTag());
        } else {
            PostServiceNotVerifiedFragment notVerifiedFragment = new PostServiceNotVerifiedFragment();
            ft.replace(R.id.post_service_container, notVerifiedFragment, notVerifiedFragment.getTag());
        }
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

    @Override
    public void onClick(View view) {

    }
}
