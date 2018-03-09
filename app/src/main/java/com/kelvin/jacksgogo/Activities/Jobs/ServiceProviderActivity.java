package com.kelvin.jacksgogo.Activities.Jobs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.Adapter.Jobs.QuotationAdapter;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.Models.JGGBiddingProviderModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppBaseModel;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserBaseModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGProposalResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.creatingAppointment;
import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.selectedCategory;
import static com.kelvin.jacksgogo.Utils.Global.getDayMonthYear;
import static com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentBaseModel.appointmentMonthDate;

public class ServiceProviderActivity extends AppCompatActivity {

    @BindView(R.id.app_invite_actionbar) Toolbar mToolbar;
    @BindView(R.id.img_detail) ImageView imgCategory;
    @BindView(R.id.lbl_title) TextView lblCategory;
    @BindView(R.id.lbl_date) TextView lblTime;
    @BindView(R.id.quotation_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.btn_invite_service_provider) TextView btnInvite;

    private JGGActionbarView actionbarView;
    private ProgressDialog progressDialog;

    private ArrayList<JGGBiddingProviderModel> quotationArray = new ArrayList<>();
    private ArrayList<JGGProposalModel> proposals;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider);
        ButterKnife.bind(this);

        initView();
        setCategory();
        getProposalsByJob();
    }

    private void initView() {
        // Top Navigationbar View
        actionbarView = new JGGActionbarView(this);
        mToolbar.addView(actionbarView);
        setSupportActionBar(mToolbar);

        actionbarView.setStatus(JGGActionbarView.EditStatus.SERVICE_PROVIDER, JGGAppBaseModel.AppointmentType.UNKNOWN);
        actionbarView.setActionbarItemClickListener(new JGGActionbarView.OnActionbarItemClickListener() {
            @Override
            public void onActionbarItemClick(View view) {
                onBackPressed();
            }
        });

        btnInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ServiceProviderActivity.this, InviteProviderActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getProposalsByJob() {
        progressDialog = Global.createProgressDialog(this);
        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, this);
        Call<JGGProposalResponse> call = apiManager.getProposalsByJob(creatingAppointment.getID(), 0, 0);
        call.enqueue(new Callback<JGGProposalResponse>() {
            @Override
            public void onResponse(Call<JGGProposalResponse> call, Response<JGGProposalResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    proposals = response.body().getValue();
                    updateRecyclerView();
                } else {
                    int statusCode  = response.code();
                    Toast.makeText(ServiceProviderActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGProposalResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ServiceProviderActivity.this, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateRecyclerView() {
        if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        }
        QuotationAdapter adapter = new QuotationAdapter(this, addDummyData());
        adapter.setOnItemClickListener(new QuotationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                onShowBidDetailClick(position);
            }
        });
        mRecyclerView.setAdapter(adapter);
    }

    private void onShowBidDetailClick(int position) {
        String status = quotationArray.get(position).getStatus().toString();

        Intent intent = new Intent(this, BidDetailActivity.class);
        intent.putExtra("bid_status", status);
        startActivity(intent);
    }

    private void setCategory() {
        // Category
        Picasso.with(this)
                .load(selectedCategory.getImage())
                .placeholder(null)
                .into(imgCategory);
        lblCategory.setText(selectedCategory.getName());
        // Time
        if (creatingAppointment.getAppointmentType() == 1) {    // One-time Job
            String time = "";
            if (creatingAppointment.getSessions() != null
                    && creatingAppointment.getSessions().size() > 0) {
                if (creatingAppointment.getSessions().get(0).isSpecific()) {
                    if (creatingAppointment.getSessions().get(0).getEndOn() != null)
                        time = "on "
                                + getDayMonthYear(appointmentMonthDate(creatingAppointment.getSessions().get(0).getStartOn()))
                                + " " + Global.getTimePeriodString(appointmentMonthDate(creatingAppointment.getSessions().get(0).getStartOn()))
                                + " - "
                                + Global.getTimePeriodString(appointmentMonthDate(creatingAppointment.getSessions().get(0).getEndOn()));
                    else
                        time = "on "
                                + getDayMonthYear(appointmentMonthDate(creatingAppointment.getSessions().get(0).getStartOn()))
                                + " " + Global.getTimePeriodString(appointmentMonthDate(creatingAppointment.getSessions().get(0).getStartOn()));
                } else {
                    if (creatingAppointment.getSessions().get(0).getEndOn() != null)
                        time = "any time until "
                                + getDayMonthYear(appointmentMonthDate(creatingAppointment.getSessions().get(0).getStartOn()))
                                + " " + Global.getTimePeriodString(appointmentMonthDate(creatingAppointment.getSessions().get(0).getStartOn()))
                                + " - "
                                + Global.getTimePeriodString(appointmentMonthDate(creatingAppointment.getSessions().get(0).getEndOn()));
                    else
                        time = "any time until "
                                + getDayMonthYear(appointmentMonthDate(creatingAppointment.getSessions().get(0).getStartOn()))
                                + " " + Global.getTimePeriodString(appointmentMonthDate(creatingAppointment.getSessions().get(0).getStartOn()));
                }
            }
            lblTime.setText(time);
        } else if (creatingAppointment.getAppointmentType() == 0) {     // Repeating Job
            String time = "";
            String dayString = creatingAppointment.getRepetition();
            String[] items = new String[0];
            if (dayString != null) {
                items = dayString.split(",");
            }
            if (creatingAppointment.getRepetitionType() == Global.JGGRepetitionType.weekly) {
                for (int i = 0; i < items.length; i ++) {
                    if (time.equals(""))
                        time = "Every " + Global.getWeekName(Integer.parseInt(items[i]));
                    else
                        time = time + ", " + "Every " + Global.getWeekName(Integer.parseInt(items[i]));
                }
            } else if (creatingAppointment.getRepetitionType() == Global.JGGRepetitionType.monthly) {
                for (int i = 0; i < items.length; i ++) {
                    if (time.equals(""))
                        time = "Every " + Global.getDayName(Integer.parseInt(items[i])) + " of the month";
                    else
                        time = time + ", " + "Every " + Global.getDayName(Integer.parseInt(items[i])) + " of the month";
                }
            }
            lblTime.setText(time);
        }
    }

    private ArrayList<JGGBiddingProviderModel> addDummyData() {

        JGGBiddingProviderModel p1 = new JGGBiddingProviderModel();
        JGGUserBaseModel user1 = new JGGUserBaseModel();
        user1.setSurname("CYYong");
        user1.setAvatarUrl(R.drawable.nurse);
        user1.setRate(4.5);
        p1.setUser(user1);
        p1.setStatus(Global.BiddingStatus.NEWPROPOSAL);
        p1.setPrice(100.00f);
        p1.setMessageCount(0);
        quotationArray.add(p1);

        JGGBiddingProviderModel p2 = new JGGBiddingProviderModel();
        JGGUserBaseModel user2 = new JGGUserBaseModel();
        user2.setSurname("Christina.P");
        user2.setAvatarUrl(R.drawable.nurse1);
        user2.setRate(5.0);
        p2.setUser(user2);
        p2.setStatus(Global.BiddingStatus.PENDING);
        p2.setPrice(105.00f);
        p2.setMessageCount(3);
        quotationArray.add(p2);

        JGGBiddingProviderModel p3 = new JGGBiddingProviderModel();
        JGGUserBaseModel user3 = new JGGUserBaseModel();
        user3.setSurname("RenYW");
        user3.setAvatarUrl(R.drawable.carousel01);
        user3.setRate(4.0);
        p3.setUser(user3);
        p3.setStatus(Global.BiddingStatus.PENDING);
        p3.setPrice(110.00f);
        p3.setMessageCount(0);
        quotationArray.add(p3);

        JGGBiddingProviderModel p4 = new JGGBiddingProviderModel();
        JGGUserBaseModel user4 = new JGGUserBaseModel();
        user4.setSurname("RositaV");
        user4.setAvatarUrl(R.drawable.carousel02);
        user4.setRate(5.0);
        p4.setUser(user4);
        p4.setStatus(Global.BiddingStatus.NOTRESPONDED);
        p4.setMessageCount(0);
        quotationArray.add(p4);

        JGGBiddingProviderModel p5 = new JGGBiddingProviderModel();
        JGGUserBaseModel user5 = new JGGUserBaseModel();
        user5.setSurname("Alicaia.Leong");
        user5.setAvatarUrl(R.drawable.carousel03);
        user5.setRate(3.5);
        p5.setUser(user5);
        p5.setStatus(Global.BiddingStatus.DECLINED);
        p5.setMessageCount(0);
        quotationArray.add(p5);

        JGGBiddingProviderModel p6 = new JGGBiddingProviderModel();
        JGGUserBaseModel user6 = new JGGUserBaseModel();
        user6.setSurname("Arimu.H");
        user6.setAvatarUrl(R.drawable.nurse1);
        user6.setRate(2.75);
        p6.setUser(user6);
        p6.setStatus(Global.BiddingStatus.REJECTED);
        p6.setPrice(90.00f);
        p6.setMessageCount(0);
        quotationArray.add(p6);

        /*JGGBiddingProviderModel p7 = new JGGBiddingProviderModel();
        JGGUserBaseModel user7 = new JGGUserBaseModel();
        user7.setSurname("RositaV");
        user7.setAvatarUrl(R.drawable.carousel02);
        user7.setRate(5.0);
        p7.setUser(user7);
        p7.setStatus(Global.BiddingStatus.NOTRESPONDED);
        p7.setMessageCount(0);
        quotationArray.add(p7);

        JGGBiddingProviderModel p8 = new JGGBiddingProviderModel();
        JGGUserBaseModel user8 = new JGGUserBaseModel();
        user8.setSurname("Alicaia.Leong");
        user8.setAvatarUrl(R.drawable.carousel03);
        user8.setRate(3.5);
        p8.setUser(user8);
        p8.setStatus(Global.BiddingStatus.DECLINED);
        p8.setMessageCount(0);
        quotationArray.add(p8);

        JGGBiddingProviderModel p9 = new JGGBiddingProviderModel();
        JGGUserBaseModel user9 = new JGGUserBaseModel();
        user9.setSurname("Arimu.H");
        user9.setAvatarUrl(R.drawable.nurse1);
        user9.setRate(2.75);
        p9.setUser(user9);
        p9.setStatus(Global.BiddingStatus.REJECTED);
        p9.setPrice(90.00f);
        p9.setMessageCount(0);
        quotationArray.add(p9);*/

        return quotationArray;
    }
}
