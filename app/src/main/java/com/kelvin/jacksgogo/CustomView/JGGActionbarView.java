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

public class JGGActionbarView extends RelativeLayout implements View.OnClickListener {

    Context mContext;

    LinearLayout mBackButton;
    LinearLayout mMoreButton;
    LinearLayout mLikeButton;

    public ImageView mBackButtonImage;
    public ImageView mMoreButtonImage;
    public ImageView mLikeButtonImage;
    public TextView mTitleTextView;
    public TextView mBackButtonTitleTextView;

    public boolean mLikeButtonSelected = false;
    public boolean mMoreButtonSelected = false;

    private EditStatus editStatus;
    public enum EditStatus {
        NONE,
        EDIT,
        MAP,
        SERVICE,
        FULLEDIT
    }

    public JGGActionbarView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    private void initView(){

        LayoutInflater mLayoutInflater      = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mActionbarView                 = mLayoutInflater.inflate(R.layout.jgg_actionbar_view, this);

        mBackButton         = (LinearLayout) mActionbarView.findViewById(R.id.btn_back);
        mBackButtonImage    = (ImageView) mActionbarView.findViewById(R.id.img_back);
        mLikeButton         = (LinearLayout) mActionbarView.findViewById(R.id.btn_like_original);
        mLikeButtonImage    = (ImageView) mActionbarView.findViewById(R.id.img_like);
        mMoreButton         = (LinearLayout) mActionbarView.findViewById(R.id.btn_more);
        mMoreButtonImage    = (ImageView) mActionbarView.findViewById(R.id.img_more_menu);
        mTitleTextView      = (TextView) mActionbarView.findViewById(R.id.lbl_detail_info_actionbar_title);
        mBackButtonTitleTextView      = (TextView) mActionbarView.findViewById(R.id.lbl_back_title);

        mBackButton.setOnClickListener(this);
        mLikeButton.setOnClickListener(this);
        mMoreButton.setOnClickListener(this);
    }

    public EditStatus getEditStatus() {
        return this.editStatus;
    }

    public void setStatus(EditStatus status) {
        this.editStatus = status;
        switch (status) {
            case NONE:
                mTitleTextView.setText("");
                mBackButtonTitleTextView.setText(R.string.title_appointment);
                mMoreButtonImage.setImageResource(R.mipmap.button_more_orange);
                break;
            case EDIT:
                mTitleTextView.setText(R.string.menu_option_edit);
                mMoreButtonImage.setImageResource(R.mipmap.button_tick_orange);
                break;
            case MAP:
                mTitleTextView.setText(R.string.title_map);
                mBackButtonTitleTextView.setText("");
                mBackButtonImage.setImageResource(R.mipmap.button_backarrow_green);
                break;
            case SERVICE:
                mTitleTextView.setText("");
                mBackButtonTitleTextView.setText("");
                mBackButtonImage.setImageResource(R.mipmap.button_backarrow_green);
                mLikeButtonImage.setImageResource(R.mipmap.button_favourite_outline_green);
                mMoreButtonImage.setImageResource(R.mipmap.button_more_green);
                break;
            case FULLEDIT:

                break;
            default:
                break;
        }
    }

    public void setDetailMoreButtonClicked(boolean isSelected) {
        if (isSelected) {
            mMoreButtonImage.setImageResource(R.mipmap.button_more_orange_active);
        } else {
            mMoreButtonImage.setImageResource(R.mipmap.button_more_orange);
        }
    }

    public void setLikeButtonClicked(boolean isSelected) {
        if (isSelected) {
            mLikeButtonImage.setImageResource(R.mipmap.button_favourite_outline_green);
        } else {
            mLikeButtonImage.setImageResource(R.mipmap.button_favourite_green);
        }
        mLikeButtonSelected = !isSelected;
    }

    public void setMoreButtonClicked(boolean isSelected) {
        if (isSelected) {
            mMoreButtonImage.setImageResource(R.mipmap.button_more_green);
        } else {
            mMoreButtonImage.setImageResource(R.mipmap.button_more_active_green);
        }
        mMoreButtonSelected = !isSelected;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onClick(View view) {
        listener.onActionbarItemClick(view);
    }

    private OnActionbarItemClickListener listener;

    public interface OnActionbarItemClickListener {
        void onActionbarItemClick(View item);
    }

    public void setActionbarItemClickListener(OnActionbarItemClickListener listener) {
        this.listener = listener;
    }
}
