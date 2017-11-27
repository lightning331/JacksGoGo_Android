package com.kelvin.jacksgogo.Activities.Search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.MainActivity;
import com.kelvin.jacksgogo.CustomView.JGGActionbarView;
import com.kelvin.jacksgogo.R;

public class ServiceBuyActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    JGGActionbarView actionbarView;
    AlertDialog alertDialog;

    static boolean alreadySetUp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_buy_activity);

        actionbarView = new JGGActionbarView(this);
        mToolbar = (Toolbar) findViewById(R.id.service_buy_actionbar);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        actionbarView.setStatus(JGGActionbarView.EditStatus.SERVICE_BUY);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                if (view.getId() == R.id.btn_back) {
                    onBackPressed();
                }
            }
        });
    }

    public void onPaymentSetup(View view) {
        if (alreadySetUp) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = (this).getLayoutInflater();
            //JGGAlertView alertView = new JGGAlertView(this);
            View alertView = inflater.inflate(R.layout.jgg_alert_view, null);
            builder.setView(alertView);
            alertDialog = builder.create();
            TextView cancelButton = (TextView) alertView.findViewById(R.id.btn_alert_cancel);
            TextView okButton = (TextView) alertView.findViewById(R.id.btn_alert_ok);
            TextView title = (TextView) alertView.findViewById(R.id.lbl_alert_titile);
            TextView desc = (TextView) alertView.findViewById(R.id.lbl_alert_description);

            title.setText(R.string.alert_payment_setup_success);
            okButton.setText(R.string.alert_ok);
            okButton.setBackgroundColor(ContextCompat.getColor(this, R.color.JGGGreen));
            okButton.setOnClickListener(this);
            cancelButton.setVisibility(View.GONE);
            desc.setVisibility(View.GONE);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        } else {
            if (view.getId() == R.id.btn_credit_card) {
                startActivity(new Intent(this, SetUpCreditCardActivity.class));
            } else if (view.getId() == R.id.btn_jacks_credit_card) {
                startActivity(new Intent(this, JacksWalletActivity.class));
            }
        }
    }

    public static void setAlreadySetUp(boolean setUp) {
        alreadySetUp = setUp;
    }

        @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_alert_ok) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
