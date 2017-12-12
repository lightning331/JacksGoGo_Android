package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 12/12/2017.
 */

public class JobMainReview extends RelativeLayout implements View.OnClickListener {

    private Context mContext;

    public TextView lblReviewDate;
    public TextView lblReviewTitle;
    public LinearLayout btnReview;

    public JobMainReview(Context context) {
        super(context);
        this.mContext = context;

        initView();
    }

    private void initView() {
        LayoutInflater mLayoutInflater       = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view                            = mLayoutInflater.inflate(R.layout.view_job_main_review, this);

        lblReviewDate = (TextView) view.findViewById(R.id.lbl_job_main_review_date);
        lblReviewTitle = (TextView) view.findViewById(R.id.lbl_job_main_review_title);
        btnReview = (LinearLayout) view.findViewById(R.id.btn_job_main_review);
        btnReview.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_job_main_review) {
            listener.onItemClick(view);
        }
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
