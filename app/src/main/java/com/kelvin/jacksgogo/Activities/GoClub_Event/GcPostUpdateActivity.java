package com.kelvin.jacksgogo.Activities.GoClub_Event;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GcPostUpdateActivity extends AppCompatActivity implements TextWatcher {

    @BindView(R.id.gc_post_actionbar)       Toolbar mToolbar;
    @BindView(R.id.edit_update)             EditText editUpdate;
    @BindView(R.id.btn_update)              Button btnUpdate;

    private Context mContext;
    private JGGActionbarView actionbarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gc_post_update);

        ButterKnife.bind(this);
        mContext = this;

        actionbarView = new JGGActionbarView(this);

        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        actionbarView.setStatus(JGGActionbarView.EditStatus.GO_CLUB_POST_UPDATE, Global.AppointmentType.UNKNOWN);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        editUpdate.addTextChangedListener(this);
    }

    private void actionbarViewItemClick(View view) {
        if (view.getId() == R.id.btn_back) {
            onBackPressed();
        }
    }

    @OnClick(R.id.btn_update)
    public void onClickNext() {
        finish();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (editUpdate.length() > 0) {
            btnUpdate.setBackgroundResource(R.drawable.purple_background);
            btnUpdate.setTextColor(ContextCompat.getColor(mContext, R.color.JGGWhite));
            btnUpdate.setClickable(true);
        } else {
            btnUpdate.setBackgroundResource(R.drawable.grey_background);
            btnUpdate.setTextColor(ContextCompat.getColor(mContext, R.color.JGGGrey3));
            btnUpdate.setClickable(false);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
