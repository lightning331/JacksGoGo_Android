package com.kelvin.jacksgogo.CustomView.RecyclerViewCell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.makeramen.roundedimageview.RoundedImageView;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by PUMA on 12/12/2017.
 */

public class ReviewCell extends RelativeLayout {

    private Context mContext;

    public TextView lblDate;
    public MaterialRatingBar ratingBar;
    public TextView lblDescription;
    public RoundedImageView avatar;

    public ReviewCell(Context context) {
        super(context);
        this.mContext = context;

        initView();
    }

    private void initView() {
        LayoutInflater mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mLayoutInflater.inflate(R.layout.cell_reviews, this);

        lblDate = (TextView) view.findViewById(R.id.lbl_date);
        lblDescription = (TextView) view.findViewById(R.id.lbl_description);
        ratingBar = (MaterialRatingBar) view.findViewById(R.id.user_ratingbar);
        avatar = (RoundedImageView) view.findViewById(R.id.img_avatar);
    }
}
