package com.kelvin.jacksgogo.CustomView.Views;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.Search.ServiceSearchActivity;
import com.kelvin.jacksgogo.Models.Jobs_Services_Events.JGGAppBaseModel;
import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/13/2017.
 */

public class SearchMainTabView extends RelativeLayout implements View.OnClickListener {

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    private View searchTabView;

    private LinearLayout servicesButton;
    private LinearLayout jobsButton;
    private LinearLayout goClubButton;
    private TextView servicesTextView;
    private TextView jobsTextView;
    private TextView goClubTextView;
    private ImageView servicesDotImageView;
    private ImageView jobsDotImageView;
    private ImageView goClubDotImageView;
    private ImageButton searchButton;
    private JGGAppBaseModel.AppointmentType type;

    public SearchMainTabView(Context context) {
        super(context);
        mContext = context;

        initView();
    }

    private void initView(){

        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        searchTabView  = mLayoutInflater.inflate(R.layout.view_search_main_tab, this);

        servicesButton = (LinearLayout) searchTabView.findViewById(R.id.services_layout);
        jobsButton = (LinearLayout) searchTabView.findViewById(R.id.jobs_layout);
        goClubButton = (LinearLayout) searchTabView.findViewById(R.id.go_club_layout);
        servicesTextView = (TextView) searchTabView.findViewById(R.id.lbl_service);
        jobsTextView = (TextView) searchTabView.findViewById(R.id.lbl_jobs);
        goClubTextView = (TextView) searchTabView.findViewById(R.id.lbl_go_club);
        servicesDotImageView = (ImageView) searchTabView.findViewById(R.id.img_services_cirle);
        jobsDotImageView = (ImageView) searchTabView.findViewById(R.id.img_jobs_circle);
        goClubDotImageView = (ImageView) searchTabView.findViewById(R.id.img_go_club_circle);
        searchButton = (ImageButton) searchTabView.findViewById(R.id.btn_search);

        searchButton.setOnClickListener(this);
        servicesButton.setOnClickListener(this);
        jobsButton.setOnClickListener(this);
        goClubButton.setOnClickListener(this);
        servicesTextView.setTag("SERVICES");
        jobsTextView.setTag("JOBS");
        goClubTextView.setTag("GOCLUB");

        type = JGGAppBaseModel.AppointmentType.SERVICES;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_search) {
            Intent intent = new Intent(view.getContext(), ServiceSearchActivity.class);
            if (type == JGGAppBaseModel.AppointmentType.SERVICES) {
                intent.putExtra("APPOINTMENT_TYPE", "SERVICES");
            } else if (type == JGGAppBaseModel.AppointmentType.JOBS) {
                intent.putExtra("APPOINTMENT_TYPE", "JOBS");
            } else if (type == JGGAppBaseModel.AppointmentType.GOCLUB) {
                intent.putExtra("APPOINTMENT_TYPE", "GOCLUB");
            }
            mContext.startActivity(intent);
        } else {
            servicesDotImageView.setVisibility(View.INVISIBLE);
            jobsDotImageView.setVisibility(View.INVISIBLE);
            goClubDotImageView.setVisibility(View.INVISIBLE);

            servicesTextView.setTextColor(getResources().getColor(R.color.JGGGrey1));
            jobsTextView.setTextColor(getResources().getColor(R.color.JGGGrey1));
            goClubTextView.setTextColor(getResources().getColor(R.color.JGGGrey1));

            if (view.getId() == R.id.services_layout) {
                servicesTextView.setTextColor(getResources().getColor(R.color.JGGGreen));
                searchButton.setImageResource(R.mipmap.button_search_green);
                servicesDotImageView.setVisibility(View.VISIBLE);
                listener.onTabbarItemClick(servicesTextView);
                type = JGGAppBaseModel.AppointmentType.SERVICES;
            } else if (view.getId() == R.id.jobs_layout) {
                jobsTextView.setTextColor(getResources().getColor(R.color.JGGCyan));
                searchButton.setImageResource(R.mipmap.button_search_cyan);
                jobsDotImageView.setVisibility(View.VISIBLE);
                listener.onTabbarItemClick(jobsTextView);
                type = JGGAppBaseModel.AppointmentType.JOBS;
            } else if (view.getId() == R.id.go_club_layout) {
                goClubTextView.setTextColor(getResources().getColor(R.color.JGGPurple));
                searchButton.setImageResource(R.mipmap.button_search_purple);
                goClubDotImageView.setVisibility(View.VISIBLE);
                listener.onTabbarItemClick(goClubTextView);
                type = JGGAppBaseModel.AppointmentType.GOCLUB;
            }
        }
    }

    private OnTabbarItemClickListener listener;

    public interface OnTabbarItemClickListener {
        void onTabbarItemClick(TextView item);
    }

    public void setTabbarItemClickListener(OnTabbarItemClickListener listener) {
        this.listener = listener;
    }
}
