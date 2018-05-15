package com.kelvin.jacksgogo.CustomView.Views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;

/**
 * Created by PUMA on 11/3/2017.
 */

public class JGGActionbarView extends RelativeLayout implements View.OnClickListener {

    private Context mContext;

    public LinearLayout mBackButton;
    public LinearLayout mMoreButton;
    public LinearLayout mLikeButton;
    public LinearLayout moreButtonsLayout;
    private LinearLayout centerTitleTextLayout;

    private LinearLayout.LayoutParams param;
    private LinearLayout.LayoutParams param1;

    private int imgMoreOutLine;
    private int imgLikeOutLine;
    private int imgMore;
    private int imgLike;

    public ImageView mBackButtonImage;
    public ImageView mMoreButtonImage;
    public ImageView mLikeButtonImage;
    public TextView mTitleTextView;
    public TextView mBackButtonTitleTextView;
    public TextView mMoreButtonText;

    public boolean mLikeButtonSelected = false;
    public boolean mMoreButtonSelected = false;

    private EditStatus editStatus;
    public enum EditStatus {
        NONE,
        APPOINTMENT,
        EDIT_MAIN,
        EDIT_DETAIL,
        MAP,
        LOCATION,
        DETAILS,
        SERVICE_LISTING,
        SERVICE_LISTING_DETAIL,
        ACTIVE_AROUND,
        POST,
        POSTED,
        SERVICE_TIME_SLOTS,
        SERVICE_REVIEWS,
        SERVICE_BUY,
        SETUP_CARD,
        JACKS_WALLET,
        REQUEST_QUOTATION,
        VERIFY_SKILL,
        SEARCH,
        SEARCH_RESULT,
        INVITE,
        EDIT_PROFILE,
        SERVICE_PROVIDER,
        BID,
        ACCEPT_BIDE,
        JOB_REPORT,
        ADD_TOOLS,
        ADD_BILLABLE_ITEM,
        POST_PROPOSAL,
        JOB_DETAILS,
        EDIT_JOB,
        JOINED_GO_CLUB,
        GO_CLUB_ATTENDEES,
        GO_CLUB_POST_UPDATE,
        GO_CLUB_SCHEDULE,
        REVIEW,
        RESCHEDULE,
        PACKAGE_SERVICE_TIME_SLOT
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
        mMoreButtonText                 = (TextView) mActionbarView.findViewById(R.id.txt_more);

        mMoreButtonText.setVisibility(GONE);
        mBackButton.setOnClickListener(this);
        mLikeButton.setOnClickListener(this);
        mMoreButton.setOnClickListener(this);
    }

    public EditStatus getEditStatus() {
        return this.editStatus;
    }

    @SuppressLint("ResourceAsColor")
    public void setStatus(EditStatus status, AppointmentType type) {
        this.editStatus = status;
        switch (status) {
            case NONE:
                mTitleTextView.setText("");
                mBackButtonTitleTextView.setText("");
                mBackButtonImage.setImageResource(R.mipmap.button_backarrow_orange);
                mMoreButtonImage.setImageResource(R.mipmap.button_more_orange);
                break;
            case APPOINTMENT:
                moreButtonsLayout.setVisibility(VISIBLE);
                mMoreButton.setClickable(true);
                mTitleTextView.setText("");
                mBackButtonTitleTextView.setText(R.string.title_appointment);
                mBackButtonImage.setImageResource(R.mipmap.button_backarrow_orange);
                mMoreButtonImage.setImageResource(R.mipmap.button_more_orange);

                param = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 3);
                param1 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 4);
                mBackButton.setLayoutParams(param);
                moreButtonsLayout.setLayoutParams(param);
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
            case LOCATION:
                setGreenBackButton("", R.string.title_location);
                mMoreButtonImage.setImageResource(R.mipmap.button_tick_green);
                if (type == AppointmentType.JOBS) {
                    setCyanBackButton("", R.string.title_location);
                    mMoreButtonImage.setImageResource(R.mipmap.button_tick_cyan);
                } else if (type == AppointmentType.EVENTS) {
                    setOrangeBackButton(R.string.title_location, "");
                    mMoreButtonImage.setImageResource(R.mipmap.button_tick_purple);
                } else if (type == AppointmentType.USERS) {
                    setOrangeBackButton(R.string.title_location, "");
                    mMoreButtonImage.setImageResource(R.mipmap.button_tick_orange);
                }
                break;
            case DETAILS:
                mTitleTextView.setText("");
                mBackButtonTitleTextView.setText("");
                if (type == AppointmentType.SERVICES) {
                    imgLikeOutLine = R.mipmap.button_favourite_outline_green;
                    imgMoreOutLine = R.mipmap.button_more_green;
                    imgLike = R.mipmap.button_favourite_green;
                    imgMore = R.mipmap.button_more_active_green;
                    setLikeButton(R.mipmap.button_backarrow_green, imgLikeOutLine, imgMoreOutLine);
                } else if (type == AppointmentType.JOBS) {
                    imgLikeOutLine = R.mipmap.button_favourite_outline_cyan;
                    imgMoreOutLine = R.mipmap.button_more_cyan;
                    imgLike = R.mipmap.button_favourite_cyan;
                    imgMore = R.mipmap.button_more_active_cyan;
                    setLikeButton(R.mipmap.button_backarrow_cyan, imgLikeOutLine, imgMoreOutLine);
                } else if (type == AppointmentType.GOCLUB
                        || type == AppointmentType.EVENTS) {
                    imgLikeOutLine = R.mipmap.button_favourite_outline_purple;
                    imgMoreOutLine = R.mipmap.button_more_purple;
                    imgLike = R.mipmap.button_favourite_purple;
                    imgMore = R.mipmap.button_more_active_purple;
                    setLikeButton(R.mipmap.button_backarrow_purple, imgLikeOutLine, imgMoreOutLine);
                    if (type == AppointmentType.GOCLUB) {
                        mLikeButton.setVisibility(GONE);
                    }
                }
                break;
            case JOB_DETAILS:
                mBackButtonTitleTextView.setText("");
                mBackButtonImage.setImageResource(R.mipmap.button_backarrow_orange);
                mTitleTextView.setText(R.string.job_details_title);

                moreButtonsLayout.setVisibility(INVISIBLE);
                moreButtonsLayout.setClickable(false);
                param = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 25);
                param1 = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 50);
                mBackButton.setLayoutParams(param);
                moreButtonsLayout.setLayoutParams(param);
                centerTitleTextLayout.setLayoutParams(param1);

                break;
            case SERVICE_LISTING:
                setOrangeBackButton(R.string.title_service_listing, "Profile");
                break;
            case ACTIVE_AROUND:
                if (type == AppointmentType.SERVICES) {
                    setGreenBackButton("", R.string.title_active_service_around);
                } else if (type == AppointmentType.JOBS) {
                    setCyanBackButton("", R.string.title_active_job_around);
                } else if (type == AppointmentType.GOCLUB) {
                    setPurpleBackButton(R.string.title_empty, R.string.title_active_goclub_around);
                }
                break;
            case POST:
                if (type == AppointmentType.SERVICES) {
                    setGreenBackButton("", R.string.title_post_service);
                } else if (type == AppointmentType.JOBS) {
                    setCyanBackButton("", R.string.title_post_job);
                } else if (type == AppointmentType.GOCLUB) {
                    setPurpleBackButton(R.string.title_post_event, R.string.title_empty);
                }
                break;
            case SERVICE_TIME_SLOTS:
                mTitleTextView.setText(R.string.title_time_slots);
                mBackButtonTitleTextView.setText("");
                mBackButtonImage.setImageResource(R.mipmap.button_backarrow_green);
                mMoreButtonImage.setImageResource(R.mipmap.button_today_green);
                break;
            case POST_PROPOSAL:
                setCyanBackButton("", R.string.title_make_proposal);
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
                setOrangeBackButton(R.string.title_verify_new_skill, "");
                break;
            case BID:
                setOrangeBackButton(R.string.title_bid, "");
                break;
            case ACCEPT_BIDE:
                setOrangeBackButton(R.string.accept_bid_title, "");
                break;
            case JOB_REPORT:
                moreButtonsLayout.setVisibility(INVISIBLE);
                moreButtonsLayout.setClickable(false);
                setOrangeBackButton(R.string.job_report_title, "");
                break;
            case ADD_TOOLS:
                moreButtonsLayout.setVisibility(INVISIBLE);
                moreButtonsLayout.setClickable(false);
                setOrangeBackButton(R.string.job_report_tools_title, "");
                break;
            case ADD_BILLABLE_ITEM:
                moreButtonsLayout.setVisibility(INVISIBLE);
                moreButtonsLayout.setClickable(false);
                setOrangeBackButton(R.string.job_report_billable_title, "");
                break;
            case EDIT_JOB:
                setOrangeBackButton(R.string.title_empty, "Back");
                break;
            case POSTED:
                mTitleTextView.setText("");
                mBackButtonTitleTextView.setText("");
                if (type == AppointmentType.SERVICES) {
                    mBackButtonImage.setImageResource(R.mipmap.button_backarrow_orange);
                    imgMoreOutLine = R.mipmap.button_more_green;
                    imgMore = R.mipmap.button_more_active_green;
                    setMoreButtonClicked(false);
                } else if (type == AppointmentType.JOBS) {
                    mBackButtonImage.setImageResource(R.mipmap.button_backarrow_orange);
                    mBackButtonTitleTextView.setText(R.string.title_appointment);
                    param = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 3);
                    param1 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 4);
                    mBackButton.setLayoutParams(param);
                    moreButtonsLayout.setLayoutParams(param);
                    centerTitleTextLayout.setLayoutParams(param1);

                    imgMoreOutLine = R.mipmap.button_more_cyan;
                    imgMore = R.mipmap.button_more_active_cyan;
                    setMoreButtonClicked(false);
                } else if (type == AppointmentType.GOCLUB) {
                    mBackButtonImage.setImageResource(R.mipmap.button_backarrow_purple);
                    imgMoreOutLine = R.mipmap.button_more_purple;
                    imgMore = R.mipmap.button_more_active_purple;
                    setMoreButtonClicked(false);
                }
                break;
            case SEARCH:
                if (type == AppointmentType.SERVICES) {
                    setGreenBackButton("", R.string.title_search);
                } else if (type == AppointmentType.JOBS) {
                    setCyanBackButton("", R.string.title_search);
                } else if (type == AppointmentType.GOCLUB) {
                    setPurpleBackButton(R.string.title_search, R.string.title_empty);
                }
                break;
            case SEARCH_RESULT:
                if (type == AppointmentType.SERVICES) {
                    setGreenBackButton("", R.string.search_result);
                } else if (type == AppointmentType.JOBS) {
                    setCyanBackButton("", R.string.search_result);
                } else if (type == AppointmentType.GOCLUB) {
                    setPurpleBackButton(R.string.search_result, R.string.title_empty);
                }
                break;
            case INVITE:
                setInviteButton(R.string.invite_actionbar_title);
                break;
            case EDIT_PROFILE:
                setInviteButton(R.string.edit_profile_actionbar_title);
                break;
            case SERVICE_PROVIDER:
                setOrangeBackButton(R.string.service_provider_actionbar_title, "");
                break;
            case SERVICE_LISTING_DETAIL:
                mTitleTextView.setText("");
                mBackButtonImage.setImageResource(R.mipmap.button_backarrow_orange);
                mBackButtonTitleTextView.setText(R.string.title_service_listing);
                param = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 3);
                param1 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 4);
                moreButtonsLayout.setLayoutParams(param);
                mBackButton.setLayoutParams(param);
                centerTitleTextLayout.setLayoutParams(param1);
                break;
            case GO_CLUB_ATTENDEES:
                setPurpleBackButton(R.string.all_attendees, R.string.title_empty);
                break;
            case GO_CLUB_POST_UPDATE:
                setPurpleBackButton(R.string.post_update, R.string.title_empty);
                break;
            case GO_CLUB_SCHEDULE:
                setPurpleBackButton(R.string.schedule, R.string.title_empty);
                mMoreButtonImage.setImageResource(R.mipmap.add_fat_purple);
                break;
            case REVIEW:
                mTitleTextView.setText("Review");
                mBackButtonImage.setImageResource(R.mipmap.button_backarrow_orange);
                break;
            case RESCHEDULE:
                mTitleTextView.setText("Reschedule");
                mBackButtonImage.setImageResource(R.mipmap.button_backarrow_orange);
                break;
            case PACKAGE_SERVICE_TIME_SLOT:
                mTitleTextView.setText(R.string.title_time_slots);
                mBackButtonTitleTextView.setText("");
                mBackButtonImage.setImageResource(R.mipmap.button_backarrow_orange);
                mMoreButtonImage.setImageResource(R.mipmap.button_reschedule_orange);
                break;
            default:
                break;
        }
    }

    public void setCategoryNameToActionBar(String name, AppointmentType type) {
        mTitleTextView.setText(name);
        if (type == AppointmentType.SERVICES)
            mBackButtonImage.setImageResource(R.mipmap.button_backarrow_green);
        else if (type == AppointmentType.JOBS)
            mBackButtonImage.setImageResource(R.mipmap.button_backarrow_cyan);
        else if (type == AppointmentType.GOCLUB
                || type == AppointmentType.EVENTS)
            mBackButtonImage.setImageResource(R.mipmap.button_backarrow_purple);
    }

    public void setDeleteJobStatus() {
        mTitleTextView.setText("");
        mBackButtonTitleTextView.setText(R.string.title_appointment);
        mBackButtonImage.setImageResource(R.mipmap.button_backarrow_orange);
        moreButtonsLayout.setVisibility(GONE);
        param = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 3);
        param1 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 4);
        mBackButton.setLayoutParams(param);
        moreButtonsLayout.setLayoutParams(param);
        centerTitleTextLayout.setLayoutParams(param1);
    }

    public void setEditProposalMenu(boolean status) {
        mMoreButton.setVisibility(View.VISIBLE);
        mMoreButtonImage.setVisibility(GONE);
        mMoreButtonText.setVisibility(VISIBLE);
        if (status) {
            setCyanBackButton("", R.string.proposal_title);
            mMoreButtonText.setText(R.string.menu_option_edit);
        } else {
            setCyanBackButton("", R.string.edit_proposal_title);
            mMoreButtonText.setText(R.string.proposal_save);
        }
    }

    public void setAcceptedBid(int title) {
        mMoreButton.setVisibility(INVISIBLE);
        setOrangeBackButton(title, "");
    }

    private void setInviteButton(int title) {
        mTitleTextView.setText(title);
        mBackButtonImage.setImageResource(R.mipmap.button_backarrow_orange);
        mMoreButtonImage.setImageResource(R.mipmap.button_tick_orange);
    }

    private void setLikeButton(int back, int like, int more) {
        mBackButtonImage.setImageResource(back);
        mLikeButtonImage.setImageResource(like);
        mMoreButtonImage.setImageResource(more);
    }

    private void setEditDoneButton() {
        mTitleTextView.setText(R.string.menu_option_edit);
        mBackButtonTitleTextView.setText(R.string.title_appointment);
        mMoreButtonImage.setImageResource(R.mipmap.button_tick_orange);
    }

    private void setOrangeBackButton(int title, String backButtonTitle) {
        mTitleTextView.setText(title);
        mBackButtonTitleTextView.setText(backButtonTitle);
        mBackButtonImage.setImageResource(R.mipmap.button_backarrow_orange);
    }

    public void setPurpleBackButton(int title, int backButtonTitle) {
        mTitleTextView.setText(title);
        mBackButtonTitleTextView.setText(backButtonTitle);
        mBackButtonImage.setImageResource(R.mipmap.button_backarrow_purple);
    }

    public void setGreenBackButton(String backTitle, int title) {
        mTitleTextView.setText(title);
        mBackButtonTitleTextView.setText(backTitle);
        mBackButtonImage.setImageResource(R.mipmap.button_backarrow_green);
    }

    public void setCyanBackButton(String backTitle, int title) {
        mTitleTextView.setText(title);
        mBackButtonTitleTextView.setText(backTitle);
        mBackButtonImage.setImageResource(R.mipmap.button_backarrow_cyan);
    }

    public void setProfileActionBar() {
        mTitleTextView.setText("");
        mBackButtonImage.setImageResource(R.mipmap.button_backarrow_orange);
        mMoreButtonImage.setImageResource(R.mipmap.button_edit_orange);
    }

    public void setCreditActionBar(String title) {
        mTitleTextView.setText(title);
        mBackButtonImage.setImageResource(R.mipmap.button_backarrow_orange);
        mMoreButtonImage.setVisibility(View.GONE);
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
            mLikeButtonImage.setImageResource(imgLikeOutLine);
        } else {
            mLikeButtonImage.setImageResource(imgLike);
        }
        mLikeButtonSelected = !isSelected;
    }

    public void setMoreButtonClicked(boolean isSelected) {
        if (isSelected) {
            mMoreButtonImage.setImageResource(imgMore);
        } else {
            mMoreButtonImage.setImageResource(imgMoreOutLine);
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
