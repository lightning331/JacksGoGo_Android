package com.kelvin.jacksgogo.Activities.Search;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.kelvin.jacksgogo.CustomView.JGGActionbarView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail.EditJobTabbarView;
import com.kelvin.jacksgogo.Fragments.Appointments.EditJobFragment;
import com.kelvin.jacksgogo.R;

public class RequestQuotationActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    public JGGActionbarView actionbarView;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_request_quotation_activity);

        // Top Navigationbar View
        actionbarView = new JGGActionbarView(this);
        mToolbar = (Toolbar) findViewById(R.id.request_quotation_actionbar);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        actionbarView.setStatus(JGGActionbarView.EditStatus.REQUEST_QUOTATION);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        // Fragment
        EditJobFragment editJobFragment = EditJobFragment.newInstance(EditJobTabbarView.EditTabStatus.DESCRIBE, true);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.request_quotation_container, editJobFragment, editJobFragment.getTag());
        ft.commit();
    }

    private void actionbarViewItemClick(View view) {
        if (view.getId() == R.id.btn_back) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = (this).getLayoutInflater();

            View alertView = inflater.inflate(R.layout.jgg_alert_view, null);
            builder.setView(alertView);
            alertDialog = builder.create();
            TextView cancelButton = (TextView) alertView.findViewById(R.id.btn_alert_cancel);
            TextView okButton = (TextView) alertView.findViewById(R.id.btn_alert_ok);
            TextView title = (TextView) alertView.findViewById(R.id.lbl_alert_titile);
            TextView desc = (TextView) alertView.findViewById(R.id.lbl_alert_description);

            title.setText(R.string.alert_quit_quotation_title);
            desc.setText(R.string.alert_quit_quotation_desc);
            okButton.setText(R.string.alert_quit_button);
            okButton.setOnClickListener(this);
            cancelButton.setOnClickListener(this);
            alertDialog.show();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_alert_cancel) {
            alertDialog.dismiss();
        } else if (view.getId() == R.id.btn_alert_ok) {
            onBackPressed();
        }
    }
}
