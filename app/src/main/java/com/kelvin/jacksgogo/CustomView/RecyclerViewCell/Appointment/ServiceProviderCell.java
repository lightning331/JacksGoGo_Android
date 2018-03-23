package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Appointment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global.JGGProposalStatus;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserProfileModel;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

import static com.kelvin.jacksgogo.Utils.Global.JGGProposalStatus.declined;
import static com.kelvin.jacksgogo.Utils.Global.JGGProposalStatus.rejected;
import static com.kelvin.jacksgogo.Utils.Global.getProposalStatus;

/**
 * Created by PUMA on 11/13/2017.
 */

public class ServiceProviderCell extends RecyclerView.ViewHolder {

    protected Context mContext;

    public LinearLayout cellBackground;
    public RoundedImageView avatar;
    public TextView lblUserName;
    public TextView lblPrice;
    public TextView lblStatus;
    public MaterialRatingBar ratingBar;
    public ImageView imgProposal;
    public LinearLayout btnProposal;
    public ImageView imgChat;
    public TextView lblMessageCount;

    public ServiceProviderCell(Context context, View itemView) {
        super(itemView);
        mContext = context;

        cellBackground = itemView.findViewById(R.id.background_layout);
        avatar = itemView.findViewById(R.id.img_bidding_provider_avatar);
        lblUserName = itemView.findViewById(R.id.lbl_bidding_provider_username);
        lblPrice = itemView.findViewById(R.id.lbl_bidding_provider_price);
        lblStatus = itemView.findViewById(R.id.lbl_bidding_provider_status);
        ratingBar = itemView.findViewById(R.id.bidding_provider_ratingBar);
        imgProposal = itemView.findViewById(R.id.img_proposal);
        btnProposal = itemView.findViewById(R.id.btn_proposal);
        imgChat = itemView.findViewById(R.id.chat_icon);
        lblMessageCount = itemView.findViewById(R.id.lblBadgeCount);
    }

    public void setData(JGGProposalModel proposal) {

        JGGUserProfileModel provider = proposal.getUserProfile();
        JGGProposalStatus status = proposal.getStatus();

        lblStatus.setVisibility(View.GONE);

        if (proposal.isInvited()) {
            imgProposal.setVisibility(View.GONE);
            lblPrice.setVisibility(View.GONE);
        }
//        if (status == newproposal) {
//            cellBackground.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGGreen10Percent));
//        }
        if (status == declined) {
            lblStatus.setVisibility(View.VISIBLE);
            imgProposal.setVisibility(View.GONE);
            lblPrice.setVisibility(View.GONE);
            itemView.setAlpha(.5f);
        } else if (status == rejected) {
            lblStatus.setVisibility(View.VISIBLE);
            itemView.setAlpha(.5f);
        }

        // Need to fix in API field
        if (proposal.getMessageCount() == 0) {
            imgChat.setImageResource(R.mipmap.chat_green);
            lblMessageCount.setVisibility(View.GONE);
        } else if (proposal.getMessageCount() > 0) {
            imgChat.setImageResource(R.mipmap.chat_filled_green);
            lblMessageCount.setText(String.valueOf(proposal.getMessageCount()));
        }
        // User avatar
        Picasso.with(mContext)
                .load(provider.getUser().getPhotoURL())
                .placeholder(R.mipmap.icon_profile)
                .into(avatar);
        // User name
        if (provider.getUser().getSurname() == null)
            lblUserName.setText(provider.getUser().getUserName());
        else
            lblUserName.setText(provider.getUser().getFullName());
        // User rating
        if (provider.getUser().getRate() == null || provider.getUser().getRate() == 0)
            ratingBar.setRating(0.0f);
        else
            ratingBar.setRating(provider.getUser().getRate().floatValue());
        // Budget
        lblPrice.setText("$" + String.valueOf(proposal.getBudget()));
        // Status
        lblStatus.setText(getProposalStatus(status));
    }
}
