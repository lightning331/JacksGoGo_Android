package com.kelvin.jacksgogo.CustomView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.TypedValue;
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
    LinearLayout moreButtonsLayout;
    LinearLayout centerTitleTextLayout;

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
        EDIT_MAIN,
        EDIT_DETAIL,
        MAP,
        SERVICE,
        SERVICE_LISTING,
        ACTIVE_SERVICE,
        POST_SERVICE,
        SERVICE_TIME_SLOTS,
        SERVICE_REVIEWS,
        SERVICE_BUY,
        SETUP_CARD,
        JACKS_WALLET,
        REQUEST_QUOTATION,
        VERIFY_SKILL
    }

    public JGGActionbarView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    private void initView(){

        LayoutInflater mLayoutInflater      = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mActionbarView                 = mLayoutInflater.inflate(R.layout.jgg_actionbar_view, this);

        mBackButton                     = (LinearLayout) mActionbarView.findViewById(R.id.btn_back);
        moreButtonsLayout               = (LinearLayout) mActionbarView.findViewById(R.id.more_buttons_layout);
        centerTitleTextLayout           = (LinearLayout) mActionbarView.findViewById(R.id.center_title_layout);
        mBackButtonImage                = (ImageView) mActionbarView.findViewById(R.id.img_back);
        mLikeButton                     = (LinearLayout) mActionbarView.findViewById(R.id.btn_like_original);
        mLikeButtonImage                = (ImageView) mActionbarView.findViewById(R.id.img_like);
        mMoreButton                     = (LinearLayout) mActionbarView.findViewById(R.id.btn_more);
        mMoreButtonImage                = (ImageView) mActionbarView.findViewById(R.id.img_more_menu);
        mTitleTextView                  = (TextView) mActionbarView.findViewById(R.id.lbl_detail_info_actionbar_title);
        mBackButtonTitleTextView        = (TextView) mActionbarView.findViewById(R.id.lbl_back_title);

        mBackButton.setOnClickListener(this);
        mLikeButton.setOnClickListener(this);
        mMoreButton.setOnClickListener(this);
    }

    public EditStatus getEditStatus() {
        return this.editStatus;
    }

    @SuppressLint("ResourceAsColor")
    public void setStatus(EditStatus status) {
        this.editStatus = status;
        switch (status) {
            case NONE:
                mTitleTextView.setText("");
                mBackButtonTitleTextView.setText(R.string.title_appointment);
                mMoreButtonImage.setImageResource(R.mipmap.button_more_orange);
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT,
                        3.0f
                );
                mBackButton.setLayoutParams(param);
                moreButtonsLayout.setLayoutParams(param);
                LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT,
                        4.0f
                );
                centerTitleTextLayout.setLayoutParams(param1);
                break;
            case EDIT_MAIN:
                mTitleTextView.setText(R.string.menu_option_edit);
                mMoreButtonImage.setImageResource(R.mipmap.button_tick_orange);
                break;
            case EDIT_DETAIL:
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
            case SERVICE_LISTING:
                mTitleTextView.setText(R.string.title_service_listing);
                mBackButtonTitleTextView.setText(R.string.title_profile);
                mBackButtonImage.setImageResource(R.mipmap.button_backarrow_orange);
                break;
            case ACTIVE_SERVICE:
                mTitleTextView.setText(R.string.title_active_service_around);
                mBackButtonTitleTextView.setText("");
                mBackButtonImage.setImageResource(R.mipmap.button_backarrow_green);
                break;
            case POST_SERVICE:
                mTitleTextView.setText(R.string.title_post_service);
                mBackButtonTitleTextView.setText("");
                mBackButtonImage.setImageResource(R.mipmap.button_backarrow_green);
                break;
            case SERVICE_TIME_SLOTS:
                mTitleTextView.setText(R.string.title_time_slots);
                mBackButtonTitleTextView.setText("");
                mBackButtonImage.setImageResource(R.mipmap.button_backarrow_green);
                mMoreButtonImage.setImageResource(R.mipmap.button_today_green);
                break;
            case SERVICE_REVIEWS:
                mTitleTextView.setText(R.string.title_review);
                mBackButtonTitleTextView.setText("");
                mBackButtonImage.setImageResource(R.mipmap.button_backarrow_green);
                break;
            case SERVICE_BUY:
                mTitleTextView.setText(R.string.title_buy_service);
                mBackButtonTitleTextView.setText("");
                mBackButtonImage.setImageResource(R.mipmap.button_backarrow_green);
                break;
            case SETUP_CARD:
                mTitleTextView.setText(R.string.title_credit_card);
                mBackButtonTitleTextView.setText("");
                mBackButtonImage.setImageResource(R.mipmap.button_backarrow_green);
                break;
            case JACKS_WALLET:
                mTitleTextView.setText(R.string.title_jacks_wallet);
                mBackButtonTitleTextView.setText("");
                mBackButtonImage.setImageResource(R.mipmap.button_backarrow_green);
                break;
            case REQUEST_QUOTATION:
                mTitleTextView.setText(R.string.title_request_quotation);
                mBackButtonTitleTextView.setText("");
                mBackButtonImage.setImageResource(R.mipmap.button_backarrow_green);
                break;
            case VERIFY_SKILL:
                mTitleTextView.setText(R.string.title_verify_new_skill);
                mBackButtonTitleTextView.setText("");
                mBackButtonImage.setImageResource(R.mipmap.button_backarrow_orange);
                break;
            default:
                break;
        }
    }

    public void setEditMoreButtonClicked(boolean isSelected) {
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

    public void setShareMoreButtonClicked(boolean isSelected) {
        if (isSelected) {
            mMoreButtonImage.setImageResource(R.mipmap.button_more_active_green);
        } else {
            mMoreButtonImage.setImageResource(R.mipmap.button_more_green);
        }
        mMoreButtonSelected = !isSelected;
    }

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
