package com.kelvin.jacksgogo.Activities.Profile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.kelvin.jacksgogo.R;

public class SetupPaymentActivity extends AppCompatActivity {
    @BindView(R.id.payment_actionbar)
    Toolbar mToolbar;
    @BindView(R.id.editTextDigit)
    EditText editTextDigit;
    @BindView(R.id.btn_next)
    Button btnNext;

    private JGGActionbarView actionbarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_payment);
        ButterKnife.bind(this);

        // Top Navigationbar View
        actionbarView = new JGGActionbarView(this);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);
        actionbarView.setCreditActionBar(getString(R.string.setup_payment));
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

    @OnClick(R.id.btn_next)
    public void goNext() {
        Intent intent = new Intent(this, WithdrawCreditActivity.class);
        startActivity(intent);
    }
}
