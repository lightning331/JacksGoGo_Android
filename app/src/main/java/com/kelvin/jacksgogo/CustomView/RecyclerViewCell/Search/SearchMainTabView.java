package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/13/2017.
 */

public class SearchMainTabView extends RelativeLayout implements View.OnClickListener {

    Context mContext;
    LayoutInflater mLayoutInflater;

    TextView servicesTextView;
    TextView jobsTextView;
    TextView goClubTextView;
    ImageView servicesDotImageView;
    ImageView jobsDotImageView;
    ImageView goClubDotImageView;
    ImageButton searchButton;
    View searchTabView;


    public SearchMainTabView(Context context) {
        super(context);
        mContext = context;

        initView();
    }

    private void initView(){

        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        searchTabView  = mLayoutInflater.inflate(R.layout.search_main_tab_view, this);

        servicesTextView = (TextView) searchTabView.findViewById(R.id.lbl_service);
        jobsTextView = (TextView) searchTabView.findViewById(R.id.lbl_jobs);
        goClubTextView = (TextView) searchTabView.findViewById(R.id.lbl_go_club);
        servicesDotImageView = (ImageView) searchTabView.findViewById(R.id.img_services_cirle);
        jobsDotImageView = (ImageView) searchTabView.findViewById(R.id.img_jobs_circle);
        goClubDotImageView = (ImageView) searchTabView.findViewById(R.id.img_go_club_circle);
        searchButton = (ImageButton) searchTabView.findViewById(R.id.btn_search);

        searchButton.setOnClickListener(this);
        servicesTextView.setOnClickListener(this);
        jobsTextView.setOnClickListener(this);
        goClubTextView.setOnClickListener(this);
        servicesTextView.setTag("SERVIES");
        jobsTextView.setTag("JOBS");
        goClubTextView.setTag("GOCLUB");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_search) {
            // back to previous view
//            Intent intent = new Intent(view.getContext(), AppFilterActivity.class);
//            mContext.startActivity(intent);
        } else {
            servicesDotImageView.setVisibility(View.INVISIBLE);
            jobsDotImageView.setVisibility(View.INVISIBLE);
            goClubDotImageView.setVisibility(View.INVISIBLE);

            servicesTextView.setTextColor(getResources().getColor(R.color.JGGGrey1));
            jobsTextView.setTextColor(getResources().getColor(R.color.JGGGrey1));
            goClubTextView.setTextColor(getResources().getColor(R.color.JGGGrey1));

            if (view.getId() == R.id.lbl_service) {
                servicesTextView.setTextColor(getResources().getColor(R.color.JGGGreen));
                servicesDotImageView.setVisibility(View.VISIBLE);
                listener.onTabbarItemClick(servicesTextView);
            } else if (view.getId() == R.id.lbl_jobs) {
                jobsTextView.setTextColor(getResources().getColor(R.color.JGGGreen));
                jobsDotImageView.setVisibility(View.VISIBLE);
                listener.onTabbarItemClick(jobsTextView);
            } else if (view.getId() == R.id.lbl_go_club) {
                goClubTextView.setTextColor(getResources().getColor(R.color.JGGGreen));
                goClubDotImageView.setVisibility(View.VISIBLE);
                listener.onTabbarItemClick(goClubTextView);
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
