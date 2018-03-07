package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 12/12/2017.
 */

public class JobMainQuotationView extends RelativeLayout implements View.OnClickListener {

    private Context mContext;

    public TextView lblTitle;
    public ImageView imgQuotation;
    public LinearLayout quotationLine;
    public TextView btnViewQuotation;

    public JobMainQuotationView(Context context) {
        super(context);
        this.mContext = context;

        initView();
    }

    private void initView() {
        LayoutInflater mLayoutInflater       = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view                            = mLayoutInflater.inflate(R.layout.view_job_main_quotation, this);

        lblTitle = (TextView) view.findViewById(R.id.lbl_title);
        imgQuotation = (ImageView) view.findViewById(R.id.img_quotation);
        quotationLine = (LinearLayout) view.findViewById(R.id.quotation_line);
        btnViewQuotation = (TextView) view.findViewById(R.id.btn_view_quotation);
        btnViewQuotation.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_view_quotation) {
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
