package com.kelvin.jacksgogo.Activities.Profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.CustomView.Views.JGGAlertView;
import com.kelvin.jacksgogo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WithdrawCreditActivity extends AppCompatActivity {
    @BindView(R.id.payment_actionbar)
    Toolbar mToolbar;

    private JGGActionbarView actionbarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_credit);
        ButterKnife.bind(this);

        // Top Navigationbar View
        actionbarView = new JGGActionbarView(this);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);
        actionbarView.setCreditActionBar(getString(R.string.withdraw_credit));
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

    @OnClick(R.id.btn_payment)
    public void setupPayment() {
        JGGAlertView alertView = new JGGAlertView(this,
                getString(R.string.payment_alert_title),
                getString(R.string.pay_desc),
                false,
                "",
                R.color.JGGWhite,
                R.color.JGGOrange,
                getString(R.string.back_credit),
                R.color.JGGOrange);
        alertView.show();
        alertView.setOnItemClickListener(new JGGAlertView.OnItemClickListener() {
            @Override
            public void onDoneButtonClick(View view) {
                onBackPressed();
            }
        });
    }
}
