package com.kelvin.jacksgogo.Activities.Search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.CustomView.Views.PostServiceTabView;
import com.kelvin.jacksgogo.Fragments.Search.PostQuotationMainTabFragment;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGQuotationModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostQuotationActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.request_quotation_actionbar) Toolbar mToolbar;
    @BindView(R.id.img_category) ImageView imgCategory;
    @BindView(R.id.lbl_category_name) TextView lblCategory;

    private JGGActionbarView actionbarView;
    private android.app.AlertDialog alertDialog;

    private JGGAppointmentModel selectedAppointment;
    private JGGQuotationModel selectedQuotation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_quotation);
        ButterKnife.bind(this);

        // Top Navigationbar View
        actionbarView = new JGGActionbarView(this);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        actionbarView.setStatus(JGGActionbarView.EditStatus.REQUEST_QUOTATION, AppointmentType.UNKNOWN);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        selectedAppointment = JGGAppManager.getInstance().getSelectedAppointment();

        // Category
        Picasso.with(this)
                .load(selectedAppointment.getCategory().getImage())
                .placeholder(null)
                .into(imgCategory);
        lblCategory.setText(selectedAppointment.getCategory().getName());

        // Create new Quotation Model
        selectedQuotation = new JGGQuotationModel();
        selectedQuotation.setProviderProfileID(selectedAppointment.getUserProfileID());
        selectedQuotation.setCategoryID(selectedAppointment.getCategoryID());
        selectedQuotation.setUserProfileID(JGGAppManager.getInstance().getCurrentUser().getID());
        selectedQuotation.setRegionID(selectedAppointment.getRegionID());
        selectedQuotation.setCurrencyCode(selectedAppointment.getCurrencyCode());

        JGGAppManager.getInstance().setSelectedQuotation(selectedQuotation);

        // Main Tab Fragment
        PostQuotationMainTabFragment frag = PostQuotationMainTabFragment.newInstance(PostServiceTabView.PostServiceTabName.DESCRIBE, true);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.request_quotation_container, frag, frag.getTag())
                .commit();
    }

    private void actionbarViewItemClick(View view) {
        if (view.getId() == R.id.btn_back) {
            FragmentManager manager = getSupportFragmentManager();
            if (manager.getBackStackEntryCount() == 0) {
                showAlertDialog();
            } else {
                Fragment fragment = manager.findFragmentById(R.id.request_quotation_container);
                if (fragment instanceof PostQuotationMainTabFragment) {
                    PostQuotationMainTabFragment frag = (PostQuotationMainTabFragment) fragment;
                    if (frag.getTabIndex() > 0) {
                        frag.setTabIndex(frag.getTabIndex() - 1);
                    } else {
                        manager.popBackStack();
                    }
                }
                else {
                    manager.popBackStack();
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_alert_cancel) {
            alertDialog.dismiss();
        } else if (view.getId() == R.id.btn_alert_ok) {
            alertDialog.dismiss();
            onBackPressed();
        }
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

        title.setText(R.string.alert_quit_quotation_title);
        desc.setText(R.string.alert_quit_quotation_desc);
        okButton.setText(R.string.alert_quit_button);
        okButton.setBackgroundColor(ContextCompat.getColor(this, R.color.JGGRed));
        cancelButton.setOnClickListener(this);

        okButton.setOnClickListener(this);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }
}
