package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global.JGGUserType;
import com.kelvin.jacksgogo.Utils.JGGAppManager;

import java.util.Date;

import static com.kelvin.jacksgogo.Utils.JGGTimeManager.appointmentMonthDate;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getDayMonthYear;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getTimePeriodString;

/**
 * Created by PUMA on 12/12/2017.
 */

public class JobStatusSummaryQuotationView extends RelativeLayout implements View.OnClickListener {

    private Context mContext;
    private int mImage;

    public TextView lblTitle;
    public ImageView imgQuotation;
    public LinearLayout quotationLine;
    public TextView lblTime;
    public TextView lblQuotationCount;
    public TextView btnViewQuotation;
    public LinearLayout viewQuotationLayout;
    public LinearLayout awardedLayout;
    public TextView lblAwardTime;
    public LinearLayout llAwardQuote;
    public ImageView imgRightButton;

    public JobStatusSummaryQuotationView(Context context, JGGUserType userType) {
        super(context);
        this.mContext = context;

        if (userType == JGGUserType.CLIENT)
            mImage = R.mipmap.button_next_green;
        else if (userType == JGGUserType.PROVIDER)
            mImage = R.mipmap.button_next_cyan;

        initView();
    }

    private void initView() {
        LayoutInflater mLayoutInflater       = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view                            = mLayoutInflater.inflate(R.layout.view_job_status_summary_quotation, this);

        imgQuotation = view.findViewById(R.id.img_quotation);
        quotationLine = view.findViewById(R.id.quotation_line);

        viewQuotationLayout = view.findViewById(R.id.view_quotation_layout);

        lblTime = view.findViewById(R.id.lbl_received_quotation_time);
        lblTitle = view.findViewById(R.id.lbl_title);

        awardedLayout = view.findViewById(R.id.awarded_layout);
        lblAwardTime = view.findViewById(R.id.lbl_award_time);

        llAwardQuote = view.findViewById(R.id.ll_award_quote);
        lblQuotationCount = view.findViewById(R.id.lbl_quotation_count);
        imgRightButton = view.findViewById(R.id.img_right);
        imgRightButton.setImageResource(mImage);

        btnViewQuotation = view.findViewById(R.id.btn_view_quotation);
        btnViewQuotation.setOnClickListener(this);
    }

    public void notifyDataChanged(boolean isDeleted, int count) {
        String strPostOn = JGGAppManager.getInstance().getSelectedAppointment().getPostOn();
        Date postOn = appointmentMonthDate(strPostOn);
        String proposalCount = "You have received " + count + " new quotation!";
        if (isDeleted) {
            quotationLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGGrey3));
            imgQuotation.setImageResource(R.mipmap.icon_provider_inactive);
            awardedLayout.setVisibility(View.VISIBLE);
            viewQuotationLayout.setVisibility(View.GONE);

            lblTime.setText(getDayMonthYear(postOn) + " " + getTimePeriodString(postOn));
            lblQuotationCount.setText(proposalCount);
            lblQuotationCount.setOnClickListener(this);
        } else {
            quotationLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.JGGGreen));
            imgQuotation.setImageResource(R.mipmap.icon_provider_green);

            if (count == 0) {       // Invite Service Provider
                lblTitle.setText(R.string.waiting_service_provider);
                btnViewQuotation.setText(R.string.invite_service_provider);
            } else if (count > 0) {      // View Quotation
                lblTitle.setText(proposalCount);
                btnViewQuotation.setText(R.string.view_quotation);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_view_quotation
                || view.getId() == R.id.lbl_quotation_count) {
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
