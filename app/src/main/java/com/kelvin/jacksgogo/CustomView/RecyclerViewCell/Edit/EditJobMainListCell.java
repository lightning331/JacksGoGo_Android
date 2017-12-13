package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Edit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/13/2017.
 */

public class EditJobMainListCell extends RelativeLayout implements View.OnClickListener {

    private Context mContext;
    public LinearLayout tagButton;
    public TextView tagTitleTextView;
    public TextView jobDescTitleTextView;
    public TextView jobDescriptionTextView;

    public EditJobMainListCell(Context context) {
        super(context);
        mContext = context;

        initView();
    }

    private void initView() {
        LayoutInflater mLayoutInflater      = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView                 = mLayoutInflater.inflate(R.layout.cell_edit_job_main_list, this);

        this.tagButton = itemView.findViewById(R.id.btn_edit_job_tag1);
        this.tagButton.setOnClickListener(this);
        this.tagTitleTextView = itemView.findViewById(R.id.lbl_edit_job_tag_title1);
        this.jobDescTitleTextView = itemView.findViewById(R.id.lbl_edit_job_description_title1);
        this.jobDescriptionTextView = itemView.findViewById(R.id.lbl_edit_job_description1);
    }

    public void setData(String title, String descriptionTitle, String description) {
        if (descriptionTitle == null) {
            jobDescTitleTextView.setVisibility(View.GONE);
        }
        jobDescTitleTextView.setText(descriptionTitle);
        tagTitleTextView.setText(title);
        jobDescriptionTextView.setText(description);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_edit_job_tag1) {
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
