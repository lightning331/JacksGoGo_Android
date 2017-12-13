package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Appointment;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.Models.Base.Global;
import com.kelvin.jacksgogo.Models.JGGBiddingProviderModel;
import com.kelvin.jacksgogo.Models.User.JGGUserBaseModel;
import com.kelvin.jacksgogo.R;
import com.makeramen.roundedimageview.RoundedImageView;

import org.w3c.dom.Text;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by PUMA on 11/13/2017.
 */

public class AppBiddingProviderCell extends RecyclerView.ViewHolder {

    protected Context mContext;

    public LinearLayout cellBackground;
    public RoundedImageView avatar;
    public TextView userName;
    public TextView price;
    public TextView status;
    public MaterialRatingBar ratingBar;
    public ImageView imgProposal;
    public LinearLayout btnProposal;

    public AppBiddingProviderCell(Context context, View itemView) {
        super(itemView);
        mContext = context;

        cellBackground = itemView.findViewById(R.id.background_layout);
        avatar = itemView.findViewById(R.id.img_bidding_provider_avatar);
        userName = itemView.findViewById(R.id.lbl_bidding_provider_username);
        price = itemView.findViewById(R.id.lbl_bidding_provider_price);
        status = itemView.findViewById(R.id.lbl_bidding_provider_status);
        ratingBar = itemView.findViewById(R.id.bidding_provider_ratingBar);
        imgProposal = itemView.findViewById(R.id.img_proposal);
        btnProposal = itemView.findViewById(R.id.btn_proposal);
    }

    public void setData(JGGBiddingProviderModel provider) {

        JGGUserBaseModel user = provider.getUser();
        Global.BiddingStatus status = provider.getStatus();

        this.status.setVisibility(View.GONE);

        if (status == Global.BiddingStatus.NEWPROPOSAL) {
            cellBackground.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGGreen10Percent));
        } else if (status == Global.BiddingStatus.PENDING) {

        } else if (status == Global.BiddingStatus.NOTRESPONDED) {
            imgProposal.setVisibility(View.GONE);
            price.setVisibility(View.GONE);
        } else if (status == Global.BiddingStatus.DECLINED) {
            this.status.setVisibility(View.VISIBLE);
            imgProposal.setVisibility(View.GONE);
            price.setVisibility(View.GONE);
            itemView.setAlpha(.5f);
        } else if (status == Global.BiddingStatus.REJECTED) {
            this.status.setVisibility(View.VISIBLE);
            itemView.setAlpha(.5f);
        }
        avatar.setImageResource(user.getAvatarUrl());
        userName.setText(user.getFullname());
        ratingBar.setRating(user.getRate());
        price.setText("$"+String.valueOf(provider.getPrice()));
        this.status.setText(provider.getStatus().toString());
    }
}
