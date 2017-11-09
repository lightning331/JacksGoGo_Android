package com.kelvin.jacksgogo.CustomView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/3/2017.
 */

public class JobDetailActionbarView extends RelativeLayout implements View.OnClickListener {

    Context mContext;
    LayoutInflater mLayoutInflater;
    LinearLayout mBackButton;
    LinearLayout mDetailMoreButton;
    View mActionbarView;

    public ImageView moreMenuImage;
    public TextView title;
    private EditStatus editStatus;

    public enum EditStatus {
        NONE,
        EDIT,
        DELETE;
    }

    public void setStatus(EditStatus status) {
        this.editStatus = status;
        switch (status) {
            case NONE:
                title.setText("");
                moreMenuImage.setImageResource(R.mipmap.button_more_orange);
                break;
            case EDIT:
                title.setText("Edit");
                moreMenuImage.setImageResource(R.mipmap.button_tick_orange);
                break;
            case DELETE:
                break;
            default:
                break;
        }
    }
    public EditStatus getEditStatus() {
        return this.editStatus;
    }

    public JobDetailActionbarView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    private void initView(){

        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mActionbarView = mLayoutInflater.inflate(R.layout.job_detail_actionbar_view, this);

        mBackButton = (LinearLayout) mActionbarView.findViewById(R.id.btn_back);
        mDetailMoreButton = (LinearLayout) mActionbarView.findViewById(R.id.btn_more);
        moreMenuImage = (ImageView) mActionbarView.findViewById(R.id.img_more_menu);
        title = (TextView) mActionbarView.findViewById(R.id.lbl_detail_info_actionbar_title);

        mBackButton.setOnClickListener(this);
        mDetailMoreButton.setOnClickListener(this);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onClick(View view) {
        listener.onDetailActionbarItemClick(view);
    }

    private OnJobDetailActionbarItemClickListener listener;

    public interface OnJobDetailActionbarItemClickListener {
        void onDetailActionbarItemClick(View item);
    }

    public void setJobDetailActionbarItemClickListener(OnJobDetailActionbarItemClickListener listener) {
        this.listener = listener;
    }
}
