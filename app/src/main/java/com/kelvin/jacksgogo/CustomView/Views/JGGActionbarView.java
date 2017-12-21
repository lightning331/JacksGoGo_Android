package com.kelvin.jacksgogo.CustomView.Views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.Models.Jobs_Services_Events.JGGAppBaseModel;
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

    LinearLayout.LayoutParams param;
    LinearLayout.LayoutParams param1;

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
        APPOINTMENT,
        EDIT_MAIN,
        EDIT_DETAIL,
        MAP,
        SERVICE,
        SERVICE_LISTING,
        SERVICE_LISTING_DETAIL,
        ACTIVE_AROUND,
        POST_SERVICE,
        SERVICE_TIME_SLOTS,
        SERVICE_REVIEWS,
        SERVICE_BUY,
        SETUP_CARD,
        JACKS_WALLET,
        REQUEST_QUOTATION,
        VERIFY_SKILL,
        POSTED_SERVICE,
        SEARCH,
        SEARCH_RESULT,
        INVITE,
        BID,
        ACCEPT_BIDE,
        JOB_REPORT
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
    public void setStatus(EditStatus status, JGGAppBaseModel.AppointmentType type) {
        this.editStatus = status;
        switch (status) {
            case NONE:
                mTitleTextView.setText("");
                mBackButtonTitleTextView.setText("");
                mBackButtonImage.setImageResource(R.mipmap.button_backarrow_orange);
                mMoreButtonImage.setImageResource(R.mipmap.button_more_orange);
                break;
            case APPOINTMENT:
                mTitleTextView.setText("");
                mBackButtonTitleTextView.setText(R.string.title_appointment);
                mBackButtonImage.setImageResource(R.mipmap.button_backarrow_orange);
                mMoreButtonImage.setImageResource(R.mipmap.button_more_orange);
                param = new LinearLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT,
                        3.0f
                );
                mBackButton.setLayoutParams(param);
                moreButtonsLayout.setLayoutParams(param);
                param1 = new LinearLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT,
                        4.0f
                );
                centerTitleTextLayout.setLayoutParams(param1);
                break;
            case EDIT_MAIN:
                setEditDoneButton();
                break;
            case EDIT_DETAIL:
                setEditDoneButton();
                break;
            case MAP:
                setGreenBackButton("", R.string.title_map);
                break;
            case SERVICE:
                mTitleTextView.setText("");
                mBackButtonTitleTextView.setText("");
                mBackButtonImage.setImageResource(R.mipmap.button_backarrow_green);
                mLikeButtonImage.setImageResource(R.mipmap.button_favourite_outline_green);
                mMoreButtonImage.setImageResource(R.mipmap.button_more_green);
                break;
            case SERVICE_LISTING:
                setOrangeBackButton(R.string.title_service_listing, R.string.title_profile);
                break;
            case ACTIVE_AROUND:
                if (type == JGGAppBaseModel.AppointmentType.SERVICES) {
                    setGreenBackButton("", R.string.title_active_service_around);
                } else if (type == JGGAppBaseModel.AppointmentType.JOBS) {
                    setCyanBackButton("", R.string.title_active_job_around);
                } else if (type == JGGAppBaseModel.AppointmentType.GOCLUB) {
                    setPurpleBackButton(R.string.title_search, R.string.title_active_goclub_around);
                }
                break;
            case POST_SERVICE:
                setGreenBackButton("", R.string.title_post_service);
                break;
            case SERVICE_TIME_SLOTS:
                mTitleTextView.setText(R.string.title_time_slots);
                mBackButtonTitleTextView.setText("");
                mBackButtonImage.setImageResource(R.mipmap.button_backarrow_green);
                mMoreButtonImage.setImageResource(R.mipmap.button_today_green);
                break;
            case SERVICE_REVIEWS:
                setGreenBackButton("", R.string.title_review);
                break;
            case SERVICE_BUY:
                setGreenBackButton("", R.string.title_buy_service);
                break;
            case SETUP_CARD:
                setGreenBackButton("", R.string.title_credit_card);
                break;
            case JACKS_WALLET:
                setGreenBackButton("", R.string.title_jacks_wallet);
                break;
            case REQUEST_QUOTATION:
                setGreenBackButton("", R.string.title_request_quotation);
                break;
            case VERIFY_SKILL:
                setOrangeBackButton(R.string.title_verify_new_skill, R.string.title_empty);
                break;
            case BID:
                setOrangeBackButton(R.string.title_bid, R.string.title_empty);
                break;
            case ACCEPT_BIDE:
                setOrangeBackButton(R.string.accept_bid_title, R.string.title_empty);
                break;
            case JOB_REPORT:
                setOrangeBackButton(R.string.job_report_title, R.string.title_empty);
                //mBackButtonImage.setBackgroundColor();
                break;
            case POSTED_SERVICE:
                mTitleTextView.setText("");
                mBackButtonTitleTextView.setText("");
                mBackButtonImage.setImageResource(R.mipmap.button_backarrow_orange);
                mMoreButtonImage.setImageResource(R.mipmap.button_more_green);
                break;
            case SEARCH:
                if (type == JGGAppBaseModel.AppointmentType.SERVICES) {
                    setGreenBackButton("", R.string.title_search);
                } else if (type == JGGAppBaseModel.AppointmentType.JOBS) {
                    setCyanBackButton("", R.string.title_search);
                } else if (type == JGGAppBaseModel.AppointmentType.GOCLUB) {
                    setPurpleBackButton(R.string.title_search, R.string.title_empty);
                }
                break;
            case SEARCH_RESULT:
                if (type == JGGAppBaseModel.AppointmentType.SERVICES) {
                    setGreenBackButton("", R.string.search_result);
                } else if (type == JGGAppBaseModel.AppointmentType.JOBS) {
                    setCyanBackButton("", R.string.search_result);
                } else if (type == JGGAppBaseModel.AppointmentType.GOCLUB) {
                    setPurpleBackButton(R.string.search_result, R.string.title_empty);
                }
                break;
            case INVITE:
                setOrangeBackButton(R.string.invite_actionbar_title, R.string.title_empty);
                break;
            case SERVICE_LISTING_DETAIL:
                mTitleTextView.setText("");
                mBackButtonImage.setImageResource(R.mipmap.button_backarrow_orange);
                mBackButtonTitleTextView.setText(R.string.title_service_listing);
                param = new LinearLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT,
                        3.0f
                );
                param1 = new LinearLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT,
                        4.0f
                );
                moreButtonsLayout.setLayoutParams(param);
                mBackButton.setLayoutParams(param);
                centerTitleTextLayout.setLayoutParams(param1);
                break;
            default:
                break;
        }
    }

    private void setEditDoneButton() {
        mTitleTextView.setText(R.string.menu_option_edit);
        mBackButtonTitleTextView.setText(R.string.title_appointment);
        mMoreButtonImage.setImageResource(R.mipmap.button_tick_orange);
    }

    private void setOrangeBackButton(int title, int backButtonTitle) {
        mTitleTextView.setText(title);
        mBackButtonTitleTextView.setText(backButtonTitle);
        mBackButtonImage.setImageResource(R.mipmap.button_backarrow_orange);
    }

    private void setPurpleBackButton(int title, int backButtonTitle) {
        mTitleTextView.setText(title);
        mBackButtonTitleTextView.setText(backButtonTitle);
        mBackButtonImage.setImageResource(R.mipmap.button_backarrow_purple);
    }

    private void setGreenBackButton(String backTitle, int title) {
        mTitleTextView.setText(title);
        mBackButtonTitleTextView.setText(backTitle);
        mBackButtonImage.setImageResource(R.mipmap.button_backarrow_green);
    }

    private void setCyanBackButton(String backTitle, int title) {
        mTitleTextView.setText(title);
        mBackButtonTitleTextView.setText(backTitle);
        mBackButtonImage.setImageResource(R.mipmap.button_backarrow_cyan);
    }

    public void setEditMoreButtonClicked(boolean isSelected) {
        if (isSelected) {
            mMoreButtonImage.setImageResource(R.mipmap.button_more_active_orange);
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
