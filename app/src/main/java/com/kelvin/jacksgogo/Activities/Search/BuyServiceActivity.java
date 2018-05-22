package com.kelvin.jacksgogo.Activities.Search;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.Activities.MainActivity;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.CustomView.Views.JGGAlertView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserProfileModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGPostAppResponse;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.Global.createProgressDialog;

public class BuyServiceActivity extends AppCompatActivity {

    @BindView(R.id.service_buy_actionbar) Toolbar mToolbar;
    @BindView(R.id.img_category) ImageView img_category;
    @BindView(R.id.lbl_title) TextView lbl_title;
    @BindView(R.id.lbl_budget) TextView lbl_budget;
    @BindView(R.id.btn_jacks_credit_card) TextView btn_jacks_credit_card;

    private JGGActionbarView actionbarView;
    private android.app.AlertDialog alertDialog;
    private ProgressDialog progressDialog;

    private JGGAppointmentModel selectedAppointment;
    private JGGUserProfileModel currentUser;
    static boolean alreadySetUp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_buy);
        ButterKnife.bind(this);

        actionbarView = new JGGActionbarView(this);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        selectedAppointment = JGGAppManager.getInstance().getSelectedAppointment();
        currentUser = JGGAppManager.getInstance().getCurrentUser();

        actionbarView.setStatus(JGGActionbarView.EditStatus.SERVICE_BUY, AppointmentType.UNKNOWN);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                if (view.getId() == R.id.btn_back) {
                    onBackPressed();
                }
            }
        });
        setData();
    }

    private void setData() {
        // Category
        Picasso.with(this)
                .load(selectedAppointment.getCategory().getImage())
                .placeholder(null)
                .into(img_category);
        lbl_title.setText(selectedAppointment.getTitle());
        lbl_budget.setText("$ " + String.valueOf(selectedAppointment.getBudget()));
        String balance = "Pay by Jacks Credit - balance $ " + String.valueOf(selectedAppointment.getBudget());
        btn_jacks_credit_card.setText(balance);
    }

    private void onBuyServiceRequest() {
        progressDialog = createProgressDialog(this);
        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGPostAppResponse> call = apiManager.buyService(selectedAppointment.getID(), currentUser.getID());
        call.enqueue(new Callback<JGGPostAppResponse>() {
            @Override
            public void onResponse(Call<JGGPostAppResponse> call, Response<JGGPostAppResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        // Todo - Refresh View
                        String jobID = response.body().getValue();
                        showAlertDialog();
                    } else {
                        Toast.makeText(BuyServiceActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(BuyServiceActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGPostAppResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(BuyServiceActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onPaymentSetup(View view) {
        if (alreadySetUp) {
            onBuyServiceRequest();
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

    private void showAlertDialog() {
        JGGAlertView builder = new JGGAlertView(this,
                getResources().getString(R.string.alert_payment_setup_success),
                "",
                false,
                "",
                R.color.JGGGreen,
                R.color.JGGGreen10Percent,
                getResources().getString(R.string.alert_ok),
                R.color.JGGGreen);
        alertDialog = builder.create();
        builder.setOnItemClickListener(new JGGAlertView.OnItemClickListener() {
            @Override
            public void onDoneButtonClick(View view) {
                if (view.getId() == R.id.btn_alert_cancel)
                    alertDialog.dismiss();
                else {
                    alertDialog.dismiss();
                    startActivity(new Intent(BuyServiceActivity.this, MainActivity.class));
                }
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }
}
