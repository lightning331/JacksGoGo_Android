package com.kelvin.jacksgogo.Activities.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.LinearLayout;

import com.kelvin.jacksgogo.Adapter.Profile.CreditAdapter;
import com.kelvin.jacksgogo.Adapter.Profile.ProfileHomeAdapter;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Profile.CreditCell;
import com.kelvin.jacksgogo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreditActivity extends AppCompatActivity {
    @BindView(R.id.btn_back)
    LinearLayout btn_back;
    @BindView(R.id.credit_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.btn_payment)
    Button btnPayment;

    Context mContext;
    CreditAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);
        ButterKnife.bind(this);

        mContext = this;

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        adapter = new CreditAdapter(mContext);
        adapter.setOnItemClickListener(new ProfileHomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder holder, int position) {
                if (holder instanceof CreditCell) {
                    CreditCell creditCell = (CreditCell) holder;
                }
            }
        });

        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.btn_back)
    public void onBack() {
        onBackPressed();
    }

    @OnClick(R.id.btn_payment)
    public void goPayment() {
        Intent intent = new Intent(this, SetupPaymentActivity.class);
        // TODO: Go back 2 activities at once
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
