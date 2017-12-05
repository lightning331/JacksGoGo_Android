package com.kelvin.jacksgogo.Activities.Profile;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kelvin.jacksgogo.CustomView.JGGActionbarView;
import com.kelvin.jacksgogo.Fragments.Profile.NotVerifiedSkillFragment;
import com.kelvin.jacksgogo.Fragments.Profile.VerifiedSkillFragment;
import com.kelvin.jacksgogo.R;

public class VerifyNewSkillsActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private JGGActionbarView actionbarView;
    private boolean alreadyVerifiedSkills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_verify_new_skills);

        Bundle bundle = getIntent().getExtras();
        alreadyVerifiedSkills = bundle.getBoolean("already_verified_skills");

        // Top Navigationbar View
        actionbarView = new JGGActionbarView(this);
        mToolbar = (Toolbar) findViewById(R.id.verify_skill_actionbar);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        actionbarView.setStatus(JGGActionbarView.EditStatus.VERIFY_SKILL);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                if (view.getId() == R.id.btn_back) {
                    FragmentManager manager = getSupportFragmentManager();
                    if (manager.getBackStackEntryCount() == 0) {
                        onBackPressed();
                    } else {
                        manager.popBackStack();
                    }
                }
            }
        });

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (alreadyVerifiedSkills) {
            VerifiedSkillFragment verifiedFragment = new VerifiedSkillFragment();
            ft.replace(R.id.verify_skill_container, verifiedFragment, verifiedFragment.getTag());
        } else {
            NotVerifiedSkillFragment notVerifiedFragment = new NotVerifiedSkillFragment();
            ft.replace(R.id.verify_skill_container, notVerifiedFragment, notVerifiedFragment.getTag());
        }
        ft.commit();
    }

    @Override
    public void onClick(View view) {

    }
}
