package com.kelvin.jacksgogo.Activities.Search;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewBehavior;
import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewHelper;
import com.kelvin.jacksgogo.Activities.MainActivity;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.CustomView.Views.PostServiceTabbarView;
import com.kelvin.jacksgogo.Fragments.Search.PostQuotationMainTabFragment;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppBaseModel;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGQuotationModel;

import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.currentUser;
import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.selectedAppointment;
import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.selectedQuotation;

public class PostQuotationActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private BottomNavigationView mbtmView;;
    private JGGActionbarView actionbarView;
    private android.app.AlertDialog alertDialog;

    private boolean isViewJob = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_quotation);

        // Hide Bottom NavigationView and ToolBar
        mbtmView = (BottomNavigationView) findViewById(R.id.request_quotation_navigation);
        BottomNavigationViewHelper.disableShiftMode(mbtmView);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mbtmView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());
        mbtmView.setOnClickListener(this);

        // Top Navigationbar View
        actionbarView = new JGGActionbarView(this);
        mToolbar = (Toolbar) findViewById(R.id.request_quotation_actionbar);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        actionbarView.setStatus(JGGActionbarView.EditStatus.REQUEST_QUOTATION, JGGAppBaseModel.AppointmentType.UNKNOWN);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                actionbarViewItemClick(view);
            }
        });

        // Create a new Quotation Model for Request
        selectedQuotation = new JGGQuotationModel();
        selectedQuotation.setProviderProfileID(selectedAppointment.getUserProfileID());
        selectedQuotation.setCategoryID(selectedAppointment.getCategoryID());
        selectedQuotation.setUserProfileID(currentUser.getID());
        selectedQuotation.setRegionID(selectedAppointment.getRegionID());
        selectedQuotation.setCurrencyCode(selectedAppointment.getCurrencyCode());

        // Main Tab Fragment
        PostQuotationMainTabFragment frag = PostQuotationMainTabFragment.newInstance(PostServiceTabbarView.PostServiceTabName.DESCRIBE, true);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.request_quotation_container, frag, frag.getTag())
                .commit();
    }

    private void actionbarViewItemClick(View view) {
        if (view.getId() == R.id.btn_back) {
            showAlertDialog(view);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_alert_cancel) {
            alertDialog.dismiss();
        } else if (view.getId() == R.id.btn_alert_ok) {
            if (!isViewJob) onBackPressed();
            else showMainActivity();
        } else if (view.getId() == R.id.request_quotation_navigation) {
            showAlertDialog(view);
        }
    }

    public void setBottomViewHidden(boolean isHidden) {
        if (isHidden) {
            this.mbtmView.setVisibility(View.GONE);
        } else {
            this.mbtmView.setVisibility(View.VISIBLE);
        }
    }

    private void showMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }

    private void showAlertDialog(View view) {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        View alertView = inflater.inflate(R.layout.jgg_alert_view, null);
        builder.setView(alertView);
        alertDialog = builder.create();
        TextView cancelButton = (TextView) alertView.findViewById(R.id.btn_alert_cancel);
        TextView okButton = (TextView) alertView.findViewById(R.id.btn_alert_ok);
        TextView title = (TextView) alertView.findViewById(R.id.lbl_alert_titile);
        TextView desc = (TextView) alertView.findViewById(R.id.lbl_alert_description);

        if (view.getId() == R.id.btn_back) {
            title.setText(R.string.alert_quit_quotation_title);
            desc.setText(R.string.alert_quit_quotation_desc);
            okButton.setText(R.string.alert_quit_button);
            okButton.setBackgroundColor(ContextCompat.getColor(this, R.color.JGGRed));
            cancelButton.setOnClickListener(this);
            isViewJob = false;
        } else if (view.getId() == R.id.request_quotation_navigation) {
            title.setText(R.string.alert_job_posted_title);
            desc.setText(R.string.alert_job_posted_desc);
            okButton.setText(R.string.alert_view_job_button);
            okButton.setBackgroundColor(ContextCompat.getColor(this, R.color.JGGGreen));
            cancelButton.setVisibility(View.GONE);
            isViewJob = true;
        }

        okButton.setOnClickListener(this);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }
}
