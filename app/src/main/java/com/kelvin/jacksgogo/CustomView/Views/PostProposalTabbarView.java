package com.kelvin.jacksgogo.CustomView.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 3/10/2018.
 */

public class PostProposalTabbarView extends RelativeLayout implements View.OnClickListener {

    Context mContext;

    LinearLayout mDescribeButton;
    LinearLayout mBidButton;
    LinearLayout mReschedulingButton;
    LinearLayout mCancellationButton;
    ImageView imgBidLine;
    ImageView imgReschedulingLine;
    ImageView imgCancellationLine;
    public ImageView mDescribeImage;
    public ImageView mBidImage;
    public ImageView mReschedulingImage;
    public ImageView mCancellationImage;
    public TextView mDescribeText;
    public TextView mBidText;
    public TextView mReschedulingText;
    public TextView mCancellationText;

    private TabName postJobTabName;

    public enum TabName {
        DESCRIBE,
        BID,
        RESCHEDULING,
        CANCELLATION
    }

    public PostProposalTabbarView(Context context) {
        super(context);
        this.mContext = context;

        initView();
    }

    private void initView() {

        LayoutInflater mLayoutInflater      = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mTabbarView                    = mLayoutInflater.inflate(R.layout.view_post_proposal_tabbar, this);

        mDescribeButton     = (LinearLayout) mTabbarView.findViewById(R.id.btn_describe);
        mBidButton         = (LinearLayout) mTabbarView.findViewById(R.id.btn_bid);
        mReschedulingButton      = (LinearLayout) mTabbarView.findViewById(R.id.btn_rescheduling);
        mCancellationButton = (LinearLayout) mTabbarView.findViewById(R.id.btn_cancellation);
        mDescribeImage      = (ImageView) mTabbarView.findViewById(R.id.img_my_services);
        mBidImage          = (ImageView) mTabbarView.findViewById(R.id.img_bid);
        mReschedulingImage       = (ImageView) mTabbarView.findViewById(R.id.img_rescheduling);
        mCancellationImage = (ImageView) mTabbarView.findViewById(R.id.img_cancellation);
        mDescribeText       = (TextView) mTabbarView.findViewById(R.id.lbl_describe);
        mBidText           = (TextView) mTabbarView.findViewById(R.id.lbl_bid);
        mReschedulingText        = (TextView) mTabbarView.findViewById(R.id.lbl_rescheduling);
        mCancellationText = (TextView) mTabbarView.findViewById(R.id.lbl_cancellation);
        imgBidLine         = (ImageView) mTabbarView.findViewById(R.id.img_bid_line);
        imgReschedulingLine      = (ImageView) mTabbarView.findViewById(R.id.img_rescheduling_line);
        imgCancellationLine = (ImageView) mTabbarView.findViewById(R.id.img_cancellation_line);

        //mAddressButton.setOnClickListener(this);
    }

    public TabName getPostJobTabName() {
        return postJobTabName;
    }

    public void setPostJobTabName(TabName name) {
        this.postJobTabName = name;

        mDescribeText.setTextColor(getResources().getColor(R.color.JGGGrey1));
        mBidText.setTextColor(getResources().getColor(R.color.JGGGrey1));
        mReschedulingText.setTextColor(getResources().getColor(R.color.JGGGrey1));
        mCancellationText.setTextColor(getResources().getColor(R.color.JGGGrey1));
        imgBidLine.setImageResource(R.mipmap.line_dotted);
        imgReschedulingLine.setImageResource(R.mipmap.line_dotted);
        imgCancellationLine.setImageResource(R.mipmap.line_dotted);

        switch (name) {
            case DESCRIBE:
                mDescribeButton.setOnClickListener(this);
                mDescribeText.setTextColor(getResources().getColor(R.color.JGGCyan));
                mDescribeImage.setImageResource(R.mipmap.counter_blueactive);
                mBidImage.setImageResource(R.mipmap.counter_grey);
                mReschedulingImage.setImageResource(R.mipmap.counter_grey);
                mCancellationImage.setImageResource(R.mipmap.counter_grey);
                break;
            case BID:
                mDescribeButton.setOnClickListener(this);
                mBidButton.setOnClickListener(this);
                imgBidLine.setImageResource(R.mipmap.line_full);
                mBidText.setTextColor(getResources().getColor(R.color.JGGCyan));
                mDescribeImage.setImageResource(R.mipmap.counter_greytick);
                mBidImage.setImageResource(R.mipmap.counter_blueactive);
                mReschedulingImage.setImageResource(R.mipmap.counter_grey);
                mCancellationImage.setImageResource(R.mipmap.counter_grey);
                break;
            case RESCHEDULING:
                mDescribeButton.setOnClickListener(this);
                mBidButton.setOnClickListener(this);
                mReschedulingButton.setOnClickListener(this);
                imgBidLine.setImageResource(R.mipmap.line_full);
                imgReschedulingLine.setImageResource(R.mipmap.line_full);
                mReschedulingText.setTextColor(getResources().getColor(R.color.JGGCyan));
                mDescribeImage.setImageResource(R.mipmap.counter_greytick);
                mBidImage.setImageResource(R.mipmap.counter_greytick);
                mReschedulingImage.setImageResource(R.mipmap.counter_blueactive);
                mCancellationImage.setImageResource(R.mipmap.counter_grey);
                break;
            case CANCELLATION:
                mDescribeButton.setOnClickListener(this);
                mBidButton.setOnClickListener(this);
                mReschedulingButton.setOnClickListener(this);
                mCancellationButton.setOnClickListener(this);
                imgBidLine.setImageResource(R.mipmap.line_full);
                imgReschedulingLine.setImageResource(R.mipmap.line_full);
                imgCancellationLine.setImageResource(R.mipmap.line_full);
                mCancellationText.setTextColor(getResources().getColor(R.color.JGGCyan));
                    mDescribeImage.setImageResource(R.mipmap.counter_greytick);
                    mBidImage.setImageResource(R.mipmap.counter_greytick);
                    mReschedulingImage.setImageResource(R.mipmap.counter_greytick);
                    mCancellationImage.setImageResource(R.mipmap.counter_blueactive);
                break;
            default:
                break;
        }
    }


    @Override
    public void onClick(View view) {
        listener.onTabItemClick(view);
    }

    private OnTabItemClickListener listener;

    public interface OnTabItemClickListener {
        void onTabItemClick(View view);
    }

    public void setTabItemClickLietener(OnTabItemClickListener listener) {
        this.listener = listener;
    }
}
