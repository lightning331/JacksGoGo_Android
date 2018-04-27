package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGJobInfoModel;

import java.util.Date;

/**
 * Created by PUMA on 3/9/2018.
 */

public class JobDetailAverageQuoteCell extends RecyclerView.ViewHolder {

    public TextView lblResponseCount;
    public TextView lblLatestResponseTime;
    public TextView lblAverageQuote;
    public LinearLayout budgetLayout;

    public JobDetailAverageQuoteCell(View itemView) {
        super(itemView);

        lblResponseCount = itemView.findViewById(R.id.lbl_response_count);
        lblLatestResponseTime = itemView.findViewById(R.id.lbl_latest_response);
        lblAverageQuote = itemView.findViewById(R.id.lbl_average_quote);
        budgetLayout = itemView.findViewById(R.id.budget_layout);
    }

    public void setAverageData(JGGJobInfoModel jobInfo) {
        if (jobInfo == null) {
            lblResponseCount.setText(" responses");
            lblAverageQuote.setText("$ ");
            lblLatestResponseTime.setText("Latest response: ");
        } else {
            lblResponseCount.setText(String.valueOf(jobInfo.getProposalCount()) + " responses");
            lblAverageQuote.setText("$ " + String.valueOf(jobInfo.getAveragePrice()));
            lblLatestResponseTime.setText("Latest response: " + getTimeAgo(jobInfo));
        }
    }

    private String getTimeAgo(JGGJobInfoModel jobInfo) {
        Date lastResponseDate = jobInfo.getLastRespondOn();
        long timeInMilliseconds = lastResponseDate.getTime();
        String ago = TimeAgo.using(timeInMilliseconds);
        return ago;
    }
}
