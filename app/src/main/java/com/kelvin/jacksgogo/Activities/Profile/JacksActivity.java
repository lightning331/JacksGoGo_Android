package com.kelvin.jacksgogo.Activities.Profile;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kelvin.jacksgogo.Adapter.Profile.JacksAdapter;
import com.kelvin.jacksgogo.Adapter.Profile.ProfileHomeAdapter;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Profile.CreditCell;
import com.kelvin.jacksgogo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JacksActivity extends AppCompatActivity {
    @BindView(R.id.btn_back)
    LinearLayout btn_back;
    @BindView(R.id.jacks_recycler_view)
    RecyclerView recyclerView;

    Context mContext;
    JacksAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jacks);
        ButterKnife.bind(this);

        mContext = this;

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        adapter = new JacksAdapter(mContext);
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
}
