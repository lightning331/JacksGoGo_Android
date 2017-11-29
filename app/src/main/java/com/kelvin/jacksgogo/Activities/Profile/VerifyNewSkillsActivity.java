package com.kelvin.jacksgogo.Activities.Profile;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.Search.ServiceListingActivity;
import com.kelvin.jacksgogo.CustomView.JGGActionbarView;
import com.kelvin.jacksgogo.R;

public class VerifyNewSkillsActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private JGGActionbarView actionbarView;
    private android.app.AlertDialog alertDialog;
    private TextView btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_verify_new_skills_activity);

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
                    onBackPressed();
                }
            }
        });
        btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);
    }

    private void showAlertDialog() {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        View alertView = inflater.inflate(R.layout.jgg_alert_view, null);
        builder.setView(alertView);
        alertDialog = builder.create();
        TextView cancelButton = (TextView) alertView.findViewById(R.id.btn_alert_cancel);
        TextView okButton = (TextView) alertView.findViewById(R.id.btn_alert_ok);
        TextView title = (TextView) alertView.findViewById(R.id.lbl_alert_titile);
        TextView desc = (TextView) alertView.findViewById(R.id.lbl_alert_description);

        title.setText(R.string.alert_verify_skill_title);
        desc.setText(R.string.alert_verify_skill_desc);
        okButton.setText(R.string.alert_verify_skill_button);
        okButton.setBackgroundColor(ContextCompat.getColor(this, R.color.JGGGreen));
        cancelButton.setVisibility(View.GONE);

        okButton.setOnClickListener(this);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_alert_ok) {
            alertDialog.dismiss();
            startActivity(new Intent(this, ServiceListingActivity.class));
        } else if (view.getId() == R.id.btn_submit) {
            showAlertDialog();
        }
    }
}
