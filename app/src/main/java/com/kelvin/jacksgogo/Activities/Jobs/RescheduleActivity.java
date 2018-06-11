package com.kelvin.jacksgogo.Activities.Jobs;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.CustomView.Views.JGGAddTimeSlotDialog;
import com.kelvin.jacksgogo.CustomView.Views.JGGAlertView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGBaseResponse;
import com.squareup.picasso.Picasso;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView.EditStatus.RESCHEDULE;
import static com.kelvin.jacksgogo.Utils.Global.AppointmentType.JOBS;
import static com.kelvin.jacksgogo.Utils.Global.AppointmentType.SERVICES;
import static com.kelvin.jacksgogo.Utils.Global.AppointmentType.UNKNOWN;
import static com.kelvin.jacksgogo.Utils.Global.createProgressDialog;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.appointmentMonthDateString;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getAppointmentTime;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getTimePeriodString;

public class RescheduleActivity extends AppCompatActivity {

    @BindView(R.id.app_detail_actionbar)        Toolbar mToolbar;
    @BindView(R.id.img_detail)                  ImageView imgCategory;
    @BindView(R.id.lbl_title)                   TextView lblCategory;
    @BindView(R.id.lbl_date)                    TextView lblTime;
    @BindView(R.id.btn_time)                    Button btnTime;

    public JGGActionbarView actionbarView;
    private ProgressDialog progressDialog;
    private AlertDialog alertDialog;

    private JGGAppointmentModel mJob;
    private boolean isIncoming;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reschedule);

        ButterKnife.bind(this);
        isIncoming = getIntent().getBooleanExtra("isIncoming", false);

        /* ---------    Custom view add to TopToolbar     --------- */
        actionbarView = new JGGActionbarView(this);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);
        actionbarView.setStatus(RESCHEDULE, UNKNOWN);

        mJob = JGGAppManager.getInstance().getSelectedAppointment();
        setCategory();

        showOriginSchedule();
    }

    private void setCategory() {
        // Category
        Picasso.with(this)
                .load(mJob.getCategory().getImage())
                .placeholder(null)
                .into(imgCategory);
        lblCategory.setText(mJob.getCategory().getName());
        // Time
        lblTime.setText(getAppointmentTime(mJob));
    }

    private void showOriginSchedule() {
        btnTime.setText(getAppointmentTime(mJob));
    }

    @OnClick(R.id.btn_time)
    public void onClickTime() {
        Global.AppointmentType type = JOBS;
        if (isIncoming) {
            // Green
            type = SERVICES;
        } else {
            // Cyan
            type = JOBS;
        }

        String startStr = appointmentMonthDateString(mJob.getSessions().get(0).getStartOn());
        String endStr = appointmentMonthDateString(mJob.getSessions().get(0).getEndOn());

        JGGAddTimeSlotDialog builder = new JGGAddTimeSlotDialog(this, type, startStr, endStr);
        builder.setOnItemClickListener(new JGGAddTimeSlotDialog.OnItemClickListener() {
            @Override
            public void onDoneButtonClick(View view, Date start, Date end, Integer number) {
                if (view.getId() == R.id.btn_add_time_cancel) {
                    alertDialog.dismiss();
                } else if (view.getId() == R.id.btn_add_time_ok) {
                    alertDialog.dismiss();
                    String startTime = getTimePeriodString(start);
                    if (end != null) {
                        String endTime = getTimePeriodString(end);
                        btnTime.setText(startTime + " - " + endTime);
                    } else {
                        btnTime.setText(startTime);
                    }
                }
            }
        });
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    @OnClick(R.id.btn_reshcedule)
    public void onClickRequestReschedule() {
        int color = R.color.JGGGreen;
        String providerName = mJob.getUserProfile().getUser().getEmail();
        String description = providerName
                + " will be notified of the rescheduling, and be asked to respond.";
        if (isIncoming) {
            color = R.color.JGGCyan;
        }
        JGGAlertView alertView = new JGGAlertView(this,
                getString(R.string.alert_request_sent),
                description,
                false,
                "",
                R.color.JGGWhite,
                color,
                getString(R.string.alert_ok),
                color);
        alertView.show();
        alertView.setOnItemClickListener(new JGGAlertView.OnItemClickListener() {
            @Override
            public void onDoneButtonClick(View view) {
                onRequestReshedule();
            }
        });
    }

    // TODO - Need to confirm what is Service ID
    private void onRequestReshedule() {
        String serviceID = mJob.getID();

        progressDialog = createProgressDialog(this);
        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGBaseResponse> call = apiManager.sendReschedulingRequest(serviceID);
        call.enqueue(new Callback<JGGBaseResponse>() {
            @Override
            public void onResponse(Call<JGGBaseResponse> call, Response<JGGBaseResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                    } else {
                        Toast.makeText(RescheduleActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RescheduleActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGBaseResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(RescheduleActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
