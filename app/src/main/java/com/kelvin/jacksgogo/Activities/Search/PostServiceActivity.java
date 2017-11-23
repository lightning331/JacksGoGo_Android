package com.kelvin.jacksgogo.Activities.Search;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kelvin.jacksgogo.CustomView.JGGActionbarView;
import com.kelvin.jacksgogo.R;

public class PostServiceActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    JGGActionbarView actionbarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_post_service_activity);

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
    }

    private void actionbarViewItemClick(View view) {
        if (view.getId() == R.id.btn_back) {
            onBackPressed();
        }
    }
}
