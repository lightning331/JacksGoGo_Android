package com.kelvin.jacksgogo.Activities.Profile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.Fragments.Profile.SetUpPaymentFragment;
import com.kelvin.jacksgogo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentMethodActivity extends AppCompatActivity {

    @BindView(R.id.payment_actionbar)
    Toolbar mToolbar;

    private JGGActionbarView actionbarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        ButterKnife.bind(this);

        // Top Navigationbar View
        actionbarView = new JGGActionbarView(this);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);
        actionbarView.setCreditActionBar(getString(R.string.title_payment));
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.payment_method_container, new SetUpPaymentFragment())
                .commit();
    }

    private void actionbarViewItemClick(View view) {
        if (view.getId() == R.id.btn_back) {
            onBackPressed();
        }
    }
}
