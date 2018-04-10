package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

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
}
