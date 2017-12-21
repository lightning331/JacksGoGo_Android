package com.kelvin.jacksgogo.Activities.Search;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.Fragments.Jobs.PostJobFragment;
import com.kelvin.jacksgogo.Fragments.Search.PostServiceMainFragment;
import com.kelvin.jacksgogo.Fragments.Search.PostServiceNotVerifiedFragment;
import com.kelvin.jacksgogo.Fragments.Search.PostServiceVerifiedFragment;
import com.kelvin.jacksgogo.Models.Jobs_Services_Events.JGGAppBaseModel;
import com.kelvin.jacksgogo.R;

public class PostServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private JGGActionbarView actionbarView;
    private boolean alreadyVerifiedSkills = getRandomBoolean();
    private String status;

    private String appType;

    public static boolean getRandomBoolean() {
        return Math.random() < 0.5;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_service);

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            status = extra.getString("EDIT_STATUS");
            appType = extra.getString("APPOINTMENT_TYPE");
        } else {
            status = "None";
        }

        actionbarView = new JGGActionbarView(this);
        mToolbar = (Toolbar) findViewById(R.id.post_service_actionbar);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        initFragment();
    }

    private void initFragment() {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        PostServiceMainFragment frag;
        switch (status) {
            case "Edit":
                frag = new PostServiceMainFragment();
                ft.replace(R.id.post_service_container, frag, frag.getTag());
                frag.setEditStatus(PostServiceMainFragment.PostEditStatus.EDIT);
                ft.commit();
                break;
            case "Duplicate":
                frag = new PostServiceMainFragment();
                ft.replace(R.id.post_service_container, frag, frag.getTag());
                frag.setEditStatus(PostServiceMainFragment.PostEditStatus.DUPLICATE);
                ft.commit();
                break;
            case "None":
                switch (appType) {
                    case "SERVICES":
                        actionbarView.setStatus(JGGActionbarView.EditStatus.POST, JGGAppBaseModel.AppointmentType.SERVICES);
                        if (alreadyVerifiedSkills) {
                            PostServiceVerifiedFragment fra = new PostServiceVerifiedFragment();
                            ft.replace(R.id.post_service_container, fra, fra.getTag());
                        } else {
                            PostServiceNotVerifiedFragment fra = new PostServiceNotVerifiedFragment();
                            ft.replace(R.id.post_service_container, fra, fra.getTag());
                        }
                        break;
                    case "JOBS":
                        actionbarView.setStatus(JGGActionbarView.EditStatus.POST, JGGAppBaseModel.AppointmentType.JOBS);
                        PostJobFragment fra = new PostJobFragment();
                        ft.replace(R.id.post_service_container, fra, fra.getTag());
                        break;
                    case "GOCLUB":
                        actionbarView.setStatus(JGGActionbarView.EditStatus.POST, JGGAppBaseModel.AppointmentType.GOCLUB);
                        break;
                    default:
                        break;
                }
                ft.commit();
                break;
            default:
                break;
        }
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
